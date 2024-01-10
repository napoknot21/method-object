package src

import java.util.Date;

public class Book extends Item {

    private int isbn;

    private String author;

    private Date releaseDate;


    public Book (String title, int isbn, String author, Date releaseDate) {

        super(title);
        this.isbn = isbn;
        this.author = author;
        this.releaseDate = releaseDate;

    }


    public int getISBN () {

        return this.isbn;

    }


    public String getAuthor () {

        return this.author;

    }

    public Date getReviewsDate () {

        return this.releaseDate;

    }


}