package opinion;

import java.util.LinkedList;

public class Book extends Item {
	private String author;
	private int nbPages;
	
	public Book(String titre,String type,String author , int nbPages ) {
		super(titre,type);
		this.author=author;
		this.nbPages=nbPages;
	}
	
	public void addReview(Review a) {
		this.review.add(a);
	}
	
	
	

}
