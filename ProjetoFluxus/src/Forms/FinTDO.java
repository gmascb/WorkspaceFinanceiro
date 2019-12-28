package Forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Classes.FinTDOClass;
import Main.Principal;

import Conexao.Conexao;
import java.sql.ResultSet;

public class FinTDO extends JInternalFrame {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtcodtdo;
	private JTextField txtNometdo;
	private JTable tblTdo;
	private JButton btnAtualiza;
	private JButton btnCancela;
	private JPanel buttonPane;
	private JComboBox cbbPagRec;

	
	public void limpaCamposTela()
	{
		txtcodtdo.setText("");
		txtNometdo.setText("");
		cbbPagRec.setSelectedIndex(0);
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
	public FinTDO() {
		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		setNormalBounds(new Rectangle(0, 0, 845, 580));
		setTitle("Tipo de Documento");
		setBounds(100, 100, 616, 509);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 48, 46, 14);
		contentPanel.add(lblCodigo);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 76, 46, 14);
		contentPanel.add(lblNome);

		JLabel lblPagRec = new JLabel("A Pagar e A Receber");
		lblPagRec.setBounds(10, 105, 110, 14);
		contentPanel.add(lblPagRec);

		txtcodtdo = new JTextField();
		txtcodtdo.setBounds(151, 45, 166, 24);
		contentPanel.add(txtcodtdo);
		txtcodtdo.setColumns(10);

		txtNometdo = new JTextField();
		txtNometdo.setBounds(151, 73, 166, 24);
		contentPanel.add(txtNometdo);
		txtNometdo.setColumns(10);

		cbbPagRec = new JComboBox();
		cbbPagRec.setModel(new DefaultComboBoxModel(new String[] { "Pagar",
				"Receber", "A Pagar e A Receber" }));
		cbbPagRec.setBounds(151, 102, 166, 20);
		contentPanel.add(cbbPagRec);

		JLabel lblCadastreUmNovo = new JLabel(
				"Cadastre Um novo Tipo de Documento");
		lblCadastreUmNovo.setBounds(10, 11, 227, 14);
		contentPanel.add(lblCadastreUmNovo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 176, 580, 296);
		contentPanel.add(scrollPane);

		tblTdo = new JTable();
		tblTdo.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"Codigo", "Nome", "Pagar ou Receber" }));
		scrollPane.setViewportView(tblTdo);
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(6, 126, 604, 38);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});

				JButton btnInsert = new JButton("Insere");
				btnInsert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						try {
							// Criou um objeto do Tipo de Documento
							FinTDOClass novoTdo = new FinTDOClass();

							// modifica o codigo do tipo de documento
							novoTdo.setCODTDO(Integer.parseInt(txtcodtdo
									.getText()));

							// modifica o nome do tipo de documento
							novoTdo.setNOME(txtNometdo.getText());

							// retornar� o indice do item que foi apresentado no
							// combo box
							// 1"Pagar",// 2"Receber",// 3"A Pagar e A Receber"
							novoTdo.setPAGREC(cbbPagRec.getSelectedIndex());

							// Cadastrou o objeto no banco
							novoTdo.cadastrar();

							// Apresentar a tela atualizada
							novoTdo.selecionaValores(tblTdo);

							//limpa os campos.
							limpaCamposTela();

						}
						// Tratamento da exce��o
						catch (Exception a) {
							JOptionPane
									.showMessageDialog(
											null,
											"Ocorreu um erro ao inserir o Tipo de Documento desejado. \n\n Mais informa��es : \n\nErro: "
													+ a.getClass()
													+ "\n\n"
													+ a.getMessage());
						}

					}
				});
				buttonPane.add(btnInsert);

				JButton btnLoad = new JButton("Load");
				btnLoad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						FinTDOClass novoFtdo = new FinTDOClass();
						novoFtdo.selecionaValores(tblTdo);

					}
				});

				JButton btnApagar = new JButton("Apagar");
				btnApagar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						FinTDOClass novoTdo = new FinTDOClass();
						novoTdo.deleta(tblTdo);
						
						novoTdo.selecionaValores(tblTdo);

					}
				});

				// /BOTAO EDITAR
				JButton btnEditar = new JButton("Editar");
				btnEditar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						// retira os bot�es
						buttonPane.setVisible(false);

						btnAtualiza.setVisible(true);

						btnCancela.setVisible(true);

						try {
							// conecta no banco.
							Conexao acessaBanco = new Conexao();

							// traz o resultado do banco para a variavel rs.
							ResultSet rs = acessaBanco
									.aplicaQueryComRetorno("SELECT * FROM FTDO WHERE CODTDO = "
											+ tblTdo.getValueAt(
													tblTdo.getSelectedRow(), 0));

							// pula uma linha para iniciar os dados da variavel
							// rs.
							rs.next();

							// /joga os campos da tabela para os campos da tela.

							txtcodtdo.setText(String.valueOf(rs.getInt(1)));
							txtNometdo.setText(rs.getString(2));
							cbbPagRec.setSelectedIndex(rs.getInt(3));

						} catch (Exception e) {
							e.printStackTrace();
						}
						//coloca o campo como disable pois o c�digo n�o poder� ser editado.
						txtcodtdo.setEnabled(false);
						

					}
				});
				buttonPane.add(btnEditar);
				buttonPane.add(btnApagar);
				buttonPane.add(btnLoad);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);

			}// fim metodo do bot�o
		}// termina a tabelaTDO

		btnAtualiza = new JButton("Atualiza");
		btnAtualiza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// cria a variavel que recebera os novos dados do tipo de
				// documento que seria editado.
				FinTDOClass novoTdo = new FinTDOClass();

				// guardo o nome dos campos.
				novoTdo.setNOME(txtNometdo.getText());
				novoTdo.setPAGREC(cbbPagRec.getSelectedIndex());

				// realiza o metodo de atualizar.
				novoTdo.atualiza(tblTdo);

				// atualiza os bot�es.
				buttonPane.setVisible(true);
				btnAtualiza.setVisible(false);
				btnCancela.setVisible(false);
				
				//faz o campo codtdo ser able novamente.
				txtcodtdo.setEnabled(true);
				
				//atualiza a tabela.
				novoTdo.selecionaValores(tblTdo);
				
				//limpa os campos da edi��o.
				limpaCamposTela();
				
				

			}
		});
		btnAtualiza.setBounds(373, 41, 98, 24);
		contentPanel.add(btnAtualiza);
		btnAtualiza.setVisible(false);

		btnCancela = new JButton("Cancela");
		btnCancela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// atualiza os bot�es.
				buttonPane.setVisible(true);
				btnAtualiza.setVisible(false);
				btnCancela.setVisible(false);
				
				//limpa os campos.
				limpaCamposTela();
			}
		});
		btnCancela.setBounds(373, 71, 98, 24);
		contentPanel.add(btnCancela);
		btnCancela.setVisible(false);
	}
}
