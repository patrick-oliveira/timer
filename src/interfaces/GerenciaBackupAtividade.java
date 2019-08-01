package interfaces;
import pomodoro.*;

public interface GerenciaBackupAtividade {
	/* ===================================================

	Metodo          - salvar
	Descricao       - Salva as informacoes de um pomodoro em um arquivo
	Entrada         - Um tipo pomodoro, com o conteudo a ser salvo. Uma string com o nome do
					perfil ao qual pertence o pomodoro.
	Processamento   - 
	Saida           - 

	=================================================== */
	void salvar(Pomodoro item, String perfil);
	/* ===================================================

	Metodo          - remover
	Descricao       - Remove as informacoes de um pomodoro especificado.
	Entrada         - Um tipo pomodoro, a ser removido da lista e cujo arquivo de backup
					sera deletado. Uma string com o nome do perfil ao qual o pomodoro
					pertence
	Processamento   - 
	Saida           - 

	=================================================== */
	void remover(Pomodoro item, String perfil);
}
