package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;

import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControleEleitor extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private int confirmacao;
	private String [] colunas = {"CPF", "Nome", "Data de Nascimento", "Seção"};
	private ArrayList<ArrayList<String>> objetos;

	/**
	 * Create the frame.
	 */
	public ControleEleitor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 525, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Recupera Eleitores --------------------------------------------------------------------------
		Banco.getInstance().getAllEleitores(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				objetos = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < arrayObject.length(); i++) {
					ArrayList<String> item = new ArrayList<String>();
					JSONObject objAtual = arrayObject.getJSONObject(i);
					item.add("" + objAtual.getInt("eleitor_id"));
					item.add("" + objAtual.getString("eleitor_cpf"));
					item.add("" + objAtual.getString("eleitor_nome"));
					item.add("" + objAtual.getInt("eleitor_data_nascimento"));
					item.add("" + objAtual.getInt("secao_id"));
					objetos.add(item);
				}

				String[][] valores = new String[objetos.size()][4];
				for(int i =0; i < objetos.size(); i++){
					for(int j =1; j < 5; j++){
						valores[i][j-1]= objetos.get(i).get(j);
					}
				}

				table = new JTable(valores, colunas);
				table.setFont(new Font("Consolas", Font.PLAIN, 11));
				table.setBounds(180, 10, 320, 288);
				table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
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
		
		// Cadastra Eleitor -----------------------------------------------------------------------
		btnCreate = new JButton("Criar Eleitor");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastrarEleitor cadastrarEleitor = new CadastrarEleitor();
			}
		});
		btnCreate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCreate.setBounds(10, 115, 160, 23);
		contentPane.add(btnCreate);

		// Edita Eleitor ---------------------------------------------------------------------------
		btnUpdate = new JButton("Editar Eleitor");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> eleitor = new ArrayList<String>();
				for (int i = 0; i < 5; i++) {
					eleitor.add(objetos.get(table.getSelectedRow()).get(i));
				}
			EditarEleitor editarEleitor = new EditarEleitor(eleitor);
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnUpdate.setBounds(10, 149, 160, 23);
		contentPane.add(btnUpdate);

		// Remove Eleitor --------------------------------------------------------------------------
		btnDelete = new JButton("Remover Eleitor");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmacao = JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o Eleitor selecionado?", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
				if (confirmacao == JOptionPane.YES_OPTION) {
					//eleitor_id = Integer.parseInt(objetos.get(table.getSelectedRow()).get(0));
					//URL_DELETE_ELEITOR = 
					/*Banco.getInstance().deleteEleitor(new BancoListener() {
								@Override
								public void BancoListenerDidFinish(JSONArray arrayObject) {
									// TODO Auto-generated method stub	
								}
							}, URL_DELETE_ELEITOR);*/
					JOptionPane.showMessageDialog(null, "Eleitor removido com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnDelete.setBounds(10, 183, 160, 23);
		contentPane.add(btnDelete);		
		setVisible(true);
	}
}
