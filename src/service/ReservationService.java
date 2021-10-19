package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService ;
    private ReservationService()
    {

    }
    public static ReservationService getInstanceOfReservationService()
    {
        if(reservationService == null)
        {
            reservationService = new ReservationService();
        }
        return reservationService ;
    }
    private HashMap<String,IRoom> rooms = new HashMap<>();
    private HashMap<String,ArrayList<Reservation>> reservations = new HashMap<>();

    public void addRoom(IRoom room)
    {
    rooms.put(room.getRoomNumber(),room) ;
    }

    public IRoom getARoom(String roomId)
    {
        return rooms.get(roomId);
    }

    public Reservation reserveRoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {

            Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
            if(reservations.containsKey(customer.getEmail()))
            {
                reservations.get(customer.getEmail()).add(newReservation);

            }
            else
            {
                ArrayList<Reservation>newReservationCollection = new ArrayList<>();
                newReservationCollection.add(newReservation);
                reservations.put(customer.getEmail(),newReservationCollection ) ;

            }

        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate)
    {
        HashSet<IRoom> availableRooms = new HashSet<>();
        HashSet<IRoom> roomsInReservationData = new HashSet<>();
        HashSet<IRoom> temp = new HashSet<>() ;

        // Iterate reservations( Map VALUE )
        for (Collection<Reservation> existingCollectionData : reservations.values()) {
            //Iterate List of IRooms
            for (Reservation existingReservationData : existingCollectionData) {
                // Add rooms in reservation data history
                roomsInReservationData.add(existingReservationData.getRoom());
                // Criteria
                if (existingReservationData.getCheckOutDate().after(new Date())) {
                    if (checkInDate.before(existingReservationData.getCheckInDate()) && checkOutDate.before(existingReservationData.getCheckInDate())) {
                        availableRooms.add(existingReservationData.getRoom());

                    } else if (checkInDate.after(existingReservationData.getCheckInDate()) && checkInDate.after(existingReservationData.getCheckOutDate())) {
                        availableRooms.add(existingReservationData.getRoom());
                    }
                    else
                    {
                        temp.add(existingReservationData.getRoom());
                    }
                }
            }
        }
        availableRooms.removeAll(temp);
        temp.clear();
        temp.addAll(rooms.values());
        temp.removeAll(roomsInReservationData);
        availableRooms.addAll(temp) ;
        return availableRooms ;

    }

    public Collection<Reservation> getCustomersReservation(Customer customer)
    {
        if(reservations.containsKey(customer.getEmail()))
        {
            return reservations.get(customer.getEmail());
        }
        return null;
    }

    public void printAllReservation()
    {
        System.out.println(reservations);
    }

    public HashMap<String,IRoom> getAllRooms() {return this.rooms ;}

    public HashMap<String,ArrayList<Reservation>> getAllReservations() {return this.reservations ;}


}
