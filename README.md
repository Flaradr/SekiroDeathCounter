# SekiroDeathCounter

## Installation

Télécharger et installer la jdk java 21 : https://www.oracle.com/fr/java/technologies/downloads/#jdk21-windows
Lancer le fichier SekiroDeathCounter[Version].jar

## Utilisation

Sélectionner le jeu.

![Sélection du jeu](img/GameSelection.PNG)

Sélectionner le fichier dans lequel sera sauvegardé le nombre de mort.

![Choix du fichier de sortie](img/OutputFile.PNG)

Choisir le fichier de sauvegarde .sl2 associé.

![Choix du fichier de sauvegarde](img/SaveFileChoice.PNG)

> [!TIP]
> Les fichiers de sauvegarde des jeux FromSoftware se trouvent généralement dans le répertoire
**C:\Users\\_NomUtilisateur_\AppData\Roaming**

#### Options
![Choix du fichier de sortie](img/Options.PNG)

Il est possible de modifier le compteur de mort.

 - `Mise à 0 du compteur` permet de réinitialiser le compteur à 0.
 - `Reset` permet de supprimer les modifications et de retrouver le nombre de mort présent dans le fichier.
 - Il est aussi possible d'ajouter N morts.


## Evolutions
- [x] Automatisation du compteur de mort
- [x] Afficher les informations du personnage (nom, level, temps de jeu,...) dans la section "Informations sur la sauvegarde"
- [ ] Permettre à l'utilisateur de choisir un slot de sauvegarde
- [ ] Implémenter solution pour Dark Souls 1, Dark Souls 2 et Dark Souls 3