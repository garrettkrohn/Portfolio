///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package sg.flooringmastery.dao;
//
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.Scanner;
//import sg.flooringmastery.UserIO.UserIO;
//import sg.flooringmastery.UserIO.UserIOConsoleImpl;
//import sg.flooringmastery.dto.Order;
//import sg.flooringmastery.dto.Product;
//import sg.flooringmastery.dto.Taxes;
//import sg.flooringmastery.service.FlooringMasteryPersistenceException;
//import sg.flooringmastery.view.FlooringMasteryView;
//
///**
// *
// * @author garrettkrohn
// */
//public class FlooringMasteryDaoFileImpl implements FlooringMasteryDao{
//    public static final String DELIMITER = ",";
//    public final String TAXES_FILE = "Taxes.txt";
//    public final String PRODUCT_FILE = "Products.txt";
//    
//    private Map<String, Taxes> taxesList = new HashMap<String, Taxes>();
//    private Map<String, Product> productList = new HashMap<String, Product>();
//    private Map<String, Order> orderList = new HashMap<String, Order>(); //name on order, order
//    
//    private UserIO io;
//    private FlooringMasteryView view;
//
//    public FlooringMasteryDaoFileImpl(UserIO io, FlooringMasteryView view) { 
//        this.io = io;
//        this.view = view;
//    }
//
//   
//    //this code will eventually be moved to the service layer, dao will receive the object and store it in the appropriate file
//    @Override
//    public void addOrder() {
//        throw new UnsupportedOperationException("Not supported yet."); 
//    }
//
//    @Override
//    public void removeOrder() {
//        throw new UnsupportedOperationException("Not supported yet."); 
//    }
//    
//    @Override
//    public void getAllOrders() {
//        throw new UnsupportedOperationException("Not supported yet."); 
//    }
//    
//    @Override
//    public List<Taxes> listAllTaxes() throws FlooringMasteryPersistenceException {
//        loadTaxes();
//        return new ArrayList(taxesList.values());
//    }
//    
//    @Override
//    public List<Product> listAllProducts() throws FlooringMasteryPersistenceException {
//        loadProduct();
//        return new ArrayList(productList.values());
//    }
//    
//    
//    public Taxes unmarshallTaxes(String taxesAsText){
//        String[] taxTokens = taxesAsText.split(DELIMITER);
//        String state = taxTokens[0];
//        String stateName = taxTokens[1];
//        BigDecimal taxRate = new BigDecimal(taxTokens[2]);
//        Taxes returnTaxes = new Taxes(state, stateName, taxRate);
//        return returnTaxes;
//    }
//    
//    private void loadTaxes() throws FlooringMasteryPersistenceException {
//        Scanner scanner;
//        
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(TAXES_FILE)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException(
//            "Could not load the Taxes into memory", e);
//        }
//        String currentLine;
//        
//        Taxes currentTaxes;
//        String throwAway = scanner.nextLine();
//        while(scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentTaxes = unmarshallTaxes(currentLine);
//            taxesList.put(currentTaxes.getState(), currentTaxes);
//        }
//        scanner.close();
//    }
//    
//    public Product unmarshallProduct(String productAsText) {
//        String[] productTokens = productAsText.split(DELIMITER);
//        String productName = productTokens[0];
//        BigDecimal costPerSquareFoot = new BigDecimal(productTokens[1]);
//        BigDecimal laborPerSquareFoot = new BigDecimal(productTokens[2]);
//        Product returnProduct = new Product(productName, costPerSquareFoot, laborPerSquareFoot);
//        return returnProduct;
//    }
//    
//    private void loadProduct() throws FlooringMasteryPersistenceException {
//        Scanner scanner;
//        
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(PRODUCT_FILE)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException(
//            "Could not load the Products into memory", e);
//        }
//        String currentLine;
//        
//        Product currentProduct;
//        String throwAway = scanner.nextLine();
//        while(scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentProduct = unmarshallProduct(currentLine);
//            productList.put(currentProduct.getProductType(), currentProduct);
//        }
//        scanner.close();
//    }
//    
//    public Order unmarshallOrder(String orderAsText) {
//        String[] orderTokens = orderAsText.split(DELIMITER);
//        String customerName = orderTokens[0];
//        String state = orderTokens[1];
//        BigDecimal taxRate = new BigDecimal(orderTokens[2]);
//        String productType = orderTokens[3];
//        BigDecimal area = new BigDecimal(orderTokens[4]);
//        BigDecimal costPerSquareFoot = new BigDecimal(orderTokens[5]);
//        BigDecimal laborCostPerSquareFoot = new BigDecimal(orderTokens[6]);
//        BigDecimal materialCost = new BigDecimal(orderTokens[7]);
//        BigDecimal laborCost = new BigDecimal(orderTokens[8]);
//        BigDecimal taxes = new BigDecimal(orderTokens[9]);
//        BigDecimal total = new BigDecimal(orderTokens[10]);
//        Order result = new Order(
//            customerName, state, taxRate, productType,
//            area, costPerSquareFoot, laborCostPerSquareFoot,
//            materialCost, laborCost, taxes, total);
//        return result;
//    }
//    
//    public String marshallOrder(Order order) {
//        String OrderToString = order.getCustomerName() + DELIMITER;
//        OrderToString += order.getState() + DELIMITER;
//        OrderToString += order.getTaxRate() + DELIMITER;
//        OrderToString += order.getProductType() + DELIMITER;
//        OrderToString += order.getArea() + DELIMITER;
//        OrderToString += order.getCostPerSquareFoot() + DELIMITER;
//        OrderToString += order.getLaborCostPerSquareFoot() + DELIMITER;
//        OrderToString += order.getMaterialCost() + DELIMITER;
//        OrderToString += order.getLaborCost() + DELIMITER;
//        OrderToString += order.getTaxes() + DELIMITER;
//        OrderToString += order.getTotal();
//        return OrderToString;
//    }
//    
//    
//    
//    public void writeOrder(LocalDate date, Order order) throws FlooringMasteryPersistenceException {
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//        
//        String fileToWrite = "Orders_" + dateConverted + ".txt";
//        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
//                    + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
//                    + "MaterialCost,LaborCost,Tax,Total";
//        PrintWriter out;
//        try {
//            out = new PrintWriter(new FileWriter(fileToWrite, true));
//        } catch (IOException e) {
//            throw new FlooringMasteryPersistenceException(
//                "Could not save Order data", e);
//        }
//        //check if the file is blank, if so, add a header
//        Scanner scanner;
//        try {
//        scanner = new Scanner(
//                new BufferedReader(
//                        new FileReader(fileToWrite)));
//            } catch (FileNotFoundException e) {
//                throw new FlooringMasteryPersistenceException(
//                        "-_- Could not load roster data into memory.", e);
//            }
//        
//        try {
//            if (scanner.nextLine() == "" || scanner.nextLine() == null) {
//            out.println(header);
//            out.flush();}
//        } catch (NoSuchElementException e) {
//            out.println(header);
//            out.flush();
//        }
//        
//        String orderAsText = marshallOrder(order);
//        out.println(orderAsText);
//        out.flush();
//        scanner.close();
//    }
//    
//    public void displayAllOrdersOnDate(LocalDate date) throws FlooringMasteryPersistenceException {
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//
//        String fileName = "Orders_" + dateConverted + ".txt"; 
//        
//        Scanner scanner;
//        
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(fileName)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException(
//            "No orders for that date.");
//        }
//        String currentLine;
//        
//        Order currentOrder = new Order("null");
//        String throwAway = scanner.nextLine();
//        while(scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentOrder = unmarshallOrder(currentLine);
//            orderList.put(currentOrder.getCustomerName(), currentOrder);
//        }
//        List<Order> searchMatchOrders = new ArrayList(orderList.values());
//        //display all, WIP, check what I did last time for other projects
//        for (int i = 0; i < searchMatchOrders.size(); i++) {
//            view.orderDisplay();
//            view.printInvoice(searchMatchOrders.get(i));  
//        } 
//            scanner.close();
//        
//    }
//    
//    public Boolean checkIfFileExists(LocalDate date) {
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//
//        String fileName = "Orders_" + dateConverted + ".txt"; 
//        Scanner scanner;
//        
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(fileName)));
//            return true;
//        } catch (FileNotFoundException e) {
//            return false;
//        }
//        
//    }
//    
//    public Boolean checkIfOrderExistsInFile(LocalDate date, String name) throws FlooringMasteryPersistenceException {
//        //create appropriate fileName
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//        String fileName = "Orders_" + dateConverted + ".txt"; 
//        
//        //scans the correct fileName
//        Scanner scanner;
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(fileName)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException(
//            "No orders for that date.");
//        }
//        
//        //all orders are scanned and added to List
//        List<Order> allOrdersOnDate = new ArrayList<Order>();
//        String currentLine;
//        Order currentOrderObject = null;
//        String throwAway = scanner.nextLine();
//        while(scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentOrderObject = unmarshallOrder(currentLine);
//            allOrdersOnDate.add(currentOrderObject);
//        }
//        
//        //search for the correct object, print invoice if found
//        Boolean nameFound = false;
//        for (int i = 0; i < allOrdersOnDate.size(); i++) {
//            if (allOrdersOnDate.get(i).getCustomerName().equals(name)) {
//            return true;
//            }
//        }
//        return false;
//    }
//    
//    
//    public void editOrder(LocalDate date, String name) throws FlooringMasteryPersistenceException {  
//        //create appropriate fileName
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//        String fileName = "Orders_" + dateConverted + ".txt"; 
//        
//        //scans the correct fileName
//        Scanner scanner;
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(fileName)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException(
//            "No orders for that date.");
//        }
//        
//        //all orders are scanned and added to List
//        List<Order> allOrdersOnDate = new ArrayList<Order>();
//        String currentLine;
//        Order currentOrderObject = null;
//        String throwAway = scanner.nextLine();
//        while(scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentOrderObject = unmarshallOrder(currentLine);
//            allOrdersOnDate.add(currentOrderObject);
//        }
//        
//        //search for the correct object, print invoice if found
//        int index = 0;
//        Boolean nameFound = false;
//        for (int i = 0; i < allOrdersOnDate.size(); i++) {
//            if (allOrdersOnDate.get(i).getCustomerName().equals(name)) {
//            view.orderDisplay();
//            view.printInvoice(allOrdersOnDate.get(i));
//            index = i;
//            nameFound = true;
//            }
//        }
//
//        if (!nameFound) {
//            io.print("Sorry, we could not find an order for " + name + " on " + date);
//        }
//        
//        io.readString("Press Enter to Continue");
//        
//        //collect edits
//        String newName = io.readString("Enter Customer Name: (" + allOrdersOnDate.get(index).getCustomerName() + ")");
//        if (newName.equals("")) {
//            newName = allOrdersOnDate.get(index).getCustomerName();
//        }
//        
//        //list all taxes
//        List<Taxes> taxes = listAllTaxes();
//        for (int i = 0; i < taxes.size(); i++) {
//            System.out.print(taxes.get(i).getState() + ", ");
//        }
//        
//        //validate state
//        Boolean stateValid = false;
//        String newState = "";
//        while (!stateValid) {
//            newState = io.readString("Enter State: (" + allOrdersOnDate.get(index).getState() + ")");
//            if (newState.equals("")) {
//                newState = allOrdersOnDate.get(index).getState();
//                break;
//            } else {
//                for (int i = 0; i < taxes.size(); i++) {
//                    if (newState.equals(taxes.get(i).getState())) {
//                        stateValid = true;
//                    }
//                }
//            }
//            if (stateValid == false) {
//                io.print("*** Invalid State ***");
//            }
//        }
//        
//        //List Product Type
//        io.print("=== Available Products ===");
//        List<Product> products = listAllProducts();
//        for (Product product : products) {
//            System.out.println(product);
//        }
//        //Validate Product Type
//        Boolean productValid = false;
//        String newProductType = "";
//        while (!productValid) {
//            newProductType = io.readString("Enter Product Type (" + allOrdersOnDate.get(index).getProductType() + ")");
//            if (newProductType.equals("")) {
//                newProductType = allOrdersOnDate.get(index).getProductType();
//                productValid = true;
//                break;
//            } else {
//                for (int i = 0; i < products.size(); i++) {
//                    if (newProductType.equals(products.get(i).getProductType())) {
//                    productValid = true;                   
//                }
//            }
//            if (productValid == false) {
//                io.print("*** Invalid Product ***");
//            }
//            }
//        }
//        
//        //validate area
//        Boolean bigDecimalValid = false;
//        String areaInput = "";
//        BigDecimal newArea = new BigDecimal("0");
//        BigDecimal min = new BigDecimal(100);
//        while (!bigDecimalValid) {
//            areaInput = io.readString("Enter Area (" + allOrdersOnDate.get(index).getArea() + ")");
//            if (areaInput.equals("")) {
//                newArea = allOrdersOnDate.get(index).getArea();
//                bigDecimalValid = true;
//                break;
//            } else {
//                try {
//                    newArea = new BigDecimal(areaInput);
//                } catch (NumberFormatException e) {
//                    io.print("Please enter a valid area");
//                }
//                if (newArea.compareTo(min) >= 0) {
//                    bigDecimalValid = true;
//                } else {
//                    io.print("area must be a minimum of 100 square feet");
//                    continue;
//                }
//            }
//        }
//        
//        //update the Order object
//        String customerName = newName;
//        String state = newState;
//        BigDecimal taxRate = allOrdersOnDate.get(index).getTaxRate();
//        String productType = newProductType;
//        BigDecimal area = newArea;
//        BigDecimal costPerSquareFoot = allOrdersOnDate.get(index).getCostPerSquareFoot();
//        BigDecimal laborCostPerSquareFoot = allOrdersOnDate.get(index).getLaborCostPerSquareFoot();
//        BigDecimal materialCost = area.multiply(costPerSquareFoot);
//        BigDecimal laborCost = area.multiply(laborCostPerSquareFoot);
//        BigDecimal oneHundred = new BigDecimal("100");
//        BigDecimal tax = (materialCost.add(laborCost)).multiply(taxRate.divide(oneHundred));
//        tax = tax.setScale(2, RoundingMode.HALF_UP);
//        BigDecimal total = materialCost.add(laborCost).add(tax);
//        total = total.setScale(2, RoundingMode.HALF_UP);
//        Order finalEditedOrder = new Order(
//            customerName, state, taxRate, productType, area,
//            costPerSquareFoot, laborCostPerSquareFoot, materialCost,
//            laborCost, tax, total);
//        
//        //remove old order
//        allOrdersOnDate.remove(allOrdersOnDate.get(index));
//        
//        //add new order
//        allOrdersOnDate.add(finalEditedOrder);
//        
//        //display new invoice
//        view.printInvoice(finalEditedOrder);
//        
//        //ask if they should add
//        String addEditedOrder = io.readString("Complete edit? Y/N");
//        if (addEditedOrder.equals("y") || addEditedOrder.equals("Y")) {
//        //need to write it all to the file now
//        writeMultipleOrders(allOrdersOnDate, date);
//        io.print("Order Added");
//        io.readString("Please Hit Enter to Continue");
//        }
//        
//        scanner.close();
//        
//    }
//    
//    public Order searchName(List<Order> order, String name) {
//        for (int i = 0; i < order.size(); i++) {
//            if (order.get(i).getCustomerName() == name) {
//                return order.get(i);
//            }
//        }
//        return null;
//    }
//    
//    public void writeMultipleOrders(List<Order> order, LocalDate date) throws FlooringMasteryPersistenceException {
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//        
//        String fileToWrite = "Orders_" + dateConverted + ".txt";
//        String header = "OrderNumber,CustomerName,State,TaxRate,ProductType,"
//                    + "Area,CostPerSquareFoot,LaborCostPerSquareFoot,"
//                    + "MaterialCost,LaborCost,Tax,Total";
//        PrintWriter out;
//        try {
//            out = new PrintWriter(new FileWriter(fileToWrite));
//        } catch (IOException e) {
//            throw new FlooringMasteryPersistenceException(
//                "Could not save Order data", e);
//        }
//        //check if the file is blank, if so, add a header
//        Scanner scanner;
//        try {
//        scanner = new Scanner(
//                new BufferedReader(
//                        new FileReader(fileToWrite)));
//            } catch (FileNotFoundException e) {
//                throw new FlooringMasteryPersistenceException(
//                        "-_- Could not load roster data into memory.", e);
//            }
//        
//        try {
//            if (scanner.nextLine() == "" || scanner.nextLine() == null) {
//            out.println(header);
//            out.flush();}
//        } catch (NoSuchElementException e) {
//            out.println(header);
//            out.flush();
//        }
//        
//        for (int i = 0; i < order.size(); i++) {
//            String currentOrder = marshallOrder(order.get(i));
//            out.println(currentOrder);
//            out.flush();
//        }
//        scanner.close();
//    }
//    
//    public void deleteOrder(String name, LocalDate date) throws FlooringMasteryPersistenceException {
//        //create appropriate fileName
//        String dateConverted = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
//        String fileName = "Orders_" + dateConverted + ".txt"; 
//        
//        //scans the correct fileName
//        Scanner scanner;
//        try {
//            scanner = new Scanner(
//                new BufferedReader(
//                    new FileReader(fileName)));
//        } catch (FileNotFoundException e) {
//            throw new FlooringMasteryPersistenceException(
//            "No orders for that date.");
//        }
//        
//        //all orders are scanned and added to List
//        List<Order> allOrdersOnDate = new ArrayList<Order>();
//        String currentLine;
//        Order currentOrderObject = null;
//        String throwAway = scanner.nextLine();
//        while(scanner.hasNextLine()) {
//            currentLine = scanner.nextLine();
//            currentOrderObject = unmarshallOrder(currentLine);
//            allOrdersOnDate.add(currentOrderObject);
//        }
//        
//        //search for the correct object, print invoice if found
//        int index = 0;
//        Boolean nameFound = false;
//        for (int i = 0; i < allOrdersOnDate.size(); i++) {
//            if (allOrdersOnDate.get(i).getCustomerName().equals(name)) {
//            view.orderDisplay();
//            view.printInvoice(allOrdersOnDate.get(i));
//            index = i;
//            nameFound = true;
//            allOrdersOnDate.remove(index);
//            } 
//        }
//        if (!nameFound) {
//            io.print("No order found under that name");
//        }
//        
//        
//        
//        //ask if they should delete
//        String addEditedOrder = io.readString("Complete deletion? Y/N");
//        if (addEditedOrder.equals("y") || addEditedOrder.equals("Y")) {
//        //need to write it all to the file now
//        writeMultipleOrders(allOrdersOnDate, date);
//        io.print("Order Added");
//        io.readString("Please Hit Enter to Continue");
//        }
//        
//        scanner.close();
//    }
//    
//}
