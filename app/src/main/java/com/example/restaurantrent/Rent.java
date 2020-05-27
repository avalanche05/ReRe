package com.example.restaurantrent;





public class Rent {

    private Long id;

    private Long idUser;
    private String idTables;
    private String date;
    private Long idOwner;
    private String time;
    private Long idRestaurant;

    public Rent() {
    }

    public Rent(Long idUser, String idTables, String date, Long idOwner, String time) {
        this.idUser = idUser;
        this.idTables = idTables;
        this.date = date;
        this.idOwner = idOwner;
        this.time = time;
    }

    public Rent(Long id, Long idUser, String idTables, String date, Long idOwner, String time, Long idRestaurant) {
        this.id = id;
        this.idUser = idUser;
        this.idTables = idTables;
        this.date = date;
        this.idOwner = idOwner;
        this.time = time;
        this.idRestaurant = idRestaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }


    public void setIdTables(String idTables) {
        this.idTables = idTables;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdTables() {
        return idTables;
    }

    public Long getIdOwner() {
        return idOwner;
    }

    public void setIdOwner(Long idOwner) {
        this.idOwner = idOwner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }
}
