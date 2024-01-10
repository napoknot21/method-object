package opinion;

import java.util.LinkedList;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.ItemFilmAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotItemException;
import exceptions.NotMemberException;

public class SocialNetwork implements ISocialNetwork {
	private LinkedList<Member> membres;
	private LinkedList<Film> films;
	private LinkedList<Book> books;
	
	
	
	
	public SocialNetwork() {
		this.membres = new LinkedList<>();
		this.books= new LinkedList<>();
		this.films= new LinkedList<>();
		
	}

	@Override
	public int countNbMembers() {
		
		// TODO Auto-generated method stub
		return this.membres.size();
	}

	@Override
	public int countNbFilms() {
		// TODO Auto-generated method stub
		return this.films.size();
	}

	@Override
	public int countNbBooks() {
		// TODO Auto-generated method stub
		return this.books.size();
	}

	@Override
	public void addMember(String login, String password, String description)
			throws BadEntryException, MemberAlreadyExistsException {
		Member b = null;
		if (login ==null ) {throw new BadEntryException("Le login n'est pas instancié");}
		if (login.trim().length() ==0) {throw new BadEntryException("Le login ne contient pas de caractères (espace exclu)");}
		if(password==null) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(password.trim().length()<4) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(description==null) {throw new BadEntryException("La description n'est pas instanciée");}
		for (Member m : this.membres) if (m.getLogin().equalsIgnoreCase(login.trim())) {throw new MemberAlreadyExistsException("Un membre avec ce login est déjà incrit");}
	
		
		Member a = new Member(login,password,description);
		this.membres.add(a);
		
		
		// TODO Auto-generated method stub

	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director, String scenarist,
			int duration) throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {
		// TODO Auto-generated method stub
		boolean w = false;
		if (login ==null ) {throw new BadEntryException("Le login n'est pas instancié");}
		if (login.trim().length() ==0) {throw new BadEntryException("Le login ne contient pas de caractères (espace exclu)");}
		if (title==null ) {throw new BadEntryException("Le titre n'est pas instancié");}
		if (title.trim().length() ==0) {throw new BadEntryException("Le titre ne contient pas de caractères (espace exclu)");}
		if(password==null) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(password.trim().length()<4) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(kind ==null) {throw new BadEntryException("La type n'est pas instanciée");}
		if(director==null) {throw new BadEntryException("La directeur n'est pas instanciée");}
		if(scenarist==null) {throw new BadEntryException("Le scénariste n'est pas instanciée");}
		if(duration<=0) {throw new BadEntryException("La  durée doit etre strictement supérieur à 0");}
		for (Film f : this.films) if (f.getTitle().equalsIgnoreCase(title.trim())) {throw new ItemFilmAlreadyExistsException("Ce film existe déjà");}
		for(Member m : this.membres)if(m.getLogin()==login && m.getPassword()==password) {w=true;}
		if ( !w) {throw new NotMemberException("Le login ou/et le mot de passe ne correspondent pas");}
		
		
		
		
		Film a = new Film(title,kind,director,scenarist,duration);
		
		this.films.add(a);

	}

	@Override
	public void addItemBook(String login, String password, String title, String kind, String author, int nbPages)
			throws BadEntryException, NotMemberException, ItemBookAlreadyExistsException {
		// TODO Auto-generated method stub
		boolean w = false;
		if (login ==null ) {throw new BadEntryException("Le login n'est pas instancié");}
		if (login.trim().length() ==0) {throw new BadEntryException("Le login ne contient pas de caractères (espace exclu)");}
		if (title==null ) {throw new BadEntryException("Le titre n'est pas instancié");}
		if (title.trim().length() ==0) {throw new BadEntryException("Le titre ne contient pas de caractères (espace exclu)");}
		if(password==null) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(password.trim().length()<4) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(kind ==null) {throw new BadEntryException("La type n'est pas instanciée");}
		if(author==null) {throw new BadEntryException("L'auteur n'est pas instanciée");}
		if(nbPages<=0) {throw new BadEntryException("Le nombre de pages doit etre strictement supérieur à 0");}
		for (Book f : this.books) if (f.getTitle().equalsIgnoreCase(title.trim())) {throw new ItemBookAlreadyExistsException("Ce livre existe déjà");}
		for(Member m : this.membres)if(m.getLogin()==login && m.getPassword()==password) {w=true;}
		if ( !w) {throw new NotMemberException("Le login ou/et le mot de passe ne correspondent pas");}
		Book a = new Book(title,kind ,author,nbPages);
		this.books.add(a);


	}

	@Override
	/*
	public float reviewItemFilm(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		
		// TODO Auto-generated method stub
	
		
		Member m = null;
		for (Member i : this.membres) if( i.getLogin()==login && i.getPassword()==password) { m = i;}

		Review a = new Review(title, mark,comment,m);
		Film b  = null;
		for(int i = 0; i<this.films.size();i++) if(films.get(i).getTitle().equals(title)) {  b = films.get(i);}
		for(Review i : b.getReview()) if(i.getMember()==m) {i=a;}
		return mark;
	}
	*/
	public float reviewItemFilm(String login, String password, String title, float mark, String comment)
	        throws BadEntryException, NotMemberException, NotItemException {

	    // TODO Auto-generated method stub
		boolean w = false;
		boolean x = false;
		if (login ==null ) {throw new BadEntryException("Le login n'est pas instancié");}
		if (login.trim().length() ==0) {throw new BadEntryException("Le login ne contient pas de caractères (espace exclu)");}
		if(password==null) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(password.trim().length()<4) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if (title==null ) {throw new BadEntryException("Le titre n'est pas instancié");}
		if (title.trim().length() ==0) {throw new BadEntryException("Le titre ne contient pas de caractères (espace exclu)");}
		if(mark<(float) 0 || mark>5) {throw new BadEntryException("La note doit etre comprise entre 0 et 5");}
		if (comment ==null ) {throw new BadEntryException("Le commentaire n'est pas instancié");}
		for(Member n : this.membres)if(n.getLogin()==login && n.getPassword()==password) {w=true ;}
		if ( !w) {throw new NotMemberException("Le login ou/et le mot de passe ne correspondent pas");}
		for(Film f : this.films) if(f.getTitle()==title) {x=true;}
		if ( !w) {throw new NotItemException("Aucun film avec ce titre est dans la liste");}
		
	
		

	    Member m=null;
	    for (Member i : this.membres) {
	        if (i.getLogin().equals(login) && i.getPassword().equals(password)) {
	            m = i;
	            break; // Ajout de cette ligne pour arrêter la boucle une fois que le membre est trouvé
	        }
	    }

	    Review a = new Review(title, mark, comment, m);
	    Film b = null;
	    for (int i = 0; i < this.films.size(); i++) {
	        if (films.get(i).getTitle().equals(title)) {
	            b = films.get(i);
	            break; // Ajout de cette ligne pour arrêter la boucle une fois que le film est trouvé
	        }
	    }

	    // Remplacer la revue existante par la nouvelle revue*
	    boolean c =false;
	    for (int i = 0; i < b.getReview().size(); i++) {
	        if (b.getReview().get(i).getMember() == m) {
	            b.getReview().set(i, a); c=true;}
	            break;
	         
	            
	            // Ajout de cette ligne pour arrêter la boucle une fois que la revue est remplacée
	        }
	    
	    
	    if(c==false) {b.addReview(a);}
	    

	    return mark;
	}


	@Override
	/*public float reviewItemBook(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		// TODO Auto-generated method stub
		Member m = null;
		for (Member i : this.membres) if( i.getLogin()==login && i.getPassword()==password) {m =new Member(login,password,i.getProfile());}

		Review a = new Review(title, mark,comment,m);
		Item b  = null;
		for(int i = 0; i<this.books.size();i++) if(books.get(i).getTitle().equals(title)) {  b = books.get(i);}
		for(Review i : b.getReview()) if(i.getMember()==m) {i=a;}
		return mark;
		
	}
	*/
	public float reviewItemBook(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		// TODO Auto-generated method stub
		boolean w = false;
		boolean x = false;
		if (login ==null ) {throw new BadEntryException("Le login n'est pas instancié");}
		if (login.trim().length() ==0) {throw new BadEntryException("Le login ne contient pas de caractères (espace exclu)");}
		if(password==null) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if(password.trim().length()<4) {throw new BadEntryException("Le mot de passe a moins de 4 caractères(espace exclu");}
		if (title==null ) {throw new BadEntryException("Le titre n'est pas instancié");}
		if (title.trim().length() ==0) {throw new BadEntryException("Le titre ne contient pas de caractères (espace exclu)");}
		if(mark<(float) 0 || mark>5) {throw new BadEntryException("La note doit etre comprise entre 0 et 5");}
		if (comment ==null ) {throw new BadEntryException("Le commentaire n'est pas instancié");}
		for(Member n : this.membres)if(n.getLogin()==login && n.getPassword()==password) {w=true ;}
		if ( !w) {throw new NotMemberException("Le login ou/et le mot de passe ne correspondent pas");}
		for(Film f : this.films) if(f.getTitle()==title) {x=true;}
		if ( !w) {throw new NotItemException("Aucun film avec ce titre est dans la liste");}
		
		Member m = null;
	    for (Member i : this.membres) {
	        if (i.getLogin().equals(login) && i.getPassword().equals(password)) {
	            m = i;
	            break; // Ajout de cette ligne pour arrêter la boucle une fois que le membre est trouvé
	        }
	    }

	    Review a = new Review(title, mark, comment, m);
	    Book b = null;
	    for (int i = 0; i < this.books.size(); i++) {
	        if (books.get(i).getTitle().equals(title)) {
	            b = books.get(i);
	            break; // Ajout de cette ligne pour arrêter la boucle une fois que le film est trouvé
	        }
	    }

	    // Remplacer la revue existante par la nouvelle revue*
	    boolean c =false;
	    for (int i = 0; i < b.getReview().size(); i++) {
	        if (b.getReview().get(i).getMember() == m) {
	            b.getReview().set(i, a); c=true;}
	            break;
	         
	            
	            // Ajout de cette ligne pour arrêter la boucle une fois que la revue est remplacée
	        }
	    
	    
	    if(c==false) {b.addReview(a);}
	    

	    return mark;
	}
	
	

	@Override
	public LinkedList<String> consultItems(String title) throws BadEntryException {
		// TODO Auto-generated method stub
		if (title ==null ) {throw new BadEntryException("Le titre n'est pas instancié");}
		if (title.trim().length() ==0) {throw new BadEntryException("Le titre ne contient pas de caractères (espace exclu)");}
		LinkedList<String> L = new LinkedList<String>();
		
		for (Film i :this.films) if( i.getTitle().equalsIgnoreCase(title.trim())){L.add(i.getTitle()+ i.getNote()) ;System.out.println(i.getTitle());System.out.println(i.getNote());}
		for (Book i :this.books) if( i.getTitle().equalsIgnoreCase(title.trim())){L.add(i.getTitle()+ i.getNote());System.out.println(i.getTitle()) ;System.out.println(i.getNote());}
		
		
		return L;
		
	}
	
}
	
	

	
	


