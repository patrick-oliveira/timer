package gerenciador_arquivos;
import perfil.Perfil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import atividade.Atividade;

public class Leitor extends GerenciadorPrincipal {
	
	
	public Leitor() {
		super();
		//System.out.println("Construtor do Leitor invocado.");
	}
	
	public void listarPerfis() {
		File[] arquivos = this.getEnderecoPerfil().listFiles();
		
		for(int i = 0; i < arquivos.length; i++) {
			System.out.println(arquivos[i].getName().replace(".dat", ""));
		}	
	}
	
	public void listarAtividades() {
		File[] arquivos = this.getEnderecoAtividade().listFiles();
		
		for(int i = 0; i < arquivos.length; i++) {
			System.out.println(arquivos[i].getName().replace(".dat", ""));
		}
	}
	
	public void listarAtividades(String perfil) {
		File perfil_path = new File(this.getEnderecoAtividade().getAbsolutePath()+"\\"+perfil);
		if(perfil_path.exists()) {
			File[] arquivos = perfil_path.listFiles();
			
			for(int i = 0; i < arquivos.length; i++) {
				System.out.println(arquivos[i].getName().replace(".dat", ""));
			}
		}
	}
	
	public Perfil lerPerfil(String nome_perfil) {
		File perfil_path = new File(this.getEnderecoPerfil().getAbsolutePath()+"\\"+nome_perfil+".dat");
		if(perfil_path.exists()) {
			try(ObjectInputStream perfilFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(perfil_path.getAbsolutePath())))){
				Perfil perfil_obj = (Perfil)perfilFile.readObject();
				return perfil_obj;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException ce) {
				ce.printStackTrace();
			} catch (NullPointerException ne) {
				ne.printStackTrace();
			}
		}
		// Talvez isso cause problemas. Faça um try/catch onde o método é invocado ou arrume isso aqui.
		return null;
	}
	
	public Atividade lerAtividade(String titulo_atividade, String nome_perfil) {
		File perfil_path = new File(this.getEnderecoAtividade().getAbsolutePath()+"\\"+nome_perfil);
		if(perfil_path.exists()) {
			try (ObjectInputStream atividadeFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream(perfil_path.getAbsolutePath()+"\\"+titulo_atividade+".dat")))){
				Atividade atividade_obj = (Atividade)atividadeFile.readObject();
				return atividade_obj;
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException ce) {
				ce.printStackTrace();
			} catch (NullPointerException ne) {
				ne.printStackTrace();
			}
		}
		// Talvez isso cause problemas. Faça um try/catch onde o método é invocado ou arrume isso aqui.
		return null;
	}
	
	public String getTitulo(String atividade, String nome_perfil) {
		return lerAtividade(atividade, nome_perfil).getTitulo();
	}
	
	public String getDescricao(String atividade, String nome_perfil) {
		return  lerAtividade(atividade, nome_perfil).getDescricao();
	}
	
	public int getDuracao(String atividade, String nome_perfil) {
		return lerAtividade(atividade, nome_perfil).getDuracao();
	}
	
	public int getDescanso(String atividade, String nome_perfil) {
		return lerAtividade(atividade, nome_perfil).getDescanso();
	}
}
