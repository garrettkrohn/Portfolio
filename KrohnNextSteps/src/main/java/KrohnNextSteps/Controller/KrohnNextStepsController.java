/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KrohnNextSteps.Controller;

import KrohnNextSteps.Service.KrohnNextStepsService;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import kns.krohnnextsteps.view.KrohnNextStepsView;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author garrettkrohn
 */
public class KrohnNextStepsController {
    
    KrohnNextStepsService service = new KrohnNextStepsService();
    KrohnNextStepsView view = new KrohnNextStepsView();
    
    public void run() {
        JSONArray guests = service.readGuests();
        JSONArray companies = service.readCompanies();
        //read message templates

        //select guest
        view.displayGuests(guests);
        JSONObject selectedGuest = view.getGuest(guests); //tested, correct Guest
        JSONObject reservation = (JSONObject) selectedGuest.get("reservation");

        //select company
        view.displayCompanies(companies); 
        JSONObject selectedCompany = view.getCompany(companies); //tested, correct company
        
        //set up strings to be referenced in templates
        String salutation = service.checkGreetingTime();
        String firstName = selectedGuest.get("firstName").toString();
        String lastName = selectedGuest.get("lastName").toString();
        String roomNumber = reservation.get("roomNumber").toString();
        String company = selectedCompany.get("company").toString();
        String city = selectedCompany.get("city").toString();
        
        //set up values to switch, 6 available
        Map<String, String> values = new HashMap<String,String>();
        values.put("salutation", salutation);
        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("roomNumber", roomNumber);
        values.put("company", company);
        values.put("city", city);
        
        String template1 = "%(firstName) welcome to %(company).";
        String template2 = "%(roomNumber) is all ready for you, %(firstName).  "
                + "Let us know if %(company) can do anything else for you!";
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        String result = sub.replace(template1);
        String result2 = sub.replace(template2);
        System.out.println(result);
        System.out.println(result2);
        
        String inputedTemplate = view.readString("Input your own template, "
                + "you may use %(salutation), %(firstName), %(lastName),"
                + "%(roomNumber), %(company) or %(city)");
        String newTemplate = sub.replace(inputedTemplate);
        System.out.println(newTemplate);
        
        //display templates
        //ask user which one
        
        //generate final message
    }
}
