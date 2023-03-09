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
	
}