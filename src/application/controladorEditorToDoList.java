package application;

import java.io.File;
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
import javafx.stage.FileChooser;
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
	private AnchorPane painel;

	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo de inicializacao da interface, quando aberta para criacao.
	Entrada         - Um tipo Perfil com as informacoes do usuario.
	Processamento   - Alimenta as listas de numeros (ComboBox) para a selecao dos tempos,
					ativa o botao "Cancelar" vinculado ao processo de criacao. Associa a janela
					com o perfil do usuario.
	Saida           - 

	=================================================== */
	public void initialize(Perfil perfil) {
		this.perfil_associado = perfil;
		botaoCancelarCriar.setDisable(false);
		carregarComboBoxTempo();
		carregarComboBoxNTarefas();
	}
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo de inicializacao da interface, quando aberta para edicao.
	Entrada         - Um tipo Perfil com as informacoes do usuario, uma string com o titulo
					da lista de tarefas.
	Processamento   - Carrega a interface com as informacoes da atividade que sera editada.
	Saida           - 

	=================================================== */
	public void initialize(Perfil perfil, String todolist) throws IOException {
		this.perfil_associado = perfil;
		try {
			botaoCancelarEditar.setDisable(false);
			ativarDesativarEditorTarefas(false);
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
			((Stage)painel.getScene().getWindow()).close();
			
		} 
	}
	
	/* ===================================================

	Metodo          - botaoOK
	Descricao       - Metodo associado ao botao "OK"
	Entrada         - 
	Processamento   - Escreve a atividade criada em um arquivo para backup.
	Saida           - 

	=================================================== */
	@FXML
	public void botaoOK() {
		if(this.todolist != null) {
			System.out.print("Teste salvando arquivo.");
			Escritor.escreverAtividade(this.todolist, this.perfil_associado.getNome());
		}
	}
	
	/* ===================================================

	Metodo          - botaoBuscarAlarmeInicio
	Descricao       - Metodo associado ao botao "Buscar" para encontrar um arquivo de audio do
					alarme de inicio
	Entrada         - 
	Processamento   - Abre um "FileChooser" para que o usuario encontre um arquivo. O endereco
					do arquivo selecionado eh apresentado em um campo de texto na interface
					grafica.
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoBuscarAlarmeInicio() {
		FileChooser buscador = new FileChooser();
		File arquivo = buscador.showOpenDialog(null);
		try {
			campoAlarmeInicio.setText(arquivo.getAbsolutePath());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - botaoBuscarAlarmeFim
	Descricao       - Metodo associado ao botao "Buscar" para encontrar um arquivo de audio do
					alarme de termino.
	Entrada         - 
	Processamento   - Abre um "FileChooser" para que o usuario encontre um arquivo. O endereco
					do arquivo selecionado eh apresentado em um campo de texto na interface
					grafica.
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoBuscarAlarmeFim() {
		FileChooser buscador = new FileChooser();
		File arquivo = buscador.showOpenDialog(null);
		
		try {
			campoAlarmeFinal.setText(arquivo.getAbsolutePath());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - botaoAdicionarTarefas
	Descricao       - Metodo associado ao botao "Adicionar Tarefas", que permite ao usuario
					comecar a adicionar tarefas na lista.
	Entrada         - 
	Processamento   - Se o usuario insere um titulo e um numero > 0 de tarefas para a lista,
					eh criada uma instancia de uma ToDoList, contendo um vetor de tarefas,
					e os elementos da interface associados a criacao de tarefas sao habilitados
					para interacao com o usuario. O objeto ToDoList eh instanciado com o construtor
					adequado, a depender da escolha do usuario de customizar ou nao o alarme.
	Saida           - 

	=================================================== */
	@FXML
	public void botaoAdicionarTarefas() {
		int numeroTarefas = Integer.parseInt(campoNumeroTarefas.getEditor().getText());
		if(numeroTarefas > 0 && !campoTitulo.getText().isEmpty()) {
			if(!checkAlarme.isSelected()) {
				this.todolist = new ToDoList(campoTitulo.getText(), campoDescricao.getText(), numeroTarefas);
			} else {
				String alarme_inicio = campoAlarmeInicio.getText();
				String alarme_final = campoAlarmeFinal.getText();
				this.todolist = new ToDoList(campoTitulo.getText(), campoDescricao.getText(), numeroTarefas, alarme_inicio, alarme_final);
			}
			ativarDesativarEditorTarefas(false);
		}
	}
	
	/* ===================================================

	Metodo          - botaoAdicionar
	Descricao       - Metodo associado ao botao "Adicionar", para adicionar tarefas na lista
	Entrada         - 
	Processamento   - Se o usuario especificou um titulo para a tarefa, faz a instanciacao
					da tarefa e insercao, se o vetor de tarefas da lista nao esta totalmente
					preenchida.
	Saida           - 

	=================================================== */
	@FXML
	public void botaoAdicionar() {
		String titulo = campoTituloTarefa.getText();
		if(!titulo.isEmpty()) {
			System.out.println("Teste adicao");
			Integer[] duracao = new Integer[3];
			Integer[] pausa = new Integer[2];
			
			duracao[0] = campoDuracaoHora.getSelectionModel().getSelectedItem();
			duracao[1] = campoDuracaoMinuto.getSelectionModel().getSelectedItem();
			duracao[2] = campoDuracaoSegundo.getSelectionModel().getSelectedItem();
			
			pausa[0] = campoPausaMinuto.getSelectionModel().getSelectedItem();
			pausa[1] = campoPausaSegundo.getSelectionModel().getSelectedItem();
			
			Tarefa nova_tarefa = new Tarefa(titulo, Utilidades.hmsParaSec(duracao[0], duracao[1], duracao[2]), Utilidades.hmsParaSec(pausa[0], pausa[1]));
			if(this.todolist.adicionaTarefa(nova_tarefa))
				listaTarefas.getItems().add(nova_tarefa.toString());
		}
	}
	
	/* ===================================================

	Metodo          - ativarDesativarEditorTarefas
	Descricao       - Metodo auxiliar para habilitacao ou desabilitacao dos itens da interface
					relativos a edicao de tarefas
	Entrada         - Um boolean com o estado dos objetos da interface
	Processamento   - Faz a mudanca de estado habilitado/desabilitado.
	Saida           - 

	=================================================== */
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
	
	/* ===================================================

	Metodo          - ativarDestivarEditorAlarmes
	Descricao       - Metodo auxiliar para a habilitacao ou desabilitacao dos itens da interfaces
					relacionados a edicao dos alarmes.
	Entrada         - Um boolean com o novo estado.
	Processamento   - Faz a mudanca de estado habilitado/desabilitado.
	Saida           - 

	=================================================== */
	public void ativarDesativarEditorAlarmes(boolean estado) {
		labelAlarmeInicio.setDisable(estado);
		labelAlarmeFinal.setDisable(estado);
		botaoBuscarAlarmeInicio.setDisable(estado);
		botaoBuscarAlarmeFinal.setDisable(estado);
		campoAlarmeInicio.setDisable(estado);
		campoAlarmeFinal.setDisable(estado);
	}
	
	/* ===================================================

	Metodo          - checkboxEvent
	Descricao       - Metodo relacionado ao Checkbox da interface, que ativa ou desativa a edicao
					de alarmes.
	Entrada         - 
	Processamento   - Verifica se o usuario ativou ou desativou o checkbox.
	Saida           - 

	=================================================== */
	@FXML
	void checkboxEvent() {
		if(!checkAlarme.isSelected()) {
			ativarDesativarEditorAlarmes(true);
		} else {
			ativarDesativarEditorAlarmes(false);
		}
	}
	
	/* ===================================================

	Metodo          - carregaAtividade
	Descricao       - Metodo auxiliar para carregar as informacoes de uma atividade e apresentar
					na interface grafica
	Entrada         - Um tipo Perfil com as informacoes do usuario, e uma String com o titulo da
					lista de tarefas.
	Processamento   - 
	Saida           - 

	=================================================== */
	public void carregaAtividade(Perfil perfil, String todolist) throws ClassNotFoundException, NullPointerException, IOException {
		Pomodoro ativ = Leitor.lerAtividade(todolist, perfil.getNome());
		this.todolist = (ToDoList)ativ;
		campoTitulo.setText(ativ.getTitulo());
		campoDescricao.setText(ativ.getDescricao());
		campoAlarmeInicio.setText(ativ.getAlarmeInicio());
		campoAlarmeFinal.setText(ativ.getAlarmeFim());
		carregaListaTarefas(ativ);
	}
	
	/* ===================================================

	Metodo          - carregaListaTarefas
	Descricao       - Metodo auxiliar para carregar as tarefas no vetor de uma ToDoList e
					apresentar na lista de visualizacao da interface.
	Entrada         - Um tipo pomodoro com a ToDoList.
	Processamento   - 
	Saida           - 

	=================================================== */
	public void carregaListaTarefas(Pomodoro ativ) {
		for(int i = 0; i < this.todolist.getIdAtual(); i++) {
			listaTarefas.getItems().add(this.todolist.getTarefas()[i].getTitulo());
		}
	}
	
	/* ===================================================

	Metodo          - carregarComboBoxNTarefas
	Descricao       - Metodo auxiliar para configurar os itens da interface. Alimenta a lista
					de selecao da quantidade de tarefas na lista. Apesar de ser um numero
					predefinido de opcoes, o usuario pode inserir um valor diferente.
	Entrada         - 
	Processamento   - Gera um vetor com um numero predefinido de inteiros e os insere na lista
	 				de selecao da interface.
	Saida           - 

	=================================================== */
	public void carregarComboBoxNTarefas() {
		Integer[] numeroTarefas = new Integer[100];
		for(int i = 0; i < 100; i++) {
			numeroTarefas[i] = i;
		}
		campoNumeroTarefas.getItems().setAll(numeroTarefas);
		campoNumeroTarefas.getSelectionModel().select(0);
	}
	
	/* ===================================================

	Metodo          - carregarComboBoxTempo
	Descricao       - Metodo auxiliar para configurar os itens da interface. Alimenta as listas
					de selecao dos tempos de duracao das tarefas.
	Entrada         - 
	Processamento   - Gera vetores com inteiros para as horas, minutos e segundos, e passa para
					as listas de selecao (ComboBox) da interface.
	Saida           - 

	=================================================== */
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
	
	/* ===================================================

	Metodo          - selecionaComboBox
	Descricao       - Metodo auxiliar a inicializacao de elementos da interface
	Entrada         - dhr: Um Integer com o numero de horas de duracao.
					  dMin: Um Integer com o numero de minutos de duracao.
					  dSec: Um Integer com o numero de segundos de duracao.
					  pMin: Um Integer com o numero de minutos de pausa.
					  pSec: Um Integer com o numero de segundos de pausa.
	Processamento   - Seleciona os itens especificados nas listas da interface
					(ComboBox).
	Saida           - 

	=================================================== */
	public void selecionaComboBox(Integer dHr, Integer dMin, Integer dSec,
			 					  Integer pMin, Integer pSec) {
		campoDuracaoHora.getSelectionModel().select(dHr);
		campoDuracaoMinuto.getSelectionModel().select(dMin);
		campoPausaMinuto.getSelectionModel().select(pMin);
		campoDuracaoSegundo.getSelectionModel().select(dSec);
		campoPausaSegundo.getSelectionModel().select(pSec);
	}
	
}
