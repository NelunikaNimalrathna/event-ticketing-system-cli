import java.io.*;
import java.util.Scanner;

public class TicketApp {

    public static void main(String[] args) {
        displayLogo();
        Scanner scanner = new Scanner(System.in);
        Configuration config = new Configuration();

        boolean validOption = false; //  to track valid input
        while (!validOption) {
            // Prompt for the main menu options
            System.out.println("\n1-Enter new configuration values");
            System.out.println("2-Load from file");
            System.out.println("Enter option: ");
            String mainOption = scanner.nextLine().trim(); // Trim whitespace
            System.out.println("Main option entered: " + mainOption); // Debug log message

            // Validate the user input
            switch (mainOption) {
                case "1":
                    configureSystem(scanner, config);
                    validOption = true; // Break the loop after valid input
                    break;
                case "2":
                    loadConfigurationFromFile(scanner, config);
                    validOption = true; // Break the loop after valid input
                    break;
                default:
                    System.out.println("Invalid option. Please enter a valid choice."); // Re-prompt
                    break;
            }
        }

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
        displayMenu(scanner, ticketPool, config); // Call the menu function
        scanner.close(); // Close scanner at the end of the program
    }

// method to display the main menu and to handle the operations
    private static void displayMenu(Scanner scanner, TicketPool ticketPool, Configuration config) {
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n1 - Start the System");
            System.out.println("2 - Save to file");
            System.out.println("3 - Exit the System");
            System.out.print("\nEnter option: ");

            String option = scanner.nextLine().trim(); // Trim whitespace

            // Check for empty input
            if (option.isEmpty()) {
                System.out.println("Invalid option. Please try again."); // Handle empty input
                continue; // Skip the rest of the loop and re-prompt
            }

            System.out.println("User entered option: " + option); // Debug log message

            switch (option) {
                case "1":
                    startSystem(ticketPool);
                    break;

                case "2":
                    saveConfigurationToFile(config, "config.txt");
                    break;

                case "3":
                    System.out.println("System stopped.");
                    isRunning = false; // Exit the loop
                    break;

                default:
                    System.out.println("Invalid option. Please try again."); // Only invalid inputs
                    break;
            }
        }
    }

    ///to configure the system by getting the user inputs
    private static void configureSystem(Scanner scanner, Configuration config) {
        System.out.println("Enter the total number of tickets: ");
        int totalTickets = validatePositiveIntInput(scanner);
        config.setTotalTickets(totalTickets);

        System.out.println("Enter the ticket release rate: ");
        int ticketReleaseRate = validatePositiveIntInput(scanner);
        config.setTicketReleaseRate(ticketReleaseRate);

        System.out.println("Enter the customer retrieval rate: ");
        int customerRetrievalRate = validatePositiveIntInput(scanner);
        config.setCustomerRetrievalRate(customerRetrievalRate);

        System.out.println("Enter the maximum ticket capacity: ");
        int maxTicketCapacity = validatePositiveIntInput(scanner);
        config.setMaxTicketCapacity(maxTicketCapacity);

        System.out.println("Configuration loaded successfully.");
        config.displayConfiguration();
    }

    //to load the cnfigurations from a file specified by user
    private static void loadConfigurationFromFile(Scanner scanner, Configuration config) {
        System.out.print("Enter the file name (Ex: configuration.txt): ");
        String filepath = scanner.nextLine().trim();

        if (filepath.isEmpty()) {
            Logger.log("File name cannot be empty. Please try again.");
        } else {
            Logger.log("\nImporting values from " + filepath + "...");
            importConfigurationFromFile(config, filepath);
            Logger.log("\nLoaded configuration: " + config.toString());
        }
    }
//to start the ticketing system with vendor and customer threads
    private static void startSystem(TicketPool ticketPool) {
        System.out.println("Starting the system...");

        Vendor vendor = new Vendor(ticketPool);
        Customer customer = new Customer(ticketPool);

        Thread vendorThread = new Thread(vendor);
        Thread customerThread = new Thread(customer);

        vendorThread.start();
        customerThread.start();

        try {
            vendorThread.join();
            customerThread.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted: " + e.getMessage());
        }
    }

    // a welcome logo for display
    private static void displayLogo() {
        System.out.println("****************************************");
        System.out.println("*                                      *");
        System.out.println("*    Real-Time Event Ticketing System  *");
        System.out.println("*                                      *");
        System.out.println("****************************************");
    }

    //To save the configuration to the file
    public static void saveConfigurationToFile(Configuration config, String baseFilePath) {
        String filePath = baseFilePath;
        int counter = 1;
        while (new File(filePath).exists()) {
            filePath = baseFilePath.replace(".txt", "") + " " + counter + ".txt";
            counter++;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(config.toString());
            System.out.println("Configuration saved to: " + filePath + ". Exit the program to view the file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    //to validate positive integer inputs
    private static int validatePositiveIntInput(Scanner scanner) {
        int input;
        while (true) {
            if (scanner.hasNextInt()) {
                input = scanner.nextInt();
                scanner.nextLine(); // Consume newline after integer
                if (input > 0) break;
                else System.out.println("Please enter a positive number:");
            } else {
                System.out.println("Invalid input. Please enter a positive number:");
                scanner.next(); // Consume invalid input
            }
        }
        return input;
    }

    //to import the configurations from a file
    public static Configuration importConfigurationFromFile(Configuration config, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null && line.startsWith("Configuration : {")) {
                String content = line.replace("Configuration : {", "").replace("}", "").trim();
                String[] keyValuePairs = content.split(",");
                for (String pair : keyValuePairs) {
                    String[] keyValue = pair.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        int value = Integer.parseInt(keyValue[1].trim());
                        switch (key) {
                            case "Total Number of Tickets":
                                config.setTotalTickets(value);
                                break;
                            case "Ticket Release Rate":
                                config.setTicketReleaseRate(value);
                                break;
                            case "Customer Retrieval Rate":
                                config.setCustomerRetrievalRate(value);
                                break;
                            case "Max Ticket Capacity":
                                config.setMaxTicketCapacity(value);
                                break;
                            default:
                                Logger.log("Unknown key: " + key);
                        }
                    }
                }
            } else {
                Logger.log("Invalid configuration format in file.");
            }
        } catch (IOException | NumberFormatException e) {
            Logger.log("Error reading or parsing configuration file: " + e.getMessage());
        }
        return config;
    }
}
