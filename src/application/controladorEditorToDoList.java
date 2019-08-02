package application;

import java.io.IOException;

import gerenciador_arquivos.Escritor;
import gerenciador_arquivos.Leitor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import perfil.Perfil;
import pomodoro.Pomodoro;
import pomodoro.Tarefa;
import pomodoro.ToDoList;
import utilidades.Utilidades;

public class controladorEditorToDoList {
	private Perfil perfil_associado;
	private ToDoList todolist;
	
	@FXML
	private TextField campoTitulo;
	@FXML
	private TextArea campoDescricao;
	@FXML
	private ComboBox<Integer> campoNumeroTarefas;
	@FXML
	private CheckBox checkAlarme;
	@FXML
	private Label labelAlarmeInicio;
	@FXML
	private Label labelAlarmeFinal;
	@FXML
	private Button botaoBuscarAlarmeInicio;
	@FXML
	private Button botaoBuscarAlarmeFinal;
	@FXML
	private TextField campoAlarmeInicio;
	@FXML
	private TextField campoAlarmeFinal;
	@FXML
	private Button botaoAdicionarTarefas;
	@FXML
	private TextField campoTituloTarefa;
	@FXML
	private Label labelTituloTarefa;
	@FXML
	private Label labelDuracaoTarefa;
	@FXML
	private ComboBox<Integer> campoDuracaoHora;
	@FXML
	private ComboBox<Integer> campoDuracaoMinuto;
	@FXML
	private ComboBox<Integer> campoDuracaoSegundo;
	@FXML
	private Label labelDuracaoHora;
	@FXML
	private Label labelDuracaoMinuto;
	@FXML
	private Label labelDuracaoSegundo;
	@FXML
	private Label labelPausaTarefa;
	@FXML
	private ComboBox<Integer> campoPausaMinuto;
	@FXML
	private ComboBox<Integer> campoPausaSegundo;
	@FXML
	private Label labelPausaMinuto;
	@FXML
	private Label labelPausaSegundo;
	@FXML
	private Button botaoCancelarEditar;
	@FXML
	private Button botaoCancelarCriar;
	@FXML
	private ListView<String> listaTarefas;
	@FXML
	private Button botaoAdicionar;
	@FXML
	private AnchorPane pane;

	
	public void initialize(Perfil perfil) {
		this.perfil_associado = perfil;
		botaoCancelarCriar.setDisable(false);
		carregarComboBoxTempo();
		carregarComboBoxNTarefas();
	}
	
	public void initialize(Perfil perfil, String todolist) throws IOException {
		this.perfil_associado = perfil;
		try {
			botaoCancelarEditar.setDisable(false);
			carregarComboBoxTempo();
			carregarComboBoxNTarefas();
			carregaAtividade(perfil, todolist);
		} catch (ClassNotFoundException | NullPointerException | IOException e) {
			System.out.println("Problema na leitura da atividade: "+ todolist);
			System.out.println(e.getMessage());
		
			// Carrega e prepara o arquivo com as informacoes do layout da janela.
			FXMLLoader loader = new FXMLLoader (getClass().getResource("janela_atividades.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene( (Pane) loader.load())); // throws IOException
			// Instancia o controlador da janela carregada anteriormente, executa a sua funcao de inicializacao,
			// passando o perfil selecionado como parametro.
			controladorJanelaAtividades controller = loader.<controladorJanelaAtividades>getController();
			controller.initialize(perfil);
			// Apresenta a janela carregada.
			stage.show();
			// Fecha a janela atual.
			((Stage)pane.getScene().getWindow()).close();
			
		} 
	}
	
	public void carregaAtividade(Perfil perfil, String todolist) throws ClassNotFoundException, NullPointerException, IOException {
		Pomodoro ativ = Leitor.lerAtividade(todolist, perfil.getNome());
		this.todolist = (ToDoList)ativ;
		campoTitulo.setText(ativ.getTitulo());
		campoDescricao.setText(ativ.getDescricao());
		campoAlarmeInicio.setText(ativ.getAlarmeInicio());
		campoAlarmeFinal.setText(ativ.getAlarmeFim());
		Integer[] dHMS = Utilidades.secParaHMS(ativ.getDuracao());
		Integer[] pHMS = Utilidades.secParaHMS(ativ.getPausa());
		selecionaComboBox(dHMS[0], dHMS[1], dHMS[2], pHMS[1], pHMS[2]);
		carregaListaTarefas(ativ);
	}
	
	public void carregaListaTarefas(Pomodoro ativ) {
		for(int i = 0; i < this.todolist.getIdAtual(); i++) {
			listaTarefas.getItems().add(this.todolist.getTarefas()[i].getTitulo());
		}
	}
	
	@FXML
	public void botaoOK() {
		if(this.todolist != null) {
			System.out.print("Teste salvando arquivo.");
			Escritor.escreverAtividade(this.todolist, this.perfil_associado.getNome());
		}
	}
	
	@FXML
	public void botaoAdicionar() {
		String titulo = campoTituloTarefa.getText();
		if(titulo != null && !titulo.isEmpty() && !this.todolist.estaCheio()) {
			System.out.println("Teste adicao");
			Integer[] duracao = new Integer[3];
			Integer[] pausa = new Integer[2];
			
			duracao[0] = campoDuracaoHora.getSelectionModel().getSelectedItem();
			duracao[1] = campoDuracaoMinuto.getSelectionModel().getSelectedItem();
			duracao[2] = campoDuracaoSegundo.getSelectionModel().getSelectedItem();
			
			pausa[0] = campoPausaMinuto.getSelectionModel().getSelectedItem();
			pausa[1] = campoPausaSegundo.getSelectionModel().getSelectedItem();
			
			Tarefa nova_tarefa = new Tarefa(titulo, Utilidades.hmsParaSec(duracao[0], duracao[1], duracao[2]), Utilidades.hmsParaSec(pausa[0], pausa[1]));
			this.todolist.adicionaTarefa(nova_tarefa);
			listaTarefas.getItems().add(nova_tarefa.toString());
		}
	}
	
	@FXML
	public void botaoAdicionarTarefas() {
		int numeroTarefas = Integer.parseInt(campoNumeroTarefas.getEditor().getText());
		if(numeroTarefas > 0 && !campoTitulo.getText().isEmpty()) {
			this.todolist = new ToDoList(campoTitulo.getText(), campoDescricao.getText(), numeroTarefas);
			ativarDesativarEditorTarefas(false);
		}
	}
	
	public void ativarDesativarEditorTarefas(boolean estado) {
		labelTituloTarefa.setDisable(estado);
		labelDuracaoTarefa.setDisable(estado);
		labelPausaTarefa.setDisable(estado);
		labelDuracaoHora.setDisable(estado);
		labelDuracaoMinuto.setDisable(estado);
		labelDuracaoSegundo.setDisable(estado);
		labelPausaMinuto.setDisable(estado);
		labelPausaSegundo.setDisable(estado);
		campoTituloTarefa.setDisable(estado);
		campoDuracaoHora.setDisable(estado);
		campoDuracaoMinuto.setDisable(estado);
		campoDuracaoSegundo.setDisable(estado);
		campoPausaMinuto.setDisable(estado);
		campoPausaSegundo.setDisable(estado);
		listaTarefas.setDisable(estado);
	}
	
	@FXML
	public void checkboxAlarme() {
		if(!checkAlarme.isSelected()) {
			ativarDesativarEditorAlarmes(true);
		} else {
			ativarDesativarEditorAlarmes(false);
		}
	}
	
	public void ativarDesativarEditorAlarmes(boolean estado) {
		labelAlarmeInicio.setDisable(estado);
		labelAlarmeFinal.setDisable(estado);
		botaoBuscarAlarmeInicio.setDisable(estado);
		botaoBuscarAlarmeFinal.setDisable(estado);
		campoAlarmeInicio.setDisable(estado);
		campoAlarmeFinal.setDisable(estado);
	}
	
	public void carregarComboBoxNTarefas() {
		Integer[] numeroTarefas = new Integer[100];
		for(int i = 0; i < 100; i++) {
			numeroTarefas[i] = i;
		}
		campoNumeroTarefas.getItems().setAll(numeroTarefas);
		campoNumeroTarefas.getSelectionModel().select(0);
	}
	
	public void carregarComboBoxTempo() {
		Integer[] hr = new Integer[25];
		Integer[] min = new Integer[60];
		Integer[] sec = new Integer[60];
		
		for(int i = 0; i <= 24; i++) {
			hr[i] = i;
		}
		for(int i = 0; i < 60; i++) {
			min[i] = i;
			sec[i] = i;
		}
		campoDuracaoHora.getItems().setAll(hr);
		campoDuracaoMinuto.getItems().setAll(min);
		campoPausaMinuto.getItems().setAll(min);
		campoDuracaoSegundo.getItems().setAll(sec);
		campoPausaSegundo.getItems().setAll(sec);
		
		selecionaComboBox(0, 25, 0, 5, 0);
	}
	
	public void selecionaComboBox(Integer dHr, Integer dMin, Integer dSec,
			 					  Integer pMin, Integer pSec) {
		campoDuracaoHora.getSelectionModel().select(dHr);
		campoDuracaoMinuto.getSelectionModel().select(dMin);
		campoPausaMinuto.getSelectionModel().select(pMin);
		campoDuracaoSegundo.getSelectionModel().select(dSec);
		campoPausaSegundo.getSelectionModel().select(pSec);
	}
	
}
