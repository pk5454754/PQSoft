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
		Button bSouth=new Button("���");
		Button bNorth=new Button("����");
		Button bWest=new Button("Ӣ��");
		bCenter=new TextArea();				//�޸ĵ�1��
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
		Button btn = (Button)e.getSource();		//�޸ĵ�2��
		if(btn.getLabel().equals("����"))
		{
			bCenter.setText("�㰴�����İ�ť");
		}
		if(btn.getLabel().equals("���"))
		{
			bCenter.setText(",.!");
		}
		else if(btn.getLabel().equals("Ӣ��"))
		{
			bCenter.setText("You type the English button");
		}
	}
}
public class Hello
{
	public static void main(String[] args) {
		WindowButton win=new WindowButton("��ťС��ϰ");
	}	
}