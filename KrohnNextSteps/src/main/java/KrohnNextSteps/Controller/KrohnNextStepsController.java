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
        //read guests, companies, and templates
        JSONArray guests = service.readGuests();
        JSONArray companies = service.readCompanies();
        JSONArray templates = service.readTemplates();

        //select guest
        view.guestBanner();
        view.displayGuests(guests);
        JSONObject selectedGuest = view.getGuest(guests); //tested, correct Guest
        JSONObject reservation = (JSONObject) selectedGuest.get("reservation");

        //select company
        view.companyBanner();
        view.displayCompanies(companies); 
        JSONObject selectedCompany = view.getCompany(companies); //tested, correct company
        
        //select template or create your own
        view.templateBanner();
        view.displayTemplates(templates);
        String selectedTemplate = view.getTemplate(templates);
        
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
        
        //print out final message
        StrSubstitutor sub = new StrSubstitutor(values, "%(", ")");
        String result = sub.replace(selectedTemplate);
        System.out.println(result);
        
    }
}
