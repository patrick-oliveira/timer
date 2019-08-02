package pomodoro;

import java.io.Serializable;
import interfaces.GerenciaTimer;
import javafx.scene.control.Label;
import utilidades.Utilidades;

public abstract class Pomodoro implements Serializable, GerenciaTimer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int tempoDeExecucao;
	protected int tempoDePausa;
	private String alarme_inicio;
	private String alarme_fim;
	
	/* ===================================================

	Metodo          - Pomodoro
	Descricao       - Metodo sobrecarregado. Construtor da classe.
	Entrada         - 
	Processamento   - Construtor "padrao" para dar liberdade de customizacao nas subclasses.
	Saida           -

	 =================================================== */
	public Pomodoro() {
		
	}
	
	/* ===================================================

	Metodo          - Pomodoro
	Descricao       - Metodo sobrecarregado. Construtor da classe.
	Entrada         - Um inteiro com o tempo de execucao (em segundos); um inteiro com o tempo de pausa (em segundos)
	Processamento   - Faz a atribuicao dos atributos, atribuindo aos audios um endereco padrao.
	Saida           -

	 =================================================== */
	public Pomodoro(int tempoDeExecucao, int tempoDePausa) {
		setTempoDeExecucao(tempoDeExecucao);
		setTempoDePausa(tempoDePausa);
		
		String endPadraoAlarmeInicio = "";
		String endPadraoAlarmeFim = "";
		setAlarmeInicio(endPadraoAlarmeInicio);
		setAlarmeFim(endPadraoAlarmeFim);		
	}

	/* ===================================================

	Metodo          - Pomodoro
	Descricao       - Metodo sobrecarregado. Construtor da classe.
	Entrada         - Um inteiro com o tempo de execucao (em segundos); um inteiro com o tempo de pausa (em segundos);
					Uma string com o endereco de um arquivo de audio para o alarme de inicio;
					Uma string com o endereco de um arquivo de audio para o alarme de termino.
	Processamento   - Faz a atribuicao dos atributos.
	Saida           -

	 =================================================== */
	public Pomodoro(int tempoDeExecucao, int tempoDePausa, String alarme_inicio,
					String alarme_fim) {
		setTempoDeExecucao(tempoDeExecucao);
		setTempoDePausa(tempoDePausa);
		setAlarmeInicio(alarme_inicio);
		setAlarmeFim(alarme_fim);
	}
	
	/* ===================================================

	Metodo          - setTitulo
	Descricao       - Metodo abstrato, a ser implementado nas subclasses. Atribui um titulo a instancia.
	Entrada         - Uma string com o novo titulo da atividade
	Processamento   - Faz a atribuicao do atributo "titulo" com o parametro especificado
	Saida           - 

	 =================================================== */
	public abstract void setTitulo(String titulo);
	
	/* ===================================================

	Metodo          - setDescricao
	Descricao       - Metodo abstrato, a ser implementado nas subclasses. Atribui uma descricao a instancia.
	Entrada         - Uma string com a descricao da atividade.
	Processamento   - Faz a atribuicao do atributo "descricao" com o parametro especificado
	Saida           - 

	 =================================================== */
	public abstract void setDescricao(String descricao);
	
	/* ===================================================

	Metodo          - setTempoDeExecucao
	Descricao       - Faz a atribuicao do atributo tempoDeExecucao.
	Entrada         - Um inteiro com o novo tempo de execucao do pomodoro (em segundos).
	Processamento   - Faz a atribuicao do atributo tempodDeExecucao do pomodoro com o novo tempo passado.
	Saida           -

	 =================================================== */
	public void setTempoDeExecucao(int tempoDeExecucao) {
		this.tempoDeExecucao = tempoDeExecucao;
	}
	
	/* ===================================================

	Metodo          - setTempoDePausa
	Descricao       - Faz a atribuicao do atributo tempoDePausa
	Entrada         - Um inteiro com o novo tempo de pausa do pomodoro (em segundos).
	Processamento   - Faz a atribuicao do atributo tempoDePausa do pomodoro com o novo tempo passado.
	Saida           -

	 =================================================== */
	public void setTempoDePausa(int tempoDePausa) {
		this.tempoDePausa = tempoDePausa;
	}
	
	/* ===================================================

	Metodo          - setAlarmeInicio
	Descricao       - Faz a atribuicao do atributo alarme_inicio
	Entrada         - Uma string com o endereco de um arquivo de audio para o alarme de inicio.
	Processamento   - Faz a atribuicao do atributo alarme_inicio do pomodoro com o novo endereco passado.
	Saida           -

	 =================================================== */
	public void setAlarmeInicio(String alarme_inicio) {
		// fazer um Exception pra verificar a validade da string
		this.alarme_inicio = alarme_inicio;
	}

	/* ===================================================

	Metodo          - setAlarmeInicio
	Descricao       - Faz a atribuicao do atributo alarme_fim.
	Entrada         - Uma string com o endereco de um arquivo de audio para o alarme de termino.
	Processamento   - Faz a atribuicao do atributo alarme_fim do pomodoro com o novo endereco passado.
	Saida           -

	 =================================================== */
	public void setAlarmeFim(String alarme_fim) {
		// fazer um Exception para verificar a validade da string
		this.alarme_fim = alarme_fim;
	}
	
	/* ===================================================

	Metodo          - getTitulo
	Descricao       - Metodo abstrato, a ser implementado nas subclasses. Obtem o titulo da instancia.
	Entrada         - 
	Processamento   - 
	Saida           - Retorna uma string com o titulo da atividade.

	 =================================================== */
	public abstract String getTitulo();
	
	/* ===================================================

	Metodo          - getDescricao
	Descricao       - Metodo abstrato, a ser implementado nas subclasses. Obtem a descricao da instancia.
	Entrada         - Uma string com a descricao da atividade.
	Processamento   - 
	Saida           - Retorna uma string com a descricao da atividade.

	 =================================================== */
	public abstract String getDescricao();
	
	/* ===================================================

	Metodo          - getDuracao
	Descricao       - retorna o tempo de execucao do pomodoro (em segundos).
	Entrada         - 
	Processamento   - 
	Saida           - Um inteiro com o tempo de exeucao do pomodoro (em segundos).

	 =================================================== */
	public int getDuracao() {
		return this.tempoDeExecucao;
	}
	
	/* ===================================================

	Metodo          - getPausa
	Descricao       - retorna o tempo de pausa do pomodoro (em segundos).
	Entrada         - 
	Processamento   - 
	Saida           - Um inteiro com o tempo de pausa do pomodoro (em segundos).

	 =================================================== */
	public int getPausa() {
		return this.tempoDePausa;
	}
	
	/* ===================================================

	Metodo          - getAlarmeInicio
	Descricao       - Retorna o endereco do alarme de termino.
	Entrada         - 
	Processamento   - 
	Saida           - Uma string com o endereco do alarme de inicio do pomodoro.

	 =================================================== */
	public String getAlarmeInicio() {
		return this.alarme_inicio;
	}
	
	
	/* ===================================================

	Metodo          - getAlarmeFim
	Descricao       - Retorna o endereco do alarme de termino.
	Entrada         - 
	Processamento   - 
	Saida           - Uma string com o endereco do alarme de termino do pomodoro.

	 =================================================== */
	public String getAlarmeFim() {
		return this.alarme_fim;
	}
	
	/* ===================================================

	Metodo          - comparaPomodoro
	Descricao       - Metodo abstrato, a ser implementado nas subclasses.
					Faz uma comparacao entre a atual instancia e um objeto passado como parametro.
	Entrada         - Um tipo Pomodoro
	Processamento   - Compara a instancia da classe com o objeto passado.
	Saida           - True se os objetos sao iguais. False caso contrario.

	 =================================================== */
	public abstract boolean comparaPomodoro(Pomodoro item);
	
//	@Override
//	public Pomodoro clone() {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try {
//			ObjectOutputStream oos = new ObjectOutputStream(bos);
//			oos.writeObject(this);
//			oos.flush();
//			oos.close();
//			bos.close();
//			byte[] byteData = bos.toByteArray();
//			
//			ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
//			return (Pomodoro) new ObjectInputStream(bais).readObject();
//		} catch (IOException | ClassNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
	/* ===================================================

	Metodo          - duracaoParaHMS
	Descricao       - Converte o tempo de duracao da instancia (em segundos, int) para
					o formato HORAS/MINUTOS/SEGUNDOS, em um vetor de Integers.
	Entrada         - 
	Processamento   - Faz a chamada da função que faz a conversão, do pacote Utilidades
	Saida           - Um vetor de Integers

	 =================================================== */
	public Integer[] duracaoParaHMS() {
		return Utilidades.secParaHMS(this.tempoDeExecucao);
	}
	
	/* ===================================================

	Metodo          - pausaParaHMS
	Descricao       - Converte o tempo de pausa da instancia (em segundos, int) para
					o formato HORAS/MINUTOS/SEGUNDOS, em um vetor de Integers.
	Entrada         - 
	Processamento   - Faz a chamada da função que faz a conversão, do pacote Utilidades
	Saida           - Um vetor de Integers

	 =================================================== */
	public Integer[] pausaParaHMS() {
		return Utilidades.secParaHMS(this.tempoDePausa);
	}
	
	// Metodos da interface. Precisei declara-los aqui para usar polimorfismo.
	public void executaTimer(Label hora, Label min, Label sec) throws InterruptedException {}
	public void imprimeTempo(int tempo, Label hora, Label min, Label sec) { }
	
}
