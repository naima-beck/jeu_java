package classeJeu;
import java.util.*;


public class Jeu {

    public int nbTour;
    public boolean jeuEnCours;
    
    public Proie proie;
    public Chasseur chasseur;
    public Grille grille;
    

    public Jeu() {
    	this.nbTour=0;
    	this.jeuEnCours=true;
    	
    	genererGrille();
    	
    	this.chasseur = new Chasseur(); 
        this.proie = new Proie();
    }
    
    public void genererGrille() {
    	this.grille = new Grille(10, 10);
    	this.grille.placerBonusMalus(10, 20);
    }
    
    public void tourDeJeu() {
    	nbTour ++;
    	
    	// toutes les actions deplacement chasseur et proie 
    	
    	grille.afficherGrille();
    	
    	try { Thread.sleep(1000); } catch (InterruptedException e) {} // petite pause
    }

    
    public void jouer() {
    	System.out.println("=== DÉBUT DE LA PARTIE ===");
        grille.afficherGrille();

        // Tant que le jeu n'est pas fini, on continue
        while (jeuEnCours) {
            tourDeJeu();
            
            // On vérifie si quelqu'un a gagné à la fin du tour
            if (estTermine()) {
                jeuEnCours = false;
            }
        }
        
        // Fin de partie
        Personnage gagnant = determinerVainqueur();
        if (gagnant != null) {
            System.out.println("Le vainqueur est : " + gagnant.getClass().getSimpleName());
        } else {
            System.out.println("Match Nul (Épuisement des énergies) !");
        }
    }

    private boolean verifVictoire() { //retourne booléen
        // à completer par axelle
    	
    	return false;
    }

    private boolean estTermine() {
    	if (verifVictoire()) 
    		return true;
    	
    	// if (chasseur.getEnergie() <= 0 && proie.getEnergie() <= 0) return true;
        return false;
    } 
    
    public Personnage determinerVainqueur() {
        return null;
    }
    
}
