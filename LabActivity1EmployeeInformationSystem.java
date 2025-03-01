// Importing the scanner module in order to get inputs from the user
import java.util.Scanner;

public class LabActivity1EmployeeInformationSystem {
    
    public static void main(String[] args) {
        // Defining the scanner module to be used later on
        Scanner newScanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = newScanner.nextLine();
        // These first two blocks of code get the user's first and last names
        System.out.print("Enter your last name: ");
        String lastName = newScanner.nextLine();

        System.out.print("Enter your age: ");
        int employeeAge = newScanner.nextInt(); // Since we are asking for the user's age, we assign 'int' to this variable

        System.out.print("How many hours do you work in a day?: ");
        Float hoursWorked = newScanner.nextFloat(); // Same goes on the hours worked and the hourly wage, but instead we use 'Float'

        System.out.print("What is your hourly wage in PHP?: ");
        Float hourlyWage = newScanner.nextFloat(); // Float was used on both of these to get the exact amount of hours and hourly wage


        // This part of the code block contains and processes the concatenation of all the collected information of the user
        System.out.println("\n---Employee Information---");
        System.out.println("Employee name: " + firstName + " " + lastName);
        System.out.println("Employee age: " + employeeAge);
        
        Float dailySalary = hoursWorked * hourlyWage; // This simply calculates the daily salary of the employee

        System.out.printf("Daily salary:  PHP %.2f\n", dailySalary); // The '%.2f' determines the maximum amount of decimal places that the computer outputs

        newScanner.close(); // It is important to close the module for the system to clean up data and make it ready for future use (if the scanner is to be used again)
    }
    
}