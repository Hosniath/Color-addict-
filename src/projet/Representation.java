
package projet;
import javax.swing.*;
import projet.Carte;
import projet.Carte.Couleur;
import projet.Jeu.Niveau;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;


public class Representation {
    private JFrame frame;
    private  Jeu games;
    private JPanel player1Hand, player2Hand, table;
    private JButton startButton;
    private JLabel player1Label, player2Label;
    private List<Joueur> joueurList;
    private Iterator<Joueur> iterator;
    public Joueur joueurActuell;//le premier joueur

 // Constructeur de la classe representation. Appelle de la mÃ©thode de pour la configuration de la fenetre d'accueil
    public Representation() {
    	homeWindow();
    }
 // Configuration de la fenetre d'acceuil
    private void homeWindow() {
        frame = new JFrame("Color Addict");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Le layout principal du frame
        // creation d'un BackgroundPanel pour l'image de fond
        BackgroundPanel welcomePanel = new BackgroundPanel("/Users/badarou/Downloads/color-addict-version-standard.jpg");
        welcomePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        // Ajout du label de bienvenue avec une meilleure visibilite( la police, la taille,etc..)
        JLabel welcomeLabel = new JLabel("Bienvenue dans Color Addict !", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(new Color(0, 0, 0, 123)); // Semi-transparent background
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);
        // Ajout d'une breve description du jeu sous le titre
        gbc.gridy++;
        JLabel instructionsLabel = new JLabel("<html><center>Alignez les cartes par couleur ou mot pour gagner.</center></html>", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("SansSerif", Font.BOLD, 20)); // Taille de police plus grande et en gras
        instructionsLabel.setForeground(Color.black);
        welcomePanel.add(instructionsLabel, gbc);
        // Stylisation du bouton "Commencer"
        startButton = new JButton("Commencer"); 
        startButton.setFont(new Font("SansSerif", Font.BOLD, 18));// la police 
        startButton.setBackground(new Color(50, 150, 50)); // Une couleur verte attrayante
        startButton.setForeground(Color.black);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setOpaque(true);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // ajout d'un Ã©couteur de souris au bouton startButton
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButton.setBackground(new Color(70, 200, 70)); // Couleur plus claire au survol
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startButton.setBackground(new Color(50, 150, 50)); // Couleur d'origine
            }
        });
        startButton.addActionListener(e-> impiezarjugar());
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy++;
        welcomePanel.add(startButton, gbc);
        frame.add(welcomePanel, BorderLayout.CENTER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width / 2, screenSize.height / 2);
        frame.setLocationRelativeTo(null);  // Suppression de l'animation de couleur sur le label d'instructions  
        frame.pack(); 
        frame.setVisible(true);
    }
  // configuration du niveau de jeu, effectue des opÃ©rations liÃ©es Ã  l'utilisateur et dÃ©marre enfin le jeu avec le niveau choisi
    public void impiezarjugar() {
        // Choisisez le niveau de jeu
        Niveau level = choisirNiveau();
        fazUsuario();
        demarrerJeu(level);
        
    }
// Configuration de l'interface utilisateur pour le jeu
    private void fazUsuario () {
        frame.getContentPane().removeAll(); // Enleve tous les composants de la fenetre precedente
        // Configuration des panneaux pour les mains des joueurs et la table
        player1Hand = new JPanel();
        player2Hand = new JPanel();
        table = new JPanel();
        // Utilisation de layouts flexibles pour les panneaux
        player1Hand.setLayout(new GridLayout(1, 0, 10, 10)); // GridLayout avec des espacements entre les composants, les chiffres 10, 10 definissent l'espacement horizontal et vertical
        player2Hand.setLayout(new GridLayout(1, 0, 10, 10));
        table.setLayout(new GridLayout(1, 0, 10, 10));
        // Ajout d'un arriere-plan distinctif pour la zone de jeu
        table.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Ajoute un contour noir
        table.setBackground(new Color(240, 240, 240)); // Couleur de fond claire pour la table
        // Amelioration de la visibilite des noms des joueurs
        player1Label = new JLabel("Joueur 1");
        player1Label.setFont(new Font("SansSerif", Font.BOLD, 20)); // Police plus grande pour les labels
        player2Label = new JLabel("Joueur 2");
        player2Label.setFont(new Font("SansSerif", Font.BOLD, 20));
        // Ajout des labels aux panneaux correspondants
        player1Hand.add(player1Label);
        player2Hand.add(player2Label); 
        player1Hand.setLayout(new GridLayout(1, 0, 10, 10)); 
        // Definition des couleurs de fond pour les panneaux
        player1Hand.setBackground(new Color(255, 228, 225)); // Un rose tres clair
        player2Hand.setBackground(new Color(225, 255, 228)); // Un vert tres clair
        table.setBackground(Color.black);
        // Ajout des panneaux 
        frame.add(player1Hand, BorderLayout.SOUTH);
        frame.add(player2Hand, BorderLayout.NORTH);
        
// Ajout d'un bouton pour recommencer le jeu 
        
        
        frame.add(new JButton("Recommencer") {{
            setPreferredSize(new Dimension(120, 50));
            setFont(new Font("SansSerif", Font.BOLD, 12));
            addActionListener(e -> {
            	frame.validate();
                frame.repaint();
                frame.dispose();
            	new Representation();
            
            });
        }}, BorderLayout.EAST);
        frame.add(table, BorderLayout.CENTER);
        // Rafraichir le cadre pour montrer les nouveaux panneaux
        frame.validate();
        frame.repaint();
        }

  // Classe interne pour creer un JPanel avec un arriere-plan d'image
    public class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
 // Methode pour choisir le niveau de jeu
    private Niveau choisirNiveau() {
        Object[] options = {"Debutants", "Inities", "Confirme"};
        int choix = JOptionPane.showOptionDialog(frame,
                "Choisissez le niveau de jeu:",
                "Niveau de Jeu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);
Niveau niveauDeJeu;
        switch (choix) { 
            case 0:
                niveauDeJeu = Niveau.Debutants;
                break;
            case 1:
                niveauDeJeu = Niveau.Inities;
                break;
            case 2:
                niveauDeJeu = Niveau.Confirme;
                break;
            default:
                niveauDeJeu = Niveau.Debutants; // Niveau par dÃ©faut
        }

        return niveauDeJeu;
    }
    
    // Methode qui demande au utilisateur de saisir leurs noms
    public void demarrerJeu(Niveau level) {
	    String joueur1 = JOptionPane.showInputDialog(frame, "Nom du premier joueur:");
	    String joueur2 = JOptionPane.showInputDialog(frame, "Nom du second joueur:");

	    if (joueur1 == null || joueur1.trim().isEmpty() || joueur2 == null || joueur2.trim().isEmpty()) {
	        // Gestion du cas ou les joueurs n'entrent pas leurs noms 
	        JOptionPane.showMessageDialog(frame, "Les noms des joueurs ne peuvent pas etre vides.");
	        return; // Sortir de la methode si les noms ne sont pas valides
	    }
	    Joueur gamesrsOne = new Joueur(joueur1.trim());
	    Donneur gamesrsTwo = new Donneur(joueur2.trim());
	    player1Label.setText("gamesRSONE " + joueur1);
	    player2Label.setText("gamesRSTWO  " + joueur2);
	    joueurList = new ArrayList<>(Arrays.asList(gamesrsOne, gamesrsTwo));
	    Table_jeu gamesboard = new Table_jeu();
	    games = new Jeu(joueurList, gamesboard, level);
	    games.melange();//Melanger les cartes
	    games.distribution();// Distribuer les cartes 
	    gamesrsTwo.PoseOnTable(gamesboard.Karte); 
	    Carte premiereCarte=games.table.Karte.get(games.table.Karte.size()-1);
	    if(premiereCarte!=null) {
	    	afficherPremiereCarte(premiereCarte);// affiche la premiere carte
	    }
	    displayCards();
    }
    
    private void afficherPremiereCarte(Carte carte) {
    	JButton carteButton= createCardButton(carte);
    	table.add(carteButton);// Ajouter le boutton representant la premiere carte deposee
  }
    public void displayCards() {
        joueurActuell= games.gamers.get(0);//le premier joueur
           // Supprime le contenu existant des panneaux des mains des joueurs et de la table
           player1Hand.removeAll();
           player2Hand.removeAll();
           table.removeAll();
    // Ajout de deux boutons pour pour piocher lorsque le joueur n'a pas la bonne carte 
         JButton cardnulle2 = new JButton("-1:pas de carte jouable");
         JButton cardnulle1 = new JButton("-1:pas de carte jouable");

         player1Hand.add(cardnulle1);//on ajoute une carte nulle dans la main du joueur qui sera clique juste quand le joueur n'a pas de carte
         player2Hand.add(cardnulle2);//de mÃªme dans la main du joueur 2
         cardnulle2.addActionListener(e -> {
          Carte ca=games.gamers.get(1).pioche.get(0);
          games.gamers.get(1).pioche.remove(1);
          JButton cardBuT = new JButton(ca.toString());
          games.gamers.get(1).Karty.add(ca); 
             player2Hand.add(cardBuT); 
             joueurActuell= games.gamers.get(0);//on change  le joueur car il pioche 
       
         });
         cardnulle1.addActionListener(e -> {
            Carte ca=games.gamers.get(0).pioche.get(0);
            JButton cardBuT = new JButton(ca.toString());
            games.gamers.get(0).Karty.add(ca);
               games.gamers.get(0).pioche.remove(0);
               player1Hand.add(cardBuT); // table est le JPanel pour la pile centrale
               joueurActuell= games.gamers.get(1);//on change le joueur 
           });
           // Ajout des cartes aux mains des joueurs avec ActionListeners

           for (Carte carte : games.gamers.get(0).Karty) { // Joueur1
               JButton cardButton = createCardButton(carte);
               cardButton.addActionListener(e ->{ 
                if (joueurActuell.equals(games.gamers.get(0))) {
                if (carte instanceof Joker) {
                           askForColor(games.gamers.get(0)); // Demander la couleur si c'est un Joker
                       }
                updateTableWithClickedCard(carte);
                player1Hand.remove(cardButton);
                Carte co=games.gamers.get(0).pioche.get(0);
                     JButton cardBu = createCardButton(co);
                     games.gamers.get(0).Karty.add(co);
                     games.gamers.get(0).pioche.remove(0);
                     
                     player1Hand.add(cardBu); // 
             // Passe au joueur suivant
                joueurActuell= games.gamers.get(1)    ;
               } else {
                   JOptionPane.showMessageDialog(null, "Ce n'est pas votre tour !");
               }});
               player1Hand.add(cardButton);
           }
           for (Carte carte : games.gamers.get(1).Karty) { //Joueur2
               JButton cardButton = createCardButton(carte);
               cardButton.addActionListener(e ->{
               if (joueurActuell.equals(games.gamers.get(1))) {
                if (carte instanceof Joker) {
                       askForColor(games.gamers.get(1)); // Demander la couleur si c'est un Joker
                   }
                updateTableWithClickedCard(carte);
                player2Hand.remove(cardButton);
                Carte co=games.gamers.get(0).pioche.get(0);
                 JButton cardBu = createCardButton(co);
                 games.gamers.get(1).Karty.add(co);
                 games.gamers.get(1).pioche.remove(0);
                 player2Hand.add(cardBu); 

                   // Passer au joueur suivant

                joueurActuell= games.gamers.get(0);
               } else {
                   JOptionPane.showMessageDialog(null, "Ce n'est pas votre tour !");

               }});

               player2Hand.add(cardButton);

           }


           // Affiche la derniÃ¨re carte jouee au centre de la table

           Carte carteSurTable = games.table.Karte.get(games.table.Karte.size() - 1);
           if (carteSurTable != null) {
               JButton tableCardButton = createCardButton(carteSurTable);
               table.add(tableCardButton, BorderLayout.CENTER);
           }

           // Rafraichissement  de l'interface utilisateur pour afficher les changements

           frame.revalidate();
           frame.repaint();

       }


    
    
    // Methode pour mettre a  jour la table avec la carte cliquee
    private void updateTableWithClickedCard(Carte clickedCard) {
        // Supprime toutes les vues precedentes de la carte du milieu 
    	//si la carte clique peut etre jouer alors on le met au centre 
    	if(games.canDepositCard(clickedCard)) {
    		System.out.println("La carte clique peut etre depose");
    		table.removeAll();
            // Creation d'un nouveau bouton pour la carte cliquee et ajout au milieu de la table
            JButton tableCardButton = createCardButton(clickedCard);
            table.add(tableCardButton, BorderLayout.CENTER); // Utilisation de  BorderLayout.CENTER pour centrer la carte
            // Rafraichissez l'interface utilisateur pour montrer la carte au centre
            table.revalidate();
            table.repaint();	
    	}else {
    		System.out.println("La carte clique NE peut PAS etre depose");
    		 JOptionPane.showMessageDialog(null, "Cette carte ne peut Ãªtre deposÃ© !");
    	}     
    }    
   
    private void askForColor(Joueur joueur) {
        // Les options de couleurs disponibles, en se basant sur l'enumeration Couleur situee dans la classe carte
        Carte.Couleur[] colors = { Carte.Couleur.JAUNE, Carte.Couleur.ROUGE, Carte.Couleur.VERTE, Carte.Couleur.BLEUE };
        
        // Transformation de  l'enumeration des couleurs en un tableau de chaines de caracteres pour l'affichage
        String[] colorNames = Arrays.stream(colors)
                                    .map(Enum::name)
                                    .toArray(String[]::new);

        // Affichage d'une boite de dialogue pour que le joueur choisisse une couleur
        String chosenColorName = (String) JOptionPane.showInputDialog(
            frame, 
           games.gamers.get(0).nom + ", choisissez la prochaine couleur :", 
            "selection de couleur", 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            colorNames, 
            colorNames[0]);
        // Verification d'un choix d'une couelur par le joueur
        if (chosenColorName != null) {
            try {
                // Conversion de  la chaine choisie en enumeration Couleur
                Carte.Couleur chosenColor = Carte.Couleur.valueOf(chosenColorName);
                // Mettre a  jour la couleur actuelle dans le jeu
                games.couleuractuelle = chosenColor;
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(frame, "Couleur non valide. Veuillez ressayer.");
                askForColor(joueur); // Demander un  nouveau si la couleur n'est pas valide
            }
        } else {
            // Gestion du cas ou le joueur ferme la boite de dialogue sans faire de choix
            JOptionPane.showMessageDialog(frame, "Vous devez choisir une couleur.");
            askForColor(joueur); // redemande Ã  nouveau
        }
    }

        
    
    private JButton createCardButton(Carte carte) {
        JButton cardButton = new JButton(carte.mot.toString()); // Utilisation du  mot de la carte pour le texte
         cardButton.setPreferredSize(new Dimension(120, 120));  //Taille carre pour le bouton

        Color backgroundColor = getColorFromCard(carte.color); // Utilisation la couleur de la police pour le fond
       Color textColor = getContrastColor(backgroundColor); // Utilisation  d'une couleur contrastee pour le texte
        cardButton.setBackground(backgroundColor);
        cardButton.setForeground(Color.WHITE);
        cardButton.setOpaque(true);
        cardButton.setBorderPainted(false);
       
     // Definition d'une taille standard pour tous les boutons de cartes
        Dimension cardButtonSize = new Dimension(20, 40); // Taille standard pour les cartes
        cardButton.setPreferredSize(cardButtonSize);
        Dimension centerCardSize = new Dimension(50, 140); // Taille pour la carte au centre
       
       
        //  ActionListener ici pour les interactions avec les cartes
        cardButton.addActionListener(e -> {
            // Logique pour jouer la carte
            System.out.println("Carte clique: " + carte);
        });
     //Ajout d'un effet de surbrillance a  la carte selectionne
        for (Component comp : player1Hand.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent evt) {
                        button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2));
                    }
                    public void mouseExited(MouseEvent evt) {
                        button.setBorder(UIManager.getBorder("Button.border"));
                    }
                });
            }
        }

        return cardButton;
    }

    private Color getColorFromCard(Carte.Couleur couleur) {
        switch (couleur) {
            case BLEUE:
                return Color.BLUE;
            case ROUGE:
                return Color.RED;
            case VERTE:
                return Color.GREEN;
            case JAUNE:                return Color.YELLOW;
            case NOIRE:
                return Color.BLACK;
            case MULTICOLORE:
            	return new Color(150, 0, 150); // Utilisation de valeurs RGB specifiques 
            default:
                return Color.WHITE;
        }
    }

    // Methode pour obtenir une couleur contrastante pour le texte
    private Color getContrastColor(Color backgroundColor) {
        // Calcul  base sur la luminosite
        double luminance = (0.299 * backgroundColor.getRed() + 0.587 * backgroundColor.getGreen() + 0.114 * backgroundColor.getBlue()) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE; // Texte noir sur fond clair, blanc sur fond fonce
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Representation());
        
    }

}