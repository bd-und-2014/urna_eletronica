package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class CadastrarEleitor extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private String[] valores;

	/**
	 * Create the frame.
	 */
	public CadastrarEleitor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 11, 364, 34);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCadastrarEleitor = new JLabel("Cadastrar Eleitor");
		lblCadastrarEleitor.setBounds(131, 11, 102, 14);
		lblCadastrarEleitor.setForeground(Color.WHITE);
		lblCadastrarEleitor.setFont(new Font("Consolas", Font.PLAIN, 11));
		panel.add(lblCadastrarEleitor);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNome.setBounds(10, 56, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblCpf.setBounds(10, 81, 46, 14);
		contentPane.add(lblCpf);
		
		textField = new JTextField();
		textField.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField.setBounds(55, 53, 126, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Consolas", Font.PLAIN, 11));
		textField_1.setBounds(55, 81, 126, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblDataDeNascimento.setBounds(10, 115, 109, 14);
		contentPane.add(lblDataDeNascimento);
		
		valores = new String[1998-1900];
		valores[0] = "";
		for (int i = 1996 ; i >= 1900 ; i--) {
			valores[1996 - i+1] = ""+ i;
		}
		
		JComboBox dataNascimentoComboBox = new JComboBox(valores);
		dataNascimentoComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		dataNascimentoComboBox.setBounds(129, 112, 70, 20);
		contentPane.add(dataNascimentoComboBox);
		
		JLabel lblZonaEleitoral = new JLabel("Zona Eleitoral");
		lblZonaEleitoral.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblZonaEleitoral.setBounds(10, 147, 91, 14);
		contentPane.add(lblZonaEleitoral);
		
		JComboBox zonaEleitoralComboBox = new JComboBox();
		zonaEleitoralComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		zonaEleitoralComboBox.setBounds(129, 143, 114, 20);
		contentPane.add(zonaEleitoralComboBox);
		
		JLabel lblSeoEleitoral = new JLabel("Se\u00E7\u00E3o Eleitoral");
		lblSeoEleitoral.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblSeoEleitoral.setBounds(10, 178, 91, 14);
		contentPane.add(lblSeoEleitoral);
		
		JComboBox secaoEleitoralComboBox = new JComboBox();
		secaoEleitoralComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		secaoEleitoralComboBox.setBounds(129, 174, 114, 20);
		contentPane.add(secaoEleitoralComboBox);
		secaoEleitoralComboBox.setEnabled(false);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCadastrar.setBounds(92, 227, 89, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnLimpar.setBounds(191, 227, 89, 23);
		contentPane.add(btnLimpar);
		
		setVisible(true);
	}
}
