/* 
 * $Id$
 * 
 * Copyright (C) 2005-09 Stephane GALLAND.
 * Copyright (C) 2012-13 Stephane GALLAND.
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
package org.arakhne.afc.ui.vector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.arakhne.afc.math.continous.object2d.Shape2f;
import org.arakhne.afc.math.matrix.Transform2D;
import org.arakhne.afc.ui.vector.Stroke.EndCap;
import org.arakhne.afc.ui.vector.Stroke.LineJoin;
import org.arakhne.vmutil.locale.Locale;

/** Utilities to create the widgets according
 * to the currently graphical API.
 *
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public abstract class VectorToolkit {
	
	private static VectorToolkit SINGLETON; 
	
	/** Replies the current generic windows toolkit.
	 * 
	 * @return the current generic windows toolkit.
	 */
	public static VectorToolkit getDefault() {
		return SINGLETON;
	}
	
	/** Register a type as Windows Toolkit.
	 * 
	 * @param type
	 */
	@SuppressWarnings("unchecked")
	private static void init() {
		Throwable error = null;
		String toolkits = Locale.getString("WINDOW_TOOLKITS"); //$NON-NLS-1$
		if (toolkits!=null && !"WINDOW_TOOLKITS".equals(toolkits)) { //$NON-NLS-1$
			String[] classes = toolkits.split("[ \t\n\f]*;[ \t\n\f]*"); //$NON-NLS-1$
		
			List<Class<? extends VectorToolkit>> types = new ArrayList<Class<? extends VectorToolkit>>();
			
			for(String className : classes) {
				try {
					Class<?> type = Class.forName(className);
					if (VectorToolkit.class.isAssignableFrom(type)) {
						types.add((Class<? extends VectorToolkit>)type);
					}
				}
				catch(Throwable _) {
					//
				}
			}
			
			if (!types.isEmpty()) {
				for(Class<? extends VectorToolkit> type : types) {
					try {
						VectorToolkit tk = type.newInstance();
						if (tk.isSupported()) {
							SINGLETON = tk;
							return;
						}
					}
					catch(Throwable e) {
						if (error==null) error = e;
					}
				}
			}
		}
		
		throw new Error("Unable to find a generic Window Toolkit. " //$NON-NLS-1$
				+"The supported toolkit classes are: " //$NON-NLS-1$
				+toolkits
				+". You must update your classpath with the Java archive that is containing one of them.", //$NON-NLS-1$
				error);
	}
	
	/** Replies if this toolkit may be used according to the current JVM settings.
	 * 
	 * @return <code>true</code> if the OS and the JVM are supported, otherwise <code>false</code>.
	 */
	protected abstract boolean isSupported();
	
	/** Cast the given object into an UI native object.
	 * 
	 * @param type is the desired type.
	 * @param o is the object to cast.
	 * @return the casted value.
	 */
	public static <T> T nativeUIObject(Class<T> type, Object o) {
		return SINGLETON.toNativeUIObject(type, o);
	}
	
	/** Cast the given object into an UI native object.
	 * 
	 * @param type is the desired type.
	 * @param o is the object to cast.
	 * @return the casted value.
	 */
	protected abstract <T> T toNativeUIObject(Class<T> type, Object o);
	
	//------------------------------
	// CONTEXT
	//------------------------------
	
	/** Must be invoked to prepare to draw in the given context.
	 * 
	 * @param context
	 */
	public static void prepareDrawing(VectorGraphics2D context) {
		SINGLETON.preDrawing(context);
	}

	/** Must be invoked to finalize the drawing in the given context.
	 * 
	 * @param context
	 */
	public static void finalizeDrawing(VectorGraphics2D context) {
		SINGLETON.postDrawing(context);
	}

	private VectorGraphics2D currentContext = null;
	
	/** Must be invoked to prepare to draw in the given context.
	 * 
	 * @param context
	 */
	protected void preDrawing(VectorGraphics2D context) {
		this.currentContext = context;
	}

	/** Must be invoked to finalize the drawing in the given context.
	 * 
	 * @param context
	 */
	protected void postDrawing(VectorGraphics2D context) {
		this.currentContext = null;
	}
	
	/** Replies the current drawing context.
	 * 
	 * @return the current drawing context.
	 */
	public VectorGraphics2D getCurrentDrawingContext() {
		return this.currentContext;
	}

	//------------------------------
	// Internal objects
	//------------------------------
	
	/** Replies the object with the given Id from the current toolkit.
	 * The objects associated to the ids depends on the VectorToolkit
	 * implementation.
	 * 
	 * @param id is the identifier of the object.
	 * @param type is the expected type for the object.
	 * @return the object, or <code>null</code> if there is no
	 * object with the given id or if the object is not of
	 * the correct type.
	 */
	public static <T> T getObjectWithId(int id, Class<T> type) {
		return SINGLETON.findObjectWithId(id,type);
	}
	
	/** Replies the object with the given Id from the current toolkit.
	 * The objects associated to the ids depends on the VectorToolkit
	 * implementation.
	 * 
	 * @param id is the identifier of the object.
	 * @param type is the expected type for the object.
	 * @return the object, or <code>null</code> if there is no
	 * object with the given id or if the object is not of
	 * the correct type.
	 */
	protected abstract <T> T findObjectWithId(int id, Class<T> type);

	//------------------------------
	// SHAPE
	//------------------------------

	/** Create a shape from a native object.
	 * <p>
	 * The type of the native object depends on the underlying graphic
	 * API. It is a <code>java.awt.Shape</code> for AWT.
	 * 
	 * @param nativeObject is the native object to translate.
	 * @return the shape.
	 */
	public static Shape2f shape(Object nativeObject) {
		return SINGLETON.createShape(nativeObject);
	}

	/** Create a shape.
	 * <p>
	 * The type of the native object depends on the underlying graphic
	 * API. It is a <code>java.awt.Shape</code> for AWT.
	 * 
	 * @param nativeObject is the native object to translate.
	 * @return the shape.
	 */
	protected abstract Shape2f createShape(Object nativeObject);

	//------------------------------
	// TRANSFORM2D
	//------------------------------

	/** Create the generic transform 2D from the low-level affine transform.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.geom.AffineTransform</code> for AWT.
	 *   
	 * @param affineTransform
	 * @return the transform
	 */
	public static Transform2D makeTransform(Object affineTransform) {
		return SINGLETON.createTransform(affineTransform);
	}

	/** Create the generic transform 2D from the low-level affine transform.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.geom.AffineTransform</code> for AWT.
	 *   
	 * @param affineTransform
	 * @return the transform
	 */
	protected abstract Transform2D createTransform(Object affineTransform);

	//------------------------------
	// DIMENSION
	//------------------------------

	/** Create a dimension
	 * 
	 * @param width
	 * @param height
	 * @return the dimension.
	 */
	public static Dimension dimension(float width, float height) {
		return SINGLETON.createDimension(width, height);
	}

	/** Create a dimension
	 * 
	 * @param width
	 * @param height
	 * @return the dimension.
	 */
	protected abstract Dimension createDimension(float width, float height);

	//------------------------------
	// MARGINS
	//------------------------------

	/** Create margins
	 * 
	 * @param top
	 * @param left
	 * @param right
	 * @param bottom
	 * @return the margins.
	 */
	public static Margins margins(float top, float left, float right, float bottom) {
		return SINGLETON.createMargins(top, left, right, bottom);
	}

	/** Create margins
	 * 
	 * @param top
	 * @param left
	 * @param right
	 * @param bottom
	 * @return the margins.
	 */
	protected abstract Margins createMargins(float top, float left, float right, float bottom);

	//------------------------------
	// PAINT
	//------------------------------

	/** Create a paint
	 * <p>
	 * The type of the native paint depends on the underlying graphic
	 * API. It is a <code>java.awt.Paint</code> for AWT.
	 * 
	 * @param paintObject
	 * @return the paint
	 */
	public static Paint paint(Object paintObject) {
		return SINGLETON.createPaint(paintObject);
	}

	/** Create a paint.
	 * <p>
	 * The type of the native paint depends on the underlying graphic
	 * API. It is a <code>java.awt.Composite</code> for AWT.
	 * 
	 * @param paintObject
	 * @return the paint
	 */
	protected abstract Paint createPaint(Object paintObject);

	//------------------------------
	// COMPOSITE
	//------------------------------

	/** Create a composite
	 * <p>
	 * The type of the native composite depends on the underlying graphic
	 * API. It is a <code>java.awt.Composite</code> for AWT.
	 * 
	 * @param compositeObject
	 * @return the composite
	 */
	public static Composite composite(Object compositeObject) {
		return SINGLETON.createComposite(compositeObject);
	}

	/** Create a composite
	 * <p>
	 * The type of the native composite depends on the underlying graphic
	 * API. It is a <code>java.awt.Composite</code> for AWT.
	 * 
	 * @param compositeObject
	 * @return the composite
	 */
	protected abstract Composite createComposite(Object compositeObject);

	/** Create an alpha composite.
	 * 
	 * @param alpha
	 * @return the composite.
	 */
	public static Composite composite(float alpha) {
		return SINGLETON.createComposite(alpha);
	}

	/** Create an alpha composite.
	 * 
	 * @param alpha
	 * @return the composite.
	 */
	protected abstract Composite createComposite(float alpha);

	//------------------------------
	// FONT
	//------------------------------

	/** Create a font.
	 * 
	 * @param name is the name of the font.
	 * @param style is the style of the font.
	 * @param size is the size of the font.
	 * @return the font.
	 */
	public static Font font(String name, FontStyle style, float size) {
		return SINGLETON.createFont(name, style, size);
	}

	/** Create a font.
	 * 
	 * @param name is the name of the font.
	 * @param style is the style of the font.
	 * @param size is the size of the font.
	 * @return the font.
	 */
	protected abstract Font createFont(String name, FontStyle style, float size);

	/** Create a font from the background font (AWT, Android...)
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.Font</code> for AWT.
	 * 
	 * @param fontObject
	 * @return the font.
	 */
	public static Font font(Object fontObject) {
		return SINGLETON.createFont(fontObject);
	}

	/** Create a font.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.Font</code> for AWT.
	 * 
	 * @param fontObject
	 * @return the font.
	 */
	protected abstract Font createFont(Object fontObject);

	/** Replies the default font.
	 * 
	 * @return the default font.
	 */
	public static Font font() {
		return SINGLETON.getDefaultFont();
	}

	/** Replies the default font.
	 * 
	 * @return the default font.
	 */
	protected abstract Font getDefaultFont();

	/** Create a font metrics.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.FontMetrics</code> for AWT.
	 * 
	 * @param metricsObject is the metrics of the font.
	 * @return the font metrics.
	 */
	public static FontMetrics fontMetrics(Object metricsObject) {
		return SINGLETON.createFontMetrics(metricsObject);
	}

	/** Create a font metrics.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.FontMetrics</code> for AWT.
	 * 
	 * @param metricsObject is the metrics of the font.
	 * @return the font metrics.
	 */
	protected abstract FontMetrics createFontMetrics(Object metricsObject);

	/** Create a font metrics for the given font.
	 * 
	 * @param font
	 * @return the font metrics.
	 */
	public static FontMetrics fontMetrics(Font font) {
		return SINGLETON.createFontMetrics(font);
	}

	/** Create a font metrics for the given font.
	 * 
	 * @param font
	 * @return the font metrics.
	 */
	protected abstract FontMetrics createFontMetrics(Font font);

	//------------------------------
	// IMAGE
	//------------------------------

	/** Create an image.
	 * 
	 * @param url
	 * @return the image
	 */
	public static Image image(URL url) {
		return SINGLETON.createImage(url);
	}

	/** Create an image.
	 * 
	 * @param url
	 * @return the image
	 */
	protected abstract Image createImage(URL url);

	/** Create an image.
	 * 
	 * @param stream
	 * @return the image
	 */
	public static Image image(InputStream stream) {
		return SINGLETON.createImage(stream);
	}

	/** Create an image.
	 * 
	 * @param stream
	 * @return the image
	 */
	protected abstract Image createImage(InputStream stream);

	/** Create an image.
	 * 
	 * @param width is the width of the new image.
	 * @param height is the height of the new image.
	 * @param isAlpha indicates if alpha color should be supported.
	 * @return the image
	 */
	public static Image image(int width, int height, boolean isAlpha) {
		return SINGLETON.createImage(width, height, isAlpha);
	}

	/** Create an image.
	 * 
	 * @param width is the width of the new image.
	 * @param height is the height of the new image.
	 * @param isAlpha indicates if alpha color should be supported.
	 * @return the image
	 */
	protected abstract Image createImage(int width, int height, boolean isAlpha);

	/** Create an image from the background image or icon (AWT, Android...)
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.Image</code>, <code>javax.swing.ImageIcon</code>, or 
	 * <code>javax.swing.Icon</code> for AWT.
	 * 
	 * @param imageObject
	 * @return the font.
	 */
	public static Image image(Object imageObject) {
		return SINGLETON.createImage(imageObject);
	}

	/** Create an image from the background image or icon (AWT, Android...).
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.Image</code> or <code>javax.swing.ImageIcon</code> for AWT.
	 * 
	 * @param imageObject
	 * @return the image.
	 */
	protected abstract Image createImage(Object imageObject);
	
	/** Create a transparent version of the given image.
	 * <p>
	 * <var>transparency</var>indicates how the alpha-component of the image is changed.
	 * The value is in <code>[-1;1]</code>.
	 * A value of <code>-1</code> means that the alpha-component is set to none.
	 * A value of <code>1</code> means that the alpha-component is completely
	 * set. A value of <code>0</code> means that the alpha-component
	 * <p>
	 * This function does not change the given image.
	 * See {@link #imageMakeTransparent(Image, float)} to
	 * change the transparency of the source image.
	 * 
	 * @param imageObject
	 * @param transparency is the transparency to apply to the given image
	 * @return the transparent image.
	 * @see #imageMakeTransparent(Image, float)
	 */
	public static Image image(Image imageObject, float transparency) {
		return SINGLETON.createTransparentImage(imageObject, transparency);
	}

	/** Make the given image more transparent.
	 * <p>
	 * <var>transparency</var>indicates how the alpha-component of the image is changed.
	 * The value is in <code>[-1;1]</code>.
	 * A value of <code>-1</code> means that the alpha-component is set to none.
	 * A value of <code>1</code> means that the alpha-component is completely
	 * set. A value of <code>0</code> means that the alpha-component
	 * <p>
	 * The given image is changed.
	 * See {@link #image(Image, float)} to
	 * avoid to change the transparency of the source image.
	 * 
	 * @param imageObject
	 * @param transparency is the transparency to apply to the given image
	 * @return the given <var>imageObject</var> if the source image is mutable; or
	 * a new image that is a transparent version of the source image if this
	 * source image is not mutable.
	 * @see #image(Image, float)
	 */
	public static Image imageMakeTransparent(Image imageObject, float transparency) {
		return SINGLETON.makeTransparentImage(imageObject, transparency);
	}

	/** Create a transparent version of the given image.
	 * <p>
	 * <var>transparency</var>indicates how the alpha-component of the image is changed.
	 * The value is in <code>[-1;1]</code>.
	 * A value of <code>-1</code> means that the alpha-component is set to none.
	 * A value of <code>1</code> means that the alpha-component is completely
	 * set. A value of <code>0</code> means that the alpha-component not changed.
	 * 
	 * @param imageObject
	 * @param transparency is the transparency to apply to the given image
	 * @return the transparent image.
	 */
	protected abstract Image createTransparentImage(Image imageObject, float transparency);

	/** Make the given image more transparent.
	 * <p>
	 * <var>transparency</var>indicates how the alpha-component of the image is changed.
	 * The value is in <code>[-1;1]</code>.
	 * A value of <code>-1</code> means that the alpha-component is set to none.
	 * A value of <code>1</code> means that the alpha-component is completely
	 * set. A value of <code>0</code> means that the alpha-component is not changed.
	 * 
	 * @param imageObject
	 * @param transparency is the transparency to apply to the given image
	 * @return the given <var>imageObject</var> if the source image is mutable; or
	 * a new image that is a transparent version of the source image if this
	 * source image is not mutable.
	 */
	protected abstract Image makeTransparentImage(Image imageObject, float transparency);

    /** Replies the specified image filtered with the given color.
	 * 
	 * @param imageObject is the image to filter.
     * @param filtering_color is the color used to filter.
     * @param alpha indicates how the icon and the color are merged. The value
     * is in <code>[0;1]</code>. A value of <code>0</code> means that the color 
     * of the icon is used. A value of <code>1</code> means that the given color
     * is used in place of the original colors. A value of <code>0.5</code>
     * means that the resulting color is at half way between the icon's color
     * and the given color. 
	 * @return the filtered image or <code>null</code>.
	 */
	public static Image image(Image imageObject, Color filtering_color, float alpha) {
		return SINGLETON.createColorizedImage(imageObject, filtering_color, alpha);
	}

    /** Replies the specified image filtered with the given color.
	 * 
	 * @param imageObject is the image to filter.
     * @param filtering_color is the color used to filter.
     * @param alpha indicates how the icon and the color are merged. The value
     * is in <code>[0;1]</code>. A value of <code>0</code> means that the color 
     * of the icon is used. A value of <code>1</code> means that the given color
     * is used in place of the original colors. A value of <code>0.5</code>
     * means that the resulting color is at half way between the icon's color
     * and the given color. 
	 * @return the filtered image or <code>null</code>.
	 */
	protected abstract Image createColorizedImage(Image imageObject, Color filtering_color, float alpha);

	/** Write the image.
	 * 
	 * @param image is the image to write.
	 * @param type is the type of encoding to use (png, jpeg...)
	 * @param stream is the output stream to write inside.
	 * @throws IOException
	 */
	public static void writeImage(Image image, String type, OutputStream stream) throws IOException {
		SINGLETON.write(image, type, stream);
	}
	
	/** Write the image.
	 * 
	 * @param image is the image to write.
	 * @param type is the type of encoding to use (png, jpeg...)
	 * @param stream is the output stream to write inside.
	 * @throws IOException
	 */
	protected abstract void write(Image image, String type, OutputStream stream) throws IOException;

	/** Replies the system background selection color.
	 * 
	 * @return the color of the background for the color.
	 */
	public static Color getSelectionBackground() {
		return SINGLETON.createSelectionBackground();
	}
	
	/** Replies the system background selection color.
	 * 
	 * @return the color of the background for the color.
	 */
	protected abstract Color createSelectionBackground();

	/** Replies the system background selection color.
	 * 
	 * @return the color of the background for the color.
	 */
	public static Color getSelectionForeground() {
		return SINGLETON.createSelectionBackground();
	}
	
	/** Replies the system background selection color.
	 * 
	 * @return the color of the background for the color.
	 */
	protected abstract Color createSelectionForeground();

	//------------------------------
	// STROKE
	//------------------------------

	/** Create a stroke
	 * 
	 * @param width
	 * @return the stroke
	 */
	public static Stroke stroke(float width) {
		return SINGLETON.createStroke(width, Stroke.DEFAULT_LINE_JOIN, Stroke.DEFAULT_END_CAP, Stroke.DEFAULT_MITTER_LIMIT, null, 0);
	}
	
	/** Create a stroke
	 * 
	 * @param width
	 * @param lineJoin
	 * @param endCap
	 * @param mitterLimit
	 * @return the stroke
	 */
	public static Stroke stroke(float width, LineJoin lineJoin, EndCap endCap, float mitterLimit) {
		return SINGLETON.createStroke(width, lineJoin, endCap, mitterLimit, null, 0);
	}

	/** Create a stroke
	 * 
	 * @param width
	 * @param lineJoin
	 * @param endCap
	 * @param mitterLimit
	 * @param dashes
	 * @param dashPhase
	 * @return the stroke
	 */
	public static Stroke stroke(float width, LineJoin lineJoin, EndCap endCap, float mitterLimit, float[] dashes, float dashPhase) {
		return SINGLETON.createStroke(width, lineJoin, endCap, mitterLimit, dashes, dashPhase);
	}

	/** Create a stroke
	 * 
	 * @param width
	 * @param join
	 * @param endCap
	 * @param mitterLimit
	 * @param dashes
	 * @param dashPhase
	 * @return the stroke
	 */
	protected abstract Stroke createStroke(float width, LineJoin join, EndCap endCap, float mitterLimit, float[] dashes, float dashPhase);

	/** Create a stroke
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.BasicStroke</code> for AWT.
	 * 
	 * @param strokeObject
	 * @return the stroke
	 */
	public static Stroke stroke(Object strokeObject) {
		return SINGLETON.createStroke(strokeObject);
	}

	/** Create a stroke
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.BasicStroke</code> for AWT.
	 * 
	 * @param strokeObject
	 * @return the stroke
	 */
	protected abstract Stroke createStroke(Object strokeObject);

	//------------------------------
	// COLOR
	//------------------------------
	
    /**
     * The color white.  In the default sRGB space.
     * @deprecated see {@link Colors#WHITE}
     */
	@Deprecated
    public final static Color WHITE;

    /**
     * The color light gray.  In the default sRGB space.
     * @deprecated see {@link Colors#LIGHT_GRAY}
     */
	@Deprecated
    public final static Color LIGHT_GRAY;

    /**
     * The color gray.  In the default sRGB space.
     * @deprecated see {@link Colors#GRAY}
     */
	@Deprecated
    public final static Color GRAY;

    /**
     * The color dark gray.  In the default sRGB space.
     * @deprecated see {@link Colors#DARK_GRAY}
     */
	@Deprecated
    public final static Color DARK_GRAY;

    /**
     * The color black.  In the default sRGB space.
     * @deprecated see {@link Colors#BLACK}
     */
	@Deprecated
    public final static Color BLACK;

    /**
     * The color red.  In the default sRGB space.
     * @deprecated see {@link Colors#RED}
     */
	@Deprecated
    public final static Color RED;

    /**
     * The color pink.  In the default sRGB space.
     * @deprecated see {@link Colors#PINK}
     */
	@Deprecated
    public final static Color PINK;

    /**
     * The color orange.  In the default sRGB space.
     * @deprecated see {@link Colors#ORANGE}
     */
	@Deprecated
    public final static Color ORANGE;

    /**
     * The color yellow.  In the default sRGB space.
     * @deprecated see {@link Colors#YELLOW}
     */
	@Deprecated
    public final static Color YELLOW;

    /**
     * The color green.  In the default sRGB space.
     * @deprecated see {@link Colors#GREEN}
     */
	@Deprecated
    public final static Color GREEN;

    /**
     * The color magenta.  In the default sRGB space.
     * @deprecated see {@link Colors#MAGENTA}
     */
	@Deprecated
    public final static Color MAGENTA;

    /**
     * The color cyan.  In the default sRGB space.
     * @deprecated see {@link Colors#CYAN}
     */
	@Deprecated
    public final static Color CYAN;

    /**
     * The color blue.  In the default sRGB space.
     * @deprecated see {@link Colors#BLUE}
     */
	@Deprecated
    public final static Color BLUE;

    /** Create a color.
	 * 
	 * @param red is the red component in [0;255].
	 * @param green is the green component in [0;255].
	 * @param blue is the blue component in [0;255].
	 * @param alpha is the alpha component in [0;255], 0 is fully transparent, 255 is fully opaque.
	 * @return the color.
	 */
	public static Color color(int red, int green, int blue, int alpha) {
		return SINGLETON.createColor(red, green, blue, alpha);
	}

    /** Create a color. Alpha is 255.
	 * 
	 * @param red is the red component in [0;255].
	 * @param green is the green component in [0;255].
	 * @param blue is the blue component in [0;255].
	 * @return the color.
	 */
	public static Color color(int red, int green, int blue) {
		return SINGLETON.createColor(red, green, blue, 255);
	}

	/** Create a color.
	 * 
	 * @param red is the red component in [0;1].
	 * @param green is the green component in [0;1].
	 * @param blue is the blue component in [0;1].
	 * @param alpha is the alpha component in [0;1], 0 is fully transparent, 1 is fully opaque.
	 * @return the color.
	 */
	public static Color color(float red, float green, float blue, float alpha) {
		return SINGLETON.createColor(
				(int)(red*255f+.5f),
				(int)(green*255f+.5f),
				(int)(blue*255f+.5f),
				(int)(alpha*255f+.5f));
	}

	/** Create a color. Alpha is 1.
	 * 
	 * @param red is the red component in [0;1].
	 * @param green is the green component in [0;1].
	 * @param blue is the blue component in [0;1].
	 * @return the color.
	 */
	public static Color color(float red, float green, float blue) {
		return SINGLETON.createColor(
				(int)(red*255f+.5f),
				(int)(green*255f+.5f),
				(int)(blue*255f+.5f),
				255);
	}

	/** Create a color.
	 * 
	 * @param red is the red component in [0;255].
	 * @param green is the green component in [0;255].
	 * @param blue is the blue component in [0;255].
	 * @param alpha is the alpha component in [0;255], 0 is fully transparent, 255 is fully opaque.
	 * @return the color.
	 */
	protected abstract Color createColor(int red, int green, int blue, int alpha);

	/** Create an opaque color.
	 * 
	 * @param rgb are the red, green and blue components
	 * @return the color.
	 */
	public static Color color(int rgb) {
		return SINGLETON.createColor(
				((rgb >> 16) & 0xFF),
				((rgb >> 8) & 0xFF),
				(rgb & 0xFF),
				255);
	}

	/** Create a color.
	 * 
	 * @param rgb are the red, green and blue components
	 * @param hasAlpa indicates if <var>rgb</var> contains alpha component.
	 * @return the color.
	 */
	public static Color color(int rgb, boolean hasAlpa) {
		if (hasAlpa) {
			return SINGLETON.createColor(
					((rgb >> 16) & 0xFF),
					((rgb >> 8) & 0xFF),
					(rgb & 0xFF),
					((rgb >> 24) & 0xFF));
		}
		return SINGLETON.createColor(
				((rgb >> 16) & 0xFF),
				((rgb >> 8) & 0xFF),
				(rgb & 0xFF),
				255);
	}
	
    /** Create a color from a underground API color.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.Color</code> for AWT.
	 * 
	 * @param rawColorObject is the color in the AWT, SWT or Android API. 
	 * @return the generic color.
	 */
	public static Color color(Object rawColorObject) {
		return SINGLETON.createColor(rawColorObject);
	}

	/** Create a color from a underground API color.
	 * <p>
	 * The type of the native affine transform depends on the underlying graphic
	 * API. It is a <code>java.awt.Color</code> for AWT.
	 * 
	 * @param rawColorObject is the color in the AWT, SWT or Android API. 
	 * @return the generic color.
	 */
	protected abstract Color createColor(Object rawColorObject);

    /** Decode the color from a string.
     * The supported formats are :
     * #RRGGBB #AARRGGBB 'red', 'blue', 'green', 'black', 'white',
     * 'gray', 'cyan', 'magenta', 'yellow', 'lightgray', 'darkgray',
     * 'orange', 'pink'.
     * This method handles string
     * formats that are used to represent octal and hexadecimal numbers.
	 * 
	 * @param c is the string representation of the color. 
	 * @return the generic color.
	 */
	public static Color color(String c) {
		if ("red".equalsIgnoreCase(c)) return RED; //$NON-NLS-1$
		if ("blue".equalsIgnoreCase(c)) return BLUE; //$NON-NLS-1$
		if ("green".equalsIgnoreCase(c)) return GREEN; //$NON-NLS-1$
		if ("black".equalsIgnoreCase(c)) return BLACK; //$NON-NLS-1$
		if ("white".equalsIgnoreCase(c)) return WHITE; //$NON-NLS-1$
		if ("gray".equalsIgnoreCase(c)) return GRAY; //$NON-NLS-1$
		if ("cyan".equalsIgnoreCase(c)) return CYAN; //$NON-NLS-1$
		if ("magenta".equalsIgnoreCase(c)) return MAGENTA; //$NON-NLS-1$
		if ("yellow".equalsIgnoreCase(c)) return YELLOW; //$NON-NLS-1$
		if ("lightgray".equalsIgnoreCase(c)) return LIGHT_GRAY; //$NON-NLS-1$
		if ("darkgray".equalsIgnoreCase(c)) return DARK_GRAY; //$NON-NLS-1$
		if ("orange".equalsIgnoreCase(c)) return ORANGE; //$NON-NLS-1$
		if ("pink".equalsIgnoreCase(c)) return PINK; //$NON-NLS-1$
		Integer intval = Integer.decode(c);
        int i = intval.intValue();
        int a;
        if (i>0xFFFFFF) a = (i >> 32) & 0xFF;
        else a = 255;
        int r = (i >> 16) & 0xFF;
        int g = (i >> 8) & 0xFF;
        int b = i & 0xFF;
		return color(r, g, b, a);
	}

    /** Creates a <code>Color</code> object based on the specified values
     * for the HSB color model.
     * <p>
     * The <code>s</code> and <code>b</code> components should be
     * floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>h</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.
     * @param  h   the hue component
     * @param  s   the saturation of the color
     * @param  b   the brightness of the color
	 * @return the generic color.
	 */
	public static Color colorHSB(float h, float s, float b) {
		return color(SINGLETON.HSBtoRGB(h, s, b));
	}
	
    /**
     * Converts the components of a color, as specified by the HSB
     * model, to an equivalent set of values for the default RGB model.
     * <p>
     * The <code>saturation</code> and <code>brightness</code> components
     * should be floating-point values between zero and one
     * (numbers in the range 0.0-1.0).  The <code>hue</code> component
     * can be any floating-point number.  The floor of this number is
     * subtracted from it to create a fraction between 0 and 1.  This
     * fractional number is then multiplied by 360 to produce the hue
     * angle in the HSB color model.

     * @param     hue   the hue component of the color
     * @param     saturation   the saturation of the color
     * @param     brightness   the brightness of the color
     * @return    the RGB value of the color with the indicated hue,
     *                            saturation, and brightness.
     */
    protected abstract int HSBtoRGB(float hue, float saturation, float brightness);
	
	//------------------------------
	// STATIC INIT
	//------------------------------
	
	static {
		init();
		WHITE = color(255, 255, 255, 255);
		LIGHT_GRAY = color(192, 192, 192, 255);
		GRAY = color(128, 128, 128, 255);
		DARK_GRAY = color(64, 64, 64, 255);
		BLACK = color(0, 0, 0, 255);
		RED = color(255, 0, 0, 255);
		PINK = color(255, 175, 175, 255);
		ORANGE = color(255, 200, 0, 255);
		YELLOW = color(255, 255, 0, 255);
		GREEN = color(0, 255, 0, 255);
		MAGENTA = color(255, 0, 255, 255);
		CYAN = color(0, 255, 255, 255);
		BLUE = color(0, 0, 255, 255);
	}

	//------------------------------
	// PDF
	//------------------------------
	
	/** Read a PDF file and reply its wrapper.
	 * 
	 * @param url is the address of the stream to read.
	 * @return the PDF wrapper.
	 * @throws IOException
	 */
	public static Pdf pdf(URL url) throws IOException {
		return SINGLETON.wrapPdf(url);
	}

	/** Read a PDF file and reply its wrapper.
	 * 
	 * @param url is the address of the stream to read.
	 * @return the PDF wrapper.
	 * @throws IOException
	 */
	protected abstract Pdf wrapPdf(URL url) throws IOException;

}