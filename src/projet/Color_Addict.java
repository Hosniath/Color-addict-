package projet;

import java.util.Scanner;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import projet.Jeu.Niveau;

public class Color_Addict {

	public static void main(String[] args) {
		Scanner sc=new Scanner(System.in);
		// TODO Auto-generated method stub
  //  Representation inter=new Representation();//représentation graphique 
  Joueur one= new Joueur("Marie-Josée");
  Donneur ouf=new Donneur("Hosni");
  Joueur two=new Joueur("Olympe");
  Joueur three=new Joueur("Josiane");
  List<Joueur> liste=new ArrayList<>(Arrays.asList(one,ouf,two,three));
  Table_jeu table=new Table_jeu();
  
  Jeu game=null;
 
  //On demande au joueurs s'il veut jouer contre un ordinateur ou pas
  int choix; 
  int choicelevel;
  
  String gagnant="";
  System.out.println("Choisir 0 pour jouer avec d'autres joueurs et 1 pour jouer contre l'ordinateur:");
  choix=sc.nextInt();
  System.out.println("Choisir le niveau de jeu: 1-Debutant, 2-Initie et 3-Confirme");
  choicelevel=sc.nextInt();
  switch(choicelevel) {
  case 1: game=new Jeu(liste,table, Niveau.Debutants);
  break;
  case 2: game=new Jeu(liste,table, Niveau.Inities);
  break;
  case 3: game=new Jeu(liste,table, Niveau.Confirme);
  break;
  default :System.out.println("Niveau non pris en compte");
  }
  if(choix ==0) {
	  game.affichage();
	  gagnant=game.jouer();
	  System.out.println("Le gagnant de cette partie est: "+gagnant);
  }else if (choix==1) {
	  String nomJoueur="";
	  System.out.println("Nom du joueur:");
	  nomJoueur=sc.next();
	  Joueur joueur= new Joueur(nomJoueur);
	  System.out.println(joueur.nom+" jouera contre l'ordinateur!"); 
	  game.affichageOrdinateur();
	  gagnant= game.ContreOrdi(joueur);
	 
	  System.out.println("Le gagnant de cette partie est: "+gagnant); 
  }else System.out.print("Ce choix n'est pas pris en compte dans le jeu");
 
  
	}
}
