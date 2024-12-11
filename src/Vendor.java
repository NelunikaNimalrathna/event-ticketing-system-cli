import java.util.Random;

public class Vendor implements Runnable {
    private final TicketPool ticketPool;

    public Vendor(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (!ticketPool.isFinished()) {
            try {
                Ticket ticket = new Ticket();
                ticketPool.addTicket(ticket);
                Thread.sleep(100); // Simulate ticket release rate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Vendor finished adding tickets.");
    }

}
