 


import javax.swing.*;
import java.awt.*;
public class Utility {

	public static void displayErrorMsg(String msg)
	{
		JFrame frame = new JFrame(); 
		JPanel panel = new JPanel();
		JLabel label = new JLabel(msg);
		label.setFont(new Font ("Arial", Font.PLAIN, 30));
		
		frame.setVisible(true);
		frame.setSize(500, 300);
		panel.add(label);
		frame.add(panel);
		frame.pack();
	}
	

}
