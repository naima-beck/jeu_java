package classeJeu;


public class Personnage {

    public int energie;

    public Case position;

    public boolean mort;

    public Case c;
    
    public boolean estEmpoisonne = false;
    
    
    public Personnage(int energieInitiale, Case positionInitiale) { //On commence par set les valeurs de création d'un personnage
    	this.energie = energieInitiale; //Energie inititale
    	this.position = positionInitiale; //Position inititale
    	this.mort = (energieInitiale <= 0); //Il faut précsier que le personnage est vivant (si énergie initiale supérieure à 0)
    }

    public void ramasser(Item i) {
        if (i != null) {
            i.interagir(this); // "Tiens Item, fais ton effet sur moi (this)"
        }
    }

    
    public void seDeplacer(Case c) {
    	if (c == null || mort) return; //Si la case n'existe pas ou que le personnage est mort il ne peut pas se déplacer
    	position = c; //nouvelle position = c
    	energie = energie - 1; // on perd 1 d'énergie 
    	
    	if (position.contientItem()) { //Si la case contient un malus ou un bonus
    		Item e = position.item; //On prend l'élément qui se situe sur notre case
    		ramasser(e); //On perd ou gagne l'énergie notée
    		
    		if (e.getEnergie() > 0 ) { //Si c'était un bonus
    			position.retirerItem(); //Le bonus disparait de la case après son utilisation 
    		}
    		
    		if (energie <= 0) { //S'il n'a plus d'énergie
    			mort = true; //Il meurt
    		}
    	}
    }

    
    public boolean estVivant() {
        if (energie <= 0) {
            mort = true;
            return false;
        }
        return true;
    }


    public int getEnergie() {
        return energie;
    }

    public Case getPosition() {
        return position;
    }
    
    public void subirEffetsPoison() {
        if (this.estEmpoisonne) {
            this.energie -= 1;
            System.out.println("Aïe ! Le poison te ronge... (-1 énergie. Reste: " + this.energie + ")");
            
            if (this.energie <= 0) {
                this.mort = true;
                System.out.println("Tu as succombé au poison...");
            }
        }
    }


}