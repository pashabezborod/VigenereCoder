package github.vigenerecoder.view;

public interface UserView {
    void callErrorMessage(String message);

    void callInfoMessage(String message);
    void initializeUI();

    String initializeCrypt();

    void refreshData();
}
