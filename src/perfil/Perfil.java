package perfil;

import java.io.Serializable;
import interfaces.GerenciaBackupPerfil;
import lista_pomodoros.ListaAtividades;
import gerenciador_arquivos.*;

public class Perfil implements Serializable, GerenciaBackupPerfil {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5783963653260107707L;
	private String nome;
	private ListaAtividades lista;
	
	/* ===================================================

	Metodo          - Perfil
	Descricao       - Construtor da classe.
	Entrada         - Uma string com o nome do perfil
	Processamento   - Faz a atribuicao do nome e a instanciacao da lista de atividades,
					vinculada ao perfil
	Saida           - 

	=================================================== */
	public Perfil (String nome) {
		this.nome = nome;
		carregaLista();
	}
	
	/* ===================================================

	Metodo          - carregaLista
	Descricao       - Instancia uma lista de atividades vinculada ao perfil
	Entrada         - 
	Processamento   - Chamada o construtor da classe ListaAtividades, e faz o vinculo
					da instancia com o atributo do Perfil
	Saida           - 

	=================================================== */
	public void carregaLista() {
		this.lista = new ListaAtividades(this.nome);
	}
	
	/* ===================================================

	Metodo          - getNome
	Descricao       - Obtem o nome atribuido ao perfil
	Entrada         - 
	Processamento   - 
	Saida           - Uma string com o conteudo do atributo "nome"

	=================================================== */
	public String getNome() {
		return this.nome;
	}
	
	/* ===================================================

	Metodo          - getLista
	Descricao       - Obtem a lista de atividades vinculada ao perfil.
	Entrada         - 
	Processamento   - 
	Saida           - Um tipo ListaAtividades com o conteudo do atributo "lista".

	=================================================== */
	public ListaAtividades getLista() {
		return this.lista;
	}
	
	/* ===================================================

	Metodo          - salvar
	Descricao       - Implementacao do metodo da interface GerenciaBackup. Salva
					as informacoes do perfil em um arquivo.
	Entrada         - Um tipo Perfil a ser salvo.
	Processamento   - Faz a chamada do metodo de backup da classe Escritor
	Saida           - 

	=================================================== */
	public void salvar() {
		Escritor.escreverPerfil(this);
	}
	
	/* ===================================================

	Metodo          - remover
	Descricao       - Implementacao do metodo da interface GerenciaBackup. Remove
					todas as informacoes do perfil.
	Entrada         - 
	Processamento   - Faz a chamada de remocao de arquivos da classe GerenciadorPrincipal
	Saida           - 

	=================================================== */
	public void remover() {
		GerenciadorPrincipal.removerArquivo(getNome());
	}
}
