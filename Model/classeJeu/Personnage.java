package classeJeu;
import java.io.*;
import java.util.*;

public class Personnage {

    public int energie;

    public Case position;

    public boolean mort;

    public Case c;
    
    
    public Personnage(int energieInitiale, Case positionInitiale) { //On commence par set les valeurs de création d'un personnage
    	this.energie = energieInitiale; //Energie inititale
    	this.position = positionInitiale; //Position inititale
    	this.mort = (energieInitiale <= 0); //Il faut précsier que le personnage est vivant (si énergie initiale supérieure à 0)
    }

    public void perdreEnergie(Element e) {
    	if (e == null) return; //Si la case vaut 0 on pas de perte ou de gain d'énergie
    	energie = energie + e.getValeur(); //Sinon l'énergie récupère la valeur de la case
    }

    
    public void seDeplacer(Case c) {
    	if (c == null || mort) return; //Si la case n'existe pas ou que le personnage est mort il ne peut pas se déplacer
    	position = c; //nouvelle position = c
    	energie = energie - 1; // on perd un d'énergie 
    	
    	if (position.contientElement()) { //Si la case contient un malus ou un bonus
    		Element e = position.element; //On prend l'élément qui se situe sur notre case
    		perdreEnergie(e); //On perd ou gagne l'énergie notée
    		
    		if (e.getValeur() > 0 ) { //Si c'était un bonus
    			position.retirerElement(); //Le bonus disparait de la case après son utilisation 
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


}
