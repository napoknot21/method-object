package opinion;

import java.util.LinkedList;

/**
 * Abstract representation of an item in a social network.
 * This class encapsulates the basic details of an item such as its title and kind, along with a list of reviews.
 */
public abstract class Item {

    /**
     * The title of the item.
     */
    private String title;

    /**
     * The kind or category of the item.
     */
    private String kind;

    /**
     * A list of reviews associated with the item.
     */
    private LinkedList<Review> reviews;


    /**
     * Constructs a new Item with a specified title and kind.
     * Initializes an empty list of reviews.
     * 
     * @param title The title of the item.
     * @param kind  The kind or category of the item.
     */
    public Item(String title, String kind) {

        this.title = title;
        this.kind = kind;
        this.reviews = new LinkedList<Review>();
    
    }


    /**
     * Retrieves the title of the item.
     * 
     * @return The title of the item.
     */
    public String getTitle() {

        return this.title;

    }


    /**
     * Retrieves the kind or category of the item.
     * 
     * @return The kind or category of the item.
     */
    public String getKind() {

        return this.kind;

    }


    /**
     * Retrieves the list of reviews associated with the item.
     * 
     * @return A LinkedList of reviews for the item.
     */
    public LinkedList<Review> getReviews() {

        return this.reviews;

    }


    /**
     * Calculates and returns the average rating of the item based on its reviews.
     * Returns Float.NaN if there are no reviews.
     * 
     * @return The average rating as a float, or Float.NaN if no reviews are present.
     */
    public float averageNote() {

        if (this.reviews.size() == 0) return Float.NaN;

        float sum = 0;

        for (Review r : this.reviews) {

            sum += r.getMark();
        }

        return (sum / this.reviews.size());

    }


    /**
     * Checks if the given title matches the title of this item.
     *
     * @param title The title to be checked.
     * @return true if the given title matches this item's title, false otherwise.
     */
    public boolean hasTitle (String title) {

        return this.title.equalsIgnoreCase(title);

    }

}
