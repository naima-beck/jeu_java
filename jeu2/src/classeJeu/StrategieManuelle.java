package classeJeu;
import java.util.List;
import java.util.Scanner;

public class StrategieManuelle implements DeplacementStrategy {
    private Scanner sc = new Scanner(System.in);

    @Override
    public Case choisirProchaineCase(Personnage p, Grille g) {
    	System.out.println("MODE MANUEL : Entrez Z, Q, S ou D (Z=haut, S=bas, Q=gauche, D=droite) : ");    	
        String choix = sc.next().toUpperCase();
        int x = p.position.x;
        int y = p.position.y;

        if (choix.equals("Z")) y--;
        else if (choix.equals("S")) y++;
        else if (choix.equals("Q")) x--;
        else if (choix.equals("D")) x++;

        // On vérifie si la case est dans la grille
        if (x >= 0 && x < g.largeur && y >= 0 && y < g.hauteur) {
            return g.cases[x][y];
        }
        return p.position; // Reste sur place si hors grille
    }
    
   

}
