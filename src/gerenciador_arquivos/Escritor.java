package gerenciador_arquivos;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import perfil.Perfil;
import pomodoro.Pomodoro;

public class Escritor extends GerenciadorPrincipal {
	
	/* ===================================================

	Metodo          - 
	Descricao       - Faz a chamada (estatica) do metodo que configura as classes
					com os parametros padroes (endereco das pastas principais)
	Entrada         - 
	Processamento   - 
	Saida           -

	 =================================================== */
	static {
		DefaultConfig();
	}
	
	/* ===================================================

	Metodo          - escreverAtividade
	Descricao       - Escreve em cima (criando, se necessario) de um arquivo um objeto
					tipo Pomodoro, na pasta de atividades vinculada ao perfil especificado
	Entrada         - Um tipo Pomodoro, a ser salvo em um arquivo, e uma string com o nome
					do perfil para identificacao da pasta a ser salva
	Processamento   - Abre o arquivo vinculado a atividade, se existe, e escreve em cima. Caso
					contrario, faz a chamada do metodo de criacao de um arquivo.
	Saida           -

	 =================================================== */
	public static void escreverAtividade(Pomodoro atividade, String perfil) {
		File atividade_path = new File(end_info_ativ.getAbsolutePath()+sep+perfil+sep+atividade.getTitulo() + ".dat");
		try {
			if(atividade_path.exists()) {
				try (ObjectOutputStream atividadeFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(atividade_path.getAbsolutePath())))){
					atividadeFile.writeObject(atividade);
				}
			} else {
				gerarArquivo(atividade, perfil);
			}
		} catch(IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	
	/* ===================================================

	Metodo          - escreverPerfil
	Descricao       - Escreve em cima (criando, se necessario) de um arquivo um objeto
					tipo Perfil.
	Entrada         - Um objeto tipo Perfil, a ser salvo.
	Processamento   - Escreve em cima do arquivo, caso ja exista, o objeto Perfil especificado.
					Caso nao exista, faz a chamada do metodo de criacao de arquivos.
	Saida           -

	 =================================================== */
	public static void escreverPerfil(Perfil perfil) {
		File Perfil_path = new File(end_info_perf.getAbsolutePath()+sep+perfil.getNome() + ".dat");
		try {
			if(Perfil_path.exists()) {
				try (ObjectOutputStream perfilFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(Perfil_path.getAbsolutePath())))){
					perfilFile.writeObject(perfil);
				}
			} else {
				gerarArquivo(perfil);
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
}
