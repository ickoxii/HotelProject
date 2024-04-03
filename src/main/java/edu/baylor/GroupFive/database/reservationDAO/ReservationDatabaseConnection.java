package edu.baylor.GroupFive.database.reservationDAO;

import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;

import edu.baylor.GroupFive.models.Reservation;

public class ReservationDatabaseConnection {



    public ReservationDatabaseConnection(){}
    //works well enough
    private Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:derby:FinalProject;", "", "");
            if(connection == null) {
                System.out.println("Could not connect");
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
        return connection;
    }

    //works well enough
    public Integer addReservation(Reservation reservation) throws SQLException {
        Connection connection = getConnection();
        if(connection == null){
            System.out.println("Connection Failed");
            return null;
        }
        Statement statement = null;
        Integer rowID = null;
        // startDate endDate price guestID roomID
        System.out.println("TEST1 - " + formatDate(reservation.getEndDate()));
        String sqlInsert = "INSERT INTO Reservations(STARTDATE,ENDDATE,PRICE,GUESTID,ROOMID) VALUES('" + formatDate(reservation.getStartDate()) + "','" +
                formatDate(reservation.getEndDate()) + "'," + reservation.getPrice() + "," + reservation.getGuestID() + "," + reservation.getRoomID() + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1); // Assuming the generated key is an integer
                System.out.println("Generated Key: " + generatedId);
                return  generatedId;
            } else {
                System.out.println("No generated keys were retrieved");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }


        return null;
    }


    //works well enough
    public List<Reservation> getReservations(){
        Connection connection =  getConnection();
        if(connection == null){
            System.out.println("Connection Failed");
            return null;
        }

        Statement statement = null;
        String sqlQuery = "SELECT * FROM Reservations";
        ResultSet rs = null;
        List<Reservation> output = new ArrayList<>();
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sqlQuery);
            while(rs.next()){
                Reservation out = new Reservation(rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("guestID"),
                        rs.getString("roomID"),
                        rs.getDouble("price"));
                output.add(out);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return output;
    }
    //works well enough
    public Boolean cancelReservation(Integer roomID, Date startDate) {
        Connection connection = getConnection();
        if(connection == null){
            System.out.println("Connection Failed");
            return null;
        }

        Statement statement = null;
        String sqlDelete = "DELETE FROM reservations WHERE roomID = " + roomID + " AND startDate = '" + formatDate(startDate) + "'";
        try {
            statement = connection.createStatement();
            statement.execute(sqlDelete);


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return true;


    }
    //works well enough
    public Reservation getInfo(Integer roomID, Date startDate) throws SQLException {
        Connection connection = getConnection();
        if(connection == null){
            System.out.println("Connection Failed");
            return null;
        }

        ResultSet rs;
        ResultSet id;
        Statement statement= null;

        String sqlQuery = "SELECT * FROM reservations WHERE roomID = " + roomID + " AND startDate = '" + formatDate(startDate) + "'";
        try {
            statement = connection.createStatement();

            rs = statement.executeQuery(sqlQuery);
            while(rs.next()){
                Reservation out = new Reservation(rs.getDate("startDate"),
                        rs.getDate("endDate"),
                        rs.getString("guestID"),
                        rs.getString("roomID"),
                        rs.getDouble("price"));

                return out;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }



        return null;

    }





    public Boolean checkIfAvailable(String roomID, Date startDate, Date endDate) {
        //'20150131'
        Connection connection = getConnection();
        if(connection == null){
            System.out.println("Connection Failed");
            return null;
        }

        ArrayList<ArrayList<Date>> mem;
        ResultSet rs = null;
        Statement statement = null;
        String sqlQuery = "SELECT * FROM reservations WHERE roomid=" + roomID;
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(sqlQuery);
            mem = new ArrayList<>();
            while(rs.next()){
                ArrayList<Date> temp = new ArrayList<>();
                temp.add(rs.getDate("startDate"));
                temp.add(rs.getDate("endDate"));
                mem.add(temp);
            }

        } catch (SQLException e) {
            System.out.println("RDC check if available failed");
            System.out.println(e.getMessage());
            return null;
        }finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }


        for(ArrayList<Date> r : mem){
            System.out.println(r.get(0) + " " + r.get(1) + " : " + startDate + " " + endDate);

            if((startDate.after(r.get(0)) || startDate.equals(r.get(0))) && startDate.before(r.get(1))){
                System.out.println("3");
                return false;
            }
            if(endDate.after(r.get(0)) && (endDate.equals(r.get(1)) || endDate.before(r.get(1)))){
                System.out.println("2");
                return false;
            }

            if((startDate.before(r.get(0)) || startDate.equals(r.get(0))) &&
                (endDate.equals(r.get(1)) || endDate.after(r.get(1)))){
                System.out.println("1");
                return false;
            }
        }
        return true;
    }


    private static String formatDate(Date myDate) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(myDate.getTime());
    }










}