# Rental API

Backend (API REST) avec Spring Boot.
Cette API fonctionne en tandem avec le Frontend Angular de ce repo : https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring.

## Prérequis

- Java JDK 17 ou supérieur
- PostgreSQL 15 ou supérieur
- Maven 3.8 ou supérieur

## Config BDD

1. Installer PostgreSQL si ce n'est pas déjà fait
2. Créer une base de données nommée `springdb`

```sql
CREATE DATABASE springdb;
```

## Installation

1. Cloner le repository

```bash
git clone <votre-repo-url>
cd Back
```

2. Installer les dépendances

Mac :

```bash
./mvnw clean install
```

Windows :

```bash
mvnw.cmd clean install
```

## Démarrage

Mac :

```bash
./mvnw spring-boot:run
```

Windows :

```bash
mvnw.cmd spring-boot:run
```

## Fonctionnement de l'API

L'API est configurée pour accepter les requêtes faite à : `http://localhost:3001` en provenance du front-end Angular qui tournera sur `http://localhost:4200`
