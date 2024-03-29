package src

import java.util.ArrayList;
import java.util.LinkedList;

public class Member {

    /**
     * Attribute username (pseudo) of the member
     */
    private String login;

    /**
     * Attribute password of the member
     */
    private String password;

    /**
     * The social network attribute of the member
     */
    private SocialNetwork socialNetwork;
    
    /**
     * Member's attribute reviews array
     */
    private ArrayList<Review> reviews;


    /**
     * Main constructor for Member
     * 
     * @param login
     * @param password
     * @param socialNetwork
     */
    public Member (String login, String password, SocialNetwork socialNetwork) {

        this.login = login;
        this.password = password;
        this.socialNetwork = socialNetwork;
        this.reviews = new ArrayList<Review>();

    }


    /**
     * Getter for the login (usermane)
     * 
     * @return the String login (username) of the member
     */
    public String getLogin() {

        return this.login;

    }


    /**
     * Getter for the password
     * 
     * @return the password of the member
     */
    public String getPassword() {

        return this.password;

    }


    /**
     * Getter for the social network
     * 
     * @return the social network
     */
    public SocialNetwork getSocialNetwork() {

        return this.socialNetwork;

    }


    /**
     * This function checks the format of parameters and create the 
     * 
     * @return null if the parameter are not in ok format or a new object film is created
     */
    public Film createItemFilm (String title, String director, int duration) {

        return ((title.equals("") || director.equals("") || duration <= 0) ? null : new Film (title, director, duration));

    }


    /**
     * This function checks if a given item exists already in the arrayList of items
     * 
     * @param items
     * @param title
     * @param director
     * 
     * @return 
     */
    public boolean isItemFilmExists (ArrayList<Item> items, String title, String director) {

        for (int i = 0; i < item.size(); i++) {
            
            Item tmp_item = items.get(i);

            if (tmp_item.getTitle().equals(title) && tmp_item.getDirector().equals(director)) {

                return true;

            }

        }

        return false;
    }


    /**
     * 
     * 
     * @param items
     * @param film
     * 
     * @return true or false
     */
    public boolean itemFilmExists (ArrayList<Item> items, Film film) {

        for (int i = 0; i < item.size(); i++) {
            
            Item tmp_item = items.get(i);

            if (tmp_item.getTitle().equals(film.getTitle()) && tmp_item.getDirector().equals(film.getDirector())) {

                return true;

            }

        }

        return false;
    }


    /**
     * 
     * 
     * @param title
     * @param director
     * @param duration
     * 
     * @return true or false
     */
    public boolean addItemFilm (String title, String director, int duration) {

        ArrayList<Item> items = this.getSocialNetwork().getItems();

        if (this.itemFilmExists(items, title, director)) {

            return false;

        }

        Film newItem = createItemFilm(title, director, duration);

        return ((newItem == null) ? true : false);

    }


    /**
     * 
     * 
     * @param film
     * 
     * @return true or false
     */
    public boolean addItemFilm (Film film) {

        ArrayList<Item> items = this.getSocialNetwork().getItems();

        if (this.itemFilmExists(items, film)) {

            return false;

        }

        

    }
}

