package classeJeu;

import java.util.ArrayList;
import java.util.List;


public class Personnage {

	protected String nom;
	
	protected int energie;

	protected Case position;

	protected boolean mort;

    public Case c;

    
    private Etat etatCourant;
    
    
    public Personnage(String nom,int energieInitiale, Case positionInitiale) { //On commence par set les valeurs de création d'un personnage
    	this.nom = nom;
    	this.energie = energieInitiale; 
    	this.position = positionInitiale; 
    	this.mort = (energieInitiale <= 0);
    	this.etatCourant = new EtatNormal();
    }
    
    
    public void setEtat(Etat nouvelEtat) {
        this.etatCourant = nouvelEtat;
    }
    
    public Etat getEtat() {
    	return etatCourant;
    }
    
    public String appliquerEtat() {
        return etatCourant.appliquerEffets(this);
    }
    
    // Pour l'Antidote par exemple :
    public void guerir() {
        this.etatCourant = new EtatNormal();
    }
    
    // Pour le Poison :
    public void empoisonner() {
        this.etatCourant = new EtatEmpoisonne();
    }
    
    
    public String seDeplacer(Case c) {
    	if (c == null || mort) return null;
    	
    	position = c; //nouvelle position = c
    	energie--; //perte d'énergie à chaque tour
    	
    	String messageComplet = null;
    	
    	if (position.contientItem()) { //Si la case contient un malus ou un bonus
    		Item i = position.item; //On prend l'élément qui se situe sur notre case
    		i.interagir(this); //sinon ramasser(i)
    		
    		String signe = (i.getEnergie() >= 0) ? "+" : "";
            String infoEnergie = " (" + signe + i.getEnergie() + ")";

            // Assemblage final : "La Proie a trouvé un bonus (+5)" ou "Le Chasseur a trouvé Poison (+0)"
            messageComplet = this.nom + " a trouvé : " + i.getDescription() + infoEnergie;
    		
    		
    		if (i.getEnergie() > 0 ) { //Si c'était un bonus
    			position.retirerItem(); //Le bonus disparait de la case après son utilisation 
    		}
    		
    		if (energie <= 0) { //S'il n'a plus d'énergie
    			mort = true; //Il meurt
    		}
    	
    	}
    	return messageComplet;
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
    
    
    
    


}