package esiea.projet.archlog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Logger {
	private Class<?> classe;
	private FormateurFactory formateur;
	private ArrayList<CibleFactory> cibles = new ArrayList<CibleFactory>();
	
    Logger(final Class<?> class_) 
    {
    	this.classe = class_;
    }
    
    Logger(final Class<?> class_, CibleFactory cible_) 
    {
    	this.classe = class_;

    	addCible(cible_);
    }
    
    Logger(final Class<?> class_, CibleFactory cible_, FormateurFactory formateur_) 
    {
    	this.classe = class_;
    	this.formateur = formateur_;
    	addCible(cible_);    	
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
    
    public void setFormateur(FormateurFactory formateur_) 
    {
    	this.formateur = formateur_;
    }

    /**
     * Fonction de création du message log et envoie vers les cibles.
     * @param msg_
     * @param level_
     */
    private void log(final String msg_, String level_) 
    {
        String logMessage = this.formateur.getLayout(this.classe.getPackage().getName(), level_, msg_);

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
    	log(msg_, "DEBUG");
    }
    
    /**
     * Fonction log pour l'affichage d'informations.
     * @param msg_
     */
    public void info(final String msg_) 
    {
        log(msg_, "INFO");
    }

    /**
     * Fonction log pour l'affichage d'erreur.
     * @param msg_
     */
    public void error(final String msg_) 
    {
    	log(msg_, "ERROR");	
    }
}
