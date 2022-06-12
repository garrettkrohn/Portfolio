/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package kns.krohnnextsteps;

import KrohnNextSteps.Controller.KrohnNextStepsController;
import KrohnNextSteps.Dao.KrohnNextStepsCompaniesDao;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 *
 * @author garrettkrohn
 */
public class App {

    public static void main(String[] args) {
        KrohnNextStepsController controller = new KrohnNextStepsController();
        controller.run();
        
//        KrohnNextStepsCompaniesDao compDao = new KrohnNextStepsCompaniesDao();
//        JSONArray test = compDao.readCompanies();  
//        JSONObject testObject = (JSONObject) test.get(0);
//        System.out.println(testObject.get("company"));
//        
    }
}
