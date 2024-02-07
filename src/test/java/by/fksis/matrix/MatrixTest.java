package by.fksis.matrix;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class MatrixTest {
	
	static Matrix m1;
	static Matrix m2;
	static Matrix wrong;
	
	@BeforeAll
	static void init() {
		m1 = new Matrix(new double[][]{
			{2.0, 2.0, 2.0},
			{1.0, 2.0, 1.0},
			{0.0, 2.0, 1.0}});
		m2 = new Matrix(new double[][] {
			{3.0, 2.0, 1.0},
			{1.0, 1.0, 1.0},
			{1.0, 1.0, 1.0}});
		wrong = new Matrix(new double[][] {
			{5.0, 4.0, 3.0},
			{2.0, 3.0, 2.0}});
	}
	
	@Test
	void testAdd() {
		Matrix result = new Matrix(new double[][] {
			{5.0, 4.0, 3.0},
			{2.0, 3.0, 2.0},
			{1.0, 3.0, 2.0}});
		assertTrue(result.equals(Matrices.add(m1, m2)));
	}
	
	@Test
	void testFailAdd() {
		assertThrows(MatrixException.class, () -> Matrices.add(wrong, m2));
	}
	
	@Test
	void testSubtract() {
		Matrix result = new Matrix(new double[][] {
			{-1.0, 0.0, 1.0},
			{0.0, 1.0, 0.0},
			{-1.0, 1.0, 0.0}});
		assertTrue(result.equals(Matrices.subtract(m1, m2)));
	}
	
	@Test
	void testFailSubtract() {
		assertThrows(MatrixException.class, () -> Matrices.subtract(wrong, m2));
	}
	
	@Test
	void testMultiplyNumber() {
		Matrix result = new Matrix(new double[][] {
			{4.6, 4.6, 4.6},
			{2.3, 4.6, 2.3},
			{0.0, 4.6, 2.3}});		
		assertTrue(result.equals(Matrices.multiply(m1, 2.3)));
	}
	
	@Test
	void testMultiplyMatrix() {
		Matrix result = new Matrix(new double[][] {
			{10.0, 8.0, 6.0},
			{6.0, 5.0, 4.0},
			{3.0, 3.0, 3.0}});
		assertTrue(result.equals(Matrices.multiply(m1, m2)));
	}
	
	@Test
	void testFailMultiplyMatrix() {
		assertThrows(MatrixException.class, () -> Matrices.multiply(m1, wrong));
	}
	
	@Test
	void testTranspose() {
		Matrix result = new Matrix(new double[][] {
			{2.0, 1.0, 0.0},
			{2.0, 2.0, 2.0},
			{2.0, 1.0, 1.0}});
		assertTrue(result.equals(Matrices.transpose(m1)));
	}
	
	@Test
	void testDet() {
		assertEquals(Matrices.det(m1), 2.0);
	}
	
	@Test
	void testFailDet() {
		assertThrows(MatrixException.class, () -> Matrices.det(wrong));
	}
	
	@Test
	void testInvert() {
		Matrix result = new Matrix(new double[][] {
			{0.0, 1.0, -1.0},
			{-0.5, 1.0, -0.0},
			{1.0, -2.0, 1.0}});
		assertTrue(result.equals(Matrices.invert(m1)));
	}
	
	@Test
	void testFailInvert() {
		assertThrows(MatrixException.class, () -> Matrices.invert(m2));
	}
	
	@Test
	void testPow() {
		Matrix result = new Matrix(new double[][] {
			{6.0, 12.0, 8.0},
			{4.0, 8.0, 5.0},
			{2.0, 6.0, 3.0}});
		assertTrue(result.equals(Matrices.pow(m1, 2)));
	}
	
	@Test
	void testRank() {
		assertEquals(Matrices.rank(m1), 3);
	}
	
	@AfterAll
	static void free() {
		m1 = null;
		m2 = null;
		wrong = null;
	}
	
}