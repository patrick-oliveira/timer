package gerenciador_arquivos;

import perfil.Perfil;
import pomodoro.Pomodoro;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;


public class GerenciadorPrincipal {
	public static File end_info_perf; // Endereco da pasta com os arquivos dos perfis
	public static File end_info_ativ; // Endereco da pasta com as atividades
	public static String sep;		  // Define o separador de diretorios como // ou \\ a depender do sistema.
	
	/* ===================================================

	Metodo          - "DefaultConfig"
	Descricao       - Especifica a configuracao padrao do Gerenciador
	Entrada         - 
	Processamento   - Cria os caminhos para as pastas de atividades e de perfis. Verifica se os diretorios existem, e os cria caso nao existam. 
					  A pasta raiz eh criada no diretorio em que o programa eh executado.
	Saida           -

	 =================================================== */
	public static void DefaultConfig() {
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		if(path.toString().contains("/")) {
			sep = "//";
		} else {
			sep = "\\";
		}
		File pasta_dados = new File(path+sep+"Dados");
		File pasta_perfis = new File(path+sep+"Dados"+sep+"Perfis");
		File pasta_atividades = new File(path+sep+"Dados"+sep+"Atividades");
		
		if(!pasta_dados.exists())
			pasta_dados.mkdir();
		if(!pasta_atividades.exists())
			pasta_atividades.mkdir();
		if(!pasta_perfis.exists())
			pasta_perfis.mkdir();

		setEnderecoPerfil(pasta_perfis);
		setEnderecoAtividade(pasta_atividades);	
	}
	
	/* ===================================================

	Metodo          - "setEnderecoAtividade"
	Descricao       - Modifica o endereco da pasta de atividades vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um diretorio.
	Processamento   - Altera o atributo "end_info_ativ" com o tipo "File" vinculado ao endereco da pasta de Atividades.
	Saida           -

	=================================================== */
	public static void setEnderecoAtividade(File novo_endereco) {
		end_info_ativ = novo_endereco;
	}

	/* ===================================================

	Metodo          - "setEnderecoPerfil"
	Descricao       - Modifica o endereco da pasta de perfis vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um diretorio.
	Processamento   - Altera o atributo "end_info_perf" com o tipo "File" vinculado ao endereco da pasta de Perfis.
	Saida           -

	=================================================== */
	public static void setEnderecoPerfil(File novo_endereco) {
		end_info_perf = novo_endereco;
	}
	
	/* ===================================================

	Metodo          - "getListaArquivos"
	Descricao       - Obtem os nomes dos arquivos dentro do diretorio passado como argumento
	Entrada         - Um tipo "File" vinculado a um diretorio.
	Processamento   - Faz uma varredura no endereco contido no objeto "File", incluindo os nomes dos arquivos em um array de strings.
	Saida           - Um array de strings

	=================================================== */
	public static String[] getListaArquivos(File pasta) {
		int n = pasta.listFiles().length;
		if (n > 0) {
			String[] lista_nomes = new String[n];
			int i = 0;
			for(File fileEntry : pasta.listFiles()) {
				lista_nomes[i] = fileEntry.getName().replace(".dat", "");
				i++;
			}
			return lista_nomes;
		} else {
			return null;
		}
	}
	
	/* ===================================================

	Metodo          - "getPerfis"
	Descricao       - Obtem os nomes dos perfis criados.
	Entrada         - 
	Processamento   - (Pelo metodo getListaArquivos) Faz uma varredura no endereco dos arquivos de perfis, 
					  incluindo os nomes dos perfis em um array de strings.
	Saida           - Um array de strings

	=================================================== */
	public static String[] getPerfis() {
		return getListaArquivos(end_info_perf);
	}	

	/* ===================================================

	Metodo          - "buscarArquivo"
	Descricao       - Metodo sobrecarregado. Faz uma busca por um arquivo de um perfil na pasta de perfis
	Entrada         - Uma string com o nome do perfil.
	Processamento   - Gera um tipo File vinculado ao endereco do arquivo do perfil buscado.
	Saida           - Retorna o tipo File caso o arquivo exista, e null caso contrario

	=================================================== */
	public static File buscarArquivo(String perfil) {
		File Perfil_path = new File(end_info_perf.getAbsolutePath()+sep+perfil+".dat");
		return Perfil_path.exists() ? Perfil_path:null;
	}
	
	/* ===================================================

	Metodo          - "buscarArquivo"
	Descricao       - Metodo sobrecarregado. Faz uma busca por um arquivo de uma atividade na pasta de atividades
						vinculada a um perfil
	Entrada         - Uma string com o nome do perfil, e uma string com o nome da atividade.
	Processamento   - Gera um tipo File vinculado ao endereco do arquivo da atividade buscada.
	Saida           - Retorna o tipo File caso o arquivo exista, e null caso contrario

	=================================================== */	
	public static File buscarArquivo(String perfil, String titulo_atividade) {
		File atividade_path = new File(end_info_ativ.getAbsolutePath()+sep+perfil+sep+titulo_atividade+".dat");
		return atividade_path.exists() ? atividade_path:null;
	}
	
	
	/* ===================================================

	Metodo          - "gerarArquivo"
	Descricao       - Metodo sobrecarregado. Gera um arquivo para o perfil especificado como argumento
	Entrada         - Um objeto Perfil.
	Processamento   - Gera um caminho para um arquivo ".dat" nomeado com o nome do Perfil passado como argumento. Verifica sua existencia, 
						e o cria caso nao exista. Tal arquivo guarda as informacoes do perfil.
	Saida           - Um tipo "File" vinculado ao endereco do arquivo vinculado ao Perfil.

	=================================================== */
	public static File gerarArquivo(Perfil perfil) {
		File Perfil_path = new File(end_info_perf.getAbsolutePath()+sep+perfil.getNome() + ".dat");
		try {
			if (!Perfil_path.exists()) {
				Perfil_path.createNewFile();
				try (ObjectOutputStream perfilFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(Perfil_path.getAbsolutePath())))){
					perfilFile.writeObject(perfil);
				}
				
				(new File(end_info_ativ.getAbsolutePath()+sep+perfil.getNome())).mkdir();
				
				System.out.println("Perfil " + perfil.getNome() + " criado");
				
			} else {
				System.out.println("Perfil "+perfil.getNome()+" ja existe");
			}
			return Perfil_path;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* ===================================================

	Metodo          - "gerarArquivo"
	Descricao       - Metodo sobrecarregado. Gera um arquivo do tipo especificado como argumento.
	Entrada         - Um tipo "Atividade", uma string com o perfil vinculado a atividade.
	Processamento   - Gera um caminho para um arquivo ".dat" nomeado com o titulo da Atividade passada como argumento. 
					   Verifica sua existencia, e o cria caso naoo exista.
	Saida           - Um tipo File vinculado ao endereco do arquivo gerado.

	=================================================== */
	public static File gerarArquivo(Pomodoro atividade, String perfil) {
		File atividade_path = new File(end_info_ativ.getAbsolutePath()+sep+perfil+sep+atividade.getTitulo() + ".dat");
		try {
			if (!atividade_path.exists()) {
				
				atividade_path.createNewFile();
				try (ObjectOutputStream perfilFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(atividade_path.getAbsolutePath())))){
					perfilFile.writeObject(atividade);
				}
				return atividade_path;
			} else {
				return null;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	/* ===================================================

	Metodo          - "removerArquivo"
	Descricao       - Metodo sobrecarregado. Deleta as informacoees do perfil especificado
	Entrada         - Uma string com o nome do perfil
	Processamento   - Deleta o arquivo de informacoes do perfil e sua pasta de atividades.
	Saida           -

	=================================================== */
	public static void removerArquivo(String perfil) {
		File perfil_path = new File(end_info_perf.getAbsolutePath()+sep+perfil+".dat");
		if(perfil_path.exists()) {
			// Deleta o arquivo com as informacoes do perfil
			perfil_path.delete();
			
			File end_atividades = new File(end_info_ativ.getAbsolutePath()+sep+perfil);
			if(end_atividades.exists()) {
				// Deleta todas as atividades vinculadas ao perfil
				File[] arquivos = end_atividades.listFiles();
				for(int i = 0; i < arquivos.length; i++) {
					if(arquivos[i].isFile()) {
						removerArquivo(arquivos[i].getName().replace(".dat", ""), perfil);
					}
				}
				// Deleta a pasta de atividades vinculada ao perfil
				end_atividades.delete();
			}
		} else {
			System.out.println("Perfil "+perfil+" nao existe");
		}
	}
	
	/* ===================================================

	Metodo          - "removerArquivo"
	Descricao       - Metodo sobrecarregado. Deleta um arquivo de atividade de um perfil.
	Entrada         - Uma String com o titulo da atividade. Uma String com o nome do perfil que possui a atividade.
	Processamento   - Verifica se a atividade com titulo passado por argumento existe na pasta de atividades vinculada ao perfil especificada. 
					  Se sim, deleta o arquivo.
	Saida           - 

	=================================================== */
	public static void removerArquivo(String titulo_atividade, String perfil) {
		File atividade = new File(end_info_ativ.getAbsolutePath()+sep+perfil+sep+titulo_atividade+".dat");
		if(atividade.exists()) {
			atividade.delete();
		} else {
			System.out.println("Atividade "+titulo_atividade+" nao existe.");
		}
	}
}
