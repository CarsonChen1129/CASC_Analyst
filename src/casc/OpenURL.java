package casc;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OpenURL extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static OpenURL dialog = null;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private String url = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			 dialog = new OpenURL();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OpenURL() {
		initGUI();
	}
	private void initGUI() {
		setTitle("Open URL");
		setBounds(100, 100, 448, 141);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			textField = new JTextField();
			textField.setBounds(32, 46, 374, 28);
			contentPanel.add(textField);
			textField.setColumns(10);
		}
		{
			JLabel lblPleaseEnterThe = new JLabel("Please enter the URL:(For now it only works on 'Detailed Results')");
			lblPleaseEnterThe.setBounds(6, 20, 800, 16);
			contentPanel.add(lblPleaseEnterThe);
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
						url = textField.getText();
						new ReadExcel();
						ReadExcel.readFromWeb(url);
						ReadExcel.main(null,0);
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
					public void mouseClicked(MouseEvent arg0) {
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
