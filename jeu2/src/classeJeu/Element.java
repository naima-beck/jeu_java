package classeJeu;

import java.io.*;
import java.util.*;


public class Element implements Item {
	private String description;
    private int valeur;
    
    
    public Element(int valeur) {
    	this.valeur=valeur;
    	if (this.valeur > 0) {
            this.description = "Bonus";
        } 
        else if (this.valeur < 0) {
            this.description = "Malus"; 
        } 
        else {
            this.description = null; // y'a rien (valeur=0)
        }
    }

  
    public String getDescription() {
    	return this.description;
    }
    
    
    public int getEnergie() {
        return this.valeur; // On renvoie la valeur stockée (-10 ou +10)
    }
    
    
}