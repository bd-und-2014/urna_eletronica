package br.unb.bd.core;

import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.JComboBox;

import org.json.JSONArray;
import org.json.JSONObject;







import com.sun.org.apache.xml.internal.security.utils.Base64;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class CadastrarCandidato extends JFrame {

	// JComponentes
	private JPanel contentPane;
	private JTextField nomeField;
	private JTextField numeroField;
	private JComboBox partidoComboBox;
	private JComboBox estadoComboBox;
	private JComboBox cargoComboBox;
	
	private String nome;
	private int numero;
	private String partido;
	private String estado;
	private String cargo;
	
	private ArrayList<ArrayList<String>> objetosPartidos, objetosEstados, objetosCargos;
	private String partido_id, estado_id, cargo_id;
	private int cargo_qtdade_digitos;
	private String[] valores;
	// URL
	private String URL_INSERT_CANDIDATO;
	public CadastrarCandidatoListener listener;
	
	public JButton btnCadastrar;
	
	/* Foto */
	
	public File arquivoFoto;
	public JLabel lblNewLabel;

	
	public interface CadastrarCandidatoListener {
		public void didFinishedCadastrar();
	}
	
	/**
	 * Create the frame.
	 */
	public CadastrarCandidato(CadastrarCandidatoListener cadastrarCandidatoListener) {
		this.listener = cadastrarCandidatoListener;
		setTitle("Cadastrar Candidato - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 379, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 166, 46, 14);
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblNome);		

		JLabel lblNumero = new JLabel("N\u00FAmero");
		lblNumero.setBounds(10, 197, 46, 14);
		lblNumero.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblNumero);

		JLabel lblPartido = new JLabel("Partido");
		lblPartido.setBounds(10, 232, 46, 14);
		lblPartido.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblPartido);

		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(10, 261, 46, 14);
		lblEstado.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblEstado);

		JLabel lblCargo = new JLabel("Cargo");
		lblCargo.setBounds(10, 286, 46, 14);
		lblCargo.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblCargo);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 355, 30);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblCadastrarCandidato = new JLabel("Cadastrar Candidato");
		lblCadastrarCandidato.setForeground(Color.WHITE);
		lblCadastrarCandidato.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblCadastrarCandidato.setBounds(127, 0, 132, 29);
		lblCadastrarCandidato.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCadastrarCandidato);
		
		nomeField = new JTextField();
		nomeField.setBounds(66, 163, 286, 20);
		nomeField.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(nomeField);
		nomeField.setColumns(10);
		
		numeroField = new JTextField();
		numeroField.setBounds(66, 194, 71, 20);
		numeroField.setFont(new Font("Consolas", Font.PLAIN, 11));
		numeroField.setColumns(10);
		contentPane.add(numeroField);
		
		partidoComboBox = new JComboBox(new String[0]);
		partidoComboBox.setBounds(65, 228, 285, 20);
		partidoComboBox.setEnabled(false);
		contentPane.add(partidoComboBox);
		
		estadoComboBox = new JComboBox(new String[0]);
		estadoComboBox.setBounds(65, 255, 150, 20);
		estadoComboBox.setEnabled(false);
		contentPane.add(estadoComboBox);
		
		cargoComboBox = new JComboBox(new String[0]);
		cargoComboBox.setBounds(65, 282, 125, 20);
		cargoComboBox.setEnabled(false);
		contentPane.add(cargoComboBox);

		Banco.getInstance().getAllPartidos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recupera��o dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
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
				partidoComboBox.setModel(new DefaultComboBoxModel(valores));
				partidoComboBox.setEnabled(true);
			}
		});

		Banco.getInstance().getAllEstados(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recupera��o dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
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
				estadoComboBox.setModel(new DefaultComboBoxModel(valores));
				estadoComboBox.setEnabled(true);
			}
		});

		Banco.getInstance().getAllCargos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recupera��o dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
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

				valores = new String[objetosCargos.size()+1];
				valores[0] = "";
				for(int i =1; i <= objetosCargos.size(); i++){
					valores[i]= objetosCargos.get(i-1).get(1);
				}
				cargoComboBox.setModel(new DefaultComboBoxModel(valores));
				cargoComboBox.setEnabled(true);
			}
		});

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(101, 330, 89, 23);
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nomeField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo nome", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (numeroField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo n�mero", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (partidoComboBox.getSelectedItem().toString().equals("")){
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo partido", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (estadoComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo estado", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (cargoComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo cargo", "Erro", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Quantidade de d�gitos incorreta para o cargo.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					if(cargo.equals("Presidente") && !estado.equals("Todos os Estados")) {
						JOptionPane.showMessageDialog(null, "Presidente deve escolher 'Todos os estados'.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					} else if (!cargo.equals("Presidente") && estado.equals("Todos os Estados")) {
						JOptionPane.showMessageDialog(null, "'Todos os Estados' n�o � valido para o cargo escolhido.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;						
					}
					
					String encodedImageString = "";
					try {
						BufferedImage image = ImageIO.read(arquivoFoto); 
						ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
						ImageIO.write(image, "jpg", baos); 
						byte[] res = baos.toByteArray();
						
						encodedImageString = Base64.encode(res);
					} catch(Exception e) {
						
					}
					
					btnCadastrar.setEnabled(false);
					Banco.getInstance().insertCandidato(new BancoListener() {
						@Override
						public void BancoListenerDidFinish(JSONArray arrayObject) {
							btnCadastrar.setEnabled(true);
							if (arrayObject == null) { return; }
							JOptionPane.showMessageDialog(null, "Candidato cadastrado com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							listener.didFinishedCadastrar();
						}
					}, nome, encodedImageString, "" + numero, estado_id, cargo_id, partido_id);		
				}
			}
		});
		btnCadastrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(btnCadastrar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(198, 329, 89, 23);
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
		contentPane.add(btnLimpar);
		
		
		JButton btnCarregarImagem = new JButton("Carregar imagem");
		btnCarregarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser("Imagens/");
				fc.setDialogTitle("Abrir");
				fc.showOpenDialog(null);
				arquivoFoto = fc.getSelectedFile();
				if(arquivoFoto == null) {
					return;
				}
				BufferedImage myPicture;
				try {
					myPicture = ImageIO.read(arquivoFoto);
					lblNewLabel.setIcon(new ImageIcon(myPicture));
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		
		btnCarregarImagem.setBounds(211, 92, 141, 30);
		contentPane.add(btnCarregarImagem);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(92, 51, 89, 100);
		contentPane.add(lblNewLabel);

		setVisible(true);
	}
	
}
