import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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
		polje1.add(prijava, prijavaConstraint);
		//server.login(System.getProperty("user.name"));
		
		this.odjava = new JButton("Odjava");
		GridBagConstraints odjavaConstraint = new GridBagConstraints();
		odjavaConstraint.gridx = 3;
		odjavaConstraint.gridy = 0;
		polje1.add(odjava, odjavaConstraint);
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
		
		this.pošlji = new JButton("Pošlji");
		GridBagConstraints pošljiConstraint = new GridBagConstraints();
		pošljiConstraint.gridx = 2;
		pošljiConstraint.gridy = 0;
		input.add(pošlji, pošljiConstraint);
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
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getSource() == this.inputfield) {
			if (e.getKeyChar() == '\n') {
				this.addMessage(System.getProperty("user.name"), this.inputfield.getText());
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
}
