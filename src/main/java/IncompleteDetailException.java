public class IncompleteDetailException extends DukeException {
    public IncompleteDetailException(String missing) {
        super(missing + " is missing");
    }
}
