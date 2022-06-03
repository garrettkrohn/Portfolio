/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.service;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryPersistenceException extends Exception {
    
    public FlooringMasteryPersistenceException(String message) {
        super(message);
    }
    
    public FlooringMasteryPersistenceException(String message, 
            Throwable cause) {
        super(message, cause);
    }
}
