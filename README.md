# MyERP

## Organisation du répertoire

*   `doc` : documentation
*   `docker` : répertoire relatifs aux conteneurs _docker_ utiles pour le projet
    *   `dev` : environnement de développement
*   `src` : code source de l'application


## Environnement de développement

Les composants nécessaires lors du développement sont disponibles via des conteneurs _docker_.
L'environnement de développement est assemblé grâce à _docker-compose_
(cf docker/dev/docker-compose.yml).

Il comporte :

*   une base de données _PostgreSQL_ contenant un jeu de données de démo (`postgresql://127.0.0.1:9032/db_myerp`)



### Lancement

    cd docker/dev
    docker-compose up


### Arrêt

    cd docker/dev
    docker-compose stop


### Remise à zero

    cd docker/dev
    docker-compose stop
    docker-compose rm -v
    docker-compose up


##Correction

#couche Model
- La méthode isEquilibree() a necessité une correction , j'ai remplacé le equal par compareTo dans la classe EcritureComptable
- La méthode getTotalDebit() de la classe EcritureComptable etait similaire a la classe getTotalCredit() 
#couche consumer
- La Requete "SQLinsertListLigneEcritureComptable" manque une virgule entre "debit" et "credit" argument du INSERT INTO
#couche business
- La méthode UpdateComptable dans la classe ComptabilitéManagerImpl, modification légère permettant de checker si l'ecriture comptable répond aux normes avant de la sauvegarder dans la base de données

## Les modifications effectués au projet

- Implémentation des tests unitaires pour chaque package dans src/test/java
- Implémentation d'un visualisation de la couverture de code avec jacoco dans le fichier "target/site/jacoco/index.html" 
- Implémentation des tests d'intégration pour la couche business et consumer dans les fichiers : src/test-business/java et src/test-consumer/java
- Implémentation de la methode addReference et modification de RG_compta_5 dans la classe ComptabiliteManagerImpl
- Ajout de deux méthodes insertSequenceEcritureComptable et deleteEcritueComptable
 ainsi que leurs requetes SQL dans le fichier sqlContext.xml.
