package packnp.pharmacie;

import java.util.LinkedList;

public interface IPharmacie {
	
	/**
	 * @return Accesseur retournant la liste des medicaments de la pharmacie
	 */
	public LinkedList<Medicament> getMedicaments();
	
	/**
	 * @param nom, nom!=null (vous n'avez pas a traiter le cas ou nom==null)
	 * @return Retourne le medicament comercialise par la pharmacie portant 
	 *    le nom precise en parametre (Retourne null si la pharmacie ne 
	 *    commercialise aucun medicament portant le nom precise en parametre). 
	 */
	public Medicament getMedicament(String nom) ;
	
	/**
	 * @param nom, nom!=null (vous n'avez pas a traiter le cas ou nom==null)
	 * @return Retourne la quantite de medicament portant le nom indique
	 *    en parametre qu'il y a en stock (0 si la pharmacie ne commercialise
	 *    aucun medicament portant le nom precise en parametre).
	 */
	public int getQuantiteMedicament(String nom) ;
	
	/**
	 * 
	 * @param m, m!=null (vous n'avez pas a traiter le cas ou m==null)
	 * ajoute m au stock :
	 * - Si il n'existe pas de medicament portant le nom m.getNom() dans this.medicaments, ajoute m dans this.medicaments. 
	 * - Sinon, ajoute la quantite m.getQuantite() a la quantite du medicament de this.medicaments portant le nom m.getNom()
	 * 
	 * Exemple :
	 * - si this.medicaments={ ("advil",2.3,12,"ibuprofene"), ("doliprane",2.54,10,"paracetamol")}
	 *   alors apres ajouter(("advil",2.3,14,"ibuprofene")) la liste medicaments devient this.medicaments={ ("advil",2.3,26,false,false,"ibuprofene"), ("doliprane",2.54,10,"paracetamol")}
	 * - si this.medicaments={ ("advil",2.3,12,"ibuprofene"), ("doliprane",2.54,10,"paracetamol")}
	 *   alors apres ajouter(("doliprane",2.54,4,"paracetamol")) la liste medicaments devient this.medicaments={ ("advil",2.3,12,"ibuprofene"), ("doliprane",2.54,14,"paracetamol")}
	 * - si this.medicaments={ ("advil",2.3,12,"ibuprofene"), ("doliprane",2.54,10,"paracetamol")}
	 *   alors apres ajouter(("Topalgic",6.15,16,"tramadol chlorhydrate")) la liste medicaments devient this.medicaments={ ("advil",2.3,12,"ibuprofene"), ("doliprane",2.54,10,"paracetamol"), ("Topalgic",6.15,16,"tramadol chlorhydrate")}
	 */
	public void ajouter(Medicament m) ;
	
	/**
	 * Methode appelee pour verifier si la pharmacie n'est pas en rupture de stock sur
	 * l'un des medicaments dont les noms sont fournis en parametre (en d'autres termes, 
	 * cette methode permet de verifier si la commande d'un client demandant un exemplaire
	 * de chacun de ces medicaments peut etre realisee).
	 * 
	 * @param nomsMedicaments, nomsMedicaments!=null (vous n'avez pas a traiter le cas ou nomsMedicaments==null)
	 * @return Retourne true si il existe au moins un exemplaire en stock de chaque medicament dont le nom apparait dans nomsMedicaments.
	 *  Sinon (donc si il y a un nom N dans nomsMedicaments tel qu'il n'y a aucun medicament de nom N en stock dans la pharmacie) retourne false 
	 */
	public boolean realisable(LinkedList<String> nomsMedicaments);
	
	/**
	 * @param m, m!=null (vous n'avez pas a traiter le cas ou m==null)
	 * @return Retourne la listes des medicaments de la pharmacie qui peuvent se substituer a m 
	 * c'est a dire la liste des medicaments qui ont la meme substance active que m
	 * (la liste retournee est vide si il n'existe aucun medicament substituable pour m)
	 */
	public LinkedList<Medicament> getSubstituables(Medicament m) ;

}
