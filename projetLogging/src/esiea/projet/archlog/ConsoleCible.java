package esiea.projet.archlog;

public class ConsoleCible extends CibleFactory {
	
	ConsoleCible() {
	}

	@Override
	public String getPathFile() 
	{
		return null;
	}

	/**
	 * Affichage des logs dans la console.
	 */
	@Override
	public void launch(final String msg_) 
	{
		System.out.println(msg_);
	}
}
