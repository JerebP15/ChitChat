import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	private JTextArea outputfield;
	private JTextField inputfield;
	private JTextArea onlineUsers;
	private JTextField setUsername;
	
	private JTextArea recivedMessage;
	private JTextField user;
	
	private JButton loginButton;
	private JButton logoutButton;
	
	private JSplitPane splitPane;
	
	private boolean logedin=false;
	
	private boolean first=true;
	
	MessageRobot newMessage; 
	UserRobot newOnlineUsers;

	public ChatFrame() {
		super();
		
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		newMessage = new MessageRobot(this);
		newOnlineUsers = new UserRobot(this);
		
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		JLabel messagebasic = new JLabel("Najprej se morate prijaviti!");
		JPanel basic = new JPanel();
		basic.setPreferredSize(new Dimension(400,50));
		
		loginButton = new JButton("Prijava");
		logoutButton = new JButton("Odjava");
		
		loginButton.addActionListener(this);
		logoutButton.addActionListener(this);
		logoutButton.setEnabled(false);
		
		basic.setLayout(layout);
		basic.add(messagebasic);
		
		basic.add(loginButton);
		basic.add(logoutButton);
		
		GridBagConstraints conbasic = new GridBagConstraints();
		conbasic.weightx = 1.0;
		conbasic.gridx = 0;
		conbasic.gridy = 0;
		conbasic.fill = GridBagConstraints.HORIZONTAL;
		
		this.outputfield = new JTextArea(30, 40);
		this.outputfield.setEditable(false);
		GridBagConstraints conMessage = new GridBagConstraints();
		conMessage.weighty = 1.0;
		conMessage.gridx = 0;
		conMessage.gridy = 1;
		JScrollPane scrollPaneSporocila = new JScrollPane(outputfield);

		JPanel vnosni = new JPanel();
		vnosni.setLayout(layout);
		GridBagConstraints conVnosni = new GridBagConstraints();
		conVnosni.weighty = 0.5;
		conVnosni.gridx = 0;
		conVnosni.gridy = 2;
		conVnosni.fill = GridBagConstraints.HORIZONTAL;
		
		this.inputfield = new JTextField(29);
		this.inputfield.setEditable(false);
		GridBagConstraints conInput = new GridBagConstraints();
		conInput.gridx = 1;
		conInput.gridy = 0;
		inputfield.addKeyListener(this);
		
		this.setUsername= new JTextField(System.getProperty("user.name"), 10);
		GridBagConstraints conVzdevek = new GridBagConstraints();
		conVzdevek.weightx = 0.5;
		conVzdevek.gridx = 0;
		conVzdevek.gridy = 0;
		setUsername.addKeyListener(this);
		
		vnosni.add(setUsername, conVzdevek);
		vnosni.add(inputfield, conInput);		
		
		JPanel left = new JPanel();
		left.setPreferredSize(new Dimension(470,600));
		GridBagConstraints conleft = new GridBagConstraints();
		conleft.gridx = 0;
		conleft.gridy = 0;
		conleft.fill = GridBagConstraints.VERTICAL;
		
		left.add(basic, conbasic);
		left.add(scrollPaneSporocila, conMessage);
		left.add(vnosni, conVnosni);
		
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(300,600));
		GridBagConstraints conright = new GridBagConstraints();
		conright.gridx = 1;
		conright.gridy = 0;
		conright.fill = GridBagConstraints.VERTICAL;
			
		this.onlineUsers = new JTextArea(10,25);
		this.onlineUsers.setEditable(false);
		GridBagConstraints conUsers = new GridBagConstraints();
		conUsers.weighty =0.5;
		conUsers.gridx = 0;
		conUsers.gridy = 0;
		conUsers.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane scrollPaneUporabniki = new JScrollPane(onlineUsers);
		right.add(scrollPaneUporabniki, conUsers);
		
		this.recivedMessage = new JTextArea(23, 25);
		this.recivedMessage.setEditable(false);
		GridBagConstraints conRecivedMessage = new GridBagConstraints();
		conRecivedMessage.weighty =0.5;
		conRecivedMessage.gridx = 0;
		conRecivedMessage.gridy = 1;
		conRecivedMessage.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane scrollPanePreSporocila = new JScrollPane(recivedMessage);
		right.add(scrollPanePreSporocila, conRecivedMessage);
		
		JLabel instructionUser = new JLabel("Uporabnik sporocila: ");
		right.add(instructionUser);
		
		this.user = new JTextField(15);
		this.user.setEditable(false);
		GridBagConstraints conuser = new GridBagConstraints();
		conuser.gridx = 0;
		conuser.gridy = 3;
		conuser.fill = GridBagConstraints.HORIZONTAL;
		user.addKeyListener(this);
		right.add(user, conuser);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(470);
		left.setMinimumSize(new Dimension(400,600));
		right.setMinimumSize(new Dimension(150,600));
		
		pane.add(splitPane);
		
		addWindowListener(new WindowAdapter(){
			public void windowOpened(WindowEvent e){
				inputfield.requestFocusInWindow();
			}
			public void windowClosing(WindowEvent e){
				try {
					logout();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
				System.exit(0);}
		});
	}

	public void addMessage(String user, String message) {
		String chatroom = this.outputfield.getText();
		this.outputfield.setText(chatroom + user + ": " + message + "\n");		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
	        try {
	        	login();
	        	} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }if (e.getSource() == logoutButton){
        	try {
        		this.addMessage("Chatroom", "Uspešno ste se odjavili");
				logout();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.inputfield && !this.inputfield.getText().equals("")) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(this.setUsername.getText(), this.inputfield.getText());
				Recived message = new Recived();
				if (this.user.getText().equals("")) {
					message.setGlobal(true);
				} else {
					message.setGlobal(false);
					message.setRecipient(this.user.getText());
				}
				message.setText(this.inputfield.getText());
				this.inputfield.setText("");
				this.user.setText("");
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.setDateFormat(new ISO8601DateFormat());
				String message2;
				try {
					message2 = mapper.writeValueAsString(message);
					try {
			        	URI uri = new URIBuilder("http://chitchat.andrej.com/messages")
			        	          .addParameter("username", this.setUsername.getText())
			        	          .build();

			        	     	  String responseBody = Request.Post(uri)
			        	          .bodyString(message2, ContentType.APPLICATION_JSON)
			        	          .execute()
			        	          .returnContent()
			        	          .asString();

			        	  System.out.println(responseBody);
			        } catch (IOException e1) {
			            e1.printStackTrace();
			        } catch (URISyntaxException e1) {
						e1.printStackTrace();
					}
				} catch (JsonProcessingException e2) {
					e2.printStackTrace();
				}	}
		}		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

	public void clearOnline() {
		this.onlineUsers.setText(null);
		
	}

	public void writeUser(User user) {
		this.onlineUsers.append(user.getUsername() + "\n");
		
	}

	public boolean getStatus() {
		return logedin;
	}

	public void writeRecivedMessage(Recived message) {
		if (message.getGlobal()) {
			this.outputfield.append((message.getSender() + ": " + message.getText()) + "\n");
		} else {
			this.recivedMessage.append((message.getSender() + ": " + message.getText() + "    Poslano ob: " + message.getSent_at()) + "\n");
		
		}
		
	}

	public String getUsername() {
		return this.setUsername.getText();
	}
	
	public void login() throws URISyntaxException, IOException {
		URI uri = new URIBuilder("http://chitchat.andrej.com/users")
				.addParameter("username", getUsername()).build();
		HttpResponse response = Request.Post(uri).execute().returnResponse();
		InputStream responseText = null;		
			if (first) {
				newOnlineUsers.pozeni();
				newMessage.pozeni();
				first=false;

			}
			logedin=true;
			this.logoutButton.setEnabled(true);
			this.loginButton.setEnabled(false);
			this.inputfield.setEditable(true);
			this.user.setEditable(true);
			responseText=response.getEntity().getContent();
		System.out.println("responseText: " + getStringFromInputStream(responseText));
	} ;
	
	public void logout() throws URISyntaxException {
		try {
			URI uri = new URIBuilder("http://chitchat.andrej.com/users")
					.addParameter("username", getUsername()).build();

			String responseBody = Request.Delete(uri).execute().returnContent().asString();

			System.out.println(responseBody);
			logedin=false;
			this.logoutButton.setEnabled(false);
			this.loginButton.setEnabled(true);
			this.inputfield.setText("");
			this.inputfield.setEditable(false);
			this.user.setEditable(false);
			this.onlineUsers.setText("");
		} catch (IOException e) {
			e.printStackTrace();
			}
}

	
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
return sb.toString();
}

	public void writeRecivedMessage(String obvestilo) {
		this.recivedMessage.append(obvestilo);
	}

	public void noUsers() {
		this.onlineUsers.setText("Noben uporabnik ni prijavljen.");
		
	}

	public void noMessages() {
		this.recivedMessage.setText("Trenutno ni novih sporocil.");
		
	}
}
