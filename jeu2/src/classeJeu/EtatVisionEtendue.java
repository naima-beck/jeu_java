package classeJeu;

public class EtatVisionEtendue implements Etat {
    private int toursRestants = 2; 

    @Override
    public String appliquerEffets(Personnage p) {
        if (toursRestants > 0) {
            // On prépare le message avec la valeur ACTUELLE avant de baisser
            String msg = p.nom + " utilise ses jumelles (encore " + toursRestants + 
                         (toursRestants > 1 ? " tours" : " tour") + ").";
            
            toursRestants--; // On baisse après avoir généré le message
            return msg;
        } else {
            p.setEtat(new EtatNormal());
            return p.nom + " range ses jumelles.";
        }
    }

    @Override
    public String getNom() {
        return "Vision Etendue";
    }
}