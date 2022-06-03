/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.view;

import java.util.List;
import sg.flooringmastery.UserIO.UserIO;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.dto.Product;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryView {
    
    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }
    
    
    
    public void viewMenu() {
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* 1. Add an Order");
        io.print("* 2. Display Orders");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Quit");
        io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }
    
    public int menuSelection() {
        int selection = io.readInt("Please select a menu option", 1, 5);
        return selection;
        
    }
    
    public void addOrderDisplay() {
        io.print("=== Add Order ===");
    }
    
    public String inputDate() {
        String result = io.readString("Please input date in MM/DD/YYYY format.");
        return result;
    }
    
    public void invalidDateFormat() {
        io.print("*** Date invalid, must be MM/DD/YYYY ***");
    }
    
    public void dateNotInFuture() {
        io.print("*** Invalid date, must be in the future ***");
    }
    
    public String inputName() {
        String result = io.readString("Please input name.");
        return result;
    }
    
    public void listAllStates(List<String> states) {
        //for loop to list all the states
        io.print("Available States:");
        for (int i = 0; i < states.size(); i++) {
            System.out.print(states.get(i) + ", ");
            
        }
    }
    
    public String inputState() {
        String result = io.readString("Please Input State.");
        return result;  
    }
    
    public void stateInvalid() {
        io.print("*** State Invalid ***");
    }
    
    public void listAllProducts(List<Product> productList) {
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(productList.get(i)); 
        }
    }
    
    public String inputProduct() {
        String result = io.readString("Please Input Product.");
        return result; 
    }
    
    public void productInvalid() {
        io.print("*** Product Invalid ***");
    }
    
    public String inputArea() {
        String result = io.readString("Please Input Area.");
        return result; 
    }
    
    public void areaInvalid() {
        io.print("*** Area Invalid ***");
    }
    
    public void areaMinInvalid() {
        io.print("Area must be a minimum of 100 square feet");
    }
    
    
    public boolean confirmOrder(Order order) {
        printInvoice(order);
        boolean validInput = false;
        while (!validInput) {
            String userAnswer = io.readString("Add Order? (Y/N)");
            if (userAnswer.equals("Y") || userAnswer.equals("y")) {
                    io.print("order submitted"); //eventually sent to dao
                    io.readString("Please press enter to continue");
                    return true;
                } else if (userAnswer.equals("N") || userAnswer.equals("n")) {
                    io.print("order deleted");
                    io.readString("Please press enter to continue");
                    return false;
                } else {
                    io.print("please input a valid answer");
                }
        }
        return false; //won't ever reach
    }
    
    public boolean confirmDeleteOrder(Order order) {
        printInvoice(order);
        boolean validInput = false;
        while (!validInput) {
            String userAnswer = io.readString("Delete Order? (Y/N)");
            if (userAnswer.equals("Y") || userAnswer.equals("y")) {
                    io.print("order deleted"); //eventually sent to dao
                    io.readString("Please press enter to continue");
                    return true;
                } else if (userAnswer.equals("N") || userAnswer.equals("n")) {
                    io.print("order retained");
                    io.readString("Please press enter to continue");
                    return false;
                } else {
                    io.print("please input a valid answer");
                }
        }
        return false; //won't ever reach
    }
    
    public void displayCustomerName(Order order) {
        io.print(order.getCustomerName());
    }
    
    public void displayState(Order order) {
        io.print(order.getState());
    }
    
    public void displayProductType(Order order) {
        io.print(order.getProductType());
    }
    
    public void displayArea(Order order) {
        io.print(order.getArea().toString());
    }
    
    public void editOrderDisplay() {
        io.print("=== Edit Order ===");
    }
    
    public void displayOrdersDisplay() {
        io.print("=== View Orders ===");
    }
    
    public void removeAnOrderDisplay() {
        io.print("=== Remove an Order ===");
    }
    
    public void addOrderConfirmation() {
        io.print("=== Current Order ===");
    }
    
    public void printInvoice(Order order) {
        io.print("= = = = = = = = = = = =");
        io.print("Invoice:");
        io.print("Order ID: " + order.getOrderID());
        io.print("Customer Name: " + order.getCustomerName());
        io.print("Product Ordered: " + order.getProductType());
        io.print("Total Area: " + order.getArea() + " Square Feet");
        io.print("Material Cost: $" + order.getMaterialCost());
        io.print("Labor Cost: $" + order.getLaborCost());
        io.print("Taxes: $" + order.getTaxes());
        io.print("Grand Total: $" + order.getTotal());
    }
    
    public void orderDisplay() {
        io.print("=== Order ===");
    }
    
    public void noOrdersOnDate() {
        io.print("No Orders on that Date");
    }
    
    public void noOrdersUnderName() {
        io.print("No Orders Under That Name and Date");
    }
    
    public void hitEnterToContinue() {
        io.readString("Please Hit Enter To Continue");
    }
    
    public int pickBetweenMultipleOrdersToEdit(List<Order> orders) {
        io.print("Order Options: ");
        String orderNumbers = "";
        for (int i = 0; i < orders.size(); i++) {
            orderNumbers += orders.get(i).getOrderID() + ", ";
        }
        io.print(orderNumbers);
        return io.readInt("Which order would you like to edit?");
    }
    
    public int pickBetweenMultipleOrdersToDelete(List<Order> orders) {
        io.print("Order Options: ");
        String orderNumbers = "";
        for (int i = 0; i < orders.size(); i++) {
            orderNumbers += orders.get(i).getOrderID() + ", ";
        }
        io.print(orderNumbers);
        return io.readInt("Which order would you like to remove?");
    }
    
    public void orderNumberInvalid() {
        io.print("Not a valid order selection");
    }
    
    public void exitMessage() {
        io.print("=== Thank You, Goodbye ===");
    }
    
}
