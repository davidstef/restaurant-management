package businessLayer;
import java.io.Serializable;

public class ArticolMeniu implements Serializable { 
	
	private String numeArticol;
	private double pret;
	
	public ArticolMeniu(String numeArticol, double pret) {
		super();
		this.numeArticol = numeArticol;
		this.pret = pret;
	}
	public String getNumeArticol() {
		return numeArticol;
	}
	public void setNumeArticol(String numeArticol) {
		this.numeArticol = numeArticol;
	}
	public double getPret() {
		return pret;
	}
	public void setPret(double pret) {
		this.pret = pret;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeArticol == null) ? 0 : numeArticol.hashCode());
		long temp;
		temp = Double.doubleToLongBits(pret);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticolMeniu other = (ArticolMeniu) obj;
		if (numeArticol == null) {
			if (other.numeArticol != null)
				return false;
		} else if (!numeArticol.equals(other.numeArticol))
			return false;
		if (Double.doubleToLongBits(pret) != Double.doubleToLongBits(other.pret))
			return false;
		return true;
	}
}
