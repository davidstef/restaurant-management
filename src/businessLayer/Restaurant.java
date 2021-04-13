package businessLayer;

import java.io.FileWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import dataLayer.FileWriterTxt;
import dataLayer.SerializatorRestaurant;

public class Restaurant implements InterfaceRestaurant, Serializable {

	private SerializatorRestaurant serial = new SerializatorRestaurant();
	private HashMap<Comanda, ArrayList<ArticolMeniu>> comenzi = serial.readFileO();
	private ArrayList<ArticolMeniu> articole = serial.readFile();
	private FileWriterTxt fileWrite = new FileWriterTxt();
	
	@Override
	public HashMap<Comanda, ArrayList<ArticolMeniu>> getComenzi() {
		return comenzi;
	}

	@Override
	public ArrayList<ArticolMeniu> getArticoleMeniu() {
		return articole;
	}

	public void setArticole(ArrayList<ArticolMeniu> articole) {
		this.articole = articole;
	}

	public void setComenzi(HashMap<Comanda, ArrayList<ArticolMeniu>> comenzi) {
		this.comenzi = comenzi;
	}
	
	/**
	 *  Aceasta este de metoda de tipul "well formed" care verifica corectitudinea
	 * operatiilor implementate
	 * @return true in cazul corectitudinii operatiilor si false daca cel putin o verificare
	 * nu este corecta
	 */
	private boolean isValid() {
		int counter = 0;
		if (articole == null || comenzi == null)
			return false;
		for (int i = 0; i < articole.size(); i++) {
			if (articole.get(i).equals(articole.get(counter)) == false)
				return false;
			counter++;
		}
		counter = 0;
		int nr = 0;
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> map : comenzi.entrySet()) {
			nr = 0;
			ArrayList<ArticolMeniu> art = map.getValue();
			for (int i = 0; i < art.size(); i++) {
				if (art.get(i).equals(art.get(nr)) == false)
					return false;
			}
			nr++;
			if (map.getValue().equals(comenzi.get(map.getKey())) == false)
				return false;
			counter++;
		}
		return true;
	}
	
	/**
	 * 	Adauga un Obiect de tipul MenuItem in lista de artticole (MenuItems) in
	 * cazul in care acesta nu exista deja
	 */
	@Override
	public boolean adaugaArticol(ArticolMeniu art) {
		assert art != null;
		assert isValid();
		int dimens = 1 + articole.size();
		for (ArticolMeniu articol : articole) {
			if (articol.getNumeArticol().equals(art.getNumeArticol()))
				return false;
		}
		articole.add(art);
		serial.writeToFile(articole);
		assert articole.size() == dimens;
		assert isValid();
		return true;
	}

	public void sterge(ProdusCompus c, ArticolMeniu a, ArrayList<ArticolMeniu> art) {
		c.stergeArticol(a);
		if (0 == c.getArticole().size())
			art.add(c);
	}
	
	/**
	 * 	Sterge un Obiect de tipul MenuItem din lista de MenuItems cu conditia ca el 
	 * sa existe deja in lista de articole
	 */
	@Override
	public boolean stergeArticol(ArticolMeniu art) {
		assert art != null;
		assert isValid();
		assert 0 < articole.size();
		int dimens = articole.size() - 1;
		boolean ok = false;
		for (ArticolMeniu articol : articole) {
			if (articol.getNumeArticol().equals(art.getNumeArticol()))
				ok = true;
		}
		ArrayList<ArticolMeniu> articoleVechi = new ArrayList<ArticolMeniu>();
		for (int i = 0; i < articole.size(); i++) {
			if (articole.get(i).getNumeArticol().equals(art.getNumeArticol())) {
				articoleVechi.add(articole.get(i));
			} else {
				if (articole.get(i) instanceof ProdusCompus) {
					((ProdusCompus) articole.get(i)).stergeArticol(art);
				}
			}
		}
		articole.removeAll(articoleVechi);
		serial.writeToFile(articole);
		assert dimens == articole.size();
		assert isValid();
		return ok;
	}
	
	/**
	 * 	Actualizeaza un Obiect de tipul MenuItem din lista de MenuItems, 
	 * mai exact ii actualizeaza pretul vechi cu cel nou dat de utilizator.
	 * Conditia este ca obiectul sa existe deja in lista
	 */
	@Override
	public boolean actualizeazaArticol(ArticolMeniu newArt) {
		assert newArt != null;
		assert isValid();
		assert 0 < articole.size();
		int dimens = articole.size();
		boolean ok = false;
		for (ArticolMeniu articol : articole) {
			if (articol.getNumeArticol().equals(newArt.getNumeArticol()))
				ok = true;
		}
		for (int i = 0; i < articole.size(); i++) {
			if (articole.get(i).getNumeArticol().equals(newArt.getNumeArticol())) {
				articole.get(i).setPret(newArt.getPret());
			}
		}
		serial.writeToFile(articole);
		assert dimens == articole.size();
		assert isValid();
		return ok;
	}
	
	/**
	 * 	Se foloseste un HashMap pentru a crea o noua comanda, cheia va fi comanda,
	 * iar valoare din HashMap va fi ArrayListul de tipul MenuItems 
	 * @param comanda = obiectul de tip Comanda care va fi efectuat
	 * @param items = lista de obiecte de tipul MenuItem care va fi continuta
	 * in comanda 
	 */
	@Override
	public boolean creareComanda(Comanda comanda, ArrayList<ArticolMeniu> items) {
		assert comanda != null;
		assert isValid();
		assert 0 < comenzi.size();
		int dimens = comenzi.size();
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> map : comenzi.entrySet()) {
			if (map.getKey().getIdComanda() == comanda.getIdComanda()
					|| map.getKey().getNrMasa() == comanda.getNrMasa()) {
				return false;
			}
		}
		comenzi.put(comanda, items);
		// orders.add(comanda);
		// serial.writeToFileO(orders);
		serial.writeToFileO(comenzi);
		assert dimens == comenzi.size();
		assert isValid();
		return true;
	}
	
	/**
	 * 	Calculeaza pretul total al comenzii adunand preturile fiecarui
	 * produs continut in comanda.
	 * @param comanda = comanda careia ii va fi calculat pretul total
	 */
	@Override
	public double calculeazaPretComanda(Comanda comanda) {
		assert comanda != null;
		assert 0 < comenzi.size();
		double pret = 0;
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> entry : comenzi.entrySet()) {
			if (entry.getKey().equals(comanda))
				for (ArticolMeniu art : entry.getValue()) {
					pret = pret + art.getPret();
				}
		}
		assert isValid();
		return pret;
	}

	/**
	 * Genereaza factura pentru o comanda parcurgand hashMapul si apeland 
	 * metoda generateBill() din clasa FileWriterTxt.
	 * @param comanda = comanda pentru care se va elibera o factura
	 */
	@Override
	public void creareFacturaComanda(Comanda comanda) {
		assert comanda != null;
		assert isValid();
		try {
			for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> entry : comenzi.entrySet()) {
				if (entry.getKey().equals(comanda))
					FileWriterTxt.genarateBill(entry.getValue(), comanda, calculeazaPretComanda(comanda));
			}
		} catch (Exception e) {
			System.out.println("ERROR: the file have not created!");
		}
		assert isValid();
	}
	
	/**
	 * Returneaza obiectul de tip SerializatorRestaurant.
	 */
	@Override
	public SerializatorRestaurant getSerialization() {
		return this.serial;
	}
	
	/**
	 * Creeaza un String cu produsele pe care le contine o comanda si 
	 * care va fi afisat in GUI.
	 * @param c = comanda ale carei articole din meniu vor fi returnate
	 * @return un String ce va contine numele fiecarui articol existent 
	 * in comanda plasata
	 */
	public String showItems(Comanda c) {
		String items = "";
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> map : comenzi.entrySet()) {
			if (map.getKey().equals(c))
				for (ArticolMeniu art : map.getValue()) {
					items = items + art.getNumeArticol() + " ";
				}
		}
		return items;
	}

}
