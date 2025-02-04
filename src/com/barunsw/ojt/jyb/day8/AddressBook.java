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

	public AddressBook() {
		try {
			initComponent();
			initTable();
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

	private void initComponent() {
		JPanel jPanel_Name = new JPanel();
		jPanel_Name.setLayout(new GridBagLayout());

		JLabel label_Name = new JLabel("이름");
		JTextField textField_Name = new JTextField(10);
		JLabel label_Age = new JLabel("나이");
		JTextField textField_Age = new JTextField(10);
		JLabel label_Gender = new JLabel("성별");
		JRadioButton btnBoy = new JRadioButton("남자");
		JRadioButton btnGirl = new JRadioButton("여자");
		JLabel label_Phone = new JLabel("전화번호");
		JTextField textField_Phone = new JTextField(10);
		JLabel label_Address = new JLabel("주소");
		JTextField textField_Address = new JTextField(10);
		JButton jButton_Add = new JButton("추가");
		JButton jButton_Change = new JButton("변경");

		jPanel_Name.add(label_Name, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(textField_Name, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(label_Age, new GridBagConstraints(2, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(textField_Age, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(label_Gender, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(btnBoy, new GridBagConstraints(5, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(btnGirl, new GridBagConstraints(6, 0, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(label_Phone, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(textField_Phone, new GridBagConstraints(1, 1, 6, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(label_Address, new GridBagConstraints(0, 2, 1, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(textField_Address, new GridBagConstraints(1, 2, 6, 1, 1.0, 1.0, GridBagConstraints.WEST,
				GridBagConstraints.BOTH, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(jButton_Add, new GridBagConstraints(5, 3, 1, 1, 1.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));

		jPanel_Name.add(jButton_Change, new GridBagConstraints(6, 3, 6, 1, 1.0, 1.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(0, 5, 0, 5), 0, 0));

		this.setLayout(new GridBagLayout());

		this.add(jPanel_Name, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

		JScrollPane scrollPane = new JScrollPane(jTable);
		scrollPane.setPreferredSize(new java.awt.Dimension(400, 200));

		this.add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.BOTH, new Insets(5, 5, 5, 5), 0, 0));
	}

	private void initTable() {

		String[] columns = { "이름", "나이", "성별", "전화번호", "주소" };

		DefaultTableModel tableModel = new DefaultTableModel(columns, 200);
		tableModel.setColumnIdentifiers(columns);

		jTable.setModel(tableModel);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Address Book");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.add(new AddressBook());
		frame.setVisible(true);
	}
}
