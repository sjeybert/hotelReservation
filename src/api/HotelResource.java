package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    static CustomerService customerServiceSingeton = CustomerService.getInstanceOfCustomerService() ;
    static ReservationService reservationServiceSingeton = ReservationService.getInstanceOfReservationService() ;
    // Return customer object
    public Customer getCustomer(String email)
    {
        if(customerServiceSingeton.getCustomerMap().containsKey(email)) {
            return customerServiceSingeton.getCustomerMap().get(email);
        }
        return null;
    }
    // Create customer
    public static void createACustomer(String email, String firstName, String lastName)
    {
        customerServiceSingeton.getCustomerMap().put(email,new Customer(firstName, lastName, email));
    }
    // Return room object by referring room number
    public IRoom getRoom(String roomNumber)
    {
        if(reservationServiceSingeton.getAllRooms().containsKey(roomNumber))
        {
            return reservationServiceSingeton.getAllRooms().get(roomNumber);
        }
        return null;
    }
    // New Reservation
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate)
    {
        return reservationServiceSingeton.reserveRoom(getCustomer(customerEmail),room, checkInDate, checkOutDate) ;
    }
    // Return customer's Reservation
    public Collection<Reservation> getCustomerReservations(String customerEmail)
    {
        if(reservationServiceSingeton.getAllReservations().containsKey(customerEmail))
        {
            return reservationServiceSingeton.getAllReservations().get(customerEmail);
        }
        return null;
    }
    // Find available room based on given dates
    public Collection<IRoom> findARoom(Date checkIn, Date checkOut)
    {
        return reservationServiceSingeton.findRooms(checkIn, checkOut) ;
    }
}
