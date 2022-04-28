package com.barunsw.ojt.cjs.day22;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectSetDialog extends JDialog {
	private final static Logger LOGGER = LoggerFactory.getLogger(ConnectSetDialog.class);
	private static ConnectSetDialog dialog;

	private JPanel jPanel_Main = new JPanel();
	private JLabel jLabel_ConnectType = new JLabel("연결 종류");
	private JLabel jLabel_ConnectIp = new JLabel("IP");
	private JLabel jLabel_ConnectPort = new JLabel("PORT");
	private JLabel jLabel_ConnectUsername = new JLabel("USERNAME");
	private JLabel jLabel_ConnectPassword = new JLabel("PASSWORD");
	private JLabel jLabel_ConnectDbname = new JLabel("DB NAME");

	private static JTextField jTextField_Ip = new JTextField("localhost");
	private static JTextField jTextField_Port = new JTextField("3306");
	private static JTextField jTextField_Username = new JTextField("root");
	private static JPasswordField jTextField_Password = new JPasswordField("js0911");
	private static JTextField jTextField_Dbname = new JTextField("ojt");
	private GridBagLayout grid = new GridBagLayout();
	private static JComboBox<String> jComboBox_DbType;

	private JPanel jPanel_Button = new JPanel();
	private JButton jButton_Connect = new JButton("확인");
	private JButton jButton_Cancel = new JButton("취소");

	private String jComboBox_Menu[] = { "MARIA", "POSTGRES" };
	
	public static ConnectVo connectVo = new ConnectVo();

	public ConnectSetDialog(DbClientFrame frame, String title, ModalityType modalType) {
		super(frame, title, modalType);
		
		try {
			initComponent();
			initEvent();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void initComponent() {
		
		this.setContentPane(jPanel_Main);
		jComboBox_DbType = new JComboBox<String>(jComboBox_Menu);
		jPanel_Main.setLayout(grid);

		jPanel_Main.add(jLabel_ConnectType, new GridBagConstraints(
						0, 0, 1, 1, 
						0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jComboBox_DbType, new GridBagConstraints(
						1, 0, 1, 1, 
						1.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jLabel_ConnectIp, new GridBagConstraints(
						0, 1, 1, 1, 0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jTextField_Ip, new GridBagConstraints(1, 1, 1, 1,
						0.0, 0.0, 
						GridBagConstraints.CENTER,
						GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0));
		jPanel_Main.add(jLabel_ConnectPort, new GridBagConstraints(
						0, 2, 1, 1,
						0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jTextField_Port, new GridBagConstraints(
						1, 2, 1, 1, 
						0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5),
						0, 0
						));
		jPanel_Main.add(jLabel_ConnectUsername, new GridBagConstraints(
						0, 3, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5),
						0, 0
						));
		jPanel_Main.add(jTextField_Username, new GridBagConstraints(
						1, 3, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jLabel_ConnectPassword, new GridBagConstraints(
						0, 4, 1, 1, 
						0.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0
						));
		jPanel_Main.add(jTextField_Password, new GridBagConstraints(
						1, 4, 1, 1, 
						0.0, 0.0, 
						GridBagConstraints.CENTER, GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jLabel_ConnectDbname, new GridBagConstraints(
						0, 5, 1, 1,
						0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Main.add(jTextField_Dbname, new GridBagConstraints(
						1, 5, 1, 1, 
						0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH,
						new Insets(5, 5, 5, 5),
						0, 0
						));
		jPanel_Main.add(jPanel_Button, new GridBagConstraints(
						0, 6, 1, 1, 
						0.0, 0.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Button.add(jButton_Connect, new GridBagConstraints(
						0, 0, 1, 1, 
						1.0, 1.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5), 
						0, 0
						));
		jPanel_Button.add(jButton_Cancel, new GridBagConstraints(
						1, 0, 1, 1, 
						1.0, 1.0, 
						GridBagConstraints.CENTER,GridBagConstraints.BOTH, 
						new Insets(5, 5, 5, 5),
						0, 0
						));
	}

	private void initEvent() {
		jButton_Connect.addActionListener(new ConnectSetDialog_jButton_Connect_ActionListener(this));
		jButton_Cancel.addActionListener(new ConnectSetDialog_jButton_Cancel_ActionListener(this));
	}

	private void clearForm() {
		jTextField_Ip.setText("");
		jTextField_Username.setText("");
		jTextField_Password.setText("");
		jTextField_Port.setText("");
		jTextField_Dbname.setText("");
		jComboBox_DbType.setSelectedIndex(0);
	}

	private static void updateForm(ConnectVo selectVo) {
		if (selectVo != null) {
			String port = connectVo.getDbUrl().substring(connectVo.getDbUrl().lastIndexOf(":") + 1);
			String ip = connectVo.getDbUrl().substring(0, connectVo.getDbUrl().indexOf(":"));

			jTextField_Ip.setText(ip);
			jTextField_Username.setText(selectVo.getDbUser());
			jTextField_Password.setText(selectVo.getDbPassword());
			jTextField_Port.setText(port);
			jTextField_Dbname.setText(selectVo.getDbName());
			jComboBox_DbType.setEditable(false);
			jComboBox_DbType.setSelectedItem(selectVo.getDb_type().toString());
		}
		else {
			JOptionPane.showMessageDialog(null, "입력된 정보가 없습니다");
		}
	}

	public static ConnectVo showDialog(DbClientFrame frame, ConnectSetType setType, ConnectVo selectVo) {
		String title = "";
		if (setType == ConnectSetType.ADD) {
			title = "연결 생성";
		}
		else if (setType == ConnectSetType.UPDATE) {
			updateForm(selectVo);
			title = "연결 수정";
		}

		if (dialog == null) {
			dialog = new ConnectSetDialog(frame, title, ModalityType.APPLICATION_MODAL);
		}
		else {
			dialog.setTitle(title);
		}
		Dimension scrDim = Toolkit.getDefaultToolkit().getScreenSize();

		int xPos = (scrDim.width - DbClientFrame.WIDTH) / 2;
		int yPos = (scrDim.height - DbClientFrame.HEIGHT) / 2;

		dialog.setLocationRelativeTo(frame);
		dialog.setBounds(xPos, yPos, 400, 400);
		dialog.setVisible(true);
		
		return connectVo;
	}

	public void jButton_Connect_ActionListener() throws Exception {
		connectVo.setDbUrl(String.format("%s:%s", jTextField_Ip.getText(), jTextField_Port.getText()));
		connectVo.setDbUser(jTextField_Username.getText());
		connectVo.setDbPassword(String.valueOf(jTextField_Password.getPassword()));
		connectVo.setDbName(jTextField_Dbname.getText());
		connectVo.setDb_type(Db_type.toDb_type(jComboBox_DbType.getSelectedItem().toString()));
		this.dispose();
		clearForm();
	}

	public void jButton_Cancel_ActionListener(ActionEvent e) {
		this.dispose();
		clearForm();
	}
}

//connect
class ConnectSetDialog_jButton_Connect_ActionListener implements ActionListener {
	private ConnectSetDialog adaptee;

	public ConnectSetDialog_jButton_Connect_ActionListener(ConnectSetDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			adaptee.jButton_Connect_ActionListener();
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

//cancel
class ConnectSetDialog_jButton_Cancel_ActionListener implements ActionListener {
	private ConnectSetDialog adaptee;

	public ConnectSetDialog_jButton_Cancel_ActionListener(ConnectSetDialog adaptee) {
		this.adaptee = adaptee;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		adaptee.jButton_Cancel_ActionListener(e);
	}
}