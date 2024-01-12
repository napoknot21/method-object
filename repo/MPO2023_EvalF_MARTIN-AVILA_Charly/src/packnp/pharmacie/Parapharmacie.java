package packnp.pharmacie;

public class Parapharmacie extends Produit {

    private String categorie;

    public Parapharmacie (String nom, double prix, int quantite, String categorie) throws Exception {

        super(nom, prix, quantite);
        this.categorie = categorie;

    }
    
    
    public String getCategorie () { return this.categorie; }


}


