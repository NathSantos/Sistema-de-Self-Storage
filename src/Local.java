/**
 * Classe Local - Representa um local na aplica��o
 * @author Nath�lia Santos &lt;nathsantos2703@gmail.com&gt;
 * @version 1.0, 2022-08-06
 */
public class Local {
	/** 
	 * n�mero do local (identifica a categoria do local)
	 * ------ TABELA DE N�MEROS POR CATEGORIA -------
	 * 	- guarda-volume -> {n�m. 01 a 20} - 20 unidades
	 *  - cont�iner -> {n�m. 21 a 50} - 30 unidades
	 *  - quarto -> {n�m. 51 a 90} - 40 unidades
	 *  - galp�o -> {n�m. 91 a 100} - 10 unidades
	 * ---------------------------------------------- 
	 */
	private int numero;
	
	/**
	 * Construtor da classe.
	 * @param numero - n�mero do local
	 */
	public Local(int numero) {
	    this.numero = numero;
	}
	
	/**
     * Pega a categoria (classe Categoria) do local
     * @return Categoria categoria
     */
	public Categoria getCategoria() {
		String categoria = null;
		if (numero >= 1 && numero <= 20) {
			categoria = "guarda-volumes";
		}
		else if (numero >= 21 && numero <= 50) {
			categoria = "cont�iner";
		}
		else if (numero >= 51 && numero <= 90) {
			categoria = "quarto";
		}
		else if (numero >= 91 && numero <= 100) {
			categoria = "galp�o";
		}
		Categoria cat = new Categoria(categoria);
		return cat;
	}
	
	/**
     * Pega o endere�o do local (todos possuem o mesmo endere�o)
     * @return String endere�o
     */
	public String getEndereco() {
		return "Rua Malt�s, bloco 54";
	}
	
	/**
     * Muda o n�mero do local (void)
     * @param numero - n�mero do local
     */
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	/**
     * Pega o n�mero do local
     * @return int numero
     */
	public int getNumero() {
		return numero;
	}	
}
