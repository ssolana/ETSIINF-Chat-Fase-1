package Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Chat extends JPanel {

	JTextArea registro;
	JTextField mensaje;
	JButton botonEnviar;
	final String ip = "127.0.0.1";	//La aplicacion esta diseñada para ejecutarse en local
	final int puertoservidor = 5001;
	final int puertocliente = 5002;
	
	/**
	 * Create the panel.
	 */
	public Chat() {
		registro = new JTextArea();			//Creo el registro donde se verán los mensajes enviados y recibidos
		registro.setBounds(10, 10, 360, 300);
		add(registro);
		
		mensaje = new JTextField();			//Establezco el campo para introducir los mensajes que luego se enviaran
		mensaje.setBounds(10, 320, 200, 20);	//Se le dan unas medidas estandar
		add(mensaje);						//Se añade el campo a la ventana del cliente, donde ya se puede escribir
		
		botonEnviar = new JButton();		//Ahora se crea el boton de enviar, para una vez escrito algo en el campo de texto,
		botonEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					Socket socket_cliente = new Socket(ip, puertoservidor);
					
					OutputStream flujo_salida = socket_cliente.getOutputStream();
					DataOutputStream flujo_s = new DataOutputStream(flujo_salida);
					
					flujo_s.writeUTF("Cliente: " + mensaje.getText());//Se utiliza la funcion writeUTF, que es la que envia strings,
																	  //ademas de que el string que se manda es el del texto
													  	              //introducido en el campo de la ventana
					
					
					socket_cliente.close();			  //Por ultimo cerramos el socket para que no se envie nada mas
					
					registro.append("Yo: " + mensaje.getText()); //Guardo el mensaje enviado en el chat del Cliente
					registro.append("\n");
					guardarChat("Yo: " + mensaje.getText() + "\n");
					mensaje.setText(null);
					
				} catch(Exception ex) {
					System.out.println("ERROR en actionPerformed del Cliente: " + ex.getMessage());
				}
			}
		});
		botonEnviar.setText("Enviar");		//que se pueda enviar al servidor en este caso
		botonEnviar.setBounds(220, 320, 150, 20);
		add(botonEnviar);
		
		setLayout(null);					//Por ultimo en el constructor establezco el layout para que no se pongan los campos
		setSize(400,400);					//unos encima de otros, le doy el tamaño a la ventana y la pongo visible para que
		setVisible(true);					//se pueda ver durante la ejecución.
		
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
