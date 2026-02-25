package classeJeu;


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
    	
    	Case caseDepartChasseur = grille.cases[0][0];
    	Case caseDepartProie = grille.cases[(grille.largeur - 1)/2][(grille.hauteur - 1)/2]; // la proie commence au milieu
    	
    	this.chasseur = new Chasseur(50, caseDepartChasseur); 
        this.proie = new Proie(50, caseDepartProie);
    }
    
    
    public void genererGrille() {
    	this.grille = new Grille(10, 10);
    	this.grille.placerBonusMalus(10, 10, 5, 5, 5, 5, 2);
    }
    
    public void tourDeJeu() {
    	nbTour ++;
    	System.out.println("\n>>> TOUR " + nbTour + " (Z,Q,S,D + Entrée)");
    	
    	
    	
    	grille.afficherGrille(proie,chasseur);
    	proie.seDeplacer(grille);
    	proie.subirEffetsPoison();
    	
    	gererCollision(); 
    	if (verifVictoire()) {
            jeuEnCours = false;
            return;
        }
    	
    	System.out.println("Le Chasseur se déplace...");
        
    	chasseur.seDeplacer(grille, proie);
    	chasseur.subirEffetsPoison();
    	gererCollision();
    	
    	if(proie.mort)
    	
    	try { Thread.sleep(1000); } catch (InterruptedException e) {} // petite pause
    }
    
    
    public void jouer() {
    	System.out.println("=== DÉBUT DE LA PARTIE ===");
        //grille.afficherGrille(proie,chasseur);

        // Tant que le jeu n'est pas fini, on continue
        while (jeuEnCours) {
            tourDeJeu();
   
            if (estTermine()) {
                jeuEnCours = false;
            }
        }
        
        
        // Fin de partie
        System.out.println("\n=== FIN DE PARTIE ===");
        grille.afficherGrille(proie, chasseur);
        
        Personnage gagnant = determinerVainqueur();
        if (gagnant instanceof Proie) {
            System.out.println("Victoire ! La proie s'est échappée !");
        } else if (gagnant instanceof Chasseur) {
            System.out.println("GAME OVER ! Le chasseur a mangé la proie.");
        } else {
            System.out.println("MATCH NUL (Bug ou épuisement).");
        }
    }

    private boolean verifVictoire() { //retourne booléen
    	if(proie.position.x == grille.xCible && proie.position.y == grille.yCible) 
    		return true;
    	return false;
    }
    
    private void gererCollision() {
        if (chasseur.position.x == proie.position.x && chasseur.position.y == proie.position.y) {
            chasseur.eliminer(proie);
        }
    }

    private boolean estTermine() {
    	if (verifVictoire()) 
    		return true;
    	
    	if (proie.mort && chasseur.mort) // les 2 sont morts
    		return true;
    	
    	else if (proie.mort) // la proie est morte
    		return true;
    	
        return false;
    } 
    
    
    public Personnage determinerVainqueur() {
    	if (verifVictoire()) {
            return this.proie;
        }
        
        if (proie.mort) {
            return this.chasseur;
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        Jeu maPartie = new Jeu();
        maPartie.jouer();
    }
   
}