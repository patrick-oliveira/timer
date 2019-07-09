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
	private File end_info_perf; // Endereço da pasta com os arquivos dos perfis
	private File end_info_ativ; // Endereço da pasta com as atividades (posteriormente vinculada à pasta do perfil especificado)
	private File perfil; // Endereço do arquivo de perfil
	
	
	
	/* ===================================================

	Metodo          - "GerenciadorPrincipal"
	Descricao       - Construtor da classe.
	Entrada         - 
	Processamento   - Cria os caminhos para as pastas. Verifica se os diretórios existem, e os cria caso não existam.
	Saida           -

	 =================================================== */
	public GerenciadorPrincipal() {
		DefaultConfig();
	}
	
	// Posso trocar isso para Static depois
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

		setEndPer(pasta_perfis);
		setEndAtiv(pasta_atividades);
		
		//System.out.println(this.end_info_ativ.toString());
		//System.out.println(this.end_info_perf.toString());
		
		// Edite isso aqui para que as extensões dos arquivos não sejam listados.
		//listarArquivos(end_info_perf);		
	}
	
	
	/* ===================================================

	Metodo          - "listarArquivos"
	Descricao       - Retorna o título de cada arquivo do diretório passado como argumento.
	Entrada         - Um tipo "File" vinculado a um diretório.
	Processamento   - Faz uma varredura no endereço contido no objeto "File", imprimindo o nome de cada arquivo no diretório.
	Saida           -

	=================================================== */
	public void listarArquivos(File pasta) {
		for(File fileEntry : pasta.listFiles()) {
			System.out.println(fileEntry.getName());
		}
	}
	
	/* ===================================================

	Metodo          - "setEndAtiv"
	Descricao       - Modifica o endereço da pasta de atividades vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um diretório.
	Processamento   - Altera o atributo "end_info_ativ" com o tipo "File" vinculado ao endereço da pasta de Atividades.
	Saida           -

	=================================================== */
	public void setEndAtiv(File novo_endereco) {
		this.end_info_ativ = novo_endereco;
	}

	/* ===================================================

	Metodo          - "setEndPer"
	Descricao       - Modifica o endereço da pasta de perfis vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um diretório.
	Processamento   - Altera o atributo "end_info_perf" com o tipo "File" vinculado ao endereço da pasta de Perfis.
	Saida           -

	=================================================== */
	public void setEndPer(File novo_endereco) {
		this.end_info_perf = novo_endereco;
	}
	
	
	public File getEndPer() {
		return this.end_info_perf;
	}
	
	public File getEndAtiv() {
		return this.end_info_ativ;
	}
	
	public File getPerfil() {
		return this.perfil;
	}
	
	/* ===================================================

	Metodo          - "setPerfil"
	Descricao       - Modifica o endereço do arquivo de Perfil vinculado ao Gerenciador.
	Entrada         - Um tipo "File" vinculado a um arquivo.
	Processamento   - Altera o atributo "perfil" com o tipo "File" vinculado ao arquivo do perfil que será vinculado.
	Saida           -

	=================================================== */
	public void setPerfil(File perfil) {
		this.perfil = perfil;
	}
	
	/* ===================================================

	Metodo          - "associaPerfil"
	Descricao       - Faz a associação entre um perfil e o Gerenciador.
	Entrada         - Um tipo "Perfil".
	Processamento   - Modifica o atributo "perfil" com o tipo "File" que contém o endereço do arquivo vinculado a um perfil.
	Saida           - Um tipo "Perfil" com os dados contidos no arquivo do endereço "perfil".

	=================================================== */
	public Perfil associaPerfil(String perfil) {
		DefaultConfig();
		File Perfil_path = gerarArquivo(perfil);
		setPerfil(Perfil_path);
		
		setEndAtiv(new File(this.end_info_ativ.getAbsolutePath()+ "\\" +perfil));
		if (!this.end_info_ativ.exists())
			this.end_info_ativ.mkdir();
		System.out.println(this.end_info_ativ.toString());
		return (new Leitor()).lerPerfil(perfil);
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
	Descricao       - Gera um arquivo do tipo especificado como argumento.
	Entrada         - Um tipo "Perfil"
	Processamento   - Gera um caminho para um arquivo ".dat" nomeado com o nome do Perfil passado como argumento. Verifica sua existência, e o cria caso não exista.
	Saida           - Um tipo "File" contendo o endereço do arquivo vinculado ao Perfil.

	=================================================== */
	public File gerarArquivo(String Perfil) {
		File Perfil_path = new File(end_info_perf.getAbsolutePath() + "\\" + Perfil + ".dat");
		try {
			if (Perfil_path.createNewFile()) {
				
				try (ObjectOutputStream perfilFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(Perfil_path.getAbsolutePath())))){
					perfilFile.writeObject(new Perfil(Perfil));
				}
			
				System.out.println("Perfil " + Perfil + " criado");
				
			} else {
				System.out.println("Perfil já existe");
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
	Processamento   - Gera um caminho para um arquivo ".dat" nomeado com o titulo da Atividade passada como argumento. Verifica sua existência, e o cria caso não exista.
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
				System.out.println("Atividade já existe");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* ===================================================

	Metodo          - "removerArquivo"
	Descricao       - Deleta as informações do perfil
	Entrada         - 
	Processamento   - Deleta o arquivo de inforações do perfil, sua pasta de atividades e reinicia as configurações do Gerenciador.
	Saida           -

	=================================================== */
	public void removerArquivo() {
		if(perfil.exists()) {
			// Deleta o arquivo com as informações do perfil
			perfil.delete();
			
			// Deleta todas as atividades vinculadas ao perfil
			File[] arquivos = end_info_ativ.listFiles();
			for(int i = 0; i < arquivos.length; i++) {
				if(arquivos[i].isFile()) {
					removerArquivo(arquivos[i].getName().replace(".dat", ""));
				}
			}
			
			// Deleta a pasta de atividades do perfil
			end_info_ativ.delete();
			
			// Reinicia o gerenciador para configuração padrão
			DefaultConfig();
		}
	}
	
	/* ===================================================

	Metodo          - "removerArquivo"
	Descricao       - Deleta um arquivo de atividade.
	Entrada         - Uma String com o titulo da atividade.
	Processamento   - Verifica se a atividade com titulo passado por argumento existe na pasta de atividades vinculada ao perfil pré-definido. Se sim, deleta o arquivo.
	Saida           - 

	=================================================== */
	public void removerArquivo(String titulo_atividade) {
		File atividade = new File(end_info_ativ.getAbsolutePath()+"\\"+titulo_atividade+".dat");
		if(atividade.exists()) {
			atividade.delete();
		} else {
			System.out.println("Atividade "+titulo_atividade+" não existe.");
		}
	}
}
