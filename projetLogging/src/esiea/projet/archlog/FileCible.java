package esiea.projet.archlog;

import java.io.FileWriter;
import java.io.IOException;

public class FileCible extends CibleFactory {
	private String path;

	FileCible(final String path_) 
	{
		this.path = path_;
	}

	@Override
	public String getPathFile() 
	{
		return this.path;
	}

    /**
     *  Fonction d'écriture de logs dans un fichier.
     * @param msg_
     * @throws IOException
     */
    private void writeToFile(final String msg_) throws IOException 
    {
    	FileWriter writer = null;

    	try {
    	     writer = new FileWriter(this.path, true);
    	     writer.write(msg_, 0, msg_.length());
    	     writer.write(System.getProperty( "line.separator" ));
    	} catch(IOException ex) {
    	    ex.printStackTrace();
    	} finally {
    		if(writer != null) {
    			writer.close();
    		}
    	}    	
    }

    /**
     * Ecriture des logs dans un fichier.
     */
    @Override
	public void launch(final String msg_) 
    {	    	
        try {
          	if(this.path != null) {
           		writeToFile(msg_);
           	}
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
	}
}