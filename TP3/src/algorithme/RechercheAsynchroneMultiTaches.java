package algorithme;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.InvocationCallback;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;

public class RechercheAsynchroneMultiTaches extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		CountDownLatch doneSignal = new CountDownLatch(bibliotheques.size());
		AtomicReference<Optional<HyperLien<Livre>>> ref = new AtomicReference<>();
		ref.set(Optional.empty());
		InvocationCallback<Optional<HyperLien<Livre>>> retour = new InvocationCallback<Optional<HyperLien<Livre>>>() {

			@Override
			public void completed(Optional<HyperLien<Livre>> response) {
				doneSignal.countDown();
				if (response.isPresent()) ref.set(response);
			}

			@Override
			public void failed(Throwable throwable) {
				// TODO Auto-generated method stub
			}

        };
		for (HyperLien<Bibliotheque> h: bibliotheques) {
			rechercheAsyncAvecRappel(h, l, client, retour);
			try {
				doneSignal.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return ref.get();
	}

	@Override
	public NomAlgorithme nom() {
		return new ImplemNomAlgorithme("recherche async multi");
	}

}
