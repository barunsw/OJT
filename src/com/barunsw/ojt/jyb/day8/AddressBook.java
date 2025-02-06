package day8;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddressBook extends JPanel {
	private static Logger LOGGER = LoggerFactory.getLogger(AddressBook.class);

	private CardLayout cardLayout = new CardLayout();
	private JTable jTable = new JTable();
	public static final int WIDTH = 600;
	public static final int HEIGHT = 400;

	private JPanel jPanel_Form = new JPanel();
	private JLabel label_Name = new JLabel("이름");
	private JTextField textField_Name = new JTextField(10);
	private JLabel label_Age = new JLabel("나이");
	private JTextField textField_Age = new JTextField(10);
	private JRadioButton btnBoy = new JRadioButton("남자");
	private JRadioButton btnGirl = new JRadioButton("여자");

	private JPanel jPanel_Command = new JPanel();
	private JLabel label_Phone = new JLabel("전화번호");
	private JTextField textField_Phone = new JTextField(10);
	private JLabel label_Address = new JLabel("주소");
	private JTextField textField_Address = new JTextField(10);

	private JPanel jPanel_Table = new JPanel();
	private JButton jButton_Add = new JButton("추가");
	private JButton jButton_Change = new JButton("변경");

	private JScrollPane scrollPane = new JScrollPane();
	private GridBagLayout gridBagLayout = new GridBagLayout();

	public AddressBook() {
		try {
			initComponent();
			initTable();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() {

	    jPanel_Form.setLayout(new GridBagLayout());

	    jPanel_Form.add(label_Name, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));

	    jPanel_Form.add(textField_Name, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

	    jPanel_Form.add(label_Age, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

	    jPanel_Form.add(textField_Age, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

	    jPanel_Form.add(btnBoy, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

	    jPanel_Form.add(btnGirl, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(5, 0, 5, 5), 0, 0));

	    label_Phone.setPreferredSize(new Dimension(120, 22));
	    jPanel_Form.add(label_Phone, new GridBagConstraints(0, 1, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

	    jPanel_Form.add(textField_Phone, new GridBagConstraints(1, 1, 6, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

	    jPanel_Form.add(label_Address, new GridBagConstraints(0, 2, 1, 1, 0.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));

	    jPanel_Form.add(textField_Address, new GridBagConstraints(1, 2, 6, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(0, 0, 5, 5), 0, 0));

	    jPanel_Command.setLayout(new GridBagLayout());

	    jPanel_Command.add(jButton_Add, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.EAST,
	            GridBagConstraints.VERTICAL, new Insets(0, 5, 0, 6), 0, 0));

	    jPanel_Command.add(jButton_Change, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0, GridBagConstraints.EAST,
	            GridBagConstraints.VERTICAL, new Insets(0, 5, 0, 6), 0, 0));

	    this.setLayout(gridBagLayout);

	    this.add(jPanel_Form, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

	    this.add(jPanel_Command, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
	            GridBagConstraints.VERTICAL, new Insets(0, 0, 0, 0), 0, 0));

	    this.add(jPanel_Table, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
	            GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
	}

	private void initTable() {
		String[] columns = { "이름", "나이", "전화번호", "주소" };

		DefaultTableModel tableModel = new DefaultTableModel(columns, 100);
		jTable = new JTable(tableModel);

		scrollPane.setViewportView(jTable);

		jPanel_Table.setLayout(new GridBagLayout());
		jPanel_Table.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
	}

}
