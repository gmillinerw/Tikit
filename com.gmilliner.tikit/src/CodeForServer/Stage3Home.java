package CodeForServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

class Stage3Home extends absGUI {

    private final Configuration configuration = cacheConfiguration();
    private User user;

    Stage3Home(User user) {
        this.user = user;
        System.out.println("The number of movies is: " + this.configuration.getNumMovies());
        System.out.println("The number of Times is: " + this.configuration.getNumTimes());
        System.out.println("The number of seats is: " + this.configuration.getNumSeats());

        System.out.println("The User Setting UserID is: " + this.user.getUserID());
        System.out.println("The User Setting FirstName is: " + this.user.getFirstName());
        System.out.println("The User Setting LastName is: " + this.user.getLastName());
        System.out.println("The User Setting BankCard is: " + this.user.getBankCard());
        System.out.println("The User Setting EMail is: " + this.user.getEMail());
        System.out.println("The User Setting UserName is: " + this.user.getUserName());
        System.out.println("The User Setting Password is: " + this.user.getPassword());
        System.out.println("The User Setting Login is: " + this.user.isLogin());
        System.out.println("The User Setting Admin is: " + this.user.isAdmin());
    }

    public void getMainDisplayDSP() {
        setGUI("JFrame Name");

        JButton chooseMovie = new JButton("Choose \n Movie");
        chooseMovie.setFont(new Font("Ariel", Font.BOLD, 20));
        chooseMovie.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            Stage4Movie Stage4Movie = new Stage4Movie(user, configuration);
            mainFrame.dispose();
            Stage4Movie.getMainDisplay();
        });
        JButton payTicket = new JButton("pay \n Ticket");
        payTicket.setFont(new Font("Ariel", Font.BOLD, 20));
        payTicket.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");

            showMessageDialog(mainFrame, "This module is still being develop.", "info.", INFORMATION_MESSAGE);
            mainFrame.dispose();
            getMainDisplayDSP();
        });

        JButton Logout = new JButton("Logout");
        Logout.setFont(new Font("Ariel", Font.BOLD, 20));
        Logout.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");

            Stage2login Screen1_login = new Stage2login();
            mainFrame.dispose();
            Screen1_login.getMainDisplay();
        });

        topPanel.setLayout(new GridLayout(5, 5, 5, 5));
        topPanel.add(chooseMovie);
        topPanel.add(payTicket);
        topPanel.add(Logout);

        basePanel.setOpaque(false);
        basePanel.setLayout(new BorderLayout(5, 5));
        basePanel.add(topPanel, BorderLayout.PAGE_START);
        basePanel.add(bottomPanel, BorderLayout.PAGE_END);

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/MovieTheaterLogo.png")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }
}
