package view;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import java.awt.Font;
public class TelaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_1;
	private JTextField textField_3;
	private JTextField textField_4;


	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public TelaGUI() throws Exception {
		setFont(new Font("Arial", Font.PLAIN, 14));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 605, 375);
		
		//----Menu
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
		mntmSairAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
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
		
		//Painel 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		//Abas utilizando JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		contentPane.add(tabbedPane);
		
		//-------Aba de Dados Pessoais------
		JPanel dadosPanel = new JPanel();
		tabbedPane.addTab("Dados Pessoais", null, dadosPanel, null);
		
		JLabel lblNewLabel = new JLabel("RGM");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		textField.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1 = new JLabel("CPF");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JFormattedTextField formattedTextField = new JFormattedTextField(new MaskFormatter ("###.###.###-##"));
		
		JLabel lblNewLabel_2 = new JLabel("Data de Nascimento");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JFormattedTextField formattedTextField_1 = new JFormattedTextField(new MaskFormatter("##/##/####"));
		formattedTextField_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("End.");
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lblNewLabel_5 = new JLabel("Município");
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lblNewLabel_6 = new JLabel("UF");
		lblNewLabel_6.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		
		JLabel lblNewLabel_7 = new JLabel("Celular");
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JFormattedTextField formattedTextField_2 = new JFormattedTextField(new MaskFormatter ("(##)#####-####"));
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		textField_1.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		textField_4.setColumns(10);
		
		JComboBox comboBox = new JComboBox();
		
		//----Posicoes dos labes e fields no panel
		GroupLayout gl_dadosPanel = new GroupLayout(dadosPanel);
		gl_dadosPanel.setHorizontalGroup(
			gl_dadosPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dadosPanel.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_dadosPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_dadosPanel.createSequentialGroup()
							.addGroup(gl_dadosPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_dadosPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(formattedTextField_1, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_dadosPanel.createSequentialGroup()
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
									.addGap(101)))
							.addGap(15)
							.addGroup(gl_dadosPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1)
								.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_dadosPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_dadosPanel.createSequentialGroup()
									.addComponent(formattedTextField, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))
						.addGroup(gl_dadosPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_dadosPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_dadosPanel.createSequentialGroup()
									.addComponent(lblNewLabel_4)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_dadosPanel.createSequentialGroup()
									.addComponent(lblNewLabel_3)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_dadosPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_5)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(26)
							.addComponent(lblNewLabel_6)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_7)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(formattedTextField_2, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)))
					.addGap(26))
		);
		gl_dadosPanel.setVerticalGroup(
			gl_dadosPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_dadosPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_dadosPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_dadosPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(formattedTextField_1, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(formattedTextField, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_dadosPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_dadosPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addGroup(gl_dadosPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_5)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_6)
						.addComponent(lblNewLabel_7)
						.addComponent(formattedTextField_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		
		dadosPanel.setLayout(gl_dadosPanel);
		
		//Aba do Curso
		JPanel cursoPanel = new JPanel();
		tabbedPane.addTab("Curso", null, cursoPanel, null);
		
		JPanel notasPanel = new JPanel();
		tabbedPane.addTab("Notas e Faltas", null, notasPanel, null);
		
		JPanel boletimPanel = new JPanel();
		tabbedPane.addTab("Boletim", null, boletimPanel, null);
		
		
		
		
		

	}
}
