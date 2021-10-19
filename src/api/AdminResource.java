package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    static CustomerService customerServiceSingeton = CustomerService.getInstanceOfCustomerService() ;
    static ReservationService reservationServiceSingeton = ReservationService.getInstanceOfReservationService() ;
    // Return Customer
    public Customer getCustomer(String email)
    {
        if(customerServiceSingeton.getCustomerMap().containsKey(email)) {
            return customerServiceSingeton.getCustomerMap().get(email);
        }
        return null;
    }
    //Add new room
    public void addRoom(List<IRoom> rooms)
    {
       for(IRoom room : rooms)
       {
           reservationServiceSingeton.getAllRooms().put(room.getRoomNumber(),room);
       }
    }
    // Return all added rooms
    public Collection<IRoom> getAllRooms()
    {
        return reservationServiceSingeton.getAllRooms().values() ;
    }
    // Return all added customers
    public Collection<Customer> getAllCustomers()
    {
        return customerServiceSingeton.getCustomerMap().values() ;
    }
    // Print all reservations in console
    public void displayAllReservations()
    {
        reservationServiceSingeton.printAllReservation();
    }
}
