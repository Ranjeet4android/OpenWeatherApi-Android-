package com.ranjeet.weatherapp.model;
/**
 * Created by Ranjeet Kushwaha on 07.01.2017
 */
public class Place {
    // Define attributes of a user
    public String name;
    public String author;
    public Integer votes;
    public Integer played;
    public Long timerequest;
    // Create a constructor
    public Place(String name, String author, Integer votes, Integer played, Long timerequest) {
        this.name = name;
        this.author = author;
        this.votes = votes;
        this.played = played;
        this.timerequest = timerequest;
    }
}

