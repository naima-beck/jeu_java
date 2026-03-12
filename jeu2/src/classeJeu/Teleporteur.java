package classeJeu;

public class Teleporteur implements Item {
    @Override
    public void interagir(Personnage p) {
        p.energie += getEnergie();
        p.setEtat(new EtatTeleportation());
    }

    @Override
    public int getEnergie() { return -1; }

    @Override
    public String getDescription() { return "Téléporteur"; }
}