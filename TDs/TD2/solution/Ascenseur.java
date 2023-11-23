import java.util.List;
import java.util.ArrayList;


public class Ascenseur {
  
    private int etageActuel;
    private List<Integer> etagesParcourus;


    /**
     * Constructor for Ascenseur class
     * @param etageActuel: Initial floor level
     */
    public Ascenseur (int etageActuel) {
    
        this.etageActuel = etageActuel;
        this.etagesParcourus = new ArrayList<>();

    }

    /**
     * Constructor without parameter
     */
    public Ascenseur () {
        this(0);
    }

    
    /**
     * Getter function for the current floor 
     * @return the current floor level
     */
    public int getEtageActuel () {
        return this.etageActuel;
    }


    /**
     * Getter function for list of floor levels
     * @return the list of floor level
     */
    public List<Integer> getEtagesParcourus () {
        return this.etagesParcourus;
    }


    /**
     * Function that close the doors and print a message
     */
    public void fermerPortes () { 

        System.out.println("Portes Fermées"); 

    }


    /**
     * Move the elevator to the level given as parameter and update information 
     * @param etageDestination: The floor level to Move (integer)
     * @return true if the current floor is not the same as the destion, false else
     */
    public boolean seDeplacer (int etageDestination) {
       
        this.fermerPortes();

        if (this.etageActuel == etageDestination) return false;

        this.etagesParcourus.add(etageDestination);
        this.etageActuel = etageDestination;
        
        return true;
    }
    

    /**
     * Main function for tests
     * @param args: The entry array for execution
     * @return void
     */
    public static void main (String[] args) {
        
        Ascenseur ascenseur = new Ascenseur ();
        
        if (ascenseur.seDeplacer(3)) {
            
            System.out.println("L'ascenseur s'est déplacé à l'étage " + ascenseur.getEtageActuel());
        
        } else {

            System.out.println("L'Ascenseur ne s'est pas déplacé");
        }

        //System.out.println("hellow world !");
    }


}
