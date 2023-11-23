/**
 * Classe representant un produit. Un produit possede un nom et un
 * numero qui correspond a son rang de creation. Il est possible de
 * recuperer ce nom, ce numero et de transformer un produit en chaine
 * de caracteres. Il est egalement possible de savoir le nombre de
 * produits deja crees.
 * 
 * @author F.Dagnat
 * @version 2
 */
public class Product {
	/**
	 * Nom du produit.
	 */
	private String name;

	/**
	 * Nombre de produits deja crees. Permet d'attribuer un numero a
	 * chaque produit, ce numero est incremente a chaque instanciation
	 */
	private static int numberCreated = 0;

	/** Le numero du produit */
	private int number;

	/**
	 * Constructeur qui prend en parametre le nom du nouveau produit
	 * 
	 * @param nom du produit a creer
	 */
	public Product(String name) {
		this.name = name;
		numberCreated++;
		this.number = numberCreated;
	}

	/**
	 * Rend une chaine de caracteres qui est le nom du produit
	 * 
	 * @return le nom du produit
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Rend le numero du produit
	 * 
	 * @return numero du produit
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Rend une chaine de caracteres qui decrit le produit
	 */
	@Override
	public String toString() {
		return "Produit " + this.name + " de num " + this.number;
	}

	/**
	 * Rend le nombre de produits crees
	 * 
	 * @return nombre de produits crees
	 */
	public static int getNumberCreated() {
		return numberCreated;
	}

	public static void main(String[] args) {
		Product p1 = new Product("p1");
		System.out.println(p1);
		System.out.println("Le nombre de produits crees est "
				+ Product.getNumberCreated());
		new Product("");
		Product p3 = new Product("p3");
		System.out.println(p3);
		System.out.println("Le nom de p3 est " + p3.getName());
		System.out.println("Le numero de p3 est " + p3.getNumber());
		System.out.println("Le nombre de produits crees est "
				+ Product.getNumberCreated());

		System.out.println("Nous allons creer 12 produits :");
		for (int i = 1; i <= 12; i++)
			new Product("p" + i);
		System.out.println("Le nombre de produits crees est "
				+ Product.getNumberCreated());
	}
}
