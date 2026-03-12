package classeJeu;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class ControleurInterface extends KeyAdapter {
    private Jeu jeu;

    public ControleurInterface(Jeu jeu) {
        this.jeu = jeu;
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
        if (!jeu.jeuEnCours) return;

        Case cible = null;

        //Si étourdie : on ignore la touche et on prend un voisin au hasard
        if (jeu.proie.getEtat() instanceof EtatEtourdi) {
            List<Case> voisins = jeu.grille.getPlusProcheVoisin(jeu.proie.position);
            cible = voisins.get(new java.util.Random().nextInt(voisins.size()));
        } 
        //Sinon on calcule selon la touche pressée
        else {
            int nx = jeu.proie.position.x;
            int ny = jeu.proie.position.y;

            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:    ny--; break;
                case KeyEvent.VK_DOWN:  ny++; break;
                case KeyEvent.VK_LEFT:  nx--; break;
                case KeyEvent.VK_RIGHT: nx++; break;
                default: return; 
            }

            if (nx >= 0 && nx < jeu.grille.getLargeur() && ny >= 0 && ny < jeu.grille.getHauteur()) {
                cible = jeu.grille.getCases()[nx][ny];
            }
        }

        //On envoie la cible (choisie ou aléatoire) au jeu
        if (cible != null) {
            jeu.tourDeJeu(cible); 
        }
    }
}
