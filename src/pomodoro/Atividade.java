package pomodoro;

import java.io.Serializable;

import interfaces.GerenciaTimer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import utilidades.Utilidades;

public class Atividade extends Pomodoro implements Serializable, GerenciaTimer, Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7712176088226708435L;
	private String titulo;
	private String descricao;
	
	/* ===================================================

	Metodo          - Atividade
	Descricao       - Metodo sobrecarregado. Construtor da classe.
	Entrada         - Uma string com o titulo da atividade;
					  Uma string com a descricao da atividade (podendo ser vazia);
					  Um inteiro com o tempo de execucao da atividade;
					  Um inteiro com o tempo de pausa da atividade.
	Processamento   - Chamada o construtor da superclasse, e faz as atribuicoes necessarias.
	Saida           -

	 =================================================== */
	public Atividade(String titulo, String descricao, int tempoExecucao, int tempoPausa) {
		super(tempoExecucao, tempoPausa);
		setTitulo(titulo);
		setDescricao(descricao);
	}
	
	/* ===================================================

	Metodo          - Atividade
	Descricao       - Metodo sobrecarregado. Construtor da classe.
	Entrada         - Uma string com o titulo da atividade;
					  Uma string com a descricao da atividade (podendo ser vazia);
					  Um inteiro com o tempo de execucao da atividade;
					  Um inteiro com o tempo de pausa da atividade;
					  Uma string com o endereco do alarme de inicio;
					  Uma string com o endereco do alarme de termino;
	Processamento   - Chamada o construtor da superclasse, e faz as atribuicoes necessarias.
	Saida           -

	 =================================================== */
	public Atividade(String titulo, String descricao, int tempoExecucao, int tempoPausa,
			 		 String alarmeInicio, String alarmeFim) {
		super(tempoExecucao, tempoPausa, alarmeInicio, alarmeFim);
		setTitulo(titulo);
		setDescricao(descricao);
	}
	
	/* ===================================================

	Metodo          - setTitulo
	Descricao       - Implementacao do metodo abstrato da superclasse. Faz a atribuicao do titulo da atividade.
	Entrada         - Uma string com o novo titulo da atividade.
	Processamento   - Faz a atribuicao do novo titulo ao atributo "titulo"
	Saida           -

	 =================================================== */
	@Override
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/* ===================================================

	Metodo          - setDescricao
	Descricao       - Faz a atribuicao da descricao da atividade.
	Entrada         - Uma string com a nova descricao da atividade.
	Processamento   - Faz a atribuicao da nova descricao ao atributo "descricao".
	Saida           -

	 =================================================== */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	/* ===================================================

	Metodo          - getTitulo
	Descricao       - Implementacao do metodo abstrato da superclasse. Obtem o titulo da instancia.
	Entrada         - 
	Processamento   - 
	Saida           - Uma string com o conteudo do atributo "titulo".

	 =================================================== */
	@Override
	public String getTitulo() {
		return this.titulo;
	}
	
	/* ===================================================

	Metodo          - getDescricao
	Descricao       - Obtem a descricao da instancia.
	Entrada         - 
	Processamento   - 
	Saida           - Uma string com o o conteudo do atributo "descricao".

	 =================================================== */
	public String getDescricao() {
		return this.descricao;
	}
	
	/* ===================================================

	Metodo          - comparaPomodoro
	Descricao       - Implementacao do metodo abstrato da superclasse. Faz uma comparacao entre a instancia atual
					e um objeto passado como argumento.
	Entrada         - Um tipo Pomodoro.
	Processamento   - Faz a comparacao entre as duas atividades pelos seus titulos.
	Saida           - True, se os titulos sao iguais. False, caso contrario.

	 =================================================== */
	@Override
	public boolean comparaPomodoro(Pomodoro item) {
		if(this.getTitulo().equals(item.getTitulo())) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return getTitulo() + "\n\n" + getDescricao();
	}
	
	
	/* ===================================================

	Metodo          - executaTimer
	Descricao       - Implementacao do metodo da interface. Eh o metodo chamado na classe controladora da janela
					do Timer, na interface de usuario. Ele fara a contagem regressiva do tempo de execucao, segui-
					do do tempo de Pausa, apresentando o tempo na tela (GUI).
	Entrada         - Tipos "Label" hora, min e sec. Um objeto da interface de usuario onde os numeros do tempo'
					serao apresentados.
	Processamento   - A chamada deste metodo eh feita em uma Thread paralela a Thread principal do programa.
					O metodo entre em um loop para a execucao, outro para a pausa, diminuindo-os em uma unidade
					a cada segundo. Os loops sao quebrados apenas quando o tempo chega a 0.
	Saida           - 

	 =================================================== */
	@Override
	public void executaTimer(Label hora, Label min, Label sec) throws InterruptedException {
		// Recupera o tempo de execucao
		int tempoAtual = getDuracao();
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
		tempoAtual = getPausa();
		while(true) {
			Thread.sleep(1000);
			tempoAtual--;
			imprimeTempo(tempoAtual, hora, min, sec);
			if(tempoAtual == 0) {
				break;
			}
		}
	}
	
	/* ===================================================

	Metodo          - imprimeTempo
	Descricao       - Implementacao do metodo da interface. Apresenta na tela o tempo da contagem regressiva
	Entrada         - Um inteiro com o tempo (em segundos) a ser apresentado. Tipos "Label" para a hora, mi-
					nutos e segundos.
	Processamento   - O tempo (em segundos) eh convertido para o formato 00:00:00 (H:M:S) e apresentado na interface de 
					usuario 
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
