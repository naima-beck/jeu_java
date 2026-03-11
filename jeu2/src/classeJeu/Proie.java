package classeJeu;
import java.io.*;
import java.util.*;

public class Proie extends Personnage {
   
	private DeplacementStrategy maStrategie; 
	private List<Case> casesVisitees = new ArrayList<>();
	
	public Proie(int energieInitiale, Case positionInitiale) {
	    super("La Proie",energieInitiale, positionInitiale);
	    this.maStrategie = new StrategieManuelle(); 
	    this.casesVisitees.add(positionInitiale);
	}
	public boolean aDejaVisite(Case c) {
        return casesVisitees.contains(c);
    }
	
	public DeplacementStrategy getStrategy() {
	    return this.maStrategie;
	}
	
    public void setStrategy(DeplacementStrategy s) { 
        this.maStrategie = s;
    }

    public String seDeplacer(Grille grille, Jeu jeu) {
        Case prochaine;

        // Gestion de l'état étourdi (Feu)
        if (this.getEtat() instanceof EtatEtourdi) {
            List<Case> voisins = grille.getPlusProcheVoisin(this.position);
            prochaine = voisins.get(new Random().nextInt(voisins.size()));
            
            jeu.setDernierMessage(this.nom + " est désorienté et titube...");
            jeu.notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        } else {
            // Sinon, utilisation de la stratégie (Manuelle ou Libre)
            prochaine = maStrategie.choisirProchaineCase(this, grille);
        }

        // On appelle le seDeplacer de Personnage pour gérer l'énergie et les items
        String message = super.seDeplacer(prochaine);

        // APRÈS le déplacement réussi, on note la nouvelle case sur le papier
        if (prochaine != null && !casesVisitees.contains(prochaine)) {
            casesVisitees.add(prochaine);
        }

        return message;
    }
   

}