# 🎵 Sporticorne

## Présentation

Sporticorne est une application Android développée en Kotlin qui permet d'explorer l'univers musical à partir des données de Spotify.

L'objectif de l'application est d'aider les utilisateurs à découvrir de nouveaux artistes, retrouver des morceaux moins connus et consulter des informations détaillées sur des musiques et des artistes à travers une interface simple et intuitive.

---

## Fonctionnalités

### Recherche musicale

L'utilisateur peut rechercher un morceau ou un artiste afin d'accéder rapidement aux informations associées.

### Musiques oubliées

Cette fonctionnalité met en avant des morceaux moins populaires ou moins connus afin de favoriser la découverte musicale.

### Artistes du passé

Une sélection d'artistes permet d'explorer différentes générations musicales et de consulter leurs informations principales.

### Dernières sorties

L'application affiche les dernières sorties disponibles sur Spotify pour suivre l'actualité musicale.

### Détail d'un morceau

Pour chaque titre, l'utilisateur peut consulter :

- le nom du morceau ;
- l'album associé ;
- la date de sortie ;
- la durée ;
- les artistes participants ;
- la popularité du morceau ;
- un lien vers Spotify.

### Détail d'un artiste

Pour chaque artiste, l'utilisateur peut consulter :

- son nom ;
- son genre musical ;
- sa popularité ;
- son nombre d'abonnés ;
- sa photo ;
- un lien vers Spotify.

---

## Technologies utilisées

- Kotlin
- Jetpack Compose
- Material 3
- Navigation 3
- ViewModel
- StateFlow
- Ktor Client
- Spotify Web API
- Coil

---

## Architecture

L'application repose sur une architecture MVVM permettant de séparer l'interface utilisateur, la logique métier et l'accès aux données.

```text
Interface utilisateur (Jetpack Compose)
                │
                ▼
           ViewModel
                │
                ▼
           Repository
                │
                ▼
          Spotify API
```

---

## Installation

1. Cloner le dépôt GitHub.
2. Ouvrir le projet dans Android Studio.
3. Configurer les identifiants Spotify dans le fichier de configuration local.
4. Synchroniser les dépendances Gradle.
5. Exécuter l'application sur un émulateur ou un appareil Android.

---

## Objectif du projet

Ce projet a été réalisé dans le cadre d'un apprentissage du développement Android moderne. Il met en œuvre la création d'interfaces avec Jetpack Compose, la consommation d'une API REST et la gestion de la navigation et des états d'une application mobile.

---

## Auteur

**Liliane CONTAL**
