package esiea.projet.archlog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formateur extends FormateurFactory {
	
	/**
	 * Formatage d'un message log.
	 * @param namePackages_
	 * @param level_
	 * @param msg_
	 * @return la chaîne log formatée.
	 */
	public String getLayout(String namePackages_, String level_, String msg_) 
	{
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss.SSS";
        String dateStr = null;

        try {
        	DateFormat date = new SimpleDateFormat(dateTimeFormat);
            dateStr = date.format(new Date()); 
        } catch (final IllegalArgumentException e) { 
        	System.err.println("Date invalide");
        }

        // Retourne le message log formaté.
        return dateStr + " [PACKAGE=" + namePackages_ + "  PRIORITE=" + level_ + "  MESSAGE=" + msg_ + "]";
	}
}
