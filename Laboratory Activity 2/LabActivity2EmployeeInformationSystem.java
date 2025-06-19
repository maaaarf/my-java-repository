// Importing the scanner module in order to get inputs from the user
import java.util.Scanner;

public class LabActivity2EmployeeInformationSystem {
    
    public static void main(String[] args) {
        // Defining the scanner module to be used later on
        Scanner newScanner = new Scanner(System.in);

        System.out.print("Enter your first name: ");
        String firstName = newScanner.nextLine();
        String upperFirstName = firstName.toUpperCase();
        // These first two blocks of code get the user's first and last names
        System.out.print("Enter your last name: ");
        String lastName = newScanner.nextLine();
        String upperLastName = lastName.toUpperCase();
        // I added the upper variants of the variables so it can output uppercase names later

        System.out.print("Enter your age: ");
        int employeeAge = newScanner.nextInt(); // Since we are asking for the user's age, we assign 'int' to this variable
        int retirementAge = (Math.abs(65-employeeAge)); // this just calculates how many years until the user's retirement age

        System.out.print("How many hours do you work in a day?: ");
        Float hoursWorked = newScanner.nextFloat(); // Same goes on the hours worked and the hourly wage, but instead we use 'Float'

        System.out.print("What is your hourly wage in PHP?: ");
        Float hourlyWage = newScanner.nextFloat(); // Float was used on both of these to get the exact amount of hours and hourly wage
    
        // This part of the code block contains and processes the concatenation of all the collected information of the user
        System.out.println("\n---Employee Information---");
        System.out.println("Employee name: \t\t " + upperLastName + ", " + upperFirstName);
        System.out.println("Employee age: \t\t " + employeeAge + " years old");
        System.out.println("Years to retirement:  \t " + retirementAge + " years");

        Float dailySalary = (float) (Math.round(hoursWorked * hourlyWage)); // These codes calculate the daily, weekly, monthly, gross and net yearly salary
        Float weeklySalary = (dailySalary * 5); // they all have (float) because 'Math.round()' outputs out int but since we used float on all of em we just force it to output float using (float)
        Float monthlySalary = (weeklySalary * 4);
        Float grossYearlySalary = (monthlySalary * 12);
        Float percentOfGYS = (0.32f * grossYearlySalary); // this gets the percent of the gross yearly salary
        Float netYearlySalary = (float) (Math.round((grossYearlySalary - percentOfGYS) * 10)) / 10 - 1500; // then this gets the final net yearly salary
        //I added the multiplication by 10 to make it so that the 'Math.round' rounds the tenth's place of the decimal point so that it evens it out to 0
        // Then back to dividing it by 10 to return the decimal point to its original place.

        System.out.printf("Daily salary:            PHP %.2f\n", dailySalary); // The '%.2f' determines the maximum amount of decimal places that the computer outputs
        System.out.printf("Weekly salary:           PHP %.2f\n", weeklySalary);
        System.out.printf("Monthly salary:          PHP %.2f\n", monthlySalary);
        System.out.printf("Gross yearly salary:     PHP %.2f\n", grossYearlySalary);
        System.out.printf("Net yearly salary:       PHP %.2f\n", netYearlySalary);

        newScanner.close(); // It is important to close the module for the system to clean up data and make it ready for future use (if the scanner is to be used again)
    }
}