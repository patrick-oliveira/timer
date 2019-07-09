package gerenciador_arquivos;

import atividade.Atividade;
import perfil.Perfil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;


public class GerenciadorPrincipal {
	private File end_info_perf; // Endere�o da pasta com os arquivos dos perfis
	private File end_info_ativ; // Endere�o da pasta com as atividades (posteriormente vinculada � pasta do perfil especificado)
	
	
	
	/* ===================================================

	Metodo          - "GerenciadorPrincipal"
	Descricao       - Construtor da classe.
	Entrada         - 
	Processamento   - Executa a configura��o padr�o do m�todo DefaultConfig()
	Saida           -

	 =================================================== */
	public GerenciadorPrincipal() {
		DefaultConfig();
	}
	
	/* ===================================================

	Metodo          - "DefaultConfig"
	Descricao       - Especifica a configura��o padr�o do Gerenciador
	Entrada         - 
	Processamento   - Cria os caminhos para as pastas de atividades e de perfis. Verifica se os diret�rios existem, e os cria caso n�o existam.
	Saida           -

	 =================================================== */
	public void DefaultConfig() {
		Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
		File pasta_dados = new File(path+"\\Dados");
		File pasta_perfis = new File(path+"\\Dados\\Perfis");
		File pasta_atividades = new File(path+"\\Dados\\Atividades");
		
		if(!pasta_dados.exists())
			pasta_dados.mkdir();
		if(!pasta_atividades.exists())
			pasta_atividades.mkdir();
		if(!pasta_perfis.exists())
			pasta_perfis.mkdir();

		setEnderecoPerfil(pasta_perfis);
		setEnderecoAtividade(pasta_atividades);
		
		
		//System.out.println(this.end_info_ativ.toString());
		//System.out.println(this.end_info_perf.toString());
		
		// Edite isso aqui para que as extens�es dos arquivos n�o sejam listados.
		//listarArquivos(end_info_perf);		
	}
	
	
	/* ===================================================

	Metodo          - "getListaArquivos"
	Descricao       - Obt�m os nomes dos arquivos dentro do diret�rio passado como argumento
	Entrada         - Um tipo "File" vinculado a um diret�rio.
	Processamento   - Faz uma varredura no endere�o contido no objeto "File", incluindo os nomes dos arquivos em um array de strings.
	Saida           - Um array de strings

	=================================================== */
	public String[] getListaArquivos(File pasta) {
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
	Descricao       - Obt�m os nomes dos perfis criados.
	Entrada         - 
	Processamento   - (Pelo m�todo getListaArquivos) Faz uma varredura no endere�o dos arquivos de perfis, incluindo os nomes dos perfis em um array de strings.
	Saida           - Um array de strings

	=================================================== */
	public String[] getPerfis() {
		return getListaArquivos(this.end_info_perf);
	}
	
	/* ===================================================

	Metodo          - "listarPerfis()"
	Descricao       - Imprime os nomes dos perfis criados.
	Entrada         - 
	Processamento   - (Pelo m�todo getListaArquivos) Faz uma varredura no endere�o dos arquivos de perfis, e imprime os nomes de todos os perfis criados.
	Saida           - 

	=================================================== */
	public void listarPerfis() {
		String[] lista_perfis = getListaArquivos(this.end_info_perf);
		try{
			for(int i = 0; i < lista_perfis.length; i++)
				System.out.println(lista_perfis[i]);
		} catch(NullPointerException e) {
			//
		}
	}
	
	/* ===================================================

	Metodo          - "setEndAtiv"
	Descricao       - Modifica o endere�o da pasta de atividades vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um diret�rio.
	Processamento   - Altera o atributo "end_info_ativ" com o tipo "File" vinculado ao endere�o da pasta de Atividades.
	Saida           -

	=================================================== */
	public void setEnderecoAtividade(File novo_endereco) {
		this.end_info_ativ = novo_endereco;
	}

	/* ===================================================

	Metodo          - "setEndPer"
	Descricao       - Modifica o endere�o da pasta de perfis vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um diret�rio.
	Processamento   - Altera o atributo "end_info_perf" com o tipo "File" vinculado ao endere�o da pasta de Perfis.
	Saida           -

	=================================================== */
	public void setEnderecoPerfil(File novo_endereco) {
		this.end_info_perf = novo_endereco;
	}
	
	
	public File getEnderecoPerfil() {
		return this.end_info_perf;
	}
	
	public File getEnderecoAtividade() {
		return this.end_info_ativ;
	}
	
	public boolean buscarArquivo(Perfil perfil) {
		File Perfil_path = new File(end_info_perf.getAbsolutePath()+"\\"+perfil.getNome()+".dat");
		return Perfil_path.exists();
	}
	
	public boolean buscarArquivo(String titulo_atividade) {
		File atividade_path = new File(end_info_ativ.getAbsolutePath()+"\\"+titulo_atividade+".dat");
		return atividade_path.exists();
	}
	
	
	/* ===================================================

	Metodo          - "gerarArquivo"
	Descricao       - Gera um arquivo para o perfil especificado como argumento
	Entrada         - Uma string com o nome do Perfil.
	Processamento   - Gera um caminho para um arquivo ".dat" nomeado com o nome do Perfil passado como argumento. Verifica sua exist�ncia, e o cria caso n�o exista.
	Saida           - Um tipo "File" contendo o endere�o do arquivo vinculado ao Perfil.

	=================================================== */
	public File gerarArquivo(String Perfil) {
		File Perfil_path = new File(end_info_perf.getAbsolutePath() + "\\" + Perfil + ".dat");
		try {
			if (Perfil_path.createNewFile()) {
				
				try (ObjectOutputStream perfilFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(Perfil_path.getAbsolutePath())))){
					perfilFile.writeObject(new Perfil(Perfil));
				}
				
				(new File(this.end_info_ativ.getAbsolutePath()+"\\"+Perfil)).mkdir();
				
				System.out.println("Perfil " + Perfil + " criado");
				
			} else {
				System.out.println("Perfil j� existe");
			}
			return Perfil_path;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* ===================================================

	Metodo          - "gerarArquivo"
	Descricao       - Gera um arquivo do tipo especificado como argumento.
	Entrada         - Um tipo "Atividade"
	Processamento   - Gera um caminho para um arquivo ".dat" nomeado com o titulo da Atividade passada como argumento. Verifica sua exist�ncia, e o cria caso n�o exista.
	Saida           -

	=================================================== */
	public void gerarArquivo(Atividade atividade) {
		File atividade_path = new File(end_info_ativ.getAbsolutePath() + "\\" + atividade.getTitulo() + ".dat");
		try {
			if (atividade_path.createNewFile()) {
				
				try (ObjectOutputStream perfilFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(atividade_path.getAbsolutePath())))){
					perfilFile.writeObject(atividade);
				}
				
				System.out.println("Atividade " + atividade.getTitulo() + " criado");
				
			} else {
				System.out.println("Atividade j� existe");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* ===================================================

	Metodo          - "removerArquivo"
	Descricao       - Deleta as informa��es do perfil especificado
	Entrada         - Uma string com o nome do perfil
	Processamento   - Deleta o arquivo de infora��es do perfil e sua pasta de atividades.
	Saida           -

	=================================================== */
	public void removerArquivo(String perfil) {
		File perfil_path = new File(this.end_info_perf.getAbsolutePath()+"\\"+perfil+".dat");
		if(perfil_path.exists()) {
			// Deleta o arquivo com as informa��es do perfil
			perfil_path.delete();
			
			File end_atividades = new File(end_info_ativ.getAbsolutePath()+"\\"+perfil);
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
			System.out.println("Perfil n�o existe");
		}
	}
	
	/* ===================================================

	Metodo          - "removerArquivo"
	Descricao       - Deleta um arquivo de atividade de um perfil.
	Entrada         - Uma String com o titulo da atividade. Uma String com o nome do perfil que possui a atividade.
	Processamento   - Verifica se a atividade com titulo passado por argumento existe na pasta de atividades vinculada ao perfil especificada. Se sim, deleta o arquivo.
	Saida           - 

	=================================================== */
	public void removerArquivo(String titulo_atividade, String perfil) {
		File atividade = new File(end_info_ativ.getAbsolutePath()+"\\"+perfil+"\\"+titulo_atividade+".dat");
		if(atividade.exists()) {
			atividade.delete();
		} else {
			System.out.println("Atividade "+titulo_atividade+" n�o existe.");
		}
	}
	
	/* ===================================================

	Metodo          - "removerPerfil"
	Descricao       - Deleta os dados de um perfil.
	Entrada         - Uma String com o nome do Perfil.
	Processamento   - (Pelo m�todo removerArquivo) Deleta o arquivo de infora��es do perfil e sua pasta de atividades.
	Saida           - 
	
	OBS : Fiz esse m�todo para ficar mais leg�vel em outras partes do c�digo quando eu quero especificamente remover um perfil.
	=================================================== */	
	public void removerPerfil(String perfil) {
		removerArquivo(perfil);
	}
}
