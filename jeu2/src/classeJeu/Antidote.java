package classeJeu;

public class Antidote implements Item {

    @Override
    public int getEnergie() {
        return 1; 
    }

    @Override
    public void interagir(Personnage p) {
        p.energie += this.getEnergie(); 
        p.guerir();
        //System.out.println("Ouf ! L'antidote t'a guéri et redonné 1 point d'énergie.");
    }
    
}