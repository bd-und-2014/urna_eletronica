package br.unb.bd.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;



public class EditarCandidato extends JFrame {

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
	private String URL_UPDATE_CANDIDATO;
	private String partido_id, estado_id, cargo_id ;
	private int cargo_qtdade_digitos, partidoIndex, estadoIndex, cargoIndex;
	public final EditarCandidatoListener listener;
	
	public interface EditarCandidatoListener {
		public void didFinishedEditar();
	}

	/**
	 * Create the frame.
	 */
	public EditarCandidato(final ArrayList<String> candidato, EditarCandidatoListener editarCandidatoListener) {
		this.listener = editarCandidatoListener;
		setTitle("Editar Candidato - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setBounds(10, 11, 414, 34);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblEditarCandidato = new JLabel("Editar Candidato");
		lblEditarCandidato.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblEditarCandidato.setForeground(Color.WHITE);
		lblEditarCandidato.setBounds(154, 11, 110, 14);
		panel.add(lblEditarCandidato);
		
		JLabel lblNome = new JLabel("Nome ");
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNome.setBounds(10, 59, 56, 14);
		contentPane.add(lblNome);
		
		nomeField = new JTextField();
		nomeField.setFont(new Font("Consolas", Font.PLAIN, 11));
		nomeField.setBounds(65, 56, 200, 20);
		nomeField.setText(candidato.get(2));
		nomeField.setColumns(10);
		contentPane.add(nomeField);
		
		JLabel lblNmero = new JLabel("N\u00FAmero");
		lblNmero.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNmero.setBounds(10, 90, 56, 14);
		contentPane.add(lblNmero);
		
		numeroField = new JTextField();
		numeroField.setFont(new Font("Consolas", Font.PLAIN, 11));
		numeroField.setBounds(65, 87, 86, 20);
		contentPane.add(numeroField);
		numeroField.setText(candidato.get(1));
		numeroField.setColumns(10);
		
		JLabel lblPartido = new JLabel("Partido");
		lblPartido.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblPartido.setBounds(10, 120, 46, 14);
		contentPane.add(lblPartido);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblEstado.setBounds(10, 145, 46, 14);
		contentPane.add(lblEstado);
		
		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblCargo.setBounds(10, 170, 46, 14);
		contentPane.add(lblCargo);
		
		
		
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
					if (candidato.get(3).equals(objetosPartidos.get(i-1).get(2))){
						partidoIndex = i;
					}
				}
				partidoComboBox = new JComboBox(valores);
				partidoComboBox.setBounds(65, 115, 285, 20);
				partidoComboBox.setSelectedIndex(partidoIndex);
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
					if (candidato.get(4).equals(objetosEstados.get(i-1).get(0))){
						estadoIndex = i;
					}
				}
				
				estadoComboBox = new JComboBox(valores);
				estadoComboBox.setBounds(65, 142, 150, 20);
				estadoComboBox.setSelectedIndex(estadoIndex);
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
					if (candidato.get(5).equals(valores[i])){
						cargoIndex = i;
					}
				}
				
				cargoComboBox = new JComboBox(valores);
				cargoComboBox.setBounds(65, 169, 125, 20);
				cargoComboBox.setSelectedIndex(cargoIndex);
				contentPane.add(cargoComboBox);
				contentPane.repaint();
			}
		});
		
		JButton btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nomeField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo nome", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (numeroField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo número", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (partidoComboBox.getSelectedItem().toString().equals("")){
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo partido", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (estadoComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo estado", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				} else if (cargoComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Você esqueceu de preencher o campo cargo", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
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
					if(cargo.equals("Presidente") && !estado.equals("Todos os Estados")) {
						JOptionPane.showMessageDialog(null, "Presidente deve escolher 'Todos os estados'.", "Erro", JOptionPane.INFORMATION_MESSAGE);
						return;
					} else if (!cargo.equals("Presidente") && estado.equals("Todos os Estados")) {
						JOptionPane.showMessageDialog(null, "'Todos os Estados' não é valido para o cargo escolhido.", "Erro", JOptionPane.INFORMATION_MESSAGE);
						return;						
					}
				}
				try {
					URL_UPDATE_CANDIDATO = "http://ervilhanalata.com.br/projetos/urna_eletronica/updateCandidato.asp?";
					URL_UPDATE_CANDIDATO += "candidato_nome="+URLEncoder.encode(nome, "UTF-8")+"&candidato_numero="+numero+"&estado_id="+estado_id+"&cargo_id="+ cargo_id;
					URL_UPDATE_CANDIDATO += "&partido_id="+partido_id+"&candidato_id="+candidato.get(0);

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
				Banco.getInstance().updateCandidato(new BancoListener() {
					@Override
					public void BancoListenerDidFinish(JSONArray arrayObject) {
						// TODO Auto-generated method stub	
					}
				}, URL_UPDATE_CANDIDATO);		
				
				JOptionPane.showMessageDialog(null, "Candidato editado com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
				listener.didFinishedEditar();
				dispose();
			}
		});
		btnConcluir.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnConcluir.setBounds(115, 227, 89, 23);
		contentPane.add(btnConcluir);
		
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
		btnLimpar.setBounds(214, 226, 89, 23);
		contentPane.add(btnLimpar);
		
		
		setVisible(true);
	}
}
