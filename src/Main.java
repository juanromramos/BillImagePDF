import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtStartDay;
	private JTextField txtEndDay;
	private JTextField txtPrice;
	private JTextField txtNumber;
	private String imagefilename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		DefaultListModel fileimages = new DefaultListModel();
		//String imagefilename;
		
		setTitle("Invoice PDF Generator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 414, 240);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList list = new JList();
		list.setBounds(10, 70, 367, 54);
		contentPane.add(list);
		
		txtStartDay = new JTextField();
		txtStartDay.setBounds(10, 39, 42, 20);
		txtStartDay.setColumns(10);
		contentPane.add(txtStartDay);
		
		txtEndDay = new JTextField();
		txtEndDay.setColumns(10);
		txtEndDay.setBounds(167, 39, 42, 20);
		contentPane.add(txtEndDay);
		
		JComboBox cmbStartMonth = new JComboBox();
		cmbStartMonth.setBounds(62, 39, 95, 20);
		contentPane.add(cmbStartMonth);
		
		JComboBox cmbEndMonth = new JComboBox();
		cmbEndMonth.setBounds(219, 39, 95, 20);
		contentPane.add(cmbEndMonth);

		cmbStartMonth.addItem("JANUARY");
		cmbEndMonth.addItem("JANUARY");
		cmbStartMonth.addItem("FEBRUARY");
		cmbEndMonth.addItem("FEBRUARY");
		cmbStartMonth.addItem("MARCH");
		cmbEndMonth.addItem("MARCH");
		cmbStartMonth.addItem("APRIL");
		cmbEndMonth.addItem("APRIL");
		cmbStartMonth.addItem("MAY");
		cmbEndMonth.addItem("MAY");
		cmbStartMonth.addItem("JUNE");
		cmbEndMonth.addItem("JUNE");
		cmbStartMonth.addItem("JULY");
		cmbEndMonth.addItem("JULY");
		cmbStartMonth.addItem("AUGUST");
		cmbEndMonth.addItem("AUGUST");
		cmbStartMonth.addItem("SEPTEMBER");
		cmbEndMonth.addItem("SEPTEMBER");
		cmbStartMonth.addItem("OCTOBER");
		cmbEndMonth.addItem("OCTUBRE");
		cmbStartMonth.addItem("NOVEMBER");
		cmbEndMonth.addItem("NOVEMBER");
		cmbStartMonth.addItem("DECEMBER");
		cmbEndMonth.addItem("DECEMBER");

		
		JLabel lblFilename = new JLabel("Filename:");
		lblFilename.setBounds(10, 14, 62, 14);
		contentPane.add(lblFilename);
		
		txtPrice = new JTextField();
		txtPrice.setColumns(10);
		txtPrice.setBounds(324, 39, 53, 20);
		contentPane.add(txtPrice);
		
		txtNumber = new JTextField();
		txtNumber.setColumns(10);
		txtNumber.setBounds(72, 11, 42, 20);
		contentPane.add(txtNumber);
				
		JButton btnAddFiles = new JButton("Add files...");
		btnAddFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				imagefilename = promptForFolder();				
				/*
				String pattern = Pattern.quote(System.getProperty("file.separator"));
				String[] splittedFileName = imagefilename.split(pattern);
				imagefilename = splittedFileName[splittedFileName.length-1];
				*/
				if (imagefilename != null)
				{
					fileimages.addElement(imagefilename);
					list.setModel(fileimages);
				}
			}
		});
		btnAddFiles.setBounds(288, 135, 89, 23);
		contentPane.add(btnAddFiles);
		
		JButton btnCreatePdf = new JButton("Create PDF");
		btnCreatePdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename = txtNumber.getText() + "-";
				filename += txtStartDay.getText() + "-";
				filename += cmbStartMonth.getSelectedItem().toString() + "-";
				filename += txtEndDay.getText() + "-";
				filename += cmbEndMonth.getSelectedItem().toString() + "-";
				filename += txtPrice.getText() + "€.pdf";
				try (PDDocument doc = new PDDocument())
				{
					PDPage page = new PDPage();
					doc.addPage(page);
					PDImageXObject pdImage = PDImageXObject.createFromFile(imagefilename, doc);
					PDPageContentStream contents = new PDPageContentStream(doc, page);
					contents.drawImage(pdImage, 20, 20);
					contents.close();
					doc.save(filename);								
					/*
					PDPage blankPage = new PDPage();
					doc.addPage(blankPage);
					doc.save(filename);
					*/
				} catch (IOException error) {
					error.printStackTrace();
				}
			}
		});
		btnCreatePdf.setBounds(10, 135, 128, 48);
		contentPane.add(btnCreatePdf);
	}
	
	public String promptForFolder()
	{
	    JFileChooser fc = new JFileChooser();
	    fc.setFileSelectionMode( JFileChooser.FILES_ONLY );

	    if( fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
	    {
	        return fc.getSelectedFile().getAbsolutePath();
	    }

	    return null;
	}
}
