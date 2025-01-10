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
git clone https://github.com/SamuelPrigent/Rentals-Backend
cd Rentals-Backend
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

## Configuration

### Variables d'environnement

Copiez le fichier `.env.example` en `.env` et remplissez les variables :

```bash
cp .env.example .env
```

#### Configuration de la base de données

Configurez les variables suivantes dans votre fichier `.env` :

- `DB_USERNAME` : Nom d'utilisateur PostgreSQL (par exemple : `samuel`)
- `DB_PASSWORD` : Mot de passe PostgreSQL (peut être laissé vide si aucun mot de passe n'est configuré en local)

La base de données doit être créée au préalable avec le nom `springdb`.

#### Génération de la clé JWT

Pour générer une clé JWT sécurisée, utilisez la commande suivante :

```bash
openssl rand -base64 32
```

La clé générée doit être copiée dans votre fichier `.env` pour la variable `JWT_SECRET_KEY`.

#### Configuration Cloudinary

Pour obtenir vos identifiants Cloudinary :

1. Créez un compte sur [Cloudinary](https://cloudinary.com/users/register/free)
2. Une fois connecté, accédez au [Dashboard](https://console.cloudinary.com/console)
3. Vous y trouverez vos identifiants :
   - Cloud Name (`CLOUDINARY_CLOUD_NAME`)
   - API Key (`CLOUDINARY_API_KEY`)
   - API Secret (`CLOUDINARY_API_SECRET`)

Copiez ces valeurs dans votre fichier `.env`.

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
