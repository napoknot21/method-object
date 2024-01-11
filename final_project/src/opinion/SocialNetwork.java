package opinion;

import java.util.LinkedList;
import exceptions.*;

/**
 * Represents a social network.
 * This class manages members, books, and films, along with their interactions in the form of reviews.
 */
public class SocialNetwork implements ISocialNetwork {

    private LinkedList<Member> members;
    private LinkedList<Item> books;
    private LinkedList<Item> films;

    /**
     * Constructs a new SocialNetwork instance.
     * Initializes empty lists for members, books, and films.
     */
    public SocialNetwork() {

        this.members = new LinkedList<Member>();
        this.books = new LinkedList<Item>();
        this.films = new LinkedList<Item>();
    
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

		validateMemberCredentials(login, password, description);

		for (int i = 0; i < this.members.size(); i++) {

			if (this.members.get(i).hasLogin(login.trim())) throw new MemberAlreadyExistsException();
		
		}

		this.members.add(new Member (login, password, description, this));
	}

	@Override
	public void addItemFilm(String login, String password, String title, String kind, String director, String scenarist,
			int duration) throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {

		validateMemberCredentials(login, password, "");
		
		for (int i = 0; i < this.members.size(); i++) {

			if (this.members.get(i).hasLogin(login)) {
				
				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
			
			}
		}

		validateItemFilmDetails(title, kind, director, scenarist, duration);

		if (this.films.size() == 0) this.films.add(new Film (title, kind, director, scenarist, duration));
		else {

			for (int i = 0; i < this.films.size(); i++) {
				
				if (this.films.get(i).hasTitle(title.trim())) throw new ItemFilmAlreadyExistsException();

			}

			this.films.add(new Film (title, kind, director, scenarist, duration));
		}
	}

	@Override
	public void addItemBook(String login, String password, String title, String kind, String author, int nbPages)
			throws BadEntryException, NotMemberException, ItemBookAlreadyExistsException {

		validateMemberCredentials(login, password, "");

		for (int i = 0; i < this.members.size(); i++) {

			if (this.members.get(i).getLogin().equals(login)) {

				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
			
			}
		}

		validateItemBookDetails(title, kind, author, nbPages);

		if (this.books.size() == 0) this.books.add(new Book (title, kind, author, nbPages));
		else {

			for (int i = 0; i < this.books.size(); i++) {
				
				if (this.books.get(i).hasTitle(title.trim())) throw new ItemBookAlreadyExistsException();
			
			}

			this.books.add(new Book (title, kind, author, nbPages));
		}
	}


	@Override
	public float reviewItemFilm(String login, String password, String title, float mark, String comment)
			throws BadEntryException, NotMemberException, NotItemException {
		
		validateMemberCredentials(login, password, "");

		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("Blank title");

		validateReviewDetails(mark, comment);

		Member member = null;
		boolean tst_m = false;

		for (int i = 0; i < this.members.size(); i++) {
			
			if (this.members.get(i).hasLogin(login)) {
			
				tst_m = true;

				if (!this.members.get(i).getPassword().equals(password)) throw new NotMemberException("Bad information member");
				else member = this.members.get(i);
			
			}

		}
		if (!tst_m) throw new NotMemberException("Member does not exists");

		Item item = null;
		boolean tst = false;

		for (Item i : this.films) {
			
			if (i.hasTitle(title.trim())) {
				
				tst = true;
				item = i;
			
			}
		
		}
		if (!tst) throw new NotItemException("The item is not in the list");

		boolean rvw = false;
		for (int i = 0; i < item.getReviews().size(); i++) {

			Review r = item.getReviews().get(i);
			
			if (r.getMember().hasLogin(login.trim()) && r.getMember().getLogin().equalsIgnoreCase(password.trim()) && r.getItem().hasTitle(title.trim())) {
			
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
		
		validateMemberCredentials(login, password, "");

		if (title == null) throw new BadEntryException("Title null");
		if (title.trim().length() == 0) throw new BadEntryException("Blank title");

		validateReviewDetails(mark, comment);
		
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

			if (r.getMember().hasLogin(login.trim()) && r.getMember().getPassword().equalsIgnoreCase(password.trim()) && r.getItem().hasTitle(title.trim())) {
				
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

			if (tmp.hasTitle(title.trim())) {

				ret.add(tmp.getTitle() + tmp.averageNote());

			}

		}

        for (int i = 0; i < this.films.size(); i++) {

            Item tmp = this.films.get(i);

            if (tmp.hasTitle(title.trim())) {

                ret.add(tmp.getTitle() + tmp.averageNote());

            }

        }

		return ret;
	}



	private void validateMemberCredentials(String login, String password, String description) throws BadEntryException {

        if (login == null || login.trim().isEmpty()) {

            throw new BadEntryException("Login cannot be null or blank.");
        }

        if (password == null || password.trim().length() < 4) {

            throw new BadEntryException("Invalid password. Password cannot be null or shorter than 4 characters.");
        }

        if (description == null) throw new BadEntryException("Description cannot be null.");

    }


	
	private void validateItemFilmDetails(String title, String kind, String director, String scenarist, int duration) throws BadEntryException {

        if (title == null || title.trim().isEmpty() || kind == null || director == null || scenarist == null) {
        
		    throw new BadEntryException("Title, kind, director, and scenarist cannot be null or blank.");
        
		}

        if (duration <= 0) throw new BadEntryException("Duration must be positive.");
        
    }


	
	private void validateItemBookDetails(String title, String kind, String author, int nbPages) throws BadEntryException {

		if (title == null || title.trim().isEmpty() || kind == null || author == null) {
        
		    throw new BadEntryException("Title, kind, director, and author cannot be null or blank.");
        
		}

        if (nbPages <= 0) throw new BadEntryException("Duration must be positive.");
        
	}


	
	private void validateReviewDetails(float mark, String comment) throws BadEntryException {

		if (mark < 0 || mark > 5) throw new BadEntryException("Mark must be positive and not greater than 5.");
			
		if (comment == null || comment.trim().length() == 0) throw new BadEntryException("Comment must not be null or blank.");

	}

}
