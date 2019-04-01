package CodeForServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Stage4Movie extends absGUI {

    int userID;
    String movieSelected;
    int TimeSelected = 0;
    int errorCount;
    User user = new User();
    Hall hall = new Hall();
    Stage3Home Stage3Home;
    Configuration configuration;

    Stage4Movie(User user, Configuration configuration) {
        this.user = user;
        this.Stage3Home = new Stage3Home(user);
        this.configuration = configuration;

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

    void getMainDisplay() {
        setGUI("Chose Movie");

        String query;
        ArrayList<String> movieName;
        query = "SELECT movieName FROM Movies";
        movieName = ExeDDL(query);
        int numOfMuvies = movieName.size();

        ArrayList<String> iconImageLocation;
        query = "SELECT iconImageLocation FROM Movies";
        iconImageLocation = ExeDDL(query);
        ArrayList<ImageIcon> iconImage = new ArrayList<>();
        for (int index = 0; index < numOfMuvies; index++) {                     //This is not worling
            System.out.println("Creating ImageIcon using path: " + iconImageLocation.get(index));
            //iconImage.add(new ImageIcon(getClass().getResource(iconImageLocation.get(index))));        //This is not worling
        }

        ArrayList<String> TimeList = new ArrayList<>();
        TimeList.add("Sellect time");
        for (int index = 1; index < configuration.getNumTimes(); index++) {
            TimeList.add("Time de: " + index + "pm");
        }

        ImageIcon[] movieImage = { //To delete
                new ImageIcon(getClass().getResource("/Media/BABY DRIVER.png")),
                new ImageIcon(getClass().getResource("/Media/INTERSTELLAR.png")),
                new ImageIcon(getClass().getResource("/Media/LOGAN.png")),
                new ImageIcon(getClass().getResource("/Media/THE DARK KNIGHT.png")),
                new ImageIcon(getClass().getResource("/Media/THE GODFATHER.png")),
                new ImageIcon(getClass().getResource("/Media/WONDER WOMAN.png"))};

        ArrayList<JButton> movieButton = new ArrayList<>();
        for (int index = 0; index < numOfMuvies; index++) {
            //           System.out.println("The index is: " + index);
            movieButton.add(new JButton(movieName.get(index)));
            movieButton.get(index).setIcon(movieImage[index]);       //To delete
//            movieButton.get(index).setIcon(iconImage.get(index));
            movieButton.get(index).setFont(new Font("Ariel", Font.BOLD, 20));
            movieButton.get(index).setHorizontalTextPosition(AbstractButton.CENTER);
            movieButton.get(index).setVerticalTextPosition(AbstractButton.BOTTOM);
            movieButton.get(index).addActionListener((ActionEvent evt) -> {
                System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
                for (int i = 0; i < numOfMuvies; i++) {
                    if (evt.getSource().equals(movieButton.get(i))) {
                        movieSelected = evt.getActionCommand();
                        break;
                    }
                }
                System.out.println("Selected movie:" + movieSelected);
            });
            topPanel.add(movieButton.get(index));
        }

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            mainFrame.dispose();
            Stage3Home.getMainDisplayDSP();
        });

        JComboBox TimeComboBox = new JComboBox(TimeList.toArray());
        TimeComboBox.setFont(new Font("Ariel", Font.BOLD, 15));
        TimeComboBox.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            TimeSelected = TimeComboBox.getSelectedIndex();
            System.out.println("Selected time: " + TimeSelected + "(" + TimeList.get(TimeSelected) + ")");
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            if (movieSelected != null && TimeSelected != 0) {
                System.out.println("The next logic is: False");
                System.out.println("The next logic is: True");
                mainFrame.dispose();
                Stage5Seat Stage5Seat;
                Stage5Seat = new Stage5Seat(user, configuration);
                Stage5Seat.getMainDisplay(movieSelected, TimeSelected);
            } else {
                System.out.println("The next logic is: False");
            }
        });

        bottomPanel.add(backButton);
        bottomPanel.add(TimeComboBox);
        bottomPanel.add(nextButton);

        topPanel.setLayout(new GridLayout(2, 6, 10, 15));

        JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/Media/chooseMovie.jpg")));
        setGUI(imageLabel);
        mainFrame.setLocationRelativeTo(null);
    }

}
