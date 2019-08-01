package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lista_pomodoros.LinkedNode;
import lista_pomodoros.ListaExecucao;
import lista_pomodoros.ListaPreparacao;
import perfil.Perfil;
import pomodoro.Atividade;
import pomodoro.Pomodoro;
import utilidades.Utilidades;
import java.io.IOException;

public class controladorJanelaAtividades {
	Perfil perfil;
	ListaPreparacao listaPreparacao = new ListaPreparacao();
	
	@FXML
	public ListView<String> listaAtividadesUsuario;
	@FXML
	public ListView<String> listaAtividadesExecucao;
	@FXML
	public TextArea descricaoAtividade;
	@FXML
	public Label duracaoAtividade;
	@FXML
	public Label pausaAtividade;
	@FXML
	public Button botaoAdicionar;
	@FXML
	public Button botaoAcionarTimer;
	@FXML
	public Button botaoDown;
	@FXML
	public Button botaoUp;
	@FXML
	public AnchorPane pane;

	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado. Inicializa a janela quando chamada a partir
					da janela de login
	Entrada         - Um tipo Perfil, apos a escolha do usuario no login
	Processamento   - Faz o vinculo do controlador com o perfil, atribuindo ao atributo "perfil"
					o objeto selecionado pelo usuario. Carrega as atividades do usuario, apresentando-as
					no objeto "ListView" listaAtividadesUsuario. Configurao ListView para que permita
					a selecao apenas de um item de cada vez.
	Saida           - 

	=================================================== */	
	public void initialize(Perfil Perfil) {
		this.perfil = Perfil;
		carregaAtividadesUsuario();
        listaAtividadesUsuario.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
	}
	
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado. Inicializa a janela quando chamada a partir da janela
					de execucao do timer
	Entrada         - Um tipo Perfil, com o perfil do usuario, e um tipo ListaExecucao, contendo as atividades
					que ainda nao foram executadas, para que o usuario possa dar continuidade ou modificar
					a lista de atividades para executar
	Processamento   - Chamada o metodo de inicializacao anterior, para as configurar basicas da janela, e
					carrega a lista de preparacao, tal como o ListView para visualizacao, com as atividades
					que sobraram da execucao.
	Saida           - 

	=================================================== */	
	public void initialize(Perfil Perfil, ListaExecucao lista) {
		initialize(Perfil);
		LinkedNode atual = lista.getUltimo();
		while(atual != null) {
			listaPreparacao.insereInicio(atual.getData());
			listaAtividadesExecucao.getItems().add(0, atual.getData().getTitulo());
			atual = atual.getAnterior();
		}
	}
	
	/* ===================================================

	Metodo          - acionarTimer
	Descricao       - Metodo atrelado ao botao "botaoAcionarTimer". Carrega a janela do timer,
					seu controlador, passando o perfil de usuario e a lista de preparacao, e fecha
					a janela atual.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	public void acionarTimer() throws IOException {
		// Carrega o arquivo com as informacoes do layout da interface
		FXMLLoader loader = new FXMLLoader(getClass().getResource("janelaTimer.fxml"));
		// Prepar a cena a ser apresentada
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene((Pane)loader.load()));
		// Instancia um controlador da janela do timer, e executa o seu inicializador com os parametros
		// adequados
		controladorJanelaTimer controller = loader.<controladorJanelaTimer>getController();
		controller.initialize(perfil, listaPreparacao);
		// Apresenta a janela
		stage.show();
		// Fecha a janela atual
		((Stage)pane.getScene().getWindow()).close();
		
	}
	
	/* ===================================================

	Metodo          - carregarJanelaCriacaoAtividade
	Descricao       - Metodo atrelado ao botao "criar atividade" do menu. Carrega a janela de edicao/criacao de atividades,
					configurando-a para criacao.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	public void carregarJanelaCriacaoAtividade() throws Exception {
		// Carrega o arquivo com as informacoes do layout da interface
		FXMLLoader loader = new FXMLLoader (getClass().getResource("editor_atividade.fxml"));
		// Prepar a cena a ser apresentada
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene( (Pane) loader.load()));
		// Configura a tela de modo que a tela anterior (de atividades) fique inacessivel enquanto a
		// criacao/edicao nao terminar
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pane.getScene().getWindow());

		// Instancia um controlador da janela do timer, e executa o seu inicializador com os parametros
		// adequados
		controladorEditorAtividades controller = loader.<controladorEditorAtividades>getController();
		controller.initialize(perfil);
		// Apresenta a janela, e coloca o metodo em espera
		stage.showAndWait();
		// Ao fechar a janela de edicao, eh chamado o metodo de carregar as atividades do usuario,
		// para que sejam carregas as atividades modificadas ou criadas.
		carregaAtividadesUsuario();
	}
	
	/* ===================================================

	Metodo          - carregarJanelaCriacaoToDoList
	Descricao       - Metodo atrelado ao botao "Criar Todo List" do menu. Carrega a janela de edicao/criacao de todo lists,
					configurando-a para criacao.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	public void carregarJanelaCriacaoToDoList() throws Exception {
		// Carrega o arquivo com as informacoes do layout da interface
		FXMLLoader loader = new FXMLLoader (getClass().getResource("janelaEdicaoToDoList.fxml"));
		// Prepar a cena a ser apresentada
		Stage stage = new Stage(StageStyle.DECORATED);
		stage.setScene(new Scene( (Pane) loader.load()));
		// Configura a tela de modo que a tela anterior (de atividades) fique inacessivel enquanto a
		// criacao/edicao nao terminar
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(pane.getScene().getWindow());

		// Instancia um controlador da janela do timer, e executa o seu inicializador com os parametros
		// adequados
		controladorEditorToDoList controller = loader.<controladorEditorToDoList>getController();
		controller.initialize(perfil);
		// Apresenta a janela, e coloca o metodo em espera
		stage.showAndWait();
		// Ao fechar a janela de edicao, eh chamado o metodo de carregar as atividades do usuario,
		// para que sejam carregas as atividades modificadas ou criadas.
		carregaAtividadesUsuario();
	}
	
	/* ===================================================

	Metodo          - carregarJanelaEdicao
	Descricao       - Metodo atrelado ao botao "editar" do menu. Carrega a janela de edicao/criacao de atividades,
					configurando-a para edicao. Ao carregar a janela de edicao, eh carregado tambem os dados da atividade
					selecionada, para que o usuario possa edita-los. A fim de evitar que um o programa feche caso ocorra
					um problema de leitura do arquivo da atividade, eh usado um try-catch de IOException. Tambem eh usado
					um catch de NullPointerException para o caso em que o usuario aperta o botao "editar" sem ter selecionado
					nada na lista.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	public void carregarJanelaEdicao(ActionEvent event) {
		try {
			// Pega o titulo da atividade selecionada no ListView
			String itemSelecionado = listaAtividadesUsuario.getSelectionModel().getSelectedItem();
			if(!itemSelecionado.isEmpty()) { // isso provavelmente eh redundante, devido ao try-catch para NullException
				if(perfil.getLista().buscaItem(itemSelecionado).getData() instanceof Atividade) {
					// Carrega o layout da janela e prepara a cena, configurando a janela de modo que a janela
					// anterior fique inacessivel ate terminar a edicao
					FXMLLoader loader = new FXMLLoader (getClass().getResource("editor_atividade.fxml"));
					Stage stage = new Stage(StageStyle.DECORATED);
					stage.setScene(new Scene ( (Pane)loader.load() ));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(pane.getScene().getWindow());
					
					// Carrega o controlador da janela de edicao, inicializando-a com as informacoes da atividade
					// que sera editada.
					controladorEditorAtividades controller = loader.<controladorEditorAtividades>getController();
					controller.initialize(perfil, itemSelecionado);
					// Apresenta a janela e coloca o presente metodo em espera
					stage.showAndWait();
					// Ao retornar, atualiza a janela de atividades com as edicoes.
					carregaAtividadesUsuario();
				} else {
					FXMLLoader loader = new FXMLLoader (getClass().getResource("janelaEdicaoToDoList.fxml"));
					Stage stage = new Stage(StageStyle.DECORATED);
					stage.setScene(new Scene ( (Pane)loader.load() ));
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(pane.getScene().getWindow());
					
					controladorEditorToDoList controller = loader.<controladorEditorToDoList>getController();
					controller.initialize(perfil, itemSelecionado);
					stage.showAndWait();
					carregaAtividadesUsuario();
				}
			}
		} catch(NullPointerException | IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - deletaAtividade
	Descricao       - Metodo vinculado ao botao "deletar" no menu.
	Entrada         - 
	Processamento   - Recupera o item selecionado na lista de atividades, chamando os metodos de remocao
					do objeto ListaAtividades, vinculado ao perfil. As exceptions sao tratadas nos metodos
					internos, entao nao ha a necessidade de try-catch aqui. Tambem eh feita a remocao
					no ListView, da interface grafica.
	Saida           - 

	=================================================== */	
	public void deletaAtividade() {
		perfil.getLista().remover(listaAtividadesUsuario.getSelectionModel().getSelectedItem());
		listaAtividadesUsuario.getItems().remove(listaAtividadesUsuario.getSelectionModel().getSelectedItem());
	}
	
	
	/* ===================================================

	Metodo          - deslogar
	Descricao       - Metodo vinculado ao botao "deslogar" no menu. Retorna para a janela de login
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	public void deslogar() {
		try {
			// Carrega as informacoes de layout e carrega a janela de perfil, fechando a atual
			FXMLLoader loader = new FXMLLoader (getClass().getResource("login_perfil.fxml"));
			Stage stage = new Stage(StageStyle.DECORATED);
			stage.setScene(new Scene((Pane)loader.load())); // lanca um IOException, caso haja
															// problema na leitura dos dados de layout
			stage.show();
			((Stage)pane.getScene().getWindow()).close();
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - listaUsuarioMouseHandler
	Descricao       - Metodo que especifica o comportamento do ListView para a lista de atividades
					com a interacao do usuario atraves do mouse.
	Entrada         - 
	Processamento   - Caso o usuario de dois cliques em algum item da lista, ele eh adicionado
					na lista de preparacao, tanto no objeto ListaPreparacao quanto no visualizador
					(ListView) listaAtividadesExecucao. Caso seja apenas um clique (selecao), as
					informacoes da atividade sao apresentadas na interface.
	Saida           - 

	=================================================== */
	@FXML
	public void listaUsuarioMouseHandler() {
		listaAtividadesUsuario.setOnMouseClicked(new EventHandler<MouseEvent>() { // define um novo metodo para o caso em que 
																				  // o usuario clica no objeto
			@Override
			public void handle(MouseEvent click) { // recebe o objeto do evento do clique
				if(click.getClickCount() == 2) { // verifica se houve dois cliques
					// Recupera o titulo da atividade selecionada
					String itemSelecionado = listaAtividadesUsuario.getSelectionModel().getSelectedItem();
					// Pega o item correspondente na lista de atividades do usuario e adiciona na lista de preparacao.
					listaPreparacao.insereInicio(perfil.getLista().buscaItem(itemSelecionado).getData());
					// Adiciona o titulo da atividade no ListView da lista de preparacao.
					listaAtividadesExecucao.getItems().add(0, itemSelecionado);
				} else {
					// Se apenas um clique foi dado (selecao), apresenta os dados da atividade na interface grafica.
					apresentarInfo();
				}
			}
		});
	}
	
	/* ===================================================

	Metodo          - listaPreparacaoMouseHandler
	Descricao       - Metodo que especifica o comportamento do ListView para a lista de preparacao
					de atividades com a interacao do usuario atraves do mouse
	Entrada         - 
	Processamento   - Caso o usuario de dois cliques em algum item do ListView da lista de preparacao,
					a atividade eh removida da lista e da interface grafica.
	Saida           - 

	=================================================== */
	@FXML
	public void listaPreparacaoMouseHandler() {
		listaAtividadesExecucao.setOnMouseClicked(new EventHandler<MouseEvent>() { // define um novo metodo para o caso em que
																				   // o usuario clica no objeto
			@Override
			public void handle(MouseEvent click) { // recebe o objeto do evento do clique
				if(click.getClickCount() == 2) {   // verifica se houve dois cliques
					// Recebe a String com o titulo da atividade selecionada, e o seu indice no ListView
					String itemSelecionado = listaAtividadesExecucao.getSelectionModel().getSelectedItem();
					int indexSelecionado = listaAtividadesExecucao.getSelectionModel().getSelectedIndex();
					// remove a atividade do objeto listaPreparacao
					listaPreparacao.remover(itemSelecionado);
					// remove o titulo da atividade do ListView
					listaAtividadesExecucao.getItems().remove(indexSelecionado);
				}
			}
		});
	}
	
	/* ===================================================

	Metodo          - moverAcima
	Descricao       - Metodo vinculado ao botao "botaoUp". Se possivel, move o item selecionado uma posicao acima
					na lista de preparacao.
	Entrada         - 
	Processamento   - Eh feita a chamada do metodo "moverAcima" no objeto listaPreparacao. Caso a operacao tenha sido
					realizada com sucesso (o elemento nao eh o primeiro da lista), retorna true, fazendo a mesma operacao
					na ListView da interface grafica.
	Saida           - 

	=================================================== */		
	public void moverAcima() {
		// Recupera o titulo da atividade selecionada
		String atividadeSelecionada = listaAtividadesExecucao.getSelectionModel().getSelectedItem();
		try {
			if(listaPreparacao.moverAcima(atividadeSelecionada)) { // tenta mover a atividade na lista de preparacao
				// recupera o indice da atividade selecionada
				int indiceSelecionado = listaAtividadesExecucao.getSelectionModel().getSelectedIndex();
				// recupera o titulo da atividade anterior a selecionada
				String atividadeAnterior = listaAtividadesExecucao.getItems().get(indiceSelecionado - 1);
				// faz a troca no ListView
				listaAtividadesExecucao.getItems().set(indiceSelecionado, atividadeAnterior);
				listaAtividadesExecucao.getItems().set(indiceSelecionado - 1, atividadeSelecionada);
				listaAtividadesExecucao.getSelectionModel().select(indiceSelecionado - 1); 
			}
		} catch(NullPointerException e) {
			// Caso esse Exception ocorra, eh porque o usuario tentou mover um elemento sem ter selecionado
			// nada na lista.
		}
	}
	
	/* ===================================================

	Metodo          - moverAbaixo
	Descricao       - Metodo vinculado ao botao "botaoDown". Se possivel, move o item selecionado uma posicao abaixo
					na lista de preparacao.
	Entrada         - 
	Processamento   - Eh feita a chamada do metodo "moverAbaixo" no objeto listaPreparacao. Caso a operacao tenha sido
					realizada com sucesso (o elemento nao eh o primeiro da lista), retorna true, fazendo a mesma operacao
					na ListView da interface grafica.
	Saida           - 

	=================================================== */			
	public void moverAbaixo() {
		// Recupera o titulo da atividade selecionada
		String atividadeSelecionada = listaAtividadesExecucao.getSelectionModel().getSelectedItem();
		try {
			if(listaPreparacao.moverAbaixo(atividadeSelecionada)) { // tenta mover a atividade na lista de preparacao
				// recupera o indice da atividade selecionada
				int indiceSelecionado = listaAtividadesExecucao.getSelectionModel().getSelectedIndex();
				// recupera o titulo da atividade anterior a selecionada
				String atividadePosterior = listaAtividadesExecucao.getItems().get(indiceSelecionado + 1);
				// faz a troca no ListView
				listaAtividadesExecucao.getItems().set(indiceSelecionado, atividadePosterior);
				listaAtividadesExecucao.getItems().set(indiceSelecionado + 1, atividadeSelecionada);
				listaAtividadesExecucao.getSelectionModel().select(indiceSelecionado + 1);
			}
		} catch(NullPointerException e) {
			// Caso esse Exception ocorra, eh porque o usuario tentou mover um elemento sem ter selecionado
			// nada na lista.
		}
	}
	
	/* ===================================================

	Metodo          - carregaAtividadesUsuario
	Descricao       - Carrega a lista de atividades do usuario e as apresenta no ListView adequado
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	public void carregaAtividadesUsuario() {
		perfil.carregaLista();
		listaAtividadesUsuario.getItems().clear();
		try {
			// Usa o metodo "toString" do objeto ListaAtividades, que retorna os titulos das atividades
			// em uma unica string, separados por virgula, e faz a separacao destes titulos em um vetor
			// de strings
			String[] atividades_nomes = perfil.getLista().toString().split(",");
			// Atribui os itens do vetor ao ListView
	        listaAtividadesUsuario.getItems().setAll(atividades_nomes);
		} catch (NullPointerException e) {
			// Caso caia neste Exception, o perfil nao tem nenhuma atividade, entao o vetor "atividades_nomes"
			// sera null.
		}
	}
	
	
	/* ===================================================

	Metodo          - apresentarInfo
	Descricao       - Apresenta as informacoes da atividade selecionada no ListView
	Entrada         - 
	Processamento   - Apenas resgata as informacoes da atividade e as direciona para os elementos adequados
					da interface grafica.
	Saida           - 

	=================================================== */	
	@FXML
	public void apresentarInfo() {
		String ativ_titulo = listaAtividadesUsuario.getSelectionModel().getSelectedItem();
		try {
			// Recupera a atividade
			Pomodoro ativ_selecionada = perfil.getLista().buscaItem(ativ_titulo).getData();
			// Apresenta a descricao no TextArea
			descricaoAtividade.setText(ativ_selecionada.toString());
			// Obtem uma string com o tempo de execucao no formato 00:00:00
			String stringTempo =  Utilidades.tempoToString(ativ_selecionada.duracaoParaHMS());
			// Apresenta o tempo de duracao no Label "duracaoAtividade"
			duracaoAtividade.setText("Duracao: "+stringTempo);
			// Obtem uma string com o tempo de pausa no formato 00:00:00
			stringTempo =  Utilidades.tempoToString(ativ_selecionada.pausaParaHMS());
			// Apresenta o tempo de pausa no Label "pausaAtividade"
			pausaAtividade.setText("Pausa: "+stringTempo);
		} catch(NullPointerException e) {
			// O metodo eh chamado quando se clica uma vez no ListView da lista de atividades.
			// Se o ListView esta vazio, entao ao clicar o metodo sera chamado, mas ao tentar
			// recuperar a atividade, retornara um ponteiro null.
		}
	}
}
