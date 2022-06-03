/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package sg.flooringmastery.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import sg.flooringmastery.dto.Order;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;
import sg.flooringmastery.service.FlooringMasteryService;
import sg.flooringmastery.service.FlooringMasteryServiceFileImpl;
import org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryOrderDaoFileImplTest {
    
    FlooringMasteryOrderDao orderDao = new FlooringMasteryOrderDaoFileImpl();
    FlooringMasteryTaxesDao taxesDao = new FlooringMasteryTaxesDaoFileImpl();
    FlooringMasteryProductDao productDao = new FlooringMasteryProductDaoFileImpl();
    FlooringMasteryService service = new FlooringMasteryServiceFileImpl(taxesDao, productDao, orderDao);
    
   

    @Test
    public void testUnmarshallOrder() throws FlooringMasteryPersistenceException {
        //test order
        int orderID = 1;
        String orderName = "Test Name";
        String orderState = "TX";
        BigDecimal orderTaxRate = new BigDecimal("4.45");
        String orderProductType = "Wood";
        BigDecimal orderArea = new BigDecimal("200");
        BigDecimal orderCostPerSquareFoot = new BigDecimal("5.15");
        BigDecimal orderLaborCostPerSqureFoot = new BigDecimal("4.75");
        BigDecimal orderMaterialCost = new BigDecimal("1030.00");
        BigDecimal orderLaborCost = new BigDecimal("950");
        BigDecimal orderTaxes = new BigDecimal("88.11");
        BigDecimal orderTotal = new BigDecimal("2068.11");
        
        
        Order testOrder = new Order(orderID, orderName, orderState,
                 orderTaxRate, orderProductType, orderArea, 
                orderCostPerSquareFoot, orderLaborCostPerSqureFoot, 
                orderMaterialCost, orderLaborCost, orderTaxes, orderTotal);
        
        //add the order to test dao
        LocalDate ld = LocalDate.now();
        orderDao.addOrder(ld, testOrder);
        
        //load the file
        String date = service.convertDateToString(ld);
        List<Order> orderList = orderDao.loadOrders(date);
        
        //get order
        Order returnOrder = orderList.get(0);
        
        assertTrue(testOrder.equals(returnOrder));
        
    }
    
    @Test
    public void testWriteOrders() throws FlooringMasteryPersistenceException {
        LocalDate date = LocalDate.now();
        int orderID = 1;
        String orderName = "Test Name";
        String orderState = "TX";
        BigDecimal orderTaxRate = new BigDecimal("4.45");
        String orderProductType = "Wood";
        BigDecimal orderArea = new BigDecimal("200");
        BigDecimal orderCostPerSquareFoot = new BigDecimal("5.15");
        BigDecimal orderLaborCostPerSqureFoot = new BigDecimal("4.75");
        BigDecimal orderMaterialCost = new BigDecimal("1030.00");
        BigDecimal orderLaborCost = new BigDecimal("950");
        BigDecimal orderTaxes = new BigDecimal("88.11");
        BigDecimal orderTotal = new BigDecimal("2068.11");
        
        
        Order testOrder = new Order(orderID, orderName, orderState,
                 orderTaxRate, orderProductType, orderArea, 
                orderCostPerSquareFoot, orderLaborCostPerSqureFoot, 
                orderMaterialCost, orderLaborCost, orderTaxes, orderTotal);
        
        int order2ID = 1;
        String order2Name = "Test Name";
        String order2State = "TX";
        BigDecimal order2TaxRate = new BigDecimal("4.45");
        String order2ProductType = "Wood";
        BigDecimal order2Area = new BigDecimal("200");
        BigDecimal order2CostPerSquareFoot = new BigDecimal("5.15");
        BigDecimal order2LaborCostPerSqureFoot = new BigDecimal("4.75");
        BigDecimal order2MaterialCost = new BigDecimal("1030.00");
        BigDecimal order2LaborCost = new BigDecimal("950");
        BigDecimal order2Taxes = new BigDecimal("88.11");
        BigDecimal order2Total = new BigDecimal("2068.11");
        
        
        Order testOrder2 = new Order(order2ID, order2Name, order2State,
                 order2TaxRate, order2ProductType, order2Area, 
                order2CostPerSquareFoot, order2LaborCostPerSqureFoot, 
                order2MaterialCost, order2LaborCost, order2Taxes, order2Total);
        
        List<Order> orders = new ArrayList<Order>();
        orders.add(testOrder);
        orders.add(testOrder2);
        
        orderDao.writeOrders(date, orders);
    }

    @Test
    public void testMarshallOrder() {
    }

    @Test
    public void testCheckIfOrderDateExists() throws Exception {
    }

    @Test
    public void testLoadOrders() throws Exception {
    }

    @Test
    public void testAddOrder() throws Exception {
    }

    @Test
    public void testCheckIfOrderExists() throws Exception {
    }

    @Test
    public void testGetOrder() throws Exception {
    }

    @Test
    public void testGetAllOrdersOnDate() throws Exception {
    }
    
}
