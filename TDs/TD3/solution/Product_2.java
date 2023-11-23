/**
 * Implementation (améliorée) de la classe Product (Exercice 2 - TD3)
 */
public class Product_2 {

    private String name;
    private int number;
    public static int numberCreated = 0;

    public Product_2 (String name) {
        this.name = name;
        this.number = ++numberCreated;
    }

    public String getName () {
        return this.name;
    }

    public int getNumber () {
        return this.number;
    }

    public static int getNumberCreated() {
        return numberCreated;
    }

    public String toString () {
        if (this == null) return "Null Product\n";
        return "Product name: " + this.getName() + "\n" + "\tProduct number: " + this.getNumber() + "\n";
    }

    public static void main (String[] args) {
    
        Product_2 p1 = new Product_2("Chips");
        Product_2 p2 = new Product_2("Fish");

        System.out.println(p1.getName());
        System.out.println(p1.toString());
        System.out.println(p1.getNumber());

        System.out.println(p2.getNumber());
        System.out.println(getNumberCreated()); 
    }
}
