import java.util.Scanner;

public class ITTicketSystem {

    static String[] caseDescriptions = new String[5];
    static String[] urgentCases = new String[5];
    static String[] caseStatus = new String[5];
    static int ticketCount = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== IT TICKETING SYSTEM =====");
            System.out.println("1. Add ticket");
            System.out.println("2. Update ticket status");
            System.out.println("3. Show all tickets");
            System.out.println("4. Generate report");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = input.nextInt();
            input.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    addTicket(input);
                    break;
                case 2:
                    updateTicketStatus(input);
                    break;
                case 3:
                    showTickets();
                    break;
                case 4:
                    generateReport();
                    break;
                case 5:
                    System.out.println("I'm bouncing fam, adios!");
                    return;
                default:
                    System.out.println("Invalid choice, please try again!");
            }
        }
    }

    public static void addTicket(Scanner input) {
        if (ticketCount >= 5) {
            System.out.println("Max number of tickets reached!");
        } else {
            System.out.print("Enter issue description: ");
            String desc = input.nextLine();

            System.out.print("Enter urgency level (Low/Medium/High): ");
            String urgency = input.nextLine();

            caseDescriptions[ticketCount] = desc;
            urgentCases[ticketCount] = urgency;
            caseStatus[ticketCount] = "Pending";
            ticketCount++;

            System.out.println("Ticket successfully created!");
        }
    }

    public static void updateTicketStatus(Scanner input) {
        if (ticketCount == 0) {
            System.out.println("No tickets to update.");
        } else {
            System.out.println("\n--- All Tickets ---");
            for (int i = 0; i < ticketCount; i++) {
                System.out.println((i + 1) + ". " + "[" + urgentCases[i] + "]" + " " + caseDescriptions[i] + " - " + "Status: " + caseStatus[i]);
            }

            System.out.print("Enter ticket number to update: ");
            int ticketNum = input.nextInt();
            input.nextLine(); // consume newline

            if (ticketNum < 1 || ticketNum > ticketCount) {
                System.out.println("Invalid ticket number.");
            } else {
                System.out.print("Enter new status (In progress/Resolved): ");
                String newStatus = input.nextLine();
                caseStatus[ticketNum - 1] = newStatus;
                System.out.println("Ticket status updated!");
            }
        }
    }

    public static void showTickets() {
        if (ticketCount == 0) {
            System.out.println("No tickets created.");
        } else {
            System.out.println("\n--- All Tickets ---");
            for (int i = 0; i < ticketCount; i++) {
                System.out.println((i + 1) + ". " + "[" + urgentCases[i] + "]" + " " + caseDescriptions[i] + " - " + "Status: " + caseStatus[i]);
            }
        }
    }

    public static void generateReport() {
        int pendingCount = 0;
        int resolvedCount = 0;

        for (int i = 0; i < ticketCount; i++) {
            if (caseStatus[i].equalsIgnoreCase("Pending")) {
                pendingCount++;
            } else if (caseStatus[i].equalsIgnoreCase("Resolved")) {
                resolvedCount++;
            }
        }

        System.out.println("\n--- Ticket Report ---");
        System.out.println("Total tickets: " + ticketCount);
        System.out.println("Pending tickets: " + pendingCount);
        System.out.println("Resolved tickets: " + resolvedCount);
    }
}