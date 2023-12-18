package src

public class Review {

    /**
     * 
     */
    private Member member;

    /**
     * 
     */
    private Item item;

    /**
     * 
     */
    private String comment;

    /**
     * 
     */
    private int mark;


    /**
     * Main constructor for the class Review
     * 
     * @param member
     * @param item
     * @param comment
     * @param mark
     */
    public Review (Member member, Item item, String comment, int mark) {

        this.member = member;
        this.item = item;
        this.comment = comment;
        this.mark = mark;

    }


    /**
     * Alternative constructor for the class Review
     * 
     * @param member
     * @param item
     * @param mark
     */
    public Review (Member member, Item item, int mark) {

        this(member, item, comment, "", mark);

    }


    /**
     * Getter for Member
     * 
     * @return the member attribute
     */
    public Member getMember () {

        return this.member;

    }


    /**
     * Getter for Item
     * 
     * @return the member attribute
     */
    public getItem () {

        return this.item;

    }


    /**
     * Getter for comment
     * 
     * @return the comment attribute
     */
    public String getComment () {

        return this.commentary;

    }


    /**
     * Getter for note
     * 
     * @return the member attribute
     */
    public int getNote () {

        return this.note;

    }

}