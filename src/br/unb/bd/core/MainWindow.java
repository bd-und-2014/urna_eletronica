package br.unb.bd.core;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import org.json.*;

import br.unb.bd.banco.Banco;
import br.unb.bd.banco.Banco.BancoListener;

public class MainWindow {

	private JFrame frame;

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
		
		// TODO: Criar uma view de carrendo para mostrar para o usuário isso
		exemploDeUtilizacaoDoBanco();
		exemploGetAllCandidatos();
		// Percebam que a unica coisa linear aqui é o inicio do download.
		// O Download em si acontece ao mesmo tempo

	}
	
	public static void exemploGetAllCandidatos() {
		// Testar o banco para o download dos candidatos
		System.out.println("- iniciar carregando dos candidatos ...");
		Banco.getInstance().getAllCandidatos(new BancoListener() {
			@Override
			public void BancoListenerDidFinish(JSONArray arrayObject) {
				if (arrayObject == null) {
					System.out.println("[ERRO] Algum erro ocorreu.");
					return;
				}
				System.out.println("- fim do carregando dos candidatos...");
				System.out.println("1. Imprimindo todos os valores: " + arrayObject);
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
				// Nesse momento o banco já executou sua função em background e tem os valores corretos
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
		frame.setBounds(new Rectangle(0, 23, 800, 600));
		frame.getContentPane().setLayout(null);
		
		// Setar tamanho do frame
		
		
		// Colocar o frame no meio da tela
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
}
