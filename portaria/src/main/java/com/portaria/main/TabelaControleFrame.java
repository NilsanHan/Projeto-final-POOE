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

import com.portaria.model.Controle;
import com.portaria.model.TabelaControleModel;
import com.portaria.reports.GeraRelatorio;
import com.portaria.service.ControleService;

import net.sf.jasperreports.engine.JRParameter;

public class TabelaControleFrame extends JFrame {

	private static final long serialVersionUID = -352389726581513577L;

	
	private static final int ID       = 0;
	private static final int ESTADO     = 1;
	private static final int HORARIO   = 2;

	
	private static final int VAZIO   = -1; 

	
	private JPanel contentPane;
	private JTable tabelaControle;
	private JButton btnBuscar;
	
	private JComboBox<String> comboBox;
	private JButton btnPrimeiro;
	private JButton btnAnterior;
	private JButton btnProximo;
	private JButton btnUltimo;
	
    private TabelaControleModel tabelaControleModel;
    private TableRowSorter<TabelaControleModel> sortTabelaControle;
    private JTextField textFieldNome;
    
	private Integer totalData = 0;
	private Integer defaultPagina = 5;
	private Integer totalPagina = 1;
	private Integer numeroPagina = 1;
	private JButton btnRelatorio;
    
    
    
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
					TabelaControleFrame frame = new TabelaControleFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TabelaControleFrame() {
		initComponents();
		createEvents();
	}
	
	private void createEvents() {
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filtro = textFieldNome.getText();
				filtrarCampos(filtro);
			}
		});
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numeroPagina = 1;
				loadDataControleFromTable();
			}
		});
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numeroPagina > 1) {
					numeroPagina = numeroPagina - 1;
					loadDataControleFromTable();
				}
			}
		});
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 if (numeroPagina < totalPagina ) {
					 numeroPagina = numeroPagina + 1;
				     loadDataControleFromTable();
				 }
			}
		});
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				numeroPagina = totalPagina;
				loadDataControleFromTable();
			}
		});
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		     	String nomeArquivo = "relatorio_controle";
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(JRParameter.REPORT_LOCALE, new Locale("pt","BR"));
				GeraRelatorio geraRelatorio = new GeraRelatorio(nomeArquivo, params);
				geraRelatorio.generateReports();
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
		
		JLabel lblNome = new JLabel("Nome:");
		
		textFieldNome = new JTextField();
		textFieldNome.setColumns(10);
	    btnBuscar = new JButton("Buscar");
		
		JToolBar toolBar = new JToolBar();
		
		btnRelatorio = new JButton("Relatório");
		
		
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
							.addGap(283)
							.addComponent(btnRelatorio))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 946, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(107, Short.MAX_VALUE))
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
					.addComponent(btnRelatorio)
					.addGap(13))
		);
		
		btnPrimeiro = new JButton("");
		btnPrimeiro.setIcon(new ImageIcon(TabelaControleFrame.class.getResource("/com/portaria/images/go-first.png")));
		toolBar.add(btnPrimeiro);
		
		btnAnterior = new JButton("");
		btnAnterior.setIcon(new ImageIcon(TabelaControleFrame.class.getResource("/com/portaria/images/go-previous.png")));
		toolBar.add(btnAnterior);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadDataControleFromTable();
			}
		});
		toolBar.add(comboBox);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"5", "10", "15", "20"}));
		
		btnProximo = new JButton("");
		btnProximo.setIcon(new ImageIcon(TabelaControleFrame.class.getResource("/com/portaria/images/go-next.png")));
		toolBar.add(btnProximo);
		
		btnUltimo = new JButton("");
		btnUltimo.setIcon(new ImageIcon(TabelaControleFrame.class.getResource("/com/portaria/images/go-last.png")));
		toolBar.add(btnUltimo);
		
		tabelaControle = new JTable();
		
		scrollPane.setViewportView(tabelaControle);
		contentPane.setLayout(gl_contentPane);
		loadDataControleFromTable();
		setLocationRelativeTo(null);
		
	}


	public void loadDataControleFromTable() {
		
		totalData = buscarTodosRegistroControle();
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
		
		tabelaControleModel = new TabelaControleModel();
		tabelaControleModel.setListaControles(listarTodosControles(numeroPagina, defaultPagina));
		tabelaControle.setModel(tabelaControleModel);

		tabelaControle.setFillsViewportHeight(true);
		tabelaControle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		sortTabelaControle = new TableRowSorter<TabelaControleModel>(tabelaControleModel);
		tabelaControle.setRowSorter(sortTabelaControle);
		
		tabelaControleModel.fireTableDataChanged();
		
		tabelaControle.getColumnModel().getColumn(ID).setWidth(11);
		tabelaControle.getColumnModel().getColumn(ESTADO).setWidth(100);
		tabelaControle.getColumnModel().getColumn(HORARIO).setWidth(100);
	
		
	}
	
	
	private Integer buscarTodosRegistroControle() {
		ControleService controleService = new ControleService();
		return controleService.calcularTotalRegistros();
	}


	public List<Controle> listarTodosControles(Integer numeroPagina, Integer defaultPagina){
		ControleService controleService = new ControleService();
		return controleService.listarTodosRegistrosComPaginacao((defaultPagina * (numeroPagina - 1)), defaultPagina);
	}
	
	
	private void filtrarCampos(String filtro) {

		RowFilter<TabelaControleModel, Object> rowFilter = null;
		try {
			rowFilter = RowFilter.regexFilter(filtro);
		}catch(PatternSyntaxException e) {
			return;
		}
        sortTabelaControle.setRowFilter(rowFilter);
	}
}
