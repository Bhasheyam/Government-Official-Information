package com.example.bhash.govermentinfo;

import java.io.Serializable;

/**
 * Created by bhash on 04-08-2017.
 */

public class OfficialData implements Serializable {

    private String Position;
    private String Name;
    private int Index;
    private String Address;
    private String Party;
    private String Phone;
    private String Site;
    private String Email;
    private String Photo;
    private String Gplus;
    private String Fb;
    private String Twitter;
    private String YouTube;
    public int getIndex() {
        return Index;
    }
    public void setIndex(int index) {
        Index = index;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }


    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getParty() {
        return Party;
    }

    public void setParty(String party) {
        Party = party;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getGplus() {
        return Gplus;
    }

    public void setGplus(String gplus) {
        Gplus = gplus;
    }

    public String getFb() {
        return Fb;
    }

    public void setFb(String fb) {
        Fb = fb;
    }

    public String getTwitter() {
        return Twitter;
    }

    public void setTwitter(String twitter) {
        Twitter = twitter;
    }

    public String getYouTube() {
        return YouTube;
    }

    public void setYouTube(String youTube) {
        YouTube = youTube;
    }



}
