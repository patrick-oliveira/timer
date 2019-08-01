package lista_pomodoros;

import interfaces.listaRealocacao;

public class ListaPreparacao extends ListaDePomodoros implements listaRealocacao {
	
	/* ===================================================

	Metodo          - ListaPreparacao
	Descricao       - Construtor da classe
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public ListaPreparacao() {
		super();
	}
	
	/* ===================================================

	Metodo          - moverAcima
	Descricao       - Move em uma posicao acima um nodo especificado
	Entrada         - Uma string com o titulo da atividade/pomodoro
	Processamento   - Se possivel, move o nodo especificado uma posicao acima na lista ligada.
	Saida           -

	 =================================================== */
	public boolean moverAcima(String titulo) {
		LinkedNode nodo = buscaItem(titulo);
		if(!nodo.getData().comparaPomodoro(getPrimeiro().getData())) {
			LinkedNode anterior = nodo.getAnterior();
			LinkedNode proximo = nodo.getProximo();
				
			if(anterior == getPrimeiro()) { // o nodo atual eh o segundo da lista
				if(proximo == null) { // a lista tem apenas dois nodos
					nodo.setAnterior(null);
					nodo.setProximo(null);
					setPrimeiro(nodo);
					
					anterior.setAnterior(nodo);
					anterior.setProximo(null);
					setUltimo(anterior);
				} else {
					nodo.setAnterior(null);
					nodo.setProximo(anterior);
					setPrimeiro(nodo);
					
					anterior.setAnterior(nodo);
					anterior.setProximo(proximo);
					proximo.setAnterior(anterior);
				}
			} else { // o nodo atual esta na terceira posicao e diante
				if(proximo == null) { // o nodo atual eh o ultimo da lista
					nodo.setAnterior(anterior.getAnterior());
					nodo.setProximo(anterior);
					anterior.getAnterior().setProximo(nodo);
					
					anterior.setAnterior(nodo);
					anterior.setProximo(null);
					setUltimo(anterior);
				} else { // o nodo atual esta em algum lugar no meio da lista.
					nodo.setAnterior(anterior.getAnterior());
					nodo.setProximo(anterior);
					anterior.getAnterior().setProximo(nodo);
					
					anterior.setAnterior(nodo);
					anterior.setProximo(proximo);
					proximo.setAnterior(anterior);
				}
			}
				
			return true;
		}
		return false;
	}
	
	/* ===================================================

	Metodo          - moverAbaixo
	Descricao       - Move em uma posicao abaixo um nodo especificado
	Entrada         - Uma string com o titulo da atividade/pomodoro
	Processamento   - Se possivel, move uma posicao abaixo um nodo especificado.
	Saida           -

	 =================================================== */
	public boolean moverAbaixo(String titulo) {
		LinkedNode nodo = buscaItem(titulo);
		if(!nodo.getData().comparaPomodoro(getUltimo().getData())) {
			LinkedNode anterior = nodo.getAnterior();
			LinkedNode proximo = nodo.getProximo();
			
			if(proximo == getUltimo()) { // o nodo atual eh o penultimo nodo
				if(anterior == null) { // a lista so tem dois nodos
					nodo.setProximo(null);
					nodo.setAnterior(proximo);
					setUltimo(nodo);
					
					proximo.setAnterior(null);
					proximo.setProximo(nodo);
					setPrimeiro(proximo);
				} else {
					nodo.setProximo(null);
					nodo.setAnterior(proximo);
					setUltimo(nodo);
					
					proximo.setAnterior(anterior);
					proximo.setProximo(nodo);
					anterior.setProximo(proximo);
				}
			} else { // o nodo esta na antepenultima posicao para tras
				if(anterior == null) { // o nodo esta na primeira posicao
					nodo.setProximo(proximo.getProximo());
					nodo.setAnterior(proximo);
					proximo.getProximo().setAnterior(nodo);
					
					proximo.setAnterior(null);
					proximo.setProximo(nodo);
					setPrimeiro(proximo);
				} else { // o nodo esta em algum lugar no meio da lista
					nodo.setProximo(proximo.getProximo());
					nodo.setAnterior(proximo);
					proximo.getProximo().setAnterior(nodo);
					
					proximo.setAnterior(anterior);
					proximo.setProximo(nodo);
					anterior.setProximo(proximo);
				}
			}
			
			return true;
		}
		return false;
	}
	

	/* ===================================================

	Metodo          - remover
	Descricao       - Faz a remocao de um nodo identificado pelo nome
	Entrada         - Uma string com o titulo da atividade/pomodoro
	Processamento   - Caso exista, faz a remocao do nodo identificado pelo nome passado como argumento
	Saida           -

	 =================================================== */	
	@Override
	public boolean remover(String titulo) {
		LinkedNode nodo = buscaItem(titulo);
		if(nodo != null) {
			LinkedNode anterior = nodo.getAnterior();
			LinkedNode proximo = nodo.getProximo();
			if(anterior == null) {
				removerPrimeiro();
				return true;
			} else if (proximo == null) {
				removerUltimo();
				return true;
			}
			anterior.setProximo(proximo);
			proximo.setAnterior(anterior);
			return true;
		} else {
			return false;
		}
	}
	
}
