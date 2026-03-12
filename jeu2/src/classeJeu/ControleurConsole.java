package classeJeu;

import java.util.List;
import java.util.Scanner;

public class ControleurConsole {
    private Jeu jeu;
    private Scanner sc;

    public ControleurConsole(Jeu jeu) {
        this.jeu = jeu;
        this.sc = new Scanner(System.in);
    }

    public void lancerBoucle() {
        while (jeu.jeuEnCours) {
            System.out.println("\n--- COMMANDE CONSOLE ---");
            Case cible = null;

            // 1. VERIFICATION CRITIQUE : Si la proie est étourdie, on ne demande rien !
            if (jeu.proie.getEtat() instanceof EtatEtourdi) {
                System.out.println("(!) La Proie est étourdie... elle titube toute seule !");
                // On laisse le modèle choisir une case aléatoire
                List<Case> voisins = jeu.grille.getPlusProcheVoisin(jeu.proie.position);
                cible = voisins.get(new java.util.Random().nextInt(voisins.size()));
            } 
            // 2. Sinon, on gère la saisie normale selon la stratégie
            else {
                if (jeu.proie.getStrategy() instanceof StrategieManuelle) {
                    cible = gererSaisieManuelle();
                } else {
                    cible = gererSaisieLibre();
                }
            }

            if (cible != null) {
                jeu.tourDeJeu(cible);
            }
        }
        System.out.println("\n--- QUE VOULEZ-VOUS FAIRE ? ---");
        System.out.println("1 : Recommencer sur la Console");
        System.out.println("2 : Recommencer sur l'Interface Graphique");
        System.out.println("3 : Quitter");

        Scanner sc = new Scanner(System.in);
        String choix = sc.nextLine();

        if (choix.equals("1")) {
            // Relance en console (récursif ou via une boucle)
            Jeu.main(null); 
        } else if (choix.equals("2")) {
            // Relance en Interface
            Jeu nouvellePartie = new Jeu();
            Jeu.JeuInterface(nouvellePartie);
            nouvellePartie.notifierObservateurs(TypeNotification.DEBUT_PARTIE);
            nouvellePartie.notifierObservateurs(TypeNotification.DEBUT_TOUR);
        } else {
            System.exit(0);
        }
    }

    private Case gererSaisieManuelle() {
        System.out.print("Z(Haut), S(Bas), Q(Gauche), D(Droite) : ");
        String choix = sc.next().toUpperCase();
        int x = jeu.proie.position.x;
        int y = jeu.proie.position.y;

        if (choix.equals("Z")) y--;
        else if (choix.equals("S")) y++;
        else if (choix.equals("Q")) x--;
        else if (choix.equals("D")) x++;

        if (x >= 0 && x < jeu.grille.largeur && y >= 0 && y < jeu.grille.hauteur) {
            return jeu.grille.cases[x][y];
        }
        System.out.println("Mouvement invalide !");
        return null;
    }

    private Case gererSaisieLibre() {
        System.out.print("TELEPORTATION - Entrez x et y (ex: 5 2) : ");
        try {
            int nx = sc.nextInt();
            int ny = sc.nextInt();
            if (nx >= 0 && nx < jeu.grille.largeur && ny >= 0 && ny < jeu.grille.hauteur) {
                Case cible = jeu.grille.cases[nx][ny];
                if (!jeu.proie.aDejaVisite(cible)) return cible;
                else System.out.println("Case déjà visitée !");
            }
        } catch (Exception e) {
            sc.next(); // Flush
        }
        return null;
    }
}