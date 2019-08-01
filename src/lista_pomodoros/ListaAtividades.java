package lista_pomodoros;

import interfaces.*;
import pomodoro.*;

import java.io.IOException;
import java.io.Serializable;
import gerenciador_arquivos.*;

public class ListaAtividades extends ListaDePomodoros implements GerenciaBackupAtividade, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4518821755504133763L;
	private String perfil;
	
	
	/* ===================================================

	Metodo          - ListaAtividades
	Descricao       - Construtor da classe
	Entrada         - Uma string com o nome do perfil ao qual a instancia sera associada.
	Processamento   - Faz o vinculo da lista com o perfil especificado. Faz uma busca pelos arquivos
					de atividades ja criadas para o perfil, e preenche a lista com as informacoes des-
					sas atividades. Mantem a lista vazia caso o vetor de strings com as atividades
					seja null (vazio).
	Saida           -

	 =================================================== */	
	public ListaAtividades(String perfil) {
		setPerfilVinculado(perfil);
		String[] lista_atividades = Leitor.getListaAtividades(perfil);
		try {
			for(String titulo_atividade : lista_atividades) {
				try {
					insereFinal(Leitor.lerAtividade(titulo_atividade, perfil));
				} catch(NullPointerException | ClassNotFoundException | IOException e) {
					System.out.println("Problema na leitura da atividadE: "+titulo_atividade);
					System.out.println(e.getMessage());
				}
			}
		} catch(NullPointerException e) {
			// Se caiu aqui, o usuario nao possui atividades, entao nao ha nada a se fazer.
		}
	}
	
	/* ===================================================

	Metodo          - setPerfilVinculado
	Descricao       - Faz a atribuicao do perfil para a lista
	Entrada         - Uma String com o nome do perfil
	Processamento   - Atribui ao atributo "perfil" o nome passado como parametro
	Saida           -

	 =================================================== */
	public void setPerfilVinculado(String perfil) {
		this.perfil = perfil;
	}
	
	/* ===================================================

	Metodo          - getPerfilVinculado
	Descricao       - Obtem o nome do perfil vinculado a lista
	Entrada         - 
	Processamento   - 
	Saida           - Retorna a String com o nome do perfil

	 =================================================== */
	public String getPerfilVinculado() {
		return this.perfil;
	}
	
	/* ===================================================

	Metodo          - remover
	Descricao       - Metodo Sobrecarregado. Remove da lista uma atividade especificada
	Entrada         - O titulo da atividade
	Processamento   - Faz a busca pelo item com o titulo especificado. Remove o nodo caso o encontre.
	Saida           -

	 =================================================== */
	@Override
	public boolean remover(String titulo) {
		LinkedNode nodo = buscaItem(titulo);
		if(nodo != null) {
			LinkedNode anterior = nodo.getAnterior();
			LinkedNode proximo = nodo.getProximo();
			if(anterior == null) {
				remover(getPrimeiro().getData(), perfil);
				removerPrimeiro();
				return true;
			}
			if(proximo == null) {
				remover(getUltimo().getData(), perfil);
				removerUltimo();
				return true;
			}
			anterior.setProximo(proximo);
			proximo.setAnterior(anterior);
			remover(nodo.getData(), perfil);
			return true;
		}
		return false;
	}
	
	
	/* ===================================================

	Metodo          - salvar
	Descricao       - Implementacao do metodo da interface para salvar o backup de uma atividade.
	Entrada         - Um tipo pomodoro (a atividade) e uma String com o perfil dono da atividade.
	Processamento   - Faz a chamada do metodo da classe Escritor para salvar a instancia do Pomodoro
					na pasta correta do perfil.
	Saida           -

	 =================================================== */
	public void salvar(Pomodoro item, String perfil) {
		Escritor.escreverAtividade(item, perfil);
	}
	
	/* ===================================================

	Metodo          - remover
	Descricao       - Metodo sobrecarregado. Implementacao do metodo da interface para remover o arquivo
					de uma atividade.
	Entrada         - Um tipo Pomodoro (a atividade) e uma String com o titulo do perfil associado.
	Processamento   - Faz a chamada do metodo para remocao de arquivos.
	Saida           -

	 =================================================== */
	public void remover(Pomodoro item, String perfil) {
		GerenciadorPrincipal.removerArquivo(item.getTitulo(), perfil);
		System.out.println("Testando remocao do arquivo. Funcao da interface.");
	}
}
