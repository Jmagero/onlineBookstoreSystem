package jm.onlineBookstoreSystem.exceptional;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
