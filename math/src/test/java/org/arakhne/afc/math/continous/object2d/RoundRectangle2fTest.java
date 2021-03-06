/* 
 * $Id$
 * 
 * Copyright (c) 2006-10, Multiagent Team, Laboratoire Systemes et Transports, Universite de Technologie de Belfort-Montbeliard.
 * Copyright (C) 2012 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
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
package org.arakhne.afc.math.continous.object2d;

import org.arakhne.afc.math.continous.object2d.Circle2f;
import org.arakhne.afc.math.continous.object2d.Ellipse2f;
import org.arakhne.afc.math.continous.object2d.PathIterator2f;
import org.arakhne.afc.math.continous.object2d.Point2f;
import org.arakhne.afc.math.continous.object2d.Rectangle2f;
import org.arakhne.afc.math.continous.object2d.RoundRectangle2f;
import org.arakhne.afc.math.continous.object2d.Segment2f;
import org.arakhne.afc.math.generic.PathElementType;
import org.arakhne.afc.math.generic.Point2D;
import org.arakhne.afc.math.matrix.Transform2D;


/**
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class RoundRectangle2fTest extends AbstractRectangularShape2fTestCase<RoundRectangle2f> {

	@Override
	protected RoundRectangle2f createShape() {
		return new RoundRectangle2f(0f, 0f, 1f, 1f, .2f, .4f);
	}

	@Override
	public void testIsEmpty() {
		assertFalse(this.r.isEmpty());
		this.r.setMinX(1f);
		assertFalse(this.r.isEmpty());
		this.r.setMinY(1f);
		assertTrue(this.r.isEmpty());
		this.r.setMinX(0f);
		assertFalse(this.r.isEmpty());
	}

	@Override
	public void testClear() {
		this.r.clear();
		assertEquals(0f, this.r.getMinX());
		assertEquals(0f, this.r.getMinY());
		assertEquals(0f, this.r.getMaxX());
		assertEquals(0f, this.r.getMaxY());
		assertEquals(0f, this.r.getArcWidth());
		assertEquals(0f, this.r.getArcHeight());
	}

	@Override
	public void testClone() {
		RoundRectangle2f b = this.r.clone();

		assertNotSame(b, this.r);
		assertEpsilonEquals(this.r.getMinX(), b.getMinX());
		assertEpsilonEquals(this.r.getMinY(), b.getMinY());
		assertEpsilonEquals(this.r.getMaxX(), b.getMaxX());
		assertEpsilonEquals(this.r.getMaxY(), b.getMaxY());
		assertEpsilonEquals(this.r.getArcWidth(), b.getArcWidth());
		assertEpsilonEquals(this.r.getArcHeight(), b.getArcHeight());
		
		b.set(this.r.getMinX()+1f, this.r.getMinY()+1f,
				this.r.getWidth()+1f, this.r.getHeight()+1f);

		assertNotEpsilonEquals(this.r.getMinX(), b.getMinX());
		assertNotEpsilonEquals(this.r.getMinY(), b.getMinY());
		assertNotEpsilonEquals(this.r.getMaxX(), b.getMaxX());
		assertNotEpsilonEquals(this.r.getMaxY(), b.getMaxY());
		assertEpsilonEquals(this.r.getArcWidth(), b.getArcWidth());
		assertEpsilonEquals(this.r.getArcHeight(), b.getArcHeight());
	}

	@Override
	public void testDistancePoint2D() {
		assertEpsilonEquals(0.065492915f, this.r.distance(new Point2f(0f, 0f)));
		
		assertEpsilonEquals(4.18243948f, this.r.distance(new Point2f(-2.3f, -3.4f)));
		assertEpsilonEquals(2.3f, this.r.distance(new Point2f(-2.3f, .5f)));
		assertEpsilonEquals(5.208449224f, this.r.distance(new Point2f(-2.3f, 5.6f)));
		
		assertEpsilonEquals(3.4f, this.r.distance(new Point2f(.5f, -3.4f)));
		assertEpsilonEquals(0f, this.r.distance(new Point2f(.5f, .5f)));
		assertEpsilonEquals(4.6f, this.r.distance(new Point2f(.5f, 5.6f)));

		assertEpsilonEquals(5.802671745f, this.r.distance(new Point2f(5.6f, -3.4f)));
		assertEpsilonEquals(4.6f, this.r.distance(new Point2f(5.6f, .5f)));
		assertEpsilonEquals(6.590588951f, this.r.distance(new Point2f(5.6f, 5.6f)));

		assertEpsilonEquals(0.04958237f, this.r.distance(new Point2f(.01f, .01f)));
	}

	@Override
	public void testDistanceSquaredPoint2D() {
		assertEpsilonEquals(0.004289322f, this.r.distanceSquared(new Point2f(0f, 0f)));
		
		assertEpsilonEquals(17.492800004f, this.r.distanceSquared(new Point2f(-2.3f, -3.4f)));
		assertEpsilonEquals(5.29f, this.r.distanceSquared(new Point2f(-2.3f, .5f)));
		assertEpsilonEquals(27.127943319f, this.r.distanceSquared(new Point2f(-2.3f, 5.6f)));
		
		assertEpsilonEquals(11.56f, this.r.distanceSquared(new Point2f(.5f, -3.4f)));
		assertEpsilonEquals(0f, this.r.distanceSquared(new Point2f(.5f, .5f)));
		assertEpsilonEquals(21.16f, this.r.distanceSquared(new Point2f(.5f, 5.6f)));

		assertEpsilonEquals(33.67099938f, this.r.distanceSquared(new Point2f(5.6f, -3.4f)));
		assertEpsilonEquals(21.16f, this.r.distanceSquared(new Point2f(5.6f, .5f)));
		assertEpsilonEquals(43.435862721f, this.r.distanceSquared(new Point2f(5.6f, 5.6f)));

		assertEpsilonEquals(0.002458411f, this.r.distanceSquared(new Point2f(.01f, .01f)));
	}

	@Override
	public void testDistanceL1Point2D() {
		//|0 - 0.029289322| + |0 - 0.058578644|
		assertEpsilonEquals(0.087867966f, this.r.distanceL1(new Point2f(0f, 0f)));
		
		//|-2.3 - 0.02| + |-3.4 - 0.08|
		assertEpsilonEquals(5.8f, this.r.distanceL1(new Point2f(-2.3f, -3.4f)));
		//|-2.3 - 0| + |0.5 - 0.5|
		assertEpsilonEquals(2.3f, this.r.distanceL1(new Point2f(-2.3f, .5f)));
		//|-2.3 - 0.029289322| + |5.6 - 0.9414214|
		assertEpsilonEquals(6.987867922f, this.r.distanceL1(new Point2f(-2.3f, 5.6f)));
		
		//|0.5 - 0.5| + |-3.4 - 0|
		assertEpsilonEquals(3.4f, this.r.distanceL1(new Point2f(.5f, -3.4f)));
		//|0.5 - 0.5| + |0.5 - 0.5|
		assertEpsilonEquals(0f, this.r.distanceL1(new Point2f(.5f, .5f)));
		//|0.5 - 0.5| + |5.6 - 1|
		assertEpsilonEquals(4.6f, this.r.distanceL1(new Point2f(.5f, 5.6f)));

		//|5.6 - 0.99338573| + |-3.4 - 0.12847054|
		assertEpsilonEquals(8.13508481f, this.r.distanceL1(new Point2f(5.6f, -3.4f)));
		//|5.6 - 1| + |0.5 - 0.5|
		assertEpsilonEquals(4.6f, this.r.distanceL1(new Point2f(5.6f, .5f)));
		//|5.6 - 0.9890606| + |5.6 - 0.89095545|
		assertEpsilonEquals(9.31998395f, this.r.distanceL1(new Point2f(5.6f, 5.6f)));

		//|0.01 - 0.031225532| + |0.01 - 0.054809466|
		assertEpsilonEquals(0.066034998f, this.r.distanceL1(new Point2f(.01f, .01f)));
	}

	@Override
	public void testDistanceLinfPoint2D() {
		//max( abs(0 - 0.029289322) , abs(0 - 0.058578644) )
		assertEpsilonEquals(0.058579f, this.r.distanceLinf(new Point2f(0f, 0f)));
		
		//max( abs(-2.3 - 0.02) , abs(-3.4 - 0.08) )
		assertEpsilonEquals(3.48f, this.r.distanceLinf(new Point2f(-2.3f, -3.4f)));
		//max( abs(-2.3 - 0) , abs(0.5 - 0.5) )
		assertEpsilonEquals(2.3f, this.r.distanceLinf(new Point2f(-2.3f, .5f)));
		//max( abs(-2.3 - 0.029289322) , abs(5.6 - 0.9414214) )
		assertEpsilonEquals(4.6586f, this.r.distanceLinf(new Point2f(-2.3f, 5.6f)));
		
		//max( abs(0.5 - 0.5) , abs(-3.4 - 0) )
		assertEpsilonEquals(3.4f, this.r.distanceLinf(new Point2f(.5f, -3.4f)));
		//max( abs(0.5 - 0.5) , abs(0.5 - 0.5) )
		assertEpsilonEquals(0f, this.r.distanceLinf(new Point2f(.5f, .5f)));
		//max( abs(0.5 - 0.5) , abs(5.6 - 1) )
		assertEpsilonEquals(4.6f, this.r.distanceLinf(new Point2f(.5f, 5.6f)));

		//max( abs(5.6 - 0.99338573) , abs(-3.4 - 0.12847054) )
		assertEpsilonEquals(4.6066f, this.r.distanceLinf(new Point2f(5.6f, -3.4f)));
		//max( abs(5.6 - 1) , abs(0.5 - 0.5) )
		assertEpsilonEquals(4.6f, this.r.distanceLinf(new Point2f(5.6f, .5f)));
		//max( abs(5.6 - 0.9890606) , abs(5.6 - 0.89095545) )
		assertEpsilonEquals(4.7090f, this.r.distanceLinf(new Point2f(5.6f, 5.6f)));

		//max( abs(0.01 - 0.031225532) , abs(0.01 - 0.054809466) )
		assertEpsilonEquals(0.044809f, this.r.distanceLinf(new Point2f(.01f, .01f)));
	}

	@Override
	public void testToBoundingBox() {
		Rectangle2f bb = this.r.toBoundingBox();
		assertEpsilonEquals(0f, bb.getMinX());
		assertEpsilonEquals(0f, bb.getMinY());
		assertEpsilonEquals(1f, bb.getMaxX());
		assertEpsilonEquals(1f, bb.getMaxY());
	}

	/**
	 */
	public void testContainsFloatFloat() {
		assertFalse(this.r.contains(0f, 0f));

		assertFalse(this.r.contains(-2.3f, -3.4f));
		assertFalse(this.r.contains(-2.3f, .5f));
		assertFalse(this.r.contains(-2.3f, 5.6f));

		assertFalse(this.r.contains(.5f, -3.4f));
		assertTrue(this.r.contains(.5f, .5f));
		assertFalse(this.r.contains(.5f, 5.6f));

		assertFalse(this.r.contains(5.6f, -3.4f));
		assertFalse(this.r.contains(5.6f, .5f));
		assertFalse(this.r.contains(5.6f, 5.6f));

		assertFalse(this.r.contains(.01f, .01f));
	}

	/**
	 */
	public void testContainsPoint2D() {
		assertFalse(this.r.contains(new Point2f(0f, 0f)));

		assertFalse(this.r.contains(new Point2f(-2.3f, -3.4f)));
		assertFalse(this.r.contains(new Point2f(-2.3f, .5f)));
		assertFalse(this.r.contains(new Point2f(-2.3f, 5.6f)));

		assertFalse(this.r.contains(new Point2f(.5f, -3.4f)));
		assertTrue(this.r.contains(new Point2f(.5f, .5f)));
		assertFalse(this.r.contains(new Point2f(.5f, 5.6f)));

		assertFalse(this.r.contains(new Point2f(5.6f, -3.4f)));
		assertFalse(this.r.contains(new Point2f(5.6f, .5f)));
		assertFalse(this.r.contains(new Point2f(5.6f, 5.6f)));

		assertFalse(this.r.contains(new Point2f(.01f, .01f)));
	}

	/**
	 */
	public void testGetClosestPointTo() {
		Point2D p;
		
		p = this.r.getClosestPointTo(new Point2f(0f, 0f));
		// ecenter = (.1; .2)
		// a = .1
		// b = .2
		// a*a = .01
		// b*b = .04
		// x0 = x - ecenterx = 0 - .1 = -.1
		// y0 = y - ecentery = 0 - .2 = -.2
		// denom*denom = a*a*y0*y0 + b*b*x0*x0 = 8.0000e-04
		// denom = 0.028284
		// f = (a*b)/denom = 0.70711
		// x2 = f * x0 = -0.070711
		// y2 = f * y0 = -0.14142
		// px2 = x2 + ecenterx = 0.029289
		// py2 = y2 + ecentery = 0.058579
		assertEpsilonEquals(0.029289f, p.getX());
		assertEpsilonEquals(0.058579f, p.getY());
		
		p = this.r.getClosestPointTo(new Point2f(-2.3f, -3.4f));
		assertEpsilonEquals(.02f, p.getX());
		assertEpsilonEquals(.08f, p.getY());
		p = this.r.getClosestPointTo(new Point2f(-2.3f, .5f));
		assertEpsilonEquals(0f, p.getX());
		assertEpsilonEquals(.5f, p.getY());
		p = this.r.getClosestPointTo(new Point2f(-2.3f, 5.6f));
		assertEpsilonEquals(0.0292893218813453f, p.getX());
		assertEpsilonEquals(0.941421356237309f, p.getY());
		
		p = this.r.getClosestPointTo(new Point2f(.5f, -3.4f));
		assertEpsilonEquals(.5f, p.getX());
		assertEpsilonEquals(0f, p.getY());
		p = this.r.getClosestPointTo(new Point2f(.5f, .5f));
		assertEpsilonEquals(.5f, p.getX());
		assertEpsilonEquals(.5f, p.getY());
		p = this.r.getClosestPointTo(new Point2f(.5f, 5.6f));
		assertEpsilonEquals(.5f, p.getX());
		assertEpsilonEquals(1f, p.getY());

		p = this.r.getClosestPointTo(new Point2f(5.6f, -3.4f));
		assertEpsilonEquals(0.993385675169617f, p.getX());
		assertEpsilonEquals(0.128470546678592f, p.getY());
		p = this.r.getClosestPointTo(new Point2f(5.6f, .5f));
		assertEpsilonEquals(1f, p.getX());
		assertEpsilonEquals(.5f, p.getY());
		p = this.r.getClosestPointTo(new Point2f(5.6f, 5.6f));
		assertEpsilonEquals(0.989060526490781f, p.getX());
		assertEpsilonEquals(0.890955431309734f, p.getY());

		p = this.r.getClosestPointTo(new Point2f(.01f, .01f));
		assertEpsilonEquals(0.0312255352089217f, p.getX());
		assertEpsilonEquals(0.0548094632188347f, p.getY());
	}

	/**
	 */
	public void testGetPathIterator() {
		float vt = 0.11045696f; // vertical tangent length for the arc
		float ht = 0.055228f; // horizontal tangent length for the arc
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 0f, .2f);
		assertElement(pi, PathElementType.LINE_TO, 0f, .8f);
		assertElement(pi, PathElementType.CURVE_TO, 0f, .8f+vt, .1f-ht, 1f, 0.1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, .9f, 1f);
		assertElement(pi, PathElementType.CURVE_TO, .9f+ht, 1f, 1f, .8f+vt, 1f, .8f);
		assertElement(pi, PathElementType.LINE_TO, 1f, .2f);
		assertElement(pi, PathElementType.CURVE_TO, 1f, .2f-vt, .9f+ht, 0f, .9f, 0f);
		assertElement(pi, PathElementType.LINE_TO, .1f, 0f);
		assertElement(pi, PathElementType.CURVE_TO, .1f-ht, 0f, 0f, .2f-vt, 0f, .2f);
		assertElement(pi, PathElementType.CLOSE);
	}
	
	/**
	 */
	public void testGetPathIteratorTransform2D() {
		Transform2D tr;
		PathIterator2f pi;
		float vt = 0.11045696f; // vertical tangent length for the arc
		float ht = 0.055228f; // horizontal tangent length for the arc
		
		tr = new Transform2D();
		pi = this.r.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 0f, .2f);
		assertElement(pi, PathElementType.LINE_TO, 0f, .8f);
		assertElement(pi, PathElementType.CURVE_TO, 0f, .8f+vt, .1f-ht, 1f, 0.1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, .9f, 1f);
		assertElement(pi, PathElementType.CURVE_TO, .9f+ht, 1f, 1f, .8f+vt, 1f, .8f);
		assertElement(pi, PathElementType.LINE_TO, 1f, .2f);
		assertElement(pi, PathElementType.CURVE_TO, 1f, .2f-vt, .9f+ht, 0f, .9f, 0f);
		assertElement(pi, PathElementType.LINE_TO, .1f, 0f);
		assertElement(pi, PathElementType.CURVE_TO, .1f-ht, 0f, 0f, .2f-vt, 0f, .2f);
		assertElement(pi, PathElementType.CLOSE);

		tr = new Transform2D();
		tr.makeTranslationMatrix(3.4f, 4.5f);
		pi = this.r.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 3.4f, 4.7f);
		assertElement(pi, PathElementType.LINE_TO, 3.4f, 5.3f);
		assertElement(pi, PathElementType.CURVE_TO, 3.4f, 5.3f+vt, 3.5f-ht, 5.5f, 3.5f, 5.5f);
		assertElement(pi, PathElementType.LINE_TO, 4.3f, 5.5f);
		assertElement(pi, PathElementType.CURVE_TO, 4.3f+ht, 5.5f, 4.4f, 5.3f+vt, 4.4f, 5.3f);
		assertElement(pi, PathElementType.LINE_TO, 4.4f, 4.7f);
		assertElement(pi, PathElementType.CURVE_TO, 4.4f, 4.7f-vt, 4.3f+ht, 4.5f, 4.3f, 4.5f);
		assertElement(pi, PathElementType.LINE_TO, 3.5f, 4.5f);
		assertElement(pi, PathElementType.CURVE_TO, 3.5f-ht, 4.5f, 3.4f, 4.7f-vt, 3.4f, 4.7f);
		assertElement(pi, PathElementType.CLOSE);
	}

	/**
	 */
	public void testSetArcWidth() {
		this.r.setArcWidth(4.5f);
		assertEpsilonEquals(4.5f, this.r.getArcWidth());
	}

	/**
	 */
	public void testSetArcHeight() {
		this.r.setArcHeight(4.5f);
		assertEpsilonEquals(4.5f, this.r.getArcHeight());
	}

	/**
	 */
	public void testIntersectsRectangle2f() {
		assertTrue(this.r.intersects(new Rectangle2f(0f, 0f, 1f, 1f)));
		assertTrue(this.r.intersects(new Rectangle2f(-5f, -5f, 6f, 6f)));
		assertTrue(this.r.intersects(new Rectangle2f(.5f, .5f, 4.5f, 4.5f)));
		assertTrue(this.r.intersects(new Rectangle2f(-5f, -5f, 10f, 10f)));
		assertFalse(this.r.intersects(new Rectangle2f(5f, .5f, 5f, .6f)));
		assertFalse(this.r.intersects(new Rectangle2f(-5f, -5f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(-5f, -5f, 10f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(5f, -5f, 1f, 10f)));
		assertTrue(this.r.intersects(new Rectangle2f(-5f, -5f, 5.01f, 5.01f)));
	}

	/**
	 */
	public void testIntersectsSegment2f() {
		assertTrue(this.r.intersects(new Segment2f(0f, 0f, 1f, 1f)));
		assertTrue(this.r.intersects(new Segment2f(-5f, -5f, 1f, 1f)));
		assertTrue(this.r.intersects(new Segment2f(.5f, .5f, 5f, 5f)));
		assertTrue(this.r.intersects(new Segment2f(.5f, .5f, 5f, .6f)));
		assertTrue(this.r.intersects(new Segment2f(-5f, -5f, 5f, 5f)));
		assertFalse(this.r.intersects(new Segment2f(-5f, -5f, -4f, -4f)));
		assertFalse(this.r.intersects(new Segment2f(-5f, -5f, 4f, -4f)));
		assertFalse(this.r.intersects(new Segment2f(5f, -5f, 6f, 5f)));
		assertFalse(this.r.intersects(new Segment2f(5f, -5f, 5f, 5f)));
		assertTrue(this.r.intersects(new Segment2f(-1f, -1f, 0.01f, 0.01f)));
	}

	/**
	 */
	public void testIntersectsCircle2f() {
		assertTrue(this.r.intersects(new Circle2f(0f, 0f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(-5f, -5f, 1f)));
		assertTrue(this.r.intersects(new Circle2f(.5f, .5f, 5f)));
		assertFalse(this.r.intersects(new Circle2f(-5f, -5f, 5f)));
		assertFalse(this.r.intersects(new Circle2f(-5f, -5f, 4f)));
		assertFalse(this.r.intersects(new Circle2f(5f, -5f, 6f)));
		assertFalse(this.r.intersects(new Circle2f(5f, -5f, 5f)));
		assertTrue(this.r.intersects(new Circle2f(-1f, -1f, 1.4284f)));
	}

	/**
	 */
	public void testIntersectsEllipse2f() {
		assertTrue(this.r.intersects(new Ellipse2f(0f, 0f, 1f, 1f)));
		assertFalse(this.r.intersects(new Ellipse2f(-5f, -5f, 1f, 1f)));
		assertFalse(this.r.intersects(new Ellipse2f(.5f, .5f, 5f, 5f)));
		assertTrue(this.r.intersects(new Ellipse2f(.5f, .5f, 5f, .6f)));
		assertFalse(this.r.intersects(new Ellipse2f(-5f, -5f, 5f, 5f)));
		assertFalse(this.r.intersects(new Ellipse2f(-5f, -5f, -4f, -4f)));
		assertFalse(this.r.intersects(new Ellipse2f(-5f, -5f, 4f, -4f)));
		assertFalse(this.r.intersects(new Ellipse2f(5f, -5f, 6f, 5f)));
		assertFalse(this.r.intersects(new Ellipse2f(5f, -5f, 5f, 5f)));
		assertTrue(this.r.intersects(new Ellipse2f(-1f, -1f, 1.2f, 1.2f)));
	}

	/**
	 */
	public static void testContainsRoundRectangleRectangle() {
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				0f, 0f, 1f, 1f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				-5f, -5f, 6f, 6f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				.5f, .5f, 4.5f, 4.5f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				-5f, -5f, 10f, 10f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				5f, .5f, 5f, .6f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				-5f, -5f, 1f, 1f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				-5f, -5f, 10f, 1f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				5f, -5f, 1f, 10f));
		assertFalse(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				-5f, -5f, 5.01f, 5.01f));
		assertTrue(RoundRectangle2f.containsRoundRectangleRectangle(
				0f, 0f, 1f, 1f, .2f, .4f,
				.25f, .25f, .5f, .5f));
	}

	/**
	 */
	@Override
	public void testIntersectsPath2f() {
		Path2f p;
		
		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(-2f, 2f);
		p.lineTo(2f, 2f);
		p.lineTo(2f, -2f);
		assertFalse(this.r.intersects(p));
		p.closePath();
		assertTrue(this.r.intersects(p));
		
		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(0f, 0f);
		p.lineTo(-2f, 2f);
		assertFalse(this.r.intersects(p));
		p.closePath();
		assertFalse(this.r.intersects(p));

		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(2f, 2f);
		p.lineTo(-2f, 2f);
		assertTrue(this.r.intersects(p));
		p.closePath();
		assertTrue(this.r.intersects(p));

		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(-2f, 2f);
		p.lineTo(2f, -2f);
		assertFalse(this.r.intersects(p));
		p.closePath();
		assertFalse(this.r.intersects(p));

		p = new Path2f();
		p.moveTo(-2f, 2f);
		p.lineTo(1f, 0f);
		p.lineTo(2f, 1f);
		assertTrue(this.r.intersects(p));
		p.closePath();
		assertTrue(this.r.intersects(p));

		p = new Path2f();
		p.moveTo(-2f, 2f);
		p.lineTo(2f, 1f);
		p.lineTo(1f, 0f);
		assertFalse(this.r.intersects(p));
		p.closePath();
		assertTrue(this.r.intersects(p));
	}

	/**
	 */
	@Override
	public void testIntersectsPathIterator2f() {
		Path2f p;

		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(-2f, 2f);
		p.lineTo(2f, 2f);
		p.lineTo(2f, -2f);
		assertFalse(this.r.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.r.intersects(p.getPathIterator()));
		
		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(0f, 0f);
		p.lineTo(-2f, 2f);
		assertFalse(this.r.intersects(p.getPathIterator()));
		p.closePath();
		assertFalse(this.r.intersects(p.getPathIterator()));

		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(2f, 2f);
		p.lineTo(-2f, 2f);
		assertTrue(this.r.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.r.intersects(p.getPathIterator()));

		p = new Path2f();
		p.moveTo(-2f, -2f);
		p.lineTo(-2f, 2f);
		p.lineTo(2f, -2f);
		assertFalse(this.r.intersects(p.getPathIterator()));
		p.closePath();
		assertFalse(this.r.intersects(p.getPathIterator()));

		p = new Path2f();
		p.moveTo(-2f, 2f);
		p.lineTo(1f, 0f);
		p.lineTo(2f, 1f);
		assertTrue(this.r.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.r.intersects(p.getPathIterator()));

		p = new Path2f();
		p.moveTo(-2f, 2f);
		p.lineTo(2f, 1f);
		p.lineTo(1f, 0f);
		assertFalse(this.r.intersects(p.getPathIterator()));
		p.closePath();
		assertTrue(this.r.intersects(p.getPathIterator()));
	}

}