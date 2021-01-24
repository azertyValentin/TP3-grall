package algorithme;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;

public class RechercheAsynchroneStreamRx extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return Observable.fromIterable(bibliotheques)
				.flatMap(h -> Observable.fromFuture(rechercheAsync(h, l, client)))
				.subscribeOn(Schedulers.io())
				.filter((x) -> x.isEmpty())
				.blockingFirst(Optional.empty());
	}

	private NomAlgorithme nom;
	
	public RechercheAsynchroneStreamRx(String str) {
		super();
		this.nom = new ImplemNomAlgorithme(str);
	}

	@Override
	public NomAlgorithme nom() {
		return nom;
	}

}
