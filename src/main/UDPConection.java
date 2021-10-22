package main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import events.OnMessageListener;


public class UDPConection extends Thread {

	private DatagramSocket socket;
	private OnMessageListener observer;

	public void setObserver(OnMessageListener observer) {
		this.observer = observer;
	}
	
	@Override
	public void run() {
		try { 
			// Escuchar
			socket = new DatagramSocket(9001);

			// 2.Esperar mensaje: Datagram se llaman

			while (true) {
				byte[] buffer = new byte[100];

				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				// Esperando datagrama
				System.out.println("Esperando datagrama");
				
				  
				try {
				socket.receive(packet);
				//datagrama recibido
				String mensaje = new String(packet.getData()).trim(); // trim quita los bytes vacios
				System.out.println("datagrama recibido: "+ mensaje);
				observer.recibirMensaje(mensaje);
				

			
				}catch (Exception e) {
					 e.printStackTrace();
				}

		}  
		
	}catch (Exception e){
        e.printStackTrace();
	}
	}
	
	public void enviarMensaje(String mensaje) {
		new Thread(

				() -> {

					try {
						InetAddress ip = InetAddress.getByName("192.168.1.50"); // 10.0.2.2
						DatagramPacket packet = new DatagramPacket(mensaje.getBytes(), mensaje.getBytes().length, ip,
								9000);
						socket.send(packet);

					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // 10.0.0.2.2
					catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

		).start();

	}
	
}
