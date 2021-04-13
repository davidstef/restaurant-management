package businessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import dataLayer.SerializatorRestaurant;

public interface InterfaceRestaurant {
	/**
	 * 
	 * @return un HashMap ce contine comenzile efectuate
	 */
	public HashMap<Comanda ,ArrayList<ArticolMeniu>> getComenzi();
	/**
	 * Modifica comenzile efectuate.
	 * @param hash = HashMapul ce contine comenzile efectuate
	 */
	public void setComenzi(HashMap<Comanda, ArrayList<ArticolMeniu>> hash);
	public ArrayList<ArticolMeniu> getArticoleMeniu();
	/**
	 * 
	 * @param art = obietul de tip ArticolMeniu ce va fi adaugat in lista
	 * @return true daca obietul nu exista si false daca exista deja in lista
	 */
	public boolean adaugaArticol(ArticolMeniu art);
	/**
	 * 
	 * @param art = obietul de tip ArticolMeniu ce va fi sters in lista
	 * @return true daca obietul exista deja si false daca nu exista in lista
	 */
	public boolean stergeArticol(ArticolMeniu art);
	/**
	 * 
	 * @param newArt = obietul de tip ArticolMeniu din lista ce va fi actualizat
	 * @return true daca obietul exista deja si false daca nu exista in lista
	 */
	public boolean actualizeazaArticol(ArticolMeniu newArt);
	/**
	 * 
	 * @param comanda = comanda care urmeaza a fi plasata
	 * @param items = lista de obiecte de tipu ArticolMeniu continute de comanda
	 * @return true daca id-ul si masa introduse nu exista deja si false in caz
	 * contrar
	 */
	public boolean creareComanda(Comanda comanda, ArrayList<ArticolMeniu> items);
	/**
	 * 
	 * @param comanda = comanda a carei pret se va calcula
	 * @return pretul total a l comenzii
	 */
	public double calculeazaPretComanda(Comanda comanda);
	/**
	 * 
	 * @param comanda = comanda a carei bon va fi generat
	 */
	public void creareFacturaComanda(Comanda comanda);
	/**
	 * 
	 * @return obiectul de tip SerializatorRestaurant
	 */
	public SerializatorRestaurant getSerialization();
}
