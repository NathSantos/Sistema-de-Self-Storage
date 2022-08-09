import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Classe infoAluguel - Representa informa��es sobre um aluguel na aplica��o
 * @author Nath�lia Santos &lt;nathsantos2703@gmail.com&gt;
 * @version 1.0, 2022-08-06
 */
public class infoAluguel {
	/** 
	 * data de in�cio do aluguel ("yyyy-MM-dd")
	 * data de t�rmino do aluguel ("yyyy-MM-dd")
	 * categoria do local
	 */
	private String dataInicio;
	private String dataTermino;
	private String categoria;
	
	/**
	 * Construtor da classe.
	 * @param dataInicio - data de in�cio do aluguel ("yyyy-MM-dd")
	 * @param dataTermino - data de t�rmino do aluguel ("yyyy-MM-dd")
	 * @param categoria - categoria do local
	 */
	public infoAluguel(String dataInicio, String dataTermino, String categoria) {
	    this.dataInicio = dataInicio;
	    this.dataTermino = dataTermino;
	    this.categoria = categoria;
	}
	
	/**
     * Converte a data de in�cio do tipo String para o tipo Date
     * @return Date data de in�cio
     */
	public Date converteInicio() throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date dataFormatada = formato.parse(dataInicio);
		return dataFormatada; 
	}
	
	/**
     * Converte a data de t�rmino do tipo String para o tipo Date
     * @return Date data de t�rmino
     */
	public Date converteTermino() throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date dataFormatada = formato.parse(dataTermino);
		return dataFormatada; 
	}
	
	/**
     * Pega a data de in�cio do aluguel 
     * @return String data de in�cio
     */
	public String getDataInicio() {
		return dataInicio;
	}
	
	/**
     * Muda a data de in�cio do aluguel (void)
     * @param dataInicio - data de in�cio do aluguel 
     */
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	/**
     * Pega a data de t�rmino do aluguel 
     * @return String data de t�rmino
     */
	public String getDataTermino() {
		return dataTermino;
	}
	
	/**
     * Muda a data de t�rmino do aluguel (void)
     * @param dataTermino - data de t�rmino do aluguel 
     */
	public void setDataTermino(String dataTermino) {
		this.dataTermino = dataTermino;
	}
	
	/**
     * Pega a categoria do local
     * @return String categoria
     */
	public String getCategoria() {
		return categoria;
	}
	
	/**
     * Muda a categoria do local (void)
     * @param categoria - categoria do local 
     */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
