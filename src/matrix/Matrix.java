package matrix;

import java.util.Locale;

public class Matrix {
	
	private double[][] m;
	
	public Matrix(int verticalSize, int horizontalSize) throws MatrixException {
		if(verticalSize < 1 || horizontalSize < 1) {
			throw new MatrixException(MatrixException.INVALID_SIZE);
		}
		m = new double[verticalSize][horizontalSize];
	}
	
	public Matrix(double[][] m) throws MatrixException {
		if(m == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		if(m.length < 1 || m[0].length < 1) {
			throw new MatrixException(MatrixException.INVALID_SIZE);
		}
		this.m = m;
	}
	
	public int getVerticalSize() {
		return m.length;
	}
	
	public int getHorizontalSize() {
		return m[0].length;
	}
	
	public double getValue(int i, int j) {
		if(!isIndexesInRange(i, j)) {
			throw new IndexOutOfBoundsException();
		}
		return m[i][j];
	}
	
	public void setValue(double value, int i, int j) {
		if(!isIndexesInRange(i, j)) {
			throw new IndexOutOfBoundsException();
		}
		m[i][j] = value;
	}
	
	private boolean isIndexesInRange(int i, int j) {
		return (i >= 0 && i < m.length) && (j >= 0 && j < m[0].length);
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Matrix " + m.length + "x" + m[0].length + ":\n");
		for(double[] row : m) {
			for(double value : row) {
				result.append(String.format(Locale.US, "%8.3f", value));
			}
			result.append('\n');
		}
		return result.toString();
	}
	
}