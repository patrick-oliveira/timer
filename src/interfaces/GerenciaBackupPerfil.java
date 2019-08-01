package interfaces;

public interface GerenciaBackupPerfil {
	/* ===================================================

	Metodo          - salvar
	Descricao       - Salvar as informacoes do perfil em um arquivo.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	void salvar();
	
	/* ===================================================

	Metodo          - salvar
	Descricao       - Deleta todos os arquivos ligados a uma instancia 
					de um Perfil.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */
	void remover();
}
