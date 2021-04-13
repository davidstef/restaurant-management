package presentationLayer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import businessLayer.ArticolMeniu;
import businessLayer.Comanda;
import businessLayer.InterfaceRestaurant;
import businessLayer.Restaurant;

public class ChefGUI extends JFrame implements Observer {

	private InterfaceRestaurant iR;
	private JPanel principalPanel = new JPanel();
	private JFrame mainFrame = new JFrame("~Chef~");
	private JTable jtable = new JTable();
	private JTextArea textArea = new JTextArea(15, 40);
	private WaiterGUI waiter = null;

	public ChefGUI(Restaurant restaurant) {
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		textArea.setEditable(false);
		JScrollPane scr = new JScrollPane(textArea);
		scr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		principalPanel.setLayout(new BoxLayout(principalPanel, BoxLayout.Y_AXIS));
		p2.setLayout(new FlowLayout(FlowLayout.LEFT));
		p1.add(scr);
		principalPanel.add(p1);
		principalPanel.add(p2);
		mainFrame.add(principalPanel);
		mainFrame.setSize(520, 350);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		iR = restaurant;
		viewOrders();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		mainFrame.setLocation(size.width / 2 - mainFrame.getSize().width / 2,
				size.height / 2 - mainFrame.getSize().height / 2);
		mainFrame.setResizable(false);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		viewOrders();
	}

	public void viewOrderObservable() {
		for (Comanda c : iR.getSerialization().readFileO().keySet()) {
			waiter.getWaiterGUI().showOrder(c);
		}
		waiter.getMainFrame().setVisible(false);
	}

	public ChefGUI getChefGUI() {
		return this;
	}

	public void viewOrders() {
		int i = 0;
		textArea.setText("");
		for (Map.Entry<Comanda, ArrayList<ArticolMeniu>> entry : iR.getSerialization().readFileO().entrySet()) {
			String min = entry.getKey().getData().getMinutes() + "";
			if (entry.getKey().getData().getMinutes() < 10) {
				min = "0" + entry.getKey().getData().getMinutes();
			}
			textArea.setText(textArea.getText() + "*Order No." + entry.getKey().getIdComanda() + ", Time: "
					+ entry.getKey().getData().getHours() + ":" + min + "\n");
			textArea.setText(textArea.getText() + " Items: ");
			for (ArticolMeniu art : entry.getValue()) {
				textArea.setText(textArea.getText() + " " + art.getNumeArticol() + ", ");
			}
			textArea.setText(textArea.getText() + "\n");
		}

	}

}
