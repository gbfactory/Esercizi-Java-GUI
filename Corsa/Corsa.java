package net.gbfactory.corsa;

import com.sun.org.apache.xpath.internal.functions.FuncFalse;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Gioco corsa
 * @author gbfactory
 * @since 04/06/2020
 */

public class Corsa extends JFrame{

	private JButton via;

	private Grafica corsa;

	public Corsa() {

		super ("Corsa V2");

		setLayout(new BorderLayout());

		corsa = new Grafica();

		via = new JButton("Via");
		via.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				via.setEnabled(false);
				corsa.startRace();
			}
		});
		add(via, BorderLayout.PAGE_START);

		add(corsa, BorderLayout.CENTER);

		setResizable(false);
		setSize(800,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);


	}

}


