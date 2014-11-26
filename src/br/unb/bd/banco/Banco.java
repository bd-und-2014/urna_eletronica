package br.unb.bd.banco;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.*;

import br.unb.bd.banco.InternetConnectionJson.InternetConnectionJsonListener;


/*
 * Essa classe implementa o padr‹o de projeto Singleton
 */
public class Banco implements InternetConnectionJsonListener {
	public interface BancoListener {
		public void BancoListenerDidFinish(JSONArray arrayObject);
	}
	
	private static String URL_ALL_CANDIDATOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getCandidatos.asp";
	private static String URL_ALL_ELEITORES = "http://ervilhanalata.com.br/ervilha/projetos/urna_eletronica/getAllEleitores.asp";
	private static String URL_ALL_PARTIDOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getPartidos.asp";
	private static String URL_ALL_ESTADOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getEstados.asp";
	private static String URL_ALL_CARGOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getCargos.asp";
	private static String URL_RESET_URNA = "http://ervilhanalata.com.br/projetos/urna_eletronica/ios/ipad/resetarTudo.asp";
	private static String URL_INSERT_CANDIDATO = "http://ervilhanalata.com.br/projetos/urna_eletronica/insertCandidato.asp?";
	private static String URL_INSERT_ELEITOR = "http://ervilhanalata.com.br/projetos/urna_eletronica/insertEleitor.asp?";
	private static String URL_ALL_ZONAS = "http://ervilhanalata.com.br/ervilha/projetos/urna_eletronica/getAllZonas.asp";
	private static String URL_ALL_SECOES = "http://ervilhanalata.com.br/ervilha/projetos/urna_eletronica/getAllSecoes.asp?";
	
	private static Banco instance;
	
	private Banco() {
		
	}
	
	public static Banco getInstance() {
		if (instance == null) {
			instance = new Banco();
		}
		return instance;
	}
	/*
	 * Controle de Votos
	 */
	
	public void getInfoVotosEstado(BancoListener listener, String estadoID) {
		//TODO: Implementar isso aqui
		
		String jsonString = "[{'porcentagem':45, 'deputado_estadual':'candidato_deputado', 'deputado_federal':'candidato_deputado', 'senador':'candidato_senador', 'governador':'governador',  'presidente':'candidato_presidente'}]";
		JSONArray arrayObject = new JSONArray(jsonString);
		listener.BancoListenerDidFinish(arrayObject);
	}
	
	/*
	 * Retorna todos os candidatos cadastrados no Banco.
	 */
	public void getAllCandidatos(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_CANDIDATOS, this, listener);
		connection.execute();
	}
	
	public void getAllPartidos(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_PARTIDOS, this, listener);
		connection.execute();
	}
	
	public void getAllEstados(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_ESTADOS, this, listener);
		connection.execute();
	}
	
		
	public void getAllEleitores(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_ELEITORES, this, listener);
		connection.execute();
	}
	
	public void getAllCargos(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_CARGOS, this, listener);
		connection.execute();
	}
	
	public void getAllZonas(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_ZONAS, this, listener);
		connection.execute();
	}
	
	public void getAllSecoes(BancoListener listener, String zona_id) {
		String urlFinal = URL_ALL_SECOES + "zona_id=" +zona_id;
		InternetConnectionJson connection = new InternetConnectionJson(urlFinal, this, listener);
		connection.execute();
	}
	
	public void resetarUrna(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_RESET_URNA, this, listener);
		connection.execute();
	}
	
	public void insertCandidato(BancoListener listener, String candidato_nome, String candidato_foto, String candidato_numero, String estado_id, String cargo_id, String partido_id) {
		
		try {
			String urlFinal = URL_INSERT_CANDIDATO + "candidato_nome=" + URLEncoder.encode(candidato_nome, "UTF-8");
			urlFinal += "&candidato_foto=" + URLEncoder.encode(candidato_foto.replace("\n", ""), "UTF-8");
			urlFinal += "&candidato_numero=" + candidato_numero;
			urlFinal += "&estado_id=" + estado_id;
			urlFinal += "&cargo_id=" + cargo_id;
			urlFinal += "&partido_id=" + partido_id;
			
			System.out.println("hey: " + urlFinal);
			
			InternetConnectionJson connection = new InternetConnectionJson(urlFinal, this, listener);
			connection.execute();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteCandidato(BancoListener listener, String URL_DELETE_CANDIDATO) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_DELETE_CANDIDATO, this, listener);
		connection.execute();
	}
	
	public void updateCandidato(BancoListener listener, String URL_UPDATE_CANDIDATO) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_UPDATE_CANDIDATO, this, listener);
		connection.execute();
	}
	
	public void insertEleitor(BancoListener listener, String eleitor_cpf, String eleitor_nome, String eleitor_foto, String eleitor_data_nascimento, String secao_id) {
		
		try {
			String urlFinal = URL_INSERT_ELEITOR + "eleitor_cpf=" + eleitor_cpf;
			urlFinal += "&eleitor_nome=" + URLEncoder.encode(eleitor_nome, "UTF-8");
			urlFinal += "&eleitor_data_nascimento=" + eleitor_data_nascimento;
			urlFinal += "&eleitor_foto=" + URLEncoder.encode(eleitor_foto.replace("\n", ""), "UTF-8");
			urlFinal += "&secao_id=" + secao_id;
			
			System.out.println("hey: " + urlFinal);
			
			InternetConnectionJson connection = new InternetConnectionJson(urlFinal, this, listener);
			connection.execute();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Retorna todos os participantes do nosso grupo. Fun�‹o de teste - Silent
	 */
	public void getParticipantesDoGrupo(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson("participantes", this, listener);
		connection.execute();
	}

	/*
	 * Essa fun�‹o Ž chamada pelo padr‹o de projeto
	 * Observer quando a conex‹o de internet j‡ baixou
	 * todos os dados necess‡rios e pode converter aquela string em um JSONArray
	 */
	@Override
	public void InternetConnectionJsonListenerDidFinish(String jsonString, BancoListener listenerBanco) {
		try {
			if (jsonString == null) {
				listenerBanco.BancoListenerDidFinish(null);
				return;
			}
			JSONArray arrayObject = new JSONArray(jsonString);
			listenerBanco.BancoListenerDidFinish(arrayObject);
		} catch (Exception e) {
			listenerBanco.BancoListenerDidFinish(null);
			e.printStackTrace();
		}
	}
	
	
}
