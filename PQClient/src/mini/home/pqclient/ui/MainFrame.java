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
	private JPanel topPanel;// 上面板历史消息区域
	private JPanel bottomPanel;//
	private Socket socket;// 当前客户端信息
	private JTextArea showArea;// 显示区域
	private JTextArea sendArea;// 发送区域
	private ReceiveThread rt;//接收信息的线程
	public String name;// 名字
	private JButton jbt;// 发送按钮
	private JPanel rightpanel;// 右边空白面板
	private JPanel middlepanel;// 中间空白面板
	private JPanel leftPanel;// 左边信息面板包括上中面板
	private boolean debug = false;// 是否调试

	public MainFrame() {
		initFrame();
		this.setSize(600, 600);
		this.setVisible(true);
		
	}

	private void initFrame() {
		name = "VIP user";// 这里可以填初始化的名字
		this.setLocation(300, 100);
		// this.setResizable(false); // 窗体不能改变大小
		this.setTitle("Personal QQ");
		this.addWindowListener(new WindowClose());
//		this.addKeyListener(new SendMessageListener(this));
		if (debug)
			this.getContentPane().setBackground(Color.black);

		addMenu();

		jbt = new JButton("发送");
		jbt.addMouseListener(new SendMessageListener(this));
		
		GridBagLayout layout = new GridBagLayout();// 布局管理器
		this.setLayout(layout);// 设置布局管理器

		showArea = new JTextArea();// 显示区域

		sendArea = new JTextArea();// 发送区域

		topPanel = new JPanel();
		topPanel.setSize(new Dimension(300, 200));
		bottomPanel = new JPanel();
		rightpanel = new JPanel();
		middlepanel = new JPanel();
		middlepanel.setSize(new Dimension(300, 100));

		// showArea.setBackground(Color.white);
		// sendArea.setBackground(Color.white);
		showArea.setLineWrap(true);// 自动换行
		showArea.setEditable(false);// 不可编辑
		sendArea.setLineWrap(true);
		sendArea.addKeyListener(new SendMessageListener(this));
		//在文本域里加监听才有效~！
		JScrollPane jsPaneltop = new JScrollPane(showArea);
		JScrollPane jsPanelbottom = new JScrollPane(sendArea);
		jsPaneltop.setPreferredSize(new Dimension(300, 200));
		//这里设好大小，不然最小化再恢复时界面会变。
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
		// 定义一个GridBagConstraints，
		// 是用来控制添加进的组件的显示位置
		gbc.fill = GridBagConstraints.BOTH;// 组件比所在区域小时
		// NONE为不调整组件大小
		// HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
		// VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
		// BOTH：使组件完全填满其显示区域。
		gbc.gridwidth = 1;// 水平所占格子数
		gbc.gridheight = 1;// 垂直所占格子数
		gbc.weightx = 4;// 是否水平拉伸组件，0表示不拉伸,拉伸输入拉伸比例
		gbc.weighty = 1;// 是否垂直拉伸组件，0表示不拉伸,拉伸输入拉伸比例
		layout.setConstraints(leftPanel, gbc);
		gbc.gridwidth = 0;
		gbc.gridheight = 2;// 垂直所占格子数
		gbc.weightx = 1;
		gbc.weighty = 1;
		layout.setConstraints(rightpanel, gbc);

		gbc.gridwidth = 1;
		gbc.gridheight = 1;// 垂直所占格子数
		gbc.weightx = 1;
		gbc.weighty = 0;
		layout.setConstraints(bottomPanel, gbc);
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;// 垂直所占格子数
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		layout.setConstraints(jbt, gbc);

	}

	private void addMenu() {
		MenuBar menubar = new MenuBar();
		Menu menu = new Menu("菜单");
		Menu mymenu = new Menu("关于");
		MenuItem mymenuitem = new MenuItem("信息");
		mymenuitem.addActionListener(new MyInfo());
		MenuItem menuitem1 = new MenuItem("连接");
		menuitem1.addActionListener(new SocketConnect(this));
		MenuItem menuitem2 = new MenuItem("设置名字");
		menuitem2.addActionListener(new SetName(this));
		MenuItem menuitem3 = new MenuItem("退出");
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
	 * 初始化Socket
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
	 * str是服务器IP
	 * 
	 * 没写端口默认8080
	 * 写了用冒号隔开
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
	 *            把一个组件填满一个panel
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
	 * 把两个组件合并起来成上下或左右布局
	 * 
	 * @param jpa
	 * @param jpb
	 * @param 上下或是左右布局
	 *            0是上下，1是左右
	 * @param one
	 *            第一个组件比例
	 * @param two
	 *            第二个组件比例
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
