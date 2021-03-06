// package murmur.jjson;

import com.google.gson.*;
import com.sun.net.httpserver.*;
import net.sf.oval.*;
import net.sf.oval.constraint.NotNull;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;

/**
 * Class jjson implements interface serv.
 * This class get a json file and check if it is correct or not.
 * 
 */
@SuppressWarnings("restriction")
public class jjson implements serv {
	
	@NotNull
    private final HttpServer serv;
    @NotNull
    private final Gson builder;
	
    private static final int PORT = 80;
    private static final int ANS_CODE = 200;
    private static final String ROOT = "/";
    
    int id = 0;
    
	/**
    	 *The main function. It shows the message that the program is working now,
    	 *start server and wait for json files.
    	 *
    	 *@param	args	is not used
    	 *@throws	IOException	if input or output exception occurred
     	*/
	public static void main( String[] args ) throws IOException
    {
        System.out.println( "Program is working...\n" );
        jjson form = new jjson();
        form.start();
        Runtime.getRuntime().addShutdownHook(new Thread(form::stop));
    }
	/**
	 * Check the given json file if it responds json format.
	 * 
	 *  @throws	IOException	if input or output exception occurred
	 */	
	public jjson() throws IOException{
		this.builder = new GsonBuilder().setPrettyPrinting().create();
		this.serv = HttpServer.create(new InetSocketAddress(PORT),0);
		this.serv.createContext(ROOT, http -> {
			
			InputStreamReader isr = new InputStreamReader(http.getRequestBody());
			String filename = http.getRequestURI().getPath();
            final String jsonRequest = new BufferedReader(isr).lines().collect(Collectors.joining());
            System.out.println("request:" + jsonRequest);
            String jsonResponse;
            id++;
            try {
                Object object = builder.fromJson(jsonRequest, Object.class);
                jsonResponse = builder.toJson(object);
            } catch (JsonSyntaxException e) {
		String[] kek= e.getMessage().split(" at ");
            	// get error
            	
            	/*	EXAMLPE: 
            	 {
 					"errorCode"  : 12345,
 					"errorMessage" : ["verbose, plain language description of the problem with hints about how to fix it]",
 					"errorPlace" : ["highlight the point where error has occurred"],
 					"resource"   : ["filename"],
 					"request-id" : ["the request id generated by the API for easier tracking of errors"],
				 }
            	 */
            	
                JsonObject jsonError = new JsonObject();
                jsonError.addProperty("errorCode", e.hashCode());
                jsonError.addProperty("errorMessage", kek[0]);
                jsonError.addProperty("errorPlace", "at "+kek[1]);
                jsonError.addProperty("resource", filename);
                jsonError.addProperty("request-id", id);
                
                jsonResponse = builder.toJson(jsonError);
                
                
            }
            System.out.println("response:" + jsonResponse);
            http.sendResponseHeaders(ANS_CODE, jsonResponse.length());
            http.getResponseBody().write(jsonResponse.getBytes());
            http.close();
			
		});
		
	}
	
	/**
	 * Start server
	 */
	@Override
   	 public void start() {
        this.serv.start();
    	}

    	/**
    	 * Stop server
    	 */
   	 @Override
   	 public void stop() {
        this.serv.stop(0);
    	}
}
