import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.client.fluent.Request;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

public class UserRobot extends TimerTask{
	private ChatFrame chatroom;
	
	public void pozeni() {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(this, 1000, 2500);
	}
	
	public UserRobot(ChatFrame chatroom) {
		this.chatroom = chatroom;

}

	@Override
	public void run() {
		try {String users = Request.Get("http://chitchat.andrej.com/users")
	    		   .execute()
	    		   .returnContent()
	    		   .asString();
	       		ObjectMapper mapper = new ObjectMapper();
	       		mapper.setDateFormat(new ISO8601DateFormat());
	       		
	       		TypeReference<List<User>> t = new TypeReference<List<User>>() { };
	    		List<User> prijavljeni = mapper.readValue(users, t);
	    		chatroom.clearOnline();
	    		
	    		if (prijavljeni.isEmpty()){
	    			chatroom.noUsers();
	    			System.out.println("NI");
	    		} else   {
	    		for (User oseba : prijavljeni) {
	    			chatroom.writeUser(oseba);
	    		}}

	    		
	       } catch (IOException e) {
	           e.printStackTrace();
	       }
		
	}
}