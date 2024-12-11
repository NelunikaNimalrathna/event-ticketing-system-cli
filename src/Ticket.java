public class Ticket {
    //counter to generate the ticket ID S
    private static int counter = 1;
    //ticket id
    private final int id;

    public Ticket() {
        this.id = counter++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ticket id=" + id;
    }
}
