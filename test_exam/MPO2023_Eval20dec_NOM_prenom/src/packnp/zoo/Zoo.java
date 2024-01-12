package packnp.zoo;

import java.util.LinkedList;

public class Zoo {

	private String nom;
	
	private LinkedList<Cage> lesCages;
	
	private LinkedList<Animal> lesAnimaux;
	
	
	public Zoo (String nom, LinkedList<Cage> lesCages, LinkedList<Animal> lesAnimaux) {
		
		this.nom = nom;
		this.lesCages = lesCages;
		this.lesAnimaux = lesAnimaux;
		
	}
	
	
	public void placerAnimal (Animal a, Cage c) {
		
		c.ajouterAnimal(a);
		
	}
	
	
	public void deplacerAnimal (Animal a, Cage cageDepart, Cage cageArrivee) {
		
		if (a ==  null) return;
		if (cageArrivee.peutContenir(a)) {
			cageDepart.retirerAnimal(a);
			cageArrivee.ajouterAnimal(a);
		}
		
		
	}
	
	
	public String toString () {
		String str = this.nom;
		for (int i = 0; i < this.lesCages.size(); i++) {
			
			str += this.lesCages.get(i).toString();
			
		}
		return str;
	}
	
	
	public static Zoo creerVincennes () {
		
		Animal a1 = new Animal ("simba", "lion");
		Animal a2 = new Animal ("mickey", "souris");
		
		Cage c1 = new Cage();
		Cage c2 = new Cage();
		
		c1.ajouterAnimal(a1);
		c2.ajouterAnimal(a2);
		
		LinkedList<Cage> lesCages = new LinkedList<Cage>();
		LinkedList<Animal> lesAnimaux = new LinkedList<Animal>();
		
		lesCages.add(c1);
		lesCages.add(c2);
		
		lesAnimaux.add(a1);
		lesAnimaux.add(a2);
		
		return  new Zoo ("Vincennes", lesCages, lesAnimaux);
	}
	
}
