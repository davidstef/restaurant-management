package presentationLayer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import businessLayer.*;

public class AdministratorGUI extends JFrame implements ActionListener {
	private InterfaceRestaurant iR;
	private Restaurant restaurant = new Restaurant();
	private JPanel principalPanel = new JPanel();
	private JFrame mainFrame = new JFrame("~Administrator~");
	private TextField numeText = new TextField("Insert the product name");
	private JLabel numeL = new JLabel("Name: ");
	private JButton btnUpdate = new JButton("UPDATE");
	private JButton btnDelete = new JButton("DELETE");
	private TextField pretText = new TextField("Insert the price");
	private JLabel pretL = new JLabel("Pret: ");
	private JButton btnAdd = new JButton("ADD");
	private JButton btnShow = new JButton("MENU DETAILS");
	private TextField compositonText = new TextField("Insert the composite");
	private JLabel compositionL = new JLabel("Composition: ");
	private JTable jtable = new JTable();
	private JPanel pan = new JPanel();
	JPanel p = new JPanel();

	public AdministratorGUI(Restaurant restaurant) {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		JPanel p4 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.LEFT));
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p3.setLayout(new FlowLayout(FlowLayout.LEFT));
		p4.setLayout(new FlowLayout(FlowLayout.LEFT));
		principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.Y_AXIS));
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		p1.add(numeL);
		p1.add(numeText);
		p2.add(pretL);
		p2.add(pretText);
		p3.add(compositionL);
		p3.add(compositonText);
		p4.add(btnAdd);
		p4.add(btnUpdate);
		p4.add(btnDelete);
		p4.add(btnShow);
		principalPanel.add(p1);
		principalPanel.add(p2);
		principalPanel.add(p3);
		principalPanel.add(p4);
		pan.add(jtable);
		principalPanel.add(pan);
		mainFrame.add(principalPanel);
		mainFrame.setSize(500, 250);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		btnAdd.addActionListener(this);
		btnDelete.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnShow.addActionListener(this);
		iR = restaurant;
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(size.width / 2 - mainFrame.getSize().width / 2,
				size.height / 2 - mainFrame.getSize().height / 2);
		mainFrame.setResizable(false);
	}

	public void addItem() {
		String nume = "";
		Double pret = 0.0;
		try {
			nume = numeText.getText();
			pret = Double.parseDouble(pretText.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incorrect name or price!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (compositonText.getText().isEmpty()) {
			ProdusDeBaza p = new ProdusDeBaza(nume, pret);
			if (iR.adaugaArticol(p) == false) {
				JOptionPane.showMessageDialog(null, "The item already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			String[] items = compositonText.getText().split("[, ]");
			ProdusCompus cp = new ProdusCompus(nume, pret);
			for (int i = 0; i < items.length; i++) {
				for (ArticolMeniu it : iR.getArticoleMeniu()) {
					if (it.getNumeArticol().equals(items[i]))
						cp.adaugaArticol(it);
				}
			}
			if (iR.adaugaArticol(cp) == false) {
				JOptionPane.showMessageDialog(null, "The item already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		noRows++;
	}

	public void updateItem() {
		String nume = "";
		Double pret = 0.0;
		try {
			nume = numeText.getText();
			pret = Double.parseDouble(pretText.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incorrect name or price!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArticolMeniu newArt = new ArticolMeniu(nume, pret);
		if (iR.actualizeazaArticol(newArt) == false) {
			JOptionPane.showMessageDialog(null, "The item doesn't exist!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void deleteItem() {
		String nume = "";
		Double pret = 0.0;
		try {
			nume = numeText.getText();
			pret = Double.parseDouble(pretText.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Incorrect name or price!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		ArticolMeniu art = new ArticolMeniu(nume, pret);
		if (iR.stergeArticol(art) == false) {
			JOptionPane.showMessageDialog(null, "The item doesn't exists!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionPerformedAdd(ActionEvent e) {
		this.addItem();
	}

	public void actionPerformedDelete(ActionEvent e) {
		this.deleteItem();
	}

	public void actionPerformedUpdate(ActionEvent e) {
		this.updateItem();
	}

	private static int noRows = 25;

	public void show() {
		JTable table = null;
		String[] col = { "Name", "Price" };
		String items[][] = new String[noRows][2];
		int k = 0;
		for (ArticolMeniu art : iR.getSerialization().readFile()) {
			items[k][1] = art.getPret() + "";
			items[k][0] = art.getNumeArticol();
			k++;
		}
		table = new JTable(items, col);
		table.setBounds(29, 39, 49, 69);
		JScrollPane scr = new JScrollPane(table);
		p.removeAll();
		p.add(scr);
		p.setLayout(new FlowLayout(FlowLayout.LEFT));
		principalPanel.add(p);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (btnAdd == e.getSource())
			this.addItem();
		else if (btnDelete == e.getSource())
			this.deleteItem();
		else if (btnUpdate == e.getSource())
			this.updateItem();
		else if (btnShow == e.getSource()) {
			pan.setVisible(false);
			this.show();
			mainFrame.revalidate();
			mainFrame.setSize(500, 660);
		} 
	}
}
