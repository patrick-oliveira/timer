package application;

import atividade.Atividade;
import perfil.Perfil;
import gerenciador_arquivos.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller {
	@FXML
	private TextField campoNome;
	@FXML
	private Button botaoCriar;
	@FXML
	private Button botaoDeletar;
	
	public static GerenciadorPrincipal ger = new GerenciadorPrincipal();
	
	@FXML
	public static void initialize() {
		//botaoCriar.setDisable(true);
		//botaoDeletar.setDisable(true);
	}
	
	@FXML
	public void botaoCriarEvento() {
		System.out.println("Botao criar apertado!");
		ger.gerarArquivo(campoNome.getText());
		
	}
	
	public void botaoDeletarEvento() {
		System.out.println("Botao deletar apertado!");
		System.out.println("Printando o que está no textfield: " + campoNome.getText());
	}
}
