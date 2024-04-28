package projet;

import java.util.ArrayList;
import java.util.List;

public class Table_jeu {
	//la table de jeu n'a que la liste des cartes que les joueurs dépose 
	List<Carte> Karte; //la liste de carte sur la table 

	public Table_jeu() {
		Karte=new ArrayList<>();//au départ la liste de carte sur la table est vide
	}
}
