package classeJeu;

public class EtatEmpoisonne implements Etat {
    @Override
    public String appliquerEffets(Personnage p) {
        p.energie -= 1; // Le dégât du poison
        if (p.energie <= 0) {
            p.mort = true;
            return p.nom + " a succombé au poison...";
        }
        return p.nom + " souffre du poison (-1 énergie).";
    }

    @Override
    public String getNom() { return "Empoisonné"; }
}