/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sg.flooringmastery.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.dto.Taxes;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public interface FlooringMasteryDao {
    
    public void addOrder();
    
    public void removeOrder();
    
    public void getAllOrders();
    
    public List<Taxes> listAllTaxes() throws FlooringMasteryPersistenceException;
    
    public List<Product> listAllProducts() throws FlooringMasteryPersistenceException;
    
    public void writeOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;
    
    public void displayAllOrdersOnDate(LocalDate date) throws FlooringMasteryPersistenceException;
    
    public Boolean checkIfFileExists(LocalDate date);
    
    public Boolean checkIfOrderExistsInFile(LocalDate date, String name) throws FlooringMasteryPersistenceException;
    
    public void editOrder(LocalDate date, String name) throws FlooringMasteryPersistenceException;
    
    public Order searchName(List<Order> order, String name);
    
    public void writeMultipleOrders(List<Order> order, LocalDate date) throws FlooringMasteryPersistenceException;
    
    public void deleteOrder(String name, LocalDate date) throws FlooringMasteryPersistenceException;
}
