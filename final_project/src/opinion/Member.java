package opinion;

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
     * Description of the member
     */
    private String description;

    /**
     * The social network attribute of the member
     */
    //private SocialNetwork socialNetwork;
    
    /**
     * Member's attribute reviews array
     */
    //private ArrayList<Review> reviews;


    public Member (String login, String password, String description) {

        this.login = login;
        this.password = password;
        this.description = description;
        //this.reviews = new LinkedList<Review>();
    }


    public String getLogin () {

        return this.login;

    }

    public String getPassword () {

        return this.password;
    }

    public String getDescription () {

        return this.description;
    }




}
