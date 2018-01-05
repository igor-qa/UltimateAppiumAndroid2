package Utils;

/**
 * Fatal exception during test initialization
 */
class SetUpException extends RuntimeException {
    SetUpException(String message, Throwable cause) {
        super(message, cause);
    }

    SetUpException(String message) {
        super(message);
    }
}
