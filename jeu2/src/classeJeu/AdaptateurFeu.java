package classeJeu;

public class AdaptateurFeu implements Item {
    private final Feu feu;

    public AdaptateurFeu(Feu feu) {
        this.feu = feu;
    }

    public int getEnergie() {
        return -feu.energieMoins(); 
    }
    
    
    @Override
    public String toString() {
        return "Feu (Attention ça brûle !)";
    }
    
    @Override
    public void interagir(Personnage p) {
        // Au lieu de simples dégâts, on change l'état
        p.setEtat(new EtatEtourdi());
    }
}