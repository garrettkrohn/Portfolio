/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package sg.flooringmastery.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.dto.Taxes;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryServiceFileImplTest {
    
    
    public FlooringMasteryServiceFileImplTest() {
    }
    
    ApplicationContext ctx = 
        new ClassPathXmlApplicationContext("applicationContext.xml");
    FlooringMasteryService service = 
        ctx.getBean("service", FlooringMasteryServiceFileImpl.class);

    @Test
    public void testConvertStringToDate() {
        String d = "01/01/2023";
        LocalDate result = service.convertStringToDate(d);
        
        LocalDate expectedResult = LocalDate.now();
        expectedResult = LocalDate.parse("2023-01-01");
        
        assertTrue("Dates should be the same", result.compareTo(expectedResult) == 0);
    }

    @Test
    public void testOrderDateFutureCheckPass() {
        LocalDate future = LocalDate.parse("2025-01-01");
        
        boolean testDate = service.orderDateFutureCheck(future);
        
        assertTrue("Future should be after today", testDate);
    }
    
    @Test
    public void testValidateName() {
        String name = "Test Name";
        boolean nameValid = service.validateName(name);
        assertTrue("Name should be valid", nameValid);
    }
    
    @Test
    public void testValidateStateWithStringListTrue() {
        List<String> stateList = new ArrayList<String>();
        stateList.add("MN");
        stateList.add("OH");
        stateList.add("IL");
        String stateName = "MN";
        boolean listContains = service.validateStateWithStringList(stateName, stateList);
        
        assertTrue("list conains the state", listContains);
    }
    
    @Test
    public void testValidateStateWithStringListFalse() {
        List<String> stateList = new ArrayList<String>();
        stateList.add("MN");
        stateList.add("OH");
        stateList.add("IL");
        String stateName = "CA";
        boolean listContains = service.validateStateWithStringList(stateName, stateList);
        
        assertFalse("list conains the state", listContains);
    }
    
    @Test
    public void testGetTaxesObjectFromListWithString() {
        List<Taxes> stateTaxesList = new ArrayList<Taxes>();
        BigDecimal bd = new BigDecimal("0");
        stateTaxesList.add(new Taxes("test", "test", bd));
        stateTaxesList.add(new Taxes("test2", "test2", bd));
        String state = "test";
        Taxes testTaxes = service.getTaxesObjectFromListWithString(state, stateTaxesList);
        
        assertTrue("list contains taxes", testTaxes.equals(stateTaxesList.get(0)));
    }
    
    @Test
    public void testValidateProduct() {
        List<Product> productList = new ArrayList<Product>();
        BigDecimal bd = new BigDecimal("0");
        productList.add(new Product("test", bd, bd));
        productList.add(new Product("test2", bd, bd));
        productList.add(new Product("test3", bd, bd));
        String testProduct = "test2";
        
        boolean listContains = service.validateProduct(testProduct, productList);
        
        assertTrue("list contains product", listContains);
    }
    
    @Test
    public void testGetProductObjectFromListWithString() {
        List<Product> productList = new ArrayList<Product>();
        BigDecimal bd = new BigDecimal("0");
        productList.add(new Product("test", bd, bd));
        productList.add(new Product("test2", bd, bd));
        productList.add(new Product("test3", bd, bd));
        String testProduct = "test2";
        
        Product testResult = service.getProductObjectFromListWithString(testProduct, productList);
        
        assertTrue("method returns product", testResult.equals(productList.get(1)));
        
    }

    @Test
    public void testConvertDateToString() throws Exception {
        LocalDate today = LocalDate.now();
        
    }

    @Test
    public void testDisplayOrders() throws Exception {
    }

    @Test
    public void testEditOrder() throws Exception {
    }

    @Test
    public void testDeleteOrder() throws Exception {
    }
    
}
