package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CadastrarCandidato extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField numeroField;
	private JTextField partidoField;
	private JTextField estadoField;
	private String nome;
	private int numero;
	private String partido;
	private String estado;

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
		
		nomeField = new JTextField();
		nomeField.setFont(new Font("Consolas", Font.PLAIN, 11));
		nomeField.setBounds(66, 46, 286, 20);
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNome.setBounds(10, 49, 46, 14);
		contentPane.add(lblNome);
		
		numeroField = new JTextField();
		numeroField.setFont(new Font("Consolas", Font.PLAIN, 11));
		numeroField.setColumns(10);
		numeroField.setBounds(66, 71, 71, 20);
		contentPane.add(numeroField);
		
		JLabel lblNumero = new JLabel("N\u00FAmero");
		lblNumero.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNumero.setBounds(10, 74, 46, 14);
		contentPane.add(lblNumero);
		
		partidoField = new JTextField();
		partidoField.setFont(new Font("Consolas", Font.PLAIN, 11));
		partidoField.setColumns(10);
		partidoField.setBounds(66, 97, 71, 20);
		contentPane.add(partidoField);
		
		JLabel lblPartido = new JLabel("Partido");
		lblPartido.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblPartido.setBounds(10, 99, 46, 14);
		contentPane.add(lblPartido);
		
		estadoField = new JTextField();
		estadoField.setFont(new Font("Consolas", Font.PLAIN, 11));
		estadoField.setColumns(10);
		estadoField.setBounds(66, 121, 124, 20);
		contentPane.add(estadoField);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblEstado.setBounds(10, 124, 46, 14);
		contentPane.add(lblEstado);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nomeField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo nome", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else if (numeroField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo número", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else if(partidoField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo partido", "Erro", JOptionPane.INFORMATION_MESSAGE);
				}  else {
					nome = nomeField.getText();
					numero = Integer.parseInt(numeroField.getText());
					partido = partidoField.getText();
					if (estadoField.getText().equals("")) {
						estado = "NULL";
					} else {
						estado = estadoField.getText();
					}
					// TODO cadastrar no banco de dados
					JOptionPane.showMessageDialog(null, "Candidato cadastrado com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		});
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
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nomeField.setText("");
				numeroField.setText("");
				partidoField.setText("");
				estadoField.setText("");
			}
		});
		btnLimpar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnLimpar.setBounds(198, 226, 89, 23);
		contentPane.add(btnLimpar);
		setVisible(true);
	}
}
