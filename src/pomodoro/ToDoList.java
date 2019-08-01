package pomodoro;

import java.io.Serializable;
import interfaces.GerenciaTimer;
import javafx.scene.control.Label;

public class ToDoList extends Pomodoro implements Serializable, GerenciaTimer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3330172375102609018L;
	String titulo;
	String descricao;
	Tarefa[] listaTarefas;
	int maxTarefas;
	int idAtual;

	public ToDoList(String titulo, String descricao, int numeroTarefas) {
		super();
		setTitulo(titulo);
		setDescricao(descricao);
		setMaxTarefas(numeroTarefas);
		this.idAtual = 0;
		listaTarefas = new Tarefa[numeroTarefas];
	}

	@Override
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	@Override
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public void setMaxTarefas(int max) {
		this.maxTarefas = max;
	}
	
	public int getMaxTarefas() {
		return this.maxTarefas;
	}
	
	@Override
	public String getTitulo() {
		return this.titulo;
	}

	@Override
	public String getDescricao() {
		return this.descricao;
	}
	
	public Tarefa[] getTarefas() {
		return this.listaTarefas;
	}
	
	public String getNomesTarefas() {
		StringBuilder construtor = new StringBuilder();
		for(int i = 0; i < idAtual; i++) {
			construtor.append((i+1)+"."+listaTarefas[i].getTitulo()+";");
		}
		return construtor.toString();
	}
	
	public boolean estaCheio() {
		if(idAtual < maxTarefas) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean adicionaTarefa(Tarefa nova_tarefa) {
		if(!estaCheio()) {
			listaTarefas[idAtual] = nova_tarefa;
			idAtual++;
			setTempoDeExecucao(getDuracao() + nova_tarefa.getDuracao());
			setTempoDePausa(getPausa() + nova_tarefa.getPausa());
			return true;
		} else {
			return false;
		}
	}
	
	public boolean adicionaTarefa(String titulo, int duracao, int pausa) {
		if(!estaCheio()) {
			return adicionaTarefa(new Tarefa(titulo, duracao, pausa));
		}  else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder construtor = new StringBuilder();
		try {
			construtor.append(getTitulo());
			construtor.append(getDescricao());
			construtor.append("\n\nTarefas:\n");
			for (int i = 0; i < idAtual; i++) {
				// melhorar isso depois
				construtor.append(listaTarefas[i].toString());
				construtor.append("\n");
			}
		} catch(NullPointerException e) {
			System.out.println(e.getMessage());
		}
		return construtor.toString();
	}

	@Override
	public boolean comparaPomodoro(Pomodoro item) {
		if(this.titulo == item.getTitulo()) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void executaTimer(Label hora, Label min, Label sec) throws InterruptedException{
		
	}
	
	@Override
	public void imprimeTempo(int tempo, Label hora, Label min, Label sec) {
		
	}
}
