package com.barunsw.ojt.mjg.day19;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbConnectionDialog extends JDialog {
	private static final Logger LOGGER = LoggerFactory.getLogger(DbConnectionDialog.class);
	
	private final int TEXT_FIELD_SIZE 		= 15;
	
	private GridBagLayout gridBagLayout 	= new GridBagLayout();
	private BorderLayout borderLayout 		= new BorderLayout();
	
	private JPanel jPanel_Form 				= new JPanel(gridBagLayout);
	private JTextField jTextField_Id 		= new JTextField(TEXT_FIELD_SIZE);      
	private JPasswordField jPasswordField 	= new JPasswordField(TEXT_FIELD_SIZE);  
	private JTextField jTextField_Db 		= new JTextField(TEXT_FIELD_SIZE);      

    private JLabel jLabel_Id 				= new JLabel("ID");      
    private JLabel jLabel_Password 			= new JLabel("PW");      
    private JLabel jLabel_Db 				= new JLabel("DB");      

    private JPanel jPanel_Button 			= new JPanel(new FlowLayout());
    private JButton jButton_Ok 				= new JButton("확인");
    private JButton jButton_Cancel 			= new JButton("취소");

    private boolean isConfirmed = false;

    public DbConnectionDialog(MainFrame parent) {
        super(parent, "DB 연결", true);
        setLayout(borderLayout);
        
        initForm();
        initButtonPanel();
        
        add(jPanel_Form, BorderLayout.CENTER);
        add(jPanel_Button, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    private void initForm() {
        jPanel_Form.add(jLabel_Id, new GridBagConstraints(
            0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 5, 5, 5), 0, 0
        ));
        
        jPanel_Form.add(jTextField_Id, new GridBagConstraints(
            1, 0, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(5, 0, 5, 5), 0, 0
        ));
        
        jPanel_Form.add(jLabel_Password, new GridBagConstraints(
            0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
        ));
        
        jPanel_Form.add(jPasswordField, new GridBagConstraints(
            1, 1, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
        jPanel_Form.add(jLabel_Db, new GridBagConstraints(
            0, 2, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 5, 5, 5), 0, 0
        ));
        
        jPanel_Form.add(jTextField_Db, new GridBagConstraints(
            1, 2, 1, 1, 1.0, 1.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0
        ));
        
        jTextField_Id.addActionListener(new DbConnectionDialog_EnterActionListener(this));
        jPasswordField.addActionListener(new DbConnectionDialog_EnterActionListener(this));
        jTextField_Db.addActionListener(new DbConnectionDialog_EnterActionListener(this));
    }
    
    private void initButtonPanel() {
        jButton_Ok.addActionListener(new DbConnectionDialog_Ok_ActionListener(this));
        jButton_Cancel.addActionListener(new DbConnectionDialog_Cancel_ActionListener(this));
        
        jPanel_Button.add(jButton_Ok);
        jPanel_Button.add(jButton_Cancel);
    }

	public void showDialog() {
		resetFields();
		setVisible(true);
	}

	private void resetFields() {
		jTextField_Id.setText("");
		jPasswordField.setText("");
		jTextField_Db.setText("");
	}
	
    public boolean isConfirmed() {
        return isConfirmed;
    }
    
    public void setConfirmed(boolean confirmed) {
        this.isConfirmed = confirmed;
    }

    public String getId() {
        return jTextField_Id.getText().trim();
    }

    public String getPassword() {
        return new String(jPasswordField.getPassword()).trim();
    }

    public String getDb() {
        return jTextField_Db.getText().trim();
    }
}

class DbConnectionDialog_Ok_ActionListener implements ActionListener {
    private DbConnectionDialog dialog;
    
    public DbConnectionDialog_Ok_ActionListener(DbConnectionDialog dialog) {
        this.dialog = dialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.setConfirmed(true);
        dialog.setVisible(false);
    }
}

class DbConnectionDialog_Cancel_ActionListener implements ActionListener {
    private DbConnectionDialog dialog;
    
    public DbConnectionDialog_Cancel_ActionListener(DbConnectionDialog dialog) {
        this.dialog = dialog;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        dialog.setConfirmed(false);
        dialog.setVisible(false);
    }
}

class DbConnectionDialog_EnterActionListener implements ActionListener {
	private DbConnectionDialog dialog;
	 
	public DbConnectionDialog_EnterActionListener(DbConnectionDialog dialog) {
		this.dialog = dialog;
	}
	 
	@Override
	public void actionPerformed(ActionEvent e) {
	    dialog.setConfirmed(true);
	    dialog.setVisible(false);
	}
}