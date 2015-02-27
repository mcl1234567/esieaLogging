package esiea.projet.archlog;

public class Base {
	public static void main(String[] args)
	{
		Logger logger = LoggerFactory.getLogger(Base.class, new ConsoleCible());
		logger.addCible(new FileCible("logs_projet.txt"));
		logger.setFormateur(new Formateur());
		logger.debug("Message tadaaaa");
		logger.info("Message toudou");
		logger.error("Message tudu");
	}
}