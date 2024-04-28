package projet;


public class Joker extends Carte{
 
	public Joker() { //pour une carte joker pas besoin des paramètres  
		super(Couleur.MULTICOLORE, Couleur.NEUTRE); //toutes les cartes Joker serait donc écrire en multicolore et de couleur neutre
		// TODO Auto-generated constructor stub
	}
	
	public Couleur verification(Couleur couleur) {
		if (couleur!=Couleur.MULTICOLORE && couleur!=Couleur.NEUTRE) {
			throw new IllegalArgumentException("La couleur d'une carte Joker ne peut être que neutre avec un mot en multicouleur .");
		}else return couleur;

	}
}
