package com.example.user.graduationproject.Bjelasnica.Utils;

public class SkiResort {

    private final Mountain mountain;
    private final String city;
    private final String liveStream;
    private final String ticketPrice;
    private final String gallery;
    private final String trailMap;

    public String getTrailMap() {
        return trailMap;
    }

    public String getGallery() {
        return gallery;
    }

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

    public SkiResort(Mountain mountain, String city, String liveStream, String ticketPrice, String gallery, String trailMap) {
        this.mountain = mountain;
        this.city = city;
        this.liveStream = liveStream;
        this.ticketPrice = ticketPrice;
        this.gallery = gallery;
        this.trailMap = trailMap;
    }
}
