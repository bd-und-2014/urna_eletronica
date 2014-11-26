package br.unb.bd.core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CadastrarEleitor extends JFrame {

	// JComponentes
	private JPanel contentPane;
	private JTextField nomeTextField;
	private JTextField CPFTextField;
	private JComboBox dataNascimentoComboBox;
	private JComboBox zonaEleitoralComboBox;
	private JComboBox secaoEleitoralComboBox;
	// Botões
	private JButton btnCadastrar;
	private JButton btnLimpar;
	private CadastrarEleitorListener listener;
	private String nome, CPF, dataNascimento, zona, secao;
	private String zona_id, secao_id;
	
	private ArrayList<ArrayList<String>> objetosZonas, objetosSecoes;
	
	public File arquivoFoto;
	public JLabel lblNewLabel;
	
	public interface CadastrarEleitorListener {
		public void didFinishedCadastrar();
	}
	
	/**
	 * Create the frame.
	 */
	public CadastrarEleitor(CadastrarEleitorListener cadastrarEleitorListener) {
		this.listener = cadastrarEleitorListener;
		setTitle("Cadastrar Eleitor - Urna Eletronica");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 423);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 364, 34);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblCadastrarEleitor = new JLabel("Cadastrar Eleitor");
		lblCadastrarEleitor.setBounds(131, 11, 102, 14);
		lblCadastrarEleitor.setForeground(Color.WHITE);
		lblCadastrarEleitor.setFont(new Font("Consolas", Font.PLAIN, 11));
		panel.add(lblCadastrarEleitor);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 183, 46, 14);
		lblNome.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblNome);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(10, 208, 46, 14);
		lblCpf.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblCpf);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setBounds(10, 242, 109, 14);
		lblDataDeNascimento.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblDataDeNascimento);
		
		JLabel lblZonaEleitoral = new JLabel("Zona Eleitoral");
		lblZonaEleitoral.setBounds(10, 274, 91, 14);
		lblZonaEleitoral.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblZonaEleitoral);
		
		JLabel lblSeoEleitoral = new JLabel("Se\u00E7\u00E3o Eleitoral");
		lblSeoEleitoral.setBounds(10, 305, 91, 14);
		lblSeoEleitoral.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(lblSeoEleitoral);
		
		nomeTextField = new JTextField();
		nomeTextField.setBounds(55, 180, 126, 20);
		nomeTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		CPFTextField = new JTextField();
		CPFTextField.setBounds(55, 208, 126, 20);
		CPFTextField.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(CPFTextField);
		CPFTextField.setColumns(10);
		
		String[] valores = new String[1996-1900+2];
		valores[0] = "";
		for (int i = 1996 ; i >= 1900 ; i--) {
			valores[1996 - i+1] = ""+ i;
		}
		
		dataNascimentoComboBox = new JComboBox(valores);
		dataNascimentoComboBox.setBounds(129, 239, 70, 20);
		dataNascimentoComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(dataNascimentoComboBox);
		
		zonaEleitoralComboBox = new JComboBox(new String[0]);
		zonaEleitoralComboBox.setBounds(129, 270, 151, 20);
		zonaEleitoralComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		zonaEleitoralComboBox.setEnabled(false);
		contentPane.add(zonaEleitoralComboBox);
		
		secaoEleitoralComboBox = new JComboBox(new String[0]);
		secaoEleitoralComboBox.setBounds(129, 301, 151, 20);
		secaoEleitoralComboBox.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(secaoEleitoralComboBox);
		secaoEleitoralComboBox.setEnabled(false);
		
		Banco.getInstance().getAllZonas(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperaï¿½ï¿½o dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				objetosZonas = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < arrayObject.length(); i++) {
					ArrayList<String> item = new ArrayList<String>();
					JSONObject objAtual = arrayObject.getJSONObject(i);
					item.add("" + objAtual.getInt("zona_id"));
					item.add("" + objAtual.getString("zona_descricao"));
					item.add("" + objAtual.getString("estado_id"));

					objetosZonas.add(item);
				}

				String[] valores = new String[objetosZonas.size()+1];
				valores[0] = "";
				for(int i =1; i <= objetosZonas.size(); i++){
					valores[i]= objetosZonas.get(i-1).get(1);
				}
				zonaEleitoralComboBox.setModel(new DefaultComboBoxModel(valores));
				zonaEleitoralComboBox.setEnabled(true);				
			}
		});
		
		zonaEleitoralComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				for(int i =0; i < objetosZonas.size(); i++){
					if(objetosZonas.get(i).get(1).equals(zonaEleitoralComboBox.getSelectedItem().toString())) {
						zona_id = objetosZonas.get(i).get(0);
						System.out.println(zona_id);
					}
				}
				Banco.getInstance().getAllSecoes(new BancoListener() {
					@Override
					public void BancoListenerDidFinish(JSONArray arrayObject) {
						if (arrayObject == null) {
							JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperaï¿½ï¿½o dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
							return;
						}
						objetosSecoes = new ArrayList<ArrayList<String>>();
						for (int i = 0; i < arrayObject.length(); i++) {
							ArrayList<String> item = new ArrayList<String>();
							JSONObject objAtual = arrayObject.getJSONObject(i);
							item.add("" + objAtual.getInt("secao_id"));
							item.add("" + objAtual.getString("secao_endereco"));
							item.add("" + objAtual.getInt("zona_id"));

							objetosSecoes.add(item);
						}

						String[] valores = new String[objetosSecoes.size()+1];
						valores[0] = "";
						for(int i =1; i <= objetosSecoes.size(); i++){
							valores[i]= objetosSecoes.get(i-1).get(1);
						}
						secaoEleitoralComboBox.setModel(new DefaultComboBoxModel(valores));
						secaoEleitoralComboBox.setEnabled(true);						
					}
				}, zona_id);
			}
		});
		
		
		
		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nomeTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vocï¿½ esqueceu de preencher o campo nome", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (CPFTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vocï¿½ esqueceu de preencher o campo CPF", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (dataNascimentoComboBox.getSelectedItem().toString().equals("")) {
						JOptionPane.showMessageDialog(null, "Vocï¿½ esqueceu de preencher o campo data de nascimento", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
				} else if (zonaEleitoralComboBox.getSelectedItem().toString().equals("")){
					JOptionPane.showMessageDialog(null, "Vocï¿½ esqueceu de preencher o campo zona eleitoral", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (secaoEleitoralComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Vocï¿½ esqueceu de preencher o secao eleitoral", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					nome = nomeTextField.getText();
					CPF = CPFTextField.getText();
					dataNascimento = dataNascimentoComboBox.getSelectedItem().toString();
					zona = zonaEleitoralComboBox.getSelectedItem().toString();
					secao = secaoEleitoralComboBox.getSelectedItem().toString();


					for(int i =0; i < objetosSecoes.size(); i++){
						if(objetosSecoes.get(i).get(1).equals(secao)) {
							secao_id = objetosSecoes.get(i).get(0);
						}
					}

					if(CPF.length() != 11) {
						JOptionPane.showMessageDialog(null, "Quantidade de dï¿½gitos incorreta para CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					String encodedImageString = "";
					try {
						BufferedImage image = ImageIO.read(arquivoFoto); 
						ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
						ImageIO.write(image, "jpg", baos); 
						byte[] res = baos.toByteArray();
						
						encodedImageString = Base64.encode(res);
					} catch(Exception e1) {
						
					}
					
					btnCadastrar.setEnabled(false);
					Banco.getInstance().insertEleitor(new BancoListener() {
						@Override
						public void BancoListenerDidFinish(JSONArray arrayObject) {
							btnCadastrar.setEnabled(true);
							if (arrayObject == null) { return; }
							JOptionPane.showMessageDialog(null, "Eleitor cadastrado com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							listener.didFinishedCadastrar();
						}
					}, CPF, nome, encodedImageString, dataNascimento, secao_id);		
				}
				
			}
		});
		btnCadastrar.setBounds(92, 354, 89, 23);
		btnCadastrar.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(btnCadastrar);
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(191, 354, 89, 23);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnLimpar.setFont(new Font("Consolas", Font.PLAIN, 11));
		contentPane.add(btnLimpar);
		
		JButton btnCarregarImagem = new JButton("Carregar imagem");
		btnCarregarImagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//JFileChooser fc = new JFileChooser("Imagens/");
				JFileChooser fc = new JFileChooser("");
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
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(92, 51, 89, 100);
		contentPane.add(lblNewLabel);
		
		setVisible(true);
	}
}
