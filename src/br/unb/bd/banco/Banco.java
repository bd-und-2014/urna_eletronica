package br.unb.bd.banco;

import org.json.*;

import br.unb.bd.banco.InternetConnectionJson.InternetConnectionJsonListener;


/*
 * Essa classe implementa o padrão de projeto Singleton
 */
public class Banco implements InternetConnectionJsonListener {
	public interface BancoListener {
		public void BancoListenerDidFinish(JSONArray arrayObject);
	}
	
	private static String URL_ALL_CANDIDATOS = "http://ervilhanalata.com.br/projetos/urna_eletronica/getCandidatos.asp";
	
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
	
	/*
	 * Retorna todos os participantes do nosso grupo. Função de teste - Silent
	 */
	public void getParticipantesDoGrupo(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson("participantes", this, listener);
		connection.execute();
	}

	/*
	 * Essa função é chamada pelo padrão de projeto
	 * Observer quando a conexão de internet já baixou
	 * todos os dados necessários e pode converter aquela string em um JSONArray
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
