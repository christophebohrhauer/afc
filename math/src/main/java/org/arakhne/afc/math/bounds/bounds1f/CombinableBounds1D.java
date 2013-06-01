/* 
 * $Id$
 * 
 * Copyright (c) 2013 Christophe BOHRHAUER
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

package org.arakhne.afc.math.bounds.bounds1f;

import org.arakhne.afc.math.bounds.CombinableBounds;
import org.arakhne.afc.math.object.Point1D;
import org.arakhne.afc.math.object.Segment1D;



/** This interface representes the bounds of an area
 * in a 1D space with could be combine to produce
 * larger or smaller bounds.
 *
 * @param <S> is the type of the segment to reply
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface CombinableBounds1D<S extends Segment1D> extends Bounds1D<S>, CombinableBounds<Bounds1D<S>,Point1D,Point1D,Float> {

	/** Clamp the center of the bounds to the limits of the underlying segment.
	 */
	public void clamp();
	
	/**
	 * Add the not-jutted point into the bounds.
	 * <p>
	 * <strong>Important:</strong>&nbsp;The function {@link #clamp()} is 
	 * automatically invoked. 
	 * 
	 * @param x is the point to combine for updating this bounding object.
	 */
	public void combine(float... x);

	/** Set this not-jutted bounds from a set of points.
	 * <p>
	 * <strong>Important:</strong>&nbsp;The function {@link #clamp()} is 
	 * automatically invoked. 
	 * 
	 * @param points are the points used to update this bounding object.
	 */
	public void set(float... points);

	/** Set this bound from the set of points.
	 * <p>
	 * <strong>Important:</strong>&nbsp;The function {@link #clamp()} is 
	 * not automatically invoked. 
	 *
	 * @param lower is the coordinate of the lowest point of the bound.
	 * @param upper is the coordinate of the uppest point of the bound.
	 */
	public void setBox(float lower, float upper);

	/** Set this bound from the set of points.
	 * Do not change the segment.
	 * <p>
	 * <strong>Important:</strong>&nbsp;The function {@link #clamp()} is 
	 * not automatically invoked. 
	 *
	 * @param bounds is the object to copy.
	 */
	public void setBox(Bounds1D<?> bounds);

	/** Set the segment on which this bound lies.
	 * <p>
	 * <strong>Important:</strong>&nbsp;The function {@link #clamp()} is 
	 * automatically invoked. 
	 *
	 * @param segment is the new segment on which this bound lies.
	 */
	public void setSegment(S segment);

}