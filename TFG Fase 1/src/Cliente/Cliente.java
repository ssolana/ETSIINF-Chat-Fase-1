package Cliente;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Cliente extends JFrame implements Runnable {

	private JPanel contentPane;
	JTextArea registro;
	JTextField mensaje;
	JButton botonEnviar;
	final String ip = "127.0.0.1";	//La aplicacion esta diseñada para ejecutarse en local
	final int puertoservidor = 5001;
	final int puertocliente = 5002;
	Conversaciones conversaciones = new Conversaciones();
	Chat chat = new Chat();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cliente frame = new Cliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cliente() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		setTitle("Cliente");
		
		JPanel panelInicial = new JPanel();
		contentPane.add(panelInicial);
		panelInicial.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 400, 270);
		getContentPane().add(scrollPane);
		scrollPane.setBorder(null);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAadir = new JButton("A\u00F1adir");
		btnAadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String amigo = JOptionPane.showInputDialog("¿Con quién quieres hablar?");
				JButton nuevoChat = new JButton(amigo);
				nuevoChat.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						contentPane.removeAll();
						contentPane.add(chat);
						contentPane.repaint();
						contentPane.revalidate();
						setTitle(amigo);
					}
				});
				panel.add(nuevoChat);
				contentPane.repaint();
				contentPane.revalidate();
				
			}
		});
		btnAadir.setBounds(154, 300, 89, 23);
		getContentPane().add(btnAadir);
		
		setLayout(null);					//Por ultimo en el constructor establezco el layout para que no se pongan los campos
		setSize(400,400);					//unos encima de otros, le doy el tamaño a la ventana y la pongo visible para que
		setVisible(true);					//se pueda ver durante la ejecución.
		
		Thread thread = new Thread(this);	//Se necesita crear un hilo para que la funcion run() se ejecute, y de esta manera, 
		thread.start();						//se puedan recibir y leer los mensajes entre cliente y servidor
	}

	@Override
	public void run() {
		
		try {
			
			ServerSocket socket_servidor = new ServerSocket(puertocliente);//Creo otro ServerSocket para que el cliente pueda estar
																   //"escuchando", y de esta manera pueda recibir mensajes
			
			while(true) {
			
				Socket socket_cl = socket_servidor.accept();
				
				InputStream flujo_entrada = socket_cl.getInputStream();
				DataInputStream flujo_e = new DataInputStream(flujo_entrada);
				
				String mensaje = flujo_e.readUTF(); //Utilizo ahora la funcion equivalente a writeUTF pero para leer, readUTF
				chat.registro.append(mensaje);			//Guardo el mensaje en el registro de mensajes, es decir, en la ventana
				chat.registro.append("\n");
				guardarChat(mensaje + "\n");
				
				socket_cl.close();
			}
			
		} catch(Exception ex) {
			System.out.println("Error en run() del Cliente: " + ex.getMessage());
		}
		
	}
	
	public void guardarChat(String lineToAppend) {
		try
        {    
            String filePath = "C:\\Users\\Sergio\\Desktop\\chat.txt";
            FileOutputStream f = new FileOutputStream(filePath, true);
            byte[] byteArr = lineToAppend.getBytes(); //converting string into byte array
            f.write(byteArr);
            f.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}

}
