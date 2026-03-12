package classeJeu;

public class Jumelles implements Item {
    @Override
    public void interagir(Personnage p) {
        p.energie += getEnergie();
        p.setEtat(new EtatVisionEtendue());
    }

    @Override
    public int getEnergie() { return 1; }

    @Override
    public String getDescription() { return "Jumelles de vision"; }
}