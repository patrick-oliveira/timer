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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import perfil.Perfil;
import pomodoro.*;
import utilidades.Utilidades;

import java.io.IOException;

import gerenciador_arquivos.*;

public class controladorEditorAtividades {
	private Perfil perfil_associado;
	private String atividade;
	
	@FXML
	private AnchorPane pane;
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
	Descricao       - Metodo sobrecarregado.
	Entrada         - 
	Processamento   - 
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
	Descricao       - Metodo sobrecarregado.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	public void initialize(Perfil perfil, String atividade) throws IOException {
		this.perfil_associado = perfil;
		try {
			this.atividade = atividade;
			carregaComboBox();
			carregaAtividade(perfil, atividade);
			botaoCancelarEditar.setDisable(false);
			ativarDesativarEditorAlarmes(true);
		} catch (ClassNotFoundException | NullPointerException | IOException e) {
			System.out.println("Problema na leitura da atividade: "+ atividade);
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
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoBuscarAlarmeInicio() {
		
	}
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoBuscarAlarmeFim() {
		
	}
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	@FXML
	public void botaoCancelarEditar() throws ClassNotFoundException, NullPointerException, IOException {
		carregaAtividade(perfil_associado, atividade);
	}
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado.
	Entrada         - 
	Processamento   - 
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

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	@FXML
	public void apertarOK() {
		Atividade nova_atividade;
		
		try {
			String titulo = campoTitulo.getText();
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
			atividade = nova_atividade.getTitulo();
		} catch(NumberFormatException e) {
			System.out.print("Valor incorreto de tempo inserido\n");
			System.out.print(e.getMessage());
			return;
		}
	}
	
	/* ===================================================

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
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

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
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

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
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

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
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

	Metodo          - 
	Descricao       - 
	Entrada         - 
	Processamento   - 
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
