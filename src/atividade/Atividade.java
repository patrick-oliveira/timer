package atividade;

import java.io.Serializable;

public class Atividade implements Serializable {
	String titulo;
	String descricao;
	int duracao;
	int descanso;
	
	public Atividade(String titulo) {
		this.titulo = titulo;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public int getDuracao() {
		return duracao;
	}
	
	public int getDescanso() {
		return descanso;
	}
}
