package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private CustomerService()
    {

    }

    public static Map<String,Customer> customers = new HashMap<>();

    public void addCustomer(String email, String firstName, String lastName)
    {
        customers.put(email,new Customer(firstName,lastName,email)) ;
    }

    public static Customer getCustomer(String customerEmail)
    {
        return customers.get(customerEmail) ;
    }

    public static Collection getAllCustomer()
    {
        return customers.values();
    }
}
