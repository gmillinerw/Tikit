package CodeForServer;

import javax.swing.*;
import java.util.ArrayList;

public abstract class absCaches extends absDAL {

    Configuration cacheConfiguration() {
        Configuration Configuration = new Configuration();
        Configuration.setNumMovies(intQuerry("SELECT setting FROM Configuration where property ='numMovies'"));
        Configuration.setNumSeats(intQuerry("SELECT setting FROM Configuration where property ='numSeats'"));
        Configuration.setNumTimes(intQuerry("SELECT setting FROM Configuration where property ='numTimes'"));
        Configuration.setPrice(intQuerry("SELECT setting FROM Configuration where property ='price'"));
        return Configuration;
    }

    User cacheUser(int userID) {
        User user = new User();
        user.setUserID(userID);
        user.setFirstName(stringQuerry("SELECT FirstName FROM USERS where userID = " + userID));
        user.setLastName(stringQuerry("SELECT LastName FROM  USERS where userID = " + userID));
        String EMail = stringQuerry("SELECT EMail FROM USERS where userID = " + userID);
        user.setUserName(stringQuerry("SELECT UserName FROM USERS where userID = " + userID), EMail);
        user.setPassword(stringQuerry("SELECT Password FROM USERS where userID = " + userID));
        user.setBankCard(stringQuerry("SELECT BankCard FROM USERS where userID = " + userID));
        user.setLogin(booleanQuerry("SELECT isLogin FROM USERS where userID = " + userID));
        user.setAdmin(booleanQuerry("SELECT isAdmin FROM USERS where userID = " + userID));

        return user;
    }

    Hall cacheHall(String movieSelected, int TimeSelected) {
        Hall hall = new Hall();

        hall.setMovieName(movieSelected);
        hall.setTimeNumber(TimeSelected);
        String query = "select MovieID from  Movies where MovieName = '"
                + hall.getMovieName() + "'";
        hall.setMovieID(intQuerry(query));
        return hall;
    }

    class Configuration {

        private int numMovies;
        private int numTimes;
        private int numSeats;
        private int price;

        public int getNumMovies() {
            return numMovies;
        }

        public void setNumMovies(int numMovies) {
            this.numMovies = numMovies;
        }

        public int getNumTimes() {
            return numTimes;
        }

        public void setNumTimes(int numTimes) {
            this.numTimes = numTimes;
        }

        public int getNumSeats() {
            return numSeats;
        }

        public void setNumSeats(int numSeats) {
            this.numSeats = numSeats;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
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

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String firstName) {
            this.FirstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String LastName) {
            this.LastName = LastName.substring(0, 1).toUpperCase() + LastName.substring(1);
        }

        public String getBankCard() {
            return BankCard;
        }

        public void setBankCard(String BankCard) {

            this.BankCard = BankCard;
        }

        public String getEMail() {
            return EMail;
        }

        public void setEMail(String EMail) {
            while (!(isEmailUnique(EMail) && isEmailSyntaxProper(EMail))) {
                System.out.println("The logic for setEMail is: True");
                EMail = JOptionPane.showInputDialog(null, "Ingrese correo del cliente:", "Agregar.", 3);
            }
            setUserName(EMail);
        }

        boolean isEmailUnique(String EMail) {

            ArrayList<String> querryResults;
            querryResults = ExeDDL("SELECT Email FROM USERS where Email='" + EMail + "'");
            boolean logicResult = querryResults.isEmpty();
            System.out.println("The logic result for isEmailUnique is: " + logicResult);
            if (logicResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The Email:\n\t\t " + EMail + "\n Already exist in our database.", "Error.", 0);
            }
            return false;
        }

        boolean isEmailSyntaxProper(String EMail) {
            boolean logicResult = (EMail.indexOf("@") <= EMail.length() - 3 && EMail.indexOf("@") >= 1);
            System.out.println("The logic result for isEmailSyntaxProper is: " + logicResult);
            if (logicResult) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "The format of the Email is invalit.", "Error.", 0);
            }
            return false;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setUserName(String userName, String EMail) {
            this.userName = userName;
            this.EMail = EMail;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public boolean isLogin() {
            return Login;
        }

        public void setLogin(boolean Login) {
            this.Login = Login;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }

    class Hall {

        private int movieID;
        private String movieName;
        private int TimeNumber;

        private int seatNum;

        public int getMovieID() {
            return movieID;
        }

        public void setMovieID(int movieID) {
            this.movieID = movieID;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public int getTimeNumber() {
            return TimeNumber;
        }

        public void setTimeNumber(int TimeNumber) {
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
