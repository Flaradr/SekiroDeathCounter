package exception;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
