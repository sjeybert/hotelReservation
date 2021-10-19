package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        setEmail(email);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        if(isValidEmail(email)) {
            this.email = email;
        }
        else
        {
            throw new IllegalArgumentException("Invalid Email");
        }
    }

    public static boolean isValidEmail(String email)
    {
        if(email != null && !email.isEmpty()) {
            String emailRegex = "^(.+)@(.+).(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);
            return pattern.matcher(email).matches();
        }
        else
        {
            return false ;
        }
    }

    @Override
    public String toString() {
        return  firstName + " - " + lastName + " - " + email ;
    }
}
