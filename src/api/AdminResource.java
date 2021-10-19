package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    // Return Customer
    public Customer getCustomer(String email)
    {
        if(CustomerService.customers.containsKey(email)) {
            return CustomerService.customers.get(email);
        }
        return null;
    }
    //Add new room
    public void addRoom(List<IRoom> rooms)
    {
       for(IRoom room : rooms)
       {
           ReservationService.rooms.put(room.getRoomNumber(),room);
       }
    }
    // Return all added rooms
    public Collection<IRoom> getAllRooms()
    {
        return ReservationService.rooms.values() ;
    }
    // Return all added customers
    public Collection<Customer> getAllCustomers()
    {
        return CustomerService.customers.values() ;
    }
    // Print all reservations in console
    public void displayAllReservations()
    {
        ReservationService.printAllReservation();
    }
}
