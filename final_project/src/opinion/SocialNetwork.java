package opinion;

import java.util.LinkedList;

import exceptions.BadEntryException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.ItemFilmAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotItemException;
import exceptions.NotMemberException;

public class SocialNetwork implements ISocialNetwork {

    private LinkedList<Member> members;

	private LinkedList<Item> items;


    public SocialNetwork () {

        this.members = new LinkedList<Member>();
		this.items = new LinkedList<Item>();

	}


	/**
     * Getters for the Social Network
     */
    public LinkedList<Member> getMembers () {
        return this.members;
    }

	
	public LinkedList<Item> getItems() {
		return this.items;
	}
	

	@Override
	public int countNbMembers() {
		return this.members.size();
	}

	@Override
	public int countNbFilms() {
		
		if (this.items.size() == 0) return 0;

		int nbFilms = 0;

		for (int i = 0; i < this.items.size(); i++) {
			Item tmp =  this.items.get(i);
			if (tmp instanceof Film) nbFilms++;
		}

		return nbFilms;
	}

	@Override
	public int countNbBooks() {
		if (this.items.size() == 0) return 0;

		int nbFilms = 0;

		for (int i = 0; i < this.items.size(); i++) {
			Item tmp =  this.items.get(i);
			if (tmp instanceof Book) nbFilms++;
		}

		return nbFilms;
	}

	@Override
	public void addMember(String login, String password, String description)
			throws BadEntryException, MemberAlreadyExistsException {
        if (login == null) throw new BadEntryException("Login null");
        if (login.trim().length() == 0) throw new BadEntryException("Login blank");
        if (password == null) throw new BadEntryException("Password null");
        if (password.trim().length() < 4) throw new BadEntryException("Password blank");
        if (description == null) throw new BadEntryException("Description null");
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getLogin().equalsIgnoreCase(login.trim())) throw new MemberAlreadyExistsException();
		}
		this.members.add(new Member (login, password, description));
	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director, String scenarist,
			int duration) throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {
		if (login == null) throw new BadEntryException("Login null");
        if (login.trim().length() == 0) throw new BadEntryException("Login blank");
		if (password == null) throw new BadEntryException("Password null");
        if (password.trim().length() < 4) throw new BadEntryException("Password blank");
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getLogin().equals(login)) {
				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
			}
		} 
		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("title blank");
		if (kind == null) throw new BadEntryException("Title null");
		if (director == null) throw new BadEntryException("Director null");
		if (scenarist == null) throw new BadEntryException("Scenarist null");
		if (duration <= 0) throw new BadEntryException("negative duration");
		if (this.items.size() == 0) this.items.add(new Film (title, kind, director, scenarist, duration));
		else {
			for (int i = 0; i < this.items.size(); i++) {
				if (this.items.get(i) instanceof Film && this.items.get(i).getTitle().equalsIgnoreCase(title.trim())) throw new ItemFilmAlreadyExistsException();
			}
			this.items.add(new Film (title, kind, director, scenarist, duration));
		}
	}

	@Override
	public void addItemBook(String login, String password, String title, String kind, String author, int nbPages)
			throws BadEntryException, NotMemberException, ItemBookAlreadyExistsException {
		if (login == null) throw new BadEntryException("Login null");
        if (login.trim().length() == 0) throw new BadEntryException("Login blank");
		if (password == null) throw new BadEntryException("Password null");
        if (password.trim().length() < 4) throw new BadEntryException("Password blank");
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getLogin().equals(login)) {
				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
			}
		}
		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("title blank");
		if (kind == null) throw new BadEntryException("Kind null");
		if (author == null) throw new BadEntryException("Author null");
		if (nbPages <= 0) throw new BadEntryException("Negative nbPages");
		if (this.items.size() == 0) this.items.add(new Book (title, kind, author, nbPages));
		else {
			for (int i = 0; i < this.items.size(); i++) {
				if (this.items.get(i) instanceof Book && this.items.get(i).getTitle().equalsIgnoreCase(title.trim())) throw new ItemBookAlreadyExistsException();
			}
			this.items.add(new Book (title, kind, author, nbPages));
		}
	}

	@Override
	public float reviewItemFilm(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float reviewItemBook(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public LinkedList<String> consultItems(String title) throws BadEntryException {
		// TODO Auto-generated method stub
		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("Title blank");
		return new LinkedList<String>();
	}

}
