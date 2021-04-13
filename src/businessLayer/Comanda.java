package businessLayer;

import java.awt.MenuItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Comanda implements Serializable {
	// private ArrayList<ArticolMeniu> articole;
	private int idComanda;
	private int nrMasa;
	private Date data;

	public Comanda(int idComanda, int nrMasa) {
		super();
		// this.articole = menuItems;
		this.idComanda = idComanda;
		this.nrMasa = nrMasa;
		this.data = new Date();
	}

	/*
	 * public ArrayList<ArticolMeniu> getOrders() { return articole; }
	 * 
	 * public void setOrders(ArrayList<ArticolMeniu> orders) { this.articole =
	 * orders; }
	 */

	public int getIdComanda() {
		return idComanda;
	}

	public void setIdComanda(int idComanda) {
		this.idComanda = idComanda;
	}

	public int getNrMasa() {
		return nrMasa;
	}

	public void setNrMasa(int nrMasa) {
		this.nrMasa = nrMasa;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prim = 29;
		int res = 1;
		if (data == null)
			res *= prim;
		else
			res = res * prim + data.hashCode();
		res = res * prim + idComanda;
		res = res * prim + nrMasa;
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Comanda com = (Comanda) obj;
		if (data == null) {
			if (com.data != null)
				return false;
		} else if (!data.equals(com.data) || idComanda != com.idComanda || nrMasa != com.nrMasa)
			return false;
		return true;
	}

}
