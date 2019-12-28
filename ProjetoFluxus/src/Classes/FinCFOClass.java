package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexao.Conexao;

public class FinCFOClass {

	private int CODCFO;
	private String NOME;
	private String CGCCFO;
	private String ENDERECO;
	private String TELEFONE;

	public int getCODCFO() {
		return CODCFO;
	}

	public void setCODCFO(int CODCFO) {
		this.CODCFO = CODCFO;
	}

	public String getNOME() {
		return NOME;
	}

	public void setNOME(String NOME) {
		this.NOME = NOME;
	}

	public String getCGCCFO() {
		return CGCCFO;
	}

	public void setCGCCFO(String CGCCFO) {
		this.CGCCFO = CGCCFO;
	}

	public String getENDERECO() {
		return ENDERECO;
	}

	public void setENDERECO(String ENDERECO) {
		this.ENDERECO = ENDERECO;
	}

	public String getTELEFONE() {
		return TELEFONE;
	}

	public void setTELEFONE(String TELEFONE) {
		this.TELEFONE = TELEFONE;
	}

	public void cadastrar() {
		// cria objeto da classe de conex�o com o banco.
		try {
			Conexao acessaBanco = new Conexao();
			acessaBanco.aplicaQuerySemRetorno("INSERT INTO FCFO VALUES ("
					+ CODCFO + ",'" + NOME + "','" + CGCCFO + "','" + ENDERECO
					+ "','" + TELEFONE + "')");
			JOptionPane.showMessageDialog(null,
					"Cliente/Fornecedor Cadastrado com Sucesso!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane
					.showMessageDialog(null,
							"Cliente/Fornecedor n�o pode ser salvo, avalie se os campos est�o corretos!");
			e.printStackTrace();
		}
	}

	public void selecionaValores(JTable tblCFO) {
		try {

			// informa o modelo da tela
			DefaultTableModel modelo = (DefaultTableModel) tblCFO.getModel();

			// cria o novo cliente que ser� adicionado na lista de clientes que
			// ser� enviado para o JTable
			FinCFOClass novoCliente = new FinCFOClass();

			// apaga a tabela para n�o inclu�-la 2x.
			while (modelo.getRowCount() != 0) {
				modelo.removeRow(0);
			}

			// Metodo XXX.selecionar � um metodo que retornar� todos os
			// clientes/ fornecedores do banco em forma
			// de ArrayList<FinCFOClass>
			// desta forma teremos uma lista com todos os clientes fornecedores
			// que est�o no banco.

			ArrayList<FinCFOClass> listaCFO = novoCliente.selecionar();

			// faremos um foreach para enviar os valores da lista para dentro da
			// tabela.

			for (FinCFOClass novoFinCFO : listaCFO) {

				// ordem de inserir no banco e no modelo.
				// codcfo, nome, cgccfo, endereco, telefone

				// cada linha que ser� rodada no Array ser� adicionada a uma
				// lista de objeto.
				Object[] linhaTabela = { novoFinCFO.getCODCFO(),
						novoFinCFO.getNOME(), novoFinCFO.getCGCCFO(),
						novoFinCFO.getENDERECO(), novoFinCFO.getTELEFONE() };

				// ser� adicionado a lista de objetos no modelo da tabela.
				modelo.addRow(linhaTabela);
			}
			// Limpa os valores da tabela
		} catch (Exception e) {

		}
	}

	public ArrayList<FinCFOClass> selecionar() throws Exception {
		ResultSet rs = null;
		ArrayList<FinCFOClass> novaArray = new ArrayList<FinCFOClass>();

		try {
			Conexao acessaBanco = new Conexao();
			rs = acessaBanco.aplicaQueryComRetorno("SELECT * FROM FCFO");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"N�o realizar a busca dos clientes/fornecedores.");
		}

		while (rs.next()) {
			// cria classe para receber da consulta sql.
			FinCFOClass novoFinCFO = new FinCFOClass();

			// seta os campos para o objeto.
			novoFinCFO.setCODCFO(rs.getInt(1));
			novoFinCFO.setNOME(rs.getString(2));
			novoFinCFO.setCGCCFO(rs.getString(3));
			novoFinCFO.setENDERECO(rs.getString(4));
			novoFinCFO.setTELEFONE(rs.getString(5));

			// adiciona no array que ser� retornado no final do metodo.
			novaArray.add(novoFinCFO);

		}
		return novaArray;
	}

	public void deleta(JTable tblCfo) {
		try {
			// conecta no banco.
			Conexao acessaBanco = new Conexao();
			// passa a query que ser� realizada.
			if (JOptionPane
					.showConfirmDialog(null,
							"Deseja Realmente excluir o item Selecionado?\nEst� opera��o � irrevers�vel!") == 0)
				acessaBanco
						.aplicaQuerySemRetorno("DELETE FROM FCFO WHERE CODCFO= "
								+ tblCfo.getValueAt(tblCfo.getSelectedRow(), 0));
			selecionaValores(tblCfo);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"ERRO: \n"+e.getClass() + "\n" + e.getMessage());
		}
	}

	public void atualiza(JTable tblCfo)
	{
		Conexao acessaBanco = new Conexao();
		/*
		      CODCFO INTEGER NOT NULL,
			  NOME VARCHAR(40) NULL,
			  CGCCFO VARCHAR(18) NULL,
			  ENDERECO VARCHAR(40) NULL,
			  TELEFONE VARCHAR(20) NULL,
		 */
		
		
		try {
			acessaBanco.aplicaQuerySemRetorno("UPDATE FCFO SET "
					+ "NOME = '"+NOME+"'"
					+ ",CGCCFO = '"+CGCCFO+"'"
					+ ",ENDERECO = '"+ENDERECO+"'"
					+ ",TELEFONE = '"+TELEFONE+"'"
					+ "WHERE CODCFO = "+tblCfo.getValueAt(tblCfo.getSelectedRow(), 0));
			
//			
//			System.out.println("UPDATE FCFO SET "
//					+ "NOME = '"+NOME+"'"
//					+ ",CGCCFO = '"+CGCCFO+"'"
//					+ ",ENDERECO = '"+ENDERECO+"'"
//					+ ",TELEFONE = '"+TELEFONE+"'"
//					+ "WHERE CODCFO = "+tblCfo.getValueAt(tblCfo.getSelectedRow(), 0));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
