/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package sg.flooringmastery;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sg.flooringmastery.controller.FlooringMasteryController;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public class App {

    public static void main(String[] args)throws FlooringMasteryPersistenceException {

        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller =
                ctx.getBean("controller", FlooringMasteryController.class);
        
        controller.run();
        
    }
}
