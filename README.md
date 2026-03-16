# Projet Java : Simulation du Jeu Chasseur et Proie sur Grille
Voici le lien vers le powerpoint : https://www.canva.com/design/DAHD2APcJ58/tfHej1Nr0DoCvz9MyLc3Ig/edit?utm_content=DAHD2APcJ58&utm_campaign=designshare&utm_medium=link2&utm_source=sharebutton

Ce projet s’inscrit dans le cadre des Travaux Pratiques de programmation orientée objet. Il porte sur la conception et le développement d'un jeu de simulation au tour par tour opposant deux entités : un Chasseur et une Proie, évoluant sur une grille interactive.

L'objectif est de modéliser des interactions entre des objets autonomes, une gestion d'environnement (grille, obstacles, bonus/malus) et des conditions de victoire multiples. Le projet met l'accent sur la logique algorithmique de déplacement, l'encapsulation des données et la gestion des ressources (énergie).

L’analyse du déroulement du jeu permet de mettre en évidence l'impact des éléments aléatoires sur la durée de vie des entités et l'efficacité de la stratégie de traque du chasseur face aux déplacements de la proie.

## Objectifs
Le projet vise à maîtriser les concepts fondamentaux de la Programmation Orientée Objet (POO) en Java. Il comprend la création d'une architecture modulaire (séparation Grille / Jeu / Personnages), la manipulation de tableaux à deux dimensions et la gestion des flux de contrôle. L’ensemble de ces étapes s’inscrit dans une démarche rigoureuse de développement logiciel.
Ce projet est une simulation de jeu au tour par tour développée en Java. Il met en scène un Chasseur poursuivant une Proie sur une grille interactive parsemée d'obstacles, de bonus et de malus. L'architecture repose sur des principes avancés de la Programmation Orientée Objet (POO) et des structures algorithmiques complexes.

## Fonctionnalités Clés
- Système de vision de la proie avec les 2 stratégies : Brouillard de guerre dynamique et mémorisation du parcours de la Proie.
<img width="947" height="666" alt="Brouillard de guerre" src="https://github.com/user-attachments/assets/25d7149f-3f15-47dc-a23a-315b02e56ad4" />
<img width="948" height="665" alt="Mémorisation" src="https://github.com/user-attachments/assets/5d5fe161-9d67-4b82-a5ff-921b5d11a5f5" />


- Items : le Feu désoriente durant 2 tours, l'Eau donne bonus + 5, le Téléporteur oblige le personnage à aller sur une case aléatoire, Element dont l'énergie ∈ (-10, 10), Poison provoque -1 à chaque tour, Antidote guérit du poison, le Piège tue, et les Jumelles : augmente vision de la proie en stratégie manuelle (2 tours)
<img width="948" height="665" alt="Vision omnisciente" src="https://github.com/user-attachments/assets/8c68778e-4cf4-4a86-9e93-33bd28c8a0a3" />

- États Altérés : Gestion d'états (Poison, Étourdissement, Vision étendue, Téléportation).

- IA Prédictive : Le Chasseur utilise l'algorithme de Dijkstra pour optimiser sa traque tout en évitant les dangers.

- Interface Hybride : Possibilité de basculer entre un mode Console et une Interface Graphique (Swing) à tout moment.
<img width="865" height="489" alt="Rendu" src="https://github.com/user-attachments/assets/c2d5f9d9-01f7-4117-85ae-81bce41d8b48" />
 
## Architecture Logicielle (Design Patterns)
Le projet applique le principe SOLID "Ouvert/Fermé", le système est ouvert aux extensions (nouveaux items/états) mais fermé aux modifications du cœur métier. Il respecte aussi le modèle-Vue-Contrôleur (MVC) : Séparation stricte entre la logique de jeu, l'affichage et la gestion des entrées.

Design patterns : 
- Observer : Permet la synchronisation en temps réel de plusieurs vues (Console et GUI) sans couplage fort.

- Strategy : Basculement dynamique entre le contrôle manuel avec le clavier et en cliquant sur la grille (mémorisation des cases déjà visitées) .

- State : Gestion des altérations de comportement des personnages via des objets d'états interchangeables.

- Factory : Centralisation de la création des items avec une gestion probabiliste de la distribution sur la grille.

- Adapter : Intégration d'éléments externes (Feu, Eau) dans le système d'items de la grille.

## Algorithmique : Traque et Théorie des Graphes
Le déplacement du Chasseur ne repose pas sur un simple rapprochement géométrique, mais sur une analyse de coût :

- Modélisation en Graphe Pondéré : La grille est traitée comme un graphe où chaque arête possède un poids orienté.

- Évaluation des Coûts : * Case normale : 10, Danger (Poison/Piège) : 50 (ou 1000 si énergie critique), Téléporteur: 5

- Pathfinding : Utilisation de l'algorithme de Dijkstra avec PriorityQueue pour trouver le chemin au coût cumulé minimal. 

## Technologies utilisés
- Langage : Java 21 (LTS)

- IDE : Eclipse

- Bibliothèques principales :

java.util (Random, List, Scanner, PriorityQueue, Random)

java.io

java.awt et javax.swing

## Compilation et Exécution
Le projet ne nécessite pas de données externes. Il suffit de compiler les fichiers sources situés dans le package classeJeu.

```
jeu2/
├── .settings
├── bin/                   
├── src/
│   └── classeJeu
│       ├── Jeu.java (Le Cœur / Modèle)
│       ├── Personnage.java (Classe mère)
│       ├── Proie.java & Chasseur.java (Classes filles)
│       ├── Grille.java & Case.java (Structure du plateau)
│       │
│       ├── Item.java (Interface)
│       ├── ItemFactory.java (Créateur d'objets)
│       ├── AdaptateurFeu.java, AdaptateurEau.java
│       ├── Element.java, Poison.java, Piege.java, Teleporteur.java, Jumelles.java ... (Les items)
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
└── images/
    ├── sol_visible.png, brouillard.png, trace_pas.png (Décors)
    ├── proie.png, chasseur.png (Personnages)
    └── bonus.png, malus.png, poison.png, teleporteur.png, jumelles.png ... (Items)
├──.DS_Store
├──.classpath
├──.project
├──classpath
└──project
```

## Composantes du Jeu analysées
L’étude s’appuie sur trois ensembles de classes principaux : L'Environnement (Grille) : Génération d'un plateau (N  M) avec placement d'une cible fixe et gestion des voisins.

Le Système de Bonus/Malus : Implémentation d'une distribution probabiliste d'objets (Element).
Bonus : Gain d'énergie (+10).
Malus : Perte d'énergie (-10).

La Dynamique des Tours : Gestion de l'épuisement progressif de l'énergie. Le jeu se termine si le Chasseur capture la Proie, si la Cible est atteinte, ou par épuisement total de l'énergie (Match Nul).

## Auteurs
- [@naima-beck](https://www.github.com/naima-beck)
- [@axellelepoul-ctrl](https://www.github.com/axellelepoul-ctrl)

Cy Tech - Sciences-Po Saint-Germain-En-Laye - [2025/2026]

## Licence
Ce projet est réalisé dans un cadre académique. Il est sous licence CC BY-NC-SA 4.0
