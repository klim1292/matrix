package by.fksis.matrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class Matrices {
	
	private Matrices() {
		
	}
	
	public static void randomFill(Matrix matrix, double minValue, double maxValue) {
		if(matrix == null) {
			throw new IllegalArgumentException();
		}
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				matrix.setValue(i, j, ThreadLocalRandom.current().nextDouble(minValue, maxValue));
			}
		}
	}
	
	public static Matrix add(Matrix matrix1, Matrix matrix2) {
		if(matrix1 == null || matrix2 == null) {
			throw new IllegalArgumentException();
		}
		int vs = matrix1.getVerticalSize();
		int hs = matrix1.getHorizontalSize();
		if(vs != matrix2.getVerticalSize() || hs != matrix2.getHorizontalSize()) {
			throw new MatrixException("Unequal size of matrices");
		}
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(i, j, matrix1.getValue(i, j) + matrix2.getValue(i, j));
			}
		}
		return result;
	}
	
	public static Matrix subtract(Matrix matrix1, Matrix matrix2) {
		if(matrix1 == null || matrix2 == null) {
			throw new IllegalArgumentException();
		}	
		int vs = matrix1.getVerticalSize();
		int hs = matrix1.getHorizontalSize();
		if(vs != matrix2.getVerticalSize() || hs != matrix2.getHorizontalSize()) {
			throw new MatrixException("Unequal size of matrices");
		}
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(i, j, matrix1.getValue(i, j) - matrix2.getValue(i, j));
			}
		}
		return result;
	}
	
	public static Matrix multiply(Matrix matrix, double multiplier) {
		if(matrix == null) {
			throw new IllegalArgumentException();
		}	
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(i, j, matrix.getValue(i, j) * multiplier);
			}
		}
		return result;
	}
	
	public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
		if(matrix1 == null || matrix2 == null) {
			throw new IllegalArgumentException();
		}	
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
				result.setValue(i, j, sum);
			}
		}
		return result;
	}
	
	public static Matrix transpose(Matrix matrix) {
		if(matrix == null) {
			throw new IllegalArgumentException();
		}
		int vs = matrix.getHorizontalSize();
		int hs = matrix.getVerticalSize();
		Matrix result = new Matrix(vs, hs);
		for(int i = 0; i < vs; i++) {
			for(int j = 0; j < hs; j++) {
				result.setValue(i, j, matrix.getValue(j, i));
			}
		}
		return result;
	}
	
	public static Matrix unit(int size) {
		Matrix result = new Matrix(size, size);
		for(int i = 0; i < size; i++) {
			result.setValue(i, i, 1.0);
		}
		return result;
	}
	
	public static double det(Matrix matrix) {
		if(matrix == null) {
			throw new IllegalArgumentException();
		}
		int hs = matrix.getHorizontalSize();
		if(matrix.getVerticalSize() != hs) {
			throw new MatrixException("The matrix is not square");
		}
		if(matrix.isZero()) {
			return 0;
		}
		double det = 0.0;
		for(int j = 0; j < hs; j++) {
			det += matrix.getValue(0, j) * cofactor(matrix, 0, j);
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
				result.setValue(j, i, cofactor(matrix, i, j) / det);
			}
		}
		return result;
	}
	
	public static Matrix pow(Matrix matrix, int n) {
		if(matrix == null) {
			throw new IllegalArgumentException();
		}
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
	
	private static void createCrossedOutComb(List<List<Integer>> combinations, List<Integer> combination, int start, int end, int index) {
		if (index == combination.size()) {
			combinations.add(List.copyOf(combination));
			return;
		}
		for (int i = start; i < end; i++) {
			combination.set(index, i);			
			createCrossedOutComb(combinations, combination, i + 1, end, index + 1);
		}
	}
	
	public static int rank(Matrix matrix) {
		if(matrix == null) {
			throw new IllegalArgumentException();
		}
		if(matrix.isZero()) {
			return 0;
		}
		int vs = matrix.getVerticalSize();
		int hs = matrix.getHorizontalSize();
		int rank = Math.min(vs, hs);
		while(rank > 0) {
			List<List<Integer>> crossedOutRowsComb = new ArrayList<>();
			List<List<Integer>> crossedOutColumnsComb = new ArrayList<>();
			createCrossedOutComb(crossedOutRowsComb, new ArrayList<>(Collections.nCopies(vs - rank, 0)), 0, vs, 0);
			createCrossedOutComb(crossedOutColumnsComb, new ArrayList<>(Collections.nCopies(hs - rank, 0)), 0, hs, 0);			
			for(List<Integer> crossedOutRows : crossedOutRowsComb) {
				for(List<Integer> crossedOutColumns : crossedOutColumnsComb) {
					if(Double.compare(minor(matrix, crossedOutRows, crossedOutColumns), 0.0) != 0) {
						return rank;
					}
				}
			}
			rank--;
		}
		return rank;
	}
	
	private static double cofactor(Matrix matrix, int i, int j) {
		return Math.pow(-1.0, i + j + 2.0) * minor(matrix, List.of(i), List.of(j));
	}
	
	private static double minor(Matrix matrix, List<Integer> crossedOutRows, List<Integer> crossedOutColumns) {
		if(matrix.getVerticalSize() == 1) {
			return 1.0;
		}
		int tempVs = matrix.getVerticalSize() - crossedOutRows.size();
		int tempHs = matrix.getHorizontalSize() - crossedOutColumns.size();
		Matrix temp = new Matrix(tempVs, tempHs);
		Iterator<Integer> itCor = crossedOutRows.iterator();
		int crossedOutRow = itCor.hasNext() ? itCor.next() : -1;
		for(int ti = 0, i = 0; ti < tempVs; ti++, i++) {
			while(i == crossedOutRow) {
				i++;
				if(itCor.hasNext()) {
					crossedOutRow = itCor.next();
				}
			}
			Iterator<Integer> itCoc = crossedOutColumns.iterator();
			int crossedOutColumn = itCoc.hasNext() ? itCoc.next() : -1;
			for(int tj = 0, j = 0; tj < tempHs; tj++, j++) {
				while(j == crossedOutColumn) {
					j++;
					if(itCoc.hasNext()) {
						crossedOutColumn = itCoc.next();
					}
				}
				temp.setValue(ti, tj, matrix.getValue(i, j));
			}
		}
		return det(temp);
	}
	
}