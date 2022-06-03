/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import sg.flooringmastery.dao.FlooringMasteryOrderDao;
import sg.flooringmastery.dao.FlooringMasteryOrderDaoFileImpl;
import sg.flooringmastery.dao.FlooringMasteryProductDao;
import sg.flooringmastery.dao.FlooringMasteryProductDaoFileImpl;
import sg.flooringmastery.dao.FlooringMasteryTaxesDao;
import sg.flooringmastery.dao.FlooringMasteryTaxesDaoFileImpl;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.dto.Taxes;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryServiceFileImpl implements FlooringMasteryService {
    
    FlooringMasteryTaxesDao taxesDao;
    FlooringMasteryProductDao productDao;
    FlooringMasteryOrderDao orderDao;

    public FlooringMasteryServiceFileImpl(FlooringMasteryTaxesDao taxesDao, 
            FlooringMasteryProductDao productDao, FlooringMasteryOrderDao orderDao) {
        this.taxesDao = taxesDao;
        this.productDao = productDao;
        this.orderDao = orderDao;  
    }
      
    @Override
    public Boolean tryDateConvert(String inputDate) {
        LocalDate today = LocalDate.now();
        LocalDate orderDate = LocalDate.now();
        try {
        orderDate = convertStringToDate(inputDate);        
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    
    @Override
    public LocalDate convertStringToDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate ld = LocalDate.now();
        ld = LocalDate.parse(stringDate, formatter);
        return ld;
    }
    
    /**This is specifically for String format for file persistence
     * 
     * @param date
     * @return 
     */
    public String convertDateToString(LocalDate date) {
        String convertedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        return convertedDate;
    }
    
    @Override
    public boolean orderDateFutureCheck(LocalDate orderDate) {
        LocalDate today = LocalDate.now();
        return orderDate.isAfter(today);
    }
    
    @Override
    public boolean validateName(String inputName) {

        if (inputName == null || inputName.length() == 0 ||
                inputName.isEmpty() || inputName.trim().isEmpty()) {
            return false;
        } else {
            return true;
        }
        
    }
    
    /**Get all states and returns a string of them, to easily print
     * 
     * @return
     * @throws FlooringMasteryPersistenceException 
     */
    @Override
    public List<String> getAllStates() throws FlooringMasteryPersistenceException {
        List<Taxes> taxesList = taxesDao.getAllStates();
        List<String> stateTaxesList = new ArrayList<String>();
        for (int i = 0; i < taxesList.size(); i++) {
            String currentState = taxesList.get(i).getState();
            stateTaxesList.add(currentState);
        }
        return stateTaxesList;
    }
    
    @Override
    public List<Taxes> getAllStatesObjects() throws FlooringMasteryPersistenceException {
        List<Taxes> taxesList = taxesDao.getAllStates();
        return taxesList;
    }
    
    @Override
    public boolean validateStateWithStringList(String state, List<String> availableStates) {
        for (int i = 0; i < availableStates.size(); i++) {
            if (availableStates.get(i).equals(state)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean validateStateWithTaxesList(String state, List<Taxes> allStates) {
        for (int i = 0; i < allStates.size(); i++) {
            if (allStates.get(i).getState().equals(state)) {
                return true;
            } 
        }
        return false;
    }
    
    /** returns the appropriate taxes object
     * 
     * @param stateName
     * @param stateTaxesList
     * @return 
     */
    public Taxes getTaxesObjectFromListWithString(String stateName, List<Taxes> stateTaxesList) {
        BigDecimal error = new BigDecimal("0");
        Taxes errorTaxes = new Taxes("error", "error", error);
        for (int i = 0; i < stateTaxesList.size(); i++) {
            if (stateTaxesList.get(i).getState().equals(stateName)) {
                return stateTaxesList.get(i);
            }
        }
        return errorTaxes;
    }
    
    @Override
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        List<Product> productList = productDao.getAllProducts();
        return productList;
    }
    
    @Override
    public boolean validateProduct(String inputProduct, List<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductType().equals(inputProduct)) {
                return true;
            }
        }
        return false;
    }
    
    public Product getProductObjectFromListWithString(String productType, List<Product> productList) {
        BigDecimal errorBD = new BigDecimal("0");
        Product error = new Product("error", errorBD, errorBD);
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductType().equals(productType)) {
                return productList.get(i);
            }
        }
        return error;
    }
    
    @Override
    public boolean validateArea(String inputArea) {
        try {
            BigDecimal area = new BigDecimal(inputArea);
            } catch (NumberFormatException e) {
                return false;
            }
        return true;
    }
    
    @Override
    public BigDecimal convertStringToBigDecimal(String inputArea) {
        BigDecimal result = new BigDecimal(inputArea);
        return result;
    }
    
    @Override
    public boolean validateAreaMinimum(BigDecimal inputArea) {
        BigDecimal min = new BigDecimal("100");
        if (inputArea.compareTo(min) >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public Order createOrderObject(LocalDate date, String name, Taxes state, 
            Product product, BigDecimal area) throws FlooringMasteryPersistenceException {
        
        String stringDate = convertDateToString(date);
        boolean orderDateExists = orderDao.checkIfOrderDateExists(stringDate);
        List<Order> allOrdersOnDate = new ArrayList<Order>();
        int id = 0; // need to generate number, last order number +1
        if (!orderDateExists) {
            id = 1;
        } else {
            allOrdersOnDate = orderDao.loadOrders(stringDate);
            id = calcNextID(allOrdersOnDate);
        }
        //String name
        String stateName = state.getState();
        BigDecimal taxRate = state.getTaxRate();
        String productType = product.getProductType();
        BigDecimal costPerSquareFoot = product.getCostPerSquareFoot();
        BigDecimal laborCostPerSquareFoot = product.getLaborPerSquareFoot();
        BigDecimal materialCost = area.multiply(costPerSquareFoot);
        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
        BigDecimal oneHundred = new BigDecimal("100");
        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate.divide(oneHundred));
        tax = tax.setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost).add(tax);
        total = total.setScale(2, RoundingMode.HALF_UP);
        Order addedOrder = new Order(
            id, name, stateName, taxRate, productType, area,
            costPerSquareFoot, laborCostPerSquareFoot, materialCost,
            laborCost, tax, total);
        
        return addedOrder;
        }
    
    public int calcNextID(List<Order> orderList) {
        int length = orderList.size();
        int lastOrderID = orderList.get(length - 1).getOrderID();
        return lastOrderID + 1;
    }
    
    public void addOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException {
        orderDao.addOrder(date, order);
    }
    
    public boolean checkIfOrderDateExists(String date) throws FlooringMasteryPersistenceException {
        return orderDao.checkIfOrderDateExists(date);
    }
    
    public boolean checkIfOrderExists(String date, String name) throws FlooringMasteryPersistenceException {
        return orderDao.checkIfOrderExists(date, name);
    }
    
    public List<Order> getOrder(String date, String name) throws FlooringMasteryPersistenceException{
        return orderDao.getOrder(date, name);
    }
    
    public List<Order> getAllOrdersOnDate(String date) throws FlooringMasteryPersistenceException{
        return orderDao.getAllOrdersOnDate(date);
    }
    
    public int getOrderIndexFromList(List<Order> orders, Order order) {
        for (int i = 0; i < orders.size(); i++) {
            int ordersID = orders.get(i).getOrderID();
            int orderID = order.getOrderID();
            if (ordersID == orderID) {
                return i;
            }
        }
        return 0;
    }
    
    public void addEditedOrder(Order newOrder, LocalDate date) throws FlooringMasteryPersistenceException {
        String convertedDate = convertDateToString(date);
        List<Order> orders = orderDao.loadOrders(convertedDate);
        int index = getOrderIndexFromList(orders, newOrder);
        newOrder.setOrderID(orders.get(index).getOrderID()); //makes sure that the old ID is held
        orders.set(index, newOrder);
        orderDao.writeOrders(date, orders);
    }
    
    public void removeOrder(Order newOrder, LocalDate date) throws FlooringMasteryPersistenceException {
        String convertedDate = convertDateToString(date);
        List<Order> orders = orderDao.loadOrders(convertedDate);
        int index = getOrderIndexFromList(orders, newOrder);
        newOrder.setOrderID(orders.get(index).getOrderID()); //makes sure that the old ID is held
        orders.remove(index);
        orderDao.writeOrders(date, orders);
    }
    
    public boolean validateOrderEdit(int selection, List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderID() == selection) {
                return true;
            }
            
        }
        return false;
    }
    
    public Order returnOrderWithIDFromList(int selection, List<Order> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderID() == selection) {
                return orderList.get(i);
            }
            
        }
        return null;
    }
    
} // end of service
    
    
//    
//    private UserIO io;
//    private FlooringMasteryView view;
//    private FlooringMasteryDao dao;
//
//    public FlooringMasteryServiceFileImpl(UserIO io, FlooringMasteryView view, FlooringMasteryDao dao) {
//        this.io = io;
//        this.view = view;
//        this.dao = dao;
//    }
//    
//    
//    
//    @Override
//    public LocalDate convertStringToDate(String stringDate) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//        LocalDate ld = LocalDate.now();
//        ld = LocalDate.parse(stringDate, formatter);
//        return ld;
//    }
//    
//    @Override
//    public boolean orderDateFutureCheck(LocalDate orderDate) {
//        LocalDate today = LocalDate.now();
//        return orderDate.isAfter(today);
//    }
//    
//    @Override
//    public void addOrder() throws FlooringMasteryPersistenceException {
//        Boolean dateValid = false;
//        LocalDate orderDateConverted = LocalDate.now();
//        //checks if date is valid format and in the future
//        while (!dateValid) {
//            try {
//            String orderDate = io.readString("Please Enter Order Date (format MM/DD/YYYY: ");
//            orderDateConverted = convertStringToDate(orderDate);
//            if (orderDateFutureCheck(orderDateConverted)) {
//                dateValid = true;
//            } else {
//                io.print("Date must be in the future, sorry our Time Machine is broken");
//                dateValid = false;
//            }
//            } catch (DateTimeParseException e) {
//                io.print("*** Error, invalid format ***");
//            }
//        }
//        
//        //check that the name is not blank
//        Boolean nameValid = false;
//        String customerName = "";
//        while (!nameValid) {
//            customerName = io.readString("Please Enter Customer Name: ");
//            if (customerName == null || customerName.length() == 0 || customerName.isEmpty() || customerName.trim().isEmpty()) {
//                io.print("*** Name may not be blank ***");
//            } else {
//                nameValid = true;
//            }
//        }
//        
//        //check against the tax file (will need to set up the Dao for that)
//        io.print("=== Available States ===");
//        List<Taxes> taxes = dao.listAllTaxes();
//        
//        for (int i = 0; i < taxes.size(); i++) {
//            System.out.print(taxes.get(i).getState() + ", ");
//        }
//        
//        //Validate State
//        Boolean stateValid = false;
//        String stateInput = "";
//        BigDecimal bd = new BigDecimal("0");
//        Taxes stateTaxes = new Taxes("", "", bd);
//        while (!stateValid) {
//            stateInput = io.readString("Please Enter State Abbreviation");
//            for (int i = 0; i < taxes.size(); i++) {
//                if (stateInput.equals(taxes.get(i).getState())) {
//                    stateValid = true;
//                    stateTaxes = new Taxes(
//                            taxes.get(i).getState(),
//                            taxes.get(i).getStateName(),
//                            taxes.get(i).getTaxRate());
//                }
//            }
//            if (stateValid == false) {
//                io.print("*** Invalid State ***");
//            }
//        }
//
//        //print a list of available products and pricing, again needs the Dao to read the file
//        io.print("=== Available Products ===");
//        List<Product> products = dao.listAllProducts();
//        for (Product product : products) {
//            System.out.println(product);
//        }
//        
//        //Validate product type
//        Boolean productValid = false;
//        String productInput = "";
//        Product productOrdered = new Product("0", bd, bd); //blank BigDecimal from above;
//        while (!productValid) {
//            productInput = io.readString("Please Enter a Product Type");
//            for (int i = 0; i < products.size(); i++) {
//                if (productInput.equals(products.get(i).getProductType())) {
//                    productValid = true;
//                    productOrdered = new Product(
//                        products.get(i).getProductType(),
//                        products.get(i).getCostPerSquareFoot(),
//                        products.get(i).getLaborPerSquareFoot());
//                }
//            }
//            if (productValid == false) {
//                io.print("*** Invalid Product ***");
//            }
//        }
//      
//        //Enter area and validate
//        Boolean bigDecimalValid = false;
//        BigDecimal area = new BigDecimal("0");
//        BigDecimal min = new BigDecimal("100");
//        while (!bigDecimalValid) {
//            try {
//                area = new BigDecimal(io.readString("Please Enter Area"));
//                } catch (NumberFormatException e) {
//                    io.print("please enter a valid area");
//                }
//                if (area.compareTo(min) >= 0) {
//                    bigDecimalValid = true;
//                } else {
//                    io.print("area must be a minimum of 100 square feet");
//                    continue;
//                }
//        }
//        
//        //Gathering and calculating for adding to order
//        String state = stateTaxes.getStateName();
//        BigDecimal taxRate = stateTaxes.getTaxRate();
//        String productType = productOrdered.getProductType();
//        BigDecimal costPerSquareFoot = productOrdered.getCostPerSquareFoot();
//        BigDecimal laborCostPerSquareFoot = productOrdered.getLaborPerSquareFoot();
//        BigDecimal materialCost = area.multiply(costPerSquareFoot);
//        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
//        BigDecimal oneHundred = new BigDecimal("100");
//        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate.divide(oneHundred));
//        tax = tax.setScale(2, RoundingMode.HALF_UP);
//        BigDecimal total = materialCost.add(laborCost).add(tax);
//        total = total.setScale(2, RoundingMode.HALF_UP);
//        Order addedOrder = new Order(
//            customerName, state, taxRate, productType, area,
//            costPerSquareFoot, laborCostPerSquareFoot, materialCost,
//            laborCost, tax, total);
//
//        view.addOrderConfirmation();
//        view.printInvoice(addedOrder);
//
//        Boolean submitOrderCont = false;
//        while (!submitOrderCont) {
//            String userAnswer = io.readString("\nWould you like to submit the order? Y/N");
//            if (userAnswer.equals("Y") || userAnswer.equals("y")) {
//                dao.writeOrder(orderDateConverted, addedOrder);
//                io.print("order submitted"); //eventually sent to dao
//                io.readString("Please press enter to continue");
//                submitOrderCont = true;
//            } else if (userAnswer.equals("N") || userAnswer.equals("n")) {
//                io.print("order deleted");
//                io.readString("Please press enter to continue");
//                submitOrderCont = true;
//            } else {
//                io.print("please input a valid answer");
//            }
//        }
//        
//        
//    }  
//    
//    public void displayOrders() throws FlooringMasteryPersistenceException{
//        Boolean dateValid = false;
//        LocalDate orderDateConverted = LocalDate.now();
//        String orderDate = "";
//        //checks if date is valid format and in the future
//        while (!dateValid) {
//            try {
//            orderDate = io.readString("Please Enter Date (format MM/DD/YYYY: ");
//            orderDateConverted = convertStringToDate(orderDate);
//            if (orderDateFutureCheck(orderDateConverted)) {
//                dateValid = true;
//            } else {
//                io.print("Date must be in the future, sorry our Time Machine is broken");
//                dateValid = false;
//                continue;
//            }
//            } catch (DateTimeParseException e) {
//                if (orderDate.equals("apple")) {
//                    io.print("Phil, apple is not a date...");
//                    continue;
//                }
//                io.print("*** Error, invalid format ***");
//            }
//            if (!dao.checkIfFileExists(orderDateConverted)) {
//                dateValid = false;
//                io.print("There are no orders for that day");
//            }
//        }
//        
//        dao.displayAllOrdersOnDate(orderDateConverted);
//        io.readString("Please hit enter to continue");
//    }
//    
//    public void editOrder() throws FlooringMasteryPersistenceException{
//        Boolean dateValid = false;
//        LocalDate orderDateConverted = LocalDate.now();
//        //checks if date is valid format and in the future
//        while (!dateValid) {
//            try {
//            String orderDate = io.readString("Please Enter Date (format MM/DD/YYYY: ");
//            orderDateConverted = convertStringToDate(orderDate);
//            if (orderDateFutureCheck(orderDateConverted)) {
//                dateValid = true;
//            } else {
//                io.print("Date must be in the future, sorry our Time Machine is broken");
//                dateValid = false;
//            }
//            } catch (DateTimeParseException e) {
//                io.print("*** Error, invalid format ***");
//            }
//            
//            if (!dao.checkIfFileExists(orderDateConverted)) {
//                dateValid = false;
//                io.print("There are no orders for that day");
//            }
//        }
//        
//        
//        //input name, check that the name is not blank
//        Boolean nameValid = false;
//        String customerName = "";
//        while (!nameValid) {
//            customerName = io.readString("Please Enter Customer Name: ");
//            if (customerName == null || customerName.length() == 0 || customerName.isEmpty() || customerName.trim().isEmpty()) {
//                io.print("*** Name may not be blank ***");
//            } 
//        
//            if (!dao.checkIfOrderExistsInFile(orderDateConverted, customerName)) {
//                nameValid = false;
//                io.print("There are no orders under that name");
//            } else {
//                nameValid = true;
//            }
//        }
//        dao.editOrder(orderDateConverted, customerName);
//    }
//    
//    public void deleteOrder() throws FlooringMasteryPersistenceException{
//        Boolean dateValid = false;
//        LocalDate orderDateConverted = LocalDate.now();
//        //checks if date is valid format and in the future
//        while (!dateValid) {
//            try {
//            String orderDate = io.readString("Please Enter Order Date (format MM/DD/YYYY: ");
//            orderDateConverted = convertStringToDate(orderDate);
//            if (orderDateFutureCheck(orderDateConverted)) {
//                dateValid = true; 
//            } else {
//                io.print("Date must be in the future, sorry our Time Machine is broken");
//                dateValid = false;
//            }
//            } catch (DateTimeParseException e) {
//                io.print("*** Error, invalid format ***");
//            }
//            if (!dao.checkIfFileExists(orderDateConverted)) {
//                dateValid = false;
//                io.print("There are no orders for that day");
//            }
//        }
//        
//        //check that the name is not blank
//        Boolean nameValid = false;
//        String customerName = "";
//        while (!nameValid) {
//            customerName = io.readString("Please Enter Customer Name: ");
//            if (customerName == null || customerName.length() == 0 || customerName.isEmpty() || customerName.trim().isEmpty()) {
//                io.print("*** Name may not be blank ***");
//            } else {
//                nameValid = true;
//            }
//        }
//        
//        dao.deleteOrder(customerName, orderDateConverted);
//        
//    }
    

