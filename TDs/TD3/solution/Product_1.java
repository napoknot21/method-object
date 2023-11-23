/**
 * Implementation de la classe Produit (Exercice 1 - TD3)
 */
public class Product_1 {

    private String name;

    public Product_1 (String name) {
        this.name = name;
    }

    public String getName () {
        return this.name;
    }

    public String toString () {
        return "Product name: " + this.getName();
    }

    public static void main (String[] args) {
    
        Product_1 p1 = new Product_1("Chips");

        System.out.println(p1.getName());
        System.out.println(p1.toString());

    }
}
