package pomodoro;

import java.io.Serializable;

import utilidades.Utilidades;

public class Tarefa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8262226399539620054L;
	private String titulo;
	private int duracao;
	private int pausa;
	
	/* ===================================================

	Metodo          - Tarefa
	Descricao       - Construtor da classe
	Entrada         - Uma String para o titulo da tarefa. Inteiros para o tempo de duracao
					e pausa.
	Processamento   - Faz a atribuicao dos valores.
	Saida           -

	 =================================================== */
	public Tarefa(String titulo, int duracao, int pausa) {
		this.titulo = titulo;
		this.duracao = duracao;
		this.pausa = pausa;
	}

	/* ===================================================

	Metodo          - getTitulo
	Descricao       - Obtem o titulo da tarefa
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public String getTitulo() {
		return this.titulo;
	}
	
	/* ===================================================

	Metodo          - getDuracao
	Descricao       - Obtem o tempo de duracao (em segundos) da tarefa.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public int getDuracao() {
		return this.duracao;
	}
	
	/* ===================================================

	Metodo          - getPausa
	Descricao       - Obtem o tempo de pausa (em segundos) da tarefa.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public int getPausa() {
		return this.pausa;
	}
	
	/* ===================================================

	Metodo          - getDuracaoHMS
	Descricao       - Obtem o tempo de duracao (como HH:MM:SS) da tarefa.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public Integer[] getDuracaoHMS() {
		return Utilidades.secParaHMS(this.duracao);
	}

	/* ===================================================

	Metodo          - getPausaHMS
	Descricao       - Obtem o tempo de pausa (como HH:MM:SS) da tarefa.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public Integer[] getPausaHMS() {
		return Utilidades.secParaHMS(this.pausa);
	}
	
	/* ===================================================

	Metodo          - toString
	Descricao       - Obtem uma string com o titulo e os tempos de duracao e pausa da tarefa.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	@Override
	public String toString() {
		return getTitulo() + " - " + Utilidades.tempoToString(getDuracaoHMS()) + " - " + Utilidades.tempoToString(getPausaHMS());
	}
	
}
