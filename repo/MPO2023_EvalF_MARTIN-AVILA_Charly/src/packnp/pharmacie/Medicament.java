package packnp.pharmacie;


public class Medicament extends Produit {
	

    private String substanceActive;

    public Medicament (String nom, double prix, int quantite, String substanceActive) throws Exception {

        super (nom, prix, quantite);
        this.substanceActive = substanceActive;

    }


    public String getSubstanceActive() {

        return this.substanceActive;

    }


    public boolean isSubstituable (Medicament m) {

        return this.getSubstanceActive().equals(m.getSubstanceActive());

    }

}
