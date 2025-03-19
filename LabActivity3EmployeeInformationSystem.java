import java.util.Scanner;

public class LabActivity3EmployeeInformationSystem {
  
  public static void main(String[] args) {
    Scanner newScanner = new Scanner(System.in);

    System.out.print("Enter your first name: ");
    String firstName = newScanner.nextLine();
    String upperFirstName = firstName.toUpperCase();

    System.out.print("Enter your last name: ");
    String lastName = newScanner.nextLine();
    String upperLastName = lastName.toUpperCase();

    System.out.print("Enter your age: ");
    int userAge = newScanner.nextInt();
    int retirementAge = (Math.abs(65-userAge));
    if (userAge < 18) {
      System.out.print("Minors are not allowed. Terminating program.");
      System.exit(0);
    } else if (userAge > 65) {
      System.out.print("Senior citizens are not allowed. Terminating program.");
      System.exit(0);
    } // this part just checks the age requirement and if they are either a minor or a senior citizen, the program terminates

    System.out.print("How many hours do you work in a day?: ");
    Float hoursWorked = newScanner.nextFloat();
      if (hoursWorked > 24) { // this bit of code terminates the program if the hours worked exceeded 24 hours
        System.out.print("Hours worked cannot exceed 24 hours. Terminating program.");
        System.exit(0);
      } else if (hoursWorked == 0) {
        System.out.print("Wrong input on daily work hours. Terminating program.");
        System.exit(0);
      } // then this part checks if the daily work hours entered is equal to 0, then terminates if it returns true

    System.out.print("Hourly wage?: ");
    float hourlyWage = newScanner.nextFloat();

    System.out.print("Enter your role code (1-Manager, 2-Supervisor, 3-Staff, 4-Intern): ");
    int userJobRole = newScanner.nextInt();

    System.out.println("\n---Employee Information---");
    System.out.println("Employee name: \t\t " + upperLastName + ", " + upperFirstName);
    System.out.println("Employee age: \t\t " + userAge + " years old");
    String userRole; // this just declares all the 'userRole' variables to String
    switch (userJobRole) { // this entire switch block checks the user's input to determine their job position
      case 1:
        userRole = "Manager";
        break;
      case 2:
        userRole = "Supervisor";
        break;
      case 3:
        userRole = "Staff";
        break;
      case 4:
        userRole = "Intern";
        break;
      default:
        userRole = "Undefined";
    }
    System.out.println("Position: \t\t " + userRole);
    System.out.println("Years to retirement:  \t " + retirementAge + " years");

    Float dailySalary = (float) (Math.round(hoursWorked * hourlyWage)); // These codes calculate the daily, weekly, monthly, and gross yearly salary
    Float weeklySalary = (dailySalary * 5); // they all have (float) because 'Math.round()' outputs out int but since we used float on all of em we just force it to output float using (float)
    Float monthlySalary = (weeklySalary * 4);
    Float grossYearlySalary = (monthlySalary * 12);
    
    System.out.printf("Daily salary:            PHP %.2f\n", dailySalary); // The '%.2f' determines the maximum amount of decimal places that the computer outputs
    System.out.printf("Weekly salary:           PHP %.2f\n", weeklySalary);
    System.out.printf("Monthly salary:          PHP %.2f\n", monthlySalary);
    System.out.printf("Gross yearly salary:     PHP %.2f\n", grossYearlySalary);


    // this part of the code checks the gross yearly salary if it is more or less than PHP 250,000
    // if it is more than PHP 250,000, it does the 32% tax deduction as well as the government-mandated benefits
    // and if it is less than or equal to PHP 250,000, it will only deduct the government-mandated benefits
    if (grossYearlySalary > 250000) {
      Float percentOfGYS = (0.32f * grossYearlySalary); // this gets the percent of the gross yearly salary
      Float netYearlySalary1 = (float) (Math.round((grossYearlySalary - percentOfGYS) * 10)) / 10 - 1500; // then this gets the final net yearly salary
      System.out.printf("Net yearly salary:       PHP %.2f\n", netYearlySalary1);

    } else if (grossYearlySalary <= 250000) {
      Float netYearlySalary2 = grossYearlySalary - 1500;
      System.out.printf("Net yearly salary:       PHP %.2f\n", netYearlySalary2);
    }  
        
    newScanner.close();
  }
}
