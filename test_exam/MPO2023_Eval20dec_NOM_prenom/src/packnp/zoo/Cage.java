package packnp.zoo;

import java.util.LinkedList;

public class Cage{

	private LinkedList<Animal> lesAnimaux;
	
	public Cage () {
		
		this.lesAnimaux = new LinkedList<Animal>();
		
	}
	
	
	public LinkedList<Animal> getLesAnimaux () {
		
		return this.lesAnimaux;
	}
	
	
	public boolean peutContenir (Animal a) {
		
		if (a == null) return false;
		
		if (this.lesAnimaux.isEmpty()) return true;
		
		return this.lesAnimaux.get(0).memeEspece(a);
		
	}
	
	
	public void ajouterAnimal (Animal a) {
		
		if (this.peutContenir(a)) {
			
			for (int i = 0; i < this.lesAnimaux.size(); i++) {
				
				Animal tmp = this.lesAnimaux.get(i);
				
				if (tmp.getNom().equals(a.getNom())) return;
				
			}
			
			this.lesAnimaux.add(a);
			
		}
		
	}
	
	
	public void retirerAnimal (Animal a) {
		
		if (a == null) return;
		
		this.lesAnimaux.remove(a);
		
	}
	
	
	
	public String toString () {
		
		String str = "cage :";
		
		if (this.lesAnimaux.isEmpty()) return str + "0";
		
		return str + this.lesAnimaux.size();
		
	}
	
}
