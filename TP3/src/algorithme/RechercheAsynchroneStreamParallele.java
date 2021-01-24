package algorithme;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.client.Client;

import infrastructure.jaxrs.HyperLien;
import infrastructure.jaxrs.Outils;
import modele.AlgorithmeRecherche;
import modele.Bibliotheque;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.NomAlgorithme;
import modele.RechercheAsynchroneAbstraite;

public class RechercheAsynchroneStreamParallele extends RechercheAsynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		return bibliotheques.parallelStream()
				.map((h) -> rechercheAsync(h, l, client))
				.map(Outils::remplirPromesse)
				.filter((x) -> x.isEmpty())
				.findAny()
				.orElse(Optional.empty());
	}
	
	private NomAlgorithme nom;
	
	public RechercheAsynchroneStreamParallele(String str) {
		super();
		this.nom = new ImplemNomAlgorithme(str);
	}

	@Override
	public NomAlgorithme nom() {
		return nom;
	}

}
