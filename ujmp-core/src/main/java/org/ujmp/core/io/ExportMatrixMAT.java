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

package org.ujmp.core.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.ujmp.core.Matrix;

public abstract class ExportMatrixMAT {

	public static void toFile(File file, Matrix matrix, Object... parameters) throws IOException {
		try {
			Class<?> c = Class.forName("org.ujmp.jmatio.ExportMatrixMAT");
			Method method = c.getMethod("toFile", new Class[] { File.class, Matrix.class,
					Object[].class });
			method.invoke(null, file, matrix, parameters);
		} catch (Exception e) {
			throw new RuntimeException("ujmp-jmatio not found in classpath", e);
		}
	}

}
