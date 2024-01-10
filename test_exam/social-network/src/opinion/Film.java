package opinion;

import java.util.LinkedList;

public class Film extends Item{
	private String director;
	private String scenarist;
	private int duration;
	
	public Film(String titre,String type,String director,String scenarist, int duration ) {
		super(titre,type);
		this.director=director;
		this.scenarist=scenarist;
		this.duration=duration;
	}
	public String toString() {
		return this.titre+" "+String.valueOf(2);
	}
	
	public void addReview(Review a) {
		this.review.add(a);
	}

}
