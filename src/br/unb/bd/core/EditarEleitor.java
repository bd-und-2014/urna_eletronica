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
public class EditarEleitor extends JFrame {

	private JPanel contentPane;
	private JTextField nomeTextField;
	public final EditarEleitorListener listener;
	private JTextField CPFTextField;
	private JButton btnConcluir;
	
	private JComboBox dataNascimentoComboBox ;
	private JComboBox zonaEleitoralComboBox ;
	private JComboBox secaoEleitoralComboBox ;
	
	private String zona_id, secao_id, nome, CPF, dataNascimento, zona, secao;
	private int dataNascimentoIndex, zonaIndex, secaoIndex;
	
	private ArrayList<ArrayList<String>> objetosZonas, objetosSecoes;
	
	public interface EditarEleitorListener {
		public void didFinishedEditar();
	}

	/**
	 * Create the frame.
	 */
	public EditarEleitor(final ArrayList<String> eleitor, EditarEleitorListener editarEleitorListener) {
		this.listener = editarEleitorListener;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 433);
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
		lblNome.setBounds(10, 200, 46, 14);
		contentPane.add(lblNome);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(10, 228, 46, 14);
		contentPane.add(lblCpf);
		
		JLabel lblDataDeNascimento = new JLabel("Data de Nascimento");
		lblDataDeNascimento.setBounds(10, 259, 105, 14);
		contentPane.add(lblDataDeNascimento);
		
		JLabel lblZonaEleitoral = new JLabel("Zona Eleitoral");
		lblZonaEleitoral.setBounds(10, 290, 76, 14);
		contentPane.add(lblZonaEleitoral);
		
		JLabel lblSeoEleitoral = new JLabel("Se\u00E7\u00E3o Eleitoral");
		lblSeoEleitoral.setBounds(10, 325, 76, 14);
		contentPane.add(lblSeoEleitoral);		
		
		
		nomeTextField = new JTextField();
		nomeTextField.setBounds(55, 200, 223, 20);
		contentPane.add(nomeTextField);
		nomeTextField.setText(eleitor.get(2));
		nomeTextField.setColumns(10);
		
		CPFTextField = new JTextField();
		CPFTextField.setBounds(55, 225, 223, 20);
		contentPane.add(CPFTextField);
		CPFTextField.setText(eleitor.get(1));
		CPFTextField.setColumns(10);
		
		String[] valores = new String[1996-1900+2];
		valores[0] = "";
		for (int i = 1996 ; i >= 1900 ; i--) {
			valores[1996 - i+1] = ""+ i;
			if(eleitor.get(3).equals(valores[1996 - i+1]) ) {
				dataNascimentoIndex = 1996 - i+1;
			}
		}		
		dataNascimentoComboBox = new JComboBox(valores);
		dataNascimentoComboBox.setBounds(124, 256, 70, 20);
		dataNascimentoComboBox.setSelectedIndex(dataNascimentoIndex);
		contentPane.add(dataNascimentoComboBox);
		
		
		zonaEleitoralComboBox = new JComboBox();
		zonaEleitoralComboBox.setBounds(124, 287, 110, 20);
		zonaEleitoralComboBox.setEnabled(false);
		contentPane.add(zonaEleitoralComboBox);
		
		secaoEleitoralComboBox = new JComboBox();
		secaoEleitoralComboBox.setBounds(124, 322, 110, 20);
		secaoEleitoralComboBox.setEnabled(false);
		contentPane.add(secaoEleitoralComboBox);
		
		Banco.getInstance().getAllZonas(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recupera��o dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
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
					if(eleitor.get(6).equals(valores[i])) {
						zonaIndex = i;
					}
				}
				zonaEleitoralComboBox.setModel(new DefaultComboBoxModel(valores));
				zonaEleitoralComboBox.setSelectedIndex(zonaIndex);
				zonaEleitoralComboBox.setEnabled(true);				
			}
		});
		
		zonaEleitoralComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (zonaEleitoralComboBox.getSelectedItem().toString().equals("")) {
					secaoEleitoralComboBox.setEnabled(false);
					return;
				}
				for(int i =0; i < objetosZonas.size(); i++){
					if(objetosZonas.get(i).get(1).equals(zonaEleitoralComboBox.getSelectedItem().toString())) {
						zona_id = objetosZonas.get(i).get(0);
					}
				}
				Banco.getInstance().getAllSecoes(new BancoListener() {
					@Override
					public void BancoListenerDidFinish(JSONArray arrayObject) {
						if (arrayObject == null) {
							JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recupera��o dos dados.", "Erro", JOptionPane.ERROR_MESSAGE);
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
							if(eleitor.get(4).equals(valores[i])) {
								secaoIndex = i;
							}
						}
						secaoEleitoralComboBox.setModel(new DefaultComboBoxModel(valores));
						secaoEleitoralComboBox.setSelectedIndex(secaoIndex);
						secaoEleitoralComboBox.setEnabled(true);						
					}
				}, zona_id);
			}
		});
		
		btnConcluir = new JButton("Concluir");
		btnConcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (nomeTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo nome", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (CPFTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo CPF", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (dataNascimentoComboBox.getSelectedItem().toString().equals("")) {
						JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo data de nascimento", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
				} else if (zonaEleitoralComboBox.getSelectedItem().toString().equals("")){
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o campo zona eleitoral", "Erro", JOptionPane.ERROR_MESSAGE);
					return;
				} else if (secaoEleitoralComboBox.getSelectedItem().toString().equals("")) {
					JOptionPane.showMessageDialog(null, "Voc� esqueceu de preencher o secao eleitoral", "Erro", JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(null, "Quantidade de d�gitos incorreta para CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					
					btnConcluir.setEnabled(false);
					/*Banco.getInstance().updateEleitor(new BancoListener() {
						@Override
						public void BancoListenerDidFinish(JSONArray arrayObject) {
							btnConcluir.setEnabled(true);
							if (arrayObject == null) { return; }
							JOptionPane.showMessageDialog(null, "Eleitor cadastrado com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							listener.didFinishedEditar();
						}
					}, CPF, nome, dataNascimento, secao_id, );	*/	
				}
			}
		});
		btnConcluir.setBounds(123, 367, 89, 23);
		contentPane.add(btnConcluir);
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nomeTextField.setText("");
				CPFTextField.setText("");
				dataNascimentoComboBox.setSelectedIndex(0);
				zonaEleitoralComboBox.setSelectedIndex(0);
			}
		});
		btnLimpar.setBounds(222, 367, 89, 23);
		contentPane.add(btnLimpar);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(165, 67, 99, 100);
		contentPane.add(lblNewLabel);
		
		try {
			byte[] imageByte = Base64.decode(eleitor.get(5));
			ImageIcon imagem = new ImageIcon(imageByte);
			lblNewLabel.setIcon(imagem);
			/*
			BufferedImage image = ImageIO.read(arquivoFoto); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
			ImageIO.write(image, "jpg", baos); 
			byte[] res = baos.toByteArray();
			
			encodedImageString = Base64.encode(res);
			*/
		} catch(Exception e) {
			
		}
		
		
		setVisible(true);
	}
}
