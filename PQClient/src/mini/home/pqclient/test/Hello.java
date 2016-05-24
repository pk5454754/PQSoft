package mini.home.pqclient.test;
import java.awt.*;
import java.awt.event.*;
class WindowButton extends Frame implements ActionListener {
	Button bSouth;
	Button bNorth;
	Button bWest;
	TextArea bCenter;
	
	WindowButton(String s)
	{		
		super(s);
		setLayout(new BorderLayout());
		Button bSouth=new Button("标点");
		Button bNorth=new Button("中文");
		Button bWest=new Button("英文");
		bCenter=new TextArea();				//修改第1处
		add(bSouth,BorderLayout.SOUTH);
		add(bNorth,BorderLayout.NORTH);
		add(bWest,BorderLayout.WEST);
		add(bCenter,BorderLayout.CENTER);
		bSouth.addActionListener(this);
		bNorth.addActionListener(this);
		bWest.addActionListener(this);
		setBounds(100, 100, 300, 300);
		setVisible(true);
		validate();		
	}
	public void actionPerformed(ActionEvent e) {
		Button btn = (Button)e.getSource();		//修改第2处
		if(btn.getLabel().equals("中文"))
		{
			bCenter.setText("你按了中文按钮");
		}
		if(btn.getLabel().equals("标点"))
		{
			bCenter.setText(",.!");
		}
		else if(btn.getLabel().equals("英文"))
		{
			bCenter.setText("You type the English button");
		}
	}
}
public class Hello
{
	public static void main(String[] args) {
		WindowButton win=new WindowButton("按钮小练习");
	}	
}