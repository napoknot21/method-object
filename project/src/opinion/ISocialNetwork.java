package opinion;

import java.util.LinkedList;

import exceptions.BadEntryException;
import exceptions.ItemFilmAlreadyExistsException;
import exceptions.ItemBookAlreadyExistsException;
import exceptions.MemberAlreadyExistsException;
import exceptions.NotItemException;
import exceptions.NotMemberException;

/** 
 * @author - A. Beugnard
 * @author - E. Cousin
 * @author - G. Ouvradou
 * @author - B. Prou
 * @author - J. Mallet
 * @date 2011-2023
 * @version V2023.1
 */

/**
 * System for collecting opinions on items from various areas (literature,
 * cinema, art, gastronomy, etc.). </p>
 * <p>
 * Access to items and opinions related to them is public. Creating a new item
 * or giving an opinion, on the other hand, is limited to users with a registered
 * profile in the system.
 * </p>
 * <p>
 * When a method can throw two types of exception, and the conditions are met to
 * raise both, there is no way to predict which of them will actually be
 * thrown.
 * </p>
 * 
 */

public interface ISocialNetwork {

    /**
     * Get the number of members registered in the <i>SocialNetwork</i>
     * 
     * @return number of members
     */
    public int countNbMembers();

    /**
     * Get the number of films registered in the <i>SocialNetwork</i>
     * 
     * @return number of films
     */
    public int countNbFilms();

    /**
     * Get the number of books registered in the <i>SocialNetwork</i>
     * 
     * @return number of books
     */
    public int countNbBooks();

    /**
     * Add a new member to the <i>SocialNetwork</i>
     * 
     * @param login
     *            the new member's login
     * @param password
     *            the new member's password
     * @param description
     *            a free <code>String</code> describing the new member's description
     * 
     * @throws BadEntryException
     *             <ul>
     *             <li>if <code>login</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>password</code> is not instantiated or contains less than
     *             four characters (not taking into account leading or trailing
     *             blanks)</li>
     *             <li>if <code>description</code> is not instantiated</li>
     *             </ul>
     * <br>
     * 
     * @throws MemberAlreadyExistsException
     *             if a member with the same login is already registered in the
     *             <i>SocialNetwork</i> (same login : not case-sensitive and
     *             leadings/trailings blanks are not taken into account)
     * 
     */
    public void addMember(String login, String password, String description)
	throws BadEntryException, MemberAlreadyExistsException;

    /**
     * Add a new film to the <i>SocialNetwork</i>
     * 
     * @param login
     *            login of the member adding the film
     * @param password
     *            password of the member adding the film
     * @param title
     *            the new film's title
     * @param kind
     *            the new film's kind (adventure, thriller, etc.)
     * @param director
     *            the new film's director
     * @param scenarist
     *            the new film's scenarist
     * @param duration
     *            the new film's duration (in minutes)
     * 
     * @throws BadEntryException
     *             <ul>
     *             <li>if <code>login</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>password</code> is not instantiated or contains less than
     *             four characters (not taking into account leading or trailing
     *             blanks)</li>
     *             <li>if <code>title</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>kind</code> is not instanciated</li>
     *             <li>if <code>director</code> is not instanciated</li>
     *             <li>if <code>scenarist</code> is not instanciated</li>
     *             <li>if <code>duration</code> is not stricly positive
     *             </ul>
     * 
     * @throws NotMemberException
     *             if <code>login</code> does not match with the login of a registered member
     *             in <i>SocialNetwork</i> or if <code>password</code> does not correspond to
     *             his registered password.
     * @throws ItemFilmAlreadyExistsException
     *             : a film with the same title is already registered in the
     *             <i>SocialNetwork</i> (same title : not case-sensitive and
     *             leadings/trailings blanks are not taken into account)
     * 
     */
    public void addItemFilm(String login, String password, String title,
			    String kind, String director, String scenarist, int duration)
	throws BadEntryException, NotMemberException,
	       ItemFilmAlreadyExistsException;

    /**
     * Add a new book to the <i>SocialNetwork</i>
     * 
     * @param login
     *            login of the member adding the book
     * @param password
     *            password of the member adding the book
     * @param title
     *            the new book's title
     * @param kind
     *            the new book's kind (adventure, thriller, etc.)
     * @param author
     *            the new book's author
     * @param nbPages
     *            number of pages of the new book's
     * 
     * @throws BadEntryException
     *             <ul>
     *             <li>if <code>login</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>password</code> is not instantiated or contains less than
     *             four characters (not taking into account leading or trailing
     *             blanks)</li>
     *             <li>if <code>title</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>kind</code> is not instanciated</li>
     *             <li>if <code>author</code> is not instanciated</li>
     *             <li>if <code>nbPages</code> is not stricly positive
     *             </ul>
     * 
     * @throws NotMemberException
     *             if <code>login</code> does not match with the login of a registered member
     *             in <i>SocialNetwork</i> or if <code>password</code> does not correspond to
     *             his registered password.
     * @throws ItemBookAlreadyExistsException
     *             a book with the same title is already registered in the
     *             <i>SocialNetwork</i> (same title : not case-sensitive and
     *             leadings/trailings blanks are not taken into account)
     * 
     */
    public void addItemBook(String login, String password, String title,
			    String kind, String author, int nbPages) throws BadEntryException,
									    NotMemberException, ItemBookAlreadyExistsException;

    /**
     * Add in the <i>SocialNetwork</i> a new review for a film on behalf of a specific member.
     * <p> 
     * If this member has already given a review for this
     * same film before, the new review replaces the previous one.
     * 
     * @param login
     *            login of the member adding the review
     * @param password
     *            password of the member adding the review
     * @param title
     *            the reviewed film's title
     * @param mark
     *            the mark given by the member for this film
     * @param comment
     *            the comment given by the member for this film
     * 
     * @throws BadEntryException
     *             <ul>
     *             <li>if <code>login</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>password</code> is not instantiated or contains less than
     *             four characters (not taking into account leading or trailing
     *             blanks)</li>
     *             <li>if <code>title</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>mark</code> is not greater or equals to 0.0 and lesser or
     *             equals to 5.0.</li>
     *             <li>if <code>comment</code> is not instantiated</li>
     *             </ul>
     * <br>
     * @throws NotMemberException
     *             if <code>login</code> does not match with the login of a registered member
     *             in <i>SocialNetwork</i> or if <code>password</code> does not correspond to
     *             his registered password.
     * @throws NotItemException
     *             if <code>title</code> is not registered as a film's title in the
     *             <i>SocialNetwork</i>
     * 
     * @return mean of the marks for this film
     */
    public float reviewItemFilm(String login, String password, String title,
				float mark, String comment) throws BadEntryException,
								   NotMemberException, NotItemException;

    /**
     * Add in the <i>SocialNetwork</i> a new review for a book on behalf of a
     * specific member.
     * <p> 
     * If this member has already given a review for this
     * same book before, the new review replaces the previous one.
     * 
     * @param login
     *            login of the member adding the review
     * @param password
     *            password of the member adding the review
     * @param title
     *            the reviewed book's title
     * @param mark
     *            the mark given by the member for this book
     * @param comment
     *            the comment given by the member for this book
     * 
     * @throws BadEntryException
     *             <ul>
     *             <li>if <code>login</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>password</code> is not instantiated or contains less than
     *             four characters (not taking into account leading or trailing
     *             blanks)</li>
     *             <li>if <code>title</code> is not instantiated or contains less than one
     *             non-space character</li>
     *             <li>if <code>mark</code> is not greater or equals to 0.0 and lesser or
     *             equals to 5.0.</li>
     *             <li>if <code>comment</code> is not instantiated</li>
     *             </ul>
     * <br>
     * @throws NotMemberException
     *             if <code>login</code> does not match with the login of a registered member
     *             in <i>SocialNetwork</i> or if <code>password</code> does not correspond to
     *             his registered password.
     * @throws NotItemException
     *             if <code>title</code> is not registered as a book's title in the
     *             <i>SocialNetwork</i>
     * 
     * @return mean of the marks for this book
     */
    public float reviewItemBook(String login, String password, String title,
				float mark, String comment) throws BadEntryException,
								   NotMemberException, NotItemException;

    /**
     * Search for items in the <i>SocialNetwork</i>.
     * 
     * @param title
     *            title of searched item(s)
     * 
     * @throws BadEntryException
     *             if <code>title</code> is not instantiated or contains less than one
     *             non-space character
     * 
     * @return  a list of <code>String</code> representing all items (book
     *         and/or film) matching the searched name.</br> Each <code>String</code> will
     *         contain at least the score of the corresponding item.
     */
    public LinkedList<String> consultItems(String title)
	throws BadEntryException;

    /**
     * A <i>SocialNetwork</i> is described at least by member's names, book's
     * titles/scores and film's titles/scores.
     * 
     * @return a <code>String</code> representing the <i>SocialNetwork</i>, with at least
     *         member's names, book's titles/scores and film's titles/scores.
     */
    public String toString();

}
