/**
 * Un Stock d'instances de Product dont la taille est fixee a la
 * creation.
 * 
 * @author F.Dagnat, J. Mallet
 * @version 2
 */
public class Stock {
	/** taille par defaut du tableau de produits **/
	private final int DEFAULT_SIZE = 1;

	/**
	 * Tableau contenant les produits de ce Stock
	 */
	private Product[] content;

	/** Nombre de produits deposes */
	private int size = 0;

	/**
	 * Constructeur avec comme parametre la taille du Stock
	 * 
	 * @param s la taille du Stock
	 */
	public Stock(int s) {
		if (s > 0) {
			this.content = new Product[s];
			return;
		}
		this.content = new Product[DEFAULT_SIZE];
	}

	/**
	 * Rajoute un nouveau produit dans le Stock
	 * 
	 * @param p le produit qui est rajoute
	 */
	public void add(Product p) {
		if (p == null)
			return;
		this.content[this.size] = p;
		this.size++;
	}

	/**
	 * Permet de connaitre le nombre de produits dans le Stock
	 */
	public int getSize() {
		return this.size;
	}

	/** Permet de savoir si le Stock est vide */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/** Permet de savoir si le Stock plein */
	public boolean isFull() {
		return this.size == this.content.length;
	}

	/**
	 * Retire le <b>dernier</b> produit ajoute au Stock et le rend en
	 * resultat
	 */
	public Product remove() {
		this.size--;
		Product p = this.content[this.size];
		this.content[this.size] = null;
		return p;
	}

	/**
	 * Rend une chaine de caracteres decrivant le Stock
	 */
	@Override
	public String toString() {
		if (isEmpty())
			return "Le Stock est vide.";
		String s = "Le Stock contient : ";
		for (int i = 0; i < this.size; i++)
			s += "\n\t" + this.content[i];
		return s;
	}

	public static void main(String[] args) {
		Stock s = new Stock(8);
		System.out.println("Stock Vide : " + s.isEmpty() + ", Stock Plein : "
				+ s.isFull());
		System.out.println(s);
		s.add(new Product("p1"));
		System.out.println("Stock Vide : " + s.isEmpty());
		System.out.println(s);
		s.add(new Product("p2"));
		System.out.println(s);
		System.out.println(s.remove() + " est retire du Stock !");
		s.add(new Product("p3"));
		System.out.println(s);
		System.out.println("Nous allons ajouter 13 produits dans le Stock.");
		int num = 0;
		for (int i = 1; i <= 13; i++) {
			if (!s.isFull()) {
				s.add(new Product("ppp" + i));
				num++;
			}
		}
		System.out.println(s);
		System.out.println("En fait, nous avons ajoute " + num + " produits.");
	}
}
