package projet;

import java.util.Iterator;
import java.util.List;

public class Donneur extends Joueur{
	
	public Donneur(String nom) {
		super(nom);
		// TODO Auto-generated constructor stub
	}
	//le donneur est un joueur chosit aléatoirement dans la liste des joueurs du jeu 
	
	//le donneur n'a pas d'attribut il doit juste poser une carte sur la table et lancer la partie
	public void start() {
		System.out.println("La partie est lancé!"); //juste une phrase pour dire que le jeu commence 
	}
	public void PoseOnTable(List<Carte> Table) {
	    // le joueur choisit comme donneur pose une première carte qui n'est pas un joker et ensuite pioche
	    boolean Apose = false;
	    Iterator<Carte> iterator = Karty.iterator();

	    while (iterator.hasNext()) {
	        Carte j = iterator.next();
	        if (!(j instanceof Joker)) {
	            Table.add(j);
	            Apose = true;
	            iterator.remove(); // Utilisation de l'itérateur pour supprimer en toute sécurité
	            break; // Sortir de la boucle après avoir ajouté une carte non-joker
	        }
	    }

	    if (Apose && pioche.size() > 0) {
	        Karty.add(pioche.get(0)); // ensuite il stabilise le nombre des cartes en main à 3 en piochant dans sa pioche
	        pioche.remove(0); // retirer la carte de la pioche après l'avoir ajoutée à la main
	        start(); // la partie est ainsi commencée
	        System.out.println("La première carte est:" + Table.get(0));
	    }
	}



	
}
