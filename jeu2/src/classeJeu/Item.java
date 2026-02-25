package classeJeu;

public interface Item {
	public int getEnergie();
	
	default public void interagir(Personnage p) {
		p.energie += this.getEnergie();
	}
}
