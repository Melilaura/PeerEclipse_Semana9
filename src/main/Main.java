package main;

import java.util.ArrayList;

import com.google.gson.Gson;

import events.OnMessageListener;
import model.Orden;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Main extends PApplet implements OnMessageListener {

	// conexion udp
	private UDPConection udp;
	// gson
	private Gson gson;
	// array de ordenes
	private ArrayList<Orden> ordenes;

	// img de ordenes
	private PImage pastelF1, pastelF2, pastelA, BBtea;

	public static void main(String[] args) {
		PApplet.main("main.Main");
	}

	@Override
	public void settings() // void Awake
	{
		size(500, 500);
	}

	@Override
	public void setup() // void Start
	{
		udp = new UDPConection();
		udp.setObserver(this);
		udp.start();

		gson = new Gson();

	
		
		
		

	

		// array ordenes
		ordenes = new ArrayList<>();

		// Img ordenes
		pastelF1 = loadImage("data/mirapasteldefresa.png");
		pastelF2 = loadImage("data/otropasteldefresaje.png");
		pastelA = loadImage("data/pasteldearandanosyei.png");
		BBtea = loadImage("data/sibuenounte.png");

	}

	@Override
	public void draw() // void Update
	{
		background(241, 234, 181);
		fill(80);
		textAlign(CENTER);
		textSize(18);
		text("PEDIDOS", 250, 20);
		textSize(14);
		
		//System.out.println(mouseX+" , "+ mouseY);

		
		
		for (int i = 0; i < ordenes.size(); i++) {
			
			int y = 60+ (100 * i);
			int x = 10;
			if(y>=460) {
				x=270;
				y=60 + ((100 *i)-400);
			}
			
			switch (ordenes.get(i).getOrden()) {
			
			case "pastel1":

				image(pastelF1, x, y , 80, 80);

				textAlign(LEFT);
				text("Pastel Fresas", x + 85, y+20);

				text(ordenes.get(i).getTiempo(), x + 85, y+40);

				break;

			case "pastel2":

				image(pastelF2, x, y, 80, 80);

				textAlign(LEFT);
				text("Rollo fresas", x + 85, y +20);

				text(ordenes.get(i).getTiempo(), x + 85, y+40);

				break;

			case "pastelA":

				image(pastelA, x, y , 80, 80);

				textAlign(LEFT);
				text("Pastel Arandanos", x + 85, y+20);

				text(ordenes.get(i).getTiempo(), x + 85, y+40);

				break;
			case "bbtea":

				image(BBtea, x, y , 80, 80);

				textAlign(LEFT);
				text("Bubble Tea", x + 85, y+20);

				textSize(14);
				text(ordenes.get(i).getTiempo(), x + 85, y+40);

				break;
			}
			
			

			
		}

	}

	@Override
	public void recibirMensaje(String mensaje) {

		Orden orden = gson.fromJson(mensaje, Orden.class);

		orden.setNum("Pedido No. " + ordenes.size());

		if (ordenes.size() < 8) {
			ordenes.add(orden);
		}

	}

	@Override
	public void mousePressed() {
		for (int i = 0; i < ordenes.size(); i++) {
			int y = 60+ (100 * i);
			int x = 10;
			if(y>=460) {
				x=270;
				y=60 + ((100 *i)-400);
			}
			if (dist(mouseX, mouseY, x, y + (100 * i)) < 100) {
				udp.enviarMensaje(ordenes.get(i).getNum() + ":" + ordenes.get(i).getOrden() + " ya esta listo");
				ordenes.remove(i);
			}
		}

	}

}
	
	
	
	

