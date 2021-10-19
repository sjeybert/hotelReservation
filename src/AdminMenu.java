import com.sun.tools.javac.Main;
import model.Room;
import model.RoomType;
import service.CustomerService;
import service.ReservationService;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AdminMenu {
    static ReservationService reservationServiceSingeton = ReservationService.getInstanceOfReservationService() ;
    static int adminInput ;
    static String adminMenu = "=========================\n1. See all Customers\n2. See all Rooms\n3. See all Reservations\n4. Add a Room\n5. Back to Main Menu\n=========================";
    static void init(Scanner scanner)
    {
        System.out.println(adminMenu);
        try {
            adminInput = Integer.valueOf(scanner.nextLine());

            switch (adminInput) {
                case 1: {
                    seeAllCustomers();
                    break;
                }
                case 2: {
                    seeAllRooms();
                    break;
                }
                case 3: {
                    reservationServiceSingeton.printAllReservation();
                    break;
                }
                case 4: {
                    addARoom(scanner);
                    break;
                }
                case 5: {
                    MainMenu.init();
                    break;
                }
                default: {
                    System.out.println("\nInvalid option, Try again");
                    init(scanner);
                }

            }
        }
        catch (Exception err)
        {
            System.out.println("ERROR : Invalid entry, Try again");
            init(scanner);
        }
    }

    private static void seeAllRooms() {
        System.out.println(reservationServiceSingeton.getAllRooms().values());
    }

    private static void addARoom(Scanner scanner) {
        boolean isRoomAvailable = true ;
        boolean isInvalidRoomType = true;
        double roomPrice = -1.0 ;
        String roomNumber = null ;
        int roomType = 0 ;
        RoomType type = RoomType.SINGLE;
        String numberRegex = "[0-9]+";
        Pattern pattern = Pattern.compile(numberRegex);
        while(isRoomAvailable)
        {
            while(roomNumber == null || roomNumber.isEmpty() || !pattern.matcher(roomNumber).matches()) {
                System.out.println("Enter Room Number :");
                roomNumber = scanner.nextLine();
                if(roomNumber == null || roomNumber.isEmpty())
                {
                    System.out.println("Room Number shouldn't be left blank");
                }
                if(!pattern.matcher(roomNumber).matches())
                {
                    System.out.println("Invalid room Number, Try again");
                }
            }

            isRoomAvailable = Room.checkRoomNumberAvailability(roomNumber) ;
            if(isRoomAvailable) {
                System.out.println("Room with the given number has already exist, Try the new one");
                roomNumber = scanner.nextLine();
            }

        }

        while(isInvalidRoomType)
        {
        System.out.println("Select Room Type\n1. Single Bed\n2. Double Bed");
        try {
            roomType = Integer.valueOf(scanner.nextLine());
            if(roomType != 1 && roomType != 2) {
                System.out.println("Invalid Entry, Try again!");
            }
            else {
                isInvalidRoomType = false;
            }
        }
        catch (Exception err)
        {
            System.out.println("ERROR : Invalid Entry");
        }

        }

        do{
            System.out.println("Enter room Price");
            try {
                roomPrice = Double.valueOf(scanner.nextLine());
                if (roomPrice < 0) {
                    System.out.println("Invalid amount has entered, Try again");
                }
            }
            catch (Exception err)
            {
                System.out.println("ERROR : Invalid amount has been entered, Try again");
            }
        }
        while(roomPrice < 0) ;
        if(roomNumber != null && roomPrice >= 0) {
            if(roomType == 1 || roomType == 2)
            {
                if(roomType == 1)
                {
                    type = RoomType.SINGLE;
                }
                else if(roomType == 2)
                {
                    type = RoomType.DOUBLE ;
                }
            }
            reservationServiceSingeton.addRoom(new Room(roomNumber, roomPrice, type));
        }

    }


    private static void seeAllCustomers() {
        System.out.println(CustomerService.getInstanceOfCustomerService().getAllCustomer());
    }
}
