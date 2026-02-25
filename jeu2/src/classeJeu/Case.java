package classeJeu;

import java.io.*;
import java.util.*;


public class Case {

	public int x;
    public int y;
    public Item item;
    //public Proie proie;
    
    public Case(int x, int y) {
    	this.x=x;
    	this.y=y;
    	this.item=null;
    }
    

    public boolean contientItem() { 
        return this.item !=null; // retourne un booléen
    }
    
  
    public void retirerItem() {
    	this.item = null;
    }
    

    public void setItem(Item item) {
        this.item = item; 
    }
        
    
    public int distanceVers(Case c) { // pour le moment ca sert à rien ça 
    	if (c == null) 
    		return Integer.MAX_VALUE; // Sécurité
    	
        return Math.abs(this.x - c.x) + Math.abs(this.y - c.y);
    }

    
    public int getValeur() {
    	if (contientItem()) {
            return this.item.getEnergie(); 
        }
        return 0; // Valeur par défaut si pas d'élément
    }

}