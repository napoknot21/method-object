/**  
 * Un paragraphe dispose du texte associe et offre un service
 * d'affichage 
 */
class Paragraph  {
  private String text;
  public Paragraph(String theText) {
    text=theText;
  }
  public String toString(){
    return text;
  }
}
