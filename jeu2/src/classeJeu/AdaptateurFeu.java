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
}