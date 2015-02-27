Framework de logging
===

Conception et développement d'un framework de logging

Le principe d’un framework de logging est de mettre à disposition un
système simple pour gérer les messages d’erreur et comprendre le flux
d’exécution d’un programme.

# Installation du framework de logging

* Télécharger le package esiea.projet.archlog
* Pour tester la stabilité et les performances du framework, le fichier Base.java contient un thread main() contenant des tests prédéfinis.
* Il est possible d'importer le package dans votre projet Java, en pensant à supprimer le fichier Base.java contenant le main() : "Import" > "General" > "Existing project into workspace"
* Sélectionner le dossier contenant le dossier "src".


# Les fonctions du framework de logging :
	
Le framework gère 3 priorités définies, DEBUG, INFO et ERROR, la fonction setLevel() permet d'ajouter des priorités.
	
Pour une configuration de messages de logs en Java :
	
Cette ligne d'instanciation est nécessaire pour configurer les messages de logs :
	
	Logger logger = LoggerFactory.getLogger(MaClasseAppelante.class, new MaCible());
  
Choix d'une cible sous format de fichier texte. Vous pouvez ajouter vos cibles, la démarche est expliquée ci-dessous.

	loggger.addCible(new FileCible("path_fichier_de_logs"));
  
Voici un formateur par défaut, vous pouvez ajouter vos propres formateurs, la démarche est expliquée ci-dessous.

	logger.setFormateur(new Formateur());
	
Les messages de logs selon les priorités :

	logger.debug("aaaaa tadaaaa");
	logger.info("bbbbb toudou");
	logger.error("cccccccc tudu");
		
Pour une configuration de messages de logs via le fichier config.properties :

	logger.loadProperties();
	
Cet appel de fonction permet de générer un log selon la priorité définie auparavant :
	
	logger.showMessage("La priorité a été redéfini");
	
Contenu à titre d'exemple du fichier properties :

	logger.fr.esiea.MaClasse.level = DEBUG
	logger.fr.esiea.MaClasse.formater = esiea.projet.archlog.FormateurShowFormat
  	logger.fr.esiea.MaClasse.cible = esiea.projet.archlog.ConsoleCible

### Comment ajouter un nouveau formateur ?
* Il suffit de créer un layout sur le même modèle que celui proposé, en héritant de la superclasse FormateurFactory :
 
    ```public String getLayout(String namePackages_, String level_, String msg_);```

### Comment ajouter une nouvelle cible ?
* Soit en utilisant la fonction setLevel, qui ajoutera une priorité personnalisée.
* Soit en développant une cible sur le même modèle que celui proposé, en héritant de la superclasse CibleFactory :

    ```public String getPathFile();```
    
    ```public void launch(final String msg_);```

## Créateurs
* Samir Maikhaf
* Morvan Calmel
* David Pavius
