package CodeForServer;

import SharedUtilities.Utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.JOptionPane.*;

/**
 * To Do
 * <p>
 * Setup send mail
 */
class Stage2login extends absGUI {

    public void getMainDisplay() {
        setGUI("Main Login");

        JLabel userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JTextField userTextF = new JTextField("glennmarm@gmail.com", 10);

        JLabel passLabel = new JLabel("Password : ", JLabel.CENTER);
        passLabel.setForeground(Color.WHITE);
        passLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JPasswordField PasswordField = new JPasswordField("123", 10);

        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(userLabel);
        topPanel.add(userTextF);
        topPanel.add(passLabel);
        topPanel.add(PasswordField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            String userName = userTextF.getText();
            String Password = PasswordField.getText();

//            boolean logicResult = isCredentialsProper(userName, Password);
//            System.out.println("The logic result for loginButton is: " + logicResult);
//
            String query = "SELECT userID FROM USERS where username ='" + userName + "' and Password='" + Password + "'";
            int userID = intQuery(query);

            if (userID > 0) {
                System.out.println("Password: " + Password + " corresponds to user: " + userName);
                User user = cacheUser(userID);
                Stage3Home Stage3Home = new Stage3Home(user);
                Stage3Home.getMainDisplayDSP();
                mainFrame.dispose();
            } else {
                System.out.println("[ERROR] The Password: " + Password + "  is NOT valid for user: " + userName);
                showMessageDialog(mainFrame, "Your Credentials are not Valid", "info.", ERROR_MESSAGE);
            }
        });

        JButton resetPas = new JButton("Reset Password");
        resetPas.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getGatherIdentityDataDSP();
        });

        JButton CreateAcct = new JButton("Create account");
        CreateAcct.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            createNewUserDSP();
        });

        bottomPanel.add(loginButton);
        bottomPanel.add(resetPas);
        bottomPanel.add(CreateAcct);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    private void getGatherIdentityDataDSP() {
        String dspName = "Gather Identity Data";
        setGUI(dspName);

        JLabel userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JTextField userTextF = new JTextField("glennmarm@gmail.com", 10);

        topPanel.setLayout(new GridLayout(1, 2, 5, 5));
        topPanel.add(userLabel);
        topPanel.add(userTextF);

        JButton setTokenButton = new JButton("Entered Token");
        setTokenButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            isTokenValidDSP(userTextF.getText());
        });

        JButton getTokenButton = new JButton("Get Token");
        getTokenButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            String userName = userTextF.getText();
            System.out.println("Verifying if user exist: " + userName);

            String query = "SELECT FirstName FROM USERS where Email ='" + userName + "'";
            ArrayList<String> QueryResults = ExeDDL(query);
            boolean logicResult = !QueryResults.isEmpty();
            System.out.println("The logic result for isTokenValid is: " + logicResult);
            if (logicResult) {
                System.out.println(userName + " Is a valid user name");
                setToken(userName);
            } else {
                System.out.println(userName + " Is a NOT valid user name");
            }
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getMainDisplay();
        });

        bottomPanel.add(setTokenButton);
        bottomPanel.add(getTokenButton);
        bottomPanel.add(closeButton);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    private void isTokenValidDSP(String user) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");

        String dspName = "Verify Token";
        setGUI(dspName);

        JLabel userLabel = new JLabel("USERNAME : ", JLabel.CENTER);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JTextField userTextF = new JTextField(user, 10);

        JLabel tokenLabel = new JLabel("Token : ", JLabel.CENTER);
        tokenLabel.setForeground(Color.WHITE);
        tokenLabel.setFont(new Font("Ariel", Font.BOLD, 18));

        JFormattedTextField tokenInt = new JFormattedTextField();
        tokenInt.setColumns(4);
        topPanel.add(tokenInt);

        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(userLabel);
        topPanel.add(userTextF);
        topPanel.add(tokenLabel);
        topPanel.add(tokenInt);

        JButton isTokenValidButton = new JButton("Verify Token");
        isTokenValidButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            boolean logicResult = isTokenValid(userTextF.getText(), Integer.parseInt(tokenInt.getText().replaceAll(",", "")));
            System.out.println("The logic result for vfyTokenButton is: " + logicResult);
            if (logicResult) {
                getResetPasswordDSP(userTextF.getText());
            } else {
                showMessageDialog(mainFrame, "The Token: " + tokenInt.getText() + " is not valid for User: " + userTextF.getText(), "Error.", ERROR_MESSAGE);
            }
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getGatherIdentityDataDSP();
        });

        bottomPanel.add(isTokenValidButton);
        bottomPanel.add(closeButton);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    private boolean isTokenValid(String userName, int token) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        System.out.println("will attempt validate Token: " + token + " for user: " + userName);

        ArrayList<String> QueryResults;
        String query = "SELECT FirstName FROM USERS where Email ='" + userName + "' and Token=" + token;
        QueryResults = ExeDDL(query);

        boolean logicResult = !QueryResults.isEmpty();
        System.out.println("The logic result for isTokenValid is: " + logicResult);
        if (logicResult) {
            System.out.println("The token: " + token + " is valid for user: " + userName);
            return true;
        } else {
            System.out.println("[ERROR] The token: " + token + " is NOT valid for user: " + userName);
            return false;
        }
    }

    private void setToken(String userName) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        Utilities Utilities = new Utilities();
        String query;

        System.out.println("will attempt to create token for user: " + userName);
        int token = Utilities.GenerateRanNum(9999, 1000);

        query = "UPDATE Users set Token=" + token + " where username='" + userName + "'";
        System.out.println((ExeDML(query) == 0)
                ? "Token: " + token + " was added to the user: " + userName
                : "[ERROR] Fail to add token: " + token + " to user: " + userName);

        Runnable mailServer = () -> {
            System.out.println("Runnable running");
            ArrayList<String> QueryResults;
            String query1 = "SELECT EMail FROM USERS where username ='" + userName + "'";
            QueryResults = ExeDDL(query1);
            String Email = QueryResults.get(0);
            String Subject = "Reset Token";
            String message = "Your token is: " + token + "\n\t Thank you";
            System.out.println("will attempt to send token: " + token + " to Email: " + userName);
            Utilities.sendMail(Email, Subject, message);
            showMessageDialog(mainFrame, "We will try to send a token to the Email: " + Email, "info.", ERROR_MESSAGE);
        };

        Thread thread = new Thread(mailServer);
        thread.start();

    }

    private void getResetPasswordDSP(String userName) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        String screenName = "Create new Password";
        setGUI(screenName);

        JLabel PasswordLabel = new JLabel("Password: ", JLabel.CENTER);
        PasswordLabel.setForeground(Color.WHITE);
        PasswordLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JPasswordField PasswordTextF = new JPasswordField("123", 10);

        JLabel RetypePasswordLabel = new JLabel("Retype Password: ", JLabel.CENTER);
        RetypePasswordLabel.setForeground(Color.WHITE);
        RetypePasswordLabel.setFont(new Font("Ariel", Font.BOLD, 18));
        JPasswordField ResetPasswordTextF = new JPasswordField("123", 10);

        topPanel.setLayout(new GridLayout(2, 2, 5, 5));
        topPanel.add(PasswordLabel);
        topPanel.add(PasswordTextF);
        topPanel.add(RetypePasswordLabel);
        topPanel.add(ResetPasswordTextF);

        JButton setPasswordButton = new JButton("Set Password");
        setPasswordButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            boolean logicResult = Arrays.equals(PasswordTextF.getPassword(), ResetPasswordTextF.getPassword());
            System.out.println("The logic result for setPasswordButton is: " + logicResult);
            if (logicResult) {
                System.out.println("The Passwords are matching");
                setPassword(userName, PasswordTextF.getText()
                );
                showMessageDialog(mainFrame, "Your Password has been updated.", "info.", INFORMATION_MESSAGE);
                getMainDisplay();
            } else {
                System.out.println("The Passwords are matching");
                showMessageDialog(mainFrame, "The Password did not match.\n Please retype it.", "info.", ERROR_MESSAGE);
            }
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            isTokenValidDSP(userName);
        });

        bottomPanel.add(setPasswordButton);
        bottomPanel.add(closeButton);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    private void setPassword(String userName, String Password) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        System.out.println("Will attempt to reset Password for user: " + userName);

        User user = new User();
        user.setUserName(userName);
        user.setPassword(Password);

        String query = "UPDATE Users set Password='" + user.getPassword() + "' where username='" + user.getUserName() + "'";
        System.out.println((ExeDML(query) == 0) ? "The Password has been updated for user: " + user.getUserName()
                : "[ERROR] Fail to update Password for user: " + user.getUserName());
    }

    /**
     * Creates a new cacheUser. Intended to be called when the "Create account"
     * button is press.
     */
    private void createNewUserDSP() {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        String screenName = "Create Account";
        setGUI(screenName);

        String[] labelLastName = {"Name", "LastName", "EMail", "Username", "Password", "BankCard"};
        String[] textFirstName = {"Name", "Last Name", "some.one@some.where", "User Name", "Password", "Bank Card"};
        JLabel[] Label = new JLabel[labelLastName.length];
        JTextField[] TextField = new JTextField[textFirstName.length];

        topPanel.setLayout(new GridLayout(6, 2, 15, 10));

        for (int i = 0; i < Label.length; i++) {
            Label[i] = new JLabel(textFirstName[i]);
            Label[i].setFont(new Font("Ariel", Font.BOLD, 15));
            Label[i].setForeground(Color.black);
            Label[i].setOpaque(true);
            topPanel.add(Label[i]);

            TextField[i] = new JTextField(labelLastName[i] + "TField");
            TextField[i].setText(textFirstName[i]);
            TextField[i].setFont(new Font("Ariel", Font.BOLD, 16));
            topPanel.add(TextField[i]);
        }

        JButton setCustomerButton = new JButton("Create Account");
        setCustomerButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            setUser(TextField);
        });

        JButton closeButton = new JButton("Cancel");
        closeButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            getMainDisplay();
        });

        bottomPanel.add(setCustomerButton, BorderLayout.LINE_END);
        bottomPanel.add(closeButton, BorderLayout.LINE_END);

        basePanel.setLayout(new GridLayout(2, 1));

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

    /**
     * Creates a new cacheUser. Intended to be called when the Create account
     * button is press.
     */
    private void setUser(JTextField[] TextField) {
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()) + " Trace Info");
        System.out.println("Verifying Data...");

        User user = new User();
        user.setFirstName(TextField[0].getText());
        user.setLastName(TextField[1].getText());
        user.setEMail(TextField[2].getText());
        user.setPassword(TextField[4].getText());
        user.setBankCard(TextField[4].getText());

        System.out.println("will create the fallowing user");
        for (JTextField TextField1 : TextField) {
            System.out.println("\t" + TextField1.getText());
        }

        System.out.println("Will attempt put the user in the DB...");
        String query = "INSERT INTO Users(FirstName, LastName, EMail,username, Password, BankCard, isLogin, isAdmin)\n\t\t "
                + "VALUES('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getEMail() + "', '" + user.getUserName() + "', '" + user.getPassword() + "', '" + user.getBankCard() + "', false, false)";
        System.out.println((ExeDML(query) == 0) ? "The user was inserted into the Database" : "Fail to insert user into the Database");
        showMessageDialog(mainFrame, "Your User Account has been Created.", "info.", INFORMATION_MESSAGE);
        getMainDisplay();
    }
}
