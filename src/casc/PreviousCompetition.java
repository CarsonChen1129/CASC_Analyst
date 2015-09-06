package casc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PreviousCompetition extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static PreviousCompetition dialog = null;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private int year =0;
	private String temp =null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new PreviousCompetition();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PreviousCompetition() {
		initGUI();
	}
	private void initGUI() {
		setTitle("Previous Competition");
		setBounds(100, 100, 452, 160);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblPleaseEnterThe = new JLabel("Please enter the year:");
			lblPleaseEnterThe.setBounds(16, 17, 133, 16);
			contentPanel.add(lblPleaseEnterThe);
		}
		{
			textField = new JTextField();
			textField.setBounds(16, 45, 403, 28);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dialog.dispose();
						temp = textField.getText();
						year = Integer.parseInt(temp);
						try{
						if(year>=2010 && year<=2013){
							new ReadExcel();
							ReadExcel.preComp(year);
							ReadExcel.main(null,0);
						}else{
							JOptionPane.showMessageDialog(null, "Sorry, no relevant data, or the data has a table in different format, please try again");
							new PreviousCompetition();
							PreviousCompetition.main(null);
						}
							
						}
						catch(Exception e1){
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Sorry, no relevant data, or the data has a table in different format, please try again");
							new PreviousCompetition();
							PreviousCompetition.main(null);
						}
					}
				});
				
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dialog.dispose();
						new Menu();
						Menu.main(null);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
