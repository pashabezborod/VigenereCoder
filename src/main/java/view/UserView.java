package view;

public interface UserView {
    void callErrorMessage(String message);

    void callInfoMessage(String message);

    String callInputMessage(String message, String title);

    void initialize();

    void refreshData();
}
