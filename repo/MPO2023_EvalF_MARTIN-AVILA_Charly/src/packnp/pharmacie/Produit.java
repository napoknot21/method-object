package packnp.pharmacie;

public class Produit implements IProduit {


    private String nom;

    private double prix;

    private int quantite;

    public Produit (String nom, double prix, int quantite) throws Error {

        if (nom == null || prix < 0.0 || quantite < 0) throw new Error();
        this.nom = nom;
        this.prix = prix;
        this.quantite = quantite;

    }


    public String getNom () {return this.nom;}

    public double getPrix () {return this.prix;}

    public int getQuantite () {return this.quantite;}


    public void setPrix (double prix) { 
    	if (prix >= 0.0) this.prix = prix;
    }
    
    public void setQuantite (int newQuantite) {
    	if (newQuantite >= 0) this.quantite = newQuantite; 
    
    }
    
    
    public boolean equals (Object obj) {
    	
    	if (obj == null) return false;
    	
    	if (obj instanceof Produit) {
    		
    		if (this.nom.equals(((Produit) obj).getNom())) return true;
    		
    	}
    	
    	return false;
    	
    }




}
