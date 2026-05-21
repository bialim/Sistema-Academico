package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import util.ConnectionFactory;

import java.awt.*;
import java.awt.event.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

	public TelaGUI() throws Exception {
		setFont(new Font("Arial", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450);

		// =========Menu===========
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAluno = new JMenu("Aluno");
		menuBar.add(mnAluno);

		JMenuItem mntmSalvarAluno = new JMenuItem("Salvar");
		mntmSalvarAluno.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mnAluno.add(mntmSalvarAluno);

		JMenuItem mntmAlterarAluno = new JMenuItem("Alterar");
		mnAluno.add(mntmAlterarAluno);

		JMenuItem mntmConsultarAluno = new JMenuItem("Consultar");
		mnAluno.add(mntmConsultarAluno);

		JMenuItem mntmExcluirAluno = new JMenuItem("Excluir");
		mnAluno.add(mntmExcluirAluno);

		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(45, 130, 255));
		mnAluno.add(separator);

		JMenuItem mntmSairAluno = new JMenuItem("Sair");
		mntmSairAluno.addActionListener(e -> System.exit(0));
		mntmSairAluno.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.SHIFT_DOWN_MASK));
		mnAluno.add(mntmSairAluno);

		JMenu mnNotasFaltas = new JMenu("Notas e Faltas");
		menuBar.add(mnNotasFaltas);

		JMenuItem mntmSalvarNotasFaltas = new JMenuItem("Salvar");
		mnNotasFaltas.add(mntmSalvarNotasFaltas);

		JMenuItem mntmAlterarNotasFaltas = new JMenuItem("Alterar");
		mntmAlterarNotasFaltas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
		mnNotasFaltas.add(mntmAlterarNotasFaltas);

		JMenuItem mntmExcluirNotasFaltas = new JMenuItem("Excluir");
		mnNotasFaltas.add(mntmExcluirNotasFaltas);

		JMenuItem mntmConsultarNotasFaltas = new JMenuItem("Consultar");
		mnNotasFaltas.add(mntmConsultarNotasFaltas);

		JMenu mnAjuda = new JMenu("Ajuda");
		menuBar.add(mnAjuda);

		JMenuItem mntmSobreAjuda = new JMenuItem("Sobre");
		mnAjuda.add(mntmSobreAjuda);

		// Painel principal
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null); // Layout absoluto
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
		lblCpf.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
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

		cbUf = new JComboBox<>(new String[] { "SP", "RJ", "MG", "ES", "BA", "PR", "SC", "RS" });
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

		// ========== Aba Curso ==========
		JPanel cursoPanel = new JPanel();
		cursoPanel.setLayout(null);
		tabbedPane.addTab("Curso", cursoPanel);

		// Curso
		JLabel lblCurso = new JLabel("Curso");
		lblCurso.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblCurso.setBounds(44, 39, 41, 16);
		cursoPanel.add(lblCurso);

		cbCurso = new JComboBox<>(new String[] { "Enfermagem", "Biomedicina", "Educação Física", "Direito",
				"Psicologia", "Ciência da Computação", "Análise e Desenvolvimento de Sistemas", "Administração",
				"Ciências Contábeis" });
		cbCurso.setBounds(116, 39, 426, 21);
		cursoPanel.add(cbCurso);

		// Campus
		JLabel lblNewLabel = new JLabel("Campus");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel.setBounds(44, 103, 61, 16);
		cursoPanel.add(lblNewLabel);

		cbCampus = new JComboBox<>(new String[] { "Tatuapé", "Villa-Lobos" });
		cbCampus.setBounds(116, 104, 426, 19);
		cursoPanel.add(cbCampus);

		// Periodo
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

		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(
			    new ImageIcon("imgs/sair.png")
			);
		btnNewButton.setBounds(36, 225, 92, 75);
		cursoPanel.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setIcon(
			    new ImageIcon("imgs/consultar.png")
			);
		btnNewButton_1.setBounds(140, 225, 92, 75);
		cursoPanel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setIcon(
			    new ImageIcon("imgs/salvar.png")
			);
		btnNewButton_2.setBounds(244, 225, 92, 75);
		cursoPanel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setIcon(
			    new ImageIcon("imgs/jar.png")
			);
		btnNewButton_3.setBounds(348, 225, 92, 75);
		cursoPanel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setIcon(new ImageIcon("imgs/info.png"));
		btnNewButton_4.setBounds(452, 225, 92, 75);
		cursoPanel.add(btnNewButton_4);

		// ========== Aba Notas e Faltas ==========
		JPanel notasPanel = new JPanel();
		notasPanel.setLayout(null);
		tabbedPane.addTab("Notas e Faltas", notasPanel);

		// ========== Aba Boletim ==========
		JPanel boletimPanel = new JPanel();
		boletimPanel.setLayout(null);
		tabbedPane.addTab("Boletim", boletimPanel);

		// DADOS DO ALUNO 

		// todos jtext tirei p puxar do banco
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

		//TABELA

		String colunas[] = { "Disciplina", "Nota 1", "Nota 2", "Média", "Faltas", "Situação" };

		String dados[][] = {};

		// mudado
		DefaultTableModel modelo = new DefaultTableModel(dados, colunas);

		tabela = new JTable(modelo);

		JScrollPane scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(20, 109, 400, 100);
		boletimPanel.add(scrollPane);

		//  RESUMO 

		JLabel lblMediaGeral = new JLabel("Média Geral: 7.5");
		lblMediaGeral.setBounds(20, 250, 150, 25);
		boletimPanel.add(lblMediaGeral);

		JLabel lblFaltas = new JLabel("Total de Faltas: 6");
		lblFaltas.setBounds(200, 250, 150, 25);
		boletimPanel.add(lblFaltas);

		// botao buscar
		JButton btnBuscar = new JButton("Buscar");

		btnBuscar.setBounds(450, 20, 100, 25);

		boletimPanel.add(btnBuscar);

		btnBuscar.addActionListener(e -> {

			buscarBoletim(txtRgmBo.getText());

		});

	}

	// método buscar
	private void buscarBoletim(String rgm) {

		try {

			Connection conn = ConnectionFactory.getConnection();

			// ========================
			// DADOS DO ALUNO
			// ========================

			String sqlAluno = "SELECT a.nome, c.nome_curso " + "FROM tb_aluno a " + "INNER JOIN tb_curso c "
					+ "ON a.id_curso = c.id_curso " + "WHERE a.rgm = ?";

			PreparedStatement psAluno = conn.prepareStatement(sqlAluno);

			psAluno.setString(1, rgm);

			ResultSet rsAluno = psAluno.executeQuery();

			if (rsAluno.next()) {

				txtNomeBo.setText(rsAluno.getString("nome"));

				txtCursoBo.setText(rsAluno.getString("nome_curso"));
			}

			// =========================
			// NOTAS
			// =========================

			String sqlNotas =

					"SELECT d.nome_disciplina, " + "n.nota, n.faltas " +

							"FROM tb_notas_faltas n " +

							"INNER JOIN tb_disciplina d " +

							"ON n.id_disciplina = d.id_disciplina " +

							"WHERE n.rgm = ?";

			PreparedStatement psNotas = conn.prepareStatement(sqlNotas);

			psNotas.setString(1, rgm);

			ResultSet rsNotas = psNotas.executeQuery();

			DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();

			modelo.setRowCount(0);

			while (rsNotas.next()) {

				double nota = rsNotas.getDouble("nota");

				String situacao = nota >= 6 ? "Aprovado" : "Reprovado";

				modelo.addRow(new Object[] {

						rsNotas.getString("nome_disciplina"), nota, "-", nota, rsNotas.getInt("faltas"), situacao });
			}

			conn.close();

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}
	}

	// metodo main
	public static void main(String[] args) throws Exception {
		TelaGUI tela = new TelaGUI();
		tela.setVisible(true);
	}
}