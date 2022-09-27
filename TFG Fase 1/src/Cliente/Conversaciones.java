package Cliente;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;

public class Conversaciones extends JPanel {

	public ArrayList<String> personas = new ArrayList<String>();
	public ArrayList<JButton> botones = new ArrayList<JButton>();
	
	JPanel panel;
	String nmatr = "a180262";
	JButton btnAadir;
	JScrollPane scrollPane;
	
	/**
	 * Create the panel.
	 */
	public Conversaciones() {
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 440, 219);
		add(scrollPane);
		scrollPane.setBorder(null);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		btnAadir = new JButton("A\u00F1adir");
		btnAadir.setBounds(178, 230, 89, 23);
		add(btnAadir);
	}

}
