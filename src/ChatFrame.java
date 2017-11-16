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
import javax.swing.JScrollPane;
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
	private JButton poslji;

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
		
		this.username = new JTextField(System.getProperty("user.name"),10);
		GridBagConstraints usernameConstraint = new GridBagConstraints();
		usernameConstraint.gridx = 1;
		usernameConstraint.gridy = 0;
		polje1.add(username, usernameConstraint);
		username.addKeyListener(this);
		
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
		outputConstraint.weightx = 0.5;
		outputConstraint.weighty = 0.5;
		outputConstraint.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane scrollPaneoutput = new JScrollPane(output);
		polje2.add(output, outputConstraint);
		polje2.add(scrollPaneoutput,outputConstraint);
		
		this.users = new JTextArea(20, 40);
		this.users.setEditable(false);
		GridBagConstraints usersConstraint = new GridBagConstraints();
		usersConstraint.gridx = 1;
		usersConstraint.gridy = 0;
		usersConstraint.weightx = 0.5;
		usersConstraint.weighty = 0.5;
		usersConstraint.fill = GridBagConstraints.HORIZONTAL;
		JScrollPane scrollPaneusers = new JScrollPane(users);
		polje2.add(users, usersConstraint);
		polje2.add(scrollPaneusers,usersConstraint);
		
		this.input = new JPanel();
		GridBagConstraints inputConstraint = new GridBagConstraints();
		inputConstraint.gridx = 0;
		inputConstraint.gridy = 2;
		pane.add(input, inputConstraint);	
				
		this.messagelabel = new JLabel("Sporocilo:");
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
		
		this.poslji = new JButton("Poslji");
		GridBagConstraints posljiConstraint = new GridBagConstraints();
		posljiConstraint.gridx = 2;
		posljiConstraint.gridy = 0;
		input.add(poslji, posljiConstraint);
		poslji.addActionListener(this);
		poslji.setEnabled(false);
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
			if (this.username.getText().equals("")){
				this.addMessage("Server","Vpisite uporabnisko ime!");
			}else{
	        try {
	        	prijava();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
        }if (e.getSource() == odjava){
        	try {
        		this.addMessage("Server","Uspesno ste se odjavili!");
				odjava();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
        }if (e.getSource() == poslji){
        	if (this.inputfield.getText().equals("")){
			}else{
        	this.addMessage(this.username.getText(),this.inputfield.getText());
        	this.inputfield.setText("");
			}        	
}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.inputfield) {
			if (e.getKeyChar() == '\n') {
				if (this.inputfield.getText().equals("")){
				}else{
				addMessage(this.username.getText(),this.inputfield.getText());
				this.inputfield.setText("");			
			}
			
		}
		}
		if (e.getSource() == this.username) {
			if (e.getKeyChar() == '\n') {
				if (this.username.getText().equals("")){
					this.addMessage("Server","Vpisite uporabnisko ime!");
					}else{
				try {
					prijava();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
			}		
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
		this.users.append(this.username.getText() + "\n");
		this.odjava.setEnabled(true);
		this.prijava.setEnabled(false);
		this.inputfield.setEditable(true);
		this.username.setEditable(false);
		this.poslji.setEnabled(true);
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
			this.users.setText(null);
			this.odjava.setEnabled(false);			
			this.inputfield.setText("");
			this.inputfield.setEditable(false);
			this.poslji.setEnabled(false);
			this.prijava.setEnabled(true);
			this.username.setEditable(true);
		} catch (IOException e) {
			e.printStackTrace();
			}
}
}
