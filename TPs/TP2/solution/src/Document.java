/**   
 * Un document dispose d'un titre, est compose d'un ensemble de
 * section et connait la section en cours de manipulation. Il offre
 * des services de deplacements entre section, d'ajout de section et
 * d'affichage
 */
import java.util.LinkedList;

public class Document {

    /** 
     * Le titre du document 
     */
    private String title;
  
    /**
     * Les sections contenues dans le document.
     */ 
    private LinkedList<Section> sections;
  
    /** 
     * L'index de la section courante (-1 s'il est avant le premier element).
     */
    private int position;


    /** 
     * constructeur prenant comme parametre le titre du document
     *
     * @param theTitle titre du document
     */
    public Document (String theTitle){
        
        this.title = theTitle;
        this.sections = new LinkedList<Section>();
        this.position = -1;
    
    }
    
    
    /**
     * Pass to previous section if the $position is positive
     */
    public void moveToPreviousSection () {

        if (this.position > 0) this.position--;

    }


    /**
     * Move the position pointer to the next position
     */
    public void moveToNextPosition () {

        if (this.position < this.sections.size() - 1) this.position++;

    }
    

    /**
     *
     *
     * @return Return the current section
     */
    public Section getCurrentSection () {

        return (this.position >= 0 && this.position < this.sections.size()) ? this.sections.get(this.position) : null;

    }

    
    /**
     * The function adds a section to the LinkedList sections
     *
     * @param section : The section to add
     */
    public boolean addSection (Section section) {

        try {

            this.sections.add(this.position+1, section);
            return true;

        } catch (IndexOutOfBoundsException e) {
            
            return false;

        }
    }


    /**
     * Getter for the title
     *
     * @return the string title
     */
    public String getTitle () {

        return this.title;

    }


    /**
     * Main function
     *
     * @param args : String arrgay of input
     */
    public static void main (String[] args) {
      // A completer
    }

}
