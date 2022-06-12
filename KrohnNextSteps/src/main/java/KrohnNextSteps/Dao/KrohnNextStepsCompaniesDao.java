/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KrohnNextSteps.Dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author garrettkrohn
 */
public class KrohnNextStepsCompaniesDao {
    
    
    
    public JSONArray readCompanies() {
        JSONParser jsonParser = new JSONParser();
        JSONArray result = new JSONArray();
        
        try (FileReader reader = new FileReader("Companies.json"))
        { 
            //read JSON file
            Object obj = jsonParser.parse(reader);
            
            JSONArray companyList = (JSONArray) obj;
            
            return companyList;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    
//    private static void parseCompanyObject(Object company) {
//        
//        JSONObject companyObject = (JSONObject) company;
//        
//        Long companyId = (Long) companyObject.get("id");
//        System.out.println("id: " + companyId);
//        
//        String companyName = (String) companyObject.get("company");
//        System.out.println("Name: " + companyName);
//
//        String city = (String) companyObject.get("city");
//        System.out.println("City: " + city);
//        
//        String timezone = (String) companyObject.get("timezone");
//        System.out.println("Timezone: " + timezone);
//        
//        
//
//    }

}
