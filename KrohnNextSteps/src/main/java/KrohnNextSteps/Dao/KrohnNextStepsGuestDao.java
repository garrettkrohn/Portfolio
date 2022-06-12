/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package KrohnNextSteps.Dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author garrettkrohn
 */
public class KrohnNextStepsGuestDao {
    
    public JSONArray readGuests() {
        JSONParser jsonParser = new JSONParser();
        JSONArray result = new JSONArray();
        
        try (FileReader reader = new FileReader("Guests.json"))
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
}
