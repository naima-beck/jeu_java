package classeJeu;

public class EtatNormal implements Etat {
    @Override
    public String appliquerEffets(Personnage p) {
        return null; // Rien ne se passe de spécial
    }
    
    @Override
    public String getNom() { return "Normal"; }
}