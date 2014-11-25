package br.unb.bd.core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

import org.json.*;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;
import br.unb.bd.core.EditarCandidato.EditarCandidatoListener;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class ControleCandidato extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton btnUpdate;
	private JButton btnDelete;
	private String [] colunas = {"Numero", "Nome", "Partido", "Estado", "Cargo"};
	private String[][] valores;
	private int confirmacao;
	private int candidato_id;
	private String URL_DELETE_CANDIDATO;
	private ArrayList<ArrayList<String>> objetos;
	
	
	public void refreshTable() {
		
		Banco.getInstance().getAllCandidatos(new BancoListener() {
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
					item.add("" + objAtual.getInt("candidato_id"));
					item.add("" + objAtual.getInt("candidato_numero"));
					item.add("" + objAtual.getString("candidato_nome"));
					item.add("" + objAtual.getString("partido_sigla"));
					item.add("" + objAtual.getString("estado_id"));
					item.add("" + objAtual.getString("cargo_nome"));
					objetos.add(item);
				}
				
				valores = new String[objetos.size()][5];
				for(int i =0; i < objetos.size(); i++){
					for(int j =1; j < 6; j++){
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
	public ControleCandidato() {
		setTitle("Controle de Candidatos - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 620, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		// Recupera Candidatos --------------------------------------------------------------------------
		refreshTable();
		// Cadastra Candidato -----------------------------------------------------------------------
		JButton btnCreate = new JButton("Cadastrar Candidato");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				CadastrarCandidato cadastrarCandidato = new CadastrarCandidato();
			}
			
		});
		btnCreate.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCreate.setBounds(10, 115, 160, 23);
		contentPane.add(btnCreate);
		
		// Edita Candidato ---------------------------------------------------------------------------
		btnUpdate = new JButton("Editar Candidato");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> candidato = new ArrayList<String>();
				for (int i = 0; i < 6; i++) {
					candidato.add(objetos.get(table.getSelectedRow()).get(i));
				}
				EditarCandidato editarCandidato = new EditarCandidato(candidato, new EditarCandidatoListener() {
					@Override
					public void didFinishedEditar() {
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
		
		// Remove Candidato --------------------------------------------------------------------------
		btnDelete = new JButton("Remover Candidato");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmacao = JOptionPane.showConfirmDialog(null, "Deseja mesmo remover o candidato selecionado?", "Confirma��o", JOptionPane.INFORMATION_MESSAGE);
				if (confirmacao == JOptionPane.YES_OPTION) {
					//dispose();
					candidato_id = Integer.parseInt(objetos.get(table.getSelectedRow()).get(0));
					URL_DELETE_CANDIDATO = "http://ervilhanalata.com.br/projetos/urna_eletronica/deleteCandidato.asp?candidato_id="+candidato_id;
					Banco.getInstance().deleteCandidato(new BancoListener() {
						@Override
						public void BancoListenerDidFinish(JSONArray arrayObject) {
							// TODO Auto-generated method stub	
						}
					}, URL_DELETE_CANDIDATO);
					JOptionPane.showMessageDialog(null, "Candidato removido com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
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
