/*
 * Copyright (C) 2008-2014 by Holger Arndt
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

package org.ujmp.core.doublematrix.calculation.entrywise.creators;

import org.ujmp.core.Matrix;
import org.ujmp.core.doublematrix.calculation.AbstractDoubleCalculation;
import org.ujmp.core.enums.ValueType;

public class Ones extends AbstractDoubleCalculation {
	private static final long serialVersionUID = 2547827499345834225L;

	public Ones(Matrix matrix) {
		super(matrix);
	}

	public double getDouble(long... coordinates) {
		return 1.0;
	}

	public static Matrix calc(Matrix source)  {
		Matrix ret = Matrix.Factory.zeros(source.getSize());
		for (long[] c : source.allCoordinates()) {
			ret.setAsDouble(1.0, c);
		}
		return ret;
	}

	public static Matrix calc(long... size)  {
		return calc(ValueType.DOUBLE, size);
	}

	public static Matrix calc(ValueType valueType, long... size)  {
		Matrix ret = Matrix.Factory.zeros(valueType, size);
		for (long[] c : ret.allCoordinates()) {
			ret.setAsDouble(1.0, c);
		}
		return ret;
	}
}
