package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.json.JSONArray;
import org.json.JSONObject;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

public class CadastrarCandidato extends JFrame {

	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField numeroField;
	private String nome;
	private int numero;
	private String partido;
	private String estado;
	private String cargo;
	private JComboBox partidoComboBox;
	private JComboBox estadoComboBox;
	private JComboBox cargoComboBox;
	private ArrayList<ArrayList<String>> objetosPartidos, objetosEstados, objetosCargos;
	private String partido_id, estado_id, cargo_id;
	private String URL_INSERT_CANDIDATO;
	private String result;
	private int cargo_qtdade_digitos;

	/**
	 * Create the frame.
	 */
	public CadastrarCandidato() {
		setTitle("Cadastrar Candidato - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		nomeField = new JTextField();
		nomeField.setFont(new Font("Consolas", Font.PLAIN, 11));
		nomeField.setBounds(66, 60, 286, 20);
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNome.setBounds(10, 63, 46, 14);
		contentPane.add(lblNome);
		
		numeroField = new JTextField();
		numeroField.setFont(new Font("Consolas", Font.PLAIN, 11));
		numeroField.setColumns(10);
		numeroField.setBounds(66, 91, 71, 20);
		contentPane.add(numeroField);
		
		JLabel lblNumero = new JLabel("N\u00FAmero");
		lblNumero.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNumero.setBounds(10, 94, 46, 14);
		contentPane.add(lblNumero);
		
		JLabel lblPartido = new JLabel("Partido");
		lblPartido.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblPartido.setBounds(10, 129, 46, 14);
		contentPane.add(lblPartido);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblEstado.setBounds(10, 158, 46, 14);
		contentPane.add(lblEstado);
		
		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblCargo.setBounds(10, 183, 46, 14);
		contentPane.add(lblCargo);
			
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 10, 355, 30);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCadastrarCandidato = new JLabel("Cadastrar Candidato");
		lblCadastrarCandidato.setForeground(Color.WHITE);
		lblCadastrarCandidato.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblCadastrarCandidato.setBounds(127, 0, 132, 29);
		lblCadastrarCandidato.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCadastrarCandidato);
		
		Banco.getInstance().getAllPartidos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				objetosPartidos = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < arrayObject.length(); i++) {
					ArrayList<String> item = new ArrayList<String>();
					JSONObject objAtual = arrayObject.getJSONObject(i);
					item.add("" + objAtual.getInt("partido_id"));
					item.add("" + objAtual.getString("partido_nome"));
					item.add("" + objAtual.getString("partido_sigla"));
					
					objetosPartidos.add(item);
				}
				
				String[] valores = new String[objetosPartidos.size()+1];
				valores[0] = "";
				for(int i =1; i <= objetosPartidos.size(); i++){
					valores[i]= objetosPartidos.get(i-1).get(1);
				}
				partidoComboBox = new JComboBox(valores);
				partidoComboBox.setBounds(65, 125, 250, 20);
				contentPane.add(partidoComboBox);
				contentPane.repaint();
			}
		});
		
		Banco.getInstance().getAllEstados(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				objetosEstados = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < arrayObject.length(); i++) {
					ArrayList<String> item = new ArrayList<String>();
					JSONObject objAtual = arrayObject.getJSONObject(i);
					item.add("" + objAtual.getString("estado_id"));
					item.add("" + objAtual.getString("estado_nome"));
					item.add("" + objAtual.getInt("pais_id"));
					
					objetosEstados.add(item);
				}
				
				String[] valores = new String[objetosEstados.size() + 1];
				valores[0] = "";
				for(int i = 1; i <= objetosEstados.size(); i++){
					valores[i]= objetosEstados.get(i-1).get(1);
				}
				
				estadoComboBox = new JComboBox(valores);
				estadoComboBox.setBounds(65, 152, 150, 20);
				contentPane.add(estadoComboBox);
				contentPane.repaint();
			}
		});
		
		Banco.getInstance().getAllCargos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				objetosCargos = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < arrayObject.length(); i++) {
					ArrayList<String> item = new ArrayList<String>();
					JSONObject objAtual = arrayObject.getJSONObject(i);
					item.add("" + objAtual.getInt("cargo_id"));
					item.add("" + objAtual.getString("cargo_nome"));
					item.add("" + objAtual.getInt("cargo_qtdade_digitos"));
					
					objetosCargos.add(item);
				}
				
				String[] valores = new String[objetosCargos.size()+1];
				valores[0] = "";
				for(int i =1; i <= objetosCargos.size(); i++){
					valores[i]= objetosCargos.get(i-1).get(1);
				}
				
				cargoComboBox = new JComboBox(valores);
				cargoComboBox.setBounds(65, 179, 125, 20);
				contentPane.add(cargoComboBox);
				
				contentPane.repaint();
			}
		});
		
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nomeField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo nome", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else if (numeroField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo número", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else if (partidoComboBox.getSelectedItem().toString().equals("")){
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo partido", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else if (estadoComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo estado", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else if (cargoComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo cargo", "Erro", JOptionPane.INFORMATION_MESSAGE);
				} else {
					nome = nomeField.getText();
					numero = Integer.parseInt(numeroField.getText());
					partido = partidoComboBox.getSelectedItem().toString();
					estado = estadoComboBox.getSelectedItem().toString();
					cargo = cargoComboBox.getSelectedItem().toString();
					
					for(int i =0; i < objetosPartidos.size(); i++){
						if(objetosPartidos.get(i).get(1).equals(partido)) {
							partido_id = objetosPartidos.get(i).get(0);
						}
					}
					
					for(int i =0; i < objetosEstados.size(); i++){
						if(objetosEstados.get(i).get(1).equals(estado)) {
							estado_id = objetosEstados.get(i).get(0);
						}
					}
					
					for(int i =0; i < objetosCargos.size(); i++){
						if(objetosCargos.get(i).get(1).equals(cargo)) {
							cargo_id = objetosCargos.get(i).get(0);
							cargo_qtdade_digitos = Integer.parseInt(objetosCargos.get(i).get(2));
						}
					}
					
					String tmp = "" + numero;
					
					if(cargo_qtdade_digitos != tmp.length()) {
						JOptionPane.showMessageDialog(null, "Quantidade de dígitos incorreta para o cargo.", "Erro", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					// TODO cadastrar no banco de dados
					
					URL_INSERT_CANDIDATO = "http://ervilhanalata.com.br/projetos/urna_eletronica/insertCandidato.asp?";
					try {
						URL_INSERT_CANDIDATO += "candidato_nome="+ URLEncoder.encode(nome, "UTF-8");
						URL_INSERT_CANDIDATO += "&candidato_foto=foto&candidato_numero=";
						URL_INSERT_CANDIDATO += numero+"&estado_id="+estado_id+"&cargo_id="+cargo_id+"&partido_id="+partido_id;

					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					
					Banco.getInstance().insertCandidato(new BancoListener() {

						@Override
						public void BancoListenerDidFinish(JSONArray arrayObject) {
							// TODO Auto-generated method stub	
						}
					}, URL_INSERT_CANDIDATO);		
					
					JOptionPane.showMessageDialog(null, "Candidato cadastrado com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
				}
			}
		});
		btnCadastrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnCadastrar.setBounds(101, 227, 89, 23);
		contentPane.add(btnCadastrar);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nomeField.setText("");
				numeroField.setText("");
				partidoComboBox.setSelectedIndex(0);
				estadoComboBox.setSelectedIndex(0);
				cargoComboBox.setSelectedIndex(0);
			}
		});
		
		btnLimpar.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnLimpar.setBounds(198, 226, 89, 23);
		contentPane.add(btnLimpar);
		
		setVisible(true);
	}
}
