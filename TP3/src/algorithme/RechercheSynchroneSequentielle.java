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

public class RechercheSynchroneSequentielle extends RechercheSynchroneAbstraite implements AlgorithmeRecherche {

	@Override
	public Optional<HyperLien<Livre>> chercher(Livre l, List<HyperLien<Bibliotheque>> bibliotheques, Client client) {
		Optional<HyperLien<Livre>> buffer;
		for(HyperLien<Bibliotheque> h: bibliotheques) {
			buffer = rechercheSync(h, l, client);
			if (buffer.isPresent()) return buffer;
		}
		return Optional.empty();
	}
	
	
	private NomAlgorithme nom;
	
	public RechercheSynchroneSequentielle(String str) {
		super();
		this.nom = new ImplemNomAlgorithme(str);
	}

	@Override
	public NomAlgorithme nom() {
		return nom;
	}

}
