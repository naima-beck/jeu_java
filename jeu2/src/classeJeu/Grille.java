package classeJeu;
import java.util.*;


public class Grille {
	
	private int hauteur;

	private int largeur;
    
    private Case[][] cases;

    private int xCible;

    private int yCible;

    private Random random = new Random();
    
    public int getLargeur() { return largeur; }
    public int getHauteur() { return hauteur; }
    public Case[][] getCases() { return cases; }
    public int getXCible() { return xCible; }
    public int getYCible() { return yCible; }
    
    public Grille(int hauteur, int largeur) {
    	this.hauteur=hauteur;
    	this.largeur=largeur;
    	this.cases = new Case[largeur][hauteur];
    	
    	
    	// Initialisation du tableau avec des cases vides
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                cases[x][y] = new Case(x, y);
            }
        }
        
        // cible placée aléatoirement
        this.xCible = random.nextInt(largeur);
        this.yCible = random.nextInt(hauteur);
    }

    
    public void placerItems(Map<String, Integer> configuration) {
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                // Sécurité : Ne pas poser d'item sur la Proie ou la Cible
                if ((x == (largeur - 1)/2 && y == (hauteur - 1)/2) || (x == xCible && y == yCible)) { 
                    continue; 
                }

                int tirage = random.nextInt(100);
                int seuilCumule = 0;

                // On parcourt la map des probabilités
                for (Map.Entry<String, Integer> entry : configuration.entrySet()) {
                    seuilCumule += entry.getValue();
                    if (tirage < seuilCumule) {
                        cases[x][y].setItem(ItemFactory.creerItem(entry.getKey()));
                        break; // Item placé, on passe à la case suivante
                    }
                }
            }
        }
    }
    
    public Case getCible() {
        return this.cases[this.xCible][this.yCible];
    }
    
    
    private boolean estDansLaGrille(int x, int y) {
        return (x >= 0 && x < largeur && y >= 0 && y < hauteur); // renvoie un booléen
    }


    public List<Case> getPlusProcheVoisin(Case c) { // cette méthode retourne la liste des cases voisines d'une case au sein d'une grille
        List<Case> voisins = new ArrayList<>();
        
        // Coordonnées relatives (Haut, Bas, Gauche, Droite)
        int[] dx = {0, 0, -1, 1}; // dx = décalage en x
        int[] dy = {-1, 1, 0, 0}; // dy = décalage en y

        for (int i = 0; i < 4; i++) {
            int prochainX = c.x + dx[i]; // on teste les 4 possibilités 
            int prochainY = c.y + dy[i];

            
            if (estDansLaGrille(prochainX, prochainY)) { // si dans la grille => on ajoute la case dans la liste des voisins
                voisins.add(cases[prochainX][prochainY]);
            }
        }
        
        return voisins;
    }
    
    public List<Case> getVoisinsRayon(Case centre, int rayon) {
        List<Case> voisins = new ArrayList<>();
        for (int dx = -rayon; dx <= rayon; dx++) {
            for (int dy = -rayon; dy <= rayon; dy++) {
                int nx = centre.x + dx;
                int ny = centre.y + dy;
                if (estDansLaGrille(nx, ny)) {
                    voisins.add(cases[nx][ny]);
                }
            }
        }
        return voisins;
    }
    
    public Case getRandomCaseExcluding(Case caseAExclure) {
        int nx, ny;
        do {
            nx = random.nextInt(largeur);
            ny = random.nextInt(hauteur);
        } while (nx == caseAExclure.x && ny == caseAExclure.y);
        
        return cases[nx][ny];
    }

    public void afficherGrille(Proie p, Chasseur s) {

    	List<Case> visibles;
        if (p.getEtat() instanceof EtatVisionEtendue) {
            // On utilise le rayon de 3 cases si les jumelles sont actives
            visibles = getVoisinsRayon(p.position, 2);
        } else {
            // Sinon vision classique (adjacentes)
            visibles = getPlusProcheVoisin(p.position);
        }

        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                Case courante = cases[x][y];

                
                if (p.position == courante) {
                    System.out.print("[P]");
                } 
                
                /*
                else if (p.aDejaVisite(courante)) {
                	System.out.print("[.]"); //affiche les cases déjà visitées
                } 
                */
            
                else if (visibles.contains(courante)) {
                	
                    if (courante.contientItem()) {
                        Item objet = courante.item;
                   
                        if (objet.getEnergie() > 0) {
                            System.out.print("[+]"); 
                        } else {
                            System.out.print("[-]"); 
                        }
                    } 
                    else {
                        System.out.print("[ ]"); 
                    }
                }
                
                else if (s.position == courante) {
                    System.out.print("[C]");
                } 
                
                else {
                    System.out.print("[ ]"); 
                }
                
            }
            System.out.println(); 
        }
    }
    
    public void afficherGrilleHistorique(Proie p, Chasseur s) {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                Case courante = cases[x][y];

              
                if (p.position == courante) {
                    System.out.print("[P]");
                } 
                else if (s.position == courante) {
                    System.out.print("[C]");
                } 
       
                else if (p.aDejaVisite(courante)) {
                    if (courante.contientItem()) {
                        Item objet = courante.item;
                        if (objet.getEnergie() > 0) {
                            System.out.print("[+]"); 
                        } else {
                            System.out.print("[-]");
                        }
                    } else {
                        System.out.print("[ ]"); 
                    }
                } 
               
                else {
                    System.out.print("[.]"); 
                }
            }
            System.out.println(); 
        }
    }
    
    public void afficherGrilleOmniscient(Proie p,Chasseur s) {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
            	if (p != null && p.position.x == x && p.position.y == y) {
                    System.out.print("[P]"); 
                }
            	else if (s != null && s.position.x == x && s.position.y == y) {
                    System.out.print("[C]"); 
                }
            	else if (cases[x][y].contientItem()) {
                    Item objet = cases[x][y].item;
                    
                    if (objet instanceof Poison) {
                        System.out.print("[~]"); 
                    } 
                    else if (objet instanceof Antidote) {
                        System.out.print("[A]"); 
                    } 
                    else if (objet instanceof Piege) {
                        System.out.print("[X]"); 
                    } 
                    else if (objet instanceof AdaptateurFeu) {
                        System.out.print("[F]"); 
                    } 
                    else if (objet instanceof AdaptateurEau) {
                        System.out.print("[E]"); 
                    } 
                    else if (objet instanceof Teleporteur) {
                        System.out.print("[T]"); // T pour Téléporteur
                    } 
                    else if (objet instanceof Jumelles) {
                        System.out.print("[J]"); // J pour Jumelles
                    }
                    else if (objet instanceof Element) {
                        
                        if (objet.getEnergie() > 0) {
                            System.out.print("[+]"); 
                        } else {
                            System.out.print("[-]"); 
                        }
                    }
            	}
                  else if (x == xCible && y == yCible) {
                    System.out.print("[O]"); 
                } else {
                    System.out.print("[ ]"); // case vide
                }
            }
            System.out.println(); 
        }
    }
    
}