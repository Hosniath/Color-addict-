package projet;

import java.util.ArrayList;
import java.util.List;

public class Ordinateur {
	//si il faut jouer contre l'ordinateur alors on attribuera les mêmes attributs que ceux des joueurs 
   public List<Carte> main;	//la main de l'ordinateur
   public List<Carte> pickaxe;  //la pioche de l'ordinateur 
   
    public Ordinateur() {
    	this.main=new ArrayList<Carte>();
    	this.pickaxe=new ArrayList<Carte>();
    }
    
  //l'ordinateur peut piocher une carte   
    public void piocherCarte() {
        if (!this.pickaxe.isEmpty()) {
            Carte cartePiochee = this.pickaxe.remove(0); // Retirez la première carte de la pioche
            this.main.add(cartePiochee); // Ajoutez la carte piochée à la main
            System.out.println("Taille de la main:"+main.size());
        }
    }


  //l'ordinateur peut deposer une carte 
    
}
