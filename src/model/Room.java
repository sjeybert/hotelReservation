package model;

import service.ReservationService;

import java.util.Objects;

public class Room implements IRoom{

    private String roomNumber ;
    private Double roomPrice ;
    private RoomType roomType;

    public Room(String roomNumber, Double roomPrice, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString()
    {
        return this.getRoomNumber() + " - "+ this.getRoomType() + " - " + this.getRoomPrice() + "$" ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    public static boolean checkRoomNumberAvailability(String roomNumber)
    {
        if(ReservationService.getInstanceOfReservationService().getAllRooms().containsKey(roomNumber))
            return true ;
        else
            return false ;
    }
}
