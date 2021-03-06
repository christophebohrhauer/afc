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

import org.arakhne.afc.math.generic.PathElementType;
import org.arakhne.afc.math.generic.Point2D;
import org.arakhne.afc.math.matrix.Transform2D;

/**
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class OpenPath2fTest extends AbstractPath2fTestCase {

	private Path2f r2;
	private Path2f r3;
	private Path2f r4;
	private Path2f r5;
	private Path2f r6;
	private Path2f r7;
	private Path2f r8;
		
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		this.r2 = new Path2f();
		this.r2.moveTo(2f, 1f);
		this.r2.lineTo(4f, 0f);
		this.r2.quadTo(5f, -2f, 2f, -3f);
		this.r2.closePath();
		
		this.r3 = new Path2f();
		this.r3.moveTo(5f, 0f);
		this.r3.lineTo(6f, 2f);
		this.r3.lineTo(7f, 1f);
		this.r3.closePath();

		this.r4 = new Path2f();
		this.r4.moveTo(1f, 2f);
		this.r4.lineTo(0f, 1f);
		this.r4.lineTo(3f, 0f);

		this.r5 = new Path2f();
		this.r5.moveTo(3f, 0f);
		this.r5.lineTo(4f, 1f);
		this.r5.lineTo(5f, -2f);
		this.r5.closePath();

		this.r6 = new Path2f();
		this.r6.moveTo(4f, 4f);
		this.r6.lineTo(0f, 2f);
		this.r6.lineTo(2f, -3f);
		this.r6.lineTo(7f, -6f);
		this.r6.lineTo(8f, 0f);

		this.r7 = new Path2f();
		this.r7.moveTo(4f, 4f);
		this.r7.lineTo(0f, 2f);
		this.r7.lineTo(2f, -3f);
		this.r7.lineTo(7f, -6f);
		this.r7.lineTo(8f, 0f);
		this.r7.closePath();

		this.r8 = new Path2f();
		this.r8.moveTo(0f, -5f);
		this.r8.lineTo(1f, -3f);
		this.r8.lineTo(2f, -6f);
		this.r8.closePath();
	}
	
	@Override
	protected void tearDown() throws Exception {
		this.r2 = this.r3 = this.r4 = null;
		this.r5 = this.r6 = this.r7 = null;
		this.r8 = null;
		super.tearDown();
	}

	@Override
	protected Path2f createShape() {
		Path2f p = new Path2f();
		p.moveTo(1f, 1f);
		p.lineTo(2f, 2f);
		p.quadTo(3f, 0f, 4f, 3f);
		p.curveTo(5f, -1f, 6f, 5f, 7f, -5f);
		return p;
	}

	/**
	 */
	@Override
	public void testGetClosestPointTo() {
		Point2D p;

		p = this.r.getClosestPointTo(new Point2f(0f, 0f));
		assertEpsilonEquals(1f, p.getX());
		assertEpsilonEquals(1f, p.getY());

		p = this.r.getClosestPointTo(new Point2f(4f, 0f));
		assertEpsilonEquals(3f, p.getX());
		assertEpsilonEquals(1.25f, p.getY());

		p = this.r.getClosestPointTo(new Point2f(4f, 2f));
		assertEpsilonEquals(4.28125f, p.getX());
		assertEpsilonEquals(2.11724f, p.getY());

		p = this.r.getClosestPointTo(new Point2f(1f, 0f));
		assertEpsilonEquals(1f, p.getX());
		assertEpsilonEquals(1f, p.getY());
	}


	@Override
	public void testDistancePoint2D() {
		assertEpsilonEquals(1.414213562f, this.r.distance(new Point2f(0f, 0f)));
		assertEpsilonEquals(1.600781059f, this.r.distance(new Point2f(4f, 0f)));
		assertEpsilonEquals(0.304707696f, this.r.distance(new Point2f(4f, 2f)));
		assertEpsilonEquals(1f, this.r.distance(new Point2f(1f, 0f)));
	}

	@Override
	public void testDistanceSquaredPoint2D() {
		assertEpsilonEquals(2f, this.r.distanceSquared(new Point2f(0f, 0f)));
		assertEpsilonEquals(2.5625f, this.r.distanceSquared(new Point2f(4f, 0f)));
		assertEpsilonEquals(.09284678f, this.r.distanceSquared(new Point2f(4f, 2f)));
		assertEpsilonEquals(1f, this.r.distanceSquared(new Point2f(1f, 0f)));
	}

	@Override
	public void testDistanceL1Point2D() {
		// (1,1)
		assertEpsilonEquals(2f, this.r.distanceL1(new Point2f(0f, 0f)));
		// (3,1.25)
		assertEpsilonEquals(2.25f, this.r.distanceL1(new Point2f(4f, 0f)));
		// (4.28125,2.11724)
		assertEpsilonEquals(.39849f, this.r.distanceL1(new Point2f(4f, 2f)));
		// (1,1)
		assertEpsilonEquals(1f, this.r.distanceL1(new Point2f(1f, 0f)));
	}

	@Override
	public void testDistanceLinfPoint2D() {
		// (1,1)
		assertEpsilonEquals(1f, this.r.distanceLinf(new Point2f(0f, 0f)));
		// (3,1.25)
		assertEpsilonEquals(1.25f, this.r.distanceLinf(new Point2f(4f, 0f)));
		// (4.28125,2.11724)
		assertEpsilonEquals(.28125f, this.r.distanceLinf(new Point2f(4f, 2f)));
		// (1,1)
		assertEpsilonEquals(1f, this.r.distanceLinf(new Point2f(1f, 0f)));
	}

	@Override
	public void testTranslateFloatFloat() {
		this.r.translate(3.4f, 4.5f);
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 4.4f, 5.5f);
		assertElement(pi, PathElementType.LINE_TO, 5.4f, 6.5f);
		assertElement(pi, PathElementType.QUAD_TO, 6.4f, 4.5f, 7.4f, 7.5f);
		assertElement(pi, PathElementType.CURVE_TO, 8.4f, 3.5f, 9.4f, 9.5f, 10.4f, -.5f);
		assertNoElement(pi);
	}

	/**
	 */
	@Override
	public void testAddIterator() {
		Path2f p2 = new Path2f();
		p2.moveTo(3.4f, 4.5f);
		p2.lineTo(5.6f, 6.7f);

		this.r.add(p2.getPathIterator());

		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertElement(pi, PathElementType.MOVE_TO, 3.4f, 4.5f);
		assertElement(pi, PathElementType.LINE_TO, 5.6f, 6.7f);
		assertNoElement(pi);
	}

	/** 
	 */
	@Override
	public void testGetPathIteratorVoid() {
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);
	}

	/** 
	 */
	@Override
	public void testGetPathIteratorTransform2D() {
		Transform2D tr;
		PathIterator2f pi;

		tr = new Transform2D();
		pi = this.r.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		tr = new Transform2D();
		tr.makeTranslationMatrix(3.4f, 4.5f);
		pi = this.r.getPathIterator(tr);
		assertElement(pi, PathElementType.MOVE_TO, 4.4f, 5.5f);
		assertElement(pi, PathElementType.LINE_TO, 5.4f, 6.5f);
		assertElement(pi, PathElementType.QUAD_TO, 6.4f, 4.5f, 7.4f, 7.5f);
		assertElement(pi, PathElementType.CURVE_TO, 8.4f, 3.5f, 9.4f, 9.5f, 10.4f, -.5f);
		assertNoElement(pi);
	}

	/** 
	 */
	@Override
	public void testTransformTransform2D() {
		Transform2D tr = new Transform2D();
		tr.makeTranslationMatrix(3.4f, 4.5f);
		Transform2D tr2 = new Transform2D();
		tr2.makeRotationMatrix(5.6f);

		Path2f clone = this.r.clone();
		clone.transform(tr);

		PathIterator2f pi = clone.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 4.4f, 5.5f);
		assertElement(pi, PathElementType.LINE_TO, 5.4f, 6.5f);
		assertElement(pi, PathElementType.QUAD_TO, 6.4f, 4.5f, 7.4f, 7.5f);
		assertElement(pi, PathElementType.CURVE_TO, 8.4f, 3.5f, 9.4f, 9.5f, 10.4f, -.5f);
		assertNoElement(pi);

		clone = this.r.clone();
		clone.transform(tr2);

		pi = clone.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1.406832516382571f, 0.144299240637928f);
		assertElement(pi, PathElementType.LINE_TO, 2.813665032765142f, 0.288598481275856f);
		assertElement(pi, PathElementType.QUAD_TO, 2.32669763553075f, -1.89379991361696f,
				4.996063427657964f, -0.198368915958538f);
		assertElement(pi, PathElementType.CURVE_TO, 3.24656275467893f, -3.93189906787186f,
				7.8097284604231056f, 0.0902295653173185f,
				2.27262796021014f, -8.29669585765750f);
		assertNoElement(pi);

		Transform2D tr3 = new Transform2D();
		tr3.mul(tr, tr2);
		clone = this.r.clone();
		clone.transform(tr3);

		pi = clone.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 4.80683251638257f, 4.64429924063793f);
		assertElement(pi, PathElementType.LINE_TO, 6.21366503276514f, 4.78859848127586f);
		assertElement(pi, PathElementType.QUAD_TO, 5.72669763553075f, 2.60620008638304f,
				8.39606342765796f, 4.30163108404146f);
		assertElement(pi, PathElementType.CURVE_TO, 6.646562754678927f, 0.568100932128142f,
				11.20972846042311f, 4.59022956531732f, 5.67262796021014f, -3.79669585765750f);
		assertNoElement(pi);
	}

	/** 
	 */
	@Override
	public void testCreateTransformedShape2D() {
		Transform2D tr = new Transform2D();
		tr.makeTranslationMatrix(3.4f, 4.5f);
		Path2f p2 = (Path2f)this.r.createTransformedShape(tr);

		PathIterator2f pi = p2.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 4.4f, 5.5f);
		assertElement(pi, PathElementType.LINE_TO, 5.4f, 6.5f);
		assertElement(pi, PathElementType.QUAD_TO, 6.4f, 4.5f, 7.4f, 7.5f);
		assertElement(pi, PathElementType.CURVE_TO, 8.4f, 3.5f, 9.4f, 9.5f, 10.4f, -.5f);
		assertNoElement(pi);
	}

	/**
	 */
	@Override
	public void testContainsPathIterator2fFloatFloat() {
		assertFalse(Path2f.contains(this.r.getPathIterator(), 0f, 0f));
		assertTrue(Path2f.contains(this.r.getPathIterator(), 4f, 3f));
		assertTrue(Path2f.contains(this.r.getPathIterator(), 2f, 2f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 2f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 5f, 0f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), -1f, -1f));
	}

	/** 
	 */
	@Override
	public void testContainsFloatfloat() {
		assertFalse(this.r.contains(0f, 0f));
		assertTrue(this.r.contains(4f, 3f));
		assertTrue(this.r.contains(2f, 2f));
		assertFalse(this.r.contains(2f, 1f));
		assertFalse(this.r.contains(5f, 0f));
		assertFalse(this.r.contains(-1f, -1f));
		assertFalse(this.r.contains(5f, 2f));
		assertFalse(this.r.contains(3.5f, -2.5f));
		assertFalse(this.r.contains(7f, -4f));
		assertFalse(this.r.contains(2.5f, 1.5f));
	}

	/** 
	 */
	@Override
	public void testContainsRectangle2f() {
		assertFalse(this.r.contains(new Rectangle2f(0f, 0f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(4f, 3f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(2f, 2f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(2f, 1f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(3f, 0f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(-1f, -1f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(4f, -3f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(-3f, 4f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(6f, -5f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(4f, 0f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(5f, 0f, 1f, 1f)));
		assertFalse(this.r.contains(new Rectangle2f(.01f, .01f, 1f, 1f)));
	}

	/**
	 */
	@Override
	public void testContainsPathIterator2fFloatFloatFloatFloat() {
		assertFalse(Path2f.contains(this.r.getPathIterator(), 0f, 0f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 4f, 3f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 2f, 2f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 2f, 1f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 3f, 0f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), -1f, -1f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 4f, -3f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), -3f, 4f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 6f, -5f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 4f, 0f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), 5f, 0f, 1f, 1f));
		assertFalse(Path2f.contains(this.r.getPathIterator(), .01f, .01f, 1f, 1f));
	}
	/** 
	 */
	@Override
	public void testIntersectsRectangle2f() {
		assertFalse(this.r.intersects(new Rectangle2f(0f, 0f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(4f, 3f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(2f, 2f, 1f, 1f)));
		assertTrue(this.r.intersects(new Rectangle2f(2f, 1f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(3f, 0f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(-1f, -1f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(4f, -3f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(-3f, 4f, 1f, 1f)));
		assertTrue(this.r.intersects(new Rectangle2f(6f, -5f, 1f, 1f)));
		assertFalse(this.r.intersects(new Rectangle2f(4f, 0f, 1f, 1f)));
		assertTrue(this.r.intersects(new Rectangle2f(5f, 0f, 1f, 1f)));
		assertTrue(this.r.intersects(new Rectangle2f(.01f, .01f, 1f, 1f)));
	}

	@Override
	public void testIntersectsPathIterator2fFloatFloatFloatFloat() {
		assertFalse(Path2f.intersects(this.r.getPathIterator(), 0f, 0f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), 4f, 3f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), 2f, 2f, 1f, 1f));
		assertTrue(Path2f.intersects(this.r.getPathIterator(), 2f, 1f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), 3f, 0f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), -1f, -1f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), 4f, -3f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), -3f, 4f, 1f, 1f));
		assertTrue(Path2f.intersects(this.r.getPathIterator(), 6f, -5f, 1f, 1f));
		assertFalse(Path2f.intersects(this.r.getPathIterator(), 4f, 0f, 1f, 1f));
		assertTrue(Path2f.intersects(this.r.getPathIterator(), 5f, 0f, 1f, 1f));
		assertTrue(Path2f.intersects(this.r.getPathIterator(), .01f, .01f, 1f, 1f));
	}

	@Override
	public void testIntersectsPath2f() {
		assertFalse(this.r.intersects(this.r2));
		assertTrue(this.r.intersects(this.r3));
		assertFalse(this.r.intersects(this.r4));
		assertFalse(this.r.intersects(this.r5));
		assertFalse(this.r.intersects(this.r6));
		assertTrue(this.r.intersects(this.r7));
		assertFalse(this.r.intersects(this.r8));

		assertFalse(this.r2.intersects(this.r));
		assertTrue(this.r3.intersects(this.r));
		assertFalse(this.r4.intersects(this.r));
		assertFalse(this.r5.intersects(this.r));
		assertFalse(this.r6.intersects(this.r));
		assertTrue(this.r7.intersects(this.r));
		assertFalse(this.r8.intersects(this.r));
	}

	@Override
	public void testIntersectsPathIterator2f() {
		assertFalse(this.r.intersects(this.r2.getPathIterator()));
		assertTrue(this.r.intersects(this.r3.getPathIterator()));
		assertFalse(this.r.intersects(this.r4.getPathIterator()));
		assertFalse(this.r.intersects(this.r5.getPathIterator()));
		assertFalse(this.r.intersects(this.r6.getPathIterator()));
		assertTrue(this.r.intersects(this.r7.getPathIterator()));
		assertFalse(this.r.intersects(this.r8.getPathIterator()));

		assertFalse(this.r2.intersects(this.r.getPathIterator()));
		assertTrue(this.r3.intersects(this.r.getPathIterator()));
		assertFalse(this.r4.intersects(this.r.getPathIterator()));
		assertFalse(this.r5.intersects(this.r.getPathIterator()));
		assertFalse(this.r6.intersects(this.r.getPathIterator()));
		assertTrue(this.r7.intersects(this.r.getPathIterator()));
		assertFalse(this.r8.intersects(this.r.getPathIterator()));
	}

	/** 
	 */
	@Override
	public void testIntersectsEllipse2f() {
		assertFalse(this.r.intersects(new Ellipse2f(0f, 0f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(4f, 3f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(2f, 2f, 1f, 2f)));
		assertTrue(this.r.intersects(new Ellipse2f(2f, 1f, 1f, 2f)));
		assertTrue(this.r.intersects(new Ellipse2f(3f, 0f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(-1f, -1f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(4f, -3f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(-3f, 4f, 1f, 2f)));
		assertTrue(this.r.intersects(new Ellipse2f(6f, -5f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(6f, -5f, .8f, 2f)));
		assertTrue(this.r.intersects(new Ellipse2f(4f, 0f, 1f, 2f)));
		assertFalse(this.r.intersects(new Ellipse2f(6f, 0f, 1f, 2f)));
	}

	/** 
	 */
	@Override
	public void testIntersectsCircle2f() {
		assertFalse(this.r.intersects(new Circle2f(0f, 0f, 1f)));
		assertTrue(this.r.intersects(new Circle2f(4f, 3f, 1f)));
		assertTrue(this.r.intersects(new Circle2f(2f, 2f, 1f)));
		assertTrue(this.r.intersects(new Circle2f(2f, 1f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(3f, 0f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(-1f, -1f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(4f, -3f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(-3f, 4f, 1f)));
		assertTrue(this.r.intersects(new Circle2f(6f, -5f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(6f, -5f, .95f)));
		assertFalse(this.r.intersects(new Circle2f(4f, 0f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(5f, 0f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(.01f, .01f, 1f)));
		assertFalse(this.r.intersects(new Circle2f(6f, 2f, .8f)));
	}

	/** 
	 */
	@Override
	public void testIntersectsSegment2f() {
		assertFalse(this.r.intersects(new Segment2f(0f, 0f, 1f, 1f)));
		assertTrue(this.r.intersects(new Segment2f(4f, 3f, 1f, 1f)));
		assertTrue(this.r.intersects(new Segment2f(2f, 2f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(2f, 1f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(3f, 0f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(3f, 0f, 1.5f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(-1f, -1f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(4f, -3f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(4f, -3f, 1f, 0f)));
		assertFalse(this.r.intersects(new Segment2f(-3f, 4f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(6f, -5f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(6f, -5f, 1f, 0f)));
		assertFalse(this.r.intersects(new Segment2f(4f, 0f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(5f, 0f, 1f, 1f)));
		assertFalse(this.r.intersects(new Segment2f(.01f, .01f, 1f, 1f)));
	}

	@Override
	public void testToBoundingBox() {
		Rectangle2f bb = this.r.toBoundingBox();
		assertEpsilonEquals(1f, bb.getMinX());
		assertEpsilonEquals(-5f, bb.getMinY());
		assertEpsilonEquals(7f, bb.getMaxX());
		assertEpsilonEquals(3f, bb.getMaxY());
	}
	
	@Override
	public void testToBoundingBoxWithCtrlPoints() {
		Rectangle2f bb = this.r.toBoundingBoxWithCtrlPoints();
		assertEpsilonEquals(1f, bb.getMinX());
		assertEpsilonEquals(-5f, bb.getMinY());
		assertEpsilonEquals(7f, bb.getMaxX());
		assertEpsilonEquals(5f, bb.getMaxY());
	}

	/**
	 */
	@Override
	public void testRemoveLast() {
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		this.r.removeLast();

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertNoElement(pi);

		this.r.removeLast();

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertNoElement(pi);

		this.r.removeLast();

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertNoElement(pi);

		this.r.removeLast();

		pi = this.r.getPathIterator();
		assertNoElement(pi);

		this.r.removeLast();

		pi = this.r.getPathIterator();
		assertNoElement(pi);
	}

	/**
	 */
	@Override
	public void testSetLastPointFloatFloat() {
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		this.r.setLastPoint(123.456f, 789.1011f);

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 123.456f, 789.1011f);
		assertNoElement(pi);
	}

	/**
	 */
	@Override
	public void testRemoveFloatFloat() {
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		this.r.remove(2f, 2f);

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		this.r.remove(4f, 3f);

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		this.r.remove(6f, 5f);

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertNoElement(pi);

		this.r.remove(6f, 5f);

		pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertNoElement(pi);
	}

	/**
	 */
	@Override
	public void testContainsPointPoint2D() {
		PathIterator2f pi = this.r.getPathIterator();
		assertElement(pi, PathElementType.MOVE_TO, 1f, 1f);
		assertElement(pi, PathElementType.LINE_TO, 2f, 2f);
		assertElement(pi, PathElementType.QUAD_TO, 3f, 0f, 4f, 3f);
		assertElement(pi, PathElementType.CURVE_TO, 5f, -1f, 6f, 5f, 7f, -5f);
		assertNoElement(pi);

		assertTrue(this.r.containsPoint(new Point2f(2f, 2f)));
		assertFalse(this.r.containsPoint(new Point2f(4f, 4f)));
		assertTrue(this.r.containsPoint(new Point2f(6f, 5f)));
		assertFalse(this.r.containsPoint(new Point2f(-1f, 6f)));
		assertFalse(this.r.containsPoint(new Point2f(1234f, 5678f)));
	}

}