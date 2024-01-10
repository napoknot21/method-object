package opinion;

import java.util.LinkedList;

public class Member {

    private String login;

    private String password;

    private String description;

   // private LinkedList<Review> reviews;


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
'
