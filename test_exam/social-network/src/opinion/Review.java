package opinion;

public class Review {
	private String title;
	private float mark;
	private String comment;
	private Member m;
	
	public Review(String title,float mark,String comment,Member m) {
		this.title=title;
		this.mark=mark;
		this.comment=comment;
		this.m=m;
		
	}
	public String toString() {
		return "Titre : "+this.title + " Note : "+this.mark + " Commentaire : "+this.comment;
	}
	public String getTitle() {
		return this.title;
	}
	
	public String getcomment() {
		return this.comment;
		
	}
	public float getMark() {
		return this.mark;
	}
	
	public Member getMember() {
		return this.m;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	
	public  void setcomment(String comment) {
		this.comment=comment;
		
	}
	public void setMark(float mark) {
		this.mark=mark;
	}

}
