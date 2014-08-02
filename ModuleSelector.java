package ModuleSelector;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;


public class ModuleSelector extends JFrame implements WindowListener,ActionListener {

	private JPanel contentPane;
	JButton btnASSD = new JButton("Setup selected module");
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JRadioButton rdbtnASSD = new JRadioButton("ASSD");
	JRadioButton rdbtnDB = new JRadioButton("Databases");
	JRadioButton rdbtnIandM = new JRadioButton("IandM");
	Properties prop = new Properties();
	InputStream input = null;
	OutputStream output = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModuleSelector frame = new ModuleSelector();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModuleSelector() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 255, 337);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblUeaAppStore = new JLabel("UEA Linux - Module Selector");
		btnASSD.addActionListener(this);

		buttonGroup.add(rdbtnASSD);
		buttonGroup.add(rdbtnDB);
		buttonGroup.add(rdbtnIandM);	
		
		
		// check for config.ini
		// if it doesn't exist, create it
		
		File f = null;
		boolean bool = false;
		try{
			f = new File("/opt/UEA/config.ini");
			bool = f.exists();
			if(bool == false){
				// config.ini doesn't exist, create it.
				System.out.println("config.ini doesn't exist");
				resetConfigToDefault();
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		
		JButton btnReset = new JButton("Reset config.ini");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// edits the config.ini file
				// to reset installed status of apps
				// TODO remove this section before final
				System.out.println("deleting and resetting config.ini");
				File f = new File("/opt/UEA/config.ini");
				f.delete();
				resetConfigToDefault();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(12)
					.addComponent(lblUeaAppStore, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnASSD))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnDB))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnIandM))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnASSD, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(7)
					.addComponent(lblUeaAppStore)
					.addGap(18)
					.addComponent(rdbtnASSD)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnDB)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnIandM)
					.addPreferredGap(ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
					.addComponent(btnASSD, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void resetConfigToDefault(){
		try{
			output = new FileOutputStream("/opt/UEA/config.ini");
			prop.setProperty("ASSDinstalled", "false");
			prop.setProperty("DBinstalled", "false");
			prop.setProperty("IandMinstalled", "false");
			prop.store(output, null);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		
		boolean proceed = false;
		
		String selBtn = getSelectedButtonText(buttonGroup);
		System.out.println("Selected button: " + selBtn);
		
		if (selBtn == null){
			JOptionPane.showMessageDialog(
					null, 
					"Please select a module!");
		}
		
		if (selBtn == "ASSD"){
			try {
				// check if installed in config.ini
				input = new FileInputStream("/opt/UEA/config.ini");
				prop.load(input);
				String installed = prop.getProperty("ASSDinstalled");
				System.out.println("Installed = ." + installed + ".");
				
				if (installed.matches("true")){
					System.out.println("here");
					// ASSD module already setup, proceed?
			        int reply = JOptionPane.showConfirmDialog(
			            null,
			            "ASSD is already installed. Install again?",
			            "Install Again?",
			            JOptionPane.YES_NO_OPTION);
			        
			        if (reply == JOptionPane.NO_OPTION){
			        	// No selected, exit.
			        	proceed = false;
			        } else {
			        	// Yes selected, proceed.
			        	proceed = true;
			        }
				}
				
				if (proceed || installed.matches("false")){
					final ProcessBuilder pb = new ProcessBuilder("mate-terminal", "-x", "/opt/UEA/assd.sh");
					System.out.println("1");
					final Process p = pb.start();
					p.waitFor();
					
					System.out.println("2");
					
					// set property values in config.ini
					output = new FileOutputStream("/opt/UEA/config.ini");
					prop.setProperty("ASSDinstalled", "true");
					prop.store(output, null);
					
				}
				JOptionPane.showMessageDialog(
						null, 
						"ASSD module is now installed. \n" +
						"\n" +		
						"Tools and documentation are located in the\n" +
						"new ASSD folder on your Desktop.",
						"Congratulations!",
						JOptionPane.INFORMATION_MESSAGE);

			} 
			catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		else if (selBtn == "Databases"){
			try {
				System.out.println("Database Manipulation Selected");
				//final ProcessBuilder pb = new ProcessBuilder("mate-terminal", "-x", "/opt/UEA/assd.sh");
				//final Process p = pb.start();
				java.lang.Runtime.getRuntime().exec("df -h");
				//btnASSD.setEnabled(false);
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if (selBtn == "IandM"){
			try {
				System.out.println("Internet and Multimedia Selected");
				//final ProcessBuilder pb = new ProcessBuilder("mate-terminal", "-x", "/opt/UEA/assd.sh");
				//final Process p = pb.start();
				java.lang.Runtime.getRuntime().exec("lsof");
				//btnASSD.setEnabled(false);
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
    public String getSelectedButtonText(ButtonGroup btnGroup) {
        for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
