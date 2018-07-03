package collegio.ipconfig;

import java.awt.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;

import org.apache.commons.lang3.SystemUtils;

import com.apple.eawt.Application;

@SuppressWarnings("serial")
public class View2 extends JFrame {
	
	JProgressBar progressBar = new JProgressBar(0, 100);
	JProgressBar progressBar2 = new JProgressBar(0, 100);
	JFrame errFrame = new JFrame("Error");
	JTextField tcam = new JTextField();
	JButton btnEsegui = new JButton("Esegui");
	JButton btnConfigura = new JButton("Configura");
	JTextField txtOS = new JTextField();
	JTextField txtCard = new JTextField();
	JTextField txtOS2 = new JTextField();
	JTextField txtCard2 = new JTextField();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View2 frame = new View2();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public View2() {
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Valentino LAN Configurator");
		setSize(400, 200);
		 URL url = View2.class.getResource("/icon.jpg");
		ImageIcon img = new ImageIcon(url);
		if(SystemUtils.IS_OS_WINDOWS)
			setIconImage(img.getImage());
		else if(SystemUtils.IS_OS_MAC)
			Application.getApplication().setDockIconImage(img.getImage());
		progressBar.setBounds(152, 82, 226, 25);
		
		progressBar.setStringPainted(true);
		
		JPanel pan_camera = new JPanel();
		JPanel pan_sala = new JPanel();
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Camera", null,pan_camera, "Configurazione IP statico per la camera dell'utente");
		btnEsegui.setBounds(287, 26, 92, 23);
		
		btnEsegui.addActionListener(new Controller(this));
		
		JLabel lblNCamera = new JLabel("Camera");
		lblNCamera.setBounds(153, 30, 65, 14);
		tcam.setBounds(210, 27, 54, 20);
		tcam.setToolTipText("Inserisci il numero della tua camera (più eventuale lettera). Es: 334, 511B");
		
		tcam.setColumns(5);
		
		JLabel lblNewLabel = new JLabel("Marco Trinelli \u00A9 2016");
		lblNewLabel.setBounds(10, 113, 150, 19);
		lblNewLabel.setForeground(Color.GRAY);
		
		JLabel lblNewLabel_1 = new JLabel("OS");
		lblNewLabel_1.setBounds(10, 11, 54, 14);
		
		JLabel lblLineCard = new JLabel("Network Card");
		lblLineCard.setBounds(10, 71, 110, 14);
		txtOS.setBounds(10, 27, 86, 20);
		
		txtOS.setEditable(false);
		txtOS.setColumns(10);
		txtCard.setBounds(10, 87, 86, 20);
		
		
		txtCard.setEditable(false);
		txtCard.setColumns(10);
		pan_camera.setLayout(null);
		pan_camera.add(lblNewLabel);
		pan_camera.add(lblNewLabel_1);
		pan_camera.add(lblLineCard);
		pan_camera.add(txtCard);
		pan_camera.add(txtOS);
		pan_camera.add(lblNCamera);
		pan_camera.add(tcam);
		pan_camera.add(btnEsegui);
		pan_camera.add(progressBar);
		tabbedPane.addTab("Sala comune", null, pan_sala, "Configurazione IP dinamico (per sala studio, cucina, casa propria, ...)");
		txtOS2.setBounds(10, 27, 86, 20);
		
		
		txtOS2.setEditable(false);
		txtOS2.setColumns(10);
		
		JLabel label = new JLabel("OS");
		label.setBounds(10, 11, 27, 14);
		
		JLabel lblNetworkCard = new JLabel("Network Card");
		lblNetworkCard.setBounds(10, 71, 110, 14);
		txtCard2.setBounds(10, 87, 86, 20);
		
		
		txtCard2.setEditable(false);
		txtCard2.setColumns(10);
		
		JLabel label_2 = new JLabel("Marco Trinelli \u00A9 2016");
		label_2.setBounds(10, 113, 150, 19);
		label_2.setForeground(Color.GRAY);
		progressBar2.setBounds(152, 82, 226, 25);
		
		progressBar2.setStringPainted(true);
		btnConfigura.setBounds(205, 26, 110, 23);
		
		btnConfigura.addActionListener(new Controller(this));
		pan_sala.setLayout(null);
		pan_sala.add(label_2);
		pan_sala.add(label);
		pan_sala.add(txtOS2);
		pan_sala.add(lblNetworkCard);
		pan_sala.add(txtCard2);
		pan_sala.add(progressBar2);
		pan_sala.add(btnConfigura);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addGap(0))
		);
		getContentPane().setLayout(groupLayout);
		JOptionPane.showMessageDialog(null,
				"1a) Su Windows: eseguire con i permessi di amministratore\n"
				+ "1b) Su Mac OS: inserire le credenziali di amministratore quando richieste\n"
				+ "2) Collegare il cavo Ethernet\n"
				+ "3) Scollegare l'interfaccia Wi-Fi\n"
				+ "4) Rimuovere server Proxy se precedentemente impostato\n"
				+ "In caso di problemi, tentare con la configurazione manuale (leggere la guida nella bacheca a piano terra)\n"
				+ "Se vengono riscontrati altri problemi rivolgersi al sistemista.", "Avviso", JOptionPane.WARNING_MESSAGE);

	}
}
