package Classes;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexao.Conexao;

public class FinLanClass {

	private int idlan;
	private String numeroDocumento;
	private int codCxa;
	private int codCfo;
	private int codTdo;
	private int idXcx;
	private String dataEmissao;
	private String dataVencimento;
	private double valorOriginal;
	private double valorJuros;
	private double valormulta;
	private double valordesconto;
	private int pagRec;
	private int statusLan;
	private String dataBaixa;
	private double valorBaixa;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public int getIdlan() {
		return idlan;
	}

	public void setIdlan(int idlan) {
		this.idlan = idlan;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public int getCodCxa() {
		return codCxa;
	}

	public void setCodCxa(int codCxa) {
		this.codCxa = codCxa;
	}

	public int getCodCfo() {
		return codCfo;
	}

	public void setCodCfo(int codCfo) {
		this.codCfo = codCfo;
	}

	public int getCodTdo() {
		return codTdo;
	}

	public void setCodTdo(int codTdo) {
		this.codTdo = codTdo;
	}

	public int getIdXcx() {
		return idXcx;
	}

	public void setIdXcx(int idXcx) {
		this.idXcx = idXcx;
	}

	public String getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(String dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public double getValorOriginal() {
		return valorOriginal;
	}

	public void setValorOriginal(double valorOriginal) {
		this.valorOriginal = valorOriginal;
	}

	public double getValorJuros() {
		return valorJuros;
	}

	public void setValorJuros(double valorJuros) {
		this.valorJuros = valorJuros;
	}

	public double getValormulta() {
		return valormulta;
	}

	public void setValormulta(double valormulta) {
		this.valormulta = valormulta;
	}

	public double getValordesconto() {
		return valordesconto;
	}

	public void setValordesconto(double valordesconto) {
		this.valordesconto = valordesconto;
	}

	public int getPagRec() {
		return pagRec;
	}

	public void setPagRec(int pagRec) {
		this.pagRec = pagRec;
	}

	public int getStatusLan() {
		return statusLan;
	}

	public void setStatusLan(int statusLan) {
		this.statusLan = statusLan;
	}

	public String getDataBaixa() {
		return dataBaixa;
	}

	public void setDataBaixa(String dataBaixa) {
		this.dataBaixa = dataBaixa;
	}

	public double getValorBaixa() {
		return valorBaixa;
	}

	public void setValorBaixa(double valorBaixa) {
		this.valorBaixa = valorBaixa;
	}

	public ArrayList<FinLanClass> selecionar() throws Exception {
		ResultSet rs = null;
		ArrayList<FinLanClass> novaArray = new ArrayList<FinLanClass>();

		try {
			Conexao acessaBanco = new Conexao();
			rs = acessaBanco.aplicaQueryComRetorno("SELECT * FROM FLAN");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel realizar a busca dos Lan�amentos.");
		}

		while (rs.next()) {
			// cria classe para receber da consulta sql.
			FinLanClass novoLan = new FinLanClass();

			// seta os campos para o objeto.
			novoLan.setIdlan(rs.getInt("IDLAN"));
			novoLan.setNumeroDocumento(rs.getString("NUMERODOCUMENTO"));
			novoLan.setCodCxa(rs.getInt("CODCXA"));
			novoLan.setCodCfo(rs.getInt("CODCFO"));
			novoLan.setCodTdo(rs.getInt("CODTDO"));
			novoLan.setIdXcx(rs.getInt("IDXCX"));
			novoLan.setDataEmissao(rs.getString("DATAEMISSAO"));
			novoLan.setDataVencimento(rs.getString("DATAVENCIMENTO"));
			novoLan.setValorOriginal(rs.getDouble("VALORORIGINAL"));
			novoLan.setValorJuros(rs.getDouble("VALORJUROS"));
			novoLan.setValormulta(rs.getDouble("VALORMULTA"));
			novoLan.setValordesconto(rs.getDouble("VALORDESCONTO"));
			novoLan.setPagRec(rs.getInt("PAGREC"));
			novoLan.setStatusLan(rs.getInt("STATUSLAN"));
			novoLan.setValorBaixa(rs.getDouble("VALORBAIXA"));
			novoLan.setDataBaixa(rs.getString("DATABAIXA"));

			// adiciona no array que ser� retornado no final do metodo.
			novaArray.add(novoLan);

		}
		return novaArray;
	}

	public void selecionaValores(JTable tblLan) {
		try {
			// Busca a tabela
			DefaultTableModel modelo = (DefaultTableModel) tblLan.getModel();

			// Limpa os valores da tabela
			while (modelo.getRowCount() != 0) {
				modelo.removeRow(0);
			}
			// Busca os cursos no banco de dados

			FinLanClass novoLan = new FinLanClass();
			ArrayList<FinLanClass> listaLan = novoLan.selecionar();

			for (FinLanClass objetoLan : listaLan) {

				/*
				 * "Ref. Lan\u00E7amento", "Numero Documento", "Conta/Caixa",
				 * "Cliente/Fornecedor", "Tipo de Documento", "Ref. Extrato",
				 * "Data Emiss\u00E3o", "Data Vencimento", "Valor Original",
				 * "Valor Juros", "Valor Multa", "Valor Desconto",
				 * "Pagar ou Receber", "Status" }));
				 */

				Object[] linha = { objetoLan.getIdlan(),
						objetoLan.getNumeroDocumento(), objetoLan.getCodCxa(),
						objetoLan.getCodCfo(), objetoLan.getCodTdo(),
						objetoLan.getIdXcx(), objetoLan.getDataEmissao(),
						objetoLan.getDataVencimento(),
						objetoLan.getValorOriginal(),
						objetoLan.getValorJuros(), objetoLan.getValormulta(),
						objetoLan.getValordesconto(), objetoLan.getPagRec(),
						objetoLan.getStatusLan(), objetoLan.getDataBaixa(),
						objetoLan.getValorBaixa() };
				// Adiciona cada linha a tabela
				modelo.addRow(linha);
			}
		}
		// Tratamento da exce��o
		catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							null,
							"Ocorreu um erro ao consultar o(s) curso(s) desejado(s). \n\n Mais informa��es : \n\nErro: "
									+ e.getClass() + "\n\n" + e.getMessage());
		}
	}

	public boolean cadastrar() {
		// cria o novo registro
		try {
			// Cria objeto de Acesso ao banco
			Conexao acessaBanco = new Conexao();
			
			ResultSet buscaTdo = acessaBanco.aplicaQueryComRetorno("SELECT PAGREC FROM FTDO WHERE CODTDO = "+codTdo);
			buscaTdo.next();
			
//			System.out.println("Tipo de Documento: "+buscaTdo.getInt("PAGREC"));
//			
//			System.out.println("Lan�amento: "+pagRec);
			
			/*
			 * pagrec do tipo de documento
			 *  									subindo +1
			 *"Pagar",                    0 		1
			 *"Receber",                  1			2
			 *"A Pagar e A Receber"       2			3
			 * 
			 * 
			 * pagrec do lan�amento
			 * 
			 *"",            0
			 *"Pagar",       1
			 *"Receber"      2
			 * 
			 * 
			 * 
			 */
			

			
			
			if(buscaTdo.getInt("PAGREC")+1 == pagRec || buscaTdo.getInt("PAGREC")+1 == 3)// se � igual tipo de documento e lan�amento
			{
				// Executou a query (que esta sendo passada por parametro)
				acessaBanco.aplicaQuerySemRetorno("INSERT INTO FLAN VALUES("
						+ idlan + "," + numeroDocumento + "," + codCxa + ","
						+ codCfo + "," + codTdo + "," + "NULL" + ",'" + dataEmissao
						+ "','" + dataVencimento + "'," + valorOriginal + ","
						+ valorJuros + "," + valormulta + "," + valordesconto + ","
						+ pagRec + "," + statusLan + "," + dataBaixa + ","
						+ valorBaixa + ")");
				
				JOptionPane.showMessageDialog(null, "Salvo com Sucesso");
				
				//ocorreu tudo bem.
				return true;
			}
			else{
				JOptionPane.showMessageDialog(null, "Erro: \nA Op��o Pagar/Receber do Tipo de Documento vinculado � diferente do campo Pagar/Receber do Lan�amento");
				return false;
			}
		}
		// Faz o tratamento da exce��o que pode ser retornada
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	public void deletar(JTable tblLan) {
		try {
			// conecta no banco.
			Conexao acessaBanco = new Conexao();
			// passa a query que ser� realizada.
			if (JOptionPane
					.showConfirmDialog(null,
							"Deseja Realmente excluir o item Selecionado?\nEst� opera��o � irrevers�vel!") == 0)
				acessaBanco
						.aplicaQuerySemRetorno("DELETE FROM FLAN WHERE IDLAN = "
								+ tblLan.getValueAt(tblLan.getSelectedRow(), 0));
			selecionaValores(tblLan);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizar() {
		try {
			// conecta no banco
			Conexao acessaBanco = new Conexao();

			// guarda os dados dentro da consulta dentro da variavel rs.

			acessaBanco.aplicaQuerySemRetorno("UPDATE FLAN SET "
					+ "NUMERODOCUMENTO ="
					+ numeroDocumento
					+ ", CODCXA="
					+ codCxa
					+ ", CODCFO="
					+ codCfo
					+ ", CODTDO="
					+ codTdo
					+ ", DATAEMISSAO='"
					+ dataEmissao
					+ "'"
					+ ", DATAVENCIMENTO='"
					+ dataVencimento
					+ "'"
					+ ", VALORORIGINAL="
					+ valorOriginal
					+ ", VALORJUROS="
					+ valorJuros
					+ ", VALORMULTA= "
					+ valormulta
					+ ", VALORDESCONTO= "
					+ valordesconto
					+ ", PAGREC= " + pagRec + " WHERE IDLAN = " + idlan);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void baixar(JLabel lblRef, JDateChooser dtcDataBaixa) {

		double ValorLiquido;
		Conexao acessaBanco = new Conexao();
		try {

			// pega o novo idxcx
			ResultSet novoIdxcx = acessaBanco
					.aplicaQueryComRetorno("SELECT MAX(IDXCX)+1 AS 'MAXIDXCX' FROM FXCX");
			novoIdxcx.next();

			// pega o extrato de caixa e coloca dentro do rs.
			ResultSet pegaDadosDoIdlan = acessaBanco
					.aplicaQueryComRetorno("SELECT * FROM FLAN WHERE IDLAN = "
							+ lblRef.getText());
			pegaDadosDoIdlan.next();

			// pega dados do fxcx
			ResultSet pegaDadosDoIdxcxDoLan = acessaBanco
					.aplicaQueryComRetorno("SELECT IDXCX,COMPENSADO FROM FXCX WHERE IDLAN = "
							+ pegaDadosDoIdlan.getInt("IDLAN"));
			pegaDadosDoIdxcxDoLan.next();

			// valor que ser� gerado do extrato
			ValorLiquido = pegaDadosDoIdlan.getDouble("ValorOriginal")
					+ pegaDadosDoIdlan.getDouble("VALORJUROS")
					+ pegaDadosDoIdlan.getDouble("VALORMULTA")
					- pegaDadosDoIdlan.getDouble("VALORDESCONTO");

			// se a pagar 1
			if (pegaDadosDoIdlan.getInt("PAGREC") == 1) {
				// tenho que gerar um extrato com valor negativo.
				ValorLiquido = ValorLiquido * (-1);
			}

			if (pegaDadosDoIdlan.getInt("STATUSLAN") == 0) {

				try {

					// acessaBanco.aplicaQuerySemRetorno("BEGIN TRAN");

					// realiza a atualiza��o da Baixa
					acessaBanco.aplicaQuerySemRetorno("UPDATE FLAN "
							+ "SET STATUSLAN = 1" + ", DATABAIXA = '"
							+ sdf.format(dtcDataBaixa.getDate()) + "'"
							+ ", VALORBAIXA = " + ValorLiquido
							+ "  WHERE IDLAN = " + lblRef.getText());

					// realiza a inclus�o do extrato de caixa.
					acessaBanco
							.aplicaQuerySemRetorno("INSERT INTO FXCX VALUES ("
									+ novoIdxcx.getInt("MAXIDXCX") // dever� ser
																	// buscado
																	// com MAX
																	// na tabela
																	// FXCX
									+ ","
									+ ValorLiquido // valor do extrato mesmo
													// valor da baixa.
									+ ","
									+ pegaDadosDoIdlan
											.getString("numeroDocumento") // mesmo
																			// numero
																			// documento
																			// do
																			// lan�amento
									+ "," + 0// compensado
									+ "," + pegaDadosDoIdlan.getInt("IDLAN") // campo
																				// idlan
									+ "," + pegaDadosDoIdlan.getInt("CODCXA")// campo
																				// conta
																				// caixa.
									+ ",'" + sdf.format(dtcDataBaixa.getDate()) // campo
																				// dataBaixa
									+ "'," + null + ")"); // data compensa��o

					// atualiza o IDXCX da tabela flan
					acessaBanco.aplicaQuerySemRetorno("UPDATE FLAN SET IDXCX="
							+ novoIdxcx.getInt("MAXIDXCX") + "WHERE IDLAN = "
							+ pegaDadosDoIdlan.getInt("IDLAN"));

					// commita a query
					// acessaBanco.aplicaQuerySemRetorno("COMMIT");

					JOptionPane.showMessageDialog(null,
							"Lan�amento Baixado com Sucesso!");

				} catch (Exception e) {
					e.printStackTrace();
					// acessaBanco.aplicaQuerySemRetorno("ROLLBACK");

				}

			}
			// se o lan�amento j� esta baixado > retira a baixa.
			else if (pegaDadosDoIdlan.getInt("STATUSLAN") == 1) {

				try {
					// INICIA transa�ao.
					// acessaBanco.aplicaQuerySemRetorno("BEGIN TRAN");

					// retira a Baixa do Lan�amento
					acessaBanco.aplicaQuerySemRetorno("UPDATE FLAN "
							+ "SET STATUSLAN = 0 " + ", DATABAIXA = " + null
							+ ", VALORBAIXA = 0" + ", IDXCX = " + null
							+ " WHERE IDLAN = " + lblRef.getText());

					if (pegaDadosDoIdxcxDoLan.getInt("COMPENSADO") == 1) {
						JOptionPane
								.showMessageDialog(null,
										"Extrato n�o pode ser exclu�do, o mesmo est� Compensado.");
					} else {
						// realiza a exclus�o do extrato.
						acessaBanco
								.aplicaQuerySemRetorno("DELETE FROM FXCX WHERE IDXCX = "
										+ pegaDadosDoIdlan.getInt("IDXCX"));
						// TEM QUE VALIDAR SE O EXTRATO TA COMPENSADO
					}

					// commita transa��o
					// acessaBanco.aplicaQuerySemRetorno("COMMIT TRAN");

					// informa que tudo ocorreu bem
					JOptionPane.showMessageDialog(null,
							"Lan�amento teve sua Baixa Cancelada com Sucesso!");

				} catch (Exception e) {
					e.printStackTrace();
					// acessaBanco.aplicaQuerySemRetorno("ROLLBACK");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
