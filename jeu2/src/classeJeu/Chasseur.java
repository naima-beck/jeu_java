package classeJeu;
import java.io.*;
import java.util.*;


public class Chasseur extends Personnage {

    public Chasseur(int energieInitiale, Case positionInitiale) {
	    super("Le Chasseur",energieInitiale, positionInitiale);
	}

    public Case prochaineCellule(Grille grille, Proie proie) {
    	if (grille == null) {
    		return null; 
    	}
    	
    	
    	List<Case> voisines = grille.getPlusProcheVoisin(this.position);
        if (proie != null && !proie.mort) {
            for (Case v : voisines) {
                if (v == proie.getPosition()) {
                    return v; // On fonce dessus immédiatement !
                }
            }
        }
        
        
        Case destination = grille.getCible();
        if (destination == null) return null;
        
        
    	
    	// ALGORITHME DE DIJKSTRA 
        // Initialisation des coûts (infini partout sauf au départ)
        Map<Case, Integer> couts = new HashMap<>();
        Map<Case, Case> parents = new HashMap<>();
        PriorityQueue<Case> filePriorite = new PriorityQueue<>(Comparator.comparingInt(c -> couts.getOrDefault(c, Integer.MAX_VALUE)));
        for (int x = 0; x < grille.getLargeur(); x++) {
            for (int y = 0; y < grille.getHauteur(); y++) {
                couts.put(grille.getCases()[x][y], Integer.MAX_VALUE);
            }
        }
        
        couts.put(this.position, 0);
        filePriorite.add(this.position);

        while (!filePriorite.isEmpty()) {
            Case actuelle = filePriorite.poll();

            if (actuelle == destination) break; // On a trouvé le chemin le plus court

            for (Case voisine : grille.getPlusProcheVoisin(actuelle)) {
                // CALCUL DU POIDS (Le coeur de l'esquive)
                int poidsCase = evaluerPoids(voisine);
                int nouveauCout = couts.get(actuelle) + poidsCase;

                if (nouveauCout < couts.get(voisine)) {
                    couts.put(voisine, nouveauCout);
                    parents.put(voisine, actuelle);
                    filePriorite.add(voisine);
                }
            }
        }

        // Reconstruction du chemin pour trouver la 1e case où aller
        Case etape = destination;
        Case prochaine = null;
        
        while (parents.get(etape) != null) {
            if (parents.get(etape) == this.position) {
                prochaine = etape;
                break;
            }
            etape = parents.get(etape);
        }

        if (prochaine == null || prochaine == this.position) {
            List<Case> lvoisines = grille.getPlusProcheVoisin(this.position);
            Case meilleureOption = lvoisines.get(0);
            double meilleurScore = Double.MAX_VALUE;

            for (Case v : lvoisines) {
                // Le score = la distance vers la cible + le poids de la case
                // On divise la distance par 2 pour que le rapprochement soit prioritaire sur la peur
                int danger = evaluerPoids(v);
                int distance = v.distanceVers(grille.getCible());
                double score = danger + (distance * 5); 

                if (score < meilleurScore) {
                    meilleurScore = score;
                    meilleureOption = v;
                }
            }
            prochaine = meilleureOption;
        }

        return prochaine;
    }		
    
    
   
    private int evaluerPoids(Case c) {
        int poidsBase = 10; 
        
        if (!c.contientItem()) return poidsBase;

        Item it = c.item;
        
        // CAS DES DANGERS (Toujours prioritaires)
        if (it instanceof Piege) return (this.energie > 15) ? 10 : 1000; 
        if (it instanceof Poison) return 50;
        if (it instanceof AdaptateurFeu) return 50;
        if (it instanceof AdaptateurEau) return 50;
        if (it instanceof Teleporteur) return 5; // il est très attiré par les trous lol

        // CAS DES BONUS (Intelligence adaptative)
        if (it instanceof Element && it.getEnergie() > 0) {
            // Si le chasseur a moins de 5 points d'énergie, le bonus devient irrésistible (poids 1)
            if (this.energie < 5) return 1; 
            // Sinon, c'est juste une case normale
            return poidsBase;
        }
        
        // CAS DES ANTIDOTES
        if (it instanceof Antidote && this.getEtat() instanceof EtatEmpoisonne) {
            return 1; 
        }

        // MALUS CLASSIQUE
        if (it instanceof Element && it.getEnergie() < 0) return 30;

        return poidsBase;
    }

 		
    		
    public void eliminer(Proie p) {
    	if (p == null) return; //Si plus de proie on arrête

        // Si le chasseur est sur la même case que la proie -> proie morte
        if (this.position == p.getPosition()) {
            p.mort = true;
        }
    	
    }

    public String seDeplacer(Grille grille, Proie proie) {
    	Case prochaine;

        if (this.getEtat() instanceof EtatEtourdi) {
            // Le chasseur aussi perd la tête !
            List<Case> voisins = grille.getPlusProcheVoisin(this.position);
            prochaine = voisins.get(new java.util.Random().nextInt(voisins.size()));
        } else {
            prochaine = prochaineCellule(grille, proie); // Son Dijkstra habituel
        }

        if (prochaine != null) {
            String msg = super.seDeplacer(prochaine);
            eliminer(proie);
            return msg;
        }
        return null;
    }

}