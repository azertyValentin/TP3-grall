- Repertoire {

	@PUT
	@Produces(TYPE_MEDIA)
	@Consumes(TYPE_MEDIA)
	@ReponsesPUTOption
	// Requête (méthode http + url) : PUT sur http://BIBLIO/CHEMIN_BIBLIO
	// Corps : Livre
	// Réponses (à spécifier par code) : 
	// - code : 404 ou 200
	Optional<HyperLien<Livre>> chercher(Livre l);


	@PUT
	@ReponsesPUTOption
	@Path(JAXRS.SOUSCHEMIN_ASYNC)
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : PUT sur http://BIBLIO/CHEMIN_BIBLIO/async 
	// Corps : Livre
	// Réponses (à spécifier par code) : 404 ou 200
	// - code : 
	Future<Optional<HyperLien<Livre>>> chercherAsynchrone(Livre l, @Suspended final AsyncResponse ar);

	@GET
	@Path(SOUSCHEMIN_CATALOGUE)
	@Produces(TYPE_MEDIA)
	// Requête (méthode http + url) : GET sur http://BIBLIO/CHEMIN_BIBLIO/catalogue 
	// Corps : vide
	// Réponses (à spécifier par code) : 404 ou 200
	// - code : 
	HyperLiens<Livre> repertorier();

- Archive 
	@Path("{id}")
	@ReponsesGETNullEn404
	// Adresse de la sous-ressource : 
	// Requête sur la sous-ressource (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	Livre sousRessource(@PathParam("id") IdentifiantLivre id) ;

	@Path("{id}")
	@GET 
	@Produces(JAXRS.TYPE_MEDIA)
	@ReponsesGETNullEn404
	// Requête (méthode http + url) : http://BIBLIO/CHEMIN_BIBLIO/{id}
	// Corps : id livre
	// Réponses (à spécifier par code) : 404 ou 200
	// - code : 
	Livre getRepresentation(@PathParam("id") IdentifiantLivre id);

	@POST
	@ReponsesPOSTEnCreated
	@Consumes(JAXRS.TYPE_MEDIA)
	@Produces(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : http://BIBLIO/CHEMIN_BIBLIO
	// Corps : Livre
	// Réponses (à spécifier par code) :
	// - code : 201
	HyperLien<Livre> ajouter(Livre l);
}

- AdminAlgo
	@PUT
	@Path(JAXRS.SOUSCHEMIN_ALGO_RECHERCHE)
	@Consumes(JAXRS.TYPE_MEDIA)
	// Requête (méthode http + url) : 
	// Corps : 
	// Réponses (à spécifier par code) :
	// - code : 
	void changerAlgorithmeRecherche(NomAlgorithme algo);
	
	
Réponse à la question : 3.2.2.3 Couche données - JAXB
Q? En étudiant les interfaces NomAlgorithme et Livre, donner le schéma et un exemple de données XML pour un nom d'algorithme et un livre. Répondre dans le readme. 

NomAlgorithme:

<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" 
           elementFormDefault="qualified"
           attributeFormDefault="unqualified">

<xs:element name="nomAlgorithme" type="NomAlgorithme"/>

<xs:complexType name="NomAlgorithme">
  <xs:sequence>
    <xs:element name="nom" type="xs:string"/>
  </xs:sequence>
</xs:complexType>
</xs:schema>

Livre:

<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" 
           elementFormDefault="qualified"
           attributeFormDefault="unqualified">

<xs:element name="livre" type="Livre"/>

<xs:complexType name="Livre">
  <xs:sequence>
    <xs:element name="titre" type="xs:string"/>
  </xs:sequence>
</xs:complexType>
</xs:schema>


Résultats:

Le client se trouve dans src/client/ClientRecherche

recherche sync seq: 7698 ms

recherche sync multi: 1552 ms

recherche sync stream 8: 1528 ms

recherche sync stream rx: 1511 ms

recherche async seq: 7557 ms

recherche async multi: 1532 ms

recherche async stream 8: 1555 ms

recherche async stream rx: 1509 ms




 