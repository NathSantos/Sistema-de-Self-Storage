import java.sql.*;

/**
 * Classe Banco de Dados - Cont�m atributos e m�todos para acessar o banco de dados
 * @author Nath�lia Santos &lt;nathsantos2703@gmail.com&gt;
 * @version 1.0, 2022-08-06
 */
public class BancoDeDados {
	/** 
	 * url para acessar o mysql 
	 * user do mysql
	 * pass (senha) do mysql
	 * conexao vai guardar o resultado da conex�o
	 */
	private static String url = "jdbc:mysql://localhost:3306/armazenamento_bd";
	private static String user = "root";
	private static String pass = "Nath2703";
	protected static Connection conexao = null; 
	// por ser est�tico, s� tem uma posi��o na mem�ria;
	// posso criar v�rios objetos da classe BancoDeDados mas apenas uma conexao ser� criada
	
	/**
	 * Construtor da classe.
	 * Verifica se o atributo conexao existe; 
	 * caso n�o exista, executa o comando conecta() para requisitar a conex�o ao banco de dados.
	 */
	public BancoDeDados() {
	    if (conexao == null) conecta();
	}
	
	/**
     * Executa a conex�o com o banco de dados
     * @return String 
     */
	private static String conecta() {
		try {
			conexao = DriverManager.getConnection(url, user, pass);
			return "Banco de dados conectado com sucesso!";
		} catch (SQLException e) { return "Erro ao se conectar com o banco de dados"; }
	}
	
	/**
     * Executa a desconex�o com o banco de dados
     * @return String 
     */
	public static String desconecta() {
		try {
			conexao.close();
			return "Banco de dados desconectado!";
		} catch (SQLException e) { return "Erro ao desconectar o banco de dados"; }
	}

}
