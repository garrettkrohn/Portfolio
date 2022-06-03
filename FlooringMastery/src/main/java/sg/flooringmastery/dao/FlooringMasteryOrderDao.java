/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sg.flooringmastery.dao;

import java.time.LocalDate;
import java.util.List;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public interface FlooringMasteryOrderDao {
    
    public Order unmarshallOrder(String orderAsText);
    
    public String marshallOrder(Order order);
    
    public boolean checkIfOrderDateExists(String date) throws FlooringMasteryPersistenceException;
            
    public List<Order> loadOrders(String date) throws FlooringMasteryPersistenceException;
       
    public void addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;
    
    public void writeOrders(LocalDate date, List<Order> orders) 
             throws FlooringMasteryPersistenceException;
        
    public boolean checkIfOrderExists(String date, String name) throws FlooringMasteryPersistenceException;
    
    public List<Order> getOrder(String date, String name) throws FlooringMasteryPersistenceException;
    
    public List<Order> getAllOrdersOnDate(String date) throws FlooringMasteryPersistenceException;
}
