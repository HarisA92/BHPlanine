package com.example.user.graduationproject.Bjelasnica.Utils;

public class SkiResort {

    private final Mountain mountain;
    private final String city;
    private final String liveStream;
    private final String ticketPrice;

    public Mountain getMountain() {
        return mountain;
    }

    public String getCity() {
        return city;
    }

    public String getLiveStream(){
        return liveStream;
    }

    public String getTicketPriceList(){
        return ticketPrice;
    }

    public SkiResort(Mountain mountain, String city, String liveStream, String ticketPrice) {
        this.mountain = mountain;
        this.city = city;
        this.liveStream = liveStream;
        this.ticketPrice = ticketPrice;
    }
}
