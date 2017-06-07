import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

public class server {

    //public static void main(String[] args){
        //login("Peter");
	    //ArrayList<String> a = users(args);
	    //System.out.println(a);
	    //logout("Peter");
	    //ArrayList<String> b = users(args);
	    //System.out.println(b);
	//}

    public static ArrayList<String> users(String[] args){
        ArrayList<String> uporabniki = new ArrayList<String>();
        try {
            String responseBody = Request.Get("http://chitchat.andrej.com/users")
                    .execute()
                    .returnContent()
                    .asString();

            uporabniki.add(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return uporabniki;
    }

    public static void login(String name){
        try {
            URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                      .addParameter("username", name)
                      .build();

              String responseBody = Request.Post(uri)
                                           .execute()
                                           .returnContent()
                                           .asString();

              System.out.println(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (URISyntaxException p){
                p.printStackTrace();
            }
    }

    public static void logout(String name){
        try {
              URI uri = new URIBuilder("http://chitchat.andrej.com/users")
                      .addParameter("username", name)
                      .build();

              String responseBody = Request.Delete(uri)
                                           .execute()
                                           .returnContent()
                                           .asString();

              System.out.println(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (URISyntaxException p){
                p.printStackTrace();
            }
    }

    public static ArrayList<String> read_message(String name){
        ArrayList<String> sporocilo = new ArrayList<String>();
        try {
            URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
                      .addParameter("username", name)
                      .build();

              String responseBody = Request.Get(uri)
                                           .execute()
                                           .returnContent()
                                           .asString();

              sporocilo.add(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (URISyntaxException p){
                p.printStackTrace();
            }
        return sporocilo;
    }

    public static void send_message(String text){
        try {
             URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
                      .addParameter("username", "miki")
                      .build();

              String message = "{ \"global\" : true, \"text\" :  {} }".format(text);

              String responseBody = Request.Post(uri)
                      .bodyString(message, ContentType.APPLICATION_JSON)
                      .execute()
                      .returnContent()
                      .asString();

              System.out.println(responseBody);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (URISyntaxException p){
                p.printStackTrace();
            }
    }

}