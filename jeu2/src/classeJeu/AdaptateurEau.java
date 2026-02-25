package classeJeu;

public class AdaptateurEau implements Item {
    
    private final Eau eau; 
    
    public AdaptateurEau(Eau eau) {
        this.eau = eau;
    }

    @Override
    public int getEnergie() {
        return -eau.calculerPression(); 
    }

    
    @Override
    public String toString() {
        return "Eau (Glou glou...)";
    }
}