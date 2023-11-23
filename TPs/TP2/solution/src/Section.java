/** 
 * Une section dispose d'un numero et d'un titre et est compose d'un
 * ensemble de paragraphes. Elle offre l'acces a son titre, l'ajout d'un
 * paragraphe et son affichage
 */
import java.util.LinkedList;

class Section {
    
    /**
     *
     */
    private String title;
    
    /**
     *
     */
    private LinkedList<Paragraph> paragraphs;



    /** Un constructeur prenant comme parametre le titre de la section
     *  @param title le titre de la section
     */
    public Section (String theTitle) {
        
        this.title=theTitle;
        this.paragraphs = new LinkedList<Paragraph>();
    
    }

}
