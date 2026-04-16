package com.newlifetechnologies.nltracingoverlay.dto;

public class ProfileDTO {

    private String name;
    private String nick;
    private String steamID;

    public ProfileDTO() {
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public String getSteamID() {
        return steamID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setSteamID(String steamID) {
        this.steamID = steamID;
    }
}