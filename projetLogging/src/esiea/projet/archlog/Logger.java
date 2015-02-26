package esiea.projet.archlog;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Logger {
	private Class<?> classe;
	
    Logger(final Class<?> class_) 
    {
    	this.classe = class_;
    }

    public void log(final String msg_, String level_) 
    {
        String dateTimeFormat = "yyyy-MM-dd hh:mm:ss";
        String dateStr = null;

        try {
        	DateFormat date = new SimpleDateFormat(dateTimeFormat);
            dateStr = date.format(new Date()); 
        } catch (final IllegalArgumentException e) { }

        System.out.println(dateStr + " [NAME=" + this.classe.getPackage().getName() + " LEVEL=" + level_ + " MESSAGE= " + msg_ + "]");
    }
    
    public void debug(final String msg_) 
    {
    	log(msg_, "DEBUG");
    }
    
    public void info(final String msg_) 
    {
        log(msg_, "INFO");
    }

    public void error(final String msg_) 
    {
    	log(msg_, "ERROR");	
    }
}
