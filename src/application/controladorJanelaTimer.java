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
	public Thread timerThread;
	private ListaExecucao lExecucao;
	private Perfil perfilAssociado;
	
	@FXML
	private ListView<String> listviewExecucao;
	@FXML
	private TextArea campoDescricao;
	@FXML
	private Label campoTempoExecucao;
	@FXML
	private Label campoTempoPausa;
	@FXML
	private Button botaoPausar;
	@FXML
	private Button botaoResumir;
	@FXML
	public Label contHora;
	@FXML
	public Label contMin;
	@FXML
	public Label contSec;
	@FXML
	private AnchorPane painel;
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo de inicializacao da janela
	Entrada         - Um tipo perfil com as informacoes do usuario. Um tipo ListaPreparacao
					com as atividades selecionadas pelo usuario, na janela anterior
	Processamento   - Cria uma lista de execucao com a as atividades da lista de preparacao,
					inicia o timer automaticamente.
	Saida           - 

	=================================================== */
	void initialize(Perfil perfil, ListaPreparacao lista) {
		perfilAssociado = perfil;
		lExecucao = new ListaExecucao();
		listviewExecucao.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		carregaListaExecucao(lista);
		
		iniciarTimer();
	}
	
	/* ===================================================

	Metodo          - remover
	Descricao       - Metodo associado ao botao "Remover".
	Entrada         - 
	Processamento   - Remove um elemento da lista de execucao. O ultimo elemento da lista nao
					pode ser removido, pois esta sendo executado.
	Saida           - 

	=================================================== */
	@FXML
	public void remover() {
		if(lExecucao.remover(listviewExecucao.getSelectionModel().getSelectedItem()))
			listviewExecucao.getItems().remove(listviewExecucao.getSelectionModel().getSelectedItem());
	}
	
	/* ===================================================

	Metodo          - cancelar
	Descricao       - Metodo associado ao botao "Cancelar".
	Entrada         - 
	Processamento   - Para o timer, abre a janela de atividades e fecha a janela atual.
	Saida           - 

	=================================================== */
	@SuppressWarnings("deprecation")
	@FXML
	void cancelar() {
		try {
			// Carrega e prepara o arquivo com as informacoes do layout da janela.
			FXMLLoader loader = new FXMLLoader (getClass().getResource("janela_atividades.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene( (Pane) loader.load())); // lanca IOException
			
			// Instancia o controlador da janela carregada anteriormente, executa a sua funcao de inicializacao,
			// passando o perfil selecionado como parametro.
			controladorJanelaAtividades controller = loader.<controladorJanelaAtividades>getController();
			controller.initialize(perfilAssociado, lExecucao);
			
			timerThread.stop();
			
			// Apresenta a janela carregada.
			stage.show();
			
			// Fecha a janela atual.
			((Stage)painel.getScene().getWindow()).close();
		} catch(IOException e ) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - moverAcima
	Descricao       - Metodo associado ao botao "Up"
	Entrada         - 
	Processamento   - Tenta mover uma posicao acima na lista de execucao uma atividade selecionada.
					O ultimo item da lista, que esta sendo executado, nao pode ser movido.
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
			// O catch cumpre o proposito de nao parar o programa quando o usuario tenta fazer
			// um movimento sem selecionar nada na lista.
		}
	}
	
	/* ===================================================

	Metodo          - moverAbaixo
	Descricao       - Metodo associado ao botao "Down"
	Entrada         - 
	Processamento   - Tenta mover uma posicao abaixo na lista de execucao uma atividade selecionada.
					A penultima atividade nao pode ser movida, devido a impossibilidade de se deslocar
					a ultima atividade.
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
			// O catch cumpre o proposito de nao parar o programa quando o usuario tenta fazer
			// um movimento sem selecionar nada na lista.
		}
	}
	
	/* ===================================================

	Metodo          - finalizarAtividade
	Descricao       - Metodo associado ao botao "Finalizar"
	Entrada         - 
	Processamento   - Finaliza a ultima tarefa da lista, sendo executada, e reinicia o timer
					com a proxima tarefa da lista.
	Saida           - 

	=================================================== */
	@SuppressWarnings("deprecation")
	@FXML
	void finalizarAtividade() {
		try {
			// Interrompe a execucao do timer
			timerThread.interrupt();
			// Para a thread do timer
			timerThread.stop();
			// Remove da lista o elemento sendo executado
			lExecucao.removerUltimo();
			listviewExecucao.getItems().remove(lExecucao.getNumeroItens());
			campoDescricao.clear();
			campoTempoExecucao.setText("");
			campoTempoPausa.setText("");
			imprimeTempo(0);
			// Recomeca o timer com um novo elemento na ultima posicao
			iniciarTimer();
		} catch(IndexOutOfBoundsException e) {
			// Catch para o caso em que a lista de atividades esta vazia e o usuario
			// tenta finalizar alguma coisa.
		}
	}
	
	/* ===================================================

	Metodo          - iniciarTimer
	Descricao       - Metodo de inicializacao automatica do Timer
	Entrada         - 
	Processamento   - Faz as chamadas dos metodos de gerenciamento do timer escritos nas classes
					de atividades, implementando metodos de uma interaface. A execucao do timer eh
					interrompida apenas com interacao do usuario, ou quando todos os elementos da
					lista foram removidos.
	Saida           - 

	=================================================== */
	void iniciarTimer() {
		// Declara uma nova Thread, especificando a sua funcao de execucao
		timerThread = new Thread(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				// O timer sera executado ate que todos os itens da lista tenham sido removidos.
				while(lExecucao.getNumeroItens() > 0) {
					// Seleciona automaticamente a ultima atividade da lista para apresentar as informacoes
					try {
						listviewExecucao.getSelectionModel().select(lExecucao.getNumeroItens() - 1);
						// Procedimento necessario para fazer modificoes na interface a partir de uma
						// thread diferente da thread da interface.
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								apresentarInfo();
							}
						}	
								);
					} catch(NullPointerException e) {
						// Um catch para o caso em que haja um problema na selecao da atividade
					}
					// Tenta fazer as chamadas das funcoes de gerenciamento do timer das classes de atividades.
					try {
						lExecucao.getUltimo().getData().executaTimer(contHora, contMin, contSec);
						// O metodo termina a linha anterior quando a atividade foi devidamente executada.
						// Entao eh removida a atividade da lista.
						lExecucao.removerUltimo();
						// Eh feita uma atualizacao da interface para tirar as informacoes da atividade removida.
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
						// Um catch para que nao de erro quando ocorre alguma interrupcao da Thread,
						// seja por um erro ou por uma interacao do usuario que decidiu interromper
						// a execucao.
					}
				}
				// Ao terminar todas as atividades, finaliza a Thread do timer.
				timerThread.stop();
			}
		});
		
		// Inicia a nova thread, paralela a atual.
		timerThread.start();
	}
	
	/* ===================================================

	Metodo          - pausarTimer
	Descricao       - Metodo associado ao botao "Pausar"
	Entrada         - 
	Processamento   - Pausa a execucao do timer.
	Saida           - 

	=================================================== */
	@SuppressWarnings("deprecation")
	@FXML
	void pausarTimer() {
		// Verifica se o timer esta realmente executando, i.e. ainda
		// existem elementos na lista de execucao.
		if(!listviewExecucao.getItems().isEmpty()) {
			// Para a execucao da Thread do timer.
			timerThread.suspend();
			// Desativa o botao "Pausar" e ativa o botao "Resumir".
			botaoResumir.setDisable(false);
			botaoPausar.setDisable(true);
		}
	}
	
	/* ===================================================

	Metodo          - resumirTimer
	Descricao       - Metodo associado ao botao "Resumir"
	Entrada         - 
	Processamento   - Resume a execucao do timer.
	Saida           - 

	=================================================== */
	@SuppressWarnings("deprecation")
	@FXML
	void resumirTimer() {
		if(!listviewExecucao.getItems().isEmpty()) {
			timerThread.resume();
			botaoResumir.setDisable(true);
			botaoPausar.setDisable(false);
		}
	}
	
	/* ===================================================

	Metodo          - carregaListaExecucao
	Descricao       - Metodo auxiliar a inicializacao da janela.
	Entrada         - 
	Processamento   - Pega os elementos da lista de preparacao e adiciona na lista de execucao.
					Adiciona os titulos das atividades na lista de visualizacao da interface.
	Saida           - 

	=================================================== */
	void carregaListaExecucao(ListaDePomodoros lista) {
		LinkedNode temp = lista.getPrimeiro();
		while(temp != null) {
			lExecucao.insereFinal(temp.getData());
			listviewExecucao.getItems().add(temp.getData().getTitulo());
			temp = temp.getProximo();
		}
	}
	
	/* ===================================================

	Metodo          - apresentarInfo
	Descricao       - Metodo auxiliar para apresentacao automatica das informacoes na interface
	Entrada         - 
	Processamento   - Pega a atividade selecionada (a ultima da lista, selecionada automaticamente
					no metodo de execucao do timer) e apresenta as informacoes na interface.
	Saida           - 

	=================================================== */
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

	Metodo          - imprimeTempo
	Descricao       - Metodo auxiliar para apresentacao do tempo do contador na interface grafica
	Entrada         - Um inteiro com o tempo em segundos
	Processamento   - Faz a formatacao adequada para apresentar o tempo no formato 00:00:00
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
