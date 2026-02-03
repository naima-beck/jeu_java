package classeJeu;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Démarrage du Jeu Chasseur & Bonhomme ===");

        // 1. CRÉATION DE LA GRILLE
        // On crée une grille de 10 lignes x 10 colonnes.
        // (Le '0' est la densité, on ne s'en sert pas encore mais le constructeur le demande)
        Grille maGrille = new Grille(10, 10);
        

        // 2. VÉRIFICATION DE LA CIBLE
        // Attention : vérifie bien si tu as mis 'xcible' ou 'xCible' dans ta classe Grille !
        System.out.println("Cible placée en : " + maGrille.xCible + ", " + maGrille.yCible);

        // 3. AFFICHAGE DE LA GRILLE VIDE
        System.out.println("\n--- Grille Initiale (Vide) ---");
        maGrille.afficherGrille();

        // 4. TEST : AJOUT De BONUS ET De MALUS
        maGrille.placerBonusMalus(10, 20);

        // 5. AFFICHAGE APRÈS AJOUT
        System.out.println("\n--- Grille avec Bonus et Malus ---");
        maGrille.afficherGrille();
        
        System.out.println("\nTest terminé avec succès !");
    }
}
