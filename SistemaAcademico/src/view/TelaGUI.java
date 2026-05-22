package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.Aluno;
import model.NotasFaltas;
import dao.AlunoDAO;
import util.ConnectionFactory;

import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import dao.CursoDAO;
import dao.NotasFaltasDAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.table.DefaultTableModel;

public class TelaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRgm, txtNome, txtEmail, txtEnd, txtMunicipio;
	private JFormattedTextField txtCpf, txtDataNasc, txtCelular;
	private JComboBox<String> cbUf, cbCurso, cbCampus;

	private JTable tabela;

	private JTextField txtRgmBo;
	private JTextField txtNomeBo;
	private JTextField txtCursoBo;
	private JTextField txtRgmNF, txtSemestreNF, txtA1NF, txtA2NF, txtAfNF, txtFaltasNF;
	private JComboBox<String> cbDisciplinaNF;
	private JTextField textField, textField_1;
	private JButton btnSalvarNF, btnConsultarNF, btnAlterarNF, btnExcluirNF;

	public TelaGUI() throws Exception {
		setFont(new Font("Arial", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450);

		// carrega a lista de ufs
		String[] ufsBanco = carregarUfsBanco();

		// =========Menu===========
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAluno = new JMenu("Aluno");
		menuBar.add(mnAluno);

		JMenuItem mntmSalvarAluno = new JMenuItem("Salvar");
		mntmSalvarAluno.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mnAluno.add(mntmSalvarAluno);
		mntmSalvarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgm.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "O campo RGM é obrigatório para salvar.");
						return;
					}
					Aluno aluno = new Aluno();
					aluno.setRgm(Integer.parseInt(txtRgm.getText().trim()));
					aluno.setNome(txtNome.getText());
					aluno.setCpf(txtCpf.getText().replaceAll("[.-]", "").trim());
					aluno.setEmail(txtEmail.getText());
					aluno.setEndereco(txtEnd.getText());
					aluno.setMunicipio(txtMunicipio.getText());
					aluno.setUf(cbUf.getSelectedItem().toString());
					aluno.setCelular(txtCelular.getText());
					aluno.setIdCurso(1);

					String dataTxt = txtDataNasc.getText().replace("/", "").trim();
					if (!dataTxt.isEmpty()) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						aluno.setDataNascimento(LocalDate.parse(txtDataNasc.getText(), formatter));
					}

					AlunoDAO dao = new AlunoDAO();
					dao.salvar(aluno);
					JOptionPane.showMessageDialog(null, "Aluno salvo com sucesso pelo menu!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar aluno pelo menu: " + ex.getMessage());
				}
			}
		});

		JMenuItem mntmAlterarAluno = new JMenuItem("Alterar");
		mnAluno.add(mntmAlterarAluno);
		mntmAlterarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaAlteracao(ufsBanco); // passa a lista de ufs atualizada para a sub-tela
			}
		});

		JMenuItem mntmConsultarAluno = new JMenuItem("Consultar");
		mnAluno.add(mntmConsultarAluno);
		mntmConsultarAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String rgmStr = JOptionPane.showInputDialog(null, "Digite o RGM do aluno para consultar:");
					if (rgmStr == null || rgmStr.trim().isEmpty()) {
						return;
					}

					int rgm = Integer.parseInt(rgmStr.trim());
					AlunoDAO dao = new AlunoDAO();
					Aluno aluno = dao.consultarPorRgm(rgm);

					if (aluno != null) {
						txtRgm.setText(String.valueOf(aluno.getRgm()));
						txtNome.setText(aluno.getNome());
						txtCpf.setText(aluno.getCpf());
						txtEmail.setText(aluno.getEmail());
						txtEnd.setText(aluno.getEndereco());
						txtMunicipio.setText(aluno.getMunicipio());
						cbUf.setSelectedItem(aluno.getUf());
						txtCelular.setText(aluno.getCelular());

						if (aluno.getDataNascimento() != null) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							txtDataNasc.setText(aluno.getDataNascimento().format(formatter));
						} else {
							txtDataNasc.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nenhum aluno localizado com o RGM informado.");
					}
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "O RGM informado deve conter apenas números.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao consultar pelo menu: " + ex.getMessage());
				}
			}
		});

		JMenuItem mntmExcluirAluno = new JMenuItem("Excluir");
		mnAluno.add(mntmExcluirAluno);
		mntmExcluirAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String rgmStr = JOptionPane.showInputDialog(null, "Digite o RGM do aluno que deseja EXCLUIR:");
					if (rgmStr == null || rgmStr.trim().isEmpty()) {
						return;
					}

					int rgm = Integer.parseInt(rgmStr.trim());
					int confirmacao = JOptionPane.showConfirmDialog(null,
							"Deseja realmente remover o aluno de RGM " + rgm + "?", "Confirmar Exclusão",
							JOptionPane.YES_NO_OPTION);

					if (confirmacao == JOptionPane.YES_OPTION) {
						AlunoDAO dao = new AlunoDAO();
						dao.excluir(rgm);

						JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.");

						txtRgm.setText("");
						txtNome.setText("");
						txtCpf.setText("");
						txtDataNasc.setText("");
						txtEmail.setText("");
						txtEnd.setText("");
						txtMunicipio.setText("");
						cbUf.setSelectedIndex(0);
						txtCelular.setText("");
					}
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "O RGM informado deve conter apenas números.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao excluir pelo menu: " + ex.getMessage());
				}
			}
		});
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(45, 130, 255));
		mnAluno.add(separator);

		JMenuItem mntmSairAluno = new JMenuItem("Sair");
		mntmSairAluno.addActionListener(e -> System.exit(0));
		mntmSairAluno.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK));
		mnAluno.add(mntmSairAluno);

		// menu notas e faltas com CRUD
		// ========== Menu Notas e Faltas com CRUD ==========
		JMenu mnNotasFaltas = new JMenu("Notas e Faltas");
		menuBar.add(mnNotasFaltas);

		// --- SALVAR NOTAS E FALTAS ---
		JMenuItem mntmSalvarNotasFaltas = new JMenuItem("Salvar");
		mnNotasFaltas.add(mntmSalvarNotasFaltas);
		mntmSalvarNotasFaltas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgmNF.getText().trim().isEmpty() || txtSemestreNF.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Preencha ao menos o RGM e o Semestre na aba antes de salvar pelo menu.");
						return;
					}
					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione uma disciplina na aba.");
						return;
					}

					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();
					int rgm = Integer.parseInt(txtRgmNF.getText().trim());
					int semestre = Integer.parseInt(txtSemestreNF.getText().trim());

					double a1 = txtA1NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA1NF.getText().trim());
					double a2 = txtA2NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA2NF.getText().trim());
					double af = txtAfNF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtAfNF.getText().trim());
					int faltas = txtFaltasNF.getText().trim().isEmpty() ? 0
							: Integer.parseInt(txtFaltasNF.getText().trim());

					double media = a1 + a2;
					String status = (media >= 6.0 || af >= 6.0) ? "Aprovado" : (media >= 5.0 ? "AF" : "Reprovado");

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);

					NotasFaltas nf = new NotasFaltas();
					nf.setRgm(rgm);
					nf.setIdDisciplina(idDisciplina);
					nf.setSemestre(semestre);
					nf.setA1(a1);
					nf.setA2(a2);
					nf.setMedia(media);
					nf.setAf(af);
					nf.setFaltas(faltas);
					nf.setStatusAluno(status);

					dao.salvar(nf);
					JOptionPane.showMessageDialog(null, "Notas e faltas salvas com sucesso pelo menu!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar pelo menu: " + ex.getMessage());
				}
			}
		});

		// --- ALTERAR NOTAS E FALTAS ---
		JMenuItem mntmAlterarNotasFaltas = new JMenuItem("Alterar");
		mntmAlterarNotasFaltas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mnNotasFaltas.add(mntmAlterarNotasFaltas);
		mntmAlterarNotasFaltas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgmNF.getText().trim().isEmpty() || txtSemestreNF.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Preencha o RGM e o Semestre na aba para poder alterar.");
						return;
					}
					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();
					int rgm = Integer.parseInt(txtRgmNF.getText().trim());
					int semestre = Integer.parseInt(txtSemestreNF.getText().trim());

					double a1 = txtA1NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA1NF.getText().trim());
					double a2 = txtA2NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA2NF.getText().trim());
					double af = txtAfNF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtAfNF.getText().trim());
					int faltas = txtFaltasNF.getText().trim().isEmpty() ? 0
							: Integer.parseInt(txtFaltasNF.getText().trim());

					double media = a1 + a2;
					String status = (media >= 6.0 || af >= 6.0) ? "Aprovado" : (media >= 5.0 ? "AF" : "Reprovado");

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);

					NotasFaltas nf = new NotasFaltas();
					nf.setRgm(rgm);
					nf.setIdDisciplina(idDisciplina);
					nf.setSemestre(semestre);
					nf.setA1(a1);
					nf.setA2(a2);
					nf.setMedia(media);
					nf.setAf(af);
					nf.setFaltas(faltas);
					nf.setStatusAluno(status);

					dao.alterar(nf);
					JOptionPane.showMessageDialog(null, "Registro alterado com sucesso pelo menu!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao alterar pelo menu: " + ex.getMessage());
				}
			}
		});

		// --- EXCLUIR NOTAS E FALTAS ---
		JMenuItem mntmExcluirNotasFaltas = new JMenuItem("Excluir");
		mnNotasFaltas.add(mntmExcluirNotasFaltas);
		mntmExcluirNotasFaltas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String rgmStr = JOptionPane.showInputDialog(null, "Digite o RGM do registro que deseja EXCLUIR:");
					if (rgmStr == null || rgmStr.trim().isEmpty())
						return;

					String semStr = JOptionPane.showInputDialog(null, "Digite o Semestre correspondente:");
					if (semStr == null || semStr.trim().isEmpty())
						return;

					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione a disciplina correspondente na caixa da aba.");
						return;
					}

					int rgm = Integer.parseInt(rgmStr.trim());
					int semestre = Integer.parseInt(semStr.trim());
					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);

					int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir as notas deste aluno?",
							"Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						dao.excluir(rgm, idDisciplina, semestre);
						JOptionPane.showMessageDialog(null, "Registro deletado com sucesso.");

						// Limpa os campos da aba
						txtRgmNF.setText("");
						txtSemestreNF.setText("");
						txtA1NF.setText("");
						txtA2NF.setText("");
						txtAfNF.setText("");
						txtFaltasNF.setText("");
						textField.setText("");
						textField_1.setText("");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao excluir pelo menu: " + ex.getMessage());
				}
			}
		});

		// --- CONSULTAR NOTAS E FALTAS ---
		JMenuItem mntmConsultarNotasFaltas = new JMenuItem("Consultar");
		mnNotasFaltas.add(mntmConsultarNotasFaltas);
		mntmConsultarNotasFaltas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String rgmStr = JOptionPane.showInputDialog(null, "Digite o RGM para consultar Notas:");
					if (rgmStr == null || rgmStr.trim().isEmpty())
						return;

					String semStr = JOptionPane.showInputDialog(null, "Digite o Semestre:");
					if (semStr == null || semStr.trim().isEmpty())
						return;

					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null,
								"Selecione a disciplina que quer consultar na lista da aba.");
						return;
					}

					int rgm = Integer.parseInt(rgmStr.trim());
					int semestre = Integer.parseInt(semStr.trim());
					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);
					NotasFaltas nf = dao.consultar(rgm, idDisciplina, semestre);

					if (nf != null) {
						txtRgmNF.setText(String.valueOf(rgm));
						txtSemestreNF.setText(String.valueOf(semestre));
						txtA1NF.setText(String.valueOf(nf.getA1()));
						txtA2NF.setText(String.valueOf(nf.getA2()));
						txtAfNF.setText(String.valueOf(nf.getAf()));
						txtFaltasNF.setText(String.valueOf(nf.getFaltas()));

						AlunoDAO alunoDAO = new AlunoDAO();
						Aluno aluno = alunoDAO.consultarPorRgm(rgm);
						if (aluno != null) {
							textField.setText(aluno.getNome());
						} else {
							textField.setText("Aluno não cadastrado");
						}

						JOptionPane.showMessageDialog(null, "Notas localizadas! Status: " + nf.getStatusAluno());
					} else {
						JOptionPane.showMessageDialog(null, "Nenhum registro de notas encontrado para esses dados.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao consultar pelo menu: " + ex.getMessage());
				}
			}
		});

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

		JMenuItem mntmSobreAjuda = new JMenuItem("Sobre");
		mnAjuda.add(mntmSobreAjuda);

		// Painel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 614, 370);
		contentPane.add(tabbedPane);

		// ========== Aba Dados Pessoais ==========
		JPanel dadosPanel = new JPanel();
		dadosPanel.setLayout(null);
		tabbedPane.addTab("Dados Pessoais", dadosPanel);

		// RGM
		JLabel lblRgm = new JLabel("RGM");
		lblRgm.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblRgm.setBounds(20, 53, 50, 25);
		dadosPanel.add(lblRgm);

		txtRgm = new JTextField();
		txtRgm.setBounds(60, 53, 150, 25);
		dadosPanel.add(txtRgm);

		// Nome
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNome.setBounds(244, 53, 50, 25);
		dadosPanel.add(lblNome);

		txtNome = new JTextField();
		txtNome.setBounds(288, 53, 280, 25);
		dadosPanel.add(txtNome);

		// CPF
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
		lblCpf.setBounds(300, 119, 50, 25);
		dadosPanel.add(lblCpf);

		txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		txtCpf.setBounds(348, 119, 150, 25);
		dadosPanel.add(txtCpf);

		// Data de Nascimento
		JLabel lblDataNasc = new JLabel("Data de Nascimento");
		lblDataNasc.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblDataNasc.setBounds(20, 119, 150, 25);
		dadosPanel.add(lblDataNasc);

		txtDataNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
		txtDataNasc.setBounds(159, 119, 100, 25);
		dadosPanel.add(txtDataNasc);

		// Email
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEmail.setBounds(20, 178, 50, 25);
		dadosPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(60, 178, 360, 25);
		dadosPanel.add(txtEmail);

		// Endereço
		JLabel lblEnd = new JLabel("End.");
		lblEnd.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblEnd.setBounds(20, 227, 50, 25);
		dadosPanel.add(lblEnd);

		txtEnd = new JTextField();
		txtEnd.setBounds(60, 227, 360, 25);
		dadosPanel.add(txtEnd);

		// Município
		JLabel lblMunicipio = new JLabel("Município");
		lblMunicipio.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblMunicipio.setBounds(20, 273, 80, 25);
		dadosPanel.add(lblMunicipio);

		txtMunicipio = new JTextField();
		txtMunicipio.setBounds(95, 273, 150, 25);
		dadosPanel.add(txtMunicipio);

		// UF
		JLabel lblUf = new JLabel("UF");
		lblUf.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblUf.setBounds(267, 273, 40, 25);
		dadosPanel.add(lblUf);

		// puxando a uf do banco na lista
		cbUf = new JComboBox<>(ufsBanco);
		cbUf.setBounds(288, 274, 72, 27);
		dadosPanel.add(cbUf);

		// Celular
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCelular.setBounds(372, 273, 60, 25);
		dadosPanel.add(lblCelular);

		txtCelular = new JFormattedTextField(new MaskFormatter("(##)#####-####"));
		txtCelular.setBounds(432, 273, 130, 25);
		dadosPanel.add(txtCelular);

		// botões do CRUD com as funcionalidades
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(25, 312, 100, 30);
		dadosPanel.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgm.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "O campo RGM é obrigatório.");
						return;
					}

					Aluno aluno = new Aluno();
					aluno.setRgm(Integer.parseInt(txtRgm.getText().trim()));
					aluno.setNome(txtNome.getText());
					aluno.setCpf(txtCpf.getText().replaceAll("[.-]", "").trim());
					aluno.setEmail(txtEmail.getText());
					aluno.setEndereco(txtEnd.getText());
					aluno.setMunicipio(txtMunicipio.getText());
					aluno.setUf(cbUf.getSelectedItem().toString());
					aluno.setCelular(txtCelular.getText());
					aluno.setIdCurso(1);

					String dataTxt = txtDataNasc.getText().replace("/", "").trim();
					if (!dataTxt.isEmpty()) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						aluno.setDataNascimento(LocalDate.parse(txtDataNasc.getText(), formatter));
					}

					AlunoDAO dao = new AlunoDAO();
					dao.salvar(aluno);

					JOptionPane.showMessageDialog(null, "Aluno salvo com sucesso!");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar aluno: " + ex.getMessage());
				}
			}
		});

		JButton btnEditar = new JButton("Alterar");
		btnEditar.setBounds(135, 312, 100, 30);
		dadosPanel.add(btnEditar);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirTelaAlteracao(ufsBanco);
			}
		});

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(245, 312, 100, 30);
		dadosPanel.add(btnConsultar);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgm.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Insira um RGM para realizar a consulta.");
						return;
					}

					int rgm = Integer.parseInt(txtRgm.getText().trim());
					AlunoDAO dao = new AlunoDAO();
					Aluno aluno = dao.consultarPorRgm(rgm);

					if (aluno != null) {
						txtNome.setText(aluno.getNome());
						txtCpf.setText(aluno.getCpf());
						txtEmail.setText(aluno.getEmail());
						txtEnd.setText(aluno.getEndereco());
						txtMunicipio.setText(aluno.getMunicipio());
						cbUf.setSelectedItem(aluno.getUf());
						txtCelular.setText(aluno.getCelular());

						if (aluno.getDataNascimento() != null) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							txtDataNasc.setText(aluno.getDataNascimento().format(formatter));
						} else {
							txtDataNasc.setText("");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Nenhum aluno localizado com o RGM informado.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao consultar aluno: " + ex.getMessage());
				}
			}
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(355, 312, 100, 30);
		dadosPanel.add(btnExcluir);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgm.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Insira um RGM para realizar a exclusão.");
						return;
					}

					int rgm = Integer.parseInt(txtRgm.getText().trim());

					int confirmacao = JOptionPane.showConfirmDialog(null,
							"Deseja realmente remover este aluno do sistema?", "Confirmar Exclusão",
							JOptionPane.YES_NO_OPTION);
					if (confirmacao == JOptionPane.YES_OPTION) {
						AlunoDAO dao = new AlunoDAO();
						dao.excluir(rgm);

						JOptionPane.showMessageDialog(null, "Aluno removido com sucesso.");

						txtRgm.setText("");
						txtNome.setText("");
						txtCpf.setText("");
						txtDataNasc.setText("");
						txtEmail.setText("");
						txtEnd.setText("");
						txtMunicipio.setText("");
						cbUf.setSelectedIndex(0);
						txtCelular.setText("");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao excluir aluno: " + ex.getMessage());
				}
			}
		});

		JButton btnLimparCampos = new JButton("Limpar");
		btnLimparCampos.setBounds(465, 312, 100, 30);
		dadosPanel.add(btnLimparCampos);
		btnLimparCampos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRgm.setText("");
				txtNome.setText("");
				txtCpf.setText("");
				txtDataNasc.setText("");
				txtEmail.setText("");
				txtEnd.setText("");
				txtMunicipio.setText("");
				cbUf.setSelectedIndex(0);
				txtCelular.setText("");
			}
		});

		// ========== Aba Curso ==========
		JPanel cursoPanel = new JPanel();
		cursoPanel.setLayout(null);
		tabbedPane.addTab("Curso", cursoPanel);

		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCurso.setBounds(44, 39, 41, 16);
		cursoPanel.add(lblCurso);

		// puxando os cursos disponíveis do bancoo
		cbCurso = new JComboBox<>();
		CursoDAO cursoDAO = new CursoDAO();
		for (String curso : cursoDAO.listarCursos()) {
			cbCurso.addItem(curso);
		}
		cbCurso.setBounds(116, 39, 426, 21);
		cursoPanel.add(cbCurso);

		JLabel lblNewLabel = new JLabel("Campus");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel.setBounds(44, 103, 61, 16);
		cursoPanel.add(lblNewLabel);

		cbCampus = new JComboBox<>(new String[] { "Tatuapé", "Villa-Lobos" });
		cbCampus.setBounds(116, 104, 426, 19);
		cursoPanel.add(cbCampus);

		JLabel lblPeriodo = new JLabel("Período");
		lblPeriodo.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblPeriodo.setBounds(44, 163, 61, 16);
		cursoPanel.add(lblPeriodo);

		JRadioButton rdbtnMatutino = new JRadioButton("Matutino");
		rdbtnMatutino.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		rdbtnMatutino.setBounds(131, 160, 101, 23);
		cursoPanel.add(rdbtnMatutino);

		JRadioButton rdbtnVespertino = new JRadioButton("Vespertino");
		rdbtnVespertino.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		rdbtnVespertino.setBounds(257, 160, 107, 23);
		cursoPanel.add(rdbtnVespertino);

		JRadioButton rdbtnNoturno = new JRadioButton("Noturno");
		rdbtnNoturno.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		rdbtnNoturno.setBounds(398, 160, 112, 23);
		cursoPanel.add(rdbtnNoturno);

		ButtonGroup grupoPeriodo = new ButtonGroup();
		grupoPeriodo.add(rdbtnMatutino);
		grupoPeriodo.add(rdbtnVespertino);
		grupoPeriodo.add(rdbtnNoturno);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(44, 225, 92, 75);
		cursoPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Salvar Curso");
		btnNewButton_1.setBounds(176, 225, 92, 75);
		cursoPanel.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String rgmTexto = txtRgm.getText().trim();
					if (rgmTexto.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Por favor, preencha o campo RGM na aba 'Dados Pessoais' primeiro.");
						return;
					}
					int rgm = Integer.parseInt(rgmTexto);

					String cursoSelecionado = cbCurso.getSelectedItem().toString();
					String campusSelecionado = cbCampus.getSelectedItem().toString();

					String periodoSelecionado = "";
					if (rdbtnMatutino.isSelected()) {
						periodoSelecionado = "Matutino";
					} else if (rdbtnVespertino.isSelected()) {
						periodoSelecionado = "Vespertino";
					} else if (rdbtnNoturno.isSelected()) {
						periodoSelecionado = "Noturno";
					} else {
						JOptionPane.showMessageDialog(null, "Selecione um período (Matutino, Vespertino ou Noturno).");
						return;
					}

					Connection conn = ConnectionFactory.getConnection();

					String sqlBuscarCurso = "SELECT id_curso FROM tb_curso WHERE nome_curso = ? AND campus = ? AND periodo_curso = ?";
					PreparedStatement psBuscar = conn.prepareStatement(sqlBuscarCurso);
					psBuscar.setString(1, cursoSelecionado);
					psBuscar.setString(2, campusSelecionado);
					psBuscar.setString(3, periodoSelecionado);
					ResultSet rs = psBuscar.executeQuery();

					int idCursoEncontrado = -1;
					if (rs.next()) {
						idCursoEncontrado = rs.getInt("id_curso");
					}
					rs.close();
					psBuscar.close();

					if (idCursoEncontrado == -1) {
						JOptionPane.showMessageDialog(null,
								"Combinação de Curso, Campus e Período não cadastrada na tabela tb_curso.");
						conn.close();
						return;
					}

					String sqlUpdateAluno = "UPDATE tb_aluno SET id_curso = ? WHERE rgm = ?";
					PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateAluno);
					psUpdate.setInt(1, idCursoEncontrado);
					psUpdate.setInt(2, rgm);

					int linhasAfetadas = psUpdate.executeUpdate();
					psUpdate.close();
					conn.close();

					if (linhasAfetadas > 0) {
						JOptionPane.showMessageDialog(null, "Curso vinculado ao aluno (RGM: " + rgm + ") com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null,
								"Não foi possível vincular o curso. Verifique se o RGM informado existe.");
					}

				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,
							"O RGM informado na aba de dados pessoais deve conter apenas números.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar informações do curso: " + ex.getMessage());
				}
			}
		});

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setBounds(300, 225, 92, 75);
		cursoPanel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(new ImageIcon("/Users/beatriz/Downloads/java-2.png"));
		btnNewButton_3.setBounds(430, 225, 92, 75);
		cursoPanel.add(btnNewButton_3);

		// ========== Aba Notas e Faltas ==========
		JPanel notasPanel = new JPanel();
		notasPanel.setLayout(null);
		tabbedPane.addTab("Notas e Faltas", notasPanel);

		JLabel lblRgmNF = new JLabel("RGM:");
		lblRgmNF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRgmNF.setBounds(20, 20, 35, 25);
		notasPanel.add(lblRgmNF);

		txtRgmNF = new JTextField();
		txtRgmNF.setBounds(67, 20, 90, 25);
		notasPanel.add(txtRgmNF);

		JLabel lblDisciplinaNF = new JLabel("Disciplina:");
		lblDisciplinaNF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDisciplinaNF.setBounds(20, 108, 100, 25);
		notasPanel.add(lblDisciplinaNF);

		cbDisciplinaNF = new JComboBox<>();
		cbDisciplinaNF.setBounds(92, 110, 479, 25);
		notasPanel.add(cbDisciplinaNF);

		try {
			NotasFaltasDAO daoNF = new NotasFaltasDAO();
			for (String disc : daoNF.listarDisciplinas()) {
				cbDisciplinaNF.addItem(disc);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar disciplinas: " + ex.getMessage());
		}

		JLabel lblSemestreNF = new JLabel("Semestre:");
		lblSemestreNF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSemestreNF.setBounds(20, 175, 100, 25);
		notasPanel.add(lblSemestreNF);

		txtSemestreNF = new JTextField();
		txtSemestreNF.setBounds(92, 177, 80, 25);
		notasPanel.add(txtSemestreNF);

		JLabel lblA1NF = new JLabel("A1:");
		lblA1NF.setBounds(20, 230, 114, 25);
		notasPanel.add(lblA1NF);

		txtA1NF = new JTextField();
		txtA1NF.setBounds(40, 230, 80, 25);
		notasPanel.add(txtA1NF);

		JLabel lblA2NF = new JLabel("A2:");
		lblA2NF.setBounds(166, 230, 24, 25);
		notasPanel.add(lblA2NF);

		txtA2NF = new JTextField();
		txtA2NF.setBounds(200, 230, 80, 25);
		notasPanel.add(txtA2NF);

		JLabel lblAfNF = new JLabel("AF:");
		lblAfNF.setBounds(306, 230, 17, 25);
		notasPanel.add(lblAfNF);

		txtAfNF = new JTextField();
		txtAfNF.setBounds(333, 230, 80, 25);
		notasPanel.add(txtAfNF);

		JLabel lblFaltasNF = new JLabel("Faltas:");
		lblFaltasNF.setBounds(446, 230, 35, 25);
		notasPanel.add(lblFaltasNF);

		txtFaltasNF = new JTextField();
		txtFaltasNF.setBounds(491, 230, 80, 25);
		notasPanel.add(txtFaltasNF);

		JButton btnSalvarNF = new JButton("Salvar");
		btnSalvarNF.setBounds(20, 266, 100, 67);
		notasPanel.add(btnSalvarNF);

		JButton btnConsultarNF = new JButton("Consultar");
		btnConsultarNF.setBounds(130, 266, 100, 67);
		notasPanel.add(btnConsultarNF);

		JButton btnAlterarNF = new JButton("Alterar");
		btnAlterarNF.setBounds(240, 266, 100, 67);
		notasPanel.add(btnAlterarNF);

		JButton btnExcluirNF = new JButton("Excluir");
		btnExcluirNF.setBounds(350, 266, 100, 67);
		notasPanel.add(btnExcluirNF);

		JButton btnLimparNF = new JButton("Limpar");
		btnLimparNF.setBounds(471, 266, 100, 67);
		notasPanel.add(btnLimparNF);

		textField = new JTextField();
		textField.setBounds(183, 20, 388, 24);
		notasPanel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(67, 62, 504, 25);
		notasPanel.add(textField_1);
		textField_1.setColumns(10);

		btnSalvarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (txtRgmNF.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Informe o RGM.");
						return;
					}
					if (txtSemestreNF.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Informe o semestre.");
						return;
					}
					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione uma disciplina.");
						return;
					}

					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();
					int rgm = Integer.parseInt(txtRgmNF.getText().trim());
					int semestre = Integer.parseInt(txtSemestreNF.getText().trim());

					double a1 = txtA1NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA1NF.getText().trim());
					double a2 = txtA2NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA2NF.getText().trim());
					double af = txtAfNF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtAfNF.getText().trim());
					int faltas = txtFaltasNF.getText().trim().isEmpty() ? 0
							: Integer.parseInt(txtFaltasNF.getText().trim());

					double media = a1 + a2;

					String status;
					if (media >= 6.0 || af >= 6.0) {
						status = "Aprovado";
					} else if (media >= 5.0) {
						status = "AF";
					} else {
						status = "Reprovado";
					}

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);

					NotasFaltas nf = new NotasFaltas();
					nf.setRgm(rgm);
					nf.setIdDisciplina(idDisciplina);
					nf.setSemestre(semestre);
					nf.setA1(a1);
					nf.setA2(a2);
					nf.setMedia(media);
					nf.setAf(af);
					nf.setFaltas(faltas);
					nf.setStatusAluno(status);

					dao.salvar(nf);
					JOptionPane.showMessageDialog(null, "Notas e faltas salvas com sucesso!");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao salvar: " + ex.getMessage());
				}
			}
		});

		btnConsultarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione uma disciplina.");
						return;
					}

					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();
					int rgm = Integer.parseInt(txtRgmNF.getText().trim());
					int semestre = Integer.parseInt(txtSemestreNF.getText().trim());

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);
					NotasFaltas nf = dao.consultar(rgm, idDisciplina, semestre);

					if (nf != null) {
						txtA1NF.setText(String.valueOf(nf.getA1()));
						txtA2NF.setText(String.valueOf(nf.getA2()));
						txtAfNF.setText(String.valueOf(nf.getAf()));
						txtFaltasNF.setText(String.valueOf(nf.getFaltas()));

						// Busca o nome do aluno no banco de dados usando o RGM informado
						AlunoDAO alunoDAO = new AlunoDAO();
						Aluno aluno = alunoDAO.consultarPorRgm(rgm);
						if (aluno != null) {
							textField.setText(aluno.getNome()); // Preenche o campo de texto ao lado do RGM com o nome
						} else {
							textField.setText("Aluno não encontrado");
						}

						JOptionPane.showMessageDialog(null, "Registro encontrado. Status: " + nf.getStatusAluno());
					} else {
						JOptionPane.showMessageDialog(null, "Registro não encontrado.");
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao consultar: " + ex.getMessage());
				}
			}
		});

		btnAlterarNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione uma disciplina.");
						return;
					}

					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();
					int rgm = Integer.parseInt(txtRgmNF.getText().trim());
					int semestre = Integer.parseInt(txtSemestreNF.getText().trim());

					double a1 = txtA1NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA1NF.getText().trim());
					double a2 = txtA2NF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtA2NF.getText().trim());
					double af = txtAfNF.getText().trim().isEmpty() ? 0.0 : Double.parseDouble(txtAfNF.getText().trim());
					int faltas = txtFaltasNF.getText().trim().isEmpty() ? 0
							: Integer.parseInt(txtFaltasNF.getText().trim());

					double media = a1 + a2;

					String status;
					if (media >= 6.0 || af >= 6.0) {
						status = "Aprovado";
					} else if (media >= 5.0) {
						status = "AF";
					} else {
						status = "Reprovado";
					}

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);

					NotasFaltas nf = new NotasFaltas();
					nf.setRgm(rgm);
					nf.setIdDisciplina(idDisciplina);
					nf.setSemestre(semestre);
					nf.setA1(a1);
					nf.setA2(a2);
					nf.setMedia(media);
					nf.setAf(af);
					nf.setFaltas(faltas);
					nf.setStatusAluno(status);

					dao.alterar(nf);
					JOptionPane.showMessageDialog(null, "Registro alterado com sucesso!");

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao alterar: " + ex.getMessage());
				}
			}
		});

		btnExcluirNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cbDisciplinaNF.getSelectedItem() == null) {
						JOptionPane.showMessageDialog(null, "Selecione uma disciplina.");
						return;
					}

					String disciplinaSelecionada = cbDisciplinaNF.getSelectedItem().toString();
					int rgm = Integer.parseInt(txtRgmNF.getText().trim());
					int semestre = Integer.parseInt(txtSemestreNF.getText().trim());

					NotasFaltasDAO dao = new NotasFaltasDAO();
					int idDisciplina = dao.buscarIdDisciplinaPorNome(disciplinaSelecionada);

					int confirm = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir este registro?",
							"Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

					if (confirm == JOptionPane.YES_OPTION) {
						dao.excluir(rgm, idDisciplina, semestre);
						JOptionPane.showMessageDialog(null, "Registro excluído com sucesso!");
						btnLimparNF.doClick();
					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex.getMessage());
				}
			}
		});

		btnLimparNF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtRgmNF.setText("");
				txtSemestreNF.setText("");
				txtA1NF.setText("");
				txtA2NF.setText("");
				txtAfNF.setText("");
				txtFaltasNF.setText("");
				if (cbDisciplinaNF.getItemCount() > 0) {
					cbDisciplinaNF.setSelectedIndex(0);
				}
			}
		});

		// ========== Aba Boletim ==========
		JPanel boletimPanel = new JPanel();
		boletimPanel.setLayout(null);
		tabbedPane.addTab("Boletim", boletimPanel);

		JLabel lblRgmBo = new JLabel("RGM:");
		lblRgmBo.setBounds(20, 20, 50, 25);
		boletimPanel.add(lblRgmBo);

		txtRgmBo = new JTextField();
		txtRgmBo.setBounds(70, 20, 120, 25);
		boletimPanel.add(txtRgmBo);

		JLabel lblNomeBo = new JLabel("Nome:");
		lblNomeBo.setBounds(220, 20, 50, 25);
		boletimPanel.add(lblNomeBo);

		txtNomeBo = new JTextField();
		txtNomeBo.setBounds(280, 20, 140, 25);
		boletimPanel.add(txtNomeBo);

		JLabel lblCursoBo = new JLabel("Curso:");
		lblCursoBo.setBounds(20, 60, 50, 25);
		boletimPanel.add(lblCursoBo);

		txtCursoBo = new JTextField();
		txtCursoBo.setBounds(70, 60, 120, 25);
		boletimPanel.add(txtCursoBo);

		String colunas[] = { "Disciplina", "Nota 1", "Nota 2", "Média", "Faltas", "Situação" };
		String dados[][] = {};

		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);
		tabela = new JTable(modelo);

		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(20, 109, 400, 100);
		boletimPanel.add(scrollPane);

		JLabel lblMediaGeral = new JLabel("Média Geral: 7.5");
		lblMediaGeral.setBounds(20, 250, 150, 25);
		boletimPanel.add(lblMediaGeral);

		JLabel lblFaltas = new JLabel("Total de Faltas: 6");
		lblFaltas.setBounds(200, 250, 150, 25);
		boletimPanel.add(lblFaltas);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(450, 20, 100, 25);
		boletimPanel.add(btnBuscar);
		btnBuscar.addActionListener(e -> {
			buscarBoletim(txtRgmBo.getText());
		});
	}

	// método que pega na lista os estados do banco
	private String[] carregarUfsBanco() {
		List<String> ufs = new ArrayList<>();
		// caso de erro, preenche aqui com os estados padrão
		String[] padrao = { "SP", "RJ", "MG", "ES", "BA", "PR", "SC", "RS" };
		try {
			Connection conn = ConnectionFactory.getConnection();
			// consulta que puxa por ordem - clóvis
			String sql = "SELECT uf FROM tb_uf ORDER BY uf ASC";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ufs.add(rs.getString("uf"));
			}
			conn.close();

			if (!ufs.isEmpty()) {
				return ufs.toArray(new String[0]);
			}
		} catch (Exception e) {
			System.out.println(
					"Não foi possível carregar as UFs do banco, utilizando lista padrão. Erro: " + e.getMessage());
		}
		return padrao;
	}

	/**
	 * abre uma nova janela, JDialog para inserção e alteração dos dados do Aluno
	 */
	private void abrirTelaAlteracao(String[] ufsDisponiveis) {
		try {
			String rgmStr = JOptionPane.showInputDialog(null, "Digite o RGM do aluno que deseja alterar:");
			if (rgmStr == null || rgmStr.trim().isEmpty()) {
				return;
			}

			int rgm = Integer.parseInt(rgmStr.trim());
			AlunoDAO dao = new AlunoDAO();
			Aluno alunoOriginal = dao.consultarPorRgm(rgm);

			if (alunoOriginal == null) {
				JOptionPane.showMessageDialog(null, "Nenhum aluno localizado com o RGM informado.");
				return;
			}

			JDialog dialogAlterar = new JDialog(this, "Alterar Dados do Aluno", true);
			dialogAlterar.setSize(550, 400);
			dialogAlterar.setLocationRelativeTo(this);
			dialogAlterar.getContentPane().setLayout(null);

			JLabel lblNomeAlt = new JLabel("Nome:");
			lblNomeAlt.setBounds(30, 30, 80, 25);
			dialogAlterar.getContentPane().add(lblNomeAlt);

			JTextField txtNomeAlt = new JTextField(alunoOriginal.getNome());
			txtNomeAlt.setBounds(120, 30, 350, 25);
			dialogAlterar.getContentPane().add(txtNomeAlt);

			JLabel lblCpfAlt = new JLabel("CPF:");
			lblCpfAlt.setBounds(30, 70, 80, 25);
			dialogAlterar.getContentPane().add(lblCpfAlt);

			JFormattedTextField txtCpfAlt = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
			txtCpfAlt.setText(alunoOriginal.getCpf());
			txtCpfAlt.setBounds(120, 70, 150, 25);
			dialogAlterar.getContentPane().add(txtCpfAlt);

			JLabel lblDataAlt = new JLabel("Data Nasc:");
			lblDataAlt.setBounds(290, 70, 80, 25);
			dialogAlterar.getContentPane().add(lblDataAlt);

			JFormattedTextField txtDataAlt = new JFormattedTextField(new MaskFormatter("##/##/####"));
			if (alunoOriginal.getDataNascimento() != null) {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				txtDataAlt.setText(alunoOriginal.getDataNascimento().format(formatter));
			}
			txtDataAlt.setBounds(370, 70, 100, 25);
			dialogAlterar.getContentPane().add(txtDataAlt);

			JLabel lblEmailAlt = new JLabel("Email:");
			lblEmailAlt.setBounds(30, 110, 80, 25);
			dialogAlterar.getContentPane().add(lblEmailAlt);

			JTextField txtEmailAlt = new JTextField(alunoOriginal.getEmail());
			txtEmailAlt.setBounds(120, 110, 350, 25);
			dialogAlterar.getContentPane().add(txtEmailAlt);

			JLabel lblEndAlt = new JLabel("Endereço:");
			lblEndAlt.setBounds(30, 150, 80, 25);
			dialogAlterar.getContentPane().add(lblEndAlt);

			JTextField txtEndAlt = new JTextField(alunoOriginal.getEndereco());
			txtEndAlt.setBounds(120, 150, 350, 25);
			dialogAlterar.getContentPane().add(txtEndAlt);

			JLabel lblMuniAlt = new JLabel("Município:");
			lblMuniAlt.setBounds(30, 190, 80, 25);
			dialogAlterar.getContentPane().add(lblMuniAlt);

			JTextField txtMuniAlt = new JTextField(alunoOriginal.getMunicipio());
			txtMuniAlt.setBounds(120, 190, 150, 25);
			dialogAlterar.getContentPane().add(txtMuniAlt);

			JLabel lblUfAlt = new JLabel("UF:");
			lblUfAlt.setBounds(290, 190, 40, 25);
			dialogAlterar.getContentPane().add(lblUfAlt);

			// Utiliza a lista vinda do banco também na tela secundária
			JComboBox<String> cbUfAlt = new JComboBox<>(ufsDisponiveis);
			cbUfAlt.setSelectedItem(alunoOriginal.getUf());
			cbUfAlt.setBounds(330, 190, 70, 25);
			dialogAlterar.getContentPane().add(cbUfAlt);

			JLabel lblCelAlt = new JLabel("Celular:");
			lblCelAlt.setBounds(30, 230, 80, 25);
			dialogAlterar.getContentPane().add(lblCelAlt);

			JFormattedTextField txtCelAlt = new JFormattedTextField(new MaskFormatter("(##)#####-####"));
			txtCelAlt.setText(alunoOriginal.getCelular());
			txtCelAlt.setBounds(120, 230, 150, 25);
			dialogAlterar.getContentPane().add(txtCelAlt);

			JButton btnConfirmarAlteracao = new JButton("Salvar Alterações");
			btnConfirmarAlteracao.setBounds(180, 290, 180, 35);
			dialogAlterar.getContentPane().add(btnConfirmarAlteracao);

			btnConfirmarAlteracao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Aluno alunoAtualizado = new Aluno();
						alunoAtualizado.setRgm(rgm);
						alunoAtualizado.setNome(txtNomeAlt.getText());
						alunoAtualizado.setCpf(txtCpfAlt.getText().replaceAll("[.-]", "").trim());
						alunoAtualizado.setEmail(txtEmailAlt.getText());
						alunoAtualizado.setEndereco(txtEndAlt.getText());
						alunoAtualizado.setMunicipio(txtMuniAlt.getText());
						alunoAtualizado.setUf(cbUfAlt.getSelectedItem().toString());
						alunoAtualizado.setCelular(txtCelAlt.getText());
						alunoAtualizado.setIdCurso(1);

						String dataTxt = txtDataAlt.getText().replace("/", "").trim();
						if (!dataTxt.isEmpty()) {
							DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
							alunoAtualizado.setDataNascimento(LocalDate.parse(txtDataAlt.getText(), formatter));
						}

						AlunoDAO daoAlterar = new AlunoDAO();
						daoAlterar.alterar(alunoAtualizado);

						JOptionPane.showMessageDialog(dialogAlterar, "Alterado com sucesso!");

						dialogAlterar.dispose();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(dialogAlterar, "Erro ao salvar alterações: " + ex.getMessage());
					}
				}
			});

			dialogAlterar.setVisible(true);

		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(null, "O RGM informado deve conter apenas números.");
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro na operação: " + ex.getMessage());
		}
	}

	// MÉTODO BUSCAR DO BOLETIM
	private void buscarBoletim(String rgm) {
		try {
			Connection conn = ConnectionFactory.getConnection();

			String sqlAluno = "SELECT a.nome, c.nome_curso " + "FROM tb_aluno a "
					+ "INNER JOIN tb_curso c ON a.id_curso = c.id_curso " + "WHERE a.rgm = ?";

			PreparedStatement psAluno = conn.prepareStatement(sqlAluno);
			psAluno.setString(1, rgm);
			ResultSet rsAluno = psAluno.executeQuery();

			if (rsAluno.next()) {
				txtNomeBo.setText(rsAluno.getString("nome"));
				txtCursoBo.setText(rsAluno.getString("nome_curso"));
			} else {
				JOptionPane.showMessageDialog(null, "Aluno não encontrado.");
				conn.close();
				return;
			}

			String sqlNotas = "SELECT d.nome_disciplina, n.a1, n.a2, n.media, n.faltas, n.status_aluno "
					+ "FROM tb_notas_faltas n " + "INNER JOIN tb_disciplina d ON n.id_disciplina = d.id_disciplina "
					+ "WHERE n.rgm = ?";

			PreparedStatement psNotas = conn.prepareStatement(sqlNotas);
			psNotas.setString(1, rgm);
			ResultSet rsNotas = psNotas.executeQuery();

			DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
			modelo.setRowCount(0);

			while (rsNotas.next()) {
				String disciplina = rsNotas.getString("nome_disciplina");
				double nota1 = rsNotas.getDouble("a1");
				double nota2 = rsNotas.getDouble("a2");
				double media = rsNotas.getDouble("media");
				int faltas = rsNotas.getInt("faltas");
				String situacao = rsNotas.getString("status_aluno");

				modelo.addRow(new Object[] { disciplina, nota1, nota2, media, faltas, situacao });
			}

			conn.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao buscar dados do boletim: " + e.getMessage());
		}
	}

	// MÉTODO MAIN
	public static void main(String[] args) throws Exception {
		TelaGUI tela = new TelaGUI();
		tela.setVisible(true);
	}
}