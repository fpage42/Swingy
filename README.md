# Swingy

Projet realisé en 2020 dans le cadre de l'école 42. Le but est de créer un petit jeu RPG en full text.

Le jeu est composé de deux parties:
  - Une partie console
  - Une partie fenettre via le framework swing
  
Le sujet demande qu'il soit réalisé en java 7 et avec une structure MVC.

Dans le cadre d'un bonus j'ai concue le jeu afin de pouvoir changer entre le mode console et fenêtre a tout moment, j'ai également sauvegardé les données dans une base SQLite

## Details

A l'ouverture le joueur peut sélectionner ou créer un héros, la création demande un nom et héros ainsi que la sélection d'une des trois classes disponibles.

Après sélection le joueur peut accéder a une page de résumé montrant le niveau du héros, ses caractéristiques ainsi que son équipement et lui propose de lancer une partie.

Une partie se résume à apparaitre sur une carte dont la taille dépend du niveau du héros, sur cette carte sont aléatoirement positionné plusieurs éléments:
  - Des coffres contenant des objets à equiper (casque/armure/arme)
  - Des fontaines de soin
  - Des montagnes infranchissables
  - Des enemies non visible sur la carte declenchant un combat a leurs rencontre
  
Le déroulé d'un combat est simple, à chaque tour le joueur choisis de combattre ou de fuir.
Si il choisis de fuir, il a environ une chance sur deux (la classe peut influer sur la fuite) de réussir. En cas d'échec, l'ennemie l'attaque une fois gratuitement.
Si il choisis d'attaquer, il inflige des dégâts, puis en recoit si le monstre n'est pas mort.
Une partie se termine quand le héros meurt, ou qu'il atteint un bord de carte.
