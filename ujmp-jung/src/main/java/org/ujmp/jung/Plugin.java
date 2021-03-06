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

package org.ujmp.jung;

import org.ujmp.core.util.AbstractPlugin;

public class Plugin extends AbstractPlugin {

	public Plugin() {
		super("Plugin to incorporate graph visualizations from Jung library");
		dependencies.add("ujmp-core");
		dependencies.add("ujmp-gui");
		dependencies.add("jung-api.jar");
		dependencies.add("jung-visualization.jar");
		dependencies.add("jung-algorithms.jar");
		dependencies.add("jung-graph-impl.jar");
		dependencies.add("collections-generic.jar");
		dependencies.add("colt.jar");
		dependencies.add("concurrent.jar");
		neededClasses.add("org.ujmp.gui.Plugin");
		neededClasses.add("edu.uci.ics.jung.graph.Graph");
		neededClasses.add("cern.colt.matrix.DoubleMatrix2D");
		neededClasses.add("EDU.oswego.cs.dl.util.concurrent.Callable");
	}

}
