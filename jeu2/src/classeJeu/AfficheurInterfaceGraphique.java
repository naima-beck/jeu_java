package classeJeu;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class AfficheurInterfaceGraphique extends JFrame implements Observer {
    private JLabel[][] casesGraphiques;
    private JLabel infoEnergie;
    private JTextArea journal;
    private Jeu jeu;

    public AfficheurInterfaceGraphique(Jeu jeu) {
        this.jeu = jeu;
        int largeur = jeu.grille.largeur;
        int hauteur = jeu.grille.hauteur;

        setTitle("Chasseur et Bonhomme - Vue Graphique");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10)); // Espacement entre les panneaux

        // --- PLATEAU DE JEU ---
        JPanel plateau = new JPanel(new GridLayout(hauteur, largeur, 2, 2));
        plateau.setBackground(Color.DARK_GRAY); // Couleur des "interstices"
        plateau.setBorder(new EmptyBorder(10, 10, 10, 10)); // Marge autour de la grille
        
        casesGraphiques = new JLabel[largeur][hauteur];
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                JLabel label = new JLabel("", SwingConstants.CENTER);
                label.setOpaque(true);
                label.setBackground(Color.WHITE);
                label.setPreferredSize(new Dimension(60, 60));
                
                
                final int finalX = x;
                final int finalY = y;
                
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        // On prévient le jeu qu'on a cliqué sur cette case
                        if (jeu.jeuEnCours && jeu.proie.getStrategy() instanceof StrategieLibre) {
                            jeu.tourDeJeu(jeu.grille.cases[finalX][finalY]);
                        }
                    }
                });
                casesGraphiques[x][y] = label;
                plateau.add(label);
            }
        }

        // --- PANNEAU LATÉRAL ---
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BorderLayout(5, 5));
        sidePanel.setPreferredSize(new Dimension(300, 0));
        sidePanel.setBorder(new EmptyBorder(10, 0, 10, 10));

        // Statistiques (Haut)
        infoEnergie = new JLabel("<html><b>ENERGIE</b><br>Proie: -- | Chasseur: --</html>");
        infoEnergie.setFont(new Font("SansSerif", Font.PLAIN, 14));
        infoEnergie.setBorder(BorderFactory.createTitledBorder("État des Joueurs"));

        // Journal (Centre)
        journal = new JTextArea();
        journal.setEditable(false);
        journal.setFont(new Font("Monospaced", Font.PLAIN, 12));
        journal.setLineWrap(true);
        journal.setWrapStyleWord(true);
        
        // Auto-scroll vers le bas
        DefaultCaret caret = (DefaultCaret) journal.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        JScrollPane scrollJournal = new JScrollPane(journal);
        scrollJournal.setBorder(BorderFactory.createTitledBorder("Journal de bord"));

        sidePanel.add(infoEnergie, BorderLayout.NORTH);
        sidePanel.add(scrollJournal, BorderLayout.CENTER);
        
        JButton btnRestart = new JButton("Nouvelle Partie");
        btnRestart.setFocusable(false);

        // Ces trois lignes sont le "kit de survie" pour la couleur Swing
        btnRestart.setOpaque(true);
        btnRestart.setBorderPainted(true);
        btnRestart.setContentAreaFilled(true); 

        // Couleur de fond (Bleu Acier) et texte
        btnRestart.setBackground(new Color(41, 128, 185)); 
        btnRestart.setForeground(Color.WHITE);
        btnRestart.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRestart.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));

        // Correction du MouseListener pour que le survol soit violent et visible
        btnRestart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRestart.setBackground(new Color(52, 152, 219)); // Bleu plus clair
                btnRestart.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRestart.setBackground(new Color(41, 128, 185)); // Retour au bleu foncé
            }
        });

        btnRestart.addActionListener(e -> {
            // Logique de réinitialisation
            reinitialiserJeu();
        });

        // Ajouter le bouton en bas du sidePanel
        sidePanel.add(btnRestart, BorderLayout.SOUTH);
        
        JButton btnConsole = new JButton("Passer en Console");
        btnConsole.setFocusable(false);
        btnConsole.setOpaque(true);
        btnConsole.setBorderPainted(true);
        btnConsole.setContentAreaFilled(true); 

        // Couleur de fond : Vert Émeraude "Pro"
        btnConsole.setBackground(new Color(39, 174, 96)); 
        btnConsole.setForeground(Color.WHITE);
        btnConsole.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConsole.setBorder(BorderFactory.createLineBorder(new Color(46, 204, 113), 2));

        // Effet de survol pour le vert
        btnConsole.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConsole.setBackground(new Color(46, 204, 113)); // Vert plus clair
                btnConsole.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConsole.setBackground(new Color(39, 174, 96)); // Retour au vert foncé
            }
        });

        btnConsole.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Voulez-vous fermer l'interface et recommencer en Console ?", 
                "Changement de mode", JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose(); // Ferme la fenêtre actuelle
                // On relance une partie en console
                Jeu nouvellePartie = new Jeu();
                ControleurConsole ctrl = Jeu.JeuConsole(nouvellePartie);
                nouvellePartie.notifierObservateurs(TypeNotification.DEBUT_PARTIE);
                nouvellePartie.notifierObservateurs(TypeNotification.DEBUT_TOUR);
                ctrl.lancerBoucle();
            }
        });

        JPanel panelBoutons = new JPanel(new GridLayout(2, 1, 5, 5)); // 2 lignes, 1 colonne, 5px d'écart
        panelBoutons.setOpaque(false); // Pour garder la couleur du sidePanel

        // 3. On ajoute les boutons DANS le petit panel
        panelBoutons.add(btnRestart);
        panelBoutons.add(btnConsole);

        // 4. On ajoute le panel DANS le panneau latéral à la place de l'ancien bouton
        sidePanel.add(panelBoutons, BorderLayout.SOUTH);

        // --- ASSEMBLAGE FINAL (déjà dans ton code normalement) ---
        add(plateau, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        this.setFocusable(true);
        pack();
        setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        setVisible(true);
        this.requestFocusInWindow();
    }

    private void reinitialiserJeu() {
        // 1. On réinitialise les données du jeu (on peut recréer les objets ou faire une méthode reset dans Jeu)
        this.jeu.nbTour = 0;
        this.jeu.jeuEnCours = true;
        this.jeu.genererGrille();
        
        // Repositionner les personnages
        Case caseDepartChasseur = jeu.grille.cases[0][0];
        Case caseDepartProie = jeu.grille.cases[(jeu.grille.largeur - 1)/2][(jeu.grille.hauteur - 1)/2];
        
        this.jeu.chasseur = new Chasseur(50, caseDepartChasseur);
        this.jeu.proie = new Proie(50, caseDepartProie);
        
        // 2. Prévenir tout le monde
        this.jeu.notifierObservateurs(TypeNotification.DEBUT_PARTIE);
        this.jeu.notifierObservateurs(TypeNotification.DEBUT_TOUR);
        
        // 3. Redonner le focus à la fenêtre pour pouvoir jouer de suite
        this.requestFocusInWindow();
    }
    
    @Override
    public void actualiser(Jeu jeu, TypeNotification type) {
        // 1. Gestion des messages (Ton code actuel)
        switch (type) {
        	
	        case DEBUT_PARTIE:
	            journal.setText(""); // On vide le journal de bord
	            journal.append("=== Nouvelle Partie Lancée ===\n");
	            journal.append("Bonne chance !\n");
	            
	            // On force l'affichage initial des statistiques (50 | 50)
	            mettreAJourStats();
	            
	            // On dessine la grille de départ
	            afficherVueLocale(); 
	            break;
            
            case DEBUT_TOUR:
                journal.append("\n[ TOUR " + jeu.nbTour + " ]\n");
                break;
            case FIN_PARTIE:
                journal.append(jeu.genererBilanFin() + "\n"); // On ajoute le texte généré par le jeu
                afficherVueOmnisciente(); 
                break;
                
            case MESSAGE_SEUL:
                if (!jeu.getDernierMessage().isEmpty()) {
                    journal.append(" > " + jeu.getDernierMessage() + "\n");
                }
                break;
        }

        // 2. Choix de la vue dynamique pendant le jeu
        if (jeu.proie.getStrategy() instanceof StrategieManuelle) {
        	//afficherVueOmnisciente(); 
        	afficherVueLocale();
        } else {
        	//afficherVueOmnisciente(); 
        	afficherVueHistorique();
        }
    }
    
    

    // --- LES 3 MÉTHODES DE DESSIN ---

    public void afficherVueLocale() {
        Case posProie = jeu.proie.getPosition();
        Case posChasseur = jeu.chasseur.getPosition(); // On récupère la position du chasseur
        List<Case> visibles;
        if (jeu.proie.getEtat() instanceof EtatVisionEtendue) {
            visibles = jeu.grille.getVoisinsRayon(posProie, 2); 
        } else {
            visibles = jeu.grille.getPlusProcheVoisin(posProie);
        }

        nettoyerPlateau();

        for (int y = 0; y < jeu.grille.hauteur; y++) {
            for (int x = 0; x < jeu.grille.largeur; x++) {
                Case courante = jeu.grille.cases[x][y];
                String fond = "brouillard"; 
                String icone = null;
                String symbole = "";

                // 1. DÉTERMINER LE FOND (Indépendant des personnages)
                if (visibles.contains(courante)) {
                    fond = "sol_visible";
                } else if (jeu.proie.aDejaVisite(courante)) {
                    // Si tu veux quand même voir tes traces de pas en mode manuel, décommente la ligne dessous
                    // fond = "trace_pas"; 
                }

                // 2. DÉTERMINER L'ICÔNE (Priorité absolue aux personnages, peu importe le fond)
                if (courante.equals(posProie)) {
                    icone = "proie"; symbole = "P";
                    fond = "sol_visible"; // On force l'herbe sous nos pieds
                } 
                else if (courante.equals(posChasseur)) {
                    icone = "chasseur"; symbole = "C";
                    // Ici, on ne change pas le fond : s'il est loin, tu verras le Chasseur sur du brouillard
                } 
                else if (visibles.contains(courante) && courante.contientItem()) {
                    // On ne voit les items QUE s'ils sont dans les cases voisines (vision locale)
                    icone = (courante.item.getEnergie() > 0) ? "Bonus" : "Malus";
                    symbole = (courante.item.getEnergie() > 0) ? "+" : "-";
                }
                

                mettreAJourCase(x, y, fond, icone, symbole);
            }
        }
        mettreAJourStats();
    }

    public void afficherVueHistorique() {
        nettoyerPlateau();
        Case posProie = jeu.proie.getPosition();
        Case posChasseur = jeu.chasseur.getPosition();

        for (int y = 0; y < jeu.grille.hauteur; y++) {
            for (int x = 0; x < jeu.grille.largeur; x++) {
                Case courante = jeu.grille.cases[x][y];
                String fond = jeu.proie.aDejaVisite(courante) ? "trace_pas" : "brouillard";
                String icone = null;
                String symbole = "";

                // Toujours afficher Proie et Chasseur en priorité
                if (courante.equals(posProie)) {
                    icone = "proie"; symbole = "P";
                    fond = "trace_pas";
                } 
                else if (courante.equals(posChasseur)) {
                    icone = "chasseur"; symbole = "C";
                    // Le chasseur apparaît sur son fond actuel (soit papier si visité, soit brouillard)
                }
                else if (fond.equals("trace_pas") && courante.contientItem()) {
                    icone = (courante.item.getEnergie() > 0) ? "Bonus" : "Malus";
                    symbole = (courante.item.getEnergie() > 0) ? "+" : "-";
                }

                mettreAJourCase(x, y, fond, icone, symbole);
            }
        }
        mettreAJourStats();
    }

    public void afficherVueOmnisciente() {
        nettoyerPlateau();
        for (int y = 0; y < jeu.grille.hauteur; y++) {
            for (int x = 0; x < jeu.grille.largeur; x++) {
                Case courante = jeu.grille.cases[x][y];
                String icone = null;
                String symbole = "";

                if (courante.equals(jeu.proie.getPosition())) { icone = "proie"; symbole = "P"; }
                else if (courante.equals(jeu.chasseur.getPosition())) { icone = "chasseur"; symbole = "C"; }
                else if (x == jeu.grille.xCible && y == jeu.grille.yCible) { icone = "cible"; symbole = "O"; }
                else if (courante.contientItem()) {
                    Item obj = courante.item;
                    
                    if (obj instanceof Poison) { icone = "Poison"; symbole = "~"; }
                    else if (obj instanceof Antidote) { icone = "Antidote"; symbole = "A"; }
                    else if (obj instanceof Piege) { icone = "Piege"; symbole = "X"; }
                    else if (obj instanceof AdaptateurFeu) { icone = "Feu"; symbole = "F"; }
                    else if (obj instanceof AdaptateurEau) { icone = "Eau"; symbole = "E"; }
                    else if (obj instanceof Teleporteur) { icone = "teleporteur"; symbole = "T"; }
                    else if (obj instanceof Jumelles) { icone = "jumelles"; symbole = "J"; }
                    else if (obj instanceof Element) {
                        if (obj.getEnergie() > 0) { icone = "Bonus"; symbole = "+"; }
                        else { icone = "Malus"; symbole = "-"; }
                    }
                }

                mettreAJourCase(x, y, "sol_visible", icone, symbole);
            }
        }
        mettreAJourStats();
    }

    

    private void nettoyerPlateau() {
        for (int y = 0; y < jeu.grille.hauteur; y++) {
            for (int x = 0; x < jeu.grille.largeur; x++) {
                casesGraphiques[x][y].setIcon(null);
                casesGraphiques[x][y].setText("");
                casesGraphiques[x][y].setBackground(Color.WHITE);
            }
        }
    }

    private void mettreAJourStats() {
        infoEnergie.setText("<html><b>ENERGIE</b><br>Proie: <font color='blue'>" + jeu.proie.energie + 
                            "</font> | Chasseur: <font color='red'>" + jeu.chasseur.energie + "</font></html>");
    }

    private void mettreAJourCase(int x, int y, String nomFond, String nomIcone, String symbole) {
        ImageIcon iconeFinale = superposerImages(nomFond, nomIcone);
        
        // Si l'image finale est vide ou n'a pas pu charger l'icône, on garde le symbole texte en secours
        casesGraphiques[x][y].setIcon(iconeFinale);
        
        // Si on a une icône, on n'affiche pas le texte. Sinon, on met le symbole (+, -, P...)
        if (nomIcone == null || nomIcone.isEmpty()) {
            casesGraphiques[x][y].setText(symbole);
            casesGraphiques[x][y].setFont(new Font("Monospaced", Font.BOLD, 25));
        } else {
            casesGraphiques[x][y].setText(""); 
        }
    }
    
    private ImageIcon superposerImages(String nomFond, String nomIcone) {
        int tailleCase = 60; // Taille définie dans ton preferredSize
        BufferedImage combinee = new BufferedImage(tailleCase, tailleCase, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = combinee.createGraphics();

        try {
            // 1. On dessine le FOND (étiré sur toute la case)
            Image imgFond = new ImageIcon("images/" + nomFond + ".png").getImage();
            g.drawImage(imgFond, 0, 0, tailleCase, tailleCase, null);

            // 2. On dessine l'ICÔNE (plus petite et centrée)
            if (nomIcone != null && !nomIcone.isEmpty()) {
                Image imgIcone = new ImageIcon("images/" + nomIcone + ".png").getImage();
                int tailleIcone = 40; 
                int decalage = (tailleCase - tailleIcone) / 2;
                g.drawImage(imgIcone, decalage, decalage, tailleIcone, tailleIcone, null);
            }
        } catch (Exception e) {
            // En cas d'erreur de chargement, on continue pour voir au moins la couleur/texte
        }

        g.dispose();
        return new ImageIcon(combinee);
    }
}