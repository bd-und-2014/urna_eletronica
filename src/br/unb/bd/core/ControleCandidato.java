package br.unb.bd.core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import org.json.*;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ControleCandidato extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollBar;
	private JButton btnUpdate;
	private JButton btnDelete;
	private String [] colunas = {"Nï¿½mero", "Nome", "Partido", "Cargo"};
	private Object [][] dados;
	int confirmacao;
	
	/**
	 * Create the frame.
	 */
	public ControleCandidato() {
		setTitle("Controle de Candidatos - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 526, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCreate = new JButton("Criar Candidato");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastrarCandidato cadastrarCandidato = new CadastrarCandidato();
				contentPane.repaint();
			}
		});
		
		btnCreate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCreate.setBounds(10, 115, 160, 23);
		contentPane.add(btnCreate);
		
		btnUpdate = new JButton("Editar Candidato");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnUpdate.setBounds(10, 149, 160, 23);
		contentPane.add(btnUpdate);
				
		
		Banco.getInstance().getAllCandidatos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				ArrayList<ArrayList<String>> objetos = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < arrayObject.length(); i++) {
					ArrayList<String> item = new ArrayList<String>();
					JSONObject objAtual = arrayObject.getJSONObject(i);
					//item.add("" + objAtual.getInt("candidato_id"));
					item.add("" + objAtual.getInt("candidato_numero"));
					item.add("" + objAtual.getString("candidato_nome"));
					item.add("" + objAtual.getString("partido_sigla"));
					item.add("" + objAtual.getString("estado_id"));
					objetos.add(item);
				}
				
				String[][] valores = new String[objetos.size()][4];
				for(int i =0; i < objetos.size(); i++){
					for(int j =0; j < 4; j++){
						valores[i][j]= objetos.get(i).get(j);
					}
				}
				
				table = new JTable(valores, colunas);
				table.setFont(new Font("Consolas", Font.PLAIN, 11));
				table.setBounds(180, 10, 320, 288);
				contentPane.add(table);
				contentPane.repaint();
				
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
					}
				});
				
			}
		});	
		
		btnDelete = new JButton("Remover Candidato");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmacao = JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o candidato selecionado?", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
				if (confirmacao == JOptionPane.YES_OPTION) {
					
				}
				contentPane.repaint();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnDelete.setBounds(10, 183, 160, 23);
		contentPane.add(btnDelete);
		
		setVisible(true);
	}
}
