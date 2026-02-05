package classeJeu;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== 🦁 JEU : CHASSEUR vs PROIE 🐰 ===");

        // --- 1. INITIALISATION ---
        
        // Création de la grille (10x10)
        Grille maGrille = new Grille(10, 10);
        maGrille.placerBonusMalus(10, 20); // 10% bonus, 20% malus

        System.out.println("🎯 Cible placée en : [" + maGrille.xCible + ", " + maGrille.yCible + "]");

        // Placement des personnages aux coins opposés
        // Proie en bas à gauche (pour l'exemple, ou adapte selon ta logique)
        Case caseDepartProie = maGrille.cases[(maGrille.largeur - 1)/2][(maGrille.hauteur - 1)/2];
        // Chasseur en haut à gauche
        Case caseDepartChasseur = maGrille.cases[0][0];
        
        // Création des objets
        Proie maProie = new Proie(50, caseDepartProie); // 50 énergie
        Chasseur monChasseur = new Chasseur(50, caseDepartChasseur); // Énergie du chasseur (si besoin)
        
        // --- AFFICHAGE DE DÉPART ---
        System.out.println("\n--- DÉBUT DE LA PARTIE ---");
        maGrille.afficherGrille(maProie, monChasseur);

        // --- 2. BOUCLE DE JEU ---
        int tour = 0;
        boolean gagne = false; // Pour savoir à la fin si on a gagné ou perdu
        
        // Le jeu continue tant que :
        // 1. La proie a de l'énergie
        // 2. La proie n'est pas morte (tuée par le chasseur)
        // 3. On n'a pas encore gagné
        while (maProie.energie > 0 && !maProie.mort && !gagne) { 
            tour++;
            System.out.println("\n>>> TOUR " + tour + " (Z,Q,S,D + Entrée)");

            // --- A. TOUR DE LA PROIE ---
            maProie.seDeplacer(maGrille);

            // Vérification VICTOIRE immédiate (avant que le chasseur ne bouge)
            if (maProie.position.x == maGrille.xCible && maProie.position.y == maGrille.yCible) {
                gagne = true;
                break; // On sort de la boucle immédiatement
            }

            // --- B. TOUR DU CHASSEUR ---
            // Le chasseur observe la grille et la proie pour bouger
            System.out.println("🤖 Le Chasseur se déplace...");
            monChasseur.seDeplacer(maGrille, maProie);

            // Vérification DÉFAITE immédiate (Capture)
            // Si le chasseur est sur la même case que la proie (ou si maProie.mort est true)
            if (maProie.position == monChasseur.position || maProie.mort) {
                System.out.println("\n😱 LE CHASSEUR T'A ATTRAPÉ !");
                maProie.mort = true; // On s'assure que le statut est mort
                break; // Fin du jeu
            }

            // --- C. BILAN DU TOUR ---
            System.out.println("------------------------------------------");
            System.out.println("📍 Bilan Tour " + tour + " :");
            System.out.println("   🐰 Proie : [" + maProie.position.x + ", " + maProie.position.y + "] | ⚡ Énergie : " + maProie.energie);
            System.out.println("   🤖 Chasseur : [" + monChasseur.position.x + ", " + monChasseur.position.y + "]");
            System.out.println("------------------------------------------");

            // Si la partie continue, on réaffiche la grille
            if (maProie.energie > 0 && !maProie.mort) {
                maGrille.afficherGrille(maProie, monChasseur);
            }
        }

        // --- 3. FIN DE PARTIE ---
        System.out.println("\n==========================================");
        if (gagne) {
            // Affichage final victorieux
            maGrille.afficherGrille(maProie, monChasseur);
            System.out.println("🎉 BRAVO ! VICTOIRE ! 🎉");
            System.out.println("Tu as atteint la sortie en " + tour + " tours.");
            System.out.println("Énergie restante : " + maProie.energie);
        } else {
            // Affichage final défaite
            if (maProie.mort) {
                System.out.println("☠️ GAME OVER : Tu as été dévoré par le Chasseur. ☠️");
            } else {
                System.out.println("☠️ GAME OVER : Mort de fatigue (Énergie épuisée). ☠️");
            }
        }
        System.out.println("==========================================");
    }
}