package classeJeu;
import java.util.Scanner;

public class StrategieLibre implements DeplacementStrategy {
    
	@Override
    public Case choisirProchaineCase(Personnage p, Grille g) {
        return p.position; 
    }

    public boolean estMouvementValide(Case actuelle, Case cible, Proie proie) {
        // En mode libre, on peut aller partout SAUF sur une case déjà visitée
        return !proie.aDejaVisite(cible);
    }
	
	
	
}
