package Classes;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexao.Conexao;

import com.toedter.calendar.JDateChooser;

public class FinXCXClass {

	private int idXcx;
	private double valor;
	private String numeroDocumento;
	private int compensado;
	private int idLan;
	private int codCxa;
	private String data;
	private String dataCompensacao;
	private double saldoAtualTemp;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	
	public String getDataCompensacao() {
		return dataCompensacao;
	}

	public void setDataCompensacao(String dataCompensacao) {
		this.dataCompensacao = dataCompensacao;
	}

	public int getIdXcx() {
		return idXcx;
	}

	public void setIdXcx(int idXcx) {
		this.idXcx = idXcx;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public int getCompensado() {
		return compensado;
	}

	public void setCompensado(int compensado) {
		this.compensado = compensado;
	}

	public int getIdLan() {
		return idLan;
	}

	public void setIdLan(int idLan) {
		this.idLan = idLan;
	}

	public int getCodCxa() {
		return codCxa;
	}

	public void setCodCxa(int codCxa) {
		this.codCxa = codCxa;
	}

	private String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void cadastrar() throws Exception {
		try {
			// Cria objeto de Acesso ao banco
			Conexao acessaBanco = new Conexao();

			// Executou a query (que esta sendo passada por parametro)
			acessaBanco.aplicaQuerySemRetorno("INSERT INTO FXCX VALUES ("
					+ idXcx + "," + valor + ",'" + numeroDocumento + "',"
					+ compensado + "," + null + "," + codCxa + ",'" + data
					+ "')");

			// valida query
			// System.out.println("INSERT INTO FXCX VALUES ("
			// + idXcx + "," + valor + ",'" + numeroDocumento +
			// "',"+ compensado+","+ null+","+codCxa+",'"+data+"')");

			JOptionPane.showMessageDialog(null, "Salvo com Sucesso");
		}
		// Faz o tratamento da exce��o que pode ser retornada
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public ArrayList<FinXCXClass> selecionar() throws Exception {
		ResultSet rs = null;
		ArrayList<FinXCXClass> novaArray = new ArrayList<FinXCXClass>();

		try {
			Conexao acessaBanco = new Conexao();
			rs = acessaBanco.aplicaQueryComRetorno("SELECT * FROM FXCX");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel realizar a busca dos Lan�amentos.");
		}

		while (rs.next()) {
			// cria classe para receber da consulta sql.
			FinXCXClass novoXcx = new FinXCXClass();

			// seta os campos para o objeto.
			novoXcx.setIdXcx(rs.getInt("IDXCX"));
			novoXcx.setValor(rs.getInt("VALOR"));
			novoXcx.setNumeroDocumento(rs.getString("NUMERODOCUMENTO"));
			novoXcx.setCompensado(rs.getInt("COMPENSADO"));
			novoXcx.setIdLan(rs.getInt("IDLAN"));
			novoXcx.setCodCxa(rs.getInt("CODCXA"));
			novoXcx.setData(rs.getString("DATA"));
			novoXcx.setDataCompensacao(rs.getString("DATACOMPENSACAO"));

			// adiciona no array que ser� retornado no final do metodo.
			novaArray.add(novoXcx);

		}
		return novaArray;
	}

	public void selecionaValores(JTable tblXcx) {
		try {
			// Busca a tabela
			DefaultTableModel modelo = (DefaultTableModel) tblXcx.getModel();

			// Limpa os valores da tabela
			while (modelo.getRowCount() != 0) {
				modelo.removeRow(0);
			}
			// Busca os cursos no banco de dados

			FinXCXClass novoXcx = new FinXCXClass();
			ArrayList<FinXCXClass> listaXcx = novoXcx.selecionar();

			for (FinXCXClass objetoXcx : listaXcx) {

				Object[] linha = { objetoXcx.getIdXcx(), objetoXcx.getValor(),
						objetoXcx.getNumeroDocumento(),
						objetoXcx.getCompensado(), objetoXcx.getIdLan(),
						objetoXcx.getCodCxa(), objetoXcx.getData(),objetoXcx.getDataCompensacao() };
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

	public void deletar(JTable tblXcx) {
		try {
			// conecta no banco.
			Conexao acessaBanco = new Conexao();
			// passa a query que ser� realizada.
			if (JOptionPane
					.showConfirmDialog(null,
							"Deseja Realmente excluir o item Selecionado?\nEst� opera��o � irrevers�vel!") == 0)
				acessaBanco
						.aplicaQuerySemRetorno("DELETE FROM FXCX WHERE IDXCX = "
								+ tblXcx.getValueAt(tblXcx.getSelectedRow(), 0));
			selecionaValores(tblXcx);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualiza(JTable tblXcx) {
		Conexao acessaBanco = new Conexao();

		try {

			acessaBanco.aplicaQuerySemRetorno("UPDATE FXCX SET" + " CODCXA = '"
					+ codCxa + "'" + ",DATA = '" + data + "'" + ",VALOR = "
					+ valor + ",NUMERODOCUMENTO = " + numeroDocumento
					+ " WHERE IDXCX = "
					+ tblXcx.getValueAt(tblXcx.getSelectedRow(), 0));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void compensar(JLabel lblRef, JDateChooser dtcCompensacao) {
		Conexao acessaBanco = new Conexao();
		try {
			// pega o extrato de caixa e coloca dentro do rs.
			ResultSet rs = acessaBanco
					.aplicaQueryComRetorno("SELECT * FROM"
							+ " FXCX JOIN FCXA ON "
							+ "FXCX.CODCXA = FCXA.CODCXA "
							+ "WHERE IDXCX = "
							+ lblRef.getText());

			// inicia a linha para o rs.
			rs.next();
			
			if (rs.getInt("COMPENSADO") == 0) {
				
				// realiza a atualiza��o do status de compensa��o.
				acessaBanco.aplicaQuerySemRetorno("UPDATE FXCX "
						+ "SET COMPENSADO = 1" + ", DATACOMPENSACAO = '"
						+ sdf.format(dtcCompensacao.getDate()) + "'  WHERE IDXCX = "
						+ lblRef.getText());
				
				//saldo que ser� atualizado na conta/caixa
				//como ser� o processo para cancelar a compensacao ser� reduzido do valor do extrato.

				saldoAtualTemp = rs.getDouble("SALDOATUAL")+rs.getDouble("VALOR");
				
				//atualiza saldo da tabela de Conta Caixa.				
				acessaBanco.aplicaQuerySemRetorno("UPDATE FCXA SET SALDOATUAL = "+saldoAtualTemp
						+"WHERE CODCXA = "+rs.getInt("CODCXA")
						);

				
				
				JOptionPane.showMessageDialog(null,
						"Extrato Compensado com Sucesso!");
			}
			
			if (rs.getInt("COMPENSADO") == 1) {
				// retira a compensa��o do extrato
				acessaBanco.aplicaQuerySemRetorno("UPDATE FXCX "
						+ "SET COMPENSADO = 0 " + ", DATACOMPENSACAO = "
						+ null + " WHERE IDXCX = " + lblRef.getText());

				//saldo que ser� atualizado na conta/caixa
				//como ser� o processo para cancelar a compensacao ser� reduzido do valor do extrato.
				saldoAtualTemp = rs.getDouble("SALDOATUAL")-rs.getDouble("VALOR");
				
				//atualiza saldo da tabela de Conta Caixa.				
				acessaBanco.aplicaQuerySemRetorno("UPDATE FCXA SET SALDOATUAL = "+saldoAtualTemp
						+"WHERE CODCXA = "+rs.getInt("CODCXA"));
				
				
				JOptionPane.showMessageDialog(null,
						"Extrato Descompensado com Sucesso!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
