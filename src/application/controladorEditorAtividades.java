package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import perfil.Perfil;
import pomodoro.*;
import utilidades.Utilidades;

import java.io.File;
import java.io.IOException;

import gerenciador_arquivos.*;

public class controladorEditorAtividades {
	private Perfil perfil_associado;
	private String tituloAtividade;
	
	@FXML
	private AnchorPane painel;
	@FXML
	private TextField campoTitulo;
	@FXML
	private TextArea campoDescricao;
	@FXML
	private ComboBox<Integer> campoDuracaoHora;
	@FXML
	private ComboBox<Integer> campoDuracaoMin;
	@FXML
	private ComboBox<Integer> campoDuracaoSec;
	@FXML
	private ComboBox<Integer> campoPausaMin;
	@FXML
	private ComboBox<Integer> campoPausaSec;
	@FXML
	private TextField campoAlarmeInicio;
	@FXML
	private TextField campoAlarmeFim;
	@FXML
	private Label labelAlarmeInicio;
	@FXML
	private Label labelAlarmeFim;
	@FXML
	private Button botaoOK;
	@FXML
	private Button botaoCancelarCriar;
	@FXML
	private Button botaoCancelarEditar;
	@FXML
	private Button botaoBuscarAlarmeInicio;
	@FXML
	private Button botaoBuscarAlarmeFim;
	@FXML
	private CheckBox edicaoAlarmeCheckbox;

	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado. Inicializa a configuracao basica da janela,
					quando chamada para criacao de uma nova atividade.
	Entrada         - Um tipo Perfil com as informacoes do usuario
	Processamento   - Carrega as listas de numeros para a escolha dos tempos,
					Ativa o botao de cancelar associado ao processo de criacao,
					Inicializa os elementos para escolha dos alarmes como desativados.
	Saida           - 

	=================================================== */	
	public void initialize(Perfil perfil) {
		this.perfil_associado = perfil;
		carregaComboBox();
		botaoCancelarCriar.setDisable(false);
		ativarDesativarEditorAlarmes(true);
	}
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado. Inicializa a configuracao basica da janela,
					quando chamada para edicao de uma atividade escolhida.
	Entrada         - Um tipo Perfil com as informacoes do usuario. Uma String com o nome
					da atividade que sera editada.
	Processamento   - Faz um try-catch para carregar a atividade. Caso nao seja possivel carrega-la
					a janela eh fechada e a anterior eh aberta.
	Saida           - 

	=================================================== */	
	public void initialize(Perfil perfil, String atividade) throws IOException {
		this.perfil_associado = perfil;
		try {
			carregaComboBox();
			this.tituloAtividade = atividade;
			botaoCancelarEditar.setDisable(false);
			ativarDesativarEditorAlarmes(true);
			carregaAtividade(perfil, atividade); // Lanca as exceptions.
		} catch (ClassNotFoundException | NullPointerException | IOException e) {
			System.out.println(e.getMessage());
			
			FXMLLoader loader = new FXMLLoader (getClass().getResource("janela_atividades.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene( (Pane) loader.load())); // throws IOException
			controladorJanelaAtividades controller = loader.<controladorJanelaAtividades>getController();
			controller.initialize(perfil);
			stage.show();
			
			((Stage)painel.getScene().getWindow()).close();
		} 
	}
	
	/* ===================================================

	Metodo          - botaoBuscarAlarmeInicio
	Descricao       - Metodo associado ao botao "buscarAlarmeInicio".
	Entrada         - 
	Processamento   - Abre um "FileChooser" para que o usuario selecione o arquivo
					de audio para o seu alarme de inicio.
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
	Descricao       - Metodo associado ao botao "buscarAlarmeFim"
	Entrada         - 
	Processamento   - Abre um "FileChooser" para que o usuario selecione o arquivo
					de audio para o seu alarme de termino.
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoBuscarAlarmeFim() {
		FileChooser buscador = new FileChooser();
		File arquivo = buscador.showOpenDialog(null);
		
		try {
			campoAlarmeFim.setText(arquivo.getAbsolutePath());
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - botaoCancelarEditar
	Descricao       - Metodo vinculado ao botar "Cancelar" no contexto de edicao.
	Entrada         - 
	Processamento   - Recarrega os elementos da interface com as informacoes da atividade 
					antes de edita-la.
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoCancelarEditar() throws ClassNotFoundException, NullPointerException, IOException {
		carregaAtividade(perfil_associado, tituloAtividade);
	}
	
	/* ===================================================

	Metodo          - botaoCancelarCriar
	Descricao       - Metodo vinculado ao botao "Cancelar" no contexto de criacao.
	Entrada         - 
	Processamento   - Esvazia os elementos da interface para o usuario iniciar
					a criacao de uma nova atividade.
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoCancelarCriar() {
		campoTitulo.setText("");
		campoDescricao.setText("");
		selecionaComboBox(0, 25, 0, 5, 0);
		campoAlarmeInicio.setText("");
		campoAlarmeFim.setText("");
		edicaoAlarmeCheckbox.setSelected(false);
	}
	
	/* ===================================================

	Metodo          - apertarOK
	Descricao       - Metodo atrelado ao botao "OK".
	Entrada         - 
	Processamento   - Recupera as informacoes dos elementos da interface, gera uma
					nova atividade com essas informacoes e a escreve em um arquivo
					na pasta de backup do perfil. No caso de edicao, o arquivo eh
					sobreescrito.
	Saida           - 

	=================================================== */	
	@FXML
	public void apertarOK() {
		Atividade nova_atividade;
		String titulo = campoTitulo.getText();
		if(!titulo.isEmpty()) { // Nao eh possivel criar uma atividade sem titulo;
			try {
				String descricao = campoDescricao.getText();
				String duracaoHora = campoDuracaoHora.getEditor().getText();
				String duracaoMin = campoDuracaoMin.getEditor().getText();
				String duracaoSec = campoDuracaoSec.getEditor().getText();
				String pausaMin = campoPausaMin.getEditor().getText();
				String pausaSec = campoPausaSec.getEditor().getText();
				System.out.println("Pegou os valores dos campos corretamente.");
				
				int tempoExecucao = Utilidades.hmsParaSec(Integer.parseInt(duracaoHora), Integer.parseInt(duracaoMin), Integer.parseInt(duracaoSec));
				int tempoPausa = Utilidades.hmsParaSec(Integer.parseInt(pausaMin), Integer.parseInt(pausaSec));
				
				if(!edicaoAlarmeCheckbox.isSelected()) {
					nova_atividade = new Atividade(titulo, descricao, tempoExecucao, tempoPausa);
				} else {
					String alarmeInicio = campoAlarmeInicio.getText();
					String alarmeFim = campoAlarmeFim.getText();
					nova_atividade = new Atividade(titulo, descricao, tempoExecucao, tempoPausa, alarmeInicio, alarmeFim);
				}
	
				Escritor.escreverAtividade(nova_atividade, this.perfil_associado.getNome());
				tituloAtividade = nova_atividade.getTitulo();
				
				
				campoDescricao.setText("");
				campoTitulo.setText("");
				selecionaComboBox(0, 25, 0, 5, 0);
				campoAlarmeInicio.setText("");
				campoAlarmeFim.setText("");
				edicaoAlarmeCheckbox.setSelected(false);
				ativarDesativarEditorAlarmes(true);
			} catch(NumberFormatException e) {
				System.out.print("Valor incorreto de tempo inserido\n");
				System.out.print(e.getMessage());
				return;
			}
		}
	}
	
	/* ===================================================

	Metodo          - checkboxEvent
	Descricao       - Metodo atrelado ao objeto "Checkbox".
	Entrada         - 
	Processamento   - Desativa ou ativa os elementos da interface relacionados
					a customizacao do alarme conforme o usuario seleciona ou
					nao o checkbox.
	Saida           - 

	=================================================== */
	@FXML
	public void checkboxEvent() {
		if(!edicaoAlarmeCheckbox.isSelected()) {
			ativarDesativarEditorAlarmes(true);
		} else {
			ativarDesativarEditorAlarmes(false);
		}
	}
	
	/* ===================================================

	Metodo          - ativarDesativarEditorAlarmes 
	Descricao       - Metodo auxiliar para a interacao do usuario com o checkbox.
	Entrada         - 
	Processamento   - Modifica para "true" ou "false" o atributo "desativado" dos elementos
					da interface relativos a edicao de alarmes.
	Saida           - 

	=================================================== */	
	public void ativarDesativarEditorAlarmes(boolean valor) {
		labelAlarmeInicio.setDisable(valor);
		labelAlarmeFim.setDisable(valor);
		botaoBuscarAlarmeInicio.setDisable(valor);
		botaoBuscarAlarmeFim.setDisable(valor);
		campoAlarmeInicio.setDisable(valor);
		campoAlarmeFim.setDisable(valor);
	}
	
	/* ===================================================

	Metodo          - carregaAtividade
	Descricao       - Metodo auxiliar para a inicializacao da interface
	Entrada         - Um tipo Perfil com as informacoes do usuario e uma String
					com o titulo da atividade a ser carregada.
	Processamento   - Carrega os elementos da interface com as informacoes da
					atividade passada como argumento.
	Saida           - 

	=================================================== */	
	public void carregaAtividade(Perfil perfil, String atividade) throws ClassNotFoundException, NullPointerException, IOException {
		Pomodoro ativ = Leitor.lerAtividade(atividade, perfil.getNome());
		campoTitulo.setText(ativ.getTitulo());
		campoDescricao.setText(ativ.getDescricao());
		Integer[] dHMS = Utilidades.secParaHMS(ativ.getDuracao());
		Integer[] pHMS = Utilidades.secParaHMS(ativ.getPausa());
		selecionaComboBox(dHMS[0], dHMS[1], dHMS[2], pHMS[1], pHMS[2]);
		campoAlarmeInicio.setText(ativ.getAlarmeInicio());
		campoAlarmeFim.setText(ativ.getAlarmeFim());
	}
	
	/* ===================================================

	Metodo          - carregaComboBox
	Descricao       - Metodo auxiliar a inicializacao da interface
	Entrada         - 
	Processamento   - Alimenta o objeto "ComboBox" (uma lista de inteiros) com
					valores para os campos de hora (24), minutos (0-59) e segundos
					(0-59). No final, deixa selecionado um tempo padrao de um pomdoro,
					25 minutos de execucao e 5 minutos de pausa.
	Saida           - 

	=================================================== */	
	public void carregaComboBox() {
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
		campoDuracaoMin.getItems().setAll(min);
		campoPausaMin.getItems().setAll(min);
		campoDuracaoSec.getItems().setAll(sec);
		campoPausaSec.getItems().setAll(sec);
		
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
		campoDuracaoMin.getSelectionModel().select(dMin);
		campoPausaMin.getSelectionModel().select(pMin);
		campoDuracaoSec.getSelectionModel().select(dSec);
		campoPausaSec.getSelectionModel().select(pSec);
	}
}
