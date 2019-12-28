package Forms;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Classes.FinXCXClass;
import Conexao.Conexao;
import Main.Principal;

import com.toedter.calendar.JDateChooser;


public class FinXCX extends JInternalFrame {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdxcx;
	private JTextField txtValor;
	private JTextField txtNumerodocumento;
	private JTextField txtCodcxa;
	private JTable tblXcx;
	private JPanel buttonPane;
	private JButton btnAtualiza;
	private JButton btnCancela;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Conexao acessaBanco = new Conexao();
	private JDateChooser dtcData;

	public void limpaCamposTela()
	{
		txtCodcxa.setText("");
		txtIdxcx.setText(getMaxIdXcx());
		txtNumerodocumento.setText("");
		txtValor.setText("");
		dtcData.setDate(null);
	}
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Principal frame = new Principal();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FinXCX() {
		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		setTitle("Extratos de Caixa");
		setBounds(100, 100, 738, 650);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblRefExtrato = new JLabel("Ref. Extrato");
		lblRefExtrato.setBounds(10, 11, 83, 14);
		contentPanel.add(lblRefExtrato);
		
		txtIdxcx = new JTextField();
		txtIdxcx.setBounds(92, 8, 86, 24);
		txtIdxcx.setText(getMaxIdXcx());
		contentPanel.add(txtIdxcx);
		txtIdxcx.setColumns(10);
		txtIdxcx.setEnabled(false);
		
		JLabel lblValor = new JLabel("Valor");
		lblValor.setBounds(10, 50, 46, 14);
		contentPanel.add(lblValor);
		
		JLabel lblNmeroDocumento = new JLabel("N\u00FAmero Documento");
		lblNmeroDocumento.setBounds(262, 14, 112, 14);
		contentPanel.add(lblNmeroDocumento);
		
		txtValor = new JTextField();
		txtValor.setBounds(44, 47, 134, 24);
		contentPanel.add(txtValor);
		txtValor.setColumns(10);
		
		dtcData = new JDateChooser("dd/MM/yyyy","##/##/#####", ' ');
		dtcData.setBounds(79, 82, 134, 28);
		contentPanel.add(dtcData);
		
		txtNumerodocumento = new JTextField();
		txtNumerodocumento.setBounds(396, 11, 86, 24);
		contentPanel.add(txtNumerodocumento);
		txtNumerodocumento.setColumns(10);
		
		JLabel lblContaCaixa = new JLabel("Conta Caixa");
		lblContaCaixa.setBounds(262, 39, 95, 14);
		contentPanel.add(lblContaCaixa);
		
		txtCodcxa = new JTextField();
		txtCodcxa.setBounds(337, 39, 86, 24);
		contentPanel.add(txtCodcxa);
		txtCodcxa.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 182, 702, 428);
		contentPanel.add(scrollPane);
		
		tblXcx = new JTable();
		tblXcx.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Ref. Extrato", "Valor", "Numero Documento", "Compensado", "Ref. Lan\u00E7amento", "Conta Caixa", "Data","Data Compensa\u00E7\u00E3o"
			}
		));
		scrollPane.setViewportView(tblXcx);
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(10, 138, 700, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				
				JButton btnCompensar = new JButton("Compensar");
				btnCompensar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					
						FinXCXCompensar novoCompensador = new FinXCXCompensar(tblXcx);
						
						novoCompensador.setVisible(true);
						
						FinXCXClass novoXcx = new FinXCXClass();
						
						novoXcx.selecionaValores(tblXcx);
					
					
					}
				});
				buttonPane.add(btnCompensar);
				
				JButton btnIncluir = new JButton("Incluir");
				btnIncluir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					
						FinXCXClass novoXcx = new FinXCXClass();
						try {

							novoXcx.setIdXcx(Integer.parseInt(txtIdxcx.getText()));
							novoXcx.setValor(Double.parseDouble(txtValor.getText()));
							novoXcx.setNumeroDocumento(txtNumerodocumento.getText());
							novoXcx.setCompensado(0);
							//novoXcx.setIdlan(Idlan) n�o ser� realizado pois este metodo � inser��o manual
							novoXcx.setCodCxa(Integer.parseInt(txtCodcxa.getText()));
							novoXcx.setData(String.valueOf(sdf.format(dtcData.getDate())));
							
							novoXcx.cadastrar();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						//atualiza tabela.
						novoXcx.selecionaValores(tblXcx);
						
						//limpa
						limpaCamposTela();
						
						
					}
				});
				buttonPane.add(btnIncluir);
				
				JButton btnEditar = new JButton("Editar");
				btnEditar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
						//retira o button pane.
						buttonPane.setVisible(false);
					
						//aparece os bot�es de edi��o.
						btnAtualiza.setVisible(true);
						btnCancela.setVisible(true);
						
						//faz o campo de ID estar disable.
						txtIdxcx.setEnabled(false);
						
						try {
							
							ResultSet rs = acessaBanco.aplicaQueryComRetorno("SELECT * FROM FXCX WHERE IDXCX = "+ tblXcx.getValueAt(tblXcx.getSelectedRow(),0));
							
							rs.next();
							
							txtCodcxa.setText(rs.getString("CODCXA"));
							txtIdxcx.setText(rs.getString("IDXCX"));
							txtNumerodocumento.setText(rs.getString("NUMERODOCUMENTO"));
							txtValor.setText(rs.getString("VALOR"));
							dtcData.setDate(rs.getDate("DATA"));
							
							
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
					}
				});
				buttonPane.add(btnEditar);
				
				JButton btnApagar = new JButton("Apagar");
				btnApagar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					
						FinXCXClass novoXcx = new FinXCXClass();
						novoXcx.deletar(tblXcx);
						//atualiza a tela.
						novoXcx.selecionaValores(tblXcx);
					}
				});
				buttonPane.add(btnApagar);
				
				JButton btnCarregar = new JButton("Carregar");
				btnCarregar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					
						FinXCXClass novoXcx = new FinXCXClass();
						novoXcx.selecionaValores(tblXcx);
					
					}
				});
				buttonPane.add(btnCarregar);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		JLabel lblData = new JLabel("Data");
		lblData.setBounds(10, 88, 55, 16);
		contentPanel.add(lblData);
		
		btnAtualiza = new JButton("Atualiza");
		btnAtualiza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				FinXCXClass novoXCX = new FinXCXClass();
				
				//coloca o button pane.
				buttonPane.setVisible(true);
			
				//desaparece os bot�es de edi��o.
				btnAtualiza.setVisible(false);
				btnCancela.setVisible(false);

				//preenche os novos campos.
				
				novoXCX.setValor(Double.parseDouble(txtValor.getText()));
				novoXCX.setNumeroDocumento(txtNumerodocumento.getText());
				novoXCX.setCompensado(0);
				novoXCX.setCodCxa(Integer.parseInt(txtCodcxa.getText()));
				novoXCX.setData(String.valueOf(sdf.format(dtcData.getDate())));

				//atualiza
				novoXCX.atualiza(tblXcx);
				
				//limpa os campos.
				limpaCamposTela();
				
				
				//atualiza a tela
				novoXCX.selecionaValores(tblXcx);
				
			
			
			}
		});
		btnAtualiza.setBounds(552, 11, 90, 28);
		btnAtualiza.setVisible(false);
		contentPanel.add(btnAtualiza);
		

		
		
		
		btnCancela = new JButton("Cancela");
		btnCancela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//coloca o button pane.
				buttonPane.setVisible(true);
			
				//desaparece os bot�es de edi��o.
				btnAtualiza.setVisible(false);
				btnCancela.setVisible(false);
				
				//limpa os campos.
				limpaCamposTela();
				
				
				
			}
		});
		btnCancela.setBounds(552, 50, 90, 28);
		contentPanel.add(btnCancela);
		btnCancela.setVisible(false);
		

	}


	public String getMaxIdXcx()
	{
		String idXcx="";
		Conexao acessaBanco = new Conexao();
		try {
			
			// pega o novo IDLAN
			ResultSet novoIdlan = acessaBanco.aplicaQueryComRetorno("SELECT MAX(IDXCX)+1 AS 'MAXIDXCX' FROM FXCX");
			novoIdlan.next();
			
			System.out.println(String.valueOf(novoIdlan.getInt("MAXIDXCX")));
			
			idXcx = String.valueOf(novoIdlan.getInt("MAXIDXCX"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return idXcx;
	}









}
