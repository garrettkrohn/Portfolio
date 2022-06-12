/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KrohnNextSteps.DTO;

/**
 *
 * @author garrettkrohn
 */
public class Company {
    int id;
    String companyName;
    String city;
    String timeZone;

    public Company(int id, String companyName, String city, String timeZone) {
        this.id = id;
        this.companyName = companyName;
        this.city = city;
        this.timeZone = timeZone;
    }
    
    public Company(){
    }

    public int getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCity() {
        return city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    
    
}
