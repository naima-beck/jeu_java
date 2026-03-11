package classeJeu;
import java.util.*;

public class EtatEtourdi implements Etat {
    private int toursRestants = 2;
    private Random rand = new Random();

    @Override
    public String appliquerEffets(Personnage p) {
        if (toursRestants > 0) {
            toursRestants--;
            return p.nom + " est étourdi ! (Encore " + toursRestants + " tours de confusion)";
        } else {
            p.setEtat(new EtatNormal());
            return p.nom + " reprend ses esprits.";
        }
    }

    // Cette méthode va nous servir dans le choix du déplacement
    public boolean estActif() {
        return toursRestants > 0;
    }

    @Override
    public String getNom() { return "Étourdi"; }
}