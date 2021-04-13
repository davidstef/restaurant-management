package businessLayer;

import java.util.ArrayList;

public class ProdusCompus extends ArticolMeniu {
	private ArrayList<ArticolMeniu> articole = new ArrayList<ArticolMeniu>();
	
	public ProdusCompus(String numeArticol, double pret) {
		super(numeArticol, pret);
	}
	
	public ArrayList<ArticolMeniu> getArticole() {
		return articole;
	}
	
	public void setArticole(ArrayList<ArticolMeniu> articole) {
		this.articole = articole;
	}

	public void adaugaArticol(ArticolMeniu articol) {
		articole.add(articol);
	}
	
	public void stergeArticol(ArticolMeniu articol) {
		articole.remove(articol);
	}

}
