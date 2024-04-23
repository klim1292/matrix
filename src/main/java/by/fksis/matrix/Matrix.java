package by.fksis.matrix;

import java.util.Arrays;

public class Matrix {
	
	private double[][] m;
	
	public Matrix(int verticalSize, int horizontalSize) {
		if(verticalSize < 1 || horizontalSize < 1) {
			throw new IllegalArgumentException();
		}
		m = new double[verticalSize][horizontalSize];
	}
	
	public Matrix(double[][] m) {
		if(m == null || m.length < 1 || m[0] == null || m[0].length < 1) {
			throw new IllegalArgumentException();
		}
		for(int i = 1; i < m.length; i++) {
			if(m[i] == null || m[i].length != m[0].length) {
				throw new IllegalArgumentException();
			}
		}
		this.m = new double[m.length][m[0].length];
		for(int i = 0; i < m.length; i++) {
			this.m[i] = Arrays.copyOf(m[i], m[i].length);
		}
	}
	
	public Matrix(Matrix matrix) {
		this(matrix.m);
	}
	
	public int getVerticalSize() {
		return m.length;
	}
	
	public int getHorizontalSize() {
		return m[0].length;
	}
	
	public double getValue(int i, int j) {
		if(!isInBounds(i, j)) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return m[i][j];
	}
	
	public void setValue(int i, int j, double value) {
		if(!isInBounds(i, j)) {
			throw new ArrayIndexOutOfBoundsException();
		}
		m[i][j] = value;
	}
	
	public boolean isZero() {
		return Arrays.stream(m).flatMapToDouble(Arrays::stream).allMatch(v -> Double.compare(v, 0.0) == 0);
	}
	
	private boolean isInBounds(int i, int j) {
		return (i >= 0 && i < m.length) && (j >= 0 && j < m[0].length);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null || obj.getClass() != getClass()) {
			return false;
		}
		Matrix matrix = (Matrix)obj;
		return Arrays.deepEquals(m, matrix.m);
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(m);
	}
	
	@Override
	public String toString() {
		StringBuilder strMatrix = new StringBuilder("Matrix " + m.length + "x" + m[0].length + ":\n");
		for(double[] row : m) {
			for(double value : row) {
				strMatrix.append(String.format("%8.3f", value));
			}
			strMatrix.append('\n');
		}
		return strMatrix.toString();
	}
	
}