/**
 * This class implements the Producer class for the TP1
 * 
 */
public class Producer {

    /**
     * Name attribute of the producer
     */
    public String name;
    
    /**
     * Stock attribute of the producer
     */
    public Stock myStock;

    /**
     * Main Constructor
     * 
     * @param name: The name of the producer
     * @param myStock: The stock (object)
     */
    public Producer(String name, Stock myStock) {
    
        this.name = name;
        /*
        if (myStock != null) {
            this.myStock = myStock;
        } else {
            this.myStock = new Stock ();
        }
        */
       this.myStock = myStock;
    
    }


    /**
     * Alternative Constructor
     * 
     * @param name: The name of the producer
     */
    public Producer (String name) {

        this(name, null);
    
    }


    /**
     * Getter for the name attribute of the producer
     * 
     * @return the name attribute of the producer
     */
    public String getName() {

        return this.name;

    }


    /**
     * Getter for the stock attribute of the producer
     * 
     * @return the stock attribute of the producer
     */
    public Stock getStock() {

        return this.myStock;

    }


    /**
     * Setter for the name attribute of the producer
     * 
     * @param name: The (new) name of the producer
     */
    public void setName (String name) {
        
        if (name == null || name.equals("")) {

            System.out.println("Impossible to set the name attribute of the producer. The name is empty");
            return;
        
        }

        this.name = name;
    }


    /**
     * Method that create a new product from the name parameter and stocks in the this.stock attribute
     * 
     * @param productName: The name of the product to stock
     */
    public void produce (String productName) {
        
        if (productName == null || productName.equals("")) {

            System.out.println("The parameter name null or ");
            return;

        }

        this.getStock().add(new Product(productName));
    } 


    /**
     * This function overrides the toString() method
     * 
     * @return A string representation of the producer object
     */
    public String toString () {
        
        return "The producer name: " + this.getName() + ". \nAnd its stock has: \n\t" + this.getStock().toString();

    }


    /**
     * Main function
     *
     * @param args : The string arrays of entry terminal commands
     */
    public static void main (String[] args) {
        
        System.out.println("Test for the Producer class");

        Product p1 = new Product ("Banane");
        Product p2 = new Product ("Pomme");
        Product p3 = new Product ("Poir");
        Product p4 = new Product ("Beurre");

        Stock st = new Stock (10);

        st.add(p1);
        st.add(p2);
        st.add(p3);

        Producer p = new Producer ("Jean", st);

        System.out.println("The name of the producer is: " + p.getName());
        System.out.println("Let's change it by Paul");

        p.setName ("Paul");
        p.setName ("");

        System.out.println(p.toString());

        System.out.println("\n================================\n");
        
        
        p.produce("Confiture");
    
        System.out.println(p.toString());

        p.getStock().remove();

        System.out.println("\n================================\n");

        System.out.println(p.toString());

    }

}