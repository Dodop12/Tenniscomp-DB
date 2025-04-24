package tenniscomp.data;

/* This is a runtime exception we define to wrap all the exceptions coming from
*  the DAO objects we're going to define.
*
*  This way we won't have `SQLException`s bubbling up in all other functions.
*/
public final class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DAOException(final String message) {
        super(message);
    }

    public DAOException(final Throwable cause) {
        super(cause);
    }

    public DAOException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
