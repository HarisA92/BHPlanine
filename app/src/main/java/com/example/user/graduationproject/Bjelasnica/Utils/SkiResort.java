package com.example.user.graduationproject.Bjelasnica.Utils;

public class SkiResort {

    private final Mountain mountain;
    private final String city;
    private final LiveStream liveStream;

    public Mountain getMountain() {
        return mountain;
    }

    public String getCity() {
        return city;
    }

    public LiveStream getLiveStream(){
        return liveStream;
    }

    public SkiResort(Mountain mountain, String city, LiveStream liveStream) {
        this.mountain = mountain;
        this.city = city;
        this.liveStream = liveStream;
    }
}
