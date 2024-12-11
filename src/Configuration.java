import java.util.Scanner;

public class Configuration {
    //Total number of tickets to add
    private int totalTickets;
    //Number of tickets released per operation
    private int ticketReleaseRate;
    //Number of tickets retrieved per opration
    private int customerRetrievalRate;
    //maximum pool size of the pool at a time
    private int maxTicketCapacity;

// constructor with parameters to initialize configuration values
    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }
//default constructer
    public Configuration() {

    }

    // Getters to get the values of the defined parameters and return the values
    //setters to set the values for the definedparameters
    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration : {" + "Total Number of Tickets: " + totalTickets + ", Ticket Release Rate: " + ticketReleaseRate + ", Customer Retrieval Rate: " + customerRetrievalRate + ", Max Ticket Capacity: " + maxTicketCapacity + '}';
    }

    // method to display configuration details
    public void displayConfiguration() {
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
    }

}
