package CodeForServer;

import SQliteConnection.SQliteCRUD;

import javax.swing.*;
import java.util.ArrayList;

abstract class absCaches extends SQliteCRUD {

    Configuration cacheConfiguration() {
        Configuration Configuration = new Configuration();
        Configuration.setNumMovies(intQuery("SELECT setting FROM Configuration where property ='numMovies'"));
        Configuration.setNumSeats(intQuery("SELECT setting FROM Configuration where property ='numSeats'"));
        Configuration.setNumTimes(intQuery("SELECT setting FROM Configuration where property ='numTimes'"));
        Configuration.setPrice(intQuery("SELECT setting FROM Configuration where property ='price'"));
        return Configuration;
    }

    User cacheUser(int userID) {
        User user = new User();
        user.setUserID(userID);
        user.setFirstName(stringQuery("SELECT FirstName FROM USERS where userID = " + userID));
        user.setLastName(stringQuery("SELECT LastName FROM  USERS where userID = " + userID));
        String EMail = stringQuery("SELECT EMail FROM USERS where userID = " + userID);
        user.setUserName(stringQuery("SELECT UserName FROM USERS where userID = " + userID), EMail);
        user.setPassword(stringQuery("SELECT Password FROM USERS where userID = " + userID));
        user.setBankCard(stringQuery("SELECT BankCard FROM USERS where userID = " + userID));
        user.setLogin(booleanQuery("SELECT isLogin FROM USERS where userID = " + userID));
        user.setAdmin(booleanQuery("SELECT isAdmin FROM USERS where userID = " + userID));

        return user;
    }

    Hall cacheHall(String movieSelected, int TimeSelected) {
        Hall hall = new Hall();

        hall.setMovieName(movieSelected);
        hall.setTimeNumber(TimeSelected);
        String query = "select MovieID from  Movies where MovieName = '"
                + hall.getMovieName() + "'";
        hall.setMovieID(intQuery(query));
        return hall;
    }

    class Configuration {

        private int numMovies;
        private int numTimes;
        private int numSeats;
        private int price;

        int getNumMovies() {
            return numMovies;
        }

        void setNumMovies(int numMovies) {
            this.numMovies = numMovies;
        }

        int getNumTimes() {
            return numTimes;
        }

        void setNumTimes(int numTimes) {
            this.numTimes = numTimes;
        }

        int getNumSeats() {
            return numSeats;
        }

        void setNumSeats(int numSeats) {
            this.numSeats = numSeats;
        }

        int getPrice() {
            return price;
        }

        void setPrice(int price) {
            this.price = price;
        }

    }

    class User {

        private int userID;
        private String FirstName;
        private String LastName;
        private String EMail;
        private String userName;
        private String Password;
        private String BankCard;
        private boolean Login;
        private boolean admin;

        int getUserID() {
            return userID;
        }

        void setUserID(int userID) {
            this.userID = userID;
        }

        String getFirstName() {
            return FirstName;
        }

        void setFirstName(String firstName) {
            this.FirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        }

        String getLastName() {
            return LastName;
        }

        void setLastName(String LastName) {
            this.LastName = LastName.substring(0, 1).toUpperCase() + LastName.substring(1);
        }

        String getBankCard() {
            return BankCard;
        }

        void setBankCard(String BankCard) {

            this.BankCard = BankCard;
        }

        String getEMail() {
            return EMail;
        }

        void setEMail(String EMail) {
            while (!(isEmailUnique(EMail) && isEmailSyntaxProper(EMail))) {
                System.out.println("The logic for setEMail is: True");
                EMail = JOptionPane.showInputDialog(null, "Enter the name of the customer:", "Add.", JOptionPane.QUESTION_MESSAGE);
            }
            setUserName(EMail);
        }

        boolean isEmailUnique(String EMail) {

            ArrayList<String> QueryResults;
            QueryResults = ExeDDL("SELECT Email FROM USERS where Email='" + EMail + "'");
            boolean logicResult = QueryResults.isEmpty();
            System.out.println("The logic result for isEmailUnique is: " + logicResult);
            if (logicResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The Email:\n\t\t " + EMail + "\n Already exist in our database.", "Error.", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }

        boolean isEmailSyntaxProper(String EMail) {
            boolean logicResult = (EMail.indexOf("@") <= EMail.length() - 3 && EMail.indexOf("@") >= 1);
            System.out.println("The logic result for isEmailSyntaxProper is: " + logicResult);
            if (logicResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The format of the Email is invalid.", "Error.", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }

        String getUserName() {
            return userName;
        }

        void setUserName(String userName) {
            this.userName = userName;
        }

        void setUserName(String userName, String EMail) {
            this.userName = userName;
            this.EMail = EMail;
        }

        String getPassword() {
            return Password;
        }

        void setPassword(String Password) {
            this.Password = Password;
        }

        boolean isLogin() {
            return Login;
        }

        void setLogin(boolean Login) {
            this.Login = Login;
        }

        boolean isAdmin() {
            return admin;
        }

        void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }

    class Hall {

        private int movieID;
        private String movieName;
        private int TimeNumber;

        private int seatNum;

        int getMovieID() {
            return movieID;
        }

        void setMovieID(int movieID) {
            this.movieID = movieID;
        }

        String getMovieName() {
            return movieName;
        }

        void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        int getTimeNumber() {
            return TimeNumber;
        }

        void setTimeNumber(int TimeNumber) {
            this.TimeNumber = TimeNumber;
        }

        public int getSeatNum() {
            return seatNum;
        }

        public void setSeatNum(int seatNum) {
            this.seatNum = seatNum;
        }

    }
}
