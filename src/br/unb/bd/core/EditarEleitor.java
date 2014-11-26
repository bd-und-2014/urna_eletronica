package br.unb.bd.core;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;

import br.unb.bd.core.EditarCandidato.EditarCandidatoListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditarEleitor extends JFrame {

	private JPanel contentPane;
	private JTextField nomeTextField;
	public final EditarEleitorListener listener;
	
	public interface EditarEleitorListener {
		public void didFinishedEditar();
	}

	/**
	 * Create the frame.
	 */
	public EditarEleitor(final ArrayList<String> eleitor, EditarEleitorListener editarEleitorListener) {
		this.listener = editarEleitorListener;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 253);
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
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 56, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblSeo = new JLabel("Se\u00E7\u00E3o");
		lblSeo.setBounds(10, 92, 46, 14);
		contentPane.add(lblSeo);
		
		JLabel lblFoto = new JLabel("Foto");
		lblFoto.setBounds(10, 126, 46, 14);
		contentPane.add(lblFoto);
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.setBounds(124, 181, 89, 23);
		contentPane.add(btnConcluir);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nomeTextField.setText("");
			}
		});
		btnLimpar.setBounds(223, 181, 89, 23);
		contentPane.add(btnLimpar);
		
		nomeTextField = new JTextField();
		nomeTextField.setBounds(55, 56, 223, 20);
		contentPane.add(nomeTextField);
		nomeTextField.setText(eleitor.get(2));
		nomeTextField.setColumns(10);
		
		setVisible(true);
	}
}
