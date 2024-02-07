package by.fksis.matrix;

import java.util.Arrays;

public class Matrix implements Cloneable {
	
	private double[][] m;
	
	public Matrix(int verticalSize, int horizontalSize) {
		if(verticalSize < 1 || horizontalSize < 1) {
			throw new IllegalArgumentException();
		}
		m = new double[verticalSize][horizontalSize];
	}
	
	public Matrix(double[][] m) {
		if(m == null || m.length < 1 || Arrays.stream(m).anyMatch(row -> row.length < 1 || row.length != m[0].length)) {
			throw new IllegalArgumentException();
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
		if(!isIndexesRange(i, j)) {
			throw new ArrayIndexOutOfBoundsException();
		}
		return m[i][j];
	}
	
	public void setValue(double value, int i, int j) {
		if(!isIndexesRange(i, j)) {
			throw new ArrayIndexOutOfBoundsException();
		}
		m[i][j] = value;
	}
	
	private boolean isIndexesRange(int i, int j) {
		return (i >= 0 && i < m.length) && (j >= 0 && j < m[0].length);
	}
	
	@Override
	public Matrix clone() throws CloneNotSupportedException {
		Matrix matrix = (Matrix)super.clone();
		matrix.m = m.clone();
		for(int i = 0; i < matrix.m.length; i++) {
			matrix.m[i] = matrix.m[i].clone();
		}
		return matrix;
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