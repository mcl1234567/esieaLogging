package esiea.projet.archlog;
import java.util.ArrayList;
import java.util.Properties;


public class Logger {
	private Class<?> classeUnique;
	private FormateurFactory formateurUnique;
	private String levelUnique = null;
	private ArrayList<CibleFactory> cibles = new ArrayList<CibleFactory>();
	private String[] levels = {"", "DEBUG", "INFO", "ERROR"};
	private int LEVEL_UNIQUE = 0;
	
    Logger(final Class<?> class_) 
    {
    	this.classeUnique = class_;
    }
    
    Logger(final Class<?> class_, CibleFactory cible_) 
    {
    	this.classeUnique = class_;

    	addCible(cible_);
    }
    
    Logger(final Class<?> class_, CibleFactory cible_, FormateurFactory formateur_) 
    {
    	this.classeUnique = class_;
    	this.formateurUnique = formateur_;
    	addCible(cible_);    	
    }
    
    /**
     * Charge le fichier properties_config.txt en tant que configuration des logs. ( incomplet )
     */
    public void loadProperties() 
    {
    	GestionProperties gp = new GestionProperties();
    	Properties properties = gp.getProperties();

    	String keyId = null;
    	String nomCible = null;

    	System.out.println("\n------------------\nChargement du fichier config.properties...\n");
    	for(String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);			
			System.out.println(key + " => " + value);
			
			// Configuration des logs : via l'analyse du fichier properties. 
			if(key.substring(0, 6).equalsIgnoreCase("logger")) {
				int longueur = key.length();
				if (key.substring(longueur-5, longueur).equalsIgnoreCase("level")) {
					setLevel(value);
				}
				if (key.substring(longueur-8, longueur).equalsIgnoreCase("formater")
				|| key.substring(longueur-9, longueur).equalsIgnoreCase("formateur")) {
					convertFormateur(value);
				}

				// Cible quelconque
				if (key.substring(longueur-5, longueur).equalsIgnoreCase("cible")
				|| key.substring(longueur-6, longueur-1).equalsIgnoreCase("cible")) {
					convertCible(value);
				}

				// Fichier cible, ajout en deux temps. Stockage du nom de la classe / ou + package
				if (
				(key.substring(longueur-5, longueur).equalsIgnoreCase("cible")
					|| key.substring(longueur-6, longueur-1).equalsIgnoreCase("cible") )
				&& ( value.equalsIgnoreCase("FileCible") 
					|| value.equalsIgnoreCase("esiea.projet.archlog.FileCible") )
				) {
					// Récupère la dernière information des clés
					keyId = key.substring(key.lastIndexOf('.') + 1, longueur);
					
					// Récupère la dernière information des valeurs
					nomCible = value;//.substring(value.lastIndexOf('.') + 1, value.length());
				}

				// Fichier cible, ajout en deux temps. 
				// Création de l'objet cible avec ajp=out du chemin
				// Ajout à la liste des cibles.
				if (key.substring(longueur-4, longueur).equalsIgnoreCase("path") 
				&& keyId.equalsIgnoreCase(key.substring(key.lastIndexOf('.', key.length()-10)+1, longueur-5))) {					
					convertCible(nomCible, value);
					nomCible = null;	// reset
				}
			}
		}
    	System.out.println("----------------------\n"); // saut de ligne
    }
    
    /**
     * Ajout d'une cible pour le logger créé dans la classe spécifié, 
     * et vérification de l'unicité des cibles. 
     * @param cible_
     */
    public void addCible(CibleFactory cible_) 
    {    	
    	boolean dejaCible = false;

    	// Ajout d'une cible de logs
    	for (int i = 0; i < cibles.size(); i++) {
    		// Vérification de l'unicté de la liste de cibles.
    		if(String.valueOf(cible_.getClass()).equalsIgnoreCase(String.valueOf(cibles.get(i).getClass()))) {
        		// Cible déjà enregistrée.
    			dejaCible = true;
    		}
		}
		// Si pas encore ciblé, alors on ajoute à la liste des cibles.
		if(!dejaCible) {
			cibles.add(cible_);
		}
    }
    
    /**
     * Configure le formateur des messages log avec le nom de la classe du formateur.
     * @param formateur_
     */
    public void setFormateur(FormateurFactory formateur_) 
    {
    	this.formateurUnique = formateur_;
    }
   
    /**
     * Création objet du formateur depuis le fichier properties.
     * @param formateur_
     */
    public void convertFormateur(String formateur_)  
    {
    	try {
    		// Récupère le nom d'une classe avec un string.
    		FormateurFactory ff = (FormateurFactory) Class.forName(formateur_).newInstance();
    		setFormateur( ff );
    	} catch(ClassNotFoundException e) {
    		System.err.println("La classe " + formateur_ + " n'existe pas.");
    	} catch (IllegalAccessException e) {
    		System.err.println("La classe " + formateur_ + " ne peut pas être instanciée.");
    	} catch (InstantiationException e2) {
    		System.err.println("La classe " + formateur_ + " ne peut pas être instanciée.");
		}
    }
   
    /**
     * Création objet d'une cible depuis le fichier properties.
     * @param cible
     */
    public void convertCible(String cible_)  
    {
    	try {
    		// Récupère le nom d'une classe avec un string.
    		CibleFactory cf = (CibleFactory) Class.forName(cible_).newInstance();
    		addCible(cf);
    	} catch(ClassNotFoundException e) {
    		System.err.println("La classe " + cible_ + " n'existe pas.");
    	} catch (IllegalAccessException e) {
    		System.err.println("La classe " + cible_ + " ne peut pas être instanciée.");
    	} catch (InstantiationException e2) {
    		System.err.println("La classe " + cible_ + " ne peut pas être instanciée.");
		}
    }
   
    /**
     * Création objet d'une cible depuis le fichier properties.
     * @param cible
     */
    public void convertCible(String cible_, String path_)  
    {
    	try {
    		// Récupère le nom d'une classe avec un string.
    		CibleFactory cf = (CibleFactory) Class.forName(cible_).newInstance();
    		cf.setPathFile(path_);
    		addCible(cf);
    	} catch(ClassNotFoundException e) {
    		System.err.println("La classe " + cible_ + " n'existe pas.");
    	} catch (IllegalAccessException e) {
    		System.err.println("La classe " + cible_ + " ne peut pas être instanciée.");
    	} catch (InstantiationException e2) {
    		System.err.println("La classe " + cible_ + " ne peut pas être instanciée.");
		}
    }

    /**
     * Configure la classe appelante.
     * @param classe_
     */
    private void setClasseAppelante(Class<?> classe_) 
    {
    	this.classeUnique = classe_;
    }
    
    /**
     * Affiche un message log dès son appel, avec le level et le formateur configuré précédemment.
     * @param msg_
     */
    public void showMessage(final String msg_) 
    {
    	if(LEVEL_UNIQUE == 4 ) {
    		log(msg_, levelUnique);
    	} else {
    		log(msg_, levels[LEVEL_UNIQUE]);
    	}
    }
    
    /**
     * Ajout d'un level unique pour lancer un évènement de log lorsque la fonction showMessage() est appelée.
     * @param level_
     */
    public void setLevel(String level_) 
    {
    	boolean otherLevel = true;
    	if (level_.toLowerCase().equalsIgnoreCase("debug") || level_.toUpperCase().equalsIgnoreCase("debug")) {			
			LEVEL_UNIQUE = 1;
			otherLevel = false;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("info") || level_.toUpperCase().equalsIgnoreCase("info")) {			
			LEVEL_UNIQUE = 2;
			otherLevel = false;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("error") || level_.toUpperCase().equalsIgnoreCase("error")) {			
			LEVEL_UNIQUE = 3;
			otherLevel = false;
		}
    	// Ajout d'un autre level.
    	if(otherLevel) {
    		LEVEL_UNIQUE = 4;
    		levelUnique = level_;
    	}
    }
    
    /**
     * Ajout d'un level unique pour lancer un évènement de log lorsque la fonction showMessage() est appelée.
     * @param classe_
     * @param level_
     */
    public void setLevel(Class<?> classe_, String level_) 
    {
    	setClasseAppelante(classe_);
    	boolean otherLevel = true;

    	if (level_.toLowerCase().equalsIgnoreCase("debug") || level_.toUpperCase().equalsIgnoreCase("debug")) {			
			LEVEL_UNIQUE = 1;
			otherLevel = false;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("info") || level_.toUpperCase().equalsIgnoreCase("info")) {			
			LEVEL_UNIQUE = 2;
			otherLevel = false;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("error") || level_.toUpperCase().equalsIgnoreCase("error")) {			
			LEVEL_UNIQUE = 3;
			otherLevel = false;
		}
    	// Ajout d'un autre level.
    	if(otherLevel) {
    		LEVEL_UNIQUE = 4;
    		levelUnique = level_;
    	}
    }

    /**
     * Fonction de création du message log formaté et envoie vers les cibles.
     * @param msg_
     * @param level_
     */
    private void log(final String msg_, String level_) 
    {
    	// Formatage du message de log via un formateur. 
        String logMessage = this.formateurUnique.getLayout(this.classeUnique.getPackage().getName(), level_, msg_);

        // Envoie du log vers les cibles
       	for (int i = 0; i < this.cibles.size(); i++) {
       		
       		this.cibles.get(i).launch(logMessage);
		}
    }
    
    /**
     * Fonction log pour les phases de debogage
     * @param msg_
     */
    public void debug(final String msg_) 
    {
    	log(msg_, levels[1]);
    }
    
    /**
     * Fonction log pour l'affichage d'informations.
     * @param msg_
     */
    public void info(final String msg_) 
    {
        log(msg_, levels[2]);
    }

    /**
     * Fonction log pour l'affichage d'erreur.
     * @param msg_
     */
    public void error(final String msg_) 
    {
    	log(msg_, levels[3]);
    }
}
