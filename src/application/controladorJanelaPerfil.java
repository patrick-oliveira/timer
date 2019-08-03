package application;

import perfil.Perfil;
import java.io.IOException;
import gerenciador_arquivos.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class controladorJanelaPerfil {
	@FXML
	private Button botaoCriar;
	@FXML
	private Button botaoDeletar;
	@FXML
	private Button botaoEntrar;
	@FXML
	private ComboBox<String> listaPerfil;
	@FXML
	private AnchorPane painel;
	
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo de inicializacao da interface.
	Entrada         - 
	Processamento   - Alimenta a estrutura "listaPerfil" da interface de usuario com os nomes dos perfis ja cadastrados.
	Saida           - 

	=================================================== */	
	public void initialize() {
		try {
			listaPerfil.getItems().addAll(Leitor.getListaPerfis());
		} catch (NullPointerException e) {
			//
		}
	}
	
	/* ===================================================

	Metodo          - entrar
	Descricao       - Metodo vinculado ao objeto "botaoEntrar", executado ao se apertar o botao.
	Entrada         - 
	Processamento   - Faz a chamada da janela de interacao com as atividades, passando como argumento para o gerenciador
					da janela, "controladorJanelaAtividades", o perfil selecionado no login.
	Saida           - 

	=================================================== */		
	public void entrar() {
		try {
			Perfil perfil = Leitor.lerPerfil(listaPerfil.getEditor().getText());
			// Carrega e prepara o arquivo com as informacoes do layout da janela.
			FXMLLoader loader = new FXMLLoader (getClass().getResource("janela_atividades.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene( (Pane) loader.load()));
			
			// Instancia o controlador da janela carregada anteriormente, executa a sua funcao de inicializacao,
			// passando o perfil selecionado como parametro.
			controladorJanelaAtividades controller = loader.<controladorJanelaAtividades>getController();
			controller.initialize(perfil);
			
			// Apresenta a janela carregada.
			stage.show();
			
			// Fecha a janela atual.
			((Stage)painel.getScene().getWindow()).close();
		} catch(NullPointerException | ClassNotFoundException | IOException e ) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - botaoCriar
	Descricao       - Metodo vinculado ao objeto "botaoCriar", executado ao se apertar o botao.
	Entrada         - 
	Processamento   - Recupera a string presente na estrutura "listaPerfil", da interface, no momento
					em que se aperta o botao. Verifica se a string eh vazia, e se nao eh, se o nome
					corresponde a um perfil existente. Caso nao, cria um novo perfil.
	Saida           - 

	=================================================== */		
	public void botaoCriar() {
		String text = listaPerfil.getEditor().getText();
        boolean EmptyComboBox = text.isEmpty() || text.trim().isEmpty();
		if(!EmptyComboBox) {
			if(Leitor.buscarPerfil(text) == null) {
        		Escritor.escreverPerfil(new Perfil(text));
        		listaPerfil.getItems().add(text);
			}
		}
		
	}
	
	/* ===================================================

	Metodo          - botaoDeletar
	Descricao       - Metodo vinculado ao objeto "botaoDeletar", executado ao se apertar o botao.
	Entrada         - 
	Processamento   - Recupera a string presente na estrutura "listaPerfil", da interface, no momento
					em que se aperta o botao. Verifica se a string eh vazia, e se nao eh, faz a chamada
					do metodo de remocao de arquivos (nele eh feito a verificacao da possibilidade de 
					se remover o arquivo).
	Saida           - 

	=================================================== */		
	public void botaoDeletar() {
		String text = listaPerfil.getEditor().getText();
        boolean EmptyComboBox = text.isEmpty() || text.trim().isEmpty();
		if(!EmptyComboBox) {
			listaPerfil.getItems().remove(text);
        	GerenciadorPrincipal.removerArquivo(text);
        	listaPerfil.getEditor().setText("");
		}
	}
}
