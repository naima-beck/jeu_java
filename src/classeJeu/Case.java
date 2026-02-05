package classeJeu;

import java.io.*;
import java.util.*;


public class Case {

	public int x;
    public int y;
    public Element element;
    public Proie proie;
    
    public Case(int x, int y) {
    	this.x=x;
    	this.y=y;
    	this.element=null;
    }

    public boolean contientElement() { 
        return this.element !=null; // retourne un booléen
    }
    
  
    
    public void retirerElement() {
    	this.element = null;
    }

    public void setElement(Element e) {
        this.element = e; 
    }
        
    
    public int distanceVers(Case c) {
    	if (c == null) 
    		return Integer.MAX_VALUE; // Sécurité
    	
        return Math.abs(this.x - c.x) + Math.abs(this.y - c.y);
    }

    
    public int getValeur() {
    	if (contientElement()) {
            return this.element.getValeur(); 
        }
        return 0; // Valeur par défaut si pas d'élément
    }

}