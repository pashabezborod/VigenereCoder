package main.view;

public interface UserView {
    void callErrorMessage(String message, boolean isCritical, Exception e);
    void callInfoMessage(String message);
    String callInputMessage(String message, String title);

    void initialize();

    void refreshData();
}
