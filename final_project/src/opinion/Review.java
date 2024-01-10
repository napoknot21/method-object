package opinion;

import java.util.LinkedList;

/**
 * Represents a review in the social network.
 * This class encapsulates details of a review including the rating, comment, member who posted it, and the item being reviewed.
 */
public class Review {

    /**
     * The rating mark of the review.
     */
    private float mark;

    /**
     * The textual comment of the review.
     */
    private String comment;

    /**
     * The member who posted the review.
     */
    private Member member;

    /**
     * The item that the review is associated with.
     */
    private Item item;


    /**
     * Constructs a new Review with specified details.
     * 
     * @param mark     The rating mark of the review.
     * @param comment  The textual comment of the review.
     * @param member   The member who is posting the review.
     * @param item     The item that is being reviewed.
     */
    public Review(float mark, String comment, Member member, Item item) {

        this.mark = mark;
        this.comment = comment;
        this.member = member;
        this.item = item;

    }


    /**
     * Retrieves the rating mark of the review.
     * 
     * @return The rating mark of the review.
     */
    public float getMark() {

        return this.mark;

    }


    /**
     * Retrieves the member who posted the review.
     * 
     * @return The member who posted the review.
     */
    public Member getMember() {

        return this.member;
        
    }


    /**
     * Retrieves the item that is being reviewed.
     * 
     * @return The item that is being reviewed.
     */
    public Item getItem() {

        return this.item;

    }


    /**
     * Retrieves the textual comment of the review.
     * 
     * @return The textual comment of the review.
     */
    public String getComment() {

        return this.comment;

    }

}
