package collegio.ipconfig;
import java.awt.HeadlessException;
import java.awt.event.*;
import java.io.*;
import javax.swing.JOptionPane;

import org.apache.commons.lang3.SystemUtils;

/**
 * @author Marco Trinelli
 *
 */
public class Controller implements ActionListener{

	private View2 gui;

	public Controller(View2 gui) {
		this.gui = gui;
		
		if(SystemUtils.IS_OS_WINDOWS){
			gui.txtOS.setText(SystemUtils.OS_NAME);
			gui.txtOS2.setText(SystemUtils.OS_NAME);
		} else if(SystemUtils.IS_OS_LINUX){
			gui.txtOS.setText(SystemUtils.OS_NAME);
			gui.txtOS2.setText(SystemUtils.OS_NAME);
		} else if(SystemUtils.IS_OS_MAC){
			gui.txtOS.setText(SystemUtils.OS_NAME);
			gui.txtOS2.setText(SystemUtils.OS_NAME);
		}
		try {
			gui.txtCard.setText(getInterface());
			gui.txtCard2.setText(gui.txtCard.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Esegui")){
					try {
						runEsegui();
					} catch (IOException | HeadlessException | InterruptedException e1) {
					}
		} else if(e.getActionCommand().equals("Configura")){
			try {
				runConfigura();
			} catch (IOException | InterruptedException e1) {
			}
}
	}
	
	private void runConfigura() throws IOException, InterruptedException {
		String interf = gui.txtCard.getText();
		if(SystemUtils.IS_OS_WINDOWS){
			gui.progressBar2.setValue(0);
			String cmdIP = "netsh interface ip set address name = \"" + interf + "\" dhcp";
			Process pIP = Runtime.getRuntime().exec(cmdIP);
			if(pIP.waitFor() == 0)
				gui.progressBar2.setValue(50);
			else
				JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione IP \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
			String cmdDNS = "netsh interface ip set dns name = \"" + interf + "\" source = dhcp";
			Process pDNS = Runtime.getRuntime().exec(cmdDNS);
			if(pDNS.waitFor() == 0){
				gui.progressBar2.setValue(100);
				JOptionPane.showMessageDialog(null, "Configurazione avvenuta con successo!");
			}else
				JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione IP \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
		} else if(SystemUtils.IS_OS_MAC){
			gui.progressBar2.setValue(0);
			String cmdIP[] = {"networksetup", "-setdhcp", interf};
			Process pIP = Runtime.getRuntime().exec(cmdIP);
			if(pIP.waitFor() == 0)
				gui.progressBar2.setValue(50);
			else
				JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione IP \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
			String cmdDNS[] = {"networksetup", "-setdnsservers", interf, "empty"};
			Process pDNS = Runtime.getRuntime().exec(cmdDNS);
			if(pDNS.waitFor() == 0){
				gui.progressBar2.setValue(100);
				JOptionPane.showMessageDialog(null, "Configurazione avvenuta con successo!");
			}else
				JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione DNS \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private void runEsegui() throws IOException, HeadlessException, InterruptedException {
		String ip = null;
		String interf = gui.txtCard.getText();
		String netmask = "255.255.255.0";
		String dns1 = "172.17.4.1";
		String dns2 = "172.18.4.1";
		String dg = "172.19.2.254";
		String room = gui.tcam.getText();
		if(!check(room)){
			JOptionPane.showMessageDialog(null, "Hai sbagliato sezione: "
					+ "La camera " + room + " non fa parte della sezione Valentino.\n"
							+ "Non sai neanche dove abiti...", "Errore", JOptionPane.ERROR_MESSAGE);
			return;
		}
		int Last;
		int Piano = Character.getNumericValue(room.charAt(0));
		int UltimeDue = Integer.parseInt(room.substring(1, 3));
		
		if(room.equals("511B")){
			ip = "172.19.2.221";
		} else if(room.equals("512B")){
			ip = "172.19.2.222";
		} else if(room.equals("514B")){
			ip = "172.19.2.223";
		} else if(room.equals("515B")){
			ip = "172.19.2.224";
		} else switch(Piano){
		case 1:
			Last = UltimeDue + 150;
			ip = "172.19.2." + Last;
			break;
		case 2:
			Last = UltimeDue + 100;
			ip = "172.19.2." + Last;
			break;
		case 3:
			Last = UltimeDue + 50;
			ip = "172.19.2." + Last;
			break;
		case 4:
			Last = UltimeDue;
			ip = "172.19.2." + Last;
			break;
		case 5:
			Last = UltimeDue + 200;
			ip = "172.19.2." + Last;
			break;
		default:
			System.out.println("Errore");
				
		}
		gui.progressBar.setValue(0);
		if(SystemUtils.IS_OS_WINDOWS){
		String cmdIP = "netsh interface ip set address name = \"" + interf + "\" source = static addr = " + ip + " mask = " + netmask + " gateway = " + dg; 
		String cmdDNS1 = "netsh interface ip set dns name = \"" + interf + "\" source = static addr = " + dns1;
		String cmdDNS2 = "netsh interface ip add dns name = \"" + interf + "\" addr = " + dns2 + " index = 2";
		/* IP */
		Process pIP = Runtime.getRuntime().exec(cmdIP);
		if(pIP.waitFor() == 0)
			gui.progressBar.setValue(33);
		else
			JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione IP \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
        
        /* DNS1 */
		Process pDNS1 = Runtime.getRuntime().exec(cmdDNS1);
		if(pDNS1.waitFor() == 0)
			gui.progressBar.setValue(67);
		else
			JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione DNS1 \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
        
        /* DNS2 */
        Process pDNS2 = Runtime.getRuntime().exec(cmdDNS2);
        if(pDNS2.waitFor() == 0){
			gui.progressBar.setValue(100);
        	JOptionPane.showMessageDialog(null, "Configurazione avvenuta con successo!");
        } else
			JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione DNS2 \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
		} else if(SystemUtils.IS_OS_MAC){
			/*
			String cmdIP = "sudo networksetup -setmanual \"" + EthName + "\" " + ip + " " + netmask + " " + dg;
			String cmdDNS = "sudo networksetup -setdnsservers \"" + EthName + "\" " + dns1 + " " + dns2;
			*/
			String cmdDNS[] = {/*"sudo", */"networksetup", "-setdnsservers", interf, dns1, dns2};
			String cmdIP[] = {/*"sudo", */"networksetup", "-setmanual", interf, ip, netmask, dg};
			gui.progressBar.setValue(0);
			Process pIP = Runtime.getRuntime().exec(cmdIP);
			if(pIP.waitFor() == 0)
				gui.progressBar.setValue(50);
			else
				JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione IP \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
	        
	        
			Process pDNS = Runtime.getRuntime().exec(cmdDNS);
			if(pDNS.waitFor() == 0){
				gui.progressBar.setValue(100);
				JOptionPane.showMessageDialog(null, "Configurazione avvenuta con successo!");
			} else
				JOptionPane.showMessageDialog(null, "Errore!\nDescrizione: Errore configurazione DNS \nSe persiste contattare il sistemista.", "Errore", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean check(String room) {
		String pattern = "\\d{3}B?"; //3 cifre più un eventuale B
		if (room.matches(pattern))
			return true;
		return false;
	}

	private String getInterface() throws IOException {
		 // parsing del comando ipconfig
		if(SystemUtils.IS_OS_WINDOWS){
			String cmdIPCONFIG = "ipconfig /all";
			Process pIPCONFIG = Runtime.getRuntime().exec(cmdIPCONFIG);
			BufferedReader br = new BufferedReader(new InputStreamReader(pIPCONFIG.getInputStream())); 
			String line = null;
			String[] languages = {"Scheda Ethernet ", "Ethernet adapter ", "Adaptador de Ethernet ", "Carte Ethernet "}; //ITA, ENG, SPA, FRA
			for(String lang: languages)
				while ((line = br.readLine()) != null) {
					if(line.contains(lang)){
						String net = line.replaceAll(lang, "").replaceAll("\\s*:\\s*", "");
						return net;
					}
				}
		} else if(SystemUtils.IS_OS_MAC){
			String cmdNET = "networksetup -listnetworkserviceorder";
			Process pNET = Runtime.getRuntime().exec(cmdNET);
			BufferedReader br = new BufferedReader(new InputStreamReader(pNET.getInputStream())); 
			String line = null;
			String line2 = null;
			br.readLine(); //IGNORE: An asterisk (*) ...
			while ((line = br.readLine()) != null) { //name
				line2 = br.readLine(); //target
				System.out.println(line);
				if(line2.contains("Ethernet")){
					String name = line.replaceAll("\\(\\d\\)\\s", "").trim();
					System.out.println("name: " + name);
					return name;
				}
				br.readLine(); //white line
			}
		}
	
		JOptionPane.showMessageDialog(null, "Errore!\n"
				+ "Descrizione: Interfaccia di rete non riconosciuta."
				+ "Controllare il collegamento del cavo Ethernet e spegnere momentaneamente il Wi-Fi.", "Errore", JOptionPane.ERROR_MESSAGE);
		return "WIRE UNPLUGGED";
	}
}
