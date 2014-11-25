package br.unb.bd.core;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.json.*;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainWindow {
	

	private JFrame frame;
	private int confirmacao;
	private JTable table;
	
	private ArrayList<ArrayList<String>> objetosEstados;
	
	public JComboBox comboBox;
	public JPanel panel_3;
	public JProgressBar progressBar;
	public JLabel label_1;
	public JLabel lblNewLabel;
	public JLabel lblErro;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// TODO: Criar uma view de carrendo para mostrar para o usu‡rio isso
		//exemploDeUtilizacaoDoBanco();
		//exemploGetAllCandidatos();
		// Percebam que a unica coisa linear aqui Ž o inicio do download.
		// O Download em si acontece ao mesmo tempo

	}
	
	public static void exemploGetAllCandidatos() {
		// Testar o banco para o download dos candidatos
		System.out.println("- iniciar carregando dos candidatos ...");
		Banco.getInstance().getAllCandidatos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				System.out.println("- fim do carregando dos candidatos...");
				System.out.println("1. Imprimindo todos os valores: " + arrayObject);
			}
		});
	}
	
	public static void exemploGetAllPartidos() {
		Banco.getInstance().getAllPartidos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					JOptionPane.showMessageDialog(null, "Algum erro ocorreu na recuperação dos dados.", "Erro", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		});
	}
	
	public static void exemploDeUtilizacaoDoBanco() {
		/* Testar o banco inicialmente */
		System.out.println("- iniciar carregando dos participantes ...");
		Banco.getInstance().getParticipantesDoGrupo(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					System.out.println("[ERRO] Algum erro ocorreu.");
					return;
				}
				System.out.println("- fim do carregando dos participantes...");
				// Nesse momento o banco j‡ executou sua fun�‹o em background e tem os valores corretos
				System.out.println("1. Imprimindo todos os valores: " + arrayObject);
				// Como fazer para pegar todos os elementos separados:
				for (int i = 0; i < arrayObject.length(); i++) {
					JSONObject objAtual = arrayObject.getJSONObject(i);
					System.out.println("2." + (i + 1) + " " + objAtual);
				}
				// Como faz para pegar o nome do primeiro integrante?
				JSONObject objPrimeiro = arrayObject.getJSONObject(0);
				System.out.println("3. " + objPrimeiro.getString("nome"));
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Consolas", Font.PLAIN, 11));
		frame.setBounds(new Rectangle(0, 23, 740, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnControleDeCandidato = new JButton("Controle de Candidatos");
		btnControleDeCandidato.setBounds(73, 73, 223, 23);
		btnControleDeCandidato.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnControleDeCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ControleCandidato controleCandidato = new ControleCandidato();
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(btnControleDeCandidato);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 355, 30);
		panel_1.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblMenuDeControle = new JLabel("Menu de Controle");
		lblMenuDeControle.setBounds(123, 6, 95, 14);
		lblMenuDeControle.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblMenuDeControle.setForeground(Color.WHITE);
		panel_1.add(lblMenuDeControle);
		
		JButton btnControleDeEleitores = new JButton("Controle de Eleitores");
		btnControleDeEleitores.setBounds(73, 107, 223, 23);
		btnControleDeEleitores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ControleEleitor controleEleitor = new ControleEleitor();
			}
		});
		btnControleDeEleitores.setFont(new Font("Consolas", Font.PLAIN, 11));
		frame.getContentPane().add(btnControleDeEleitores);
		
		JButton btnNewButton = new JButton("Resetar Urna");
		btnNewButton.setBounds(73, 197, 223, 23);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				confirmacao = JOptionPane.showConfirmDialog(null, "Deseja mesmo resetar a Urna?", "Confirmação", JOptionPane.INFORMATION_MESSAGE);
				if (confirmacao == JOptionPane.YES_OPTION) {
					Banco.getInstance().resetarUrna(new BancoListener() {
						@Override
						public void BancoListenerDidFinish(JSONArray arrayObject) {
							// TODO Auto-generated method stub	
						}
					});
					JOptionPane.showMessageDialog(null, "Urna Resetada com sucesso.", "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 11));
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(377, 10, 355, 30);
		panel.setLayout(null);
		panel.setBackground(Color.DARK_GRAY);
		frame.getContentPane().add(panel);
		
		JLabel lblControleDosVotos = new JLabel("Controle dos Votos");
		lblControleDosVotos.setForeground(Color.WHITE);
		lblControleDosVotos.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblControleDosVotos.setBounds(126, 6, 109, 14);
		panel.add(lblControleDosVotos);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		panel_2.setBounds(377, 52, 355, 206);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBounds(6, 38, 343, 162);
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		panel_3.setVisible(false);
		
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setHorizontalAlignment(SwingConstants.LEFT);
		lblEstado.setBounds(6, 10, 60, 16);
		panel_2.add(lblEstado);
		
		comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (comboBox.getSelectedIndex() == 0) {
					panel_3.setVisible(false);
				} else {
					panel_3.setVisible(true);
					refreshCandidatosPanel_3();
				}
			}
		});
		comboBox.setBounds(67, 6, 282, 27);
		comboBox.setEnabled(false);
		panel_2.add(comboBox);
		
		JLabel lblDeVotos = new JLabel("% de votos efetuados/apurados:");
		lblDeVotos.setHorizontalAlignment(SwingConstants.LEFT);
		lblDeVotos.setBounds(0, 6, 343, 16);
		panel_3.add(lblDeVotos);
		
		progressBar = new JProgressBar();
		progressBar.setBounds(0, 26, 293, 20);
		panel_3.add(progressBar);
		
		label_1 = new JLabel("0%");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(301, 28, 42, 16);
		panel_3.add(label_1);
		
		JLabel label_2 = new JLabel("Candidatos Eleitos até o momento:");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setBounds(0, 55, 343, 16);
		panel_3.add(label_2);
		
		table = new JTable();
		table.setBounds(0, 72, 343, 90);
		panel_3.add(table);
		table.setOpaque(false);
		
		lblNewLabel = new JLabel("carregando...");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setBounds(377, 256, 90, 16);
		frame.getContentPane().add(lblNewLabel);
		
		lblErro = new JLabel("erro");
		lblErro.setForeground(Color.RED);
		lblErro.setHorizontalAlignment(SwingConstants.RIGHT);
		lblErro.setBounds(671, 256, 61, 16);
		lblErro.setVisible(false);
		frame.getContentPane().add(lblErro);
		lblNewLabel.setVisible(false);
		
		
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
				
				comboBox.setModel(new DefaultComboBoxModel(valores));
				comboBox.setEnabled(true);
			}
		});
		
		
		
		
		// Colocar o frame no meio da tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
	public void iniciarClock() {
		ActionListener action = new ActionListener() {  
            public void actionPerformed(@SuppressWarnings("unused") java.awt.event.ActionEvent e) {  
            	refreshCandidatosPanel_3();
            }  
        };  
        Timer t = new Timer(3000, action);  
        t.start(); 
	}
	public void refreshCandidatosPanel_3() {
		if (panel_3.isVisible() == false || lblNewLabel.isVisible() == true) { return; } 
		int selectedIndex = comboBox.getSelectedIndex();
		if (selectedIndex == 0) { 
			panel_3.setVisible(false);
			return;
		}
		String estadoID = objetosEstados.get(selectedIndex - 1).get(0);
		lblNewLabel.setVisible(true);
		Banco.getInstance().getInfoVotosEstado(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				lblNewLabel.setVisible(false);
				iniciarClock();
				if (arrayObject == null || arrayObject.length() == 0) {
					lblErro.setVisible(true);
					return;
					// Algum erro
				}
				lblErro.setVisible(false);
				JSONObject objAtual = arrayObject.getJSONObject(0);
				progressBar.setValue(objAtual.getInt("porcentagem"));
				label_1.setText(objAtual.getInt("porcentagem") + "%");
				int selectedIndex = comboBox.getSelectedIndex();
				if (selectedIndex == objetosEstados.size()) { // Todos os estados
					String[][] conteudo = new String[1][2];
					String[] titulos = new String[2];
					titulos[0] = "Cargo";
					titulos[1] = "Candidato";
					conteudo[0][0] = "Presidente";
					conteudo[0][1] = objAtual.getString("presidente");
					table.setModel(new DefaultTableModel(conteudo, titulos));
				} else {
					String[][] conteudo = new String[5][2];
					String[] titulos = new String[2];
					titulos[0] = "Cargo";
					titulos[1] = "Candidato";
					conteudo[0][0] = "Deputado Estadual";
					conteudo[1][0] = "Deputado Federal";
					conteudo[2][0] = "Senador";
					conteudo[3][0] = "Governador";
					conteudo[4][0] = "Presidente";
					conteudo[0][1] = objAtual.getString("deputado_estadual");
					conteudo[1][1] = objAtual.getString("deputado_federal");
					conteudo[2][1] = objAtual.getString("senador");
					conteudo[3][1] = objAtual.getString("governador");
					conteudo[4][1] = objAtual.getString("presidente");
					
					table.setModel(new DefaultTableModel(conteudo, titulos));
				}
			}
		}, estadoID);
		

	}
}
