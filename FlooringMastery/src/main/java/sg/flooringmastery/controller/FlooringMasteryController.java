/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import sg.flooringmastery.UserIO.UserIO;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.dto.Taxes;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;
import sg.flooringmastery.service.FlooringMasteryService;
import sg.flooringmastery.view.FlooringMasteryView;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryService service;

    public FlooringMasteryController(FlooringMasteryView view, 
            FlooringMasteryService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws FlooringMasteryPersistenceException{
        Boolean keepGoing = true;
        int userInput = 0;
        
        while (keepGoing) {
            view.viewMenu();
            userInput = view.menuSelection();
            
            switch (userInput) {
                case 1: 
                    addOrder();
                    break;
                case 2:
                    displayOrders();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    exitMessage();
                    keepGoing = false;
                    break;
                default:
                    
            }
                            
        }
        
    }//end of run
    
    public void addOrder() throws FlooringMasteryPersistenceException{
        view.addOrderDisplay();
        LocalDate orderDate = inputOrderDate();
        String orderName = inputOrderName();
        Taxes orderStateTax = inputState();
        Product orderProduct = inputProduct();
        BigDecimal orderArea = inputArea();
        
        Order newOrder = service.createOrderObject(orderDate, orderName, orderStateTax, orderProduct, orderArea);
        
        Boolean addOrderToDao = view.confirmOrder(newOrder);
        
        if (addOrderToDao) {
            service.addOrder(orderDate, newOrder); //add the order
        } 
       //send to service layer to create order object, then to dao to store it!
       //need to get the orderDao working to generate the id # so the object can be created
        
        
    }
    
    public LocalDate inputOrderDate() {
         //get and validate the date
        Boolean dateValid = false;
        Boolean dateFuture = false;
        String dateInput = "";
        LocalDate validInputDate = LocalDate.now();
        while (!dateValid || !dateFuture) {
            //input date
            dateInput = view.inputDate();
            //trys to convert it to LocalDate, returns Boolean
            dateValid = service.tryDateConvert(dateInput);
            //if it doesn't work, then shows error, jumps back
            if (!dateValid) {
                view.invalidDateFormat();
                continue;
            } else {
                dateValid = true;
            }
        
        //convert it to LocalDate
        validInputDate = service.convertStringToDate(dateInput);
        
            //check if date is in the future
            dateFuture = service.orderDateFutureCheck(validInputDate);
            if (!dateFuture) {
                view.dateNotInFuture();
            } else {
                dateFuture = true;
            }
        
        
        }
        return validInputDate;
    }
    
    public String inputOrderName() {
        Boolean nameValid = false;
        String nameInput = "";
        while (!nameValid) {
            nameInput = view.inputName();
            nameValid = service.validateName(nameInput);
        }
        return nameInput;
    }
    
    public Taxes inputState() throws FlooringMasteryPersistenceException {
        Boolean stateValid = false;
        String stateInput = "";
        List<String> stateTaxesList = new ArrayList<String>();
        List<Taxes> taxesList = service.getAllStatesObjects();
        while (!stateValid) {
            //list all of the states
            stateTaxesList = service.getAllStates();
            view.listAllStates(stateTaxesList);
            stateInput = view.inputState();
            stateValid = service.validateStateWithStringList(stateInput, stateTaxesList);
            if (!stateValid) {
                view.stateInvalid();
            }           
        }
        Taxes stateTaxes = service.getTaxesObjectFromListWithString(stateInput, taxesList);
        return stateTaxes;
    }
    
    public Product inputProduct() throws FlooringMasteryPersistenceException {
        Boolean productValid = false;
        String productInput = "";
        List<Product> productList = new ArrayList<Product>();
        productList = service.getAllProducts();
        while (!productValid) {
            view.listAllProducts(productList);
            productInput = view.inputProduct();
            productValid = service.validateProduct(productInput, productList);
            if (!productValid) {
                view.productInvalid();
            }
        }
        Product product = service.getProductObjectFromListWithString(productInput, productList);
        return product;
    }
    
    public BigDecimal inputArea() {
        Boolean areaValid = false;
        Boolean areaValidMin = false;
        String areaInput = "";
        BigDecimal area = new BigDecimal("0");
        while (!areaValid || !areaValidMin) {
            areaInput = view.inputArea();
            areaValid = service.validateArea(areaInput);
            if (!areaValid) {
                view.areaInvalid();
                continue;
            }
        
        area = service.convertStringToBigDecimal(areaInput);
        
            areaValidMin = service.validateAreaMinimum(area);
            if (!areaValidMin) {
                view.areaMinInvalid();
            }
        }
        return area;
    }
    
    
    public void editOrder() throws FlooringMasteryPersistenceException {
        boolean orderDateExists = false;
        boolean orderNameExists = false;
        Order order = null;
        List<Order> orderMatches = new ArrayList<Order>();
        LocalDate orderDate = LocalDate.now();
        String orderName = "";
        String date = "";
        
        while (!orderDateExists || !orderNameExists) {
            //input date
            orderDate = inputDate();
            date = service.convertDateToString(orderDate);
            orderDateExists = service.checkIfOrderDateExists(date);
            

            if (!orderDateExists) {
                view.noOrdersOnDate();
                continue;
            }
            //input name
            orderName = inputOrderName();
            orderNameExists = service.checkIfOrderExists(date, orderName);
            if (!orderNameExists) {
                view.noOrdersUnderName();
                continue;
                } 
            }
        
        //prints out invoice for one or multiple
         orderMatches = service.getOrder(date, orderName);
         for (int i = 0; i < orderMatches.size(); i++) {
             view.printInvoice(orderMatches.get(i));
         }
         
        if (orderMatches.size() > 1) {
            boolean validOrderSelection = false;
            while (!validOrderSelection) {
                //asks which order the user would like to edit
                int selection = view.pickBetweenMultipleOrdersToEdit(orderMatches);
                validOrderSelection = service.validateOrderEdit(selection, orderMatches);
                if (!validOrderSelection) {
                    view.orderNumberInvalid();
                }
                order = service.returnOrderWithIDFromList(selection, orderMatches);
            }
        } else {
        order = orderMatches.get(0);
        }
        
        //need to put in a method to chose which order if there are multiples
        
        view.editOrderDisplay();
        
        //editName
        String newName = editOrderName(order);
        
        //editState
        Taxes newState = editTaxState(order);
        
        //edit product type
        Product newProduct = editProductType(order);
        
        //edit area
        BigDecimal newArea = editArea(order);
        
        //create new object
        Order newOrder = service.createOrderObject(orderDate, newName, newState, newProduct, newArea);
        newOrder.setOrderID(order.getOrderID());
        
        boolean orderConfirm = view.confirmOrder(newOrder);
        if (orderConfirm) {
        service.addEditedOrder(newOrder, orderDate);
        }

    }
    
    public String editOrderName(Order order) {
        view.displayCustomerName(order);
        String newName = view.inputName();
        if (newName.equals("")) {
            return order.getCustomerName();
        } else {
            return newName;
        }
    } 
    
    
    
    public Taxes editTaxState(Order order) throws FlooringMasteryPersistenceException {
        boolean stateValid = false;
        view.displayState(order);
        List<Taxes> allStates = service.getAllStatesObjects();
        List<String> stateTaxesList = new ArrayList<String>();
        stateTaxesList = service.getAllStates();
        view.listAllStates(stateTaxesList);
        while (!stateValid) {
            String newTaxes = view.inputState();
            if (newTaxes.equals("")) {
                String state = order.getState();
                return service.getTaxesObjectFromListWithString(state, allStates);
            } else {
                stateValid = service.validateStateWithTaxesList(newTaxes, allStates);
                    if (!stateValid) {
                        view.stateInvalid();
                        continue;
                    }
                }
            return service.getTaxesObjectFromListWithString(newTaxes, allStates);
        }
        return null;
    }
    
    
    public Product editProductType(Order order) throws FlooringMasteryPersistenceException {
        boolean productValid = false;
        List<Product> allProducts = service.getAllProducts();
        view.displayProductType(order); 
        List<Product> productList = new ArrayList<Product>();
        productList = service.getAllProducts();
        view.listAllProducts(productList);
        while (!productValid) {
            String newProduct = view.inputProduct();
            if (newProduct.equals("")) {
                String product = order.getProductType();
                return service.getProductObjectFromListWithString(product, allProducts);
            } else {
                productValid = service.validateProduct(newProduct, allProducts);
                if (!productValid) {
                    view.productInvalid();
                    continue;
                }
            }
            return service.getProductObjectFromListWithString(newProduct, allProducts);
        }
        return null;
    }
    
    public BigDecimal editArea(Order order) {
        boolean areaValid = false;
        boolean areaMinValid = false;
        BigDecimal area = new BigDecimal("0");
        while (!areaValid || !areaMinValid) {
            String newArea = view.inputArea();
            if (newArea.equals("")) {
                return order.getArea();
            } else {
                areaValid = service.validateArea(newArea);
                if (!areaValid) {
                    view.areaInvalid();
                    continue;
                }
                area = service.convertStringToBigDecimal(newArea);
                if (!areaMinValid) {
                areaMinValid = service.validateAreaMinimum(area);
                if (!areaMinValid) {
                    view.areaMinInvalid();
                    continue;
                    }
                }    
            }
        }
        return area;
    }
    
    //public BigDecimal editArea(Order order) {
    //check if there is an input, otherwise return the current area
    
    
    
    public void displayOrders() throws FlooringMasteryPersistenceException {
        LocalDate orderDate = inputDate();
        String date = service.convertDateToString(orderDate);
        
        boolean orderDateExists = service.checkIfOrderDateExists(date);
        if (!orderDateExists) {
            view.noOrdersOnDate();
        } else {
        List<Order> orders = service.getAllOrdersOnDate(date);
        for (int i = 0; i < orders.size(); i++) {
            view.printInvoice(orders.get(i));
        }
    }
    }
        
    
    /**Specifically for displaying orders, can be a date in the past
     * 
     * @return 
     */
    public LocalDate inputDate() {
        Boolean dateValid = false;
        String dateInput = "";
        LocalDate validInputDate = LocalDate.now();
        while (!dateValid) {
            //input date
            dateInput = view.inputDate();
            //trys to convert it to LocalDate, returns Boolean
            dateValid = service.tryDateConvert(dateInput);
            //if it doesn't work, then shows error, jumps back
            if (!dateValid) {
                view.invalidDateFormat();
                continue;
            } else {
                dateValid = true;
                validInputDate = service.convertStringToDate(dateInput);
            }
        }
        return validInputDate;
    }
    
    public void exitMessage() {
        view.exitMessage();
    }
    
    public void removeOrder() throws FlooringMasteryPersistenceException {
        boolean orderDateExists = false;
        boolean orderNameExists = false;
        Order order = null;
        List<Order> orderMatches = new ArrayList<Order>();
        LocalDate orderDate = LocalDate.now();
        String orderName = "";
        String date = "";
        
        while (!orderDateExists || !orderNameExists) {
            //input date
            orderDate = inputDate();
            date = service.convertDateToString(orderDate);
            orderDateExists = service.checkIfOrderDateExists(date);
            

            if (!orderDateExists) {
                view.noOrdersOnDate();
                continue;
            }
            //input name
            orderName = inputOrderName();
            orderNameExists = service.checkIfOrderExists(date, orderName);
            if (!orderNameExists) {
                view.noOrdersUnderName();
                continue;
                } 
            }
        
        //prints out invoice for one or multiple
         orderMatches = service.getOrder(date, orderName);
         for (int i = 0; i < orderMatches.size(); i++) {
             view.printInvoice(orderMatches.get(i));
         }
         
        if (orderMatches.size() > 1) {
            boolean validOrderSelection = false;
            while (!validOrderSelection) {
                //asks which order the user would like to edit
                int selection = view.pickBetweenMultipleOrdersToDelete(orderMatches);
                validOrderSelection = service.validateOrderEdit(selection, orderMatches);
                if (!validOrderSelection) {
                    view.orderNumberInvalid();
                }
                order = service.returnOrderWithIDFromList(selection, orderMatches);
            }
        } else {
        order = orderMatches.get(0);
        }
        
        
       
        boolean deleteConfirm = view.confirmDeleteOrder(order);
        if (deleteConfirm) {
        service.removeOrder(order, orderDate);
        }

    }
} // end of file
    
