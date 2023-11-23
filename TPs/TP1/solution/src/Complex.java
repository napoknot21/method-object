/**
 * Une classe realisant la notion de <i>nombre complexe</i>
 * 
 * @author Fabien Dagnat
 * @version 1.0
 */
public class Complex {
	private final double x;
	private final double y;

	/**
	 * Creation d'un nouvel objet representant le nombre complexe x+iy
	 * 
	 * @param x Partie reelle
	 * @param y Partie imaginaire
	 */
	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calcul et renvoie la somme de deux nombres complexes.
	 * 
	 * @param c1 Le premier nombre.
	 * @param c2 Le second nombre.
	 * @return La somme <code> c1 + c2 </code>
	 * @exception NullPointerException Si l'un des arguments est <code> null </code>.
	 */
	public Complex add(Complex c1, Complex c2) {
		return new Complex(c1.getX() + c2.getX(), c1.getY() + c2.getY());
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}
}
