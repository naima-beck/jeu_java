package classeJeu;
import java.io.*;
import java.util.*;


public class Grille {
	
	public int hauteur;

    public int largeur;
    
    public Case[][] cases;

    public int xCible;

    public int yCible;

    private Random random = new Random();
    
    
    public Grille(int hauteur, int largeur) {
    	this.hauteur=hauteur;
    	this.largeur=largeur;
    	this.cases = new Case[largeur][hauteur];
    	
    	// Initialisation du tableau avec des cases vides
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                cases[x][y] = new Case(x, y);
            }
        }
        
        // cible en bas à droite de la grille
        this.xCible = largeur-1;
        this.yCible = hauteur-1;
    }

    
    public void placerBonusMalus(int pourcentageBonus, int pourcentageMalus) {
   
            if (pourcentageBonus + pourcentageMalus > 100) {
                System.out.println("Erreur : Le total des pourcentages dépasse 100 ! Pourcentage mis par défaut");
                pourcentageBonus = 10;
                pourcentageMalus = 20;
            }

            for (int x = 0; x < largeur; x++) {
                for (int y = 0; y < hauteur; y++) {
                    
                	// exception : il faudra aussi faire une exception sur la position du chasseur
                    if ((x == 0 && y == 0) || (x == xCible && y == yCible)) { // rien sur case départ sur case cible
                        continue; 
                    }

                    int tirage = random.nextInt(100); // nombre entre 0 et 99

                   
                    if (tirage < pourcentageBonus) {
                        cases[x][y].element = new Element(10); //bonus
                    } 
                    
                    
                    else if (tirage < (pourcentageBonus + pourcentageMalus)) {
                        cases[x][y].element = new Element(-10); //malus
                    }
                    
                    // Sinon (si tirage >= pourcentageBonus + pourcentageMalus) : La case reste vide.
                }
            }
        }
    
    
    
    private boolean estDansLaGrille(int x, int y) {
        return (x >= 0 && x < largeur && y >= 0 && y < hauteur); // renvoie un booléen
    }


    public List<Case> getVoisins(Case c) { // cette méthode retourne la liste des cases voisines d'une case au sein d'une grille
        List<Case> voisins = new ArrayList<>();
        
        // Coordonnées relatives (Haut, Bas, Gauche, Droite)
        int[] dx = {0, 0, -1, 1}; // dx = décalage en x
        int[] dy = {-1, 1, 0, 0}; // dy = décalage en y

        for (int i = 0; i < 4; i++) {
            int prochainX = c.x + dx[i]; // on teste les 4 possibilités 
            int prochainY = c.y + dy[i];

            
            if (estDansLaGrille(prochainX, prochainY)) { // si dans la grille => on ajoute la case dans la liste des voisins
                voisins.add(cases[prochainX][prochainY]);
            }
        }
        
        return voisins;
    }

    
    public void afficherGrille() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (cases[x][y].contientElement()) {
                	if (cases[x][y].element.getValeur() > 0) {
                        System.out.print("[+]"); // bonus
                    } else if (cases[x][y].element.getValeur() < 0) {
                        System.out.print("[-]"); // malus 
                    }
                    
                } else if (x == xCible && y == yCible) {
                    System.out.print("[O]"); // cible
                } else {
                    System.out.print("[ ]"); // case vide
                }
            }
            System.out.println(); // Retour à la ligne
        }
    }
    

}
