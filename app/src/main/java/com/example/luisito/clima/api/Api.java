
package com.example.luisito.clima.api;


public class Api {

    private String cod;
    private Double message;
    private Integer cnt;
    private java.util.List<com.example.luisito.clima.api.List> list = null;
    private City city;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getMessage() {
        return message;
    }

    public void setMessage(Double message) {
        this.message = message;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public java.util.List<com.example.luisito.clima.api.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.example.luisito.clima.api.List> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
