package Classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.toedter.calendar.JDateChooser;

import Conexao.Conexao;
import Forms.FinRelatorioFinanceiro;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class FinRelatorioFinanceiroClass {

	public void gerarRelatorio(int pagRec, JDateChooser dtinicial,
			JDateChooser dtfinal) {
		FinRelatorioFinanceiroClass novoLan = new FinRelatorioFinanceiroClass();

		try {
			// busca relatorio
			JasperReport report = JasperCompileManager
					.compileReport("Relatorios/Simple_Blue.jrxml");

			// passa os dados que ser�o impressos no relat�rio
			JasperPrint print = JasperFillManager.fillReport(
					report,
					null,
					new JRBeanCollectionDataSource(novoLan.sqlMontaRelatorio(
							pagRec, dtinicial, dtfinal)));

			// cria visualiza��o no aplicativo JasperViewer (como se fosse o
			// RMRelViewer)
			JasperViewer frame = new JasperViewer(print);

			// mostra visualiza��o.
			frame.setVisible(true);

		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null,
					"" + e.getClass() + "\n" + e.getMessage() + "\n");

			e.printStackTrace();

		}

	}

	public ArrayList<FinLanClass> sqlMontaRelatorio(int pagRec,
			JDateChooser dtinicial, JDateChooser dtfinal) throws SQLException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ResultSet rs;
		ArrayList<FinLanClass> novaArray = new ArrayList<FinLanClass>();

		try {
			Conexao acessaBanco = new Conexao();


			System.out.println();
			
			rs = acessaBanco
					.aplicaQueryComRetorno("SELECT IDLAN,NUMERODOCUMENTO,VALORORIGINAL "
							+ "FROM FLAN "
							+ "WHERE "
							+ "DATAEMISSAO "
							+ "BETWEEN '"
							+ sdf.format(dtinicial.getDate())
							+ "' AND '"
							+ sdf.format(dtfinal.getDate())
							+ "' AND PAGREC = " + pagRec);

			
			while (rs.next()) {
				// cria classe para receber da consulta sql.
				FinLanClass novoLan = new FinLanClass();

				// seta os campos para o objeto.
				System.out.println("" + rs.getInt("IDLAN"));
				novoLan.setIdlan(rs.getInt(1));
				System.out.println("" + rs.getString("NUMERODOCUMENTO"));
				novoLan.setNumeroDocumento(rs.getString(2));
				System.out.println("" + rs.getDouble("VALORORIGINAL"));
				novoLan.setValorOriginal(rs.getDouble(3));

				// adiciona no array que ser� retornado no final do metodo.
				novaArray.add(novoLan);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"N�o realizou a busca dos Lan�amentos.\n Ou n�o preencheu os lan�amentos nas lista para gerar o relat�rio.");
		}

		return novaArray;
	}
}