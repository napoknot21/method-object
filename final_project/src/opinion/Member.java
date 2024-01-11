package opinion;

import exceptions.*;

/**
 * Represents a member in the social network.
 * This class contains personal details of a member and their associated social network.
 */
public class Member {

    /**
     * Username (pseudonym) of the member.
     */
    private String login;

    /**
     * Password of the member.
     */
    private String password;

    /**
     * A brief description of the member.
     */
    private String description;

    /**
     * The associated social network of the member.
     */
    private SocialNetwork socialNetwork;


    /**
     * Constructs a new Member with specified details.
     * 
     * @param login          Username of the member.
     * @param password       Password of the member.
     * @param description    A brief description of the member.
     * @param socialNetwork  The associated social network of the member.
     */
    public Member (String login, String password, String description, SocialNetwork socialNetwork) {

        this.login = login;
        this.password = password;
        this.description = description;
        this.socialNetwork = socialNetwork;

    }


    /**
     * Retrieves the username of the member.
     * 
     * @return The username of the member.
     */
    public String getLogin () {

        return this.login;

    }


    /**
     * Retrieves the password of the member.
     * 
     * @return The password of the member.
     */
    public String getPassword () {

        return this.password;

    }


    /**
     * Retrieves the description of the member.
     * 
     * @return The description of the member.
     */
    public String getDescription () {

        return this.description;

    }


    /**
     * Retrieves the associated social network of the member.
     * 
     * @return The social network associated with the member.
     */
    public SocialNetwork getSocialNetwork () {

        return this.socialNetwork;

    }

    
    /**
     * Updates the username of the member.
     * If the new username is already taken or the same as the current one, it prints an appropriate message.
     * 
     * @param newLogin The new username of the member.
     */
    public void setLogin (String newLogin) {

        if (newLogin.equalsIgnoreCase(this.getLogin())) {

            SocialNetwork sn = this.getSocialNetwork();

            for (Member m : sn.getMembers()) {

                if (m.getLogin().equalsIgnoreCase(newLogin)) {

                    System.out.println("New Login is already taken. Login not changed");

                }

            }

            this.login = newLogin;
            return;

        }
        System.out.println("The new Login is the same. Login not changed");
    }


    /**
     * Updates the password of the member.
     * Changes the password only if the new password is different from the current one.
     * 
     * @param newPassword The new password of the member.
     */
    public void setPassword (String newPassword) {

        if (newPassword.equals(this.getPassword())) this.password = newPassword;

    }


    /**
     * Updates the description of the member.
     * Changes the description only if the new description is different from the current one.
     * 
     * @param newDescription The new description of the member.
     */
    public void setDescription (String newDescription) {

        if (!newDescription.equals(this.getDescription())) this.description = newDescription;

    }


    /**
     * Updates the profile of the member including login, password, and description.
     * Changes are made only if the new values are non-null and not empty.
     * 
     * @param newLogin        The new username of the member.
     * @param newPassword     The new password of the member.
     * @param newDescription  The new description of the member.
     */
    public void updateProfile (String newLogin, String newPassword, String newDescription) {

        if (newLogin != null && newLogin.trim().length() != 0) this.setLogin(newLogin);
        if (newPassword != null && newPassword.trim().length() != 0) this.setPassword(newPassword);
        if (newDescription!= null && newDescription.trim().length()!= 0) this.setDescription(newDescription);

    }


    /**
     * Adds a new film item to the social network.
     *
     * @param title       The title of the film.
     * @param kind        The kind or category of the film.
     * @param director    The director of the film.
     * @param scenarist   The scenarist of the film.
     * @param duration    The duration of the film in minutes.
     * @throws BadEntryException               If the film details are invalid.
     * @throws NotMemberException              If the member is not valid.
     * @throws ItemFilmAlreadyExistsException  If a film with the same title already exists.
     */
    public void addItemFilm (String title, String kind, String director, String scenarist,
			int duration) throws BadEntryException, NotMemberException, ItemFilmAlreadyExistsException {

        this.getSocialNetwork().addItemFilm(this.getLogin(), this.getPassword(), title, kind, director, scenarist, duration);

    }


    /**
     * Adds a new book item to the social network.
     *
     * @param title       The title of the book.
     * @param kind        The kind or category of the book.
     * @param author      The author of the book.
     * @param nbPages     The number of pages in the book.
     * @throws BadEntryException               If the book details are invalid.
     * @throws NotMemberException              If the member is not valid.
     * @throws ItemBookAlreadyExistsException  If a book with the same title already exists.
     */
    public void addItemBook(String login, String password, String title, String kind, String author, int nbPages)
			throws BadEntryException, NotMemberException, ItemBookAlreadyExistsException {

        this.getSocialNetwork().addItemBook(this.getLogin(), this.getPassword(), title, kind, author, nbPages);
        
    }


    /**
     * Adds a new review for a book in the social network.
     *
     * @param title    The title of the book.
     * @param mark     The rating mark for the book.
     * @param comment  The comment for the book review.
     * @throws BadEntryException      If the review details are invalid.
     * @throws NotMemberException     If the member is not valid.
     * @throws NotItemException       If the book does not exist.
     */
    public void addBookReview (String title, float mark, String comment) 
            throws BadEntryException, NotMemberException, NotItemException {

        this.getSocialNetwork().reviewItemFilm(this.getLogin(), this.getPassword(), title, mark, comment);

    }


    /**
     * Adds a new review for a film in the social network.
     *
     * @param title    The title of the film.
     * @param mark     The rating mark for the film.
     * @param comment  The comment for the film review.
     * @throws BadEntryException      If the review details are invalid.
     * @throws NotMemberException     If the member is not valid.
     * @throws NotItemException       If the film does not exist.
     */
    public void addFilmReview (String title, float mark, String comment)
            throws BadEntryException, NotMemberException, NotItemException {

        this.getSocialNetwork().reviewItemFilm(this.getLogin(), this.getPassword(), title, mark, comment);

    }


    /**
     * Checks if the given login matches the login of this member.
     *
     * @param login The login to be checked.
     * @return true if the given login matches this member's login, false otherwise.
     */
    public boolean hasLogin(String login) {

        return this.login.equalsIgnoreCase(login);
    
    }

}
