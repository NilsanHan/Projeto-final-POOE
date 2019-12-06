package com.portaria.main;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.portaria.conexao.Conexao;
import com.portaria.model.Controle;
import com.portaria.service.ControleService;

public class ControleEntrada extends JFrame {

	private static final long serialVersionUID = 5982264099348912699L;

	private JPanel contentPane;

	private JComboBox<String> portaComboBox;
	private JComboBox<String> baudRateComboBox;
	private JComboBox<String> dataBitsComboBox;
	private JComboBox<String> paridadeComboBox;
	private JComboBox<String> stopBitsComboBox;

	private JButton btnConectar;

//	private String baudRate[] = {"300", "600", "1200", "2400","9600","14400", "19200","39400"};
	private String baudRate[] = { "9600" };
	private String dataBits[] = { "5", "6", "7", "8" };
	private String paridade[] = { "0", "1", "2", "3", "4" };
	private String stopBits[] = { "0", "1", "2", "3" };

	private boolean portOpen = false;
	private int intBaudRate = 0;
	private int intDataBits = 0;
	private int intParidade = 0;
	private int intStopBits = 0;

	private Conexao conexao;

	private String dir;
	private JButton btnAbrir;
	private JButton btnFechar;
	private JLabel lblStatus;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControleEntrada frame = new ControleEntrada();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ControleEntrada() {

		getPathLib();

		initComponents();

		leiaBaudRate();
		leiaDataBits();
		leiaParidade();
		leiaStopBits();
	}

	private void getPathLib() {
		setDir(System.getProperty("user.dir"));
		try {
			System.load(getDir() + "\\rxtxSerial.dll");
			System.load(getDir() + "\\rxtxParallel.dll");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private void leiaPortas() {
		Conexao conexao = new Conexao();

		List<String> portasSistema = new ArrayList<>();

		portasSistema = conexao.leiaPortas();

		if (portasSistema.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Nenhuma porta encontrada! - Verifique", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}

		portaComboBox
				.setModel(new DefaultComboBoxModel<String>(portasSistema.toArray(new String[portasSistema.size()])));
	}

	private void leiaBaudRate() {
		baudRateComboBox.setModel(new DefaultComboBoxModel<String>(this.getBaudRate()));
	}

	private void leiaDataBits() {
		dataBitsComboBox.setModel(new DefaultComboBoxModel<String>(this.dataBits));
	}

	private void leiaParidade() {
		paridadeComboBox.setModel(new DefaultComboBoxModel<String>(this.getParidade()));
	}

	private void leiaStopBits() {
		stopBitsComboBox.setModel(new DefaultComboBoxModel<String>(this.getStopBits()));
	}

	private void criarConexao(ActionEvent e) {

		if (Objects.isNull(conexao)) {
			if (getIntBaudRate() == 0) {
				conexao = new Conexao();

				JOptionPane.showMessageDialog(null, "Conectado ao portão com sucesso!");
				btnConectar.setEnabled(false);
			} else {
				conexao = new Conexao(getIntBaudRate());
			}
			portOpen = conexao.openConnetion(getPortaComboBox().getSelectedItem().toString());
		}

	}

	private void enviarMensagem(ActionEvent e, String c) {
		Thread tarefa = new Thread() {

			public void run() {
				if (true) {
//					c = c.equals("1") ? "2" : "1";
					conexao.sendData(c);
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
					}

				}
			}
		};
		tarefa.start();
	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 835, 553);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblPorta = new JLabel("Porta:");

		portaComboBox = new JComboBox<String>();

		JLabel lblBaudRate = new JLabel("Baud Rate:");

		baudRateComboBox = new JComboBox<String>();

		JLabel lblDataBits = new JLabel("Data Bits:");

		dataBitsComboBox = new JComboBox<String>();

		JLabel lblParidade = new JLabel("Paridade:");

		paridadeComboBox = new JComboBox<String>();

		JLabel lblStopBits = new JLabel("Stop Bits:");

		stopBitsComboBox = new JComboBox<String>();

		btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				criarConexao(e);
			}
		});

		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date data = new Date();
//				JOptionPane.showMessageDialog(null, "Portão abrindo\nHora:" + data);
				lblStatus.setText("Status: Abrindo");
//				System.out.println("Hora:"+data.getHours());
				enviarMensagem(e, "1");
				
				ControleService controleService = new ControleService();
				Controle controle = new Controle();
				controle.setEstado("abrindo");
				controle.setHorario(data);
				controleService.salvarControle(controle);
			}
		});

		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Date data = new Date();
//				JOptionPane.showMessageDialog(null, "Portão abrindo\nHora:" + data);
				lblStatus.setText("Status: Fechando");
//				System.out.println("Hora:"+data.getHours());
				enviarMensagem(e, "2");
				
				ControleService controleService = new ControleService();
				Controle controle = new Controle();
				controle.setEstado("fechando");
				controle.setHorario(data);
				controleService.salvarControle(controle);

			}

		});

		lblStatus = new JLabel("Status:");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(65).addGroup(gl_contentPane
						.createParallelGroup(Alignment.TRAILING).addComponent(lblBaudRate).addComponent(lblPorta)
						.addGroup(
								gl_contentPane
										.createParallelGroup(Alignment.LEADING).addComponent(lblDataBits)
										.addGroup(gl_contentPane
												.createParallelGroup(Alignment.TRAILING).addComponent(lblStopBits)
												.addComponent(lblParidade))))
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(baudRateComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(portaComboBox, GroupLayout.PREFERRED_SIZE, 349,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(dataBitsComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(stopBitsComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(paridadeComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
												.addComponent(btnAbrir, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnConectar, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addGap(34).addComponent(btnFechar, GroupLayout.PREFERRED_SIZE, 87,
												GroupLayout.PREFERRED_SIZE)))
						.addGap(112)
						.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
						.addGap(65)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(66)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblPorta)
						.addComponent(portaComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStatus, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblBaudRate).addComponent(
						baudRateComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblDataBits).addComponent(
						dataBitsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblParidade).addComponent(
						paridadeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(lblStopBits).addComponent(
						stopBitsComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE))
				.addGap(30).addComponent(btnConectar).addGap(51).addGroup(gl_contentPane
						.createParallelGroup(Alignment.BASELINE).addComponent(btnAbrir).addComponent(btnFechar))
				.addContainerGap(120, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);

		leiaPortas();

	}

	public JComboBox<String> getPortaComboBox() {
		return portaComboBox;
	}

	public void setPortaComboBox(JComboBox<String> portaComboBox) {
		this.portaComboBox = portaComboBox;
	}

	public JComboBox<String> getBaudRateComboBox() {
		return baudRateComboBox;
	}

	public void setBaudRateComboBox(JComboBox<String> baudRateComboBox) {
		this.baudRateComboBox = baudRateComboBox;
	}

	public JComboBox<String> getDataBitsComboBox() {
		return dataBitsComboBox;
	}

	public void setDataBitsComboBox(JComboBox<String> dataBitsComboBox) {
		this.dataBitsComboBox = dataBitsComboBox;
	}

	public JComboBox<String> getParidadeComboBox() {
		return paridadeComboBox;
	}

	public void setParidadeComboBox(JComboBox<String> paridadeComboBox) {
		this.paridadeComboBox = paridadeComboBox;
	}

	public JComboBox<String> getStopBitsComboBox() {
		return stopBitsComboBox;
	}

	public void setStopBitsComboBox(JComboBox<String> stopBitsComboBox) {
		this.stopBitsComboBox = stopBitsComboBox;
	}

	public String[] getBaudRate() {
		return baudRate;
	}

	public void setBaudRate(String[] baudRate) {
		this.baudRate = baudRate;
	}

	public String[] getDataBits() {
		return dataBits;
	}

	public void setDataBits(String[] dataBits) {
		this.dataBits = dataBits;
	}

	public String[] getParidade() {
		return paridade;
	}

	public void setParidade(String[] paridade) {
		this.paridade = paridade;
	}

	public String[] getStopBits() {
		return stopBits;
	}

	public void setStopBits(String[] stopBits) {
		this.stopBits = stopBits;
	}

	public boolean isPortOpen() {
		return portOpen;
	}

	public void setPortOpen(boolean portOpen) {
		this.portOpen = portOpen;
	}

	public int getIntBaudRate() {
		return intBaudRate;
	}

	public void setIntBaudRate(int intBaudRate) {
		this.intBaudRate = intBaudRate;
	}

	public int getIntDataBits() {
		return intDataBits;
	}

	public void setIntDataBits(int intDataBits) {
		this.intDataBits = intDataBits;
	}

	public int getIntParidade() {
		return intParidade;
	}

	public void setIntParidade(int intParidade) {
		this.intParidade = intParidade;
	}

	public int getIntStopBits() {
		return intStopBits;
	}

	public void setIntStopBits(int intStopBits) {
		this.intStopBits = intStopBits;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}
