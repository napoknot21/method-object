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
     * Main function
     *
     * @param args : String arrgay of input
     */
    public static void main(String[] args){
      // A completer
    }

}
