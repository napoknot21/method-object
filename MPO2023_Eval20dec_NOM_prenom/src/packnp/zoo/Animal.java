package packnp.zoo;

public class Animal{
	
	
	private String nom;
	
	private String espece;

	public Animal(String nom, String espece) {
		
		this.nom = nom;
		this.espece = espece;
		
	}
	
	
	public String getNom () {
		
		return this.nom;
		
	}
	
	
	public String getEspece () {
		
		return this.espece;
		
	}
	
	
	public boolean memeEspece (Animal a) {
		
		if (a == null) return false;
		return a.getEspece().equals(this.getEspece());
		
	}
	
	
	
	
	
}
