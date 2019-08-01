package lista_pomodoros;
import pomodoro.*;

public class LinkedNode {
	private Pomodoro data;
	private LinkedNode proximo;
	private LinkedNode anterior;
	
	/* ===================================================

	Metodo          - LinkedNode
	Descricao       - Construtor da classe
	Entrada         - Um tipo Pomodoro.
	Processamento   - Atribui o objeto do argumento como conteudo do nodo.
	Saida           - 

	=================================================== */
	public LinkedNode(Pomodoro item) {
		setData(item);
	}
	
	/* ===================================================

	Metodo          - setProximo
	Descricao       - Faz a atribuicao do nodo posterior ao atual 
	Entrada         - Um tipo LinkedNode com o proximo nodo
	Processamento   - 
	Saida           - 

	=================================================== */	
	public void setProximo(LinkedNode novoProximo) {
		this.proximo = novoProximo;
	}
	
	/* ===================================================

	Metodo          - setAnterior
	Descricao       - Faz a atribuicao do nodo anterior ao atual
	Entrada         - Um tipo LinkedNode com o nodo anterior
	Processamento   - 
	Saida           - 

	=================================================== */	
	public void setAnterior(LinkedNode novoAnterior) {
		this.anterior = novoAnterior;
	}
	
	
	/* ===================================================

	Metodo          - setData
	Descricao       - Faz atribuicao de um objeto ao atributo "data".
	Entrada         - Um tipo Pomodoro.
	Processamento   - Apenas faz a atribuicao do atributo "data".
	Saida           - 

	=================================================== */
	public void setData(Pomodoro item) {
		this.data = item;
	}

	/* ===================================================

	Metodo          - getProximo	
	Descricao       - Retorna o nodo posterior ao atual
	Entrada         - 
	Processamento   - 
	Saida           - Um tipo LinkedNode com o nodo posterior

	=================================================== */	

	public LinkedNode getProximo() {
		return this.proximo;
	}
	
	/* ===================================================

	Metodo          - getAnterior
	Descricao       - Retorna o nodo anterior ao atual
	Entrada         - 
	Processamento   - 
	Saida           - Um tipo LinkedNode com o nodo anterior

	=================================================== */	
	public LinkedNode getAnterior() {
		return this.anterior;
	}

	/* ===================================================

	Metodo          - getData
	Descricao       - Retorna o conteudo do nodo
	Entrada         - 
	Processamento   - Apenas retorna o conteudo do atributo "data"
	Saida           - 

	=================================================== */
	public Pomodoro getData() {
		return this.data;
	}
}
