package br.unb.bd.core;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import org.json.*;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class MainWindow {
	

	private JFrame frame;
	private int confirmacao;
	
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
		frame.setBounds(new Rectangle(0, 23, 400, 300));
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnControleDeCandidato = new JButton("Controle de Candidatos");
		btnControleDeCandidato.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnControleDeCandidato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ControleCandidato controleCandidato = new ControleCandidato();
			}
		});
		btnControleDeCandidato.setBounds(73, 73, 223, 23);
		frame.getContentPane().add(btnControleDeCandidato);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setBounds(10, 10, 355, 30);
		frame.getContentPane().add(panel_1);
		
		JLabel lblMenuDeControle = new JLabel("Menu de Controle");
		lblMenuDeControle.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblMenuDeControle.setForeground(Color.WHITE);
		panel_1.add(lblMenuDeControle);
		
		JButton btnControleDeEleitores = new JButton("Controle de Eleitores");
		btnControleDeEleitores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ControleEleitor controleEleitor = new ControleEleitor();
			}
		});
		btnControleDeEleitores.setFont(new Font("Consolas", Font.PLAIN, 11));
		btnControleDeEleitores.setBounds(73, 107, 223, 23);
		frame.getContentPane().add(btnControleDeEleitores);
		
		JButton btnNewButton = new JButton("Resetar Urna");
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
		btnNewButton.setBounds(73, 197, 223, 23);
		frame.getContentPane().add(btnNewButton);
		
		// Setar tamanho do frame
		
		
		// Colocar o frame no meio da tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
}
