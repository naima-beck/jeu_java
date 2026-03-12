package classeJeu;

public class AfficheurConsole implements Observer {
    @Override
    public void actualiser(Jeu jeu, TypeNotification type) {
    	
    	switch (type) {
    	case DEBUT_PARTIE:
    	    System.out.println("==========================================");
    	    System.out.println("   BIENVENUE DANS LA CHASSE AU BONHOMME   ");
    	    System.out.println("==========================================");
    	    System.out.println(" Objectif : Atteindre la cible 'O' !");
    	    System.out.println(" Attention au Chasseur 'C' et aux pièges.");
    	    System.out.println("==========================================\n");
    	    break;
    	    
    	case DEBUT_TOUR:
    		System.out.println("\n>>> TOUR " + jeu.nbTour);
    		
    		if (jeu.proie.getStrategy() instanceof StrategieManuelle) {
    			System.out.println("--- VISION LOCALE (Mode Manuel) ---");
	            jeu.grille.afficherGrille(jeu.proie, jeu.chasseur);
	            //jeu.grille.afficherGrilleOmniscient(jeu.proie, jeu.chasseur);
	            
    		}
    		else if (jeu.proie.getStrategy() instanceof StrategieLibre) {
    	        System.out.println("--- CARTE MÉMOIRE (Mode Libre) ---");
    	        jeu.grille.afficherGrilleHistorique(jeu.proie, jeu.chasseur);
    	        //jeu.grille.afficherGrilleOmniscient(jeu.proie, jeu.chasseur);
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
    	        System.out.println(jeu.genererBilanFin()); // On affiche le texte généré par le jeu
    	        jeu.grille.afficherGrilleOmniscient(jeu.proie, jeu.chasseur);
    	    }
    	    break;
        }
    	
    	
    }
}