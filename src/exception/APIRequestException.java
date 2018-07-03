package exception;

public class APIRequestException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public APIRequestException() {
		super("An Error occured while serving your API Request");
		// TODO Auto-generated constructor stub
	}

	public APIRequestException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public APIRequestException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public APIRequestException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public APIRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
