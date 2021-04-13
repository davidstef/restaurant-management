package dataLayer;

import java.io.*;
import java.util.ArrayList;

import businessLayer.ArticolMeniu;
import businessLayer.Comanda;

public class FileWriterTxt {
	private static int index = 0;

	public FileWriterTxt() {

	}

	public static void genarateBill(ArrayList<ArticolMeniu> articole, Comanda c, double pret) {
		final String PATH = "C:\\Users\\Pc\\eclipse-workspace\\Project4TP\\bill_" + index + ".txt";
		ArrayList<String> text = new ArrayList<String>();
		text.add("Bill NO. " + index + "\n");
		text.add("ID order: " + c.getIdComanda());
		text.add("Table No. " + c.getNrMasa());
		text.add("Date: " + c.getData() + "\n");
		for (int i = 0; i < articole.size(); i++) {
			text.add("Name: " + articole.get(i).getNumeArticol());
			text.add("Price: " + articole.get(i).getPret() + " RON\n");
		}
		text.add("___________________________________________________________________" + "\n");
		text.add("Total price: " + pret + " RON");
		try (FileWriter fileWriter = new FileWriter(PATH); BufferedWriter buf = new BufferedWriter(fileWriter)) {
			for (int i = 0; i < text.size(); i++) {
				if (0 != text.get(i).compareTo("\n"))
					buf.write(text.get(i));
				buf.newLine();
			}
			index++;
		} catch (IOException e) {
			System.out.println("ERROR: The file haven't created!");
		}
	}

}
