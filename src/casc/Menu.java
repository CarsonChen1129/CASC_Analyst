package casc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Menu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Menu frame = null;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private final JButton btnExit = new JButton("Exit");
	private final JButton btnOpenLocalFile = new JButton("Open local file");
	private final JButton btnPreviousCompotitions = new JButton("Previous Competitions");
	private final JButton btnOpenUrl = new JButton("Open URL");
	private final JLabel lblNewLabel = new JLabel("The CADE ATP System Competition");
	private final JLabel lblTheCadeAtp = new JLabel("The CADE ATP System Competition");
	private final File dataFile = new File("Excel.xls");


	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Menu();
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
	public Menu() {
		initGUI();
	}
	private void initGUI() {
		setTitle("CASC Analysis Software");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		contentPane.add(panel);
		panel.setLayout(null);
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(dataFile.exists()){
				
					int n = JOptionPane.showConfirmDialog(null,"Do you want to delete the local data file?","Thank you for using",JOptionPane.YES_NO_OPTION);
					if(n == 0){
						dataFile.delete();
					}
				}
				System.exit(1);
			}
		});
		btnExit.setBounds(142, 388, 186, 29);
		panel.add(btnExit);
		btnOpenLocalFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    frame.dispose();
				new ReadExcel();
				ReadExcel.readLocalFile();
				ReadExcel.main(null,0);
			}
		});
		btnOpenLocalFile.setBounds(142, 306, 186, 29);
		
		panel.add(btnOpenLocalFile);
		btnPreviousCompotitions.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new PreviousCompetition();
				PreviousCompetition.main(null);
			}
		});
		btnPreviousCompotitions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPreviousCompotitions.setBounds(142, 347, 186, 29);
		
		panel.add(btnPreviousCompotitions);
		btnOpenUrl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
				new OpenURL();
				OpenURL.main(null);
			}
		});
		btnOpenUrl.setBounds(142, 265, 186, 29);
		
		panel.add(btnOpenUrl);
		lblNewLabel.setIcon(new ImageIcon("lib/Untitled_1.png"));
		lblNewLabel.setBounds(142, 6, 186, 200);
		
		panel.add(lblNewLabel);
		lblTheCadeAtp.setFont(new Font("Geneva", Font.BOLD | Font.ITALIC, 14));
		lblTheCadeAtp.setBounds(123, 218, 246, 16);
		
		panel.add(lblTheCadeAtp);
	
	}
}
