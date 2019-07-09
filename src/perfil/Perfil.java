package perfil;

import java.io.Serializable;

public class Perfil implements Serializable {
	String nome;
	
	public Perfil (String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
