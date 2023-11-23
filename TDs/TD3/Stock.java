import java.util.Random;

/**
 * Implementation de la classe Stock (exercice 3 - TD3)
 */
public class Stock {
    
    private int max;
    private int size;
    public Product_2[] content;

    public Stock (int max) {

        if (max > 0) {
            this.max = max;
            this.content = new Product_2[this.max];
        } else {
            this.max = 1000; // Random value
            this.content = new Product_2[this.max];
        }
        this.size = 0;
            
    }

    public Stock () {
        this(1000);
    }


    public void add (Product_2 p) {

        if (this.isFull()) {

            System.out.println("Impossible to add more Products. Full array");
            return;
        
        } else if (this.isEmpty()) {

            this.content[0] = p;
        
        } else {
            
            this.content[this.getSize()] = p;
        
        }
        
        this.size++;
        System.out.println("Product added successfully");
    }


    public Product_2 remove () {

        if (this.isEmpty()) {

            System.out.println("Impossible to remove more Products. Empty array");
            return null;
        }

        Product_2[] list = this.getContent();
        Product_2 p = list[this.getSize()-1];
        
        list[this.getSize()-1] = null;
        this.setSize(-1);
        
        System.out.println("Product removed successfully\n");
        
        return p;
    }


    public boolean isFull () {
        return this.getSize() == this.getMax();
    }


    public boolean isEmpty () {
        return this.getSize() == 0;
    }

    /**  getters and setters */

    public Product_2[] getContent () {
        return this.content;
    }

    public int getMax () {
        return this.max;
    }

    public int getSize () {
        return this.size;
    }

    public void setSize (int i) {
        this.size += i;
    }

    public void setSize () {
        this.setSize(1);
    }

    /**************** */

    /**
     * toString function that convert the object to string
     * @return A text describing the object
     */
    public String toString() {

        String st = "Le Stock a une capacité de " + this.getMax() + " produits totals.\n";
        
        if (this.isEmpty()) {
            
            st += "Il y a aucun prouduit pour l'instant\n";
        
        } else {

            st += "Il y a " + this.getSize() + " produits\n";
            st += "Les produits dans le stock sont les suivants:\n";
            
            for (int i = 0; i < this.getSize(); i++) {

                Product_2 p = this.content[i];
                st += "\t" + p.toString() + "\n";
            
            }
        }

        return st;
    }



    /**
     * Main function
     */
    public static void main (String[] args) {

        Product_2 product1 = new Product_2("Banane");
        Product_2 product2 = new Product_2("Pomme");
        Product_2 product3 = new Product_2("Abricot");
        Product_2 product4 = new Product_2("Fraise");
        Product_2 product5 = new Product_2("Farine");

        Stock stock = new Stock(10);
        stock.add(product1);
        stock.add(product2);
        stock.add(product3);
        stock.add(product4);
        stock.add(product5);
        

        System.out.println(stock.getMax());
        System.out.println(stock.getSize());
        System.out.println(stock.toString());


        System.out.println("================================\n");

        Product_2 re = stock.remove();
        System.out.println(re.toString());

        System.out.println(stock.toString());
    }

}
