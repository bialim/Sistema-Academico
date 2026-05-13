package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;

public class TelaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtRgm, txtNome, txtEmail, txtEnd, txtMunicipio;
	private JFormattedTextField txtCpf, txtDataNasc, txtCelular;
	private JComboBox<String> cbUf;

	public TelaGUI() throws Exception {
		setFont(new Font("Arial", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 450);
		
		// Menu
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
		
		// Painel principal com layout absoluto
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
		
		cbUf = new JComboBox<>(new String[]{"SP", "RJ", "MG", "ES", "BA", "PR", "SC", "RS"});
		cbUf.setBounds(300, 273, 60, 27);
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
		
		
		// ========== Aba Notas e Faltas ==========
		JPanel notasPanel = new JPanel();
		notasPanel.setLayout(null);
		tabbedPane.addTab("Notas e Faltas", notasPanel);
		

		
		// ========== Aba Boletim ==========
		JPanel boletimPanel = new JPanel();
		boletimPanel.setLayout(null);
		tabbedPane.addTab("Boletim", boletimPanel);
		
		
	}
	
}