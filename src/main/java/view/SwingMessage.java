package view;

import javax.swing.*;

public class SwingMessage {

    public static String getInputData(String message, String title, JFrame jFrame) {
        return JOptionPane.showInputDialog(jFrame, message, title, JOptionPane.QUESTION_MESSAGE);
    }

    public static void showErrorMessage(String message, JFrame jFrame) {
        JOptionPane.showMessageDialog(jFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
        jFrame.dispose();
        System.exit(1);
    }

    public static void showInfoMessage(String message, JFrame jFrame) {
        JOptionPane.showMessageDialog(jFrame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirmDialog(String message, JFrame jFrame) {
        int temp = JOptionPane.showConfirmDialog(jFrame, message, "Are you sure?", JOptionPane.YES_NO_OPTION);
        return temp == 0;
    }
}
