package casc;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ErrorHandler extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static ErrorHandler frame = null;
	private final JPanel panel = new JPanel();
	private final JLabel lblSorryForSome = new JLabel("Sorry, for some reason it crashed,please try again :)");
	private final JLabel lblNewLabel = new JLabel("New label");
	private final JButton btnTryAgain = new JButton("Try again");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new ErrorHandler();
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
	public ErrorHandler() {
		initGUI();
	}
	private void initGUI() {
		setTitle("Error");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 430, 184);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBounds(0, 0, 430, 156);
		
		contentPane.add(panel);
		panel.setLayout(null);
		lblSorryForSome.setBounds(83, 39, 327, 16);
		
		panel.add(lblSorryForSome);
		lblNewLabel.setIcon(new ImageIcon("lib/rac-mobile-error-icon.png"));
		lblNewLabel.setBounds(22, 24, 49, 47);
		
		panel.add(lblNewLabel);
		btnTryAgain.setBounds(150, 98, 117, 29);
		btnTryAgain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    frame.dispose();
				new Menu();
				Menu.main(null);
			}
		});
		
		panel.add(btnTryAgain);
	}
}
