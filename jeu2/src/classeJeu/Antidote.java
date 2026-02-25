package classeJeu;

public class Antidote implements Item {

    @Override
    public int getEnergie() {
        return 1; // Donne +1 vie bonus (comme tu l'as demandé)
    }

    @Override
    public void interagir(Personnage p) {
        p.energie += this.getEnergie(); // Donne la vie bonus
        p.estEmpoisonne = false; // Guérit le poison !
        System.out.println("Ouf ! L'antidote t'a guéri et redonné 1 point d'énergie.");
    }
    
    // g
}