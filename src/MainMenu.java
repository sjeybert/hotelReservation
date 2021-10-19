import api.HotelResource;
import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static Scanner scanner = new Scanner(System.in);
    static int userInput;

    static void init() {
        String mainMenu = "1. Find and reserve a Room\n2. See my reservations\n3. Create an account\n4. Admin\n5. Exit";
        while (userInput != 5) {
            System.out.println(mainMenu);
            try {
                userInput = Integer.valueOf(scanner.nextLine());

                switch (userInput) {
                    case 1: {
                        Collection<IRoom> availableRooms = new ArrayList<>();
                        boolean isValidDate = false;
                        boolean isValidRoom = false;
                        boolean isValidCheckInDate = false;
                        boolean isValidCheckOutDate = false;
                        String checkInString = null;
                        String checkOutString = null;
                        Date checkInDate = null;
                        Date checkOutDate = null;
                        int userPreferredRoomNumber = -1;
                        String emailID = null;
                        Customer customer = null;
                        IRoom customerPreferredRoom = null;

                        try {
                            while (!isValidCheckInDate) {

                                System.out.println("Enter Check-In Date dd/mm/yyyy example 25/01/2022");
                                checkInString = scanner.nextLine();
                                if (isAValidDateFormat(checkInString)) {
                                    isValidCheckInDate = true;
                                    checkInDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkInString);
                                }

                            }
                            while (!isValidCheckOutDate) {
                                System.out.println("Enter Check-Out Date dd/mm/yyyy example 30/01/2022");
                                checkOutString = scanner.nextLine();
                                if (isAValidDateFormat(checkOutString)) {
                                    isValidCheckOutDate = true;
                                    checkOutDate = new SimpleDateFormat("dd/MM/yyyy").parse(checkOutString);

                                }

                            }
                            if (checkOutDate.before(checkInDate)) {
                                System.out.println("Check-Out Date should come after Check-In date");
                                break;
                            }
                            availableRooms = ReservationService.findRooms(checkInDate, checkOutDate);
                            if (availableRooms.size() == 0) {
                                System.out.println("No matches found");
                                // Using calendar
                                Calendar cal = Calendar.getInstance();
                                // Add 7 days from checkIn date
                                cal.setTime(checkInDate);
                                cal.add(Calendar.DAY_OF_MONTH, 7);
                                // Update checkIn date
                                checkInDate = cal.getTime() ;
                                // Add 7 days from checkOut date
                                cal.setTime(checkOutDate);
                                cal.add(Calendar.DAY_OF_MONTH, 7);
                                // Update checkIn date
                                checkOutDate = cal.getTime() ;

                                availableRooms = ReservationService.findRooms(checkInDate, checkOutDate);
                                System.out.println("##IN-" + checkInDate + " OUT-" + checkOutDate + " AVAI- " + availableRooms);
                                if (availableRooms.size() == 0) {
                                    System.out.println("No Recommendation found");
                                    break;
                                }
                                else
                                {
                                    System.out.println("Recommended rooms, checkIn date - " + checkInDate + " | checkOut date - " + checkOutDate);
                                }
                            }
                            System.out.println("Available rooms are : \n" + availableRooms);

                            while (userPreferredRoomNumber < 0) {
                                try {
                                    System.out.println("Select room by entering room number from the available list");
                                    userPreferredRoomNumber = Integer.valueOf(scanner.nextLine());

                                    for (IRoom room : availableRooms) {
                                        if (Integer.valueOf(room.getRoomNumber()) == userPreferredRoomNumber) {
                                            isValidRoom = true;
                                            customerPreferredRoom = room;
                                        }
                                    }
                                    if (!isValidRoom) {
                                        System.out.println("Invalid Room Number, Try again");
                                        userPreferredRoomNumber = -1;
                                    } else {

                                        while (!Customer.isValidEmail(emailID)) {
                                            System.out.println("Enter Email ID");
                                            emailID = scanner.nextLine();
                                            if (!Customer.isValidEmail(emailID)) {
                                                System.out.println("Invalid Email ID, Try again");
                                            }
                                        }
                                        if (CustomerService.customers.containsKey(emailID)) {
                                            customer = CustomerService.getCustomer(emailID);
                                            if(customer != null && customerPreferredRoom != null && checkInDate != null && checkOutDate != null) {
                                                System.out.println("***SUCCESS***");
                                                System.out.println(ReservationService.reserveRoom(customer, customerPreferredRoom, checkInDate, checkOutDate));
                                            }
                                            else
                                            {
                                                System.out.println("Failed! Invalid data");
                                            }
                                        } else {
                                            System.out.println("Account not found!");
                                            break;
                                        }
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println("ERROR : Invalid Entry" );
                                    userPreferredRoomNumber = -1 ;
                                }
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                            System.out.println("Invalid Date Format, Try again");
                        } finally {
                            break;
                        }
                    }
                    case 2: {
                        String emailID = null;
                        Customer customer = null;
                        System.out.println("Enter Email ID");
                        emailID = scanner.nextLine();
                        if (Customer.isValidEmail(emailID)) {
                            if (CustomerService.customers.containsKey(emailID)) {
                                customer = CustomerService.getCustomer(emailID);
                                if (ReservationService.getCustomersReservation(customer) == null) {
                                    System.out.println("No data found");
                                } else {
                                    System.out.println(ReservationService.getCustomersReservation(customer));
                                }
                            } else {
                                System.out.println("Account not found!");
                            }
                        } else {
                            System.out.println("Invalid Email ID, Try again");
                        }
                        break;
                    }
                    case 3: {
                        boolean isInvalidEmail = true;

                        String firstName = null;
                        while (firstName == null || firstName.isEmpty()) {
                            System.out.println("Enter First name");
                            firstName = scanner.nextLine();
                            if (firstName == null || firstName.isEmpty()) {
                                System.out.println("Name should not be left blank");
                            }
                        }
                        String lastName = null;
                        while (lastName == null || lastName.isEmpty()) {
                            System.out.println("Enter Last name");
                            lastName = scanner.nextLine();
                            if (lastName == null || lastName.isEmpty()) {
                                System.out.println("Name should not be left blank");
                            }
                        }
                        String email;
                        while (isInvalidEmail) {
                            System.out.println("Enter Email ID");
                            email = scanner.nextLine();
                            if (Customer.isValidEmail(email)) {
                                isInvalidEmail = false;
                                createAccount(firstName, lastName, email);
                            } else {
                                System.out.println("Invalid Email, Try again!");
                            }

                        }
                        break;
                    }
                    case 4: {
                        AdminMenu.init(scanner);
                        break;
                    }
                    case 5: {
                        userInput = 5;
                        break;
                    }
                    default:
                        System.out.println("\nInvalid option, Try again");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("ERROR : Invalid Entry, Try again");
            }
        }

    }

    private static void createAccount(String firstName, String lastName, String email) {
        HotelResource.createACustomer(email, firstName, lastName);
    }

    private static boolean isAValidDateFormat(String date) {
        boolean isValid = false;
        Date currentDate = null;
        Date formattedDate = null;

        try {
            currentDate = sdf.parse(sdf.format(new Date()));
            String regex = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
            Pattern pattern = Pattern.compile(regex);
            formattedDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            if (pattern.matcher(date).matches() && formattedDate.compareTo(currentDate) >= 0) {
                isValid = true;
            } else {
                isValid = false;
                throw new RuntimeException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid Date");

        }
        return isValid;

    }
}
