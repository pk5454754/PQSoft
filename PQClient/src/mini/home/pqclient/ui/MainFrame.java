package mini.home.pqclient.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import mini.home.pqclient.action.ExitPQ;
import mini.home.pqclient.action.MyInfo;
import mini.home.pqclient.action.SetName;
import mini.home.pqclient.action.SocketConnect;
import mini.home.pqclient.listener.SendMessageListener;
import mini.home.pqclient.listener.WindowClose;
import mini.home.pqclient.thread.ReceiveThread;
import mini.home.uitl.fin.ConfClass;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 203465082499839121L;
	private JPanel topPanel;// �������ʷ��Ϣ����
	private JPanel bottomPanel;//
	private Socket socket;// ��ǰ�ͻ�����Ϣ
	private JTextArea showArea;// ��ʾ����
	private JTextArea sendArea;// ��������
	private ReceiveThread rt;//������Ϣ���߳�
	public String name;// ����
	private JButton jbt;// ���Ͱ�ť
	private JPanel rightpanel;// �ұ߿հ����
	private JPanel middlepanel;// �м�հ����
	private JPanel leftPanel;// �����Ϣ�������������
	private boolean debug = false;// �Ƿ����

	public MainFrame() {
		initFrame();
		this.setSize(600, 600);
		this.setVisible(true);
		
	}

	private void initFrame() {
		name = "VIP user";// ����������ʼ��������
		this.setLocation(300, 100);
		// this.setResizable(false); // ���岻�ܸı��С
		this.setTitle("Personal QQ");
		this.addWindowListener(new WindowClose());
//		this.addKeyListener(new SendMessageListener(this));
		if (debug)
			this.getContentPane().setBackground(Color.black);

		addMenu();

		jbt = new JButton("����");
		jbt.addMouseListener(new SendMessageListener(this));
		
		GridBagLayout layout = new GridBagLayout();// ���ֹ�����
		this.setLayout(layout);// ���ò��ֹ�����

		showArea = new JTextArea();// ��ʾ����

		sendArea = new JTextArea();// ��������

		topPanel = new JPanel();
		topPanel.setSize(new Dimension(300, 200));
		bottomPanel = new JPanel();
		rightpanel = new JPanel();
		middlepanel = new JPanel();
		middlepanel.setSize(new Dimension(300, 100));

		// showArea.setBackground(Color.white);
		// sendArea.setBackground(Color.white);
		showArea.setLineWrap(true);// �Զ�����
		showArea.setEditable(false);// ���ɱ༭
		sendArea.setLineWrap(true);
		sendArea.addKeyListener(new SendMessageListener(this));
		//���ı�����Ӽ�������Ч~��
		JScrollPane jsPaneltop = new JScrollPane(showArea);
		JScrollPane jsPanelbottom = new JScrollPane(sendArea);
		jsPaneltop.setPreferredSize(new Dimension(300, 200));
		//������ô�С����Ȼ��С���ٻָ�ʱ�����䡣
		jsPanelbottom.setPreferredSize(new Dimension(300, 100));

		this.compFillToJPanel(topPanel, jsPaneltop);
		this.compFillToJPanel(middlepanel, jsPanelbottom);

		leftPanel = this.pMerger(topPanel, middlepanel, 0, 3, 1);
		if (debug) {
			// bottomPanel.setBackground(Color.black);
			leftPanel.setBackground(Color.green);
			// rightpanel.setBackground(Color.LIGHT_GRAY);
		}
		this.add(leftPanel);
		// this.add(topPanel);
		this.add(rightpanel);
		// this.add(middlepanel);
		this.add(bottomPanel);
		this.add(jbt);

		GridBagConstraints gbc = new GridBagConstraints();
		// ����һ��GridBagConstraints��
		// ������������ӽ����������ʾλ��
		gbc.fill = GridBagConstraints.BOTH;// �������������Сʱ
		// NONEΪ�����������С
		// HORIZONTAL���ӿ������ʹ����ˮƽ��������������ʾ���򣬵��ǲ��ı�߶ȡ�
		// VERTICAL���Ӹ������ʹ���ڴ�ֱ��������������ʾ���򣬵��ǲ��ı��ȡ�
		// BOTH��ʹ�����ȫ��������ʾ����
		gbc.gridwidth = 1;// ˮƽ��ռ������
		gbc.gridheight = 1;// ��ֱ��ռ������
		gbc.weightx = 4;// �Ƿ�ˮƽ���������0��ʾ������,���������������
		gbc.weighty = 1;// �Ƿ�ֱ���������0��ʾ������,���������������
		layout.setConstraints(leftPanel, gbc);
		gbc.gridwidth = 0;
		gbc.gridheight = 2;// ��ֱ��ռ������
		gbc.weightx = 1;
		gbc.weighty = 1;
		layout.setConstraints(rightpanel, gbc);

		gbc.gridwidth = 1;
		gbc.gridheight = 1;// ��ֱ��ռ������
		gbc.weightx = 1;
		gbc.weighty = 0;
		layout.setConstraints(bottomPanel, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;// ��ֱ��ռ������
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		layout.setConstraints(jbt, gbc);

	}

	private void addMenu() {
		MenuBar menubar = new MenuBar();
		Menu menu = new Menu("�˵�");
		Menu mymenu = new Menu("����");
		MenuItem mymenuitem = new MenuItem("��Ϣ");
		mymenuitem.addActionListener(new MyInfo());
		MenuItem menuitem1 = new MenuItem("����");
		menuitem1.addActionListener(new SocketConnect(this));
		MenuItem menuitem2 = new MenuItem("��������");
		menuitem2.addActionListener(new SetName(this));
		MenuItem menuitem3 = new MenuItem("�˳�");
		menuitem3.addActionListener(new ExitPQ(this));
		menu.add(menuitem1);
		menu.add(menuitem2);
		menu.add(menuitem3);
		mymenu.add(mymenuitem);
		menubar.add(menu);
		menubar.add(mymenu);

		this.setMenuBar(menubar);
	}

	public JTextArea getShowArea() {
		return showArea;
	}

	public void setShowArea(JTextArea showArea) {
		this.showArea = showArea;
	}

	/**
	 * ��ʼ��Socket
	 * <table border="1">
	 * <tr>
	 * <td>
	 * hello</td>
	 * <td>world</td>
	 * </tr>
	 * <br>
	 * </br>
	 * <tr>
	 * <td>
	 * hello</td>
	 * <td>world</td>
	 * </tr>
	 * </table>
	 * 
	 * str�Ƿ�����IP
	 * 
	 * ûд�˿�Ĭ��8080
	 * д����ð�Ÿ���
	 * 
	 */
	public void initSocket(String str) {
		// TODO Auto-generated method stub
		String[] ip;
		if (str == null || str.trim().equals("")) {
			return;
		}
		if (str.contains(":")) {
			ip = str.split(":");
		} else {
			ip = new String[2];
			ip[0] = str;
			ip[1] = ConfClass.ServerPort;
		}
		try {
			this.socket = new Socket(ip[0], Integer.parseInt(ip[1]));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rt = new ReceiveThread(this);
		new Thread(rt).start();
	}

	/**
	 * @param jp
	 * @param c
	 *            ��һ���������һ��panel
	 */
	public void compFillToJPanel(JPanel jp, Component c) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		// System.out.println(gbc.hashCode());
		jp.setLayout(gridBagLayout);
		jp.add(c);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gridBagLayout.setConstraints(c, gbc);
	}

	/**
	 * ����������ϲ����������»����Ҳ���
	 * 
	 * @param jpa
	 * @param jpb
	 * @param ���»������Ҳ���
	 *            0�����£�1������
	 * @param one
	 *            ��һ���������
	 * @param two
	 *            �ڶ����������
	 * 
	 */
	public JPanel pMerger(Component jpa, Component jpb, int updownOrleftright,
			int one, int two) {
		JPanel jpc = new JPanel();
		if (debug)
			jpc.setBackground(Color.MAGENTA);
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		jpc.setLayout(gridBagLayout);
		jpc.add(jpa);
		jpc.add(jpb);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = updownOrleftright;
		gbc.gridheight = 1;
		if (updownOrleftright == 0) {
			gbc.weightx = 1;
			gbc.weighty = one;
			gridBagLayout.setConstraints(jpa, gbc);
			gbc.gridwidth = 0;
			gbc.gridheight = 1;
			gbc.weightx = 0;
			gbc.weighty = two;
			gridBagLayout.setConstraints(jpb, gbc);
		} else {
			gbc.weightx = one;
			gbc.weighty = 0;
			gridBagLayout.setConstraints(jpa, gbc);
			gbc.gridwidth = 0;
			gbc.gridheight = 0;
			gbc.weightx = two;
			gbc.weighty = 0;
			gridBagLayout.setConstraints(jpb, gbc);
		}
		
		return jpc;

	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public JTextArea getSendArea() {
		return sendArea;
	}

	public void setSendArea(JTextArea sendArea) {
		this.sendArea = sendArea;
	}

}
