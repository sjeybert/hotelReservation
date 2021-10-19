package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;

public class CustomerService {

    private static CustomerService customerService ;

    private CustomerService()
    {

    }

    public static CustomerService getInstanceOfCustomerService()
    {
        if(customerService == null)
        {
            customerService = new CustomerService();
        }
        return customerService ;
    }

    private HashMap<String,Customer> customers = new HashMap<String,Customer>();

    public void addCustomer(String email, String firstName, String lastName)
    {
        customers.put(email,new Customer(firstName,lastName,email)) ;
    }

    public Customer getCustomer(String customerEmail)
    {
        return customers.get(customerEmail) ;
    }

    public Collection getAllCustomer()
    {
        return customers.values();
    }

    public HashMap<String,Customer> getCustomerMap() {return customers;} ;
}
