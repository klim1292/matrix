package by.fksis.matrix;

public class MatrixException extends RuntimeException {

	private static final long serialVersionUID = 3355809114828827848L;

	public MatrixException() {
		
	}
	
	public MatrixException(String message) {
		super(message);
	}
	
	public MatrixException(Throwable couse) {
		super(couse);
	}
	
	public MatrixException(String message, Throwable couse) {
		super(message, couse);
	}
	
}