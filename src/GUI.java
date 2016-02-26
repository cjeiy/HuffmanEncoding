import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class GUI implements ActionListener{
	
	private String encodedFileName = "compressed_file.data";
	private JButton encodeButton;
	private JButton decodeButton;
	private JTextField fileToEncode;
	
	
	public GUI(String[] args){
		JFrame frame = new JFrame();
		JPanel mainLayout = new JPanel();
		mainLayout.setLayout(new GridLayout(1,0));
		mainLayout.add(encodePanelSetup());
		mainLayout.add(decodePanelSetup());
		frame.setSize(800, 300);
		frame.add(mainLayout);
		frame.setVisible(true);
		
	}
	
	
	private JPanel encodePanelSetup(){
		JPanel encodePanel = new JPanel();
		encodePanel.setLayout(new GridLayout(0,2));
		
		JLabel inputTextForEncodingField = new JLabel("Insert the name of the file ");	
		fileToEncode = new JTextField();
		encodeButton = new JButton("Encode file");
		encodeButton.addActionListener(this);
		
		encodePanel.add(inputTextForEncodingField);
		encodePanel.add(fileToEncode);
		encodePanel.add(encodeButton);

		return encodePanel;
	}
	
	private JPanel decodePanelSetup(){
		JPanel decodePanel = new JPanel();
		decodePanel.setLayout(new FlowLayout());	
		decodeButton = new JButton("Decode file");
		decodeButton.addActionListener(this);
		decodePanel.add(decodeButton);
		return decodePanel;
		
	}
	
	public static void main(String[] args) {
		new GUI(args);	
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(encodeButton)){
			
			
			new EncodeFile(new String[]{fileToEncode.getText(), encodedFileName});
			
			JButton b = (JButton)e.getSource();
			
			b.setBackground(Color.green);
			
			
		}else if(e.getSource().equals(decodeButton)){
			
			String[] splittedFileName = fileToEncode.getText().split("\\.");
			String decodedFileName = splittedFileName[0]+"_decoded."+splittedFileName[1];
			
			System.out.println(decodedFileName);
			
			new DecodeFile(new String[]{decodedFileName, encodedFileName});
			
		}
		
		}
	}		

