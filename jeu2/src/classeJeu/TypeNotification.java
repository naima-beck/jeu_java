package classeJeu;

public enum TypeNotification {
    DEBUT_TOUR,    // On affiche la grille pour que le joueur choisisse son coup
    MESSAGE_SEUL,  // On affiche juste un petit texte (ex: "Tu es empoisonné")
    FIN_PARTIE     // On affiche le résultat final
}