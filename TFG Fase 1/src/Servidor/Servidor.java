package Servidor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Servidor extends JFrame implements Runnable {

	private JPanel contentPane;
	JTextArea registro;
	JTextField mensaje;
	JButton botonEnviar;
	final String ip = "127.0.0.1";	//La aplicacion esta diseñada para ejecutarse en local
	final int puertoservidor = 5001;
	final int puertocliente = 5002;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servidor frame = new Servidor();
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
	public Servidor() {
		setTitle("Servidor");
		
		registro = new JTextArea();			//Creo el registro donde se verán los mensajes enviados y recibidos
		registro.setBounds(10, 10, 360, 300);
		getContentPane().add(registro);
		
		mensaje = new JTextField();			//Establezco el campo para introducir los mensajes que luego se enviaran
		mensaje.setBounds(10, 320, 200, 20);	//Se le dan unas medidas estandar
		getContentPane().add(mensaje);						//Se añade el campo a la ventana del cliente, donde ya se puede escribir
		
		botonEnviar = new JButton();		//Ahora se crea el boton de enviar, para una vez escrito algo en el campo de texto,
		botonEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Socket socket_sr = new Socket(ip, puertocliente);
					
					OutputStream flujo_salida = socket_sr.getOutputStream();
					DataOutputStream flujo_s = new DataOutputStream(flujo_salida);
					
					flujo_s.writeUTF("Servidor: " + mensaje.getText());//Se utiliza la funcion writeUTF, que es la que envia strings,
																       //ademas de que el string que se manda es el del texto
				  													   //introducido en el campo de la ventana
					
					socket_sr.close();			//Por ultimo cerramos el socket para que no se envie nada mas
					
					registro.append("Yo: " + mensaje.getText()); //Guardo el mensaje enviado en el chat del Servidor
					registro.append("\n");
					mensaje.setText(null);
					
				} catch(Exception ex) {
					System.out.println("ERROR en actionPerformed del Cliente: " + ex.getMessage());
				}
			}
		});
		botonEnviar.setText("Enviar");		//que se pueda enviar al servidor en este caso
		botonEnviar.setBounds(220, 320, 150, 20);
		getContentPane().add(botonEnviar);
		
		getContentPane().setLayout(null);					//Por ultimo en el constructor establezco el layout para que no se pongan los campos
		setSize(400,400);					//unos encima de otros, le doy el tamaño a la ventana y la pongo visible para que
		setVisible(true);					//se pueda ver durante la ejecución.
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);//Cuando se cierra la ventana acabo la ejecucion
		
		Thread thread = new Thread(this);	//Se necesita crear un hilo para que la funcion run() se ejecute, y de esta manera, 
		thread.start();						//se puedan recibir y leer los mensajes entre cliente y servidor
	}

	@Override
	public void run() {
		
		try {
			
			ServerSocket socket_servidor = new ServerSocket(puertoservidor);
			
			while(true) {
			
				Socket socket_cl = socket_servidor.accept();
				
				InputStream flujo_entrada = socket_cl.getInputStream();
				DataInputStream flujo_e = new DataInputStream(flujo_entrada);
				
				String mensaje = flujo_e.readUTF(); //Utilizo ahora la funcion equivalente a writeUTF pero para leer, readUTF
				registro.append(mensaje);			//Guardo el mensaje en el registro de mensajes, es decir, en la ventana
				registro.append("\n");
				
				socket_cl.close();
			}
			
		} catch(Exception ex) {
			System.out.println("Error en run() del Servidor: " + ex.getMessage());
		}
	}

}
