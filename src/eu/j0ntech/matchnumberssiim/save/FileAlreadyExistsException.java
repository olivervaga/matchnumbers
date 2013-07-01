package eu.j0ntech.matchnumberssiim.save;

/**
 * Use homemade exception, because Java 6 doesn't have this exception.
 * @author oliver
 *
 */
public class FileAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private static final String message = "File already exists";
	
	public FileAlreadyExistsException() {
		super(message);
	}

}
