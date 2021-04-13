package presentationLayer;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import businessLayer.InterfaceRestaurant;
import businessLayer.Restaurant;
import dataLayer.SerializatorRestaurant;

public class View extends JFrame implements ActionListener {
	AdministratorGUI adminGui;
	WaiterGUI waiterGui;
	ChefGUI chefGUI;
	private Restaurant restaurant = new Restaurant();
	private JLabel label = new JLabel("Select what user you wanna be!");
	private JFrame jframe = new JFrame();
	private JPanel panel = new JPanel();
	private JButton btnAdmin = new JButton("Aministrator");
	private JButton btnWaiter = new JButton("Waiter");
	private JButton btnChef = new JButton("Chef");
	
	
	public View(String ser) {
		SerializatorRestaurant.setSer(ser);
		btnAdmin.addActionListener(this);
		btnWaiter.addActionListener(this);
		btnChef.addActionListener(this);
		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER));
		p1.add(label);
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		p2.add(btnAdmin);
		p2.add(btnWaiter);
		p2.add(btnChef);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(p1);
		panel.add(p2);
		jframe.add(panel);
		jframe.setSize(400, 200);
		jframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jframe.setVisible(true);
		this.setResizable(false);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		jframe.setLocation(size.width/2-jframe.getSize().width/2, size.height/2-jframe.getSize().height/2);
		jframe.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAdmin) {
			if(adminGui == null)
			adminGui = new AdministratorGUI(restaurant);
			jframe.setVisible(true);
			this.setVisible(false);
		} else if(e.getSource() == btnWaiter) {
			if(waiterGui == null)
			waiterGui = new WaiterGUI(restaurant);
			jframe.setVisible(true);
			this.setVisible(false);
		} else if(e.getSource() == btnChef) {
			if(chefGUI == null) {
				chefGUI = new ChefGUI(restaurant);
				waiterGui.registerObserver(chefGUI);
			}
			jframe.setVisible(true);
			this.setVisible(false);
		}
		
	}
}
