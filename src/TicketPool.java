import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private Queue<Ticket> ticketQueue = new LinkedList<>();
    private final int maxCapacity;
    private int totalTicketsAdded = 0;
    private int totalTicketsRemoved = 0;
    private final int totalTickets;

    public TicketPool(int maxCapacity, int totalTickets) {
        this.maxCapacity = maxCapacity;
        this.totalTickets = totalTickets;
    }
//To adda ticket to the pool
    public synchronized void addTicket(Ticket ticket) {
        while (ticketQueue.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        if (totalTicketsAdded < totalTickets) {
            ticketQueue.add(ticket);
            totalTicketsAdded++;
            System.out.println("Vendor added ticket ID: " + ticket.getId() + ". Current pool size: " + ticketQueue.size());
            notifyAll(); // Notify waiting customers
        }

        if (totalTicketsAdded >= totalTickets) {
            notifyAll(); // To Ensure all threads wake up to finish
        }
    }

//to remove a ticket from a pool
    public synchronized Ticket removeTicket() {
        while (ticketQueue.isEmpty() && totalTicketsRemoved < totalTickets) {
            try {
                wait(); // Customer waits if pool is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }

        if (!ticketQueue.isEmpty()) {
            Ticket ticket = ticketQueue.poll();
            totalTicketsRemoved++;
            System.out.println("Customer purchased ticket ID: " + ticket.getId() + ". Remaining in pool: " + ticketQueue.size());
            notifyAll(); // Notify waiting vendors
            return ticket;
        }

        if (totalTicketsRemoved >= totalTickets) {
            notifyAll();
        }

        return null;
    }

//to check the ticketpool ha completed all ticket operations

    public synchronized boolean isFinished() {
        return totalTicketsAdded >= totalTickets && totalTicketsRemoved >= totalTickets;
    }
}
