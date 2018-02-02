package ro.wade.cryma.InternalDBInteractor;

import java.sql.SQLException;
import java.util.List;

import ro.wade.cryma.InternalDBInteractor.comment.CommentRetriever;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//    	System.out.println("Fetching all cryptocurrencies from fuseki");
//        List<Cryptocurrency> currencies = CryptoRetriever.getCryptocurrencies("http://localhost:3030/crypto");
//        for(Cryptocurrency currency: currencies) {
//        	System.out.println(currency);
//        }
    	
//    	System.out.println("Fetching a single cryptocurrency from fuseki");
//    	Cryptocurrency coin = CryptoRetriever.getCryptocurrency("http://localhost:3030/crypto", "Bitcoin");
//        System.out.println("\n\n----------------------------\n");
//        System.out.println(coin);
    	
    	System.out.println("Fetching names of all cryptocurrencies present in Fuseki");
    	List<String> currencies = CryptoRetriever.getCryptocurrencyNames("http://localhost:3030/crypto");
        System.out.println(currencies);
        
//    	try {
//    		CommentRetriever retriever = new CommentRetriever();
//    		retriever.saveComment("Bitcoin", "Comment no.4");
//			System.out.println(retriever.getComments("Bitcoin"));
//			retriever.closeConnection();
//		} catch (ClassNotFoundException | SQLException e) {
//			e.printStackTrace();
//		}
    }
}
