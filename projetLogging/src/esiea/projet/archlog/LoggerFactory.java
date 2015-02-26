package esiea.projet.archlog;

public class LoggerFactory {
	public static Logger getLogger(final Class<?> class_) 
	{
        if (class_ == null) {
            throw new UnsupportedOperationException("No class provided, and an appropriate one cannot be found.");
        }

        return new Logger(class_);
	}
	
}
