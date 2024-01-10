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

	private LinkedList<Item> books;
    private LinkedList<Item> films;

    public SocialNetwork () {

        this.members = new LinkedList<Member>();
		this.films = new LinkedList<Item>();
        this.books = new LinkedList<Item>();

	}

	/**
     * Getters for the Social Network
     */
    public LinkedList<Member> getMembers () {
        return this.members;
    }
	
	public LinkedList<Item> getFilms() {
		return this.films;
	}

    public LinkedList<Item> getBooks() {
        return this.books;
    }
	

	@Override
	public int countNbMembers() {
		return this.members.size();
	}

	@Override
	public int countNbFilms() {
	    return this.films.size();
	}

	@Override
	public int countNbBooks() {
		return this.books.size();
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
		this.members.add(new Member (login, password, description, this));
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
		if (this.films.size() == 0) this.films.add(new Film (title, kind, director, scenarist, duration));
		else {
			for (int i = 0; i < this.films.size(); i++) {
				if (this.films.get(i).getTitle().equalsIgnoreCase(title.trim())) throw new ItemFilmAlreadyExistsException();
			}
			this.films.add(new Film (title, kind, director, scenarist, duration));
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
		if (this.books.size() == 0) this.books.add(new Book (title, kind, author, nbPages));
		else {
			for (int i = 0; i < this.books.size(); i++) {
				if (this.books.get(i).getTitle().equalsIgnoreCase(title.trim())) throw new ItemBookAlreadyExistsException();
			}
			this.books.add(new Book (title, kind, author, nbPages));
		}
	}

	@Override
	public float reviewItemFilm(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		if (login == null) throw new BadEntryException("Login null");
        if (login.trim().length() == 0) throw new BadEntryException("Login blank");
		if (password == null) throw new BadEntryException("Password null");
        if (password.trim().length() < 4) throw new BadEntryException("Password blank");
		if (mark < 0 || mark > 5) throw new BadEntryException("No valid value for mark");
		if (comment == null) throw new BadEntryException("Comment null");
		if (comment.trim().length() == 0) throw new BadEntryException("Blank comment");
		
		Member member = null;
		boolean tst_m = false;
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getLogin().equals(login)) {
				tst_m = true;
				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
				else member = this.members.get(i);
			}
		}
		if (!tst_m) throw new NotMemberException("Member does not exists");

		Item item = null;
		boolean tst = false;
		for (Item i : this.films) {
			if (i.getTitle().equalsIgnoreCase(title.trim())) {
				tst = true;
				item = i;
			}
		}
		if (!tst) throw new NotItemException("The item is not in the list");

		boolean rvw = false;
		for (int i = 0; i < item.getReviews().size(); i++) {
			Review r = item.getReviews().get(i);
			if (r.getMember().getLogin().equalsIgnoreCase(login.trim()) && r.getMember().getLogin().equalsIgnoreCase(password.trim()) && r.getItem().getTitle().equalsIgnoreCase(title.trim())) {
				item.getReviews().set(i, new Review(mark, comment, member, item));
				rvw = true;
			}
		}
		if (rvw == false) item.getReviews().add(new Review(mark, comment, member, item));

		return mark;
	}

	@Override
	public float reviewItemBook(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		if (login == null) throw new BadEntryException("Login null");
        if (login.trim().length() == 0) throw new BadEntryException("Login blank");
		if (password == null) throw new BadEntryException("Password null");
        if (password.trim().length() < 4) throw new BadEntryException("Password blank");
		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("Blank title");
		if (mark < 0 || mark > 5) throw new BadEntryException("No valid value for mark");
		if (comment == null) throw new BadEntryException("Comment null");
		if (comment.trim().length() == 0) throw new BadEntryException("Blank comment");
		
		//if (this.members.size() == 0) throw new BadEntryException("Empty members list");
		Member member = null;
		boolean tst_m = false;
		for (int i = 0; i < this.members.size(); i++) {
			if (this.members.get(i).getLogin().equals(login)) {
				tst_m = true;
				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
				else member = this.members.get(i);
			}
		}
		if (!tst_m) throw new NotMemberException("Member does not exists");

		//if (this.books.size() == 0) throw new BadEntryException("List empty. Not in the list");
		Item item = null;
		boolean tst = false;
		for (Item i : this.books) {
			if (i.getTitle().equalsIgnoreCase(title.trim())) {
				tst = true;
				item = i;
			}
		}
		if (!tst) throw new NotItemException("The item is not in the list");

		boolean rvw = false;
		for (int i = 0; i < item.getReviews().size(); i++) {
			Review r = item.getReviews().get(i);
			if (r.getMember().getLogin().equalsIgnoreCase(login.trim()) && r.getMember().getLogin().equalsIgnoreCase(password.trim()) && r.getItem().getTitle().equalsIgnoreCase(title.trim())) {
				item.getReviews().set(i, new Review(mark, comment, member, item));
				rvw = true;
			}
		}
		if (rvw == false) item.getReviews().add(new Review(mark, comment, member, item));

		return mark;
	}

	@Override
	public LinkedList<String> consultItems(String title) throws BadEntryException {
		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("Title blank");
		LinkedList<String> ret = new LinkedList<String>();

		for (int i = 0; i < this.books.size(); i++) {
			Item tmp = this.books.get(i);
			if (tmp.getTitle().equalsIgnoreCase(title.trim())) {
				ret.add(tmp.getTitle() + tmp.averageNote());
			}
		}
        for (int i = 0; i < this.films.size(); i++) {
            Item tmp = this.films.get(i);
            if (tmp.getTitle().equalsIgnoreCase(title.trim())) {
                ret.add(tmp.getTitle() + tmp.averageNote());
            }
        }
		return ret;
	}

}
