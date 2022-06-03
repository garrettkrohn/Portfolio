/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryOrderDaoFileImpl implements FlooringMasteryOrderDao {
    
    private List<Order> allOrdersOnDate = new ArrayList<Order>();
    public static final String DELIMITER = ",";
    private String fileName = "";

    public FlooringMasteryOrderDaoFileImpl() {
    }    
    
    public Order unmarshallOrder(String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);
        int orderID = Integer.valueOf(orderTokens[0]);
        String customerName = orderTokens[1];
        String state = orderTokens[2];
        BigDecimal taxRate = new BigDecimal(orderTokens[3]);
        String productType = orderTokens[4];
        BigDecimal area = new BigDecimal(orderTokens[5]);
        BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[6]);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(orderTokens[7]);
        BigDecimal materialCost = new BigDecimal(orderTokens[8]);
        BigDecimal laborCost = new BigDecimal(orderTokens[9]);
        BigDecimal taxes = new BigDecimal(orderTokens[10]);
        BigDecimal total = new BigDecimal(orderTokens[11]);
        Order result = new Order(
            orderID, customerName, state, taxRate, productType,
            area, costPerSquareFoot, laborCostPerSquareFoot,
            materialCost, laborCost, taxes, total);
        return result;
    }
    
    public String marshallOrder(Order order) {
        String result = order.getOrderID() + DELIMITER;
        result += order.getCustomerName() + DELIMITER;
        result += order.getState() + DELIMITER;
        result += order.getTaxRate() + DELIMITER;
        result += order.getProductType() + DELIMITER;
        result += order.getArea() + DELIMITER;
        result += order.getCostPerSquareFoot() + DELIMITER;
        result += order.getLaborCostPerSquareFoot() + DELIMITER;
        result += order.getMaterialCost() + DELIMITER;
        result += order.getLaborCost() + DELIMITER;
        result += order.getTaxes() + DELIMITER;
        result += order.getTotal();
        return result;
    }
    
    public boolean checkIfOrderDateExists(String date) throws FlooringMasteryPersistenceException {
        fileName = "Orders_" + date + ".txt"; 
        
        //scans the correct fileName
        Scanner scanner;
        try {
            scanner = new Scanner(
                new BufferedReader(
                    new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            return false;
        }
        return true;
    }
    
    public List<Order> loadOrders(String date) throws FlooringMasteryPersistenceException {
        fileName = "Orders_" + date + ".txt"; 
        
        allOrdersOnDate.clear();
        
        //scans the correct fileName
        Scanner scanner;
        try {
            scanner = new Scanner(
                new BufferedReader(
                    new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
            "No orders for that date.");
        }
        
        //all orders are scanned and added to List
        String currentLine;
        Order currentOrderObject = null;
        String throwAway = scanner.nextLine();
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentOrderObject = unmarshallOrder(currentLine);
            allOrdersOnDate.add(currentOrderObject);
        }
        return allOrdersOnDate;
    }
    
    
    public void addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException {
        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        
        fileName = "Orders_" + dateConverted + ".txt";
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
                    + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
                    + "MaterialCost,LaborCost,Tax,Total";
        
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(fileName, true));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                "Could not save Order data", e);
        }
        //check if the file is blank, if so, add a header
        Scanner scanner;
        try {
        scanner = new Scanner(
                new BufferedReader(
                        new FileReader(fileName)));
            } catch (FileNotFoundException e) {
                throw new FlooringMasteryPersistenceException(
                        "-_- Could not load roster data into memory.", e);
            }
        
        try {
            if (scanner.nextLine() == "" || scanner.nextLine() == null) {
            out.println(header);
            out.flush();}
        } catch (NoSuchElementException e) {
            out.println(header);
            out.flush();
        }
        
        String orderAsText = marshallOrder(order);
        out.println(orderAsText);
        out.flush();
        scanner.close();
    }
    
     public void writeOrders(LocalDate date, List<Order> orders) 
             throws FlooringMasteryPersistenceException {
        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        
        fileName = "Orders_" + dateConverted + ".txt";
        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
                    + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
                    + "MaterialCost,LaborCost,Tax,Total";
        
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter(fileName));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException(
                "Could not save Order data", e);
        }
        
        //print header
        out.println(header);
        out.flush();
        //print orders
         for (int i = 0; i < orders.size(); i++) {
            String orderAsText = marshallOrder(orders.get(i));
            out.println(orderAsText);
            out.flush();
         }
     }
       
        
    
     
    public boolean checkIfOrderExists(String date, String name) throws FlooringMasteryPersistenceException {
        loadOrders(date);
        for (int i = 0; i < allOrdersOnDate.size(); i++) {
            if (allOrdersOnDate.get(i).getCustomerName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    
    public List<Order> getOrder(String date, String name) throws FlooringMasteryPersistenceException {
        loadOrders(date);
        List<Order> orderMatches = new ArrayList<Order>();
        for (int i = 0; i < allOrdersOnDate.size(); i++) {
            if (allOrdersOnDate.get(i).getCustomerName().equals(name)) {
                orderMatches.add(allOrdersOnDate.get(i));
            }
        }
        return orderMatches;
    }
    
    public List<Order> getAllOrdersOnDate(String date) throws FlooringMasteryPersistenceException {
        allOrdersOnDate = loadOrders(date);
        return allOrdersOnDate;
    }
    
}
