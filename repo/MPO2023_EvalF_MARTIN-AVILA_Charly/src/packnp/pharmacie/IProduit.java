package packnp.pharmacie;

public interface IProduit {
	
	/**
	 * @return Retourne le nom du produit
	 */
	public String getNom();

	/**
	 * @return Retourne le prix du produit
	 */
	public double getPrix();

	/**
	 * @return Retourne la quantite du produit
	 */
	public int getQuantite() ;

	/**
	 * Affecte prix pour prix du produit si le
	 * parametre prix est positif ou nul (si prix>=0.0).
	 * Sinon (donc si le parametre est negatif) ne fait rien
	 * et en particulier ne modifie pas le produit
	 * @param prix
	 */
	public void setPrix(double prix) ;

	/**
	 * Affecte quantite pour quantite du produit si le
	 * parametre est positif ou nul (si quantite>=0).
	 * Sinon (donc si le parametre est negatif) ne fait rien
	 * et en particulier ne modifie pas le produit
	 * @param quantite
	 */
	public void setQuantite(int quantite) ;
	
	/**
	 * @param obj
	 * @return  Retourne true si obj n'est pas null et obj est de type IProduit et le nom de obj
	 * est egal (au sens du equals de String) au nom de this.
	 * Sinon, retourne false (--> retourne false si obj vaut null ou si obj n'est pas de 
	 * type IProduit ou si le nom et obj n'est pas egal (au sens du equals de String) 
	 * au nom de this).
	 */
	public boolean equals(Object obj) ;

}
