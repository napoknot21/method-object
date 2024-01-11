package opinion;

/**
 * Represents a book in the social network.
 * This class extends from the abstract class Item and includes specific attributes for a book,
 * such as the author and the number of pages.
 */
public class Book extends Item {

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The number of pages in the book.
     */
    private int nbPages;

    /**
     * Constructs a new Book with specified details.
     * 
     * @param title     The title of the book.
     * @param kind      The kind or genre of the book.
     * @param author    The author of the book.
     * @param nbPages   The number of pages in the book.
     */
    public Book(String title, String kind, String author, int nbPages) {

        super(title, kind);
        this.author = author;
        this.nbPages = nbPages;
    
    }


    /**
     * Retrieves the author of the book.
     * 
     * @return The author of the book.
     */
    public String getAuthor() {
        
        return this.author;
    
    }


    /**
     * Retrieves the number of pages in the book.
     * 
     * @return The number of pages in the book.
     */
    public int getNbPages() {

        return this.nbPages;

    }


    /**
     * Returns a string representation of the book, including its title, kind, author, and number of pages.
     * 
     * @return A string representation of the book.
     */
    public String toString() {

        return super.getTitle() + ", " + super.getKind() + ", " + this.getAuthor() + ", " + this.getNbPages();

    }
    
}
