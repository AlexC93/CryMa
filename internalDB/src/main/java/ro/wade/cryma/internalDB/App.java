package ro.wade.cryma.internalDB;

import java.io.FileNotFoundException;
import java.io.IOException;

import ro.wade.cryma.internalDB.data.MainModel;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
			MainModel model = new MainModel();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
