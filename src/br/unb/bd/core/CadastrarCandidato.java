package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class CadastrarCandidato extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the frame.
	 */
	public CadastrarCandidato() {
		setTitle("Cadastrar Candidato - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 398, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField.setBounds(66, 46, 286, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNome.setBounds(10, 49, 46, 14);
		contentPane.add(lblNome);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField_1.setColumns(10);
		textField_1.setBounds(66, 71, 71, 20);
		contentPane.add(textField_1);
		
		JLabel lblNumero = new JLabel("N\u00FAmero");
		lblNumero.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNumero.setBounds(10, 74, 46, 14);
		contentPane.add(lblNumero);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField_2.setColumns(10);
		textField_2.setBounds(66, 97, 71, 20);
		contentPane.add(textField_2);
		
		JLabel lblPartido = new JLabel("Partido");
		lblPartido.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblPartido.setBounds(10, 99, 46, 14);
		contentPane.add(lblPartido);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField_3.setColumns(10);
		textField_3.setBounds(66, 121, 124, 20);
		contentPane.add(textField_3);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblEstado.setBounds(10, 124, 46, 14);
		contentPane.add(lblEstado);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCadastrar.setBounds(101, 227, 89, 23);
		contentPane.add(btnCadastrar);
		
		JLabel label = new JLabel("*");
		label.setFont(new Font("Consolas", Font.PLAIN, 11));
		label.setBounds(355, 48, 11, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setFont(new Font("Consolas", Font.PLAIN, 11));
		label_1.setBounds(139, 74, 11, 14);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("*");
		label_2.setFont(new Font("Consolas", Font.PLAIN, 11));
		label_2.setBounds(139, 99, 11, 14);
		contentPane.add(label_2);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 11, 356, 29);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCadastrarCandidato = new JLabel("Cadastrar Candidato");
		lblCadastrarCandidato.setForeground(Color.WHITE);
		lblCadastrarCandidato.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblCadastrarCandidato.setBounds(127, 0, 132, 29);
		lblCadastrarCandidato.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCadastrarCandidato);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnLimpar.setBounds(198, 226, 89, 23);
		contentPane.add(btnLimpar);
		setVisible(true);
	}
}
