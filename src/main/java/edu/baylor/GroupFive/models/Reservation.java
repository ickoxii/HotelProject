package edu.baylor.GroupFive.models;

import java.util.Date;

public class Reservation {

    Date startDate;
    Date endDate;
    String guestID;
    String roomID;
    Double price;

    public Reservation(Date startDate, Date endDate, String guestID, String roomID, Double price){
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestID = guestID;
        this.roomID = roomID;
        this.price = price;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public String getGuestID(){
        return guestID;
    }

    public String getRoomID(){
        return roomID;
    }



    public Double getPrice(){
        return price;
    }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public void setGuestID(String guestID){
        this.guestID = guestID;
    }

    public void setRoomID(String roomID){
        this.roomID = roomID;
    }



    public void setPrice(Double price){
        this.price = price;
    }

    public String toString(){
        String out = "Start Date: " + startDate + "\nEnd Date: " + endDate + "\nGuest ID: " + guestID + "\nRoom ID: " + roomID + "\nPrice: " + price;
        return out;
    }

}