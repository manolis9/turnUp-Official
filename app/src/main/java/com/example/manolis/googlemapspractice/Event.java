package com.example.manolis.googlemapspractice;

import com.google.android.gms.games.Player;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Manolis on 2016-07-16.
 */

public class Event {

        private String eventTitle;

        private Player createdBy;

        private boolean isActive;

        private String numberOfPlayers;
        private String sport;
        private String date;
        private String time;
        private  String description;
        private String address;
        private LatLng location;

        public Event() {

        }

        public String getDescription() {return description;}

        public void setDescription(String description) {this.description = description;}

        public String getAddress() {return address;}

        public void setAddress(String address) {this.address = address;}

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNumberOfPlayers() {
            return numberOfPlayers;
        }

        public void setNumberOfPlayers(String numberOfPlayers) {this.numberOfPlayers = numberOfPlayers;}

        public String getTitle() {
            return eventTitle;
        }

        public void setTitle(String eventTitle) {
            this.eventTitle = eventTitle;
        }

        public String getSport() {
            return sport;
        }

        public void setSport(String sport) {
            this.sport = sport;
        }

        public LatLng getLocation() {
        return location;
    }

        public void setLocation(LatLng location) {
        this.location = location;
    }

        public void unsetLocation() {
        this.location = null;
    }

    }

