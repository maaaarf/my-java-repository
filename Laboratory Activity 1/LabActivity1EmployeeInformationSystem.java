// Importing the scanner module in order to get inputs from the user
import java.util.Scanner;

public class LabActivity1EmployeeInformationSystem {
    
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
        System.out.println("Employee age: \t\t " + employeeAge + " " + "years old");
        System.out.println("Years to retirement: \t" + " " + retirementAge + " " + "years");

        Float dailySalary = (float) (Math.round(hoursWorked * hourlyWage)); // These codes calculate the daily, weekly, monthly, gross and net yearly salary
        Float weeklySalary = (float) (Math.round(dailySalary * 5)); // they all have (float) because 'Math.round()' outputs out int but since we used float on all of em we just force it to output float using (float)
        Float monthlySalary = (float) (Math.round(weeklySalary * 4));
        Float grossYearlySalary = (float) (Math.round(monthlySalary * 12));

        Float percentage = 0.32f; // I separated the percent in this because java considers '0.32' as a double and cannot compute it directly
        Float percentOfGYS = (Math.abs(percentage * grossYearlySalary)); // this gets the percent of the gross yearly salary
        Float netYearlySalary = (Math.abs(grossYearlySalary - percentOfGYS) - 1500 - 0.01f); // then this gets the final net yearly salary
        // I added '-0.01' cuz the output always comes up an additional 0.01
        System.out.printf("Daily salary:            PHP %.2f\n", dailySalary); // The '%.2f' determines the maximum amount of decimal places that the computer outputs
        System.out.printf("Weekly salary:           PHP %.2f\n", weeklySalary);
        System.out.printf("Monthly salary:          PHP %.2f\n", monthlySalary);
        System.out.printf("Gross yearly salary:     PHP %.2f\n", grossYearlySalary);
        System.out.printf("Net yearly salary:       PHP %.2f\n", netYearlySalary);

        newScanner.close(); // It is important to close the module for the system to clean up data and make it ready for future use (if the scanner is to be used again)
    }
}