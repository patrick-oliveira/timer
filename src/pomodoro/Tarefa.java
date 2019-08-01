package pomodoro;

import java.io.Serializable;

import utilidades.Utilidades;

public class Tarefa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8262226399539620054L;
	String titulo;
	int duracao;
	int pausa;
	
	public Tarefa(String titulo, int duracao, int pausa) {
		this.titulo = titulo;
		this.duracao = duracao;
		this.pausa = pausa;
	}

	public String getTitulo() {
		return this.titulo;
	}
	
	public int getDuracao() {
		return this.duracao;
	}
	
	public int getPausa() {
		return this.pausa;
	}
	
	public Integer[] getDuracaoHMS() {
		return Utilidades.secParaHMS(this.duracao);
	}
	
	public Integer[] getPausaHMS() {
		return Utilidades.secParaHMS(this.pausa);
	}
	
	@Override
	public String toString() {
		return getTitulo() + " - " + Utilidades.tempoToString(getDuracaoHMS()) + " - " + Utilidades.tempoToString(getPausaHMS());
	}
	
}
