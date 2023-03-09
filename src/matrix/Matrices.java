package matrix;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

public final class Matrices {
	
	private Matrices() {
		
	}

	public static void fill(Matrix matrix, InputStream source) throws MatrixException {
		if(matrix == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		try(Scanner scnr = new Scanner(source)) {
			for(int i = 0; i < vs; i++) {
				for(int j = 0; j < hs; j++) {
					matrix.setValue(scnr.nextDouble(), i, j);
				}
			}
		}
	}
	
	public static void randomFill(Matrix matrix, int minValue, int maxValue) throws MatrixException {
		if(matrix == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		Random rand = new Random();
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				matrix.setValue(rand.ints(minValue, maxValue).findFirst().getAsInt(), i, j);
			}
		}
	}
	
	public static Matrix add(Matrix matrix1, Matrix matrix2) throws MatrixException {
		if(matrix1 == null || matrix2 == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int vs = matrix1.getVerticalSize();
		int hs = matrix1.getHorizontalSize();
		if(vs != matrix2.getVerticalSize() || hs != matrix2.getHorizontalSize()) {
			throw new MatrixException(MatrixException.UNEQUAL_SIZE);
		}
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(matrix1.getValue(i, j) + matrix2.getValue(i, j), i, j);
			}
		}
		return result;
	}
	
	public static Matrix subtract(Matrix matrix1, Matrix matrix2) throws MatrixException {
		if(matrix1 == null || matrix2 == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int vs = matrix1.getVerticalSize();
		int hs = matrix1.getHorizontalSize();
		if(vs != matrix2.getVerticalSize() || hs != matrix2.getHorizontalSize()) {
			throw new MatrixException(MatrixException.UNEQUAL_SIZE);
		}
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(matrix1.getValue(i, j) - matrix2.getValue(i, j), i, j);
			}
		}
		return result;
	}
	
	public static Matrix multiply(Matrix matrix, double multiplier) throws MatrixException {
		if(matrix == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(matrix.getValue(i, j) * multiplier, i, j);
			}
		}
		return result;
	}
	
	public static Matrix multiply(Matrix matrix1, Matrix matrix2) throws MatrixException {
		if(matrix1 == null || matrix2 == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int n = matrix1.getHorizontalSize();
		if(n != matrix2.getVerticalSize()) {
			throw new MatrixException(MatrixException.UNEQUAL_COLUMN_ROW_COUNT);
		}
		int vs = matrix1.getVerticalSize();
		int hs = matrix2.getHorizontalSize();
		double sum;
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				sum = 0.0;
				for(int k = 0; k < n; k++) {
					sum += matrix1.getValue(i, k) * matrix2.getValue(k, j);
				}
				result.setValue(sum, i, j);
			}
		}
		return result;
	}
	
	public static Matrix transpose(Matrix matrix) throws MatrixException {
		if(matrix == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int vs = matrix.getHorizontalSize();
		int hs = matrix.getVerticalSize();
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(matrix.getValue(j, i), i, j);
			}
		}
		return result;
	}
	
	public static Matrix getUnit(int size) throws MatrixException {
		Matrix result = new Matrix(size, size);
		for(int i = 0; i < size; i++) {
			result.setValue(1.0, i, i);
		}
		return result;
	}
	
	public static double det(Matrix matrix) throws MatrixException {
		if(matrix == null) {
			throw new MatrixException(MatrixException.NULL_INSTANCE);
		}
		
		int hs = matrix.getHorizontalSize();
		if(matrix.getVerticalSize() != hs) {
			throw new MatrixException(MatrixException.NOT_SQUARE);
		}
		double det = 0.0;
		for(int j = 0; j < hs; j++) {
			det += matrix.getValue(0, j) * A(matrix, 0, j);
		}
		return det;
	}
	
	private static double A(Matrix matrix, int i, int j) throws MatrixException {
		return Math.pow(-1.0, i + j + 2.0) * M(matrix, i, j);
	}
	
	private static double M(Matrix matrix, int i, int j) throws MatrixException {
		if(matrix.getVerticalSize() == 1) {
			return 1.0;
		}
		int vs = matrix.getVerticalSize() - 1;
		int hs = matrix.getHorizontalSize() - 1;
		Matrix result = new Matrix(vs, hs);
		for(int ri = 0, k = 0; ri < vs; ri++, k++) {
			if(k == i) {
				k++;
			}
			for(int rj = 0, l = 0; rj < hs; rj++, l++) {
				if(l == j) {
					l++;
				}
				result.setValue(matrix.getValue(k, l), ri, rj);
			}
		}
		return det(result);
	}
	
}