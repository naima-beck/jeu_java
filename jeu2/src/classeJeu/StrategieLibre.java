package classeJeu;
import java.util.Scanner;

public class StrategieLibre implements DeplacementStrategy {
    private Scanner sc = new Scanner(System.in);

    @Override
    public Case choisirProchaineCase(Personnage p, Grille g) {
        while (true) {
        	System.out.println("MODE LIBRE : Entrez x et y (ex: 5 2)");

            
            int nx = sc.nextInt();
            int ny = sc.nextInt();

            // 1. Vérifier si c'est dans la grille
            if (nx >= 0 && nx < g.largeur && ny >= 0 && ny < g.hauteur) {
                Case cible = g.cases[nx][ny];
                if (p instanceof Proie) {
                    // Note les parenthèses : ((Proie) p).aDejaVisite(cible)
                    if (!((Proie) p).aDejaVisite(cible)) {
                        return cible; 
                    } else {
                        System.out.println("ERREUR : Case déjà visitée !");
                    }
                }
                
            } else {
            	System.out.println("ERREUR : Coordonnées hors grille !");

            }
        }
    }
}
