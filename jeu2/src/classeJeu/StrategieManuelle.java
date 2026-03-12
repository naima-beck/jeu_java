package classeJeu;
import java.util.List;
import java.util.Scanner;

public class StrategieManuelle implements DeplacementStrategy {
    
	@Override
    public Case choisirProchaineCase(Personnage p, Grille g) {
        // Cette méthode ne sera plus utilisée par l'interface graphique
        // Mais on peut la laisser pour le mode Console pur si tu veux
        return p.position; 
    }

    // AJOUTE CETTE MÉTHODE pour l'interface graphique
    public boolean estMouvementValide(Case actuelle, Case cible) {
        // En manuel, on ne peut bouger que d'une case (adjacente)
        int dist = Math.abs(actuelle.x - cible.x) + Math.abs(actuelle.y - cible.y);
        return dist == 1;
    }
	
	
	
    
   

}
