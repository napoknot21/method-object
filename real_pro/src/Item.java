package src

import java.util.ArrayList;

public class Item {

    /**
     * Attribute name for the item
     */
    private final String title;

    /**
     * Reviews for the item
     */
    private ArrayList<Review> reviews;

    
    /**
     * Main constructor for the Mother class Item
     * 
     * @param title : The name of the item
     */
    public Item (String title) {

        this.title = title;
        this.reviews = new ArrayList<Review>();

    }


    /**
     * Getter for the title of the item
     * 
     * @return The name of the item
     */
    public String getTitle() {

        return this.title;

    }


    /**
     * Getter for the reviews of the item
     * 
     * @return The ArrayList reviews of the item
     */
    public ArrayList<Review> getReviews() {

        return this.reviews;

    }


}