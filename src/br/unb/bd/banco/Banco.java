package br.unb.bd.banco;

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
	private static String URL_ALL_PARTIDOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getPartidos.asp";
	private static String URL_ALL_ESTADOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getEstados.asp";
	private static String URL_ALL_CARGOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getCargos.asp";
	//private static String URL_INSERT_CANDIDATO = "http://ervilhanalata.com.br/projetos/urna_eletronica/getCargos.asp";
	
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
	
	public void getAllCargos(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson(URL_ALL_CARGOS, this, listener);
		connection.execute();
	}
	
	public void insertCandidato(BancoListener listener) {
		//InternetConnectionJson connection = new InternetConnectionJson(URL_INSERT_CANDIDATO, this, listener);
		//connection.execute();
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
