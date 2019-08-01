package utilidades;

public class Utilidades {
	
	/* ===================================================

	Metodo          - secParaHMS
	Descricao       - Converte o tempo em segundos para o formato
					00:00:00
	Entrada         - Um inteiro com o tempo em segundos.
	Processamento   - 
	Saida           - Um vetor de Integers de tamanho 3 com
					as horas, minutos e segundos.

	=================================================== */
	public static Integer[] secParaHMS(int tempo) {
		Integer[] hms = new Integer[3];
		hms[0] = tempo/3600;
		hms[1] = (tempo%3600)/60;
		hms[2] = (tempo%3600)%60;
		return hms;
	}
	
	/* ===================================================

	Metodo          - hmsParaSec
	Descricao       - Metodo sobrecarregado. Converte um tempo no formato HH:MM:SS para segundos.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	public static int hmsParaSec(int hr, int min, int sec) {
		return hr*3600 + min*60 + sec;
	}
	
	/* ===================================================

	Metodo          - initialize
	Descricao       - Metodo sobrecarregado. Converte um tempo no formato MM:SS para segundos.
	Entrada         - 
	Processamento   - 
	Saida           - 

	=================================================== */	
	public static int hmsParaSec(int min, int sec) {
		return min*60 + sec;
	}
	
	/* ===================================================

	Metodo          - tempoToString
	Descricao       - Gera uma String com o tempo no formato
					00:00:00
	Entrada         - Um vetor de Integers com as horas, minutos
					e segundos.
	Processamento   - Instancia um construtor de strings que converte
					os valores do vetor de Integers adequadamente para 
					apresentalos como 00:00:00.
	Saida           - Uma string com o tempo;

	=================================================== */	
	public static String tempoToString(Integer[] hms) {
		StringBuilder construtor = new StringBuilder();
		if(hms[0] <= 9) {
			construtor.append("0"+hms[0].toString()+":");
		} else {
			construtor.append(hms[0].toString()+":");
		}
		if(hms[1] <= 9) {
			construtor.append("0"+hms[1].toString()+":");
		} else {
			construtor.append(hms[1].toString()+":");
		}
		if(hms[2] <= 9) {
			construtor.append("0"+hms[2].toString());
		} else {
			construtor.append(hms[2].toString());
		}
		return construtor.toString();
	}
}
