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
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneStreamRx extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return Observable.fromIterable(bibliotheques)
				.flatMap(h -> Observable.fromCallable(() -> rechercheSync(h, l, client)))
				.subscribeOn(Schedulers.io())
				.filter((x) -> x.isEmpty())
				.blockingFirst(Optional.empty());
	}

	@Override
	public NomAlgorithme nom() {
		return new ImplemNomAlgorithme("recherche sync stream rx");
	}

}
