package com.portaria.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableRowSorter;

import com.portaria.model.Porteiro;
import com.portaria.model.TabelaPorteiroModel;
import com.portaria.reports.GeraRelatorio;
import com.portaria.service.PorteiroService;

import net.sf.jasperreports.engine.JRParameter;

public class TabelaPorteiroFrame extends JFrame {

	private static final long serialVersionUID = -352389726581513577L;

	
	private static final int ID       = 0;
	private static final int NOME     = 1;
	private static final int BAIRRO   = 2;
	private static final int CIDADE   = 3;
	private static final int ENDERECO = 4;
	private static final int CEP      = 5;
	private static final int NUMERO   = 6;
	
	private static final int VAZIO   = -1; 

	
	private JPanel contentPane;
	private JTable tabelaPorteiro;
	private JButton btnIncluir;
	private JButton btnBuscar;
	
	private JComboBox<String> comboBox;
	private JButton btnPrimeiro;
	private JButton btnAnterior;
	private JButton btnProximo;
	private JButton btnUltimo;
	
    private TabelaPorteiroModel tabelaPorteiroModel;
    private TableRowSorter<TabelaPorteiroModel> sortTabelaPorteiro;
    
    private JButton btnAlterar;
    private JTextField textFieldNome;
    
	private Integer totalData = 0;
	private Integer defaultPagina = 5;
	private Integer totalPagina = 1;
	private Integer numeroPagina = 1;
	private JButton btnRelatorio;
	private JButton btnRelatorio_1;
    
    
    
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					for ( LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
						if ("Nimbus".equals(laf.getName())){
							UIManager.setLookAndFeel(laf.getClassName());
						} else {
							System.out.println();
						}
						
					}
					TabelaPorteiroFrame frame = new TabelaPorteiroFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TabelaPorteiroFrame() {
		initComponents();
		createEvents();
	}
	
	private void createEvents() {
		btnIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PorteiroFrame porteiroFrame = new PorteiroFrame(new JFrame(), tabelaPorteiro, tabelaPorteiroModel, 0, 0);
				porteiroFrame.setLocationRelativeTo(null);
				porteiroFrame.setVisible(true);
			
			}
		});
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tabelaPorteiro.getSelectedRow()!= VAZIO && tabelaPorteiro.getSelectedRow() < tabelaPorteiroModel.getRowCount()) {
					int linhaSelecionada = tabelaPorteiro.getSelectedRow();
					System.out.println(linhaSelecionada);
					PorteiroFrame porteiroFrame = new PorteiroFrame(new JFrame(), tabelaPorteiro, tabelaPorteiroModel, linhaSelecionada, 1);
					porteiroFrame.setLocationRelativeTo(null);
					porteiroFrame.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null,"Selecione um registro na tabela" ,"Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filtro = textFieldNome.getText();
				filtrarCampos(filtro);
			}
		});
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numeroPagina = 1;
				loadDataPorteiroFromTable();
			}
		});
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numeroPagina > 1) {
					numeroPagina = numeroPagina - 1;
					loadDataPorteiroFromTable();
				}
			}
		});
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if (numeroPagina < totalPagina ) {
					 numeroPagina = numeroPagina + 1;
				     loadDataPorteiroFromTable();
				 }
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numeroPagina = totalPagina;
				loadDataPorteiroFromTable();
			}
		});
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		     	String nomeArquivo = "relatorio_porteiro";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(JRParameter.REPORT_LOCALE, new Locale("pt","BR"));
				GeraRelatorio geraRelatorio = new GeraRelatorio(nomeArquivo, params);
				geraRelatorio.generateReports();
			}
		});
		
		btnRelatorio_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PorteiroService porteiroService = new PorteiroService();
				List<Porteiro> listaPorteiro = porteiroService.listarTodosPorteiros();
				String nomeArquivo = "relatorio_porteiro2";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(JRParameter.REPORT_LOCALE, new Locale("pt","BR"));
				GeraRelatorio geraRelatorio = new GeraRelatorio(nomeArquivo, params, listaPorteiro);
				geraRelatorio.callReport();
			}
		});
	}


	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1129, 587);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		btnIncluir = new JButton("Incluir");
		btnAlterar = new JButton("Alterar");
		
		JLabel lblNome = new JLabel("Nome:");
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
	    btnBuscar = new JButton("Buscar");
		
		JToolBar toolBar = new JToolBar();
		
		btnRelatorio = new JButton("Relatório");
		
		btnRelatorio_1 = new JButton("Relatorio_2");
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNome)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textFieldNome, GroupLayout.PREFERRED_SIZE, 638, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBuscar)
							.addGap(27)
							.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnIncluir)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAlterar)
							.addGap(133)
							.addComponent(btnRelatorio)
							.addGap(31)
							.addComponent(btnRelatorio_1))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 946, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(109, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(36)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNome)
							.addComponent(textFieldNome, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnBuscar))
						.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnIncluir)
						.addComponent(btnAlterar)
						.addComponent(btnRelatorio)
						.addComponent(btnRelatorio_1))
					.addGap(13))
		);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setIcon(new ImageIcon(TabelaPorteiroFrame.class.getResource("/com/portaria/images/go-first.png")));
		toolBar.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setIcon(new ImageIcon(TabelaPorteiroFrame.class.getResource("/com/portaria/images/go-previous.png")));
		toolBar.add(btnAnterior);
		
		comboBox = new JComboBox<String>();
		toolBar.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20"}));
		
		btnProximo = new JButton("");
		btnProximo.setIcon(new ImageIcon(TabelaPorteiroFrame.class.getResource("/com/portaria/images/go-next.png")));
		toolBar.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setIcon(new ImageIcon(TabelaPorteiroFrame.class.getResource("/com/portaria/images/go-last.png")));
		toolBar.add(btnUltimo);
		
		tabelaPorteiro = new JTable();
		
		scrollPane.setViewportView(tabelaPorteiro);
		contentPane.setLayout(gl_contentPane);
		loadDataPorteiroFromTable();
		setLocationRelativeTo(null);
		
	}


	public void loadDataPorteiroFromTable() {
		
		totalData = buscarTodosRegistroPorteiro();
		defaultPagina = Integer.valueOf(comboBox.getSelectedItem().toString());
		Double totalPaginacao = Math.ceil(totalData.doubleValue()/defaultPagina.doubleValue());
		
		totalPagina = totalPaginacao.intValue();
	
		if (numeroPagina.equals(1)) {
			btnPrimeiro.setEnabled(false);
			btnProximo.setEnabled(false);
		} else {
			btnPrimeiro.setEnabled(true);
			btnProximo.setEnabled(true);
		}
		
		if (numeroPagina.equals(totalPagina)) {
			btnUltimo.setEnabled(false);
			btnProximo.setEnabled(false);
		} else {
			btnUltimo.setEnabled(true);
			btnProximo.setEnabled(true);
		}
		
		if (numeroPagina > totalPagina) {
			numeroPagina = 1;
		}
		
		tabelaPorteiroModel = new TabelaPorteiroModel();
		tabelaPorteiroModel.setListaPorteiros(listarTodosPorteiros(numeroPagina, defaultPagina));
		tabelaPorteiro.setModel(tabelaPorteiroModel);

		tabelaPorteiro.setFillsViewportHeight(true);
		tabelaPorteiro.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sortTabelaPorteiro = new TableRowSorter<TabelaPorteiroModel>(tabelaPorteiroModel);
		tabelaPorteiro.setRowSorter(sortTabelaPorteiro);
		
		tabelaPorteiroModel.fireTableDataChanged();
		
		tabelaPorteiro.getColumnModel().getColumn(ID).setWidth(11);
		tabelaPorteiro.getColumnModel().getColumn(NOME).setWidth(100);
		tabelaPorteiro.getColumnModel().getColumn(ENDERECO).setWidth(100);
		tabelaPorteiro.getColumnModel().getColumn(BAIRRO).setWidth(50);
		tabelaPorteiro.getColumnModel().getColumn(CIDADE).setWidth(100);
		tabelaPorteiro.getColumnModel().getColumn(CEP).setWidth(20);
		tabelaPorteiro.getColumnModel().getColumn(NUMERO).setWidth(5);
		
	}
	
	
	private Integer buscarTodosRegistroPorteiro() {
		PorteiroService porteiroService = new PorteiroService();
		return porteiroService.calcularTotalRegistros();
	}


	public List<Porteiro> listarTodosPorteiros(Integer numeroPagina, Integer defaultPagina){
		PorteiroService porteiroService = new PorteiroService();
		return porteiroService.listarTodosRegistrosComPaginacao((defaultPagina * (numeroPagina - 1)), defaultPagina);
	}
	
	
	private void filtrarCampos(String filtro) {

		RowFilter<TabelaPorteiroModel, Object> rowFilter = null;
		try {
			rowFilter = RowFilter.regexFilter(filtro);
		}catch(PatternSyntaxException e) {
			return;
		}
        sortTabelaPorteiro.setRowFilter(rowFilter);
	}
}
