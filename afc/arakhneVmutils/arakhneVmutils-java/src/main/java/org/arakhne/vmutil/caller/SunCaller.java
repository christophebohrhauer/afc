/* 
 * $Id$
 * 
 * Copyright (C) 2012 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */

package org.arakhne.vmutil.caller;

import sun.reflect.Reflection;

/**
 * This utility class provides a way to determine which class
 * call a function.
 * <p>
 * It inspirated from the Sun's <code>sun.reflect.Reflection</code> class
 * 
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@SuppressWarnings("restriction")
public class SunCaller extends StackTraceCaller {
	
	/**
	 */
	public SunCaller() {
		//
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public Class<?> getCallerClass(int level) {
    	// Parameter value of Reflection.getClassClass:
		//
    	// 0: Reflection.class | Reflection.getCallerClass()
    	// 1: Caller.class     | this 
		// 2: ???              | Caller of this function - not interesting because known
		// 3: ???              | Caller of the caller of this function - START INTEREST HERE
		if (level<0) throw new IllegalArgumentException();
		return Reflection.getCallerClass(level+2);
	}

}