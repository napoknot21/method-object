package src

import java.util.ArrayList;

public class SocialNetwork implements {


    private final String name;

    private ArrayList<Member> members;

    private ArrayList<Items> items;



    public SocialNetwork (String name) {
        
        this.name = name;
        this.members = new ArrayList<Member>();
        this.items = new ArrayList<Items>();

    }




}