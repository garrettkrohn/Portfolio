/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KrohnNextSteps.Service;

import KrohnNextSteps.Dao.KrohnNextStepsCompaniesDao;
import KrohnNextSteps.Dao.KrohnNextStepsGuestDao;
import KrohnNextSteps.Dao.KrohnNextStepsTemplatesDao;
import java.util.Calendar;
import org.json.simple.JSONArray;

/**
 *
 * @author garrettkrohn
 */
public class KrohnNextStepsService {
    
    KrohnNextStepsCompaniesDao compDao = new KrohnNextStepsCompaniesDao();
    KrohnNextStepsGuestDao guestDao = new KrohnNextStepsGuestDao();
    KrohnNextStepsTemplatesDao templateDao = new KrohnNextStepsTemplatesDao();
    
    public JSONArray readCompanies() {
        return compDao.readCompanies();
    }
    
    public JSONArray readGuests() {
        return guestDao.readGuests();
    }
    
    public JSONArray readTemplates() {
        return templateDao.readTemplates();
    }
    
    public String checkGreetingTime() {
        Calendar cal = Calendar.getInstance();
        int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);
        
        if (timeOfDay >= 0 && timeOfDay <12) {
            return "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            return "Good Afternoon";
        } else {
            return "Good Evening";
        }
        
    }
    
}
