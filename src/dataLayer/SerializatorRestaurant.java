package dataLayer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import businessLayer.ArticolMeniu;
import businessLayer.Comanda;
import businessLayer.Restaurant;

public class SerializatorRestaurant {
	private static String ser = "restaurant.ser";
	private static String ord = "Orders.ser";
	
	public static String getSer() {
		return ser;
	}

	public static void setSer(String ser) {
		SerializatorRestaurant.ser = ser;
	}

	public static void writeToFile(ArrayList<ArticolMeniu> articole) {
		try {
			ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(ser));
			obj.writeObject(articole);
			System.out.println("Mere serializarea");
			obj.close();
		} catch (IOException e) {		
			System.out.println("Nu s-a putut realiza serializarea!");	
		}
	}
	
	public ArrayList<ArticolMeniu> readFile() {
		ArrayList<ArticolMeniu> articole = new ArrayList<ArticolMeniu>();
		try {
		ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(ser));
		try {
			articole = (ArrayList<ArticolMeniu>) objInput.readObject();
			System.out.println("Mere deserializarea");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		}
		objInput.close();
		} catch (IOException e) {
			System.out.println("Empty .ser!"); 
		}	
		return articole;
	}
	
	public static void writeToFileO(HashMap<Comanda, ArrayList<ArticolMeniu>> orders) {
		try {
			ObjectOutputStream obj = new ObjectOutputStream(new FileOutputStream(ord));
			obj.writeObject(orders);
			System.out.println("Mere serializarea");
			obj.close();
		} catch (IOException e) {		
			System.out.println("Nu s-a putut realiza serializarea!");	
		}
	}
	
	public static HashMap<Comanda, ArrayList<ArticolMeniu>> readFileO() {
		HashMap<Comanda, ArrayList<ArticolMeniu>> comenzi = new HashMap<Comanda, ArrayList<ArticolMeniu>>();
		try {
		ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(ord));
		try {
			comenzi = (HashMap<Comanda, ArrayList<ArticolMeniu>>) objInput.readObject();
			System.out.println("Mere deserializarea");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		}
		objInput.close();
		} catch (IOException e) {
			System.out.println("Empty .ser!");
		}	
		return comenzi;
	}
	
	public static HashMap<Comanda, ArrayList<ArticolMeniu>> readFileRest() {
		Restaurant r = new Restaurant();
		try {
		ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(ord));
		try {
			r = (Restaurant) objInput.readObject();
			System.out.println("Mere deserializarea");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
		}
		objInput.close();
		} catch (IOException e) {
			System.out.println("Empty .ser!");
		}	
		return r.getComenzi();
	}
	
	
}
