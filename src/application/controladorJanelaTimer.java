package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lista_pomodoros.LinkedNode;
import lista_pomodoros.ListaDePomodoros;
import lista_pomodoros.ListaExecucao;
import lista_pomodoros.ListaPreparacao;
import perfil.Perfil;
import pomodoro.Pomodoro;
import utilidades.Utilidades;



public class controladorJanelaTimer {
	@FXML
	ListView<String> listviewExecucao;
	@FXML
	TextArea campoDescricao;
	@FXML
	Label campoTempoExecucao;
	@FXML
	Label campoTempoPausa;
	@FXML
	Button botaoPausar;
	@FXML
	Button botaoResumir;
	@FXML
	Label contHora;
	@FXML
	Label contMin;
	@FXML
	Label contSec;
	@FXML
	AnchorPane pane;
	
	Thread timerThread;
	ListaExecucao lExecucao;
	Perfil perfilAssociado;
	
	void initialize(Perfil perfil, ListaPreparacao lista) {
		perfilAssociado = perfil;
		lExecucao = new ListaExecucao();
		listviewExecucao.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		carregaListaExecucao(lista);
		
		iniciarTimer();
	}
	
	/* ===================================================

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	@FXML
	public void remover() {
		if(lExecucao.remover(listviewExecucao.getSelectionModel().getSelectedItem()))
			listviewExecucao.getItems().remove(listviewExecucao.getSelectionModel().getSelectedItem());
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	void cancelar() {
		try {
			// Carrega e prepara o arquivo com as informacoes do layout da janela.
			FXMLLoader loader = new FXMLLoader (getClass().getResource("janela_atividades.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene( (Pane) loader.load()));
			
			// Instancia o controlador da janela carregada anteriormente, executa a sua funcao de inicializacao,
			// passando o perfil selecionado como parametro.
			controladorJanelaAtividades controller = loader.<controladorJanelaAtividades>getController();
			controller.initialize(perfilAssociado, lExecucao);
			
			timerThread.stop();
			
			// Apresenta a janela carregada.
			stage.show();
			
			// Fecha a janela atual.
			((Stage)pane.getScene().getWindow()).close();
		} catch(NullPointerException | IOException e ) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */		
	public void moverAcima() {
		String atividadeSelecionada = listviewExecucao.getSelectionModel().getSelectedItem();
		try {
			if(lExecucao.moverAcima(atividadeSelecionada)) {
				int indiceSelecionado = listviewExecucao.getSelectionModel().getSelectedIndex();
				String atividadeAnterior = listviewExecucao.getItems().get(indiceSelecionado - 1);
				System.out.println("Indice selecionado: "+indiceSelecionado+" - Atividade selecionada: "+atividadeSelecionada+" - Atividade anterior: "+atividadeAnterior);
				listviewExecucao.getItems().set(indiceSelecionado, atividadeAnterior);
				listviewExecucao.getItems().set(indiceSelecionado - 1, atividadeSelecionada);
			}
		} catch(NullPointerException e) {
			//
		}
	}
	
	/* ===================================================

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */		
	public void moverAbaixo() {
		String atividadeSelecionada = listviewExecucao.getSelectionModel().getSelectedItem();
		try {
			if(lExecucao.moverAbaixo(atividadeSelecionada)) {
				int indiceSelecionado = listviewExecucao.getSelectionModel().getSelectedIndex();
				String atividadePosterior = listviewExecucao.getItems().get(indiceSelecionado + 1);
				listviewExecucao.getItems().set(indiceSelecionado, atividadePosterior);
				listviewExecucao.getItems().set(indiceSelecionado + 1, atividadeSelecionada);
			}
		} catch(NullPointerException e) {
			//
		}
	}
	
	void iniciarTimer() {
		timerThread = new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				try {
					System.out.println("Na lista: "+lExecucao.getNumeroItens());
					while(lExecucao.getNumeroItens() > 0) {
						try {
							listviewExecucao.getSelectionModel().select(lExecucao.getNumeroItens() - 1);
							Platform.runLater(new Runnable() {
									@Override
									public void run() {
										apresentarInfo();
//										imprimeTempo(lExecucao.getUltimo().getData().getDuracao());
									}
								}	
							);
						} catch(NullPointerException e) {
							
						}
						System.out.println("Iniciando tarefa!");
						try {
							lExecucao.getUltimo().getData().executaTimer(contHora, contMin, contSec);
							lExecucao.removerUltimo();
							System.out.println("Terminando tarefa!");
							Platform.runLater(new Runnable(){
								@Override
								public void run() {
									listviewExecucao.getItems().remove(lExecucao.getNumeroItens());
									campoDescricao.clear();
									campoTempoExecucao.setText("");
									campoTempoPausa.setText("");
								}
							});
						} catch(InterruptedException e) {
							System.out.println("Testando interrupcao.");
						}
					}
					System.out.println("Terminando timer.");
					timerThread.stop();
				} catch (Exception e) {
					//
				}
			}
		});
		
		timerThread.start();
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	void pausarTimer() {
		if(!listviewExecucao.getItems().isEmpty()) {
			timerThread.suspend();
			botaoResumir.setDisable(false);
			botaoPausar.setDisable(true);
		}
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	void resumirTimer() {
		if(!listviewExecucao.getItems().isEmpty()) {
			timerThread.resume();
			botaoResumir.setDisable(true);
			botaoPausar.setDisable(false);
		}
	}
	
	@SuppressWarnings("deprecation")
	@FXML
	void finalizarAtividade() {
		timerThread.interrupt();
		timerThread.stop();
		lExecucao.removerUltimo();
		listviewExecucao.getItems().remove(lExecucao.getNumeroItens());
		campoDescricao.clear();
		campoTempoExecucao.setText("");
		campoTempoPausa.setText("");
		imprimeTempo(0);
		iniciarTimer();
	}
	
	void carregaListaExecucao(ListaDePomodoros lista) {
		LinkedNode temp = lista.getPrimeiro();
		while(temp != null) {
			lExecucao.insereFinal(temp.getData());
			listviewExecucao.getItems().add(temp.getData().getTitulo());
			temp = temp.getProximo();
		}
	}
	
	@FXML
	void apresentarInfo() {
		try {
			String ativTitulo = listviewExecucao.getSelectionModel().getSelectedItem();
			Pomodoro ativ_selecionada = perfilAssociado.getLista().buscaItem(ativTitulo).getData();
			campoDescricao.setText(ativ_selecionada.toString());
			String stringTempo = Utilidades.tempoToString(ativ_selecionada.duracaoParaHMS());
			campoTempoExecucao.setText("Duracao: "+stringTempo);
			stringTempo = Utilidades.tempoToString(ativ_selecionada.pausaParaHMS());
			campoTempoPausa.setText("Pausa: "+stringTempo);
		} catch (NullPointerException e) {
			// Lista vazia
		}
	}

	/* ===================================================

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	public void imprimeTempo(int tempo) {
		Integer[] hms = Utilidades.secParaHMS(tempo);
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				if(hms[0] <= 9) {
					contHora.setText("0"+hms[0].toString());
				} else {
					contHora.setText(hms[0].toString());
				}
				
				if(hms[1] <= 9) {
					contMin.setText("0"+hms[1].toString());
				} else {
					contMin.setText(hms[1].toString());
				}
				
				if(hms[2] <= 9) {
					contSec.setText("0"+hms[2].toString());
				} else {
					contSec.setText(hms[2].toString());
				}
			}
		});
	}
}
