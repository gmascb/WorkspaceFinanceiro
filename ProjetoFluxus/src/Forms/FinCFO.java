package Forms;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Classes.FinCFOClass;
import Conexao.Conexao;
import Main.Principal;

public class FinCFO extends JInternalFrame {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtcodcfo;
	private JTextField txtNome;
	private JTextField txtCGCCFO;
	private JTextField txtEndereco;
	private JTextField txtTelefone;
	private JTable tblCFO;
	private JPanel buttonPane;
	private JButton btnAtualizar;
	private JButton btnCancelar;

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
	 * @throws PropertyVetoException 
	 */
	public FinCFO() {
		setClosable(true);
		setMaximizable(true);
		setResizable(true);
		
		setNormalBounds(new Rectangle(0, 0, 840, 580));
		setTitle("Clientes e Fornecedores");
		setBounds(100, 100, 840, 580);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		//setMaximumSize(JFrame.MAXIMIZED_BOTH);
		
		JLabel lblCadastroDeClientes = new JLabel(
				"Cadastro de Clientes e Fornecedores");
		lblCadastroDeClientes.setBounds(10, 11, 237, 14);
		contentPanel.add(lblCadastroDeClientes);

		JLabel lblCdigo = new JLabel("C\u00F3digo :");
		lblCdigo.setBounds(20, 36, 66, 14);
		contentPanel.add(lblCdigo);

		JLabel lblNome = new JLabel("Nome :");
		lblNome.setBounds(20, 77, 56, 14);
		contentPanel.add(lblNome);

		JLabel lblCpfcnpj = new JLabel("CPF/CNPJ :");
		lblCpfcnpj.setBounds(311, 39, 66, 14);
		contentPanel.add(lblCpfcnpj);

		JLabel lblEndereo = new JLabel("Endere\u00E7o :");
		lblEndereo.setBounds(311, 74, 76, 14);
		contentPanel.add(lblEndereo);

		JLabel lblTelefone = new JLabel("Telefone :");
		lblTelefone.setBounds(20, 117, 66, 14);
		contentPanel.add(lblTelefone);

		txtcodcfo = new JTextField();
		txtcodcfo.setBounds(132, 36, 86, 24);
		contentPanel.add(txtcodcfo);
		txtcodcfo.setColumns(10);

		txtNome = new JTextField();
		txtNome.setBounds(132, 74, 86, 24);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);

		txtCGCCFO = new JTextField();
		txtCGCCFO.setBounds(423, 36, 86, 24);
		contentPanel.add(txtCGCCFO);
		txtCGCCFO.setColumns(10);

		txtEndereco = new JTextField();
		txtEndereco.setBounds(423, 71, 86, 24);
		contentPanel.add(txtEndereco);
		txtEndereco.setColumns(10);

		txtTelefone = new JTextField();
		txtTelefone.setBounds(132, 114, 86, 24);
		contentPanel.add(txtTelefone);
		txtTelefone.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 227, 812, 316);
		contentPanel.add(scrollPane);

		tblCFO = new JTable();
		tblCFO.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"C\u00F3digo", "Nome", "CPF/CNPJ", "Endereco", "Telefone" }));
		scrollPane.setViewportView(tblCFO);
		{
			buttonPane = new JPanel();
			buttonPane.setBounds(456, 177, 378, 38);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				// TODO Bot�o Cancela
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});

				// TODO Bot�o Insert
				JButton btnInsert = new JButton("Insert");
				btnInsert.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						try {
							// cria instancia que ser� incluida no banco.
							FinCFOClass novoCliente = new FinCFOClass();

							// seta as variaveis
							novoCliente.setCODCFO(Integer.parseInt(txtcodcfo
									.getText()));
							novoCliente.setNOME(txtNome.getText());
							novoCliente.setCGCCFO(txtCGCCFO.getText());
							novoCliente.setENDERECO(txtEndereco.getText());
							novoCliente.setTELEFONE(txtTelefone.getText());

							// insere cliente no banco
							novoCliente.cadastrar();

							//atualiza a tela.
							novoCliente.selecionaValores(tblCFO);
							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null,
									"ERRO:" + e.getMessage());
						}

					}
				});
				buttonPane.add(btnInsert);

				// TODO Bot�o Load
				JButton btnLoad = new JButton("Load");
				btnLoad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						FinCFOClass novoCliFor = new FinCFOClass();

						novoCliFor.selecionaValores(tblCFO);

					}
				});

				// TODO Bot�o deleta
				JButton btnDelete = new JButton("Delete");
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						FinCFOClass novoCliFor = new FinCFOClass();
						novoCliFor.deleta(tblCFO);
						novoCliFor.selecionaValores(tblCFO);
					}
				});
				
				// TODO Bot�o Editar
				JButton btnEditar = new JButton("Editar");
				btnEditar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					
						//buttonPane fica invisivel
						buttonPane.setVisible(false);
						//aparece bot�o para salvar o Editado.
						btnAtualizar.setVisible(true);
						btnCancelar.setVisible(true);	
												
						//muda o campo de IDLAN para disable
						txtcodcfo.setEnabled(false);
						
						try {
							//cria conex�o para pegar os campos do cli/for
							Conexao acessaBanco = new Conexao();
							
							//busca os dados do cliente e p�e os mesmos no objeto rs.
							ResultSet rs = acessaBanco.aplicaQueryComRetorno("SELECT * FROM FCFO WHERE CODCFO = "+tblCFO.getValueAt(
									tblCFO.getSelectedRow(), 0));
							
							rs.next();
							//atualiza os campos da tela com os dados do que foi retornado na consulta sql.
							txtcodcfo.setText(rs.getString("CODCFO"));
							txtNome.setText(rs.getString("NOME"));
							txtCGCCFO.setText(rs.getString("CGCCFO"));
							txtEndereco.setText(rs.getString("ENDERECO"));
							txtTelefone.setText(rs.getString("TELEFONE"));
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				buttonPane.add(btnEditar);
				buttonPane.add(btnDelete);
				buttonPane.add(btnLoad);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		// TODO Bot�o Atualizar
		btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				//buttonPane fica visivel
				buttonPane.setVisible(true);
				//desaparece bot�o para salvar o Editado.
				btnAtualizar.setVisible(false);
				btnCancelar.setVisible(false);
				
				//cria objeto para receber novos valores.
				FinCFOClass novoCFO = new FinCFOClass();
				
				//atualiza os novos dados para o objeto que ser� atualizado.
				novoCFO.setCGCCFO(txtCGCCFO.getText());
				novoCFO.setENDERECO(txtEndereco.getText());
				novoCFO.setNOME(txtNome.getText());
				novoCFO.setTELEFONE(txtTelefone.getText());
				
				//chama o metodo par atualizar.
				novoCFO.atualiza(tblCFO);
				
				//atualiza a tela.
				novoCFO.selecionaValores(tblCFO);
				
				//depois de atualizar poe os campos como vazios.
				txtCGCCFO.setText("");
				txtcodcfo.setText("");
				txtEndereco.setText("");
				txtNome.setText("");
				txtTelefone.setText("");
				
				txtcodcfo.setEnabled(true);
				
			
			}
		});
		btnAtualizar.setVisible(false);
		btnAtualizar.setBounds(20, 157, 90, 28);
		contentPanel.add(btnAtualizar);
		
		// TODO Bot�o Cancelar
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				//buttonPane fica visivel
				buttonPane.setVisible(true);
				//desaparece bot�o para salvar o Editado.
				btnAtualizar.setVisible(false);
				btnCancelar.setVisible(false);
				
				//volta como era antes.
				txtCGCCFO.setText("");
				txtcodcfo.setText("");
				txtEndereco.setText("");
				txtNome.setText("");
				txtTelefone.setText("");
				
				//volta com o campo como habilitado para edi��o.
				txtcodcfo.setEnabled(true);
			
			
			}
		});
		btnCancelar.setBounds(128, 157, 90, 28);
		btnCancelar.setVisible(false);
		contentPanel.add(btnCancelar);
	}
}