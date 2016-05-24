package mini.home.pqclient.test;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Flavor extends JFrame implements ActionListener {
	public static void main(String arg[]) {
		new Flavor();
	}

	public Flavor() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setLocation(250, 150);
		Container pane = getContentPane();
		pane.setLayout(new FlowLayout());
		JButton button = new JButton("Dialog");
		button.addActionListener(this);
		pane.add(button);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String selection = e.getActionCommand();
		if (selection.equals("Dialog")) {
			String possibleFlavor[] = { "Vanilla", "Chocolate", "Rocky Road" };
			String favorite = (String) JOptionPane.showInputDialog(
					this,// 定义输入对话框的属性
					"Choose your favorite", "Ice Cream Flavors",
					JOptionPane.QUESTION_MESSAGE, null, possibleFlavor,
					possibleFlavor[0]);
			if (favorite == null)
				System.out.println("No choice made");
			else
				System.out.println("Favorite: " + favorite);
		}
	}
}