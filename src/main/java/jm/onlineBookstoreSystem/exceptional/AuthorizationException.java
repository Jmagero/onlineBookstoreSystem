package jm.onlineBookstoreSystem.exceptional;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
