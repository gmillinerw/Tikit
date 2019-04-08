package CodeForServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

class Stage5Seat extends absGUI {

    int userID;
    String movieSelected;
    int TimeSelected;
    private final User user;
    private final Configuration configuration;
    private final Stage3Home Stage3Home;
    //    this.movieImage = new ImageIcon[3];
    private final ImageIcon[] movieImage = {
            new ImageIcon(getClass().getResource("/Media/FreeSeat.png")),
            new ImageIcon(getClass().getResource("/Media/OccupiedSeat.png")),
            new ImageIcon(getClass().getResource("/Media/selectedSeat.png"))
    };
    private final int FreeSeat = 0;
    private final int OccupiedSeat = 1;
    private final int selectedSeat = 2;
    //        ArrayList<JButton> seatButton = new ArrayList<>();
//        ArrayList<ImageIcon> section = new ArrayList<>();
//        section.add(0, new ImageIcon("/Media/FreeSeat.png"));
//        section.add(1, new ImageIcon("/Media/OccupiedSeat.png"));
//        section.add(2, new ImageIcon("/Media/selectedSeat.png"));
    private final JLabel listSeats = new JLabel("No seats has been selected");
    private final JLabel totalCost = new JLabel("The total cost is: 0");
    private int errorCount;
    private Hall hall;
    private JButton[] seatButton;
    Stage5Seat(User user, Configuration configuration) {
        this.user = user;
        this.Stage3Home = new Stage3Home(user);
        this.configuration = configuration;

        System.out.println("\n");
        System.out.println("The number of movies is: " + this.configuration.getNumMovies());
        System.out.println("The number of Times is: " + this.configuration.getNumTimes());
        System.out.println("The number of seats is: " + this.configuration.getNumSeats());
        System.out.println("\n");
        System.out.println("The User Setting UserID is: " + this.user.getUserID());
        System.out.println("The User Setting FirstName is: " + this.user.getFirstName());
        System.out.println("The User Setting LastName is: " + this.user.getLastName());
        System.out.println("The User Setting BankCard is: " + this.user.getBankCard());
        System.out.println("The User Setting EMail is: " + this.user.getEMail());
        System.out.println("The User Setting UserName is: " + this.user.getUserName());
        System.out.println("The User Setting Password is: " + this.user.getPassword());
        System.out.println("The User Setting Login is: " + this.user.isLogin());
        System.out.println("The User Setting Admin is: " + this.user.isAdmin());
        System.out.println("\n");
    }

    void getMainDisplay(String movieSelected, int TimeSelected) {
        System.out.println("METHOD INFO: " + Arrays.toString(Thread.currentThread().getStackTrace()));
        setGUI("Chose Seats");
        hall = cacheHall(movieSelected, TimeSelected);
        this.seatButton = new JButton[configuration.getNumSeats()];

        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(7, 7, 5, 5));
        topLeftPanel.setOpaque(false);

        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new GridLayout(7, 7, 5, 5));
        topRightPanel.setOpaque(false);

        for (int i = 0; i < configuration.getNumSeats(); i++) {
            seatButton[i] = new JButton(Integer.toString((i + 1)));
            seatButton[i].setFont(new Font("Ariel", Font.BOLD, 15));
            seatButton[i].setHorizontalTextPosition(AbstractButton.CENTER);
            seatButton[i].setVerticalTextPosition(AbstractButton.BOTTOM);
            seatButton[i].setIcon(movieImage[FreeSeat]);
            if (i < configuration.getNumSeats() / 2) {
                topLeftPanel.add(seatButton[i]);
            } else {
                topRightPanel.add(seatButton[i]);
            }
            seatButton[i].addActionListener((ActionEvent evt) -> {
                System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
                seatButtonEvt(evt);
            });
        }
        topPanel.setLayout(new GridLayout(1, 2, 30, 15));
        topPanel.setOpaque(false);
        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);

        JButton selectButton = new JButton("select");
        selectButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            Stage3Home.getMainDisplayDSP();
            mainFrame.dispose();
        });

        JButton BackButton = new JButton("Back");
        BackButton.addActionListener((ActionEvent evt) -> {
            System.out.println("EVT INFO: " + evt.getActionCommand() + " button was press");
            Stage4Movie Stage4Movie = new Stage4Movie(user, configuration);
            Stage4Movie.getMainDisplay();
            mainFrame.dispose();
        });

        listSeats.setForeground(Color.WHITE);
        listSeats.setFont(new Font("Ariel", Font.BOLD, 20));
        totalCost.setForeground(Color.WHITE);
        totalCost.setFont(new Font("Ariel", Font.BOLD, 20));

        JPanel bottomRightPanel = new JPanel();
        bottomRightPanel.setLayout(new GridLayout(2, 1, 20, 5));
        bottomRightPanel.setOpaque(false);
        bottomRightPanel.add(listSeats);
        bottomRightPanel.add(totalCost);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setLayout(new GridLayout(1, 2, 20, 5));
        bottomLeftPanel.setOpaque(false);
        bottomLeftPanel.add(BackButton);
        bottomLeftPanel.add(selectButton);

        bottomPanel.add(bottomRightPanel);
        bottomPanel.add(bottomLeftPanel);
        basePanel.setLayout(new GridLayout(2, 5, 40, 5));
        basePanel.add(topPanel);
        basePanel.add(bottomPanel);
        setGUI(new JLabel(new ImageIcon(getClass().getResource("/Media/Seats.jpg"))));
        mainFrame.setLocationRelativeTo(null);

        setSeatIcons();
    }

    private void setSeatIcons() {
        String query;
        query = "SELECT SeatNum FROM SEATS where movieID = " + hall.getMovieID()
                + " and Time = " + hall.getTimeNumber() + " and isBooked = True";
        ArrayList<Integer> OccupiedSeatList = getIntListQuery(query);
        for (Integer integer : OccupiedSeatList) {
            seatButton[integer - 1].setIcon(movieImage[OccupiedSeat]);
        }

        query = "SELECT seatNum FROM RESERVATIONS where movieID = " + hall.getMovieID()
                + " and Time = " + hall.getTimeNumber() + " and BookedBy = '" + user.getUserName() + "'";
        ArrayList<Integer> selectedSeatList = getIntListQuery(query);
        for (Integer integer : selectedSeatList) {
            seatButton[integer - 1].setIcon(movieImage[selectedSeat]);
        }

        ArrayList<Integer> RemovedSeatList = new ArrayList<>(selectedSeatList);
        RemovedSeatList.removeAll(OccupiedSeatList);
        RemovedSeatList.forEach(System.out::println);
    }

    private void seatButtonEvt(ActionEvent evt) {
        System.out.println("METHOD INFO: " + Arrays.toString(Thread.currentThread().getStackTrace()));
        String query;
        int DSPSeatNumber = Integer.parseInt(evt.getActionCommand());
        int ArraySeatNumber = DSPSeatNumber - 1;
        query = "SELECT isBooked FROM SEATS where movieID = " + hall.getMovieID()
                + " and Time = " + hall.getTimeNumber() + " and seatNum = " + DSPSeatNumber;

        if (!booleanQuery(query)) {
            query = "SELECT ReservationID FROM RESERVATIONS where movieID = " + hall.getMovieID() + " and Time = "
                    + hall.getTimeNumber() + " and seatNum = " + DSPSeatNumber + " and BookedBy = '" + user.getUserName() + "'";
            if (intQuery(query) > 0) {

                ExeDML("DELETE FROM RESERVATIONS WHERE movieID=" + hall.getMovieID() + " and Time=" + hall.getTimeNumber()
                        + " and seatNum=" + DSPSeatNumber + " and BookedBy='" + user.getUserName() + "'");
                seatButton[ArraySeatNumber].setIcon(movieImage[FreeSeat]);
                System.out.println("Unselected Seat #: " + DSPSeatNumber);
            } else {
                ExeDML("INSERT INTO RESERVATIONS (SeatID,movieID, Time, seatNum, BookedBy) VALUES (" + 99 + "," + hall.getMovieID() + ","
                        + hall.getTimeNumber() + "," + DSPSeatNumber + ",'" + user.getUserName() + "')");
                System.out.println("Selected Seat #: " + DSPSeatNumber);
                seatButton[ArraySeatNumber].setIcon(movieImage[selectedSeat]);
            }
            tallySeats(evt);
        } else {
            errorCount++;
            seatButton[ArraySeatNumber].setIcon(movieImage[OccupiedSeat]);
            System.out.println("Seat: " + DSPSeatNumber + " ...  " + errorCount
                    + " times user tried to select a seat that was occupied");
        }
    }

    private void tallySeats(ActionEvent evt) {
        System.out.println("METHOD INFO: " + Arrays.toString(Thread.currentThread().getStackTrace()));

        String query = "SELECT seatNum FROM RESERVATIONS where movieID = " + hall.getMovieID() + " and Time = "
                + hall.getTimeNumber() + " and BookedBy = '" + user.getUserName() + "'";
        ArrayList<String> takenSeats = ExeDDL(query);

        StringBuilder takenSeatsSB = new StringBuilder();
        for (String Seat : takenSeats) {
            takenSeatsSB.append(Seat);
            takenSeatsSB.append(", ");
        }
        String message = "Seats selected: " + takenSeatsSB.toString();
        listSeats.setText(message);
        System.out.println(message);

        message = "Total cost: " + takenSeats.size() * configuration.getPrice();
        totalCost.setText(message);
        System.out.println(message);
    }
}
