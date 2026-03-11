package classeJeu;

import java.util.ArrayList;
import java.util.List;

public class Jeu { //

    public int nbTour;
    public boolean jeuEnCours;
    
    public Proie proie;
    public Chasseur chasseur;
    public Grille grille;
    
    private List<Observer> observateurs = new ArrayList<>();
    private String dernierMessage = "";
    
    private static final int HIGH_VALUE = 51; //On définit la valeur seuil du changement de mode de déplacement
    
    
    public void ajouterObservateur(Observer o) {
        observateurs.add(o);
    }

    public void notifierObservateurs(TypeNotification type) {
        for (Observer o : observateurs) {
            o.actualiser(this, type);
        }
    }
    
    public String getDernierMessage() { return dernierMessage; }
    public void setDernierMessage(String msg) { this.dernierMessage = msg; }

    public Jeu() {
    	this.nbTour=0;
    	this.jeuEnCours=true;
    	
    	genererGrille();
    	
    	Case caseDepartChasseur = grille.cases[0][0];
    	Case caseDepartProie = grille.cases[(grille.largeur - 1)/2][(grille.hauteur - 1)/2]; // la proie commence au milieu
    	
    	this.chasseur = new Chasseur(50, caseDepartChasseur); 
        this.proie = new Proie(50, caseDepartProie);
        
        //this.proie.ajouterObservateur(this);
    }
    
    
    
    public void genererGrille() {
    	this.grille = new Grille(10, 10);
    	this.grille.placerBonusMalus(10, 10, 5, 5, 5, 5, 2);
    }
    
    
    public void x(Personnage p) {
    	
    	String msgMouvement = (p instanceof Proie) ? ((Proie) p).seDeplacer(grille,this) : ((Chasseur) p).seDeplacer(grille,proie) ; 
        
    	if (msgMouvement != null) {
            setDernierMessage(msgMouvement);
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        }


    	String msgEtat = p.appliquerEtat(); 
        if (msgEtat != null) {
            setDernierMessage(msgEtat);
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        }
        
    }
    
    public void tourDeJeu() {
    	nbTour ++;
    	setDernierMessage(""); //réinitialisation du message
    	

    	notifierObservateurs(TypeNotification.DEBUT_TOUR);
    	
    	if (proie.energie > HIGH_VALUE && !(proie.getStrategy() instanceof StrategieLibre)) {
    		setDernierMessage("!!! ÉNERGIE ÉLEVÉE : Passage en MODE LIBRE (téléportation possible) !!!");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
    		proie.setStrategy(new StrategieLibre());
        } else if (proie.energie <= HIGH_VALUE && !(proie.getStrategy() instanceof StrategieManuelle)) {
            setDernierMessage("--- ÉNERGIE FAIBLE : Retour en MODE MANUEL (cases adjacentes) ---");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            proie.setStrategy(new StrategieManuelle());
        }

    	x(proie);
        
        
    	gererCollision(); 
    	if (estTermine()) {
            jeuEnCours = false;
            notifierObservateurs(TypeNotification.FIN_PARTIE);
            return;
        }
    	
    	setDernierMessage("Le Chasseur se déplace...");
    	notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        
    	x(chasseur);
    	
    	gererCollision();
    	
    	if(estTermine()){
            jeuEnCours = false;
            notifierObservateurs(TypeNotification.FIN_PARTIE);
        }
    	
    	//try { Thread.sleep(1000); } catch (InterruptedException e) {} // petite pause
    }
    
    
    public void jouer() {
    	System.out.println("=== DÉBUT DE LA PARTIE ===");
       
        while (jeuEnCours) {
            tourDeJeu();

        }
    }

    private boolean verifVictoireProie() { //retourne booléen
    	if(proie.position.x == grille.xCible && proie.position.y == grille.yCible) 
    		return true;
    	
    	return false;
    }
    
    private boolean verifVictoireChasseur() {
    	if (chasseur.position.x == grille.xCible && chasseur.position.y == grille.yCible)
            return true;
    	if(proie.mort)
    		return true;
    	return false;
    }
    
    private void gererCollision() {
        if (chasseur.position.x == proie.position.x && chasseur.position.y == proie.position.y) {
            chasseur.eliminer(proie);
        }
    }

    private boolean estTermine() {
    	if (verifVictoireProie()) 
    		return true;
    	
    	if (verifVictoireChasseur()) 
    		return true;
    	
    	
    	if (proie.mort && chasseur.mort) // les 2 sont morts
    		return true;
    	
        return false;
    } 
    
    
    public Personnage determinerVainqueur() {
    	if (verifVictoireProie()) {
            return this.proie;
        }
        
        if (verifVictoireChasseur()) {
            return this.chasseur;
        }
        
        return null;
    }
    
    public static void main(String[] args) {
        Jeu maPartie = new Jeu();
        AfficheurConsole ecran = new AfficheurConsole();
        
        // On lie l'afficheur au jeu
        maPartie.ajouterObservateur(ecran);
        
        maPartie.jouer();
    }
   
}