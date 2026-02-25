package classeJeu;

public class Poison implements Item {

    @Override
    public int getEnergie() {
        return 0; // Sur le coup, ça ne fait pas de dégâts immédiats
    }

    @Override
    public void interagir(Personnage p) {
        p.estEmpoisonne = true;
        System.out.println("Oh non ! Tu as été empoisonné !");
    }
}