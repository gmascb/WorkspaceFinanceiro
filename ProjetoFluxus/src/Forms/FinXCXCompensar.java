package Forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import Classes.FinXCXClass;
import Conexao.Conexao;
import Main.Principal;

import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FinXCXCompensar extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblRefDoExtrato;
	private JLabel lblRef;
	private JDateChooser dateChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Principal main = new Principal();
			main.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FinXCXCompensar(JTable tblXcx) {
		setModal(true);

		setTitle("Compensar Extrato");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(166, 132, 114, 28);
		contentPanel.add(dateChooser);

		JLabel lblDataDeCompensao = new JLabel("Data de Compensa\u00E7\u00E3o:");
		lblDataDeCompensao.setBounds(6, 139, 157, 16);
		contentPanel.add(lblDataDeCompensao);

		JLabel lblInf = new JLabel(
				"<html> Este processo ir\u00E1 realizar a compensa\u00E7\u00E3o do Extrato de Caixa para que o mesmo realize atualiza\u00E7\u00E3o do Saldo da Conta/Caixa que o mesmo faz Refer\u00EAncia. </html>");
		lblInf.setBounds(6, 6, 422, 71);
		contentPanel.add(lblInf);

		JLabel lblRefDoExtrato = new JLabel("Ref. do Extrato de Caixa:");
		lblRefDoExtrato.setBounds(6, 104, 136, 16);
		contentPanel.add(lblRefDoExtrato);

		lblRef = new JLabel("_");
		lblRef.setBounds(166, 95, 114, 31);
		contentPanel.add(lblRef);
		buscaIDXCX(tblXcx);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnExecutar = new JButton("Executar");
				btnExecutar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						// cria objeto que ser� compensado
						FinXCXClass novoXCX = new FinXCXClass();

						// chama o metodo para compensar.
						novoXCX.compensar(lblRef, dateChooser);

						// fecha a janela ap�s compensar.
						dispose();

					}
				});
				btnExecutar.setActionCommand("OK");
				buttonPane.add(btnExecutar);
				getRootPane().setDefaultButton(btnExecutar);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						dispose();

					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}

	}

	public void buscaIDXCX(JTable tblXcx) {

		Conexao acessaBanco = new Conexao();
		try {
			ResultSet rs = acessaBanco
					.aplicaQueryComRetorno("SELECT IDXCX FROM FXCX WHERE IDXCX = "
							+ tblXcx.getValueAt(tblXcx.getSelectedRow(), 0));
			// pula a linha para funcionar.
			rs.next();

			// preenche o campo ref. do extrato.
			// System.out.println(String.valueOf(tblXcx.getValueAt(tblXcx.getSelectedRow(),
			// 0)));

			lblRef.setText(String.valueOf(tblXcx.getValueAt(
					tblXcx.getSelectedRow(), 0)));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
