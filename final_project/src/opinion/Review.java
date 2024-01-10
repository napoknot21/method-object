package opinion;

import java.util.LinkedList;

public class Review {

    private float mark;

    private String comment;

    private Member member;

    private Item item;

    public Review(float mark, String comment, Member member, Item item) {

        this.mark = mark;
        this.comment = comment;
        this.member = member;
        this.item = item;

    }

    public float getMark() {

        return this.mark;

    }


    public Member getMember() {

        return this.member;

    }

    public Item getItem() {

        return this.item;

    }


    public String getComment() {

        return this.comment;

    }

}