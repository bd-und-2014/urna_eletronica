package br.unb.bd.banco;

import javax.swing.*;

import br.unb.bd.banco.Banco.BancoListener;

/*
 * Essa classe serve para criar uma conexão com a internet
 * em Background (outra Thread) e quando terminar, comunicar
 * com a Thread principal (UI Thread) transmitindo uma String
 * que contém aquele Text JSON
 */



public class InternetConnectionJson extends SwingWorker<Void, Void> {
	/* Implementação do padrão de projeto Observer */
	public interface InternetConnectionJsonListener {
		public void InternetConnectionJsonListenerDidFinish(String jsonString, BancoListener listenerBanco);
	} 

	private InternetConnectionJsonListener listener; /* Observer */
	private BancoListener listenerBanco; /* Observer do Banco para ser propagado */
	private String jsonString;
	private String url;
	
	public InternetConnectionJson(String url, InternetConnectionJsonListener listener, BancoListener listenerBanco) {
		this.listener = listener;
		this.url = url;
		this.listenerBanco = listenerBanco;
	}
	
	/* O que será executado em background */
	@Override
	protected Void doInBackground() throws Exception {
		//TODO: Connectar na URL e baixar os dados
		
		if (url.equals("participantes")) { // Apenas para testar inicialmente
			jsonString = "[{\"id\":\"1\", \"nome\":\"Matheus Ervilha\"}, {\"id\":\"2\", \"nome\":\"Matheus Medeiros\"}, {\"id\":\"3\", \"nome\":\"Stephannie Chiang\"}]";
			// Apenas um sleep para simular o background thread
			Thread.sleep(2000); // 2 segundos
			return null;
		}
		
		return null;
	}

	/* Assim que acabou o background, ele irá executar isso */
	@Override
	protected void done() {
		System.out.println("[InternetConnectionJson] Terminou");
		listener.InternetConnectionJsonListenerDidFinish(jsonString, listenerBanco);
		
		super.done();
	}



}
