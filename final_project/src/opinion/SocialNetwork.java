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

    public SocialNetwork () {
        this.members = new LinkedList<Member>()
    }

	@Override
	public int countNbMembers() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
        if (login == null) throw new BadEntryException("Login null");
        if (login.trim().length() == 0) throw new BadEntryException("Login black");
        if (password == null) throw new BadEntryException("Password null");
        if (password.trim().length() < 4) throw new BadEntryException("Password black");
        if (description == null) throw new BadEntryException("Description null");
        

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
