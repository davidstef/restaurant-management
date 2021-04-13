package presentationLayer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;

import businessLayer.ArticolMeniu;
import businessLayer.Comanda;
import businessLayer.InterfaceRestaurant;
import businessLayer.Restaurant;

public class WaiterGUI extends Observable implements ActionListener {

	private ArrayList<ArticolMeniu> menuItems = new ArrayList<ArticolMeniu>();
	private JPanel principalPanel = new JPanel();
	private JFrame mainFrame = new JFrame("~Waiter~");
	private InterfaceRestaurant iR;
	private ArrayList<Observer> observers = new ArrayList<>();
	private JTextField idText = new JTextField(8);
	private JTextField tableText = new JTextField(8);
	private JLabel idL = new JLabel("Order ID: ");
	private JLabel tableL = new JLabel("Nr. Table: ");
	private JButton btnComanda = new JButton("ADD ORDER");
	private JButton btnShow = new JButton("VIEW ORDERS");
	private JButton btnFinalizare = new JButton("BILL");
	private JButton btnItem = new JButton("ADD ITEM");
	private JComboBox produseMeniu = new JComboBox();
	private JTextArea orderItems = new JTextArea(10, 40);
	private JScrollPane scroll = new JScrollPane(orderItems);
	private JTable jtable = new JTable();
	private JPanel pan = new JPanel();
	private JButton btnClear = new JButton("CLEAR AREA");
	JPanel p = new JPanel();
	

	public WaiterGUI(Restaurant restaurant) {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER));
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		p3.setLayout(new FlowLayout(FlowLayout.CENTER));
		p4.setLayout(new FlowLayout(FlowLayout.CENTER));
		principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.Y_AXIS));
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		produseMeniu.setSize(100, 100);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		p1.add(idL);
		p1.add(idText);
		p2.add(tableL);
		p2.add(tableText);
		p3.add(produseMeniu);
		p3.add(scroll);
		p4.add(btnItem);
		p4.add(btnComanda);
		p4.add(btnClear);
		p4.add(btnShow);
		p4.add(btnFinalizare);
		principalPanel.add(p1);
		principalPanel.add(p2);
		principalPanel.add(p3);
		principalPanel.add(p4);
		pan.add(jtable);
		principalPanel.add(pan);
		mainFrame.add(principalPanel);
		mainFrame.setSize(570, 410);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		iR = restaurant;
		btnShow.addActionListener(this);
		orderItems.setEditable(false);
		btnItem.addActionListener(this);
		btnClear.addActionListener(this);
		btnComanda.addActionListener(this);
		btnFinalizare.addActionListener(this);
		computeWithItems();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(size.width / 2 - mainFrame.getSize().width / 2,
				size.height / 2 - mainFrame.getSize().height / 2);
		mainFrame.setResizable(false);
	}

	private static int noRows = 25;

	public void createOrder() {
		Integer id = 0;
		Integer masa = 0;
		try {
			id = Integer.parseInt(idText.getText());
			masa = Integer.parseInt(tableText.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incorrect ID or table No.!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(orderItems.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please enter the products", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArrayList<ArticolMeniu> newMenuItems = new ArrayList<ArticolMeniu>();
		for (int i = 0; i < menuItems.size(); i++) {
			newMenuItems.add(menuItems.get(i));
		}
		Comanda c = new Comanda(id, masa);
		if (iR.creareComanda(c, newMenuItems) == false) {
			JOptionPane.showMessageDialog(null, "The Id or Table is already occupied! ", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		iR.creareComanda(c, newMenuItems);
		menuItems.clear();
		showOrder(c);
		noRows++;
	}

	public void generateBill() {
		Integer id = 0;
		Integer masa = 0;
		try {
			id = Integer.parseInt(idText.getText());
			masa = Integer.parseInt(tableText.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please enter the ID and the table number!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> entry : iR.getSerialization().readFileO().entrySet()) {
			if (id == entry.getKey().getIdComanda()) {
				iR.creareFacturaComanda(entry.getKey());
			}
		}
	}

	public void addItem() {
		for (ArticolMeniu art : iR.getSerialization().readFile()) {
			if (art.getNumeArticol().equals(produseMeniu.getSelectedItem().toString())) {
				System.out.println(art.getNumeArticol());
				menuItems.add(art);
				orderItems.setText(orderItems.getText() + produseMeniu.getSelectedItem().toString() + "\n");
			}
		}
	}

	public void viewOrders() {
		String structure[] = { "ID", "NR. MASA", "DATA CURENTA", "PRODUSE COMANDA" };
		String articole[][] = new String[noRows][4];
		int cont = 0;
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> entry : iR.getSerialization().readFileO().entrySet()) {
			articole[cont][2] = entry.getKey().getData() + "";
			articole[cont][0] = entry.getKey().getIdComanda() + "";
			articole[cont][1] = entry.getKey().getNrMasa() + "";
			String nume = "";
			for (ArticolMeniu art : iR.getSerialization().readFile()) {
				for (ArticolMeniu item : entry.getValue()) {
					if (art.equals(item))
						nume += art.getNumeArticol() + ", ";
				}
			}
			articole[cont][3] = nume;
			cont++;
		}
		JTable tab = new JTable(articole, structure);
		JScrollPane scr = new JScrollPane(tab);
		p.removeAll();
		p.add(scr);
		principalPanel.add(p);
	}

	public void computeWithItems() {
		for (ArticolMeniu articol : iR.getSerialization().readFile()) {
			produseMeniu.addItem(articol.getNumeArticol());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnComanda == e.getSource()) {
			this.createOrder();
		} else if (e.getSource() == btnItem) {
			addItem();
		} else if (e.getSource() == btnShow) {
			pan.setVisible(false);
			this.viewOrders();
			mainFrame.revalidate();
			mainFrame.setSize(570, 830);
		} else if (e.getSource() == btnClear) {
			menuItems.clear();
			String empty = new String("");
			orderItems.setText(empty);
		} else if (e.getSource() == btnFinalizare) {
			generateBill();
		}
	}

	public ArrayList<Observer> getObservers() {
		return observers;
	}

	public void setObservers(ArrayList<Observer> observers) {
		this.observers = observers;

	}

	public void notifOrder(Observable obs, String articole) {
		for (Observer o : observers) {
			o.update(obs, articole);
		}
	}

	public JFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void registerObserver(Observer obs) {
		observers.add(obs);
	}

	public void showOrder(Comanda c) {
		String data = "*The order " + c.getIdComanda() + " table No. + " + c.getNrMasa() + " date: " + c.getData()
				+ ":\n";
		int i = 0;
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> entry : iR.getSerialization().readFileO().entrySet()) {
			if (c.equals(entry.getKey()))
				data = data + entry.getValue().get(i).getNumeArticol() + ", " + entry.getValue().get(i).getPret();
			i++;
		}
		setChanged();
		notifOrder(this, data);
	}

	public WaiterGUI getWaiterGUI() {
		return this;
	}

}
