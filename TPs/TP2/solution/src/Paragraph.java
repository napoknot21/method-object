/**  
 * Un paragraphe dispose du texte associe et offre un service
 * d'affichage 
 */
class Paragraph  {

    /**
     * The text content attribute
     */
    private String text;


    /**
     * Main contructor for the Paragraph
     *
     * @param theText : The text content
     */
    public Paragraph(String theText) {

        this.text = theText;
    
    }


    /**
     * Override function of toString()
     *
     * @return the text attribute of the Paragraph
     */
    public String toString () {

        return this.text;
    
    }


    /**
     * Main function
     *
     * @param args : THe string array arguemnt of input
     */
    public static void main (String[] args) {

        System.out.println("Test");

    }
}
