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

    private String name;

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
		int nb = 0;
        if (this.members.isEmpty()) return 0;
		//for (int i = 0; i < this.members.size());
        return 0;
	}

	@Override
	public int countNbFilms() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int countNbBooks() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addMember(String login, String password, String description)
			throws BadEntryException, MemberAlreadyExistsException {
		if (login == null) throw new BadEntryException("Login Null error");
        if (login.trim().length() < 1) throw new BadEntryException("Login bad format");
        if (password == null) throw new BadEntryException("Password null error");
        if (password.trim().length() < 4) throw new BadEntryException("Passwod bad format");
        if (description == null) throw new BadEntryException("Description profil null");

        Member m = new Member (login, password, description, this);


	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director, String scenarist,
			int duration) throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {
		// TODO Auto-generated method stub

	}

	@Override
	public void addItemBook(String login, String password, String title, String kind, String author, int nbPages)
			throws BadEntryException, NotMemberException, ItemBookAlreadyExistsException {
		// TODO Auto-generated method stub

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
		return null;
	}

}
