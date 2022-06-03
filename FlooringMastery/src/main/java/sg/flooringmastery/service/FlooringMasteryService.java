/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sg.flooringmastery.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.dto.Taxes;

/**
 *
 * @author garrettkrohn
 */
public interface FlooringMasteryService {
    
    public Boolean tryDateConvert(String inputDate);

    public LocalDate convertStringToDate(String stringDate);
    
    public String convertDateToString(LocalDate date);
    
    public boolean orderDateFutureCheck(LocalDate orderDate);
    
    public boolean validateName(String inputName);
    
    public List<String> getAllStates() throws FlooringMasteryPersistenceException;
    
    public List<Taxes> getAllStatesObjects() throws FlooringMasteryPersistenceException;
    
    public boolean validateStateWithStringList(String stateName, List<String> availableStates);
    
    public boolean validateStateWithTaxesList(String state, List<Taxes> allStates);
    
    public Taxes getTaxesObjectFromListWithString(String stateName, List<Taxes> stateTaxesList);
    
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
    
    public boolean validateProduct(String inputProduct, List<Product> productList);
    
    public Product getProductObjectFromListWithString(String productType, List<Product> productList);
    
    public boolean validateArea(String inputArea);
    
    public boolean validateAreaMinimum(BigDecimal inputArea);
    
     public Order createOrderObject(LocalDate date, String name, Taxes state, 
            Product product, BigDecimal area) throws FlooringMasteryPersistenceException;
    
    public BigDecimal convertStringToBigDecimal(String inputArea);
    
    public void addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException;
    
    public void removeOrder(Order newOrder, LocalDate date) throws FlooringMasteryPersistenceException;
    
    public int calcNextID(List<Order> orderList);
    
    public boolean checkIfOrderDateExists(String date) throws FlooringMasteryPersistenceException;
    
    public boolean checkIfOrderExists(String date, String name) throws FlooringMasteryPersistenceException;
    
    public List<Order> getOrder(String date, String name) throws FlooringMasteryPersistenceException;
    
    public List<Order> getAllOrdersOnDate(String date) throws FlooringMasteryPersistenceException;
    
    public int getOrderIndexFromList(List<Order> orders, Order order);
    
    public void addEditedOrder(Order newOrder, LocalDate date) throws FlooringMasteryPersistenceException;
    
    public boolean validateOrderEdit(int selection, List<Order> orderList);
    
    public Order returnOrderWithIDFromList(int selection, List<Order> orderList);
//    
//    public void addOrder() throws FlooringMasteryPersistenceException;
//    
//    public void displayOrders() throws FlooringMasteryPersistenceException;
//    
//    public void editOrder() throws FlooringMasteryPersistenceException;
//    
//    public void deleteOrder() throws FlooringMasteryPersistenceException;

    
}
