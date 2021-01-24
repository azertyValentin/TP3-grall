package algorithme;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneMultiTaches extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		ExecutorService executor = Executors.newCachedThreadPool();
		CountDownLatch doneSignal = new CountDownLatch(bibliotheques.size());
		AtomicReference<Optional<HyperLien<Livre>>> ref = new AtomicReference<>();
		ref.set(Optional.empty());
		for (HyperLien<Bibliotheque> h : bibliotheques) {
			executor.submit(() -> {
				Optional<HyperLien<Livre>> buffer = rechercheSync(h, l, client);
				doneSignal.countDown();
				if (buffer.isPresent()) ref.set(buffer);
			});
		}
		try {
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ref.get();
	}

	private NomAlgorithme nom;
	
	public RechercheSynchroneMultiTaches(String str) {
		super();
		this.nom = new ImplemNomAlgorithme(str);
	}

	@Override
	public NomAlgorithme nom() {
		return nom;
	}

}
