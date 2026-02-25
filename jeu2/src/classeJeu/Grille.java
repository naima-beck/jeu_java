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
        
        // cible en bas à droite de la grille
        this.xCible = largeur-1;
        this.yCible = hauteur-1;
    }

    
    public void placerBonusMalus(int pBonus, int pMalus, int pFeu, int pEau, int pPoison, int pAntidote, int pPiege) {
        
        // 1. Sécurité : On vérifie que le total ne dépasse pas 100%
    	int total = pBonus + pMalus + pFeu + pEau + pPoison + pAntidote + pPiege;
        if (total > 100) {
            System.out.println("Erreur : Total > 100%. Je remets des valeurs par défaut.");
            // On répartit un peu au hasard pour que ça fasse moins de 100
            pBonus = 10; pMalus = 10; pFeu = 5; pEau = 5; pPoison = 5; pAntidote = 5; pPiege = 2; 
        }

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                
                // On ignore départ et arrivée
                if ((x == 0 && y == 0) || (x == xCible && y == yCible)) { 
                    continue; 
                }

                int tirage = random.nextInt(100); // 0 à 99

                // --- LOGIQUE CUMULATIVE ---
                
                // 1. Zone Bonus (ex: 0 à 9)
                if (tirage < pBonus) {
                    cases[x][y].setItem(new Element(random.nextInt(10) + 1)); // valeur entre 1 et 10
                } 
                // 2. Zone Malus (ex: 10 à 29) -> On ajoute pBonus pour décaler le seuil
                else if (tirage < (pBonus + pMalus)) {
                    cases[x][y].setItem(new Element(-(random.nextInt(10) + 1))); // valeur entre -1 et -10
                }
                // 3. Zone Feu (ex: 30 à 39)
                else if (tirage < (pBonus + pMalus + pFeu)) {
                    cases[x][y].setItem(new AdaptateurFeu(new Feu()));
                }
                // 4. Zone Eau (ex: 40 à 49)
                else if (tirage < (pBonus + pMalus + pFeu + pEau)) {
                    cases[x][y].setItem(new AdaptateurEau(new Eau()));
                }
                
                // 5. Zone Poison 
                else if (tirage < (pBonus + pMalus + pFeu + pEau + pPoison)) {
                    cases[x][y].setItem(new Poison());
                }
                // 6. Zone Antidote 
                else if (tirage < (pBonus + pMalus + pFeu + pEau + pPoison + pAntidote)) {
                    cases[x][y].setItem(new Antidote());
                }
                // 7. Zone Piège Mortel 
                else if (tirage < (pBonus + pMalus + pFeu + pEau + pPoison + pAntidote + pPiege)) {
                    cases[x][y].setItem(new Piege());
                }
                
                // Le reste (50 à 99) reste vide.
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

    
    
    public void afficherGrille(Proie p,Chasseur s) {
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
                        System.out.print("[~]"); // ~ pour représenter la fumée du poison
                    } 
                    else if (objet instanceof Antidote) {
                        System.out.print("[A]"); // A pour Antidote
                    } 
                    else if (objet instanceof Piege) {
                        System.out.print("[X]"); // X pour un danger mortel
                    } 
                    else if (objet instanceof AdaptateurFeu) {
                        System.out.print("[F]"); // F pour Feu
                    } 
                    else if (objet instanceof AdaptateurEau) {
                        System.out.print("[E]"); // E pour Eau
                    } 
                    else if (objet instanceof Element) {
                        // Pour les Elements classiques, on regarde l'énergie
                        if (objet.getEnergie() > 0) {
                            System.out.print("[+]"); // Bonus classique
                        } else {
                            System.out.print("[-]"); // Malus classique
                        }
                    }
            	}
                  else if (x == xCible && y == yCible) {
                    System.out.print("[O]"); // cible
                } else {
                    System.out.print("[ ]"); // case vide
                }
            }
            System.out.println(); 
        }
    }
    
}