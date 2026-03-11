package classeJeu;

public interface Item {
	public int getEnergie();
	
	default public void interagir(Personnage p) {
		p.energie += this.getEnergie();
	}
	
	default String getDescription() {
		String nomClasse = this.getClass().getSimpleName();
	    
	   
	    if (nomClasse.startsWith("Adaptateur")) {
	        return nomClasse.replace("Adaptateur", ""); 
	    }
	    
	    return nomClasse;
    }

}
