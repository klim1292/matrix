package by.fksis.matrix;

import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public final class Matrices {
	
	private static final double EPS = 1.0E-9;
	
	private Matrices() {
		
	}
	
	public static void fill(Matrix matrix) {
		Objects.requireNonNull(matrix);
		
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		@SuppressWarnings("resource")
		Scanner scnr = new Scanner(System.in);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				System.out.printf("[%d][%d]=", i, j);
				matrix.setValue(scnr.nextDouble(), i, j);
			}
		}
	}
	
	public static void randomFill(Matrix matrix, double minValue, double maxValue) {
		Objects.requireNonNull(matrix);
		
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				matrix.setValue(ThreadLocalRandom.current().nextDouble(minValue, maxValue), i, j);
			}
		}
	}
	
	public static Matrix add(Matrix matrix1, Matrix matrix2) {
		Objects.requireNonNull(matrix1);
		Objects.requireNonNull(matrix2);
		
		int vs = matrix1.getVerticalSize();
		int hs = matrix1.getHorizontalSize();
		if(vs != matrix2.getVerticalSize() || hs != matrix2.getHorizontalSize()) {
			throw new MatrixException("Unequal size of matrices");
		}
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(matrix1.getValue(i, j) + matrix2.getValue(i, j), i, j);
			}
		}
		return result;
	}
	
	public static Matrix subtract(Matrix matrix1, Matrix matrix2) {
		Objects.requireNonNull(matrix1);
		Objects.requireNonNull(matrix2);
		
		int vs = matrix1.getVerticalSize();
		int hs = matrix1.getHorizontalSize();
		if(vs != matrix2.getVerticalSize() || hs != matrix2.getHorizontalSize()) {
			throw new MatrixException("Unequal size of matrices");
		}
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(matrix1.getValue(i, j) - matrix2.getValue(i, j), i, j);
			}
		}
		return result;
	}
	
	public static Matrix multiply(Matrix matrix, double multiplier) {
		Objects.requireNonNull(matrix);
		
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
	
	public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
		Objects.requireNonNull(matrix1);
		Objects.requireNonNull(matrix2);
		
		int n = matrix1.getHorizontalSize();
		if(n != matrix2.getVerticalSize()) {
			throw new MatrixException("Column count of the first matrix is unequal to row count of the second matrix");
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
	
	public static Matrix transpose(Matrix matrix) {
		Objects.requireNonNull(matrix);
		
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
	
	public static Matrix unit(int size) {
		Matrix result = new Matrix(size, size);
		for(int i = 0; i < size; i++) {
			result.setValue(1.0, i, i);
		}
		return result;
	}
	
	public static double det(Matrix matrix) {
		Objects.requireNonNull(matrix);
		
		int hs = matrix.getHorizontalSize();
		if(matrix.getVerticalSize() != hs) {
			throw new MatrixException("The matrix is not square");
		}
		double det = 0.0;
		for(int j = 0; j < hs; j++) {
			det += matrix.getValue(0, j) * A(matrix, 0, j);
		}
		return det;
	}
	
	public static Matrix invert(Matrix matrix) {
		double det = det(matrix);
		if(det == 0.0) {
			throw new MatrixException("The matrix is degenerate");
		}
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(A(matrix, i, j) / det, j, i);
			}
		}
		return result;
	}
	
	public static Matrix pow(Matrix matrix, int n) {
		Objects.requireNonNull(matrix);
		
		int vs = matrix.getVerticalSize();
		if(vs != matrix.getHorizontalSize()) {
			throw new MatrixException("The matrix is not square");
		}
		if(n == 0) {
			return unit(vs);
		}
		Matrix result = matrix;
		for(int i = 1; i < Math.abs(n); i++) {
			result = multiply(result, matrix);
		}
		return (n > 0) ? result : invert(result);
	}
	
	public static int rank(Matrix matrix) {
		Objects.requireNonNull(matrix);
		
		try {
			matrix = matrix.clone();
		} catch (CloneNotSupportedException e) {
			throw new MatrixException(e);
		}
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		int rank = Math.max(vs, hs);
		boolean[] line = new boolean[vs];
		for(int j = 0; j < hs; j++) {
			int i;
			for(i = 0; i < vs; i++) {
				if(!line[i] && Math.abs(matrix.getValue(i, j)) > EPS) {
					break;
				}
			}
			if(i == vs) {
				rank--;
			} else {
				line[i] = true;
				transformation(matrix, i, j);
			}
		}
		return rank;
	}
	
	private static void transformation(Matrix matrix, int i, int j) {
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();		
		for (int l = j + 1; l < hs; l++) {
			matrix.setValue(matrix.getValue(i, l) / matrix.getValue(i, j),  i, l);
		}
		for (int k = 0; k < vs; k++) {
			if (k != i && Math.abs(matrix.getValue(k, j)) > EPS) {
				for (int l = j + 1; l < hs; l++) {
					matrix.setValue(matrix.getValue(k, l) - matrix.getValue(i, l) * matrix.getValue(k, j), k, l);
				}
			}
		}
	}
	
	private static double A(Matrix matrix, int i, int j) {
		return Math.pow(-1.0, i + j + 2.0) * M(matrix, i, j);
	}
	
	private static double M(Matrix matrix, int i, int j) {
		if(matrix.getVerticalSize() == 1) {
			return 1.0;
		}
		int tvs = matrix.getVerticalSize() - 1;
		int ths = matrix.getHorizontalSize() - 1;
		Matrix temp = new Matrix(tvs, ths);
		for(int ti = 0, k = 0; ti < tvs; ti++, k++) {
			if(k == i) {
				k++;
			}
			for(int tj = 0, l = 0; tj < ths; tj++, l++) {
				if(l == j) {
					l++;
				}
				temp.setValue(matrix.getValue(k, l), ti, tj);
			}
		}
		return det(temp);
	}
	
}