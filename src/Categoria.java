/**
 * Classe Categoria - Representa uma categoria de um local na aplica��o
 * @author Nath�lia Santos &lt;nathsantos2703@gmail.com&gt;
 * @version 1.0, 2022-08-06
 */
public class Categoria {
	/** Categoria do Local */
	private String categoria;
	
	/**
	 * Construtor da classe.
	 * @param categoria - categoria do local
	 */
	public Categoria(String categoria) {
	    this.categoria = categoria;
	}
	
	/**
     * Muda a categoria do local (void)
     * @param categoria - categoria do local
     */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	/**
     * Pega a categoria do local
     * @return String categoria
     */
	public String getCategoria() {
		return categoria;
	}
	
	/**
     * Pega o custo por m�s da categoria selecionada
     * @return int custo por m�s
     */
	public int getCusto() {
		int custo = 0;
		if (categoria == "guarda-volumes") {
			custo = 100;
		}
		else if (categoria == "cont�iner") {
			custo = 350;
		}
		else if (categoria == "quarto") {
			custo = 600;
		}
		else if (categoria == "galp�o") {
			custo = 1000;
		}
		return custo;
	}
	
	/**
     * Pega o comprimento (em metros) da categoria selecionada
     * @return int comprimento
     */
	public int getComprimento() {
		int comprimento = 0;
		if (categoria == "guarda-volumes") {
			comprimento = 1;
		}
		else if (categoria == "cont�iner") {
			comprimento = 6;
		}
		else if (categoria == "quarto") {
			comprimento = 12;
		}
		else if (categoria == "galp�o") {
			comprimento = 50;
		}
		return comprimento;
	}
	
	/**
     * Pega a largura (em metros) da categoria selecionada
     * @return int largura
     */
	public int getLargura() {
		int largura = 0;
		if (categoria == "guarda-volumes") {
			largura = 1;
		}
		else if (categoria == "cont�iner") {
			largura = 3;
		}
		else if (categoria == "quarto") {
			largura = 12;
		}
		else if (categoria == "galp�o") {
			largura = 50;
		}
		return largura;
	}
	
	
	/**
     * Pega a altura (em metros) da categoria selecionada
     * @return int altura
     */
	public int getAltura() {
		int altura = 0;
		if (categoria == "guarda-volumes") {
			altura = 1;
		}
		else if (categoria == "cont�iner") {
			altura = 3;
		}
		else if (categoria == "quarto") {
			altura = 3;
		}
		else if (categoria == "galp�o") {
			altura = 10;
		}
		return altura;
	}
	
	/**
     * Pega o volume (em metros c�bicos) da categoria selecionada
     * @return int volume
     */
	public int getVolume() {
		int volume = getComprimento() * getAltura() * getLargura();
		return volume;
	}
}	

