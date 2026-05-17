// EXCEPTION HANDLING: Custom exception for parking plaza errors
public class PlazaFullException extends Exception {
    public PlazaFullException() {
        super("Parking Plaza is full! No available slots at the moment.");
    }
    
    public PlazaFullException(String message) {
        super(message);
    }
    
    public PlazaFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
