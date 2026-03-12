# Arborescence des fichiers
```
PROJET_JAVA
│
├── src
│   └── classeJeu
│       ├── Jeu.java (Le Cœur / Modèle)
│       ├── Personnage.java (Classe mère)
│       ├── Proie.java & Chasseur.java (Classes filles)
│       ├── Grille.java & Case.java (Structure du plateau)
│       │
│       ├── Item.java (Interface)
│       ├── ItemFactory.java (Créateur d'objets)
│       ├── AdaptateurFeu.java, AdaptateurEau.java
│       ├── Element.java, Poison.java, Piege.java, ... (Les items)
│       ├── Teleporteur.java, Jumelles.java (Nouveaux items)
│       │
│       ├── Etat.java (Interface pour les altérations)
│       ├── EtatNormal.java, EtatEtourdi.java, EtatVisionEtendue.java, EtatNormal, EtatTeleportation
│       │
│       ├── Strategy.java (Interface pour l'IA)
│       ├── StrategieManuelle.java, StrategieLibre.java
│       │
│       ├── Observer.java (Interface pour l'affichage)
│       ├── AfficheurConsole.java & AfficheurInterfaceGraphique.java (Vues)
│       ├── ControleurConsole.java & ControleurInterface.java (Commandes)
│       └── TypeNotification.java (Enumération)
│
└── images (ou res)
    ├── sol_visible.png, brouillard.png, trace_pas.png (Décors)
    ├── proie.png, chasseur.png (Personnages)
    ├── bonus.png, malus.png, poison.png (Items classiques)
    └── teleporteur.png, jumelles.png (Nouveaux items)

```
