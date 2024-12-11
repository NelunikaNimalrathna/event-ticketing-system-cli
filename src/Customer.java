import java.util.Random;

public class Customer implements Runnable {
    //shared ticket pool
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (!ticketPool.isFinished()) {
            try {
                Ticket ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    Thread.sleep(500); // Simulate ticket retrieval rate
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Customer finished ticket purchase.");
    }


}
