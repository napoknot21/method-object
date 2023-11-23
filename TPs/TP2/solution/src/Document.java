import java.util.LinkedList;
/**   
 * Un document dispose d'un titre, est compose d'un ensemble de
 * section et connait la section en cours de manipulation. Il offre
 * des services de deplacements entre section, d'ajout de section et
 * d'affichage
 */
public class Document {
  /** Le titre du document */
  private String title;
  /** Les sections contenues dans le document. */ 
  private LinkedList<Section> sections;
  /** L'index de la section courante (-1 s'il est avant le premier
   * element). */
  private int position;

  /** 
   * constructeur prenant comme parametre le titre du document 
   * @param theTitle titre du document
   */
  public Document(String theTitle){
    title = theTitle;
    sections = new LinkedList<Section>();
    position = -1;
  }
    // A completer

  public static void main(String[] arg){
      // A completer
  }
}
