package classeJeu;

public class Piege implements Item { //mortel
    
    @Override
    public int getEnergie() {
        return -9999; 
    }
    
    @Override
    public void interagir(Personnage p) {
        p.energie =0; 
        p.mort = true;
    }
}