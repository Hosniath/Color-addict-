package projet;
import java.util.List;

import java.util.Scanner;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import projet.Carte.Couleur;
public class Jeu {
	// une enumeration pour les differents niveau pour jouer
	public enum Niveau {
		Debutants,
		Inities,
		Confirme,

	} 
	
	
	private final Object verrou = new Object(); //objet de verouillage utile dans la synchronisation multithreads
    private volatile boolean jeuEnCours = true;
    
    private  Niveau level=null; // le niveau de jeu choisit
	private  List <Carte> cards; //la liste des cartes dans le jeu 
	public List<Joueur> gamers; //la liste des joueurs dans le jeu 
	public Table_jeu table; //la table du jeu 
	private Donneur donneur; //le donneur dans le jeu qui fais partie des joueurs 
	public Couleur couleuractuelle; 
	
	//la methode  qui genere la pile de carte necessaire pour le jeu
		public List<Carte> generer(){
		
			List<Carte> res=new ArrayList<Carte>();//le resultat a retourner
	
		Couleur[] coo;
	//on ne joue pas avec toute les cartes quand le niveau est debutants donc on va essayer de generer les cartes en fonction de cela 
		
		if (level == Niveau.Confirme || level == Niveau.Inities) {
			coo = new Couleur[]{Couleur.BLEUE, Couleur.JAUNE, Couleur.NOIRE, Couleur.ROUGE, Couleur.VERTE};
		} else {
			coo = new Couleur[]{Couleur.BLEUE, Couleur.JAUNE, Couleur.ROUGE, Couleur.VERTE};
		}

		//on fera toute les combinaisons possible de mot et de couleur 
		for (Couleur c: coo ) {
			for (Couleur e: coo) {
				Carte creation=new Carte (c,e); //une carte est cree avec une combinaison de couleur et de carte 
				res.add(creation); //on ajoute cette carte dans la liste de carte 
			}
		}
		//ensuite on ajoute quatre carte joker dans le jeu 

		res.add(new Joker());	
		res.add(new Joker());
		res.add(new Joker());
		res.add(new Joker());
		return res;
	}

	//le choix du donneur dans le jeu 
	public static Donneur choixDonneur(List< Joueur> liste) {
		Donneur res=new Donneur(null);
		for(Joueur e: liste) {
			if(e instanceof Donneur) {
				res=(Donneur) e;//on attribue a res le donneur choisit dans la liste des joueurs 
			}
		}

		return  res; //on retourne le joueur a  cette position
	}
	

	//le constructeur pour la creation du jeu : pour un jeu il faut des joueurs et un lot de carte 	
	public Jeu(List<Joueur> gamers,Table_jeu table, Niveau level) {
		this.gamers=gamers;
		this.level=level;
		this.cards=generer();//on genere le lot de carte avec la methode generer
		this.donneur= choixDonneur(gamers);//le donneur est chosit dans la liste des joueurs
		this.table=table;
		//pour le level du jeu on laissera le choix a l utilisateur 
	}
	
	//la methode qui permet de distribuer les cartes et d attribuer les pioches aux joueurs 
	public void distribution () {
		//le nombre de carte au depart moins la carte a  mettre au centre 
		
		//creation d une instance random 
		Random random=new Random();
		/*
On entre dans une boucle qui parcours la liste des joueurs dans le jeu pour leur distribuer trois carte a chacun de facon aleatoirepour chaque joueur 
on lui donne trois carte pris aleatoirement dans la liste des cartes disponibles 
ces cartes sont ainsi ajoute a la main du joueur et on retire la carte  distribue du lot pour ne pas le distribuer une deuxieme fois 
	*/			
		for (Joueur j:gamers) {
			for (int i=0;i<3; i++) {
				int num= random.nextInt(cards.size());
				j.Karty.add(cards.get(num));
				cards.remove(num);
				//on met a jour la liste de carte
			}
		}
		
		/*
apres avoir distribuer les cartes aux joueurs il faut leur donner a chacun une pioche 
les pioches doivent etre egales pour les joueurs
la taille des pioches est obtenue en divisant le nombre de carte restant de facon egale entre les joueurs
		*/
		
		int sizepioche=cards.size()/gamers.size();
		for (Joueur j:gamers) {
			for (int i=0;i<sizepioche; i++) {
				int num= random.nextInt(cards.size());
				j.pioche.add(cards.get(num));
				cards.remove(num);

			}
		}
		
//si apres le partage, il y a des cartes restantes on les supprime 
		while (!cards.isEmpty()) {
			cards.remove(0);
		}
	}

	/*
la methode qui permet de melanger l'ensemble des cartes du jeu afin d'avoir un jeu plus juste 
Pour le melange des cartes on utilisera la methode shuffles de la classe collection  sur la liste de carte disponible 	
	*/
	
	public void melange() {	
		Collections.shuffle(cards);
	}

	 /*
	Initialisation du jeu 
Pour commencer le jeu on melange les cartes, on les distribue entre les joueurs et le donneur pose la premiere carte sur la table 
	 */
	public void initialisegame() {
		melange();
		distribution();
		donneur.PoseOnTable(table.Karte);
		
	};

//Verifie si une carte peut etre depose en fonction des regles du jeu 
	public boolean canDepositCard(Carte carte) {
	
		if (couleuractuelle != null) {
			return carte.color.equals(couleuractuelle) || 
					carte.mot.equals(couleuractuelle) || 
					carte instanceof Joker;
		} else {
			Carte lastCard = table.Karte.get(table.Karte.size() - 1);
			return carte.color.equals(lastCard.color) ||
					carte.mot.equals(lastCard.mot) ||
					carte.mot.equals(lastCard.color) ||
					carte.color.equals(lastCard.mot) ||
					carte instanceof Joker;
		}
	}


// Methode pour demander la prochaine couleur apres q un joueur est depose un Joker
	
	public Couleur askNextColor(Joueur joueur) {
		Scanner sc = new Scanner(System.in);
		System.out.println(joueur.nom + ", vous avez jouer un Joker. Choisissez la prochaine couleur :");
		String chosenColor = sc.nextLine().toUpperCase();
		// Convertir la chaine de caracteres en enumeration Couleur
		try {
			return Couleur.valueOf(chosenColor);
		} catch (IllegalArgumentException e) {
			System.out.println("Couleur non valide. Veuillez reessayer.");
			return askNextColor(joueur); // pour demander a  nouveau
		}
	}

//Methode qui permet a un joueur de jouer une carte si il peut, cela retourne la carte qu'il veut deposer 
	
	public Carte DeposerCarte(Joueur j) {
	    Carte carteChoisie = null;
	    boolean choixValide = false;
	    Scanner scanner=new Scanner(System.in);
	    while (!choixValide) {
	        // Affichage de la main du joueur
	        System.out.println("Main de " + j.nom + " : " + j.Karty);
	        System.out.print("Choisissez le numéro de la carte que vous voulez jouer (ou -1 si vous n'avez pas la carte correspondante) : ");
	        int numeroCarte = scanner.nextInt();

	        if (numeroCarte == -1) {
	            choixValide = true; // Le joueur  ne peux pas jouer ou ne veux pas jouer
	        } else {
// Verifier si le numero correspond a une carte dans la main du joueur
	            for (Carte c : j.Karty) {
	                if (numeroCarte == c.numeroCarte) {
	                    carteChoisie = c;
	                    choixValide = true;
	                    break;
	                }
	            }
	            if (!choixValide) {
	                System.out.println("Veuillez déposer une carte que vous possédez.");
	            }
	        }
	    }
	    return carteChoisie;
	}

	//Une methode qui permet au joueur j de joueur la carte c dans les regles du jeu 
	
	public void PlayCard(Joueur j, Carte c) {
		if(canDepositCard(c)) {
			table.Karte.add(c);
		j.Karty.remove(c);
		System.out.println(j.nom + " a depose la carte " + c);  
		if (c instanceof Joker) {
			couleuractuelle = askNextColor(j); // Demande de la couleur apres avoir joue un Joker
		} else {
			// Si la carte n est pas un Joker, on remet couleurActuelle a null
			couleuractuelle = null;
		}
		}else {
            // si la carte ne peut pas etre deposee, on demande au joueur de revoir sa selection
            System.out.println("La carte que vous avez choisie ne peut pas être déposée. Veuillez revoir votre sélection.");
           Carte ca=DeposerCarte(j); 
           PlayCard(j,ca);// Appel recursif pour permettre au joueur de rechoisir
        }
		 
		
	}

	//Une methode qui verifie si le jeu est bloque. On verifie si au moins un joueur peut deposer au moins une carte,
	// si oui le jeu n est pas bloque sinon le jeu est bloque.
	public boolean bloque(List<Joueur> joueurs) {
		for (Joueur j : joueurs) {
			for (Carte c : j.Karty) {
				if (canDepositCard(c)) {
					return false;
				}
			}
		}
		return true;
	}

	/*
//Methode qui chosis une couleur au hasard dans les couleurs disponibles 
//elle est appele quand le jeu est bloque
   */
	
	public Couleur getRandomColor() {
		Couleur choix;
		Couleur[] coo= new Couleur []{Couleur.BLEUE,Couleur.JAUNE,Couleur.ROUGE,Couleur.VERTE};
		List<Couleur> couleursDisponibles = Arrays.asList(coo); 
		Random random = new Random();
		choix=couleursDisponibles.get(random.nextInt(couleursDisponibles.size()));
		System.out.println("Couleur d'actualité: "+choix);
		return choix;
	}

/*
 * Cette methode verifie la taille de la main du joueur, si la taille est 3 alors il depose et pioche une carte 
 * mais si c est superieur a 3 il depose sans piocher si il a la carte
 */

	public void JouerTour(Joueur j) {
		if (j.Karty.size() == 3) {
			Carte carte=DeposerCarte(j);
			if(carte!=null) {
				PlayCard(j,carte);

				if(!j.pioche.isEmpty()) {
					j.piocher();
				}
				System.out.println(j.nom+" a maintenant "+j.Karty.size()+ " cartes en main et "+j.pioche.size() +" dans sa pioche");  
			}else {
				if(!j.pioche.isEmpty()) {
					System.out.println(j.nom+" n'a pas la carte correspondante et pioche une carte");
					j.piocher();
				}else {
					System.out.println(j.nom+ " n'a pas la carte. +Pioche vide donc on passe son tour.");
				}
			}
		} else {
			Carte carte=DeposerCarte(j);
			if(carte!=null) {
				PlayCard(j,carte); 
			}else {
				if(!j.pioche.isEmpty()) {
					System.out.println(j.nom+" n'a pas la carte correspondante et pioche une carte");
					j.piocher();
				}else {
					System.out.println(j.nom+ " n'a pas la carte. Pioche vide donc on passe son tour.");
				}
			}

		}
	}

/*
 * La logique du jeu pour un niveau debutant et initie 
 *On rentre dans une boucle ou on ne sort que si il ne reste qu un joueur. On verifie d abord si le jeu n est ni bloque ou si le joueur qui doit jouer 
 *a sa main vide. Ensuite chaque joueur joue son tour avec la methode jouerTour(j)
 */
	public Joueur Game(List<Joueur> gagnants, List<Joueur> listeJoueur) {
		List<Joueur> joueurList = new ArrayList<>(listeJoueur);
		boolean firstIteration = true;

		while (joueurList.size()!= 1) {
			Iterator<Joueur> iterator = joueurList.iterator();
			while (iterator.hasNext()) {
				Joueur j = iterator.next();
				System.out.println(j.nom+" commence avec"+j.Karty.size()+" cartes en main et"+j.pioche.size() +" dans sa pioche");
// Verifier si le joueur n est pas le donneur lors de la premiere iteration
				if (!firstIteration || !(j instanceof Donneur)) {
 
					if(bloque(joueurList)) {
						couleuractuelle= getRandomColor();  
					}
	
					if (j.Karty.isEmpty()) {
						System.out.println(j.nom +" sort du jeu");
						gagnants.add(j);
						iterator.remove();
						continue;//passe au joueur suivant
					}
					
					 JouerTour(j);
				}
			}
			firstIteration = false;
		}
		return gagnants.isEmpty() ? null : gagnants.get(0);
	}

/*
 * la liste des cartes que peut deposer le joueur lors de son tour 
 * Cette methode sera utiliser pour le niveau Confirme ou chaque joueur peut jouer quand il peut 
 */
	public List<Carte> peutDeposer(Joueur joueur){
		List<Carte>	depot=new ArrayList<Carte>();
		for(Carte c:joueur.Karty) {
			if(canDepositCard(c)) {
				depot.add(c);
			}
		}
		return depot;
	}
	/*
	 * 		Logique du jeu pour un confirme
	 * Pour permettre a chaque joueur de jouer des qu il peut sans suivre un ordre de tour specifique on utilisera une approche multithreads 
	 * ou chaque joueur est represente par un thread distinct.Le Executor service est utilise pour gerer ces threads et 
	 * l objet verrou est utilise pour synchroniser l acces ou differentes ressources
	 * service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS) est utilise pour attendre la fin de toutes les taches.
	 *  La variable jeuEnCours est utilisee pour arreter les threads une fois que le jeu est termine.
	 * 
	 */

	public Joueur GameConfirme(List<Joueur> gagnants, List<Joueur> listeJoueur) {
        ExecutorService service = Executors.newFixedThreadPool(listeJoueur.size());
        
        
        

        for (Joueur joueurCourant : listeJoueur) {
            service.submit(() -> {
                while (jeuEnCours) {
                    List<Carte> depot = peutDeposer(joueurCourant);
                    synchronized (verrou) {
                        if (!depot.isEmpty()) {
                        	if(bloque(listeJoueur)) {
                    			couleuractuelle= getRandomColor();  
                    		}
                            JouerTour(joueurCourant);
                        }
                    }
                 verifierFinJeu(gagnants, listeJoueur);   
                }
            });
        }

        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return determinerGagnant(gagnants);
    }

// Methode utilisee pour verifier et mettre a jour l etat du jeu de maniere thread-safe.
	
    private void verifierFinJeu(List<Joueur> gagnants, List<Joueur> listeJoueur) {
        synchronized (verrou) {
            Iterator<Joueur> it = listeJoueur.iterator();
            while (it.hasNext()) {
                Joueur joueur = it.next();
                if (joueur.Karty.isEmpty()) {
                	System.out.println(joueur.nom +" sort du jeu");
                    gagnants.add(joueur);
                    it.remove();
                }
            }
            if (listeJoueur.size() == 1) {
                jeuEnCours = false;
            }
        }
    }

    private Joueur determinerGagnant(List<Joueur> gagnants) {
        return gagnants.isEmpty() ? null : gagnants.get(0);
    }
	
    /*	
     *   Logique du jeu pour l ordinateur 
     * La methode parcourt la main de l ordinateur et essaie de jouer une carte valide. Si aucune carte valide n est trouvee,
     * l ordinateur pioche une carte, ou son tour est passe si la pioche est vide.
     */

    public void OrdiJour(Ordinateur o) {
        Iterator<Carte> iterator = o.main.iterator();
        boolean aPose = false;

        while (iterator.hasNext()) {
            Carte carte = iterator.next();
            if (canDepositCard(carte)) {
                table.Karte.add(carte);
                System.out.println("Carte déposée par l'ordinateur: " + carte);
                iterator.remove(); 
                aPose = true;
                o.piocherCarte();
    // Traitement specifique pour les jokers ou autres cartes speciales
                if (carte instanceof Joker) {
                    couleuractuelle = getRandomColor();
                } else {
                    couleuractuelle = null;
                }

               
                break; // Sort de la boucle apres avoir joue une carte
            }
        }

        // Si l ordinateur n a pas pu jouer une carte et qu il doit piocher
        if (!aPose && !o.pickaxe.isEmpty()) {
            System.out.println("L'ordi n'a pas la carte correspondante et pioche une carte");
           o.piocherCarte();
        } else if (!aPose) {
            System.out.println("L'ordi n'a pas la carte et a une pioche vide. On passe le tour de l'ordinateur:");
        }
    }
/*
 * Cette methode simule une partie entre un joueur humain et un ordinateur.Les etapes: 
Melange les cartes et determination de la premiere carte a jouer. Distribution des cartes entre le joueur et l ordinateur.
Les tours alternent entre le joueur humain (JouerTour(j)) et l ordinateur (OrdiJour(o)).On verifie apres chaque tour si l un des joueurs a gagne,
c est-a-dire si lun d eux na plus de cartes.La partie se termine lorsque l un des joueurs n a plus de cartes dans sa main, et le gagnant est annonce.
 */
    
    public String ContreOrdi(Joueur j) {
        Ordinateur o = new Ordinateur();
        String gagnant = "";
        melange(); 

// Trouver et retirer la premiere carte non-Joker pour la mettre sur la table
        Iterator<Carte> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Carte carte = iterator.next();
            if (!(carte instanceof Joker)) {
                table.Karte.add(carte);
                System.out.println("La première carte sur la table est:" + carte);
                iterator.remove(); 
                break;
            }
        }

        for (int i = 0; i < 3; i++) {
            j.Karty.add(cards.get(0));
            o.main.add(cards.get(1));
            cards.remove(0);
            cards.remove(0);
        }

        for (int i = 0; i + 1 < cards.size(); i += 2) {
            j.pioche.add(cards.get(i));
            o.pickaxe.add(cards.get(i + 1));
        }

        cards.clear();

        System.out.println(j.nom + " a " + j.Karty.size() + " cartes en main et " + j.pioche.size() + " dans sa pioche");
        System.out.println("L'ordinateur a " + o.main.size() + " cartes en main et " + o.pickaxe.size() + " dans sa pioche");

        while (gagnant.isEmpty()) {
            JouerTour(j); // le joueur joue
            OrdiJour(o); // ensuite l'ordi
            if (j.Karty.isEmpty()) {
                gagnant = j.nom;
            } else if (o.main.isEmpty()) {
                gagnant = "l'ordinateur";
            }
        }
        return gagnant;
    }


//La methode essentielle du jeu qui est jouer
	public String jouer() {
		List<Joueur> gagnants=new ArrayList<>();//la liste des gagnants en gardant l'ordre de sortie des joueurs 	
		// selon le niveau s'applique des regles
		Joueur gagnant=null;//le joueur a  retourner a la fin
		switch (level) { 
		case Confirme:
				initialisegame();
			gagnant=GameConfirme(gagnants,gamers);
			break;
		case Inities:
				initialisegame();
			gagnant=Game(gagnants,gamers);
			break;
		case Debutants:	
				initialisegame();
			gagnant=Game(gagnants,gamers);
			break;
		default: System.out.println("Niveau non pris en compte par le jeu");
		break;
		}
		return gagnant.nom;
	}

//L affichage pour un jeu entre joueurs 
	public void affichage() {
		System.out.println("Le niveau du jeu choisit est: " +level);
		System.out.println("On a :"+gamers.size()+" joueurs");
		for(int i=0;i<gamers.size();i++) {
			System.out.println(gamers.get(i).toString());
		}
		System.out.println("On a "+cards.size()+ " cartes dans le jeu.");
		for (int i=0; i<cards.size();i++) {
			System.out.println(cards.get(i).toString());
		}
		System.out.println("Celui qui debutera le jeu est: "+donneur);
	}
//L affichage lorque le joueur choisit de jouer contre l ordinateur 
	public void affichageOrdinateur() {
		System.out.println("Le niveau du jeu choisit est: " +level);
		System.out.println("On a "+cards.size()+ " cartes dans le jeu.");
		for (int i=0; i<cards.size();i++) {
			System.out.println(cards.get(i).toString());
		}
		System.out.println("Let's go");
		
	}

}