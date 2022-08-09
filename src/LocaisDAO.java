import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Classe LocaisDAO - Classe intermedi�ria entre as tabelas locaisTotais, locaisDisponiveis, locaisAlugados (mysql) e as classes Cliente, Categoria, Local, infoAluguel, Opcional (java)
 * !!! � NECESS�RIO EXECUTAR ESSA CLASSE PELA PRIMEIRA AP�S TER CRIADO O BANCO DE DADOS PARA PREENCHER A TABELA LOCAIS TOTAIS E LOCAIS DISPON�VEIS !!!
 * @author Nath�lia Santos &lt;nathsantos2703@gmail.com&gt;
 * @version 1.0, 2022-08-06
 */
public class LocaisDAO extends BancoDeDados{
	
	// ---------------------- TABELA LOCAIS TOTAIS (DISP + ALUGADO) ----------------------------
	
	/**
     * Adiciona os 100 locais padr�o na tabela locaisTotais
     * Executado somente na primeira vez para preencher a tabela
     * A tabela locaisTotais nunca ser� alterada, ela apenas guarda todos os 100 locais e suas respectivas categorias
     * @return boolean 
     */
	public boolean adicionarLocaisTotais() {
	    try {
	      Statement st = conexao.createStatement();
	      for(int i=1;i<=100;i++) {
	    	  Local loc = new Local(i);
	    	  Categoria cat = loc.getCategoria();
	    	  st.executeUpdate("INSERT INTO locaisTotais VALUES ('" + loc.getNumero() + "'," + " '" + 
	    			  			cat.getCategoria() + "', '" + loc.getEndereco() + "')");
	      }
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Lista os locais na tabela locaisTotais, de acordo com: N�mero, Categoria e Endere�o (padr�o)
     * @return String relat�rio dos locais totais
     */
	public String listarLocaisTotais() {
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisTotais");
			String relatorioTotais = "";
			while (rs.next()) {
				relatorioTotais += "N�mero " + rs.getString(1) + ", Categoria: " + rs.getString(2) +
									", Endere�o: " + rs.getString(3) + "\n";
			}
			return relatorioTotais;
	    }
	    catch (SQLException e) { return null; }
	}
	
	
	// -------------------------- TABELA LOCAIS DISPON�VEIS ------------------------------------
	
	/**
     * Adiciona os 100 locais da tabela locaisTotais na tabela locaisDisponiveis
     * Executado somente na primeira vez para preencher a tabela
     * A tabela locaisDisponiveis pode sofrer altera��es conforme os alugu�is forem sendo feitos
     * @return boolean 
     */	
	public boolean adicionarLocaisDisponiveis() {
	    try {
	      Statement st = conexao.createStatement();
	      st.executeUpdate("INSERT INTO locaisDisponiveis (numero, categoria) " +
	    		  						"SELECT numero, categoria FROM locaisTotais");
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Adiciona um local na tabela locaisDisponiveis
     * @param numero - numero do local
     * @param categoria - categoria do local
     * @return boolean 
     */	
	public boolean adicionarDisponivel(String numero, String categoria) {
	    try {
	      Statement st = conexao.createStatement();
	      st.executeUpdate("INSERT INTO locaisDisponiveis VALUES ('" + numero + "'," + " '" + 
					categoria + "')");
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Remove um local da tabela locaisDisponiveis
     * @param numero - numero do local
     * @return boolean 
     */	
	public boolean removerLocalDisponivel(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      st.executeUpdate("DELETE FROM locaisDisponiveis WHERE numero='" + numero + "'");
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Lista os locais na tabela locaisDisponiveis, de acordo com: N�mero e Categoria
     * @return String relat�rio dos locais dispon�veis
     */
	public String listarLocaisDisponiveis() {
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisDisponiveis");	
			int quant = 0;
			String relatorioDisponiveis = "";
			while (rs.next()) {
				relatorioDisponiveis += "N " + rs.getString(1) + ", Categoria: " 
									+ rs.getString(2) + "\n";
				quant++;
			}
			relatorioDisponiveis += "-> Quantidade de Locais Dispon�veis: " + quant;
			return relatorioDisponiveis;
	    }
	    catch (SQLException e) { return null; }
	}
	
	/**
     * Pega um local da tabela locaisDisponiveis de acordo com o n�mero
     * Retorna true caso ache o local e false caso contr�rio
     * @param numero - n�mero do local
     * @return boolean
     */
	public boolean getLocalDisponivel(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      ResultSet rs = st.executeQuery("SELECT * FROM locaisDisponiveis WHERE numero='" + numero + "'");
	      if (rs.next()) {
	    	  return true;
	      }
	      else {
	    	  return false;
	      }
	    }
	    catch (SQLException e) { return false; }
	}
	
	/**
     * Pega a quantidade de locais dispon�veis 
     * @return int quantidade de locais dispon�veis
     */
	public int quantDisponiveis() {
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisDisponiveis");	
			int quant = 0;
			while (rs.next()) {
				quant++;
			}
			return quant;
	    }
	    catch (SQLException e) { return -1; }
	}
	
	/**
     * Gera a tabela de locais dispon�veis que vai ser mostrada na interface gr�fica
     * @return String[][] tabela
     */
	public String[][] tabelaDisponiveis() {
		String[][] data = new String[quantDisponiveis()][2]; // [rows][columns]
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisDisponiveis ORDER BY numero ASC");	
			int i = 0;
			while (rs.next()) {
				for(int j=0; j < 2; j++) {
					data[i][j] = rs.getString(j+1);
				}
				i=i+1;
			}
			return data;
	    }
	    catch (SQLException e) { System.out.println(e); }
		return data;
	}


	// ----------------------------- TABELA LOCAIS ALUGADOS ------------------------------------
	
	/**
     * Pega a quantidade de locais alugados 
     * @return int quantidade de locais alugados
     */
	public int quantAlugados() {
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisAlugados");	
			int quant = 0;
			while (rs.next()) {
				quant++;
			}
			return quant;
	    }
	    catch (SQLException e) { return -1; }
	}
	
	/**
     * Lista os locais na tabela locaisAlugados de acordo com todas as informa��es de todos os alugu�is
     * @return String relat�rio dos locais alugados
     */
	public String listarLocaisAlugados() {		
		try {
			ClienteDAO clienteBD = new ClienteDAO();
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisAlugados ORDER BY numero ASC");
			int i = 1;
			String relatorio = "";
			while (rs.next()) {
				relatorio += i + ". N�m: " + rs.getString(1) + ", Categoria: " + rs.getString(2) + 
						"\nNome do Cliente: " + clienteBD.getNomePorCpf(rs.getString(3)) + ", CPF: " + rs.getString(3) + 
						"\nIn�cio do Aluguel: " + rs.getString(4) +
						"\nT�rmino do Aluguel: " + rs.getString(5) + 
						"\nCusto do Aluguel por M�s: R$" + rs.getString(6) + ",00" +
						"\nPer�odo de Aluguel: " + rs.getString(7) + " meses" +
						"\nCusto Aluguel Local: R$" + rs.getString(8) + ",00" +
						"\nCusto Opcionais: R$" + rs.getString(9) + ",00" +
						"\nCusto Total: R$" + rs.getString(10) + ",00" +
						"\n----------------------------------------------\n";
				
				i++;
			}
			return relatorio;
	    }
	    catch (SQLException e) { return null;}
	}
	
	/**
     * Adiciona um local na tabela locaisAlugados
     * @param numero - numero do local
     * @param categoria - categoria do local
     * @param cpf - cpf do cliente que alugou o local
     * @param inicio - data de in�cio do aluguel
     * @param termino - data de t�rmino do aluguel
     * @param custoPorMes - custo por m�s do local
     * @param meses - quantidade de meses de aluguel
     * @param custoLoc - custo total do local (custoPorMes x meses)
     * @param custoOpc - custo total dos opcionais selecionados
     * @param custoTotal - custo total do aluguel (custoLoc + custoOpc)
     * @return boolean 
     */	
	public boolean adicionarAlugado(String numero, String categoria, String cpf, String inicio, String termino, int custoPorMes, int meses, int custoLoc, int custoOpc, int custoTotal) {
	    try {
	      Statement st = conexao.createStatement();
	      st.executeUpdate("INSERT INTO locaisAlugados VALUES ('" + numero + "', '" + 
					categoria + "', '" + cpf + "', '" + inicio + "', '" + termino + "', '" + custoPorMes + "', '"
					+ meses + "', '" + custoLoc + "', '" + custoOpc + "', '" + custoTotal + "')");
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Procura disponibilidade para a categoria do local escolhido
     * Caso tenha disponibilidade, o local � alugado
     * @param cpf - cpf do cliente que alugou o local
     * @param categoria - categoria do local
     * @param inicio - data de in�cio do aluguel
     * @param termino - data de t�rmino do aluguel
     * @param seguro - (1 ou 0) se o cliente escolheu o seguro ou n�o
     * @param chave - (1 ou 0) se o cliente escolheu a chave extra ou n�o
     * @param controle - (1 ou 0) se o cliente escolheu o controle clim�tico ou n�o
     * @return boolean 
     */	
	public boolean procuraDisponibilidadeEAluga(String cpf, String categoria, String inicio, String termino, int seguro, int chave, int controle) {
	    try {
	    	Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisDisponiveis WHERE categoria='"+ categoria + 
											"' ORDER BY numero ASC limit 1");
			
			Categoria cat = new Categoria(categoria);
			Opcionais opc = new Opcionais(seguro, chave, controle);
			int custoPorMes = cat.getCusto();
			int meses = calculaMeses(inicio, termino);
			int custoLocal = custoPorMes * meses;
			int custoTotal = custoLocal + opc.getCustoOpcionais();
			
			while (rs.next()) {
				
				// adicionando nos Locais Alugados 
				adicionarAlugado(rs.getString(1), rs.getString(2), cpf, inicio, termino, custoPorMes, meses, custoLocal, opc.getCustoOpcionais(), custoTotal);
				
				// removendo dos Locais Dispon�veis
				removerLocalDisponivel(rs.getString(1));
			}	
			return true;
	    } 
		catch (SQLException e) { return false; }
	}
	
	/**
     * Calcula a quantidade de meses de aluguel
     * O custo m�nimo de aluguel � cobrado por 1 m�s, mesmo que o per�odo de aluguel seja menor que 1 m�s
     * @param inicio - data de in�cio do aluguel
     * @param termino - data de t�rmino do aluguel
     * @return int meses 
     */	
	public int calculaMeses(String inicio, String termino) { // "yyyy-MM-dd"
		
		// String -> Date
		Date dataInicio = null;
		try {
			dataInicio = new SimpleDateFormat("yyyy-MM-dd").parse(inicio);
		} catch (ParseException e1) {e1.printStackTrace();}
		
		Date dataTermino = null;
		try {
			dataTermino = new SimpleDateFormat("yyyy-MM-dd").parse(termino);
		} catch (ParseException e1) {e1.printStackTrace();}
		
		// Date -> Calendar (define datas)
		Calendar dateIni = Calendar.getInstance();
		dateIni.setTime(dataInicio);
		Calendar dateEnd = Calendar.getInstance();
		dateEnd.setTime(dataTermino);

		// Calcula diferen�a
		int meses = (dateEnd.get(Calendar.YEAR) * 12 + dateEnd.get(Calendar.MONTH))
		        - (dateIni.get(Calendar.YEAR) * 12 + dateIni.get(Calendar.MONTH));
		
		// O menor valor poss�vel ser� o de 1 m�s de aluguel, mesmo que a diferen�a de datas seja menor que 30 dias
		if (meses == 0) {
			meses = 1;
		}
		
		return meses;
	}
 	
	/**
     * Cancela o aluguel do local de acordo com o n�mero do local e cpf do cliente que fez o aluguel
     * O local � removido da tabela locaisAlugados e adicionado na tabela locaisDisponiveis
     * @param cpf - cpf do cliente que alugou o local
     * @param numero - numero do local
     * @return boolean 
     */	
	public boolean cancelaAluguel(String cpf, String numero) {
	    try {
	    	Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisAlugados WHERE numero='"+ numero + 
											"' AND cpf='" + cpf + "' ORDER BY numero ASC limit 1");	
			if (rs.next()) {				
				// removendo dos Locais Alugados
				removerLocalAlugado(rs.getString(1));
				
				// adicionando nos Locais Dispon�veis 
				adicionarDisponivel(rs.getString(1), rs.getString(2));	
				
				return true;
			}	
	    } 
		catch (SQLException e) { return false; }
		return false;
	}
	
	/**
     * Altera as datas de in�cio e t�rmino de um aluguel
     * @param cpf - cpf do cliente que alugou o local
     * @param numero - n�mero do local
     * @param inicio - data de in�cio do aluguel
     * @param termino - data de t�rmino do aluguel
     * @return boolean 
     */	
	public boolean alteraDataAluguel(String cpf, String numero, String inicio, String termino) {
	    try {
	      Statement st = conexao.createStatement();
	      
	      int meses = calculaMeses(inicio, termino);
	      int custoPorMes = 0;
	      int custoOpcionais = 0;
	      
	      ResultSet rs = st.executeQuery("SELECT custoPorMes, custoOpcionais FROM locaisAlugados WHERE numero='" + numero + "'");
	      if (rs.next()) {
	    	  custoPorMes = rs.getInt(1);
	    	  custoOpcionais = rs.getInt(2);
	      }
	      int custoLocal = custoPorMes * meses;
	      int custoTotal = custoOpcionais + custoLocal;
	      
	      st.executeUpdate("UPDATE locaisAlugados SET dataInicio='" + inicio + "', dataTermino='" + termino + 
	    		  "', meses='" + meses + "', custoLocal='" + custoLocal + "', custoTotal='" + custoTotal +
	    		  "' WHERE numero=" + numero + " AND cpf='" + cpf + "'");
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Remove um local da tabela locaisAlugados
     * @param numero - n�mero do local
     * @return boolean 
     */	
	public boolean removerLocalAlugado(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      st.executeUpdate("DELETE FROM locaisAlugados WHERE numero='" + numero + "'");
	      return true;
	    } catch (SQLException e) { return false; }
	}
	
	/**
     * Pega um local da tabela locaisAlugados de acordo com o n�mero
     * Retorna true caso ache o local e false caso contr�rio
     * @param numero - n�mero do local
     * @return boolean
     */
	public boolean getLocalAlugado(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      ResultSet rs = st.executeQuery("SELECT * FROM locaisAlugados WHERE numero='" + numero + "'");
	      if (rs.next()) {
	    	  return true;
	      }
	      else {return false;}
	    }
	    catch (SQLException e) { return false; }
	}
	
	/**
     * Pega o CPF do cliente que realizou determinado aluguel de acordo com o n�mero do local
     * @param numero - n�mero do local
     * @return String cpf do cliente
     */
	public String getCpfPorNumeroAluguel(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      ResultSet rs = st.executeQuery("SELECT cpf FROM locaisAlugados WHERE numero='" + numero + "'");
	      if (rs.next()) {
	    	  return rs.getString(1);
	      }
	      else { return "nenhum cpf registrado neste n�mero de aluguel!"; }
	    }
	    catch (SQLException e) { return null; }
	}
	
	/**
     * Pega a categoria do local de acordo com o n�mero 
     * @param numero - n�mero do local
     * @return String categoria do local
     */
	public String getCategoriaPorNumero(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      ResultSet rs = st.executeQuery("SELECT categoria FROM locaisAlugados WHERE numero='" + numero + "'");
	      if (rs.next()) {
	    	  return rs.getString(1);
	      }
	      else { return "n�o foi poss�vel achar uma categoria"; }
	    }
	    catch (SQLException e) { return null; }
	}
	
	/**
     * Gera a tabela de locais alugados que vai ser mostrado na interface gr�fica
     * @return String[][] tabela
     */
	public String[][] tabelaAlugados() {
		String[][] data = new String[quantAlugados()][10]; // [rows][columns]
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisAlugados ORDER BY numero ASC");
			int i = 0;
			while (rs.next()) {
				for(int j=0; j < 10; j++) {
					data[i][j] = rs.getString(j+1);
				}
				i=i+1;
			}
			return data;
	    }
	    catch (SQLException e) { System.out.println(e); }
		return data;
	}
	
	/**
     * Gera a tabela de locais alugados por cpf de cliente que vai ser mostrado na interface gr�fica
     * @return String[][] tabela
     */
	public String[][] tabelaAlugadosPorCpf(String cpf) {
		String[][] data = new String[quantAlugados()][10]; // [rows][columns]
		try {
			Statement st = conexao.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM locaisAlugados WHERE cpf='" + cpf + "'");	
			int i = 0;
			int k = 0;
			while (rs.next()) {
				for(int j=0; j < 10; j++) {
					// selecionando TODAS as colunas EXCETO a CPF
					if(j != 2) {
						data[i][k] = rs.getString(j+1);
						k++;
					}
				}
				k=0;
				i=i+1;
			}
			return data;
	    }
	    catch (SQLException e) { System.out.println(e); }
		return data;
	}
	
	
	// ----------------------------- OPCIONAIS ------------------------------------
	
	/**
     * Pega o custo total dos opcionais de um aluguel acordo com o n�mero do local
     * @param numero - n�mero do local
     * @return int custo dos opcionais
     */
	public int getCustoOpcionaisPorNumero(String numero) {
	    try {
	      Statement st = conexao.createStatement();
	      ResultSet rs = st.executeQuery("SELECT custoOpcionais FROM locaisAlugados WHERE numero='" + numero + "'");
	      if (rs.next()) {
	    	  return rs.getInt(1);
	      }
	      else { return -1; }
	    }
	    catch (SQLException e) { return -1; }
	}
	
	/**
     * Lista os opcionais selecionados e seus devidos custos no fechamento de conta do aluguel
     * @param seguro - (1 ou 0) se o cliente escolheu o seguro ou n�o
     * @param chave - (1 ou 0) se o cliente escolheu a chave extra ou n�o
     * @param controle - (1 ou 0) se o cliente escolheu o controle clim�tico ou n�o
     * @return String relatorio opcionais 
     */
	public String listarOpcionaisFechamentoDeConta(int seguro, int chave, int controle) {
		int i = 0;
		String opc = "\nOpcionais Selecionados: ";
		if (seguro == 1) {
			i++;
			opc += "\n   " + i + ". Seguro: R$ 250,00";
		}
		if (chave == 1) {
			i++;
			opc += "\n   " + i + ". Chave Extra: R$ 50,00";
		}
		if (controle == 1) {
			i++;
			opc += "\n   " + i + ". Controle Clim�tico: R$ 200,00";
		}
		if (i == 0) {
			opc += "-----";
		}
		return opc;
	}
	
	public static void main(String args[]) {
		// EXECUTAR O PROGRAMA PELA PRIMEIRA VEZ PARA PREENCHER A TABELA DE LOCAIS DISPON�VEIS COM OS LOCAIS DISPON�VEIS PARA ALUGUEL
		LocaisDAO locaisTotais = new LocaisDAO();
		locaisTotais.adicionarLocaisTotais();
		locaisTotais.adicionarLocaisDisponiveis();
	}
}
