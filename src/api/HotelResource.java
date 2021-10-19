package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    // Return customer object
    public Customer getCustomer(String email)
    {
        if(CustomerService.customers.containsKey(email)) {
            return CustomerService.customers.get(email);
        }
        return null;
    }
    // Create customer
    public static void createACustomer(String email, String firstName, String lastName)
    {
        CustomerService.customers.put(email,new Customer(firstName, lastName, email));
    }
    // Return room object by referring room number
    public IRoom getRoom(String roomNumber)
    {
        if(ReservationService.rooms.containsKey(roomNumber))
        {
            return ReservationService.rooms.get(roomNumber);
        }
        return null;
    }
    // New Reservation
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate)
    {
        return new ReservationService().reserveRoom(getCustomer(customerEmail),room, checkInDate, checkOutDate) ;
    }
    // Return customer's Reservation
    public Collection<Reservation> getCustomerReservations(String customerEmail)
    {
        if(ReservationService.reservations.containsKey(customerEmail))
        {
            return  ReservationService.reservations.get(customerEmail);
        }
        return null;
    }
    // Find available room based on given dates
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut)
    {
        return new ReservationService().findRooms(checkIn, checkOut) ;
    }
}
