package classeJeu;

public class EtatTeleportation implements Etat {
    @Override
    public String appliquerEffets(Personnage p) {
        // Cette méthode ne sera probablement pas appelée car le Jeu 
        // change l'état en "Normal" juste avant dans x(p).
        return null; 
    }

    @Override
    public String getNom() {
        return "Teleportation";
    }
}