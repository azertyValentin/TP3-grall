package client;

import java.util.Optional;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

import infrastructure.jaxrs.AdapterClientReponsesPUT404EnOption;
import infrastructure.jaxrs.AdapterClientReponsesPUTEnOption;
import infrastructure.jaxrs.HyperLien;
import modele.AdminAlgo;
import modele.ImplemLivre;
import modele.ImplemNomAlgorithme;
import modele.Livre;
import modele.Repertoire;

public class ClientRecherche {
	public static Client clientJAXRS() {
        ClientConfig config = new ClientConfig();
        config.register(new AdapterClientReponsesPUT404EnOption());
        config.register(AdapterClientReponsesPUTEnOption.class);
        return ClientBuilder.newClient(config);
    }
	
	private static void search(Repertoire repertoire) {
        long time = System.nanoTime();
        Optional<HyperLien<Livre>> url = repertoire.chercher(new ImplemLivre("Services5.6"));
        time = System.nanoTime() - time;
        if(url.isPresent())
            System.out.print("Livre disponible à l'adresse : " + url.get().getUri());
        else
            System.out.print("Echec");
     
        System.out.println(" Temps nécessaire : " + (time / 1000000));
    }
	
	public static void main(String[] args) {
        WebTarget cible = clientJAXRS().target("http://localhost:8080/TP3/portail/").property(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML);
        AdminAlgo adminAlgo = WebResourceFactory.newResource(AdminAlgo.class, cible);
        Repertoire repertoire = WebResourceFactory.newResource(Repertoire.class, cible);

        System.out.println("-----\nrecherche sync seq");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync seq"));
        search(repertoire);

        System.out.println("-----\nrecherche sync multi");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync multi"));
        search(repertoire);

        System.out.println("-----\nrecherche sync stream 8");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream 8"));
        search(repertoire);

        System.out.println("-----\nrecherche sync stream rx");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream rx"));
        search(repertoire);

        System.out.println("-----\nrecherche async seq");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync seq"));
        search(repertoire);

        System.out.println("-----\nrecherche async multi");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync multi"));
        search(repertoire);

        System.out.println("-----\nrecherche async stream 8");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream 8"));
        search(repertoire);

        System.out.println("-----\nrecherche async stream rx");
        adminAlgo.changerAlgorithmeRecherche(new ImplemNomAlgorithme("recherche sync stream rx"));
        search(repertoire);
    }
}
