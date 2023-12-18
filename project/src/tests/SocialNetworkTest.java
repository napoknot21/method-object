package tests;
import opinion.ISocialNetwork;
import opinion.SocialNetwork;
import exceptions.BadEntryException;
import exceptions.MemberAlreadyExistsException;
import exceptions.ItemFilmAlreadyExistsException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.NotMemberException;
import exceptions.NotItemException;
import java.util.LinkedList;

public class SocialNetworkTest {

    public static void initialisationTest() {
	System.out.println("Testing  initialisation  of a brand new ISocialNetwork  ");
	try {

	    // a brand new ISocialNetwork should not contain any member nor any item
	    ISocialNetwork sn = new SocialNetwork();
	    if (sn.countNbMembers()!= 0) {
		System.out.println("Err 1.1 :  non-zero number of Member in a newly created ISocialNetwork");
		System.exit(1);
	    }
	    if (sn.countNbBooks() != 0) {
		System.out.println("Err 1.2 : non-zero number of Book in a newly created ISocialNetwork");
		System.exit(1);
	    }
	    if (sn.countNbFilms() != 0) {
		System.out.println("Err 1.3 : non-zero number of Film in a newly created ISocialNetwork");
		System.exit(1);
	    }
	    
	}
	catch (Exception e) {
	    System.out.println("Unexpected Exception : " + e);
	    e.printStackTrace();
	}
    }
    public static void addMemberTest() {
	int nbMembres = 0;
	int nbLivres = 0;
	int nbFilms = 0;

	System.out.println("Tests  ajouter des membres au réseau social  ");


	ISocialNetwork sn = new SocialNetwork();

	// tests de addMember
	nbFilms = sn.countNbFilms();
	nbLivres = sn.countNbBooks();

	// tentative d'ajout de membres avec entrées "incorrectes"

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember(null, "qsdfgh", "");	
	    System.out.println("Erreur 3.1 :  l'ajout d'un membre dont le pseudo n'est pas instancié est accepté ");
	}
	catch (BadEntryException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.1 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.1, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("  ", "qsdfgh", "");	
	    System.out.println("Erreur 3.2 :  l'ajout d'un membre dont le pseudo ne contient pas un caractère, autre que des espaces, est accepté ");
	}
	catch (BadEntryException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.2 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}			
	catch (Exception e) {
	    System.out.println("Erreur 3.2, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("B", null, "");	
	    System.out.println("Erreur 3.3 :  l'ajout d'un membre dont le password n'est pas instancié est accepté ");
	}
	catch (BadEntryException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.3 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.3, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("B", "  qwd  ", "");	
	    System.out.println("Erreur 3.4 :  l'ajout d'un membre dont le password ne contient pas au moins 4 caractères, autre que des espaces de début ou de fin, est accepté ");
	}
	catch (BadEntryException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.4 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.4, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("BBBB", "bbbb", null);	
	    System.out.println("Erreur 3.5 :  l'ajout d'un membre dont le profil n'est pas instancié est accepté ");
	}
	catch (BadEntryException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.5 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.5, Exception non prévue : " + e);
	    e.printStackTrace();
	}


	// ajout de 3 membres avec entrées "correctes"
	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("Paul", "paul", "lecteur impulsif");
	    sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
	    sn.addMember("Alice", "alice", "passionnée de bande dessinée");
	    if (sn.countNbMembers()!= (nbMembres + 3)) 
		System.out.println("Erreur 3.6 :  le nombre de membres après ajout de 3 membres n'a pas augmenté de 3");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.6, Exception non prévue : " + e);
	    e.printStackTrace();
	}


	// tentative d'ajout de membre "existant"
	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("Paul", "abcdefghij", "");	
	    System.out.println("Erreur 3.7 :  l'ajout d'un membre avec le pseudo du premier membre ajouté est accepté ");
	}
	catch (MemberAlreadyExistsException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.7 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.7, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("Alice", "abcdefghij", "");	
	    System.out.println("Erreur 3.8 :  l'ajout d'un membre avec le pseudo du dernier membre ajouté est accepté ");
	}
	catch (MemberAlreadyExistsException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.8 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.8, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("anToine", "abcdefghij", "");	
	    System.out.println("Erreur 3.9 :  l'ajout d'un membre avec un pseudo existant (avec casse différente) est accepté ");
	}
	catch (MemberAlreadyExistsException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.9 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.9, Exception non prévue : " + e);
	    e.printStackTrace();
	}


	nbMembres = sn.countNbMembers();
	try {
	    sn.addMember("  Antoine  ", "abcdefghij", "");	
	    System.out.println("Erreur 3.10 :  l'ajout d'un membre avec un pseudo existant (avec leading et trailing blanks) est accepté ");
	}
	catch (MemberAlreadyExistsException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.10 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.10, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	nbMembres = sn.countNbMembers();
	String nomConstruit = "An";
	String nomConstruit2 = "ne";
	nomConstruit = nomConstruit + "toi" + nomConstruit2 ;	// "Antoine"
	try {
	    sn.addMember(nomConstruit, "abcdefghij", "");	
	    System.out.println("Erreur 3.10 :  l'ajout d'un membre avec un pseudo existant (obtenu par concaténation de String) est accepté");
	}
	catch (MemberAlreadyExistsException e) {
	    if (sn.countNbMembers() != nbMembres)
		System.out.println("Erreur 3.10 :  le nombre de membres après tentative d'ajout refusée a été modifié");
	}
	catch (Exception e) {
	    System.out.println("Erreur 3.10, Exception non prévue : " + e);
	    e.printStackTrace();
	}

	if (nbFilms != sn.countNbFilms()) {
	    System.out.println("Erreur 3.11 :  le nombre de films après utilisation de addMember a été modifié");
	}
	if (nbLivres != sn.countNbBooks()) {
	    System.out.println("Erreur 3.12 :  le nombre de livres après utilisation de addMember a été modifié");				
	}

    }

    public static void addItemTest() {
	int nbMembres = 0;
	int nbLivres = 0;
	int nbFilms = 0;
		
	System.out.println("Tests ajouter des items au réseau social  ");
		
		
	try {

	    ISocialNetwork sn = new SocialNetwork();
	    // ajout de 3 membres avec entrées "correctes"
	    nbMembres = sn.countNbMembers();
	    sn.addMember("Paul", "paul", "lecteur impulsif");
	    sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
	    sn.addMember("Alice", "alice", "passionnée de bande dessinée");

			
	    // tests de addItemLivre et addItemFilm
	    nbMembres = sn.countNbMembers();
			
	    // tentative d'ajout de livres  avec entrées "incorrectes"
	    nbLivres = sn.countNbBooks();
	    nbFilms = sn.countNbFilms();
			
	    try {
		sn.addItemBook(null, "antoine", "xxxxx", "yyyyy", "zzzzz", 123);	
		System.out.println("Erreur 5.0.1 :  l'ajout d'un livre avec pseudo non instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.0.1 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("  ", "antoine", "xxxxx", "yyyyy", "zzzzz", 123);	
		System.out.println("Erreur 5.0.2 :  l'ajout d'un livre avec un pseudo qui ne contient pas un caractère, autre que des espaces, est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.0.2 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", null, "xxxxx", "yyyyy", "zzzzz", 123);	
		System.out.println("Erreur 5.0.3 :  l'ajout d'un livre avec password  non instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.0.3 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", " tre", "xxxxx", "yyyyy", "zzzzz", 123);	
		System.out.println("Erreur 5.0.4 :  l'ajout d'un livre avec un password qui ne contient pas au moins 4 caractères, autre que des espaces de début ou de fin, est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.0.4 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Paul", "antoine", "xxxxx", "yyyyy", "zzzzz", 123);	
		System.out.println("Erreur 5.0.5 :  l'ajout d'un livre avec un pseudo et un password qui ne  correspondent pas est accepté ");
	    }
	    catch (NotMemberException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.0.5 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", "antoine", null, "qsdfgh", "aaaaa", 123);	
		System.out.println("Erreur 5.1 :  l'ajout d'un livre dont le titre n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.1 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", "antoine", "  ", "qsdfgh", "aaaaa", 123);	
		System.out.println("Erreur 5.2 :  l'ajout d'un livre dont le titre ne contient pas un caractère, autre que des espaces, est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.2 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", "antoine", "titre", null, "aaaaa", 123);	
		System.out.println("Erreur 5.3 :  l'ajout d'un livre dont le genre n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.3 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", "antoine", "titre", "genre", null, 123);	
		System.out.println("Erreur 5.4 :  l'ajout d'un livre dont l'auteur n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.4 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", "antoine", "titre", "genre", "auteur", -12);	
		System.out.println("Erreur 5.6 :  l'ajout d'un livre dont le nombre de pages est négatif est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.6 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Antoine", "antoine", "titre", "genre", "auteur", 0);	
		System.out.println("Erreur 5.7 :  l'ajout d'un livre dont le nombre de pages est nul est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 5.7 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }
			
	    // tentative d'ajout de films  avec entrées "incorrectes"
	    nbLivres = sn.countNbBooks();
	    nbFilms = sn.countNbFilms();

	    try {
		sn.addItemFilm(null, "antoine", "xxxxx", "yyyyy", "zzzzz", "tttt", 123);	
		System.out.println("Erreur 7.0.1 :  l'ajout d'un film avec pseudo non instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.0.1 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();			
	    try {
		sn.addItemFilm("  ", "antoine", "xxxxx", "yyyyy", "zzzzz", "tttt", 123);	
		System.out.println("Erreur 7.0.2 :  l'ajout d'un film avec un pseudo qui ne contient pas un caractère, autre que des espaces, est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.0.2 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();			
	    try {
		sn.addItemFilm("Antoine", null, "xxxxx", "yyyyy", "zzzzz", "tttt", 123);	
		System.out.println("Erreur 7.0.3 :  l'ajout d'un film avec password  non instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.0.3 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();			
	    try {
		sn.addItemFilm("Antoine", " tg", "xxxxx", "yyyyy", "zzzzz", "tttt", 123);	
		System.out.println("Erreur 7.0.4 :  l'ajout d'un film avec un password qui ne contient pas au moins 4 caractères, autre que des espaces de début ou de fin, est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.0.4 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();			
	    try {
		sn.addItemFilm("Alice", "paul", "xxxxx", "yyyyy", "zzzzz", "tttt", 123);	
		System.out.println("Erreur 7.0.5 :  l'ajout d'un film avec un pseudo et un password qui ne  correspondent pas est accepté ");
	    }
	    catch (NotMemberException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.0.5 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
			
	    nbFilms = sn.countNbFilms();			
	    try {
		sn.addItemFilm("Paul", "paul", null, "qsdfgh", "realisateur", "scenariste", 123);	
		System.out.println("Erreur 7.1 :  l'ajout d'un film dont le titre n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.1 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "  ", "qsdfgh", "realisateur", "scenariste", 123);	
		System.out.println("Erreur 7.2 :  l'ajout d'un film dont le titre ne contient pas un caractère, autre que des espaces, est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.2 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "titre", null, "realisateur", "scenariste", 123);	
		System.out.println("Erreur 7.3 :  l'ajout d'un film dont le genre n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.3 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "titre", "genre", null, "scenariste", 123);	
		System.out.println("Erreur 7.4 :  l'ajout d'un film dont le réalisateur n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.4 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "titre", "genre", "realisateur", null, 123);	
		System.out.println("Erreur 7.5 :  l'ajout d'un film dont le scénariste n'est pas instancié est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.5 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "titre", "genre", "realisateur", "scenariste", -12);	
		System.out.println("Erreur 7.6 :  l'ajout d'un film dont la durée est négative est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.6 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "titre", "genre", "realisateur", "scenariste", 0);	
		System.out.println("Erreur 7.7 :  l'ajout d'un film dont la durée est nulle est accepté ");
	    }
	    catch (BadEntryException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 7.7 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }

	    // ajout de 3 livres et 4 films "corrects"
	    nbLivres = sn.countNbBooks();
	    nbFilms = sn.countNbFilms();
	    sn.addItemBook("Alice", "alice", "Lignes de faille", "roman", "Nancy Huston", 220);
	    sn.addItemFilm("Alice", "alice", "Le train sifflera trois fois", "western 1952", "Fred Zinnemann", "Carl Foreman", 85);
	    sn.addItemBook("Paul", "paul", "La peste", "roman", " Albert Camus", 336);
	    sn.addItemFilm("Paul", "paul", "Avant l'aube", "thriller 2011", "Raphael Jacoulot", "Lise Macheboeuf et Raphael Jacoulot", 104);
	    sn.addItemBook("Antoine", "antoine", "Guerre et Paix", "roman", "Leon Tosltoi", 1247);
	    sn.addItemFilm("Antoine", "antoine", "Le discours d'un roi", "drame historique 2010", "Tom Hooper", "David Seidler", 118);
	    sn.addItemFilm("Alice", "alice", "Black Swan", "drame 2010", "Darren Aronofsky", "John McLaughlin et Mark Heyman et Andres Heinz", 103);
	    if (sn.countNbBooks()!= (nbLivres + 3)) 
		System.out.println("Erreur 8.0 :  le nombre de livres après ajout de 3 livres n'a pas augmenté de 3");
	    if (sn.countNbFilms()!= (nbFilms + 4)) 
		System.out.println("Erreur 8.0 :  le nombre de films après ajout de 4 livres n'a pas augmenté de 4");
			
	    // tentative d'ajout de livre  existant
	    nbFilms = sn.countNbFilms();
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Alice", "alice", "la Peste", "roman", " Albert Camus", 336);	
		System.out.println("Erreur 8.1 :  l'ajout d'un livre avec un titre existant (avec case différente) est accepté ");
	    }
	    catch (ItemBookAlreadyExistsException e) {
		if (sn.countNbBooks() != nbLivres)
		    System.out.println("Erreur 8.1 :  le nombre de livres après tentative d'ajout refusée a été modifié");
	    }

	    // ajout d'un livre  avec titre de film existant
	    nbLivres = sn.countNbBooks();
	    try {
		sn.addItemBook("Alice", "alice", "Le train sifflera trois fois", "roman", " J. W. Cunningham", 257);	
		if (sn.countNbBooks() != (nbLivres + 1))
		    System.out.println("Erreur 8.2 :  le nombre de livres après ajout n'a pas été modifié");
	    }
	    catch (ItemBookAlreadyExistsException e) {
		System.out.println("Erreur 8.2 :  l'ajout d'un livre avec un titre de film existant  a été refusé ");
	    }

	    if (nbFilms != sn.countNbFilms()) {
		System.out.println("Erreur 8.4 :  le nombre de films après utilisation de addItemLivre a été modifié");
	    }
			
	    // tentative d'ajout de film  existant
	    nbLivres = sn.countNbBooks();
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", " black swan  ", "drame 2010", "Darren Aronofsky", "John McLaughlin et Mark Heyman et Andres Heinz", 103);
		System.out.println("Erreur 8.5 :  l'ajout d'un film avec un titre existant (avec case différente) est accepté ");
	    }
	    catch (ItemFilmAlreadyExistsException e) {
		if (sn.countNbFilms() != nbFilms)
		    System.out.println("Erreur 8.5 :  le nombre de films après tentative d'ajout refusée a été modifié");
	    }
			
	    // ajout d'un film  avec titre de livre existant
	    nbFilms = sn.countNbFilms();
	    try {
		sn.addItemFilm("Paul", "paul", "Guerre et Paix", "aventure historique", "King Vidor", "Bridget Boland, Robert Westbery", 200);	
		if (sn.countNbFilms() != (nbFilms + 1))
		    System.out.println("Erreur 8.6 :  le nombre de films après ajout n'a pas été modifié");
	    }
	    catch (ItemFilmAlreadyExistsException e) {
		System.out.println("Erreur 8.6 :  l'ajout d'un film avec un titre de livre existant  a été refusé ");
	    }

			
	    if (nbLivres != sn.countNbBooks()) {
		System.out.println("Erreur 8.8 :  le nombre de livres après utilisation de addItemFilm a été modifié");				
	    }

	    if (nbMembres != sn.countNbMembers()) {
		System.out.println("Erreur 7.10 :  le nombre de membres après utilisation de addItemFilm et addItemLivre a été modifié");
	    }

	}
	catch (Exception e) {
	    System.out.println("Exception non prévue : " + e);
	    e.printStackTrace();
	}
    }


    public static void reviewItemTest() {
	System.out.println("Tests  de reviewing d'items du réseau social  ");
	// to complete

    }

    
    public static void consultItemsTest() {

	int nbMembres = 0;
	int nbLivres = 0;
	int nbFilms = 0;
		
	System.out.println("Tests  de consultation d'items du réseau social  ");
		
	try {

	    // un réseau social créé ne doit avoir ni membres ni items
	    ISocialNetwork sn = new SocialNetwork();
			
	    // ajout de 3 membres avec entrées "correctes"
	    nbMembres = sn.countNbMembers();
	    sn.addMember("Paul", "paul", "lecteur impulsif");
	    sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
	    sn.addMember("Alice", "alice", "passionnée de bande dessinée");
	    if (sn.countNbMembers()!= (nbMembres + 3)) 
		System.out.println("Erreur 3.6 :  le nombre de membres après ajout de 3 membres n'a pas augmenté de 3");

	    // ajout de 3 livres et 4 films "corrects"
	    nbLivres = sn.countNbBooks();
	    nbFilms = sn.countNbFilms();
	    sn.addItemBook("Alice", "alice", "Lignes de faille", "roman", "Nancy Huston", 220);
	    sn.addItemFilm("Alice", "alice", "Le train sifflera trois fois", "western 1952", "Fred Zinnemann", "Carl Foreman", 85);
	    sn.addItemBook("Paul", "paul", "La peste", "roman", " Albert Camus", 336);
	    sn.addItemFilm("Paul", "paul", "Avant l'aube", "thriller 2011", "Raphael Jacoulot", "Lise Macheboeuf et Raphael Jacoulot", 104);
	    sn.addItemBook("Antoine", "antoine", "Guerre et Paix", "roman", "Leon Tosltoi", 1247);
	    sn.addItemFilm("Antoine", "antoine", "Le discours d'un roi", "drame historique 2010", "Tom Hooper", "David Seidler", 118);
	    sn.addItemFilm("Alice", "alice", "Black Swan", "drame 2010", "Darren Aronofsky", "John McLaughlin et Mark Heyman et Andres Heinz", 103);
	    sn.addItemBook("Alice", "alice", "Le train sifflera trois fois", "roman", " J. W. Cunningham", 257);
	    sn.addItemFilm("Paul", "paul", "Guerre et Paix", "aventure historique", "King Vidor", "Bridget Boland, Robert Westbery", 200);
	    // review d'un film et d'un livre ayant le même titre et par plusieurs
	    sn.reviewItemFilm("Alice", "alice", "Guerre et Paix", 4.5f, "on ne voit pas le temps passer");	
	    sn.reviewItemFilm("Antoine", "antoine", "Guerre et Paix", 3.5f, "on ne voit pas le temps passer");	
	    sn.reviewItemBook("Alice", "alice", "Guerre et Paix", 2.0f, "un peu long");	
	    sn.reviewItemBook("Paul", "paul", "Guerre et Paix", 3.0f, "un peu long");	
			
	    // tests de consultItem
	    nbMembres = sn.countNbMembers();
	    nbFilms = sn.countNbFilms();
	    nbLivres = sn.countNbBooks();
	    try {
		sn.consultItems(null);	
		System.out.println("Erreur 9.1 :  la consultation d'un item dont le nom n'est pas instancié est acceptée ");
	    }
	    catch (BadEntryException e) {
	    }
	    try {
		sn.consultItems("  ");	
		System.out.println("Erreur 9.2 :  la consultation d'un item dont le nom ne contient pas un caractère, autre que des espaces, est acceptée ");
	    }
	    catch (BadEntryException e) {
	    }
	    try {
		LinkedList <String> liste = sn.consultItems("La malaria");	
		if (liste.size() != 0)
		    System.out.println("Erreur 9.3 :  la consultation d'un item inexistant trouve des items qui correspondent ");
	    }
	    catch (BadEntryException e) {
		System.out.println("Erreur 9.3 :  la consultation d'un item dont le nom est correct n'est pas acceptée ");
	    }
	    try {
		LinkedList <String> liste = sn.consultItems(" La Peste  ");	
		if (liste.size() != 1)
		    System.out.println("Erreur 9.4 :  la consultation d'un item livre existant ne trouve pas d'item qui correspond ");
	    }
	    catch (BadEntryException e) {
		System.out.println("Erreur 9.4 :  la consultation d'un item livre existant dont le nom est correct n'est pas acceptée ");
	    }
	    try {
		LinkedList <String> liste = sn.consultItems(" BlaCk swan  ");	
		if (liste.size() != 1)
		    System.out.println("Erreur 9.5 :  la consultation d'un item film existant ne trouve pas d'item qui correspond ");
	    }
	    catch (BadEntryException e) {
		System.out.println("Erreur 9.5 :  la consultation d'un item film existant dont le nom est correct n'est pas acceptée ");
	    }
	    try {
		LinkedList <String> liste = sn.consultItems("Guerre et Paix");	
		if (liste.size() != 2)
		    System.out.println("Erreur 9.6 :  la consultation d'un item film et livre existant ne trouve pas d'items qui correspondent ");
		else {
		    String sFilm = liste.getFirst();
		    String sLivre = liste.getLast();
		    String st;
		    if (sLivre.contains("aventure historique")  && (sFilm.contains("roman"))) {
			st = sFilm;
			sFilm = sLivre;
			sLivre = st;
		    }
		    if (sLivre.contains("King Vidor")  || sFilm.contains("Leon Tosltoi")) 					
			System.out.println("Erreur 9.6 :  la consultation d'un item film et livre existant ne rend pas les chaînes des items qui correspondent ");				
		    if (!sLivre.contains("Guerre et Paix")  &&  !sLivre.contains("roman")  && !sLivre.contains("Leon Tosltoi") && !sLivre.contains("1247"))
			System.out.println("Erreur 9.6 :  la consultation d'un item film et livre existant ne rend pas la chaîne du livre qui correspond ");
		    if (!sFilm.contains("Guerre et Paix")  &&  !sFilm.contains("aventure historique")  && !sFilm.contains("King Vidor") && !sFilm.contains("Bridget Boland, Robert Westbery") && !sFilm.contains("200"))
			System.out.println("Erreur 9.6 :  la consultation d'un item film et livre existant ne rend pas la chaîne du film qui correspond ");
		    if (!sLivre.contains("2.5") || !sFilm.contains("4.0")) 					
			System.out.println("Erreur 9.6 :  la consultation d'un item film et livre existant ne rend pas les chaînes note qui correspondent ");				
		}
	    }
	    catch (BadEntryException e) {
		System.out.println("Erreur 9.6 :  la consultation d'un item film et livre existant dont le nom est correct n'est pas acceptée ");
	    }
			
	    if (nbMembres != sn.countNbMembers()) {
		System.out.println("Erreur 9.10 :  le nombre de membres après utilisation de consultItem  a été modifié");
	    }
	    if (nbFilms != sn.countNbFilms()) {
		System.out.println("Erreur 9.11 :  le nombre de films après utilisation de consultItem a été modifié");
	    }
	    if (nbLivres != sn.countNbBooks()) {
		System.out.println("Erreur 9.12 :  le nombre de livres après utilisation de consultItem a été modifié");				
	    }
	}
	catch (Exception e) {
	    System.out.println("Exception non prévue : " + e);
	    e.printStackTrace();
	}
    }

    
    public static void main(String[] args) {
	initialisationTest();
	System.out.println("\n\n **********************************************************************************************\n");
	// addMemberTest();
	// System.out.println("\n\n **********************************************************************************************\n");
	// addItemTest();
	// System.out.println("\n\n **********************************************************************************************\n");
	// reviewItemTest();
	// System.out.println("\n\n **********************************************************************************************\n");
	// consultItemsTest();
    }

}
