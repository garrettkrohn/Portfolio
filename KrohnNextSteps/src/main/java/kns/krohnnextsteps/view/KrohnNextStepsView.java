/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kns.krohnnextsteps.view;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sg.flooringmastery.UserIO.UserIO;
import sg.flooringmastery.UserIO.UserIOConsoleImpl;

/**
 *
 * @author garrettkrohn
 */
public class KrohnNextStepsView {
    
    UserIO io = new UserIOConsoleImpl();
    
    public void displayCompanies(JSONArray companies) {
        for (int i = 0; i < companies.size(); i++) {
            JSONObject currentCompany = (JSONObject) companies.get(i);
            System.out.println((i+1) + ": " + currentCompany.get("company"));
        }
    }
    
    public JSONObject getCompany(JSONArray companies) {
        int userSelection = (io.readInt("Please select Company", 1, companies.size())) - 1;
        JSONObject selectedCompany = (JSONObject) companies.get(userSelection);
        return selectedCompany;
    }
    
    public void displayGuests(JSONArray guests) {
        for (int i = 0; i < guests.size(); i++) {
            JSONObject currentGuest = (JSONObject) guests.get(i);
            System.out.println((i+1) + ": " + currentGuest.get("firstName") + " " + 
                    currentGuest.get("lastName"));
        }
    }
    
    public JSONObject getGuest(JSONArray guests) {
        int userSelection = (io.readInt("Please select Guest", 1, guests.size())) - 1;
        JSONObject selectedCompany = (JSONObject) guests.get(userSelection);
        return selectedCompany;
    }
    
    public String readString() {
        return io.readString("Please enter template");
    }
    
    public String readString(String prompt) {
        return io.readString(prompt);
    }
    
}
