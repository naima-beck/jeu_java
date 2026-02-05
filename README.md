# Projet Java : Simulation du Jeu Chasseur et Proie sur Grille
Ce projet s’inscrit dans le cadre des Travaux Pratiques de programmation orientée objet. Il porte sur la conception et le développement d'un jeu de simulation au tour par tour opposant deux entités : un Chasseur et une Proie, évoluant sur une grille interactive.

L'objectif est de modéliser des interactions entre des objets autonomes, une gestion d'environnement (grille, obstacles, bonus/malus) et des conditions de victoire multiples. Le projet met l'accent sur la logique algorithmique de déplacement, l'encapsulation des données et la gestion des ressources (énergie).

L’analyse du déroulement du jeu permet de mettre en évidence l'impact des éléments aléatoires sur la durée de vie des entités et l'efficacité de la stratégie de traque du chasseur face aux déplacements de la proie.

## Objectifs
Le projet vise à maîtriser les concepts fondamentaux de la Programmation Orientée Objet (POO) en Java. Il comprend la création d'une architecture modulaire (séparation Grille / Jeu / Personnages), la manipulation de tableaux à deux dimensions et la gestion des flux de contrôle. L’ensemble de ces étapes s’inscrit dans une démarche rigoureuse de développement logiciel.

## Technologies utilisés
- Langage : Java

- IDE : Eclipse

- Bibliothèques principales :

java.util (Random, List, Scanner)

java.io

## Compilation et Exécution
Le projet ne nécessite pas de données externes. Il suffit de compiler les fichiers sources situés dans le package classeJeu.

```bash
projet-chasseur-proie/
├── .settings
├── bin/                    # Fichiers compilés
├── src/
│   └── classeJeu/          # Package principal
│       ├── Main.java       # Point d'entrée
│       ├── Jeu.java        # Moteur du jeu
│       ├── Grille.java     # Gestion du terrain
│       ├── Case.java       # Unité élémentaire
│       ├── Element.java    # Bonus et Malus
│       ├── Chasseur.java   # Logique du Chasseur
│       └── Proie.java      # Logique de la Proie
├──.DS_Store
├──.classpath
├──.project
├──README.md
├──classpath
└──project
```

## Composantes du Jeu analysées
L’étude s’appuie sur trois ensembles de classes principaux :L'Environnement (Grille) : Génération d'un plateau (N  M) avec placement d'une cible fixe et gestion des voisins.

Le Système de Bonus/Malus : Implémentation d'une distribution probabiliste d'objets (Element).
Bonus : Gain d'énergie (+10).
Malus : Perte d'énergie (-10).

La Dynamique des Tours : Gestion de l'épuisement progressif de l'énergie. Le jeu se termine si le Chasseur capture la Proie, si la Cible est atteinte, ou par épuisement total de l'énergie (Match Nul).

## Auteurs
- [@naima-beck](https://www.github.com/naima-beck)
- [@axellelepoul-ctrl](https://www.github.com/axellelepoul-ctrl)

Cy Tech - Sciences-Po Saint-Germain-En-Laye - [2025/2026]

## License
Ce projet est réalisé dans un cadre académique. Il est sous licence CC BY-NC-SA 4.0
