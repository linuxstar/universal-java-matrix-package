/*
 * Copyright (C) 2008 Holger Arndt, Andreas Naegele and Markus Bundschus
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

import org.ujmp.core.Matrix.Format;
import org.ujmp.core.exceptions.MatrixException;

public class TestExportMatrixMAT extends AbstractExportMatrixTest {

	@Override
	public Format getFormat() {
		return Format.MAT;
	}

	@Override
	public void testExportToFile() throws Exception {

		try {
			Class.forName("org.ujmp.jmatio.ExportMatrixMAT");
		} catch (ClassNotFoundException e) {
			return;
		}

		super.testExportToFile();

	}

	@Override
	public void testExportToStream() throws Exception {
		try {
			super.testExportToStream();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	@Override
	public void testExportToWriter() throws Exception {
		try {
			super.testExportToWriter();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	@Override
	public void testExportToClipboard() throws Exception {
		try {
			super.testExportToClipboard();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

	@Override
	public void testExportToString() throws Exception {
		try {
			super.testExportToString();
		} catch (MatrixException e) {
			return;
		}
		throw new Exception("this method should not be supported");
	}

}