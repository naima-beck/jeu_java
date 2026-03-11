package classeJeu;
import java.util.*;


public class Grille {
	
	public int hauteur;

    public int largeur;
    
    public Case[][] cases;

    public int xCible;

    public int yCible;

    private Random random = new Random();
    
    
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

    
    public void placerBonusMalus(int pBonus, int pMalus, int pFeu, int pEau, int pPoison, int pAntidote, int pPiege) {
        
        // Sécurité : On vérifie que le total ne dépasse pas 100%
    	int total = pBonus + pMalus + pFeu + pEau + pPoison + pAntidote + pPiege;
        if (total > 100) {
            System.out.println("Erreur : Total > 100%. Je remets des valeurs par défaut.");
            // On répartit un peu au hasard pour que ça fasse moins de 100
            pBonus = 10; pMalus = 10; pFeu = 5; pEau = 5; pPoison = 5; pAntidote = 5; pPiege = 2; 
        }

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                if ((x == (largeur - 1)/2 && y == (hauteur - 1)/2) || (x == xCible && y == yCible)) { 
                    continue; 
                }

                int tirage = random.nextInt(100);

                if (tirage < pBonus) {
                    cases[x][y].setItem(ItemFactory.creerItem("bonus"));
                } 
                else if (tirage < (pBonus + pMalus)) {
                    cases[x][y].setItem(ItemFactory.creerItem("malus"));
                }
                else if (tirage < (pBonus + pMalus + pFeu)) {
                    cases[x][y].setItem(ItemFactory.creerItem("feu"));
                }
                else if (tirage < (pBonus + pMalus + pFeu + pEau)) {
                    cases[x][y].setItem(ItemFactory.creerItem("eau"));
                }
                else if (tirage < (pBonus + pMalus + pFeu + pEau + pPoison)) {
                    cases[x][y].setItem(ItemFactory.creerItem("poison"));
                }
                else if (tirage < (pBonus + pMalus + pFeu + pEau + pPoison + pAntidote)) {
                    cases[x][y].setItem(ItemFactory.creerItem("antidote"));
                }
                else if (tirage < (pBonus + pMalus + pFeu + pEau + pPoison + pAntidote + pPiege)) {
                    cases[x][y].setItem(ItemFactory.creerItem("piege"));
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

    public void afficherGrille(Proie p, Chasseur s) {

        List<Case> visibles = getPlusProcheVoisin(p.position);

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