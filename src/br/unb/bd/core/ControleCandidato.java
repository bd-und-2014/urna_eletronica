package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControleCandidato extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollBar;
	String [] colunas = {"Número", "Nome", "Partido", "Cargo"};
	Object [][] dados = {
			{"45", "Aécio Neves", "PSDB", "Presidente"},
			{"13", "Dilma Rousseff", "PT", "Presidente"},
		};

	/**
	 * Create the frame.
	 */
	public ControleCandidato() {
		setTitle("Controle de Candidatos - Urna Eletronica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 526, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCreate = new JButton("Criar Candidato");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastrarCandidato cadastrarCandidato = new CadastrarCandidato();
			}
		});
		btnCreate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCreate.setBounds(10, 115, 160, 23);
		contentPane.add(btnCreate);
		
		JButton btnUpdate = new JButton("Editar Candidato");
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnUpdate.setBounds(10, 149, 160, 23);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Remover Candidato");
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnDelete.setBounds(10, 183, 160, 23);
		contentPane.add(btnDelete);
		
		table = new JTable(dados, colunas);
		//scrollBar = new JScrollPane(table);
		table.setFont(new Font("Consolas", Font.PLAIN, 11));
		table.setBounds(180, 11, 320, 288);
		contentPane.add(table);
		//contentPane.add(scrollBar);
		
		
		
		
		setVisible(true);
	}
}
