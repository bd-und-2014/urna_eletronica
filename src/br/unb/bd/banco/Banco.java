package br.unb.bd.banco;

import org.json.*;

import br.unb.bd.banco.InternetConnectionJson.InternetConnectionJsonListener;


/*
 * Essa classe implementa o padr�o de projeto Singleton
 */
public class Banco implements InternetConnectionJsonListener {
	public interface BancoListener {
		public void BancoListenerDidFinish(JSONArray arrayObject);
	}
	
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
	 * Retorna todos os participantes do nosso grupo. Fun��o de teste - Silent
	 */
	public JSONArray getParticipantesDoGrupo(BancoListener listener) {
		InternetConnectionJson connection = new InternetConnectionJson("participantes", this, listener);
		connection.execute();
		return null;
	}

	/*
	 * Essa fun��o � chamada pelo padr�o de projeto
	 * Observer quando a conex�o de internet j� baixou
	 * todos os dados necess�rios e pode converter aquela string em um JSONArray
	 */
	@Override
	public void InternetConnectionJsonListenerDidFinish(String jsonString, BancoListener listenerBanco) {
		try {
			JSONArray arrayObject = new JSONArray(jsonString);
			listenerBanco.BancoListenerDidFinish(arrayObject);
		} catch (Exception e) {
			listenerBanco.BancoListenerDidFinish(null);
			e.printStackTrace();
		}
	}
	
	
}
