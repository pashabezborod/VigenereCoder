package github.vigenerecoder.view;

import github.vigenerecoder.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

@Component
@Lazy
public class SwingUserView extends JFrame implements UserView {

    private Connector connector;
    private JComboBox<String> names;
    private JTextField yourPassword;
    private JTextField newPassName;
    private JTextField newPassword;

    @Override
    public String initializeCrypt() {
        return callInputMessage("Enter your crypt", "Welcome to Vigenere Coder!");
    }

    @Autowired
    public void setConnector(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void callErrorMessage(String message) {
        SwingMessage.showErrorMessage(message, this);
    }

    @Override
    public void callInfoMessage(String message) {
        SwingMessage.showInfoMessage(message, this);
    }

    public String callInputMessage(String message, String title) {
        return SwingMessage.getInputData(message, title, this);
    }

    public void initializeUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(705, 290);
        setLayout(null);
        setLocationRelativeTo(null);

        ArrayList<JComponent> components = new ArrayList<>();
        Font italicFont = new Font("Verdana", Font.ITALIC, 15);
        Font boldFont = new Font("Verdana", Font.BOLD, 20);

        JLabel firstText = new JLabel("Your passwords");
        firstText.setBounds(0, 5, 705, 30);
        firstText.setFont(boldFont);
        firstText.setHorizontalAlignment(JLabel.CENTER);
        components.add(firstText);

        names = new JComboBox<>();
        names.setBounds(5, 55, 275, 30);
        refreshNamesBox();
        names.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                initializePasswordField();
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                initializePasswordField();
            }
        });
        components.add(names);

        JLabel yourCodeLabel = new JLabel("Your password:");
        yourCodeLabel.setBounds(275, 55, 150, 30);
        yourCodeLabel.setFont(italicFont);
        yourCodeLabel.setHorizontalAlignment(JLabel.CENTER);
        components.add(yourCodeLabel);

        yourPassword = new JTextField();
        yourPassword.setBounds(415, 55, 280, 30);
        yourPassword.setHorizontalAlignment(JLabel.CENTER);
        initializePasswordField();
        yourPassword.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(yourPassword.getText()), null);
                callInfoMessage("Password copied to your clipboard");
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        components.add(yourPassword);

        JLabel secondText = new JLabel("Add a new password");
        secondText.setBounds(0, 105, 705, 30);
        secondText.setFont(boldFont);
        secondText.setHorizontalAlignment(JLabel.CENTER);
        components.add(secondText);

        newPassName = new JTextField("Password name");
        newPassName.setBounds(5, 155, 220, 30);
        newPassName.setHorizontalAlignment(JLabel.CENTER);
        newPassName.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (newPassName.getText().equals("Password name")) newPassName.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (newPassName.getText().equals("")) newPassName.setText("Password name");
            }
        });
        components.add(newPassName);

        newPassword = new JTextField("Password to add");
        newPassword.setBounds(240, 155, 295, 30);
        newPassword.setHorizontalAlignment(JLabel.CENTER);
        newPassword.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                if (newPassword.getText().equals("Password to add")) {
                    newPassword.setText("");
                }
            }

            public void focusLost(FocusEvent e) {
                if (newPassword.getText().equals("")) newPassword.setText("Password to add");
            }
        });
        components.add(newPassword);

        JButton buttonNewPass = new JButton("Add password");
        buttonNewPass.setBounds(550, 155, 145, 30);
        buttonNewPass.addActionListener(e -> {
            connector.addNewPassword(newPassName.getText(), newPassword.getText());
            initializePasswordField();
            newPassword.setText("Password to add");
            newPassName.setText("Password name");
        });
        components.add(buttonNewPass);

        JButton buttonChangeCrypt = new JButton("Change your crypt");
        buttonChangeCrypt.setBounds(5, 210, 220, 30);
        buttonChangeCrypt.addActionListener(e -> connector.changeCrypt(SwingMessage.getInputData("Enter your new crypt", "Crypt changing", this)));
        components.add(buttonChangeCrypt);

        JButton buttonDeletePass = new JButton("Delete a password");
        buttonDeletePass.setBounds(240, 210, 220, 30);
        buttonDeletePass.addActionListener(e -> {
            if (SwingMessage.showConfirmDialog("Do you want to delete this password?", this)) {
                connector.deletePassword((String) names.getSelectedItem());
                initializePasswordField();
            }
        });
        components.add(buttonDeletePass);

        JButton buttonSaveAndExit = new JButton("Change password");
        buttonSaveAndExit.setBounds(475, 210, 220, 30);
        buttonSaveAndExit.addActionListener(e -> {
            String newPass = callInputMessage("Enter new password for " + names.getSelectedItem(), "Change password");
            connector.changePassword((String) names.getSelectedItem(), newPass);
        });
        components.add(buttonSaveAndExit);

        components.forEach(this::add);
        setVisible(true);
    }

    public void refreshData() {
        refreshNamesBox();
        initializePasswordField();
    }

    private void refreshNamesBox() {
        names.removeAllItems();
        ArrayList<String> passes = connector.getAllNames();
        for (String pass : passes) names.addItem(pass);
    }

    private void initializePasswordField() {
        String name = (String) names.getSelectedItem();
        if (name == null || name.equals("")) {
            yourPassword.setText("");
            return;
        }
        String pass = connector.getPassword(name);
        yourPassword.setText(pass);
    }
}
