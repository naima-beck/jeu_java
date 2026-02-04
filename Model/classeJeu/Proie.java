package classeJeu;
import java.io.*;
import java.util.*;

public class Proie extends Personnage {

   
	public Proie(int energieInitiale, Case positionInitiale) {
	    super(energieInitiale, positionInitiale);
	}

    public Case prochaineCellule(List<Case> cases) {
    	if (cases == null || cases.isEmpty()) return null; //On vérifie si le personnage est bien sur une case
    	
    	Scanner sc = new Scanner(System.in); //On prend en compte ce que le joueur tape
    	
    	while (true) {
            System.out.println("Déplacement (Z=haut, S=bas, Q=gauche, D=droite) : "); //C'est dur de faire avec les flèches alors on utilise ces touches pour se déplacer
            String entree = sc.next().toUpperCase(); //Lis ce que le joueur entre et le met en majuscule
            char touche = entree.charAt(0); //On change notre string en char

            int nx = position.x;
            int ny = position.y;

            switch (touche) {
                case 'Z': ny = position.y - 1; break; // haut, on break à chaque fois la boucle si on clique sur une des touches
                case 'S': ny = position.y + 1; break; // bas
                case 'Q': nx = position.x - 1; break; // gauche
                case 'D': nx = position.x + 1; break; // droite
                default:
                    System.out.println("Touche invalide. Utilise Z/S/Q/D."); //Si jamais on clique sur une autre touche ça redémarre
                    continue; // redemande quelle touche on veut utiliser
            }

            //Cherche la case voisine correspondante dans la liste "cases"
            for (Case c : cases) {
                if (c.x == nx && c.y == ny) { //On vérifie si cette nouvelle case est dans la liste des cases voisines
                    return c; //On a trouvé la bonne case
                }
            }

            //Si on arrive ici : la direction est valide MAIS pas de voisin (bord de grille)
            System.out.println("Impossible d'aller dans cette direction (bord ou mur). Réessaie.");
        }
    	

    }

    public void seDéplacer(Grille grille) {
    	List<Case> voisine = grille.getPlusProcheVoisin(position); //On définit les voisins
    	Case prochaine = prochaineCellule(voisine); //On appelle la méthode qui permet de choisir la prochain case
    	super.seDeplacer(prochaine); //On appelle le déplacement une fois qu'on a choisit la nouvelle case    	
    }

}
