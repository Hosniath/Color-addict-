package projet;
import java.util.ArrayList;
import java.util.List;
public class Joueur {
    protected String nom;//nom du joueur 
	protected List<Carte> Karty ; // la main du joueur c'est-a-dire les cartes que possède le joueur
	protected List<Carte> pioche; //la liste de carte ou le joueur doit piocher
	//le constructeur d'un joueur
	public Joueur(String nom) {//constructeur pour la création d'un joueur 
		Karty=new ArrayList<>();//la main du joueur est intialement vide
		pioche=new ArrayList<>();//de même que sa pioche
		this.nom=nom;
	}
	//la méthode qui permet au joueur de piocher une carte dans sa pioche 
	//pour cela on transferera une carte de sa pioche à sa main 
	public void piocher() {
		if(!pioche.isEmpty()) {//on verifie si la pioche est vide avant de piocher
			Karty.add(pioche.get(0)); //il pioche juste la carte au dessus de sa pioche et celle-ci devient sienne
		//et la pioche perd une carte 
		pioche.remove(0);
		}else System.out.println("La pioche de "+nom+" est vide");
		
	}

	
	public String toString() {
		return nom;
	}
	
	public void affichageMainJoueur() {
		System.out.println("La main de "+nom+ " se présente comme suit:");	
		for (int i=0; i<Karty.size();i++) {
			System.out.println(Karty.get(i).toString());
		}

	}
	
}
