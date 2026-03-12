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

 // --- MÉTHODE 1 : C'est elle qui gère maintenant l'historique ---
    @Override
    public String seDeplacer(Case c) {
        if (c == null) return null;

        // LE PAPIER : On enregistre la case AVANT de bouger
        if (!casesVisitees.contains(c)) {
            casesVisitees.add(c);
        }

        // On appelle la logique de Personnage (items, énergie, messages)
        // C'est ce "super" qui fait le lien avec ton code ci-dessus
        return super.seDeplacer(c);
    }

    // --- MÉTHODE 2 : On la nettoie pour qu'elle ne fasse QUE le choix de case ---
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

        // NOTE : On a supprimé le bloc "casesVisitees.add" ici 
        // car il est maintenant géré automatiquement juste au-dessus !
        
        // On appelle notre propre méthode seDeplacer(Case) 
        // qui va s'occuper du papier ET du mouvement Personnage
        return this.seDeplacer(prochaine);
    }
    
    
   

}