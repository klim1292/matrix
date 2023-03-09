package matrix;

public class MatrixException extends Exception {

	private static final long serialVersionUID = -4421067597250826033L;
	public static final String INVALID_SIZE = "Invalid matrix size. Minimum size is 1x1";
	public static final String NULL_INSTANCE = "The passed instance is null";
	public static final String UNEQUAL_SIZE = "Unequal size of matrices";
	public static final String UNEQUAL_COLUMN_ROW_COUNT = "Column count of the first matrix is unequal to row count of the second matrix";
	public static final String NOT_SQUARE = "The matrix is not square";
	public static final String DEGENERATE = "The matrix is degenerate";
	
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