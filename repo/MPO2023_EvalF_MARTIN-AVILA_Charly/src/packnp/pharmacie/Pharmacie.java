package packnp.pharmacie;

import java.util.LinkedList;

public class Pharmacie implements IPharmacie {

    private LinkedList<Medicament> medicaments;

    public Pharmacie () {

        this.medicaments = new LinkedList<Medicament>();

    }


    public LinkedList<Medicament> getMedicaments () {

        return this.medicaments;

    }


    public Medicament getMedicament (String nom) {

        if (this.getMedicaments().size() == 0) return null;

        for (Medicament m : this.getMedicaments()) {

            if (m.getNom().equals(nom)) return m;

        }

        return null;

    }


    public int getQuantiteMedicament (String nom) {

        Medicament m = this.getMedicament(nom);
        
        return (m == null) ? 0 : m.getQuantite();
    
    }


    public void ajouter (Medicament m) {

        if (this.getMedicaments().size() == 0) {
            
            this.medicaments.add(m);
            return;

        }

        for (Medicament med : this.getMedicaments()) {

            if (m.getNom().equals(med.getNom())) {

                med.setQuantite(med.getQuantite()+ m.getQuantite());
                return;

            }

        }
        
        this.medicaments.add(m);

    }


    public boolean realisable (LinkedList<String> noms) {

        for (String n : noms) {

            if (this.getQuantiteMedicament(n) <= 0) return false;

        }

        return true;

    }


    public LinkedList<Medicament> getSubstituables (Medicament m) {

        LinkedList<Medicament> list = new LinkedList<Medicament>();

        for (Medicament md : this.getMedicaments()) {


        	if (m.isSubstituable(md)) list.add(md);


        }

        return list;

    }



}
