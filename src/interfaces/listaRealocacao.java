package interfaces;

public interface listaRealocacao {
	/* ===================================================

	Metodo          - moverAcima
	Descricao       - Desloca, se possível, o elemento especificado uma casa acima
					na lista ligada, e na apresentacao das atividades na interface
					grafica.
	Entrada         - Uma string com o titulo da atividade a ser movida.
	Processamento   - 
	Saida           - Um boolean indicando se o deslocamento foi realizado ou nao.

	=================================================== */
	boolean moverAcima(String titulo);
	
	/* ===================================================

	Metodo          - moverAbaixo
	Descricao       - Desloca, se possivel, o elemento especificado uma casa
					abaixo na lista ligada, se na apresentacao das atividades na
					interface grafica
	Entrada         - Uma string com o titulo da atividade a ser movida
	Processamento   - 
	Saida           - Um boolean indicando se o deslocamento foi realizado ou nao.

	=================================================== */
	boolean moverAbaixo(String titulo);
	
	/* ===================================================

	Metodo          - remover
	Descricao       - Remove um elemento da lista ligada e da apresentacao
					na interface grafica.
	Entrada         - Uma string com o titulo da atividade a ser removida.
	Processamento   - 
	Saida           - Um boolean indicando se a remocao foi feita ou nao.

	=================================================== */
	boolean remover(String titulo);
	
}
