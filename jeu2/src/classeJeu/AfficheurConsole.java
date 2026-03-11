package classeJeu;

public class AfficheurConsole implements Observer {
    @Override
    public void actualiser(Jeu jeu, TypeNotification type) {
    	
    	switch (type) {
    	case DEBUT_TOUR:
    		System.out.println("\n>>> TOUR " + jeu.nbTour);
    		
    		if (jeu.proie.getStrategy() instanceof StrategieManuelle) {
    			System.out.println("--- VISION LOCALE (Mode Manuel) ---");
	            //jeu.grille.afficherGrille(jeu.proie, jeu.chasseur);
	            jeu.grille.afficherGrilleOmniscient(jeu.proie, jeu.chasseur);
	            
    		}
    		else if (jeu.proie.getStrategy() instanceof StrategieLibre) {
    	        System.out.println("--- CARTE MÉMOIRE (Mode Libre) ---");
    	        //jeu.grille.afficherGrilleHistorique(jeu.proie, jeu.chasseur);
    	        jeu.grille.afficherGrilleOmniscient(jeu.proie, jeu.chasseur);
    	    }
    		
    		System.out.println("Énergie Proie : " + jeu.proie.energie);
            System.out.println("Énergie Chasseur : " + jeu.chasseur.energie);
            break;
    	
    	
    	case MESSAGE_SEUL:
    		if (!jeu.getDernierMessage().isEmpty()) {
                System.out.println(" > " + jeu.getDernierMessage());
            }
            break;
    
    	case FIN_PARTIE:
    		if (!jeu.jeuEnCours) {
                System.out.println("\n=== FIN DE PARTIE ===");
                jeu.grille.afficherGrille(jeu.proie, jeu.chasseur);
                //jeu.grille.afficherGrilleOmniscient(jeu.proie, jeu.chasseur);
                
                Personnage gagnant = jeu.determinerVainqueur();
                
                if (gagnant instanceof Proie) {
                    System.out.println("Victoire ! La proie s'est échappée et a atteint la cible !");
                } else if (gagnant instanceof Chasseur) {
                    if (jeu.proie.mort) {
                        System.out.println("GAME OVER ! La proie est morte.");
                    } else {
                        System.out.println("GAME OVER ! Le chasseur a atteint la cible.");
                    }
                } else {
                    System.out.println("Égalité : Tout le monde est mort...");
                }
    		}
        }
    }
}