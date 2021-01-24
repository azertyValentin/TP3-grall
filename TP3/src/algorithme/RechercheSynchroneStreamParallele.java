package algorithme;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheSynchroneAbstraite;

public class RechercheSynchroneStreamParallele extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return bibliotheques.parallelStream().map((h) -> rechercheSync(h, l, client))
		.filter((x) -> x.isEmpty())
		.findAny()
		.orElse(Optional.empty());
	}

	@Override
	public NomAlgorithme nom() {
		return new ImplemNomAlgorithme("recherche sync stream 8");
	}

}
