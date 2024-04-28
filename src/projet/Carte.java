package projet;

public class Carte {
	//les cartes standard 
	public enum Couleur { //une liste énuméré des couleurs possibles dans le jeu 
		JAUNE ,
		ROUGE ,
		VERTE,
		NOIRE,
		BLEUE,
		MULTICOLORE,
		NEUTRE
	}
	protected Couleur mot; //le mot écrire sur la carte qui représente une couleur
	protected Couleur color; // la couleur de la police du mot écrire sur la carte 
	 private static int compteurCarte = 0; // Compteur pour le numéro de carte
	    protected int numeroCarte;

	//une méthode pour vérifier la couleur du mot de la carte 
	//étant une carte simple elle ne peut pas être écrite en multicolore 
	public Couleur verification(Couleur couleur) {
		if (couleur==Couleur.MULTICOLORE || couleur==Couleur.NEUTRE )  {
			throw new IllegalArgumentException("La couleur d'une carte standard ne peut être multicolore.");
		}else return couleur;

	}
	public Carte(Couleur mot, Couleur color) {// le constructeur pour la création d'une carte
		this.numeroCarte=++compteurCarte; //incrémentation
		this.mot=verification (mot);
		this.color=verification(color);
	}
	public String toString() {
		return numeroCarte+ ": "+mot+" de "+color;
	}
}