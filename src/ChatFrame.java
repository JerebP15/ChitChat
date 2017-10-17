import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;

public class ChatFrame extends JFrame implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea output;
	private JTextField inputfield;
	private JTextField username;
	private JButton prijava;
	private JButton odjava;
	private JLabel usernamelabel;
	private Container polje1;
	private Container polje2;
	private JTextArea users;
	private JLabel messagelabel;
	private JPanel input;
	private JButton pošlji;

	public ChatFrame() {
		super();
		Container pane = this.getContentPane();
		pane.setLayout(new GridBagLayout());
		
		this.polje1 = new JPanel();
		GridBagConstraints polje1Constraint = new GridBagConstraints();
		polje1Constraint.gridx = 0;
		polje1Constraint.gridy = 0;
		pane.add(polje1, polje1Constraint);
		
		this.usernamelabel = new JLabel("Username:");
		GridBagConstraints usernamelabelConstraint = new GridBagConstraints();
		usernamelabelConstraint.gridx = 0;
		usernamelabelConstraint.gridy = 0;
		polje1.add(usernamelabel, usernamelabelConstraint);
		
		this.username = new JTextField(10);
		GridBagConstraints usernameConstraint = new GridBagConstraints();
		usernameConstraint.gridx = 1;
		usernameConstraint.gridy = 0;
		polje1.add(username, usernameConstraint);
		polje1.addKeyListener(this);
		
		this.prijava = new JButton("Prijava");
		GridBagConstraints prijavaConstraint = new GridBagConstraints();
		prijavaConstraint.gridx = 2;
		prijavaConstraint.gridy = 0;
		prijava.addActionListener(this);
		polje1.add(prijava, prijavaConstraint);
		//server.login(System.getProperty("user.name"));
		
		this.odjava = new JButton("Odjava");
		GridBagConstraints odjavaConstraint = new GridBagConstraints();
		odjavaConstraint.gridx = 3;
		odjavaConstraint.gridy = 0;
		polje1.add(odjava, odjavaConstraint);
		odjava.addActionListener(this);
		odjava.setEnabled(false);
		//server.logout(System.getProperty("user.name"));
		
		this.polje2 = new JPanel();
		GridBagConstraints polje2Constraint = new GridBagConstraints();
		polje2Constraint.gridx = 0;
		polje2Constraint.gridy = 1;
		pane.add(polje2, polje2Constraint);	

		this.output = new JTextArea(20, 40);
		this.output.setEditable(false);
		GridBagConstraints outputConstraint = new GridBagConstraints();
		outputConstraint.gridx = 0;
		outputConstraint.gridy = 1;
		polje2.add(output, outputConstraint);
		
		this.users = new JTextArea(20, 40);
		this.users.setEditable(false);
		GridBagConstraints usersConstraint = new GridBagConstraints();
		usersConstraint.gridx = 1;
		usersConstraint.gridy = 0;
		polje2.add(users, usersConstraint);
		
		this.input = new JPanel();
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		pane.add(input, inputConstraint);	
				
		this.messagelabel = new JLabel("Sporoèilo:");
		GridBagConstraints messagelabelConstraint = new GridBagConstraints();
		usernamelabelConstraint.gridx = 0;
		usernamelabelConstraint.gridy = 0;
		input.add(messagelabel, messagelabelConstraint);
		
		this.inputfield = new JTextField(60);
		GridBagConstraints inputfieldConstraint = new GridBagConstraints();
		inputfieldConstraint.gridx = 1;
		inputfieldConstraint.gridy = 0;
		input.add(inputfield, inputfieldConstraint);
		inputfield.addKeyListener(this);
		inputfield.setEditable(false);
		
		this.pošlji = new JButton("Pošlji");
		GridBagConstraints pošljiConstraint = new GridBagConstraints();
		pošljiConstraint.gridx = 2;
		pošljiConstraint.gridy = 0;
		input.add(pošlji, pošljiConstraint);
		pošlji.addActionListener(this);
		pošlji.setEnabled(false);
		input.addKeyListener(this);
	}

	/**
	 * @param person - the person sending the message
	 * @param message - the message content
	 */
	public void addMessage(String person, String message) {
		String chat = this.output.getText();
		this.output.setText(chat + person + ": " + message + "\n");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == prijava) {
	        try {
	        	prijava();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }if (e.getSource() == odjava){
        	try {
        		this.addMessage("Server","Uspešno ste se odjavili!");
				odjava();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
        }if (e.getSource() == pošlji){
        	this.addMessage(this.username.getText(),this.inputfield.getText());
        	this.inputfield.setText("");
        	
}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.inputfield) {
			if (e.getKeyChar() == '\n') {
				addMessage(this.username.getText(),this.inputfield.getText());
				this.inputfield.setText("");
			}
			
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
	public void prijava() throws URISyntaxException, IOException {
		URI uri = new URIBuilder("http://chitchat.andrej.com/users")
				.addParameter("username", this.username.getText()).build();
		HttpResponse response = Request.Post(uri).execute().returnResponse();
		InputStream responseText = null;
		if (this.username.getText() == ""){
			uri = new URIBuilder("http://chitchat.andrej.com/users")
					.addParameter("username", System.getProperty("user.name")).build();
		}
		this.odjava.setEnabled(true);
		this.prijava.setEnabled(false);
		this.inputfield.setEditable(true);
		this.username.setEditable(false);
		this.pošlji.setEnabled(true);
		if (response.getStatusLine().getStatusCode()==200) {	
			responseText=response.getEntity().getContent();
						}else if(response.getStatusLine().getStatusCode()==403){

							
			responseText=response.getEntity().getContent();
		}
	} ;
	
	public void odjava() throws URISyntaxException {
		try {
			URI uri = new URIBuilder("http://chitchat.andrej.com/users")
					.addParameter("username", getName()).build();

			String responseBody = Request.Delete(uri).execute().returnContent().asString();

			System.out.println(responseBody);
			this.odjava.setEnabled(false);			
			this.inputfield.setText("");
			this.inputfield.setEditable(false);
			this.pošlji.setEnabled(false);
			this.prijava.setEnabled(true);
			this.username.setEditable(true);
		} catch (IOException e) {
			e.printStackTrace();
			}
}
}
