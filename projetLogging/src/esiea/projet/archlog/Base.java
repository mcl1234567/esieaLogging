package esiea.projet.archlog;

public class Base {
	public static void main(String[] args)
	{
		// Configuration de logs en Java
		Logger logger = LoggerFactory.getLogger(Base.class, new ConsoleCible());
		logger.addCible(new FileCible("logs_projet.txt"));
		logger.setFormateur(new FormateurShowFormat());
		logger.debug("aaaaa tadaaaa");
		logger.info("bbbbb toudou");
		logger.error("cccccccc tudu");
		
		// Configuration des logs via le fichier properties_config.txt
		logger.loadProperties();
		logger.showMessage("Level redéfini");
	}
}