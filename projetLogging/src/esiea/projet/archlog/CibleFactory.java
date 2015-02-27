package esiea.projet.archlog;

import java.io.FileWriter;
import java.io.IOException;

public class CibleFactory implements Cible {
	private String path;

	public CibleFactory() 
	{
		// TODO Auto-generated constructor stub
	}

	public CibleFactory( String path_) 
	{
		this.path = path_;
	}

	public void launch(final String msg_) 
	{
		System.out.println(msg_);
	}

    /**
     *  Fonction d'écriture de logs dans un fichier.
     * @param msg_
     * @throws IOException
     */
    protected void writeToFile(final String msg_) throws IOException 
    {
    	FileWriter writer = null;

    	try {
    	     writer = new FileWriter(this.path, true);
    	     writer.write(msg_, 0, msg_.length());
    	     writer.write(System.getProperty( "line.separator" ));
    	} catch(IOException ex) {
    		System.err.println("Erreur lors de l'écriture dans le fichier de logs.");
    	} finally {
    		if(writer != null) {
    			writer.close();
    		}
    	}
    }

	public void setPathFile(String path_) 
	{
		this.path = path_;
	}

	public String getPathFile() 
	{
		return this.path;
	}
}
