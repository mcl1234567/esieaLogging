package esiea.projet.archlog;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class GestionProperties {
	Properties properties;

	public GestionProperties() 
	{
		this.properties = new Properties();
		try {
			properties.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			System.err.println("Erreur de chargement du fichier properties.");
		}
	}

	public Properties getProperties() 
	{		
		return this.properties; 
	}
}
