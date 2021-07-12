package exceptions;

public class CategoryNotFoundException extends Exception {
    private static final long serialVersionUID = -8794036497233630091L;

    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String msg) {
        super(msg);
    }
}
