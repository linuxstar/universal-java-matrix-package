/*
 * Copyright (C) 2008-2015 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.core.calculation;

import java.math.BigDecimal;

import org.ujmp.core.DenseMatrix;
import org.ujmp.core.DenseMatrix2D;
import org.ujmp.core.Matrix;
import org.ujmp.core.SparseMatrix;
import org.ujmp.core.doublematrix.DenseDoubleMatrix2D;
import org.ujmp.core.interfaces.HasColumnMajorDoubleArray1D;
import org.ujmp.core.interfaces.HasRowMajorDoubleArray2D;
import org.ujmp.core.mapmatrix.MapMatrix;
import org.ujmp.core.util.MathUtil;
import org.ujmp.core.util.UJMPSettings;
import org.ujmp.core.util.VerifyUtil;
import org.ujmp.core.util.concurrent.PForEquidistant;

public class DivideMatrix {

	public static final DivideMatrixCalculation<Matrix, Matrix, Matrix> MATRIX = new DivideMatrixMatrix();

	public static final DivideMatrixCalculation<DenseMatrix, DenseMatrix, DenseMatrix> DENSEMATRIX = new DivideMatrixDenseMatrix();

	public static final DivideMatrixCalculation<DenseMatrix2D, DenseMatrix2D, DenseMatrix2D> DENSEMATRIX2D = new DivideMatrixDenseMatrix2D();

	public static final DivideMatrixCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> DENSEDOUBLEMATRIX2D = new DivideMatrixDenseDoubleMatrix2D();

	public static final DivideMatrixCalculation<SparseMatrix, SparseMatrix, SparseMatrix> SPARSEMATRIX = new DivideMatrixSparseMatrix();
}

class DivideMatrixDenseMatrix implements
		DivideMatrixCalculation<DenseMatrix, DenseMatrix, DenseMatrix> {
	public final void calc(final DenseMatrix source1, final DenseMatrix source2,
			final DenseMatrix target) {
		if (source1 instanceof DenseMatrix2D && source2 instanceof DenseMatrix2D
				&& target instanceof DenseMatrix2D) {
			DivideMatrix.DENSEMATRIX2D.calc((DenseMatrix2D) source1, (DenseMatrix2D) source2,
					(DenseMatrix2D) target);
		} else {
			VerifyUtil.verifySameSize(source1, source2, target);
			for (long[] c : source1.allCoordinates()) {
				BigDecimal v1 = source1.getAsBigDecimal(c);
				BigDecimal v2 = source2.getAsBigDecimal(c);
				BigDecimal result = MathUtil.divide(v1, v2);
				target.setAsBigDecimal(result, c);
			}
			if (source1 != target) {
				MapMatrix<String, Object> a = source1.getMetaData();
				if (a != null) {
					target.setMetaData(a.clone());
				}
			}
		}
	}
}

class DivideMatrixDenseMatrix2D implements
		DivideMatrixCalculation<DenseMatrix2D, DenseMatrix2D, DenseMatrix2D> {

	public final void calc(final DenseMatrix2D source1, final DenseMatrix2D source2,
			final DenseMatrix2D target) {
		if (source1 instanceof DenseDoubleMatrix2D && source2 instanceof DenseDoubleMatrix2D
				&& target instanceof DenseDoubleMatrix2D) {
			DivideMatrix.DENSEDOUBLEMATRIX2D.calc((DenseDoubleMatrix2D) source1,
					(DenseDoubleMatrix2D) source2, (DenseDoubleMatrix2D) target);
		} else {
			VerifyUtil.verifySameSize(source1, source2, target);
			for (int r = (int) source1.getRowCount(); --r != -1;) {
				for (int c = (int) source1.getColumnCount(); --c != -1;) {
					BigDecimal v1 = source1.getAsBigDecimal(r, c);
					BigDecimal v2 = source2.getAsBigDecimal(r, c);
					BigDecimal result = MathUtil.divide(v1, v2);
					target.setAsBigDecimal(result, r, c);
				}
			}
			if (source1 != target) {
				MapMatrix<String, Object> a = source1.getMetaData();
				if (a != null) {
					target.setMetaData(a.clone());
				}
			}
		}
	}
}

class DivideMatrixDenseDoubleMatrix2D implements
		DivideMatrixCalculation<DenseDoubleMatrix2D, DenseDoubleMatrix2D, DenseDoubleMatrix2D> {

	public final void calc(final DenseDoubleMatrix2D source1, final DenseDoubleMatrix2D source2,
			final DenseDoubleMatrix2D target) {
		if (source1 instanceof HasColumnMajorDoubleArray1D
				&& source2 instanceof HasColumnMajorDoubleArray1D
				&& target instanceof HasColumnMajorDoubleArray1D) {
			calc(((HasColumnMajorDoubleArray1D) source1).getColumnMajorDoubleArray1D(),
					((HasColumnMajorDoubleArray1D) source2).getColumnMajorDoubleArray1D(),
					((HasColumnMajorDoubleArray1D) target).getColumnMajorDoubleArray1D());
		} else if (source1 instanceof HasRowMajorDoubleArray2D
				&& source2 instanceof HasRowMajorDoubleArray2D
				&& target instanceof HasRowMajorDoubleArray2D) {
			calc(((HasRowMajorDoubleArray2D) source1).getRowMajorDoubleArray2D(),
					((HasRowMajorDoubleArray2D) source2).getRowMajorDoubleArray2D(),
					((HasRowMajorDoubleArray2D) target).getRowMajorDoubleArray2D());
		} else {
			VerifyUtil.verifySameSize(source1, source2, target);
			for (int r = (int) source1.getRowCount(); --r != -1;) {
				for (int c = (int) source1.getColumnCount(); --c != -1;) {
					target.setDouble(source1.getDouble(r, c) / source2.getDouble(r, c), r, c);
				}
			}
		}
		if (source1 != target) {
			MapMatrix<String, Object> a = source1.getMetaData();
			if (a != null) {
				target.setMetaData(a.clone());
			}
		}
	}

	private final void calc(final double[][] source1, final double[][] source2,
			final double[][] target) {
		VerifyUtil.verifySameSize(source1, source2, target);
		if (UJMPSettings.getInstance().getNumberOfThreads() > 1 && source1.length >= 100
				&& source1[0].length >= 100) {
			new PForEquidistant(0, source1.length - 1) {
				public void step(int i) {
					double[] v1 = source1[i];
					double[] v2 = source2[i];
					double[] t = target[i];
					for (int c = source1[0].length; --c != -1;) {
						t[c] = v1[c] / v2[c];
					}
				}
			};
		} else {
			double[] v1 = null;
			double[] v2 = null;
			double[] t = null;
			for (int r = source1.length; --r != -1;) {
				v1 = source1[r];
				v2 = source2[r];
				t = target[r];
				for (int c = source1[0].length; --c != -1;) {
					t[c] = v1[c] / v2[c];
				}
			}
		}
	}

	private final void calc(final double[] source1, final double[] source2, final double[] target) {
		VerifyUtil.verifySameSize(source1, source2, target);
		final int length = source1.length;
		for (int i = 0; i < length; i++) {
			target[i] = source1[i] / source2[i];
		}
	}

}

class DivideMatrixSparseMatrix implements
		DivideMatrixCalculation<SparseMatrix, SparseMatrix, SparseMatrix> {

	public final void calc(final SparseMatrix source1, final SparseMatrix source2,
			final SparseMatrix target) {
		VerifyUtil.verifySameSize(source1, source2, target);
		// have to iterate over all values in source2 to perform division by
		// zero if necessary
		for (long[] c : source2.allCoordinates()) {
			BigDecimal v1 = source1.getAsBigDecimal(c);
			BigDecimal v2 = source2.getAsBigDecimal(c);
			BigDecimal result = MathUtil.divide(v1, v2);
			target.setAsBigDecimal(result, c);
		}
		if (source1 != target) {
			MapMatrix<String, Object> a = source1.getMetaData();
			if (a != null) {
				target.setMetaData(a.clone());
			}
		}
	}
}

class DivideMatrixMatrix implements DivideMatrixCalculation<Matrix, Matrix, Matrix> {

	public final void calc(final Matrix source1, final Matrix source2, final Matrix target) {
		if (source1 instanceof DenseMatrix && source2 instanceof DenseMatrix
				&& target instanceof DenseMatrix) {
			DivideMatrix.DENSEMATRIX.calc((DenseMatrix) source1, (DenseMatrix) source2,
					(DenseMatrix) target);
		} else if (source1 instanceof SparseMatrix && source2 instanceof SparseMatrix
				&& target instanceof SparseMatrix) {
			DivideMatrix.SPARSEMATRIX.calc((SparseMatrix) source1, (SparseMatrix) source2,
					(SparseMatrix) target);
		} else {
			VerifyUtil.verifySameSize(source1, source2, target);
			for (long[] c : source1.allCoordinates()) {
				BigDecimal v1 = source1.getAsBigDecimal(c);
				BigDecimal v2 = source2.getAsBigDecimal(c);
				BigDecimal result = MathUtil.divide(v1, v2);
				target.setAsBigDecimal(result, c);
			}
			if (source1 != target) {
				MapMatrix<String, Object> a = source1.getMetaData();
				if (a != null) {
					target.setMetaData(a.clone());
				}
			}
		}
	}
}
