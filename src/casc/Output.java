package casc;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JScrollPane;

import java.awt.Font;

public class Output extends JFrame implements WindowListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JTextField textField = new JTextField();
	private final JButton btnNextTable = new JButton("Next Sheet");
	private final JButton btnPreviousTable = new JButton("Previous Sheet");
	private final JLabel lblTable = new JLabel("Sheet:");
	private final JButton btnGo = new JButton("Go");
	public int num = -1;
	private final JScrollPane scrollPane = new JScrollPane();
	public static JTextArea textArea = new JTextArea();
    public FileReader theFile;
    public static int sheetNum = -1;
    public static int currentPage = 0;
    private final JLabel lblNewLabel = new JLabel("New label");
    private final File dataFile = new File("Excel.xls");
    private static Output frame = null ;
    public static boolean check = false;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Output();
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
	public Output() {
		textField.setBounds(62, 25, 68, 28);
		textField.setColumns(10);
		initGUI();

	}
	
	private void initGUI() {
		setTitle("Result");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener((WindowListener) this);
		setBounds(100, 100, 759, 558);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		contentPane.add(textField);
		scrollPane.setBounds(18, 65, 723, 450);
		

		
		//contentPane.add(textArea);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea);
		
		try {
			theFile = new FileReader("output.txt");
			BufferedReader br = new BufferedReader(theFile);
			try {
				textArea.read(br, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		btnNextTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(currentPage+1 < sheetNum){
					currentPage += 1;
					new ReadExcel();
					ReadExcel.main(null, currentPage);
					if(check == true){
						frame.dispose();
					}

				}else{
					JOptionPane.showMessageDialog(null, "End of the file");
					new ReadExcel();
					ReadExcel.main(null, currentPage);
					if(check == true){
						frame.dispose();
					}
				}
			}
		});
		
		btnNextTable.setBounds(635, 26, 117, 29);
		
		contentPane.add(btnNextTable);
		btnPreviousTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(currentPage-1 >= 0){
					currentPage -= 1;
					new ReadExcel();
					ReadExcel.main(null, currentPage);
					if(check == true){
						frame.dispose();
					}

				}else{
					JOptionPane.showMessageDialog(null, "This is the first page");
					new ReadExcel();
					ReadExcel.main(null, currentPage);
					if(check == true){
						frame.dispose();
					}
				}
			}
		});
		btnPreviousTable.setBounds(506, 26, 117, 29);
		
		contentPane.add(btnPreviousTable);
		lblTable.setBounds(18, 31, 45, 16);
		
		contentPane.add(lblTable);
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				num = Integer.parseInt(textField.getText());
				if(num>0 && num <=sheetNum){
				currentPage = num -1;
				new ReadExcel();
				ReadExcel.main(null, currentPage);
				if(check == true){
					frame.dispose();
				}
				}else{
					JOptionPane.showMessageDialog(null, "Sorry, no relevant sheet is found");
					currentPage = 0;
					new ReadExcel();
					ReadExcel.main(null, currentPage);
					if(check == true){
						frame.dispose();
					}
					
				}
				
				
			}
		});
		btnGo.setBounds(142, 26, 75, 29);
		
		contentPane.add(btnGo);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 15));
		lblNewLabel.setBounds(299, 31, 132, 16);
		lblNewLabel.setText((currentPage+1)+"/"+sheetNum);
		
		contentPane.add(lblNewLabel);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
		int n = JOptionPane.showConfirmDialog(null,"Do you want to delete the local data file?","Thank you for using",JOptionPane.YES_NO_OPTION);
		if(n == 0){
			dataFile.delete();
		}
		System.exit(1);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
