package classeJeu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

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
    	
    	Map<String, Integer> maConfig = new HashMap<>();
    	maConfig.put("bonus", 10);
    	maConfig.put("malus", 8);
    	maConfig.put("teleporteur", 10); 
    	maConfig.put("jumelles", 10);    
    	maConfig.put("poison", 5);
    	maConfig.put("antidote", 5);
    	maConfig.put("feu", 5);
    	maConfig.put("eau", 5);
    	maConfig.put("piege", 5);
    	
    	grille.placerItems(maConfig);
    }
    
    
    public void x(Personnage p) {
        if (p.mort) return;

        // Si le personnage est marqué pour téléportation
        if (p.getEtat() instanceof EtatTeleportation) {
            p.position = grille.getRandomCaseExcluding(p.position);
            
            p.setEtat(new EtatNormal());
            setDernierMessage(p.nom + " a été téléporté !");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            return; // Fin du tour pour ce perso
        }

        // Mouvement normal
        String msgMouvement = (p instanceof Proie) ? 
            ((Proie) p).seDeplacer(grille, this) : 
            ((Chasseur) p).seDeplacer(grille, proie); 

        if (msgMouvement != null) {
            setDernierMessage(msgMouvement);
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        }

        if (p.mort) return;

        String msgEtat = p.appliquerEtat(); 
        if (msgEtat != null) {
            setDernierMessage(msgEtat);
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        }
    }
    
    public void tourDeJeu(Case cibleClavier) {
    	if (cibleClavier == null || !jeuEnCours) return;
    	
    	nbTour ++;
    	setDernierMessage(""); //réinitialisation du message
    	

    	verifierChangementStrategie();

        // 2. Mouvement de la Proie (Clavier ou Console, c'est la même chose ici)
    	if (proie.getEtat() instanceof EtatTeleportation) {
            proie.position = grille.getRandomCaseExcluding(proie.position);
            proie.setEtat(new EtatNormal());
            setDernierMessage(proie.nom + " a été téléporté !");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            // On ne fait pas le mouvement du clic, on passe direct à la suite
        } 
        else {
            // Mouvement normal de la Proie via ton clic
            String msg = proie.seDeplacer(cibleClavier); 
            if (msg != null) {
                setDernierMessage(msg);
                notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            }
        }
        
        String msgEtat = proie.appliquerEtat();
        if (msgEtat != null) {
            setDernierMessage(msgEtat);
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        }
        
        gererCollision(); 
        
        // 3. Tour du Chasseur
        if (!estTermine() && chasseur.estVivant()) {
            setDernierMessage("Le Chasseur se déplace...");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            x(chasseur);
            gererCollision();
        }
        
        if(chasseur.mort) {
        	setDernierMessage("Le Chasseur est mort...");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
        }
        

        // 4. Notifications de fin ou nouveau tour
        if (estTermine()) {
            jeuEnCours = false;
            notifierObservateurs(TypeNotification.FIN_PARTIE);
        } else {
            notifierObservateurs(TypeNotification.DEBUT_TOUR);
        }
    }
    
    private void verifierChangementStrategie() {
        if (proie.energie > HIGH_VALUE && !(proie.getStrategy() instanceof StrategieLibre)) {
            setDernierMessage("!!! ÉNERGIE ÉLEVÉE : Passage en MODE LIBRE !!!");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            proie.setStrategy(new StrategieLibre());
        } else if (proie.energie <= HIGH_VALUE && !(proie.getStrategy() instanceof StrategieManuelle)) {
            setDernierMessage("--- ÉNERGIE FAIBLE : Retour en MODE MANUEL ---");
            notifierObservateurs(TypeNotification.MESSAGE_SEUL);
            proie.setStrategy(new StrategieManuelle());
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
    
    public String genererBilanFin() {
        StringBuilder bilan = new StringBuilder();
        bilan.append("\n=== FIN DE PARTIE ===\n");
        
        Personnage gagnant = this.determinerVainqueur();
        
        if (gagnant instanceof Proie) {
            bilan.append("VICTOIRE ! La proie s'est échappée et a atteint la cible !");
        } else if (gagnant instanceof Chasseur) {
            if (proie.mort) {
                bilan.append("GAME OVER ! Le chasseur a gagné : La proie est morte.");
            } else {
                bilan.append("GAME OVER ! Le chasseur a gagné : Il a atteint la cible.");
            }
        } else {
            bilan.append("ÉGALITÉ : Personne n'a survécu...");
        }
        
        return bilan.toString();
    }
    
    public static ControleurConsole JeuConsole(Jeu j) {
    	AfficheurConsole console = new AfficheurConsole();
        j.ajouterObservateur(console);
        
        ControleurConsole ctrlConsole = new ControleurConsole(j);
        return ctrlConsole;
        
    	
    }
    
    
    
    
    public static void JeuInterface(Jeu j) {
    	
        AfficheurInterfaceGraphique gui = new AfficheurInterfaceGraphique(j);
        
        j.ajouterObservateur(gui);

        ControleurInterface ctrlGraphique = new ControleurInterface(j);
        
        gui.addKeyListener(ctrlGraphique);
    }
    
    public static void main(String[] args) {
        lancerApplication();
    }

    public static void lancerApplication() {
        Jeu maPartie = new Jeu();
        
        // Fenêtre de choix au lancement
        String[] options = {"Interface Graphique", "Console"};
        int choix = JOptionPane.showOptionDialog(null, 
            "Comment voulez-vous jouer ?", 
            "Démarrage du Jeu", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, options, options[0]);

        if (choix == 0) { // Interface Graphique
            JeuInterface(maPartie);
            maPartie.notifierObservateurs(TypeNotification.DEBUT_PARTIE);
            maPartie.notifierObservateurs(TypeNotification.DEBUT_TOUR);
        } 
        else if (choix == 1) { // Console
            ControleurConsole monControleur = JeuConsole(maPartie);
            maPartie.notifierObservateurs(TypeNotification.DEBUT_PARTIE);
            maPartie.notifierObservateurs(TypeNotification.DEBUT_TOUR);
            monControleur.lancerBoucle();
        }
    }
   
}