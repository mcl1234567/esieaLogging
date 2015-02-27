package esiea.projet.archlog;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;


public class Logger {
	private Class<?> classeUnique;
	private FormateurFactory formateurUnique;
	private String levelUnique;

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

    	System.out.println("\nChargement du fichier config.properties...\n");
    	for(String key : properties.stringPropertyNames()) {
			String value = properties.getProperty(key);			
			System.out.println(key + " => " + value);
			
			// Configuration des logs : via l'analyse du fichier properties. 
			if(key.substring(0, 6).equalsIgnoreCase("logger")) {
				int longueur = key.length();
				if (key.substring(longueur-5, longueur).equalsIgnoreCase("level")) {
					setLevel(value);
					// packages + classe
					//setClasseAppelante(key.substring(6, longueur-6));
				}
				if (key.substring(longueur-8, longueur).equalsIgnoreCase("formater")) {
					//setFormateurViaString(value);
					// packages + classe
					//setClasseAppelante(key.substring(6, longueur-6));
				}
			}
		}
    	System.out.println("");
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
     * Configure le formateur des messages log via le nom de la classe du formateur a utilisé.
     * @param formateur_
     */
    public void setFormateur(FormateurFactory formateur_) 
    {
    	this.formateurUnique = formateur_;
    }
   
    public void setFormateur(String formateur_)  
    {
    	// TO DO
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
    	log(msg_, levels[LEVEL_UNIQUE]);
    }
    
    /**
     * Ajout d'un level unique pour lancer un évènement de log lorsque la fonction showMessage() est appelée.
     * @param level_
     */
    public void setLevel(String level_) 
    {
    	if (level_.toLowerCase().equalsIgnoreCase("debug") || level_.toUpperCase().equalsIgnoreCase("debug")) {			
			LEVEL_UNIQUE = 1;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("info") || level_.toUpperCase().equalsIgnoreCase("info")) {			
			LEVEL_UNIQUE = 2;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("error") || level_.toUpperCase().equalsIgnoreCase("error")) {			
			LEVEL_UNIQUE = 3;
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

    	if (level_.toLowerCase().equalsIgnoreCase("debug") || level_.toUpperCase().equalsIgnoreCase("debug")) {			
			LEVEL_UNIQUE = 1;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("info") || level_.toUpperCase().equalsIgnoreCase("info")) {			
			LEVEL_UNIQUE = 2;
		}
    	if (level_.toLowerCase().equalsIgnoreCase("error") || level_.toUpperCase().equalsIgnoreCase("error")) {			
			LEVEL_UNIQUE = 3;
		}
    }

    /**
     * Fonction de création du message log formaté et envoie vers les cibles.
     * @param msg_
     * @param level_
     */
    private void log(final String msg_, String level_) 
    {
    	// Formatage du message log via un formateur. 
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
