package opinion;

import java.util.LinkedList;

public class Item {
	protected String titre;
	protected String type;
	protected LinkedList<Review> review;

	
	public Item(String titre,String type) {
		this.titre=titre;
		this.type=type;
		this.review= new LinkedList<Review>();
	}
	
	public LinkedList<Review> getReview(){
		return this.review;
	}
	
	public String getTitle() {
		return this.titre;
	}
	
	
	public float getNote() {
		if (this.review.size()==0) {return Float.NaN;}
		
		float s=0;
		
		for (Review r :this.review) {
			s=(float) (s+r.getMark());
		}
		
		s=s/(this.review.size());
		return s;
	}
	
	

}
