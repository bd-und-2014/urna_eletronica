package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class EditarEleitor extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public EditarEleitor(final ArrayList<String> eleitor) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 11, 414, 34);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblEditarEleitor = new JLabel("Editar Eleitor");
		lblEditarEleitor.setBounds(165, 11, 84, 14);
		lblEditarEleitor.setForeground(Color.WHITE);
		lblEditarEleitor.setFont(new Font("Consolas", Font.PLAIN, 11));
		panel.add(lblEditarEleitor);
		
		setVisible(true);
	}
}
