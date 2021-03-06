package br.unb.bd.core;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;
import br.unb.bd.core.CadastrarEleitor.CadastrarEleitorListener;
import br.unb.bd.core.EditarEleitor.EditarEleitorListener;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControleEleitor extends JFrame {

	// Painel
	private JPanel contentPane;
	// Componentes da tabela
	private JTable table;
	private JScrollPane scrollPane;
	private String [] colunas = {"CPF", "Nome", "Data de Nascimento", "Se��o"};
	// Bot�es
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	
	private int confirmacao;
	private ArrayList<ArrayList<String>> objetos;
	
	public void refreshTable() {
		Banco.getInstance().getAllEleitores(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recupera��o dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
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
					item.add("" + objAtual.getString("secao_endereco"));
					item.add("" + objAtual.getString("eleitor_foto"));
					item.add("" + objAtual.getString("zona_descricao"));
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
				scrollPane = new JScrollPane(table);
				scrollPane.setBounds(180, 10, 400, 288);
				contentPane.add(scrollPane);
				contentPane.repaint();

				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
					}
				});
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ControleEleitor() {
		setTitle("Controle Eleitor - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 620, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Recupera Eleitores --------------------------------------------------------------------------
		refreshTable();
		
		// Cadastra Eleitor -----------------------------------------------------------------------
		btnCreate = new JButton("Cadastrar Eleitor");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CadastrarEleitor cadastrarEleitor = new CadastrarEleitor(new CadastrarEleitorListener() {
					
					@Override
					public void didFinishedCadastrar() {
						// TODO Auto-generated method stub
						contentPane.remove(scrollPane);
						refreshTable();
						
					}
				});
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
				for (int i = 0; i < 7; i++) {
					eleitor.add(objetos.get(table.getSelectedRow()).get(i));
				}
			EditarEleitor editarEleitor = new EditarEleitor(eleitor, new EditarEleitorListener() {				
				@Override
				public void didFinishedEditar() {
					// TODO Auto-generated method stub
					contentPane.remove(scrollPane);
					refreshTable();
				}
			});
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
				
				confirmacao = JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o Eleitor selecionado?", "Confirma��o", JOptionPane.INFORMATION_MESSAGE);
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
					contentPane.remove(scrollPane);
					refreshTable();
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
