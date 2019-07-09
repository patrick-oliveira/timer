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
	@FXML
	private ComboBox listaPerfil;
	
	public static GerenciadorPrincipal ger = new GerenciadorPrincipal();
	
	@FXML
	public void initialize() {
		//botaoCriar.setDisable(true);
		//botaoDeletar.setDisable(true);
		listaPerfil.getItems().addAll(ger.getPerfis());
	}
	
	@FXML
	public void botaoCriar() {
		System.out.println("Botao criar apertado!");
		String text = listaPerfil.getEditor().getText();
        boolean EmptyComboBox = text.isEmpty() || text.trim().isEmpty();
		if(!EmptyComboBox) {
        	ger.gerarArquivo(text);
        	listaPerfil.getItems().add(text);
		}
		
	}
	
	public void botaoDeletar() {
		System.out.println("Botao deletar apertado!");
		String text = listaPerfil.getEditor().getText();
        boolean EmptyComboBox = text.isEmpty() || text.trim().isEmpty();
		if(!EmptyComboBox) {
			System.out.println("Tentando deletar o perfil " + text);
			listaPerfil.getItems().remove(text);
        	ger.removerPerfil(text);
		}
	}
}
