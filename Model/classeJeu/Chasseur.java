package classeJeu;
import java.io.*;
import java.util.*;


public class Chasseur extends Personnage {

    public Chasseur(int energieInitiale, Case positionInitiale) {
	    super(energieInitiale, positionInitiale);
	}

    public Case prochaineCellule(Grille grille, Proie proie) {
    	if (grille == null) return null;
    	
    	//Vérfiier qu'il y a des plu sproches voisins et obtenir leur liste
    	List <Case> voisines = grille.getPlusProcheVoisin(this.position);
    	if (voisines == null || voisines.isEmpty()) return null;
    	
    	//Obtenir la case de la proie si elle est dans les plus proches voisins
    	if (proie != null && proie.getPosition() != null); //Si la proie est toujours sur la grille
    		Case versProie = proie.getPosition(); 
    		for (Case v : voisines) {
    			if (v == versProie) {
    				return v;
    			}
		
    	//Si la proie n'est pas dans les plus proches voisions, on vise la cible
    	Case cible = grille.getCible();
    	if (cible == null) return voisines.get(0); //Si pas de cible
    	
    	Case meilleure = voisines.get(0);
        int meilleureDistance = meilleure.distanceVers(cible);
    	
    	for (Case m : voisines) {
            int d = m.distanceVers(cible);
            if (d < meilleureDistance) {
                meilleureDistance = d;
                meilleure = m;
            }
        }

        	return meilleure;			
    	}
    }
 		
    		
    public void eliminer(Proie p) {
    	if (p == null) return; //Si plus de proie on arrête

        // Si le chasseur est sur la même case que la proie -> proie morte
        if (this.position == p.getPosition()) {
            p.mort = true;
        }
    	
    }

    public void seDéplacer(Grille grille, Proie proie) {
    	Case next = prochaineCellule(grille, proie);
    	if (next != null) {
            super.seDeplacer(next);
            eliminer(proie);
            }
    }

}
