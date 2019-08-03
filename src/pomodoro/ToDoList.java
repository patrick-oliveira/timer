package pomodoro;

import java.io.Serializable;
import interfaces.GerenciaTimer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import utilidades.Utilidades;

public class ToDoList extends Pomodoro implements Serializable, GerenciaTimer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3330172375102609018L;
	private String titulo;
	private String descricao;
	private Tarefa[] listaTarefas;
	private int maxTarefas;
	private int idAtual;

	/* ===================================================

	Metodo          - ToDoList
	Descricao       - Construtor da classe
	Entrada         - Uma string para o titulo, uma para a descricao, um inteiro com o numero de tarefas
					que a lista contem.
	Processamento   - Faz a atribuicao de valores e instanciacao do vetor de tarefas.
	Saida           -

	 =================================================== */
	public ToDoList(String titulo, String descricao, int numeroTarefas) {
		super();
		setTitulo(titulo);
		setDescricao(descricao);
		setMaxTarefas(numeroTarefas);
		this.idAtual = 0;
		listaTarefas = new Tarefa[numeroTarefas];
	}
	
	public ToDoList(String titulo, String descricao, int numeroTarefas, String alarme_inicio, String alarme_fim) {
		super();
		this.setAlarmeInicio(alarme_inicio);
		this.setAlarmeFim(alarme_fim);
		setTitulo(titulo);
		setDescricao(descricao);
		setMaxTarefas(numeroTarefas);
		this.idAtual = 0;
		listaTarefas = new Tarefa[numeroTarefas];
	}

	/* ===================================================

	Metodo          - setTitulo
	Descricao       - Faz a atribuicao do titulo da lista.
	Entrada         - Uma string com o titulo.
	Processamento   - 
	Saida           -

	 =================================================== */
	@Override
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/* ===================================================

	Metodo          - setDescricao
	Descricao       - Faz a atribuicao da descricao da lista.
	Entrada         - Uma string com a descricao.
	Processamento   - 
	Saida           -

	 =================================================== */
	@Override
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	/* ===================================================

	Metodo          - setMaxTarefas
	Descricao       - Faz a atribuicao do numero maximo de tarefas da lista.
	Entrada         - Um inteiro com o numero de tarefas.
	Processamento   - 
	Saida           -

	 =================================================== */
	public void setMaxTarefas(int max) {
		this.maxTarefas = max;
	}
	
	/* ===================================================

	Metodo          - getIdAtual
	Descricao       - Obtem o numero de itens adicionados na lista de tarefas
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public int getIdAtual() {
		return this.idAtual;
	}
	
	/* ===================================================

	Metodo          - getMaxTarefas
	Descricao       - Obtem o numero maximo de tarefas que podem ser adicionadas
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public int getMaxTarefas() {
		return this.maxTarefas;
	}
	
	/* ===================================================

	Metodo          - getTitulo
	Descricao       - Obtem o titulo da lista de tarefas
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	@Override
	public String getTitulo() {
		return this.titulo;
	}

	/* ===================================================

	Metodo          - getDescricao
	Descricao       - Obtem a descricao da lista de tarefas.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	@Override
	public String getDescricao() {
		return this.descricao;
	}
	
	/* ===================================================

	Metodo          - getTarefas
	Descricao       - Obtem o vetor de tarefas da lista.
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public Tarefa[] getTarefas() {
		return this.listaTarefas;
	}
	
	/* ===================================================

	Metodo          - getNomesTarefas
	Descricao       - Obtem uma string com o nome de todas as tarefas adicionadas, separadas por ;
	Entrada         - 
	Processamento   - Percorre as tarefas adicionadas no vetor, construindo uma string com seus nomes.
	Saida           -

	 =================================================== */
	public String getNomesTarefas() {
		StringBuilder construtor = new StringBuilder();
		for(int i = 0; i < idAtual; i++) {
			construtor.append((i+1)+"."+listaTarefas[i].getTitulo()+";");
		}
		return construtor.toString();
	}
	
	/* ===================================================

	Metodo          - estaCheio
	Descricao       - Verifica se o vetor de tarefas esta totalmente preenchido
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	public boolean estaCheio() {
		if(idAtual < maxTarefas) {
			return false;
		} else {
			return true;
		}
	}
	
	/* ===================================================

	Metodo          - adicionaTarefa
	Descricao       - Metodo sobrecarregado. Adiciona uma nova tarefa no vetor de tarefas.
	Entrada         - Um tipo Tarefa com o novo item a ser adicionado.
	Processamento   - Verifica se o vetor de tarefas ja esta preenchido. Caso nao esteja,
					adiciona uma nova tarefa, e incrementa o tempo total da lista com o
					tempo da tarefa adicionada.
	Saida           -

	 =================================================== */
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
	
	/* ===================================================

	Metodo          - adicionaTarefa
	Descricao       - Metodo sobrecarregado. Adiciona uma nova tarefa no vetor de tarefas.
	Entrada         - Uma string com o titulo da tarefa, e inteiros com seu tempo de execucao
					e de pausa.
	Processamento   - Gera uma instancia de Tarefa e passa este objeto para a outra funcao de adicionar
					tarefas.
	Saida           -

	 =================================================== */
	public boolean adicionaTarefa(String titulo, int duracao, int pausa) {
		if(!estaCheio()) {
			return adicionaTarefa(new Tarefa(titulo, duracao, pausa));
		}  else {
			return false;
		}
	}
	
	/* ===================================================

	Metodo          - toString
	Descricao       - 
	Entrada         - 
	Processamento   - Gera uma string contendo o titulo da lista, sua descricao, e os titulos e tempos
					das tarefas contidas, uma em cada linha.
	Saida           -

	 =================================================== */
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

	/* ===================================================

	Metodo          - comparaPomodoros
	Descricao       - 
	Entrada         - 
	Processamento   - Faz a comparacao entre duas listas pelo seu titulo
	Saida           -

	 =================================================== */
	@Override
	public boolean comparaPomodoro(Pomodoro item) {
		if(this.titulo == item.getTitulo()) {
			return true;
		} else {
			return false;
		}
	}
	
	/* ===================================================

	Metodo          - executaTimer
	Descricao       - Metodo da interface de gerenciamento do Timer.
	Entrada         - Tipos Label para a apresentacao da hora, minutos e segundos do tempo restante
					na interface grafica.
	Processamento   - Executa a contagem regressiva para cada item no vetor de tarefas.
	Saida           -

	 =================================================== */
	@Override
	public void executaTimer(Label hora, Label min, Label sec) throws InterruptedException{
		int i = 0;
		while(i < idAtual) {
			// Recupera o tempo de execucao
			int tempoAtual = listaTarefas[i].getDuracao();
			while(true) {
				// Faz a thread atual (paralela a principal) esperar por 1 segundo (1000ms)
				Thread.sleep(1000);
				tempoAtual--;
				// Apresenta o tempo atual na interface de usuario
				imprimeTempo(tempoAtual, hora, min, sec);
				if(tempoAtual == 0) {
					break;
				}
			}
			tempoAtual = listaTarefas[i].getPausa();
			while(true) {
				Thread.sleep(1000);
				tempoAtual--;
				imprimeTempo(tempoAtual, hora, min, sec);
				if(tempoAtual == 0) {
					break;
				}
			}
			i++;
		}
	}
	
	/* ===================================================

	Metodo          - imprimeTempo
	Descricao       - Metodo da interface de gerenciamento do timer. Apresenta o tempo
					do contador na interface grafica
	Entrada         - Um inteiro com o tempo (em segundos) a ser apresentado, e tipos Label
					para a apresentacao das horas, minutos e segundos restantes.
	Processamento   - Faz a formatacao adequada das strings de tempos para que seja apresentado
					no formato 00:00:00.
	Saida           -

	 =================================================== */
	@Override
	public void imprimeTempo(int tempo, Label hora, Label min, Label sec) {
		Integer[] hms = Utilidades.secParaHMS(tempo);
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				if(hms[0] <= 9) {
					hora.setText("0"+hms[0].toString());
				} else {
					hora.setText(hms[0].toString());
				}
				
				if(hms[1] <= 9) {
					min.setText("0"+hms[1].toString());
				} else {
					min.setText(hms[1].toString());
				}
				
				if(hms[2] <= 9) {
					sec.setText("0"+hms[2].toString());
				} else {
					sec.setText(hms[2].toString());
				}
			}
		});
	}
}
