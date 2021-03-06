/* 
 * $Id$
 * 
 * Copyright (C) 2004-2009 Stephane GALLAND.
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

package org.arakhne.vmutil;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

/**
 * This utility class provides a way to extend the reflection API and
 * the Class class with autoboxing-compliant functions.
 * 
 * @author $Author: galland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since since JDK 1.5
 */
public class ReflectionUtil {

    /**
     * Determines if the specified <code>Object</code> is assignment-compatible
     * with the object represented by the <code>Class</code>.  This method extends
     * {@link Class#isInstance(Object)} with autoboxing support.
     *
     * @param type is the class against which the object must be test
     * @param obj is the object to check
     * @return <code>true</code> if <code>obj</code> is an instance of the type
     * @see Class#isInstance(Object)
     */
    public static boolean isInstance(Class<?> type, Object obj) {
    	assert(type!=null);
    	if (obj==null) return false;

    	// Test according to the Class's behaviour
    	if (type.isInstance(obj)) return true;
    	
    	// Test according to autoboxing
    	if (type.isPrimitive()
    			&&
    			type!=Void.class && type!=void.class) {
    		
    		if (type==Boolean.class) return boolean.class.isInstance(obj);
    		if (type==boolean.class) return Boolean.class.isInstance(obj);
    		
    		if (type==Character.class) return char.class.isInstance(obj);
    		if (type==char.class) return Character.class.isInstance(obj);

    		if (type==Byte.class) return byte.class.isInstance(obj);
    		if (type==byte.class) return Byte.class.isInstance(obj);

    		if (type==Short.class) return short.class.isInstance(obj);
    		if (type==short.class) return Short.class.isInstance(obj);

    		if (type==Integer.class) return int.class.isInstance(obj);
    		if (type==int.class) return Integer.class.isInstance(obj);

    		if (type==Long.class) return long.class.isInstance(obj);
    		if (type==long.class) return Long.class.isInstance(obj);

    		if (type==Float.class) return float.class.isInstance(obj);
    		if (type==float.class) return Float.class.isInstance(obj);
    		
    		if (type==Double.class) return double.class.isInstance(obj);
    		if (type==double.class) return Double.class.isInstance(obj);

    		if (type==Void.class) return void.class.isInstance(obj);
    		if (type==void.class) return Void.class.isInstance(obj);
    		
    		assert false: "Unsupported primitive type"; //$NON-NLS-1$
    	}
    	
    	return false;
    }


    /**
     * Determines if the <code>assignmentTarget</code> object is either the same as,
     * or is a superclass or superinterface of, the class or interface 
     * represented by the specified
     * <code>assignementSource</code> parameter. This method extends
     * {@link Class#isAssignableFrom(Class)} with autoboxing support.
     * 
     * @param assignementTarget is the class that is tested to be a super class.
     * @param assignementSource is the class that is tested to be a sub class.
     * @return <code>true</code> if an object of the <var>assignementSource</var> type
     * could be assigned to a variable of <var>assignementTarget</var> type,
     * otherwise <code>false</code>.
     */
    public static boolean isAssignableFrom(Class<?> assignementTarget, Class<?> assignementSource) {
    	assert(assignementSource!=null);
    	assert(assignementTarget!=null);
    	
    	// Test according to the Class's behaviour
    	if (assignementTarget.isAssignableFrom(assignementSource)) return true;
    	
    	// Test according to autoboxing
    	if (assignementTarget.isPrimitive() && assignementSource.isPrimitive()
    			&&
    			assignementTarget!=Void.class && assignementTarget!=void.class
    			&&
    			assignementSource!=Void.class && assignementSource!=void.class) {
    		return true;
    	}
    	
    	return false;
    }

	/** Replies the type that corresponds to the specified class.
	 * If the name corresponds to a primitive type, the low-level type
	 * will be replied.
	 * This method extends
     * {@link Class#forName(String)} with autoboxing support.
	 * 
	 * @param name is the name of the class to load.
	 * @return the loaded class
	 * @throws ClassNotFoundException  if name names an
	 * unknown class or primitive
	 */
	public static Class<?> forName(String name) throws ClassNotFoundException {
		if (name == null || "".equals(name) || "null".equals(name) || "void".equals(name)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return void.class;
		}
		if ("boolean".equals(name)) { //$NON-NLS-1$
			return boolean.class;
		}
		if ("byte".equals(name)) { //$NON-NLS-1$
			return byte.class;
		}
		if ("char".equals(name)) { //$NON-NLS-1$
			return char.class;
		}
		if ("double".equals(name)) { //$NON-NLS-1$
			return double.class;
		}
		if ("float".equals(name)) { //$NON-NLS-1$
			return float.class;
		}
		if ("int".equals(name)) { //$NON-NLS-1$
			return int.class;
		}
		if ("long".equals(name)) { //$NON-NLS-1$
			return long.class;
		}
		if ("short".equals(name)) { //$NON-NLS-1$
			return short.class;
		}
		return Class.forName(name);
	}

	/** Replies the type that corresponds to the specified class.
	 * If the name corresponds to a primitive type, the low-level type
	 * will be replied.
	 * This method extends
     * {@link Class#forName(String)} with autoboxing support.
	 * 
	 * @param name is the name of the class to load.
	 * @param loader is the class loader to use.
	 * @return the loaded class
	 * @throws ClassNotFoundException  if name names an
	 * unknown class or primitive
	 */
	public static Class<?> forName(String name, ClassLoader loader) throws ClassNotFoundException {
		return forName(name, true, loader);
	}

	/** Replies the type that corresponds to the specified class.
	 * If the name corresponds to a primitive type, the low-level type
	 * will be replied.
	 * This method extends
     * {@link Class#forName(String)} with autoboxing support.
	 * 
	 * @param name is the name of the class to load.
	 * @param typeInitialization must be <code>true</code> to initialize the type, <code>false</code> otherwise.
	 * @param loader is the class loader to use.
	 * @return the loaded class
	 * @throws ClassNotFoundException  if name names an
	 * unknown class or primitive
	 */
	public static Class<?> forName(String name, boolean typeInitialization, ClassLoader loader) throws ClassNotFoundException {
		if (name == null || "".equals(name) || "null".equals(name) || "void".equals(name)) { //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			return void.class;
		}
		if ("boolean".equals(name)) { //$NON-NLS-1$
			return boolean.class;
		}
		if ("byte".equals(name)) { //$NON-NLS-1$
			return byte.class;
		}
		if ("char".equals(name)) { //$NON-NLS-1$
			return char.class;
		}
		if ("double".equals(name)) { //$NON-NLS-1$
			return double.class;
		}
		if ("float".equals(name)) { //$NON-NLS-1$
			return float.class;
		}
		if ("int".equals(name)) { //$NON-NLS-1$
			return int.class;
		}
		if ("long".equals(name)) { //$NON-NLS-1$
			return long.class;
		}
		if ("short".equals(name)) { //$NON-NLS-1$
			return short.class;
		}
		return Class.forName(name, typeInitialization, loader);
	}

	/**
	 * Replies the list of the classes in the given package.
	 * @param pkg is the package to explore.
	 * @return the list of classes in the package.
	 */
	public static Collection<Class<?>> getPackageClasses(Package pkg) {
		return getPackageClasses(pkg.getName());
	}

	/**
	 * Replies the list of the classes in the given package.
	 * @param packageName is the name of the package to explore.
	 * @return the list of classes in the package.
	 */
	public static Collection<Class<?>> getPackageClasses(String packageName) {
		Collection<Class<?>> classes = new ArrayList<Class<?>>();
	 
		String[] entries = System.getProperty("java.class.path").split( //$NON-NLS-1$
				Pattern.quote(System.getProperty("path.separator"))); //$NON-NLS-1$
		String lentry;			
		
		for(String path : entries) {
			lentry = path.toLowerCase();
			if(lentry.endsWith(".jar") || lentry.endsWith(".war")) { //$NON-NLS-1$//$NON-NLS-2$
				getPackageClassesFromJar(classes, path, packageName);
			}else{
				getPackageClassesFromFileSystem(classes, path, packageName);
			}
	 
		}
	 
		return classes;
	}
	
	private static String basename(String name) {
		int idx = name.lastIndexOf('/');
		if (idx>=0 && idx<name.length()) {
			return name.substring(idx+1);
		}
		return name;
	}

	private static String filename(String name) {
		String basename = basename(name);
		int idx = basename.indexOf('.');
		if (idx>=0 && idx<basename.length()) {
			return basename.substring(0,idx);
		}
		return name;
	}

	private static void getPackageClassesFromJar(Collection<Class<?>> classes, String jarFilename, String packageName) {
		try {
			JarFile jarFile = new JarFile(jarFilename);
			try {
				String packagePath = packageName.replace(".", "/"); //$NON-NLS-1$//$NON-NLS-2$
		
				Enumeration<JarEntry> entries = jarFile.entries();
				JarEntry entry;
				String entryPath, entryClassname;
				
				while (entries.hasMoreElements()) {
					entry = entries.nextElement();
					entryPath = entry.getName();
		
					// In package and not inner class
					if (entryPath.startsWith(packagePath)
						&& !entryPath.endsWith("/") //$NON-NLS-1$
						&& !entryPath.contains("$")) { //$NON-NLS-1$
						entryClassname = packageName + "." + filename(entryPath); //$NON-NLS-1$
						try {
							classes.add(Class.forName(entryClassname));
						}
						catch(ClassNotFoundException _) {
							//
						}
					}	
				}
			}
			finally {
				jarFile.close();
			}
		}
		catch(IOException _) {
			//
		}
	}

	private static void getPackageClassesFromFileSystem(Collection<Class<?>> classes, String directory, String packageName) {
		String packagePath = packageName.replace(".", File.separator); //$NON-NLS-1$
		File packageDirectory = new File(directory, packagePath);
		String entryClassname;
		
		if (packageDirectory.isDirectory()) {
			for(String entryPath : packageDirectory.list()) {
	
				// In package and not inner class
				if (!entryPath.contains("$")) { //$NON-NLS-1$
					entryClassname = packageName + "." + FileSystem.shortBasename(entryPath); //$NON-NLS-1$
					try {
						classes.add(Class.forName(entryClassname));
					}
					catch(AssertionError e) {
						throw e;
					}
					catch(Throwable _) {
						//
					}
				}
			}
		}
	}
	
	/**
	 * Replies the list of all the subclasses of the given class
	 * in the current classpath.
	 * 
	 * @param <T> is the type of the superclass.
	 * @param className is the name of the class to explore.
	 * @return the list of subclasses.
	 */
	public static <T> Collection<Class<? extends T>> getSubClasses(Class<T> className) {
		Collection<Class<? extends T>> list = new ArrayList<Class<? extends T>>();
		getSubClasses(className, true, true, true, list);
		return list;
	}

	/**
	 * Replies the list of all the subclasses of the given class
	 * in the current classpath.
	 * 
	 * @param <T> is the type of the superclass.
	 * @param className is the name of the class to explore.
	 * @param allowAbstract is <code>true</code> to allow abstract classes to be put in the replied list
	 * @param allowInterface is <code>true</code> to allow interfaces to be put in the replied list.
	 * @param allowEnum is <code>true</code> to allow enumeration to be put in the replied list.
	 * @param result is the list of subclasses which will be filled by this function.
	 */
	public static <T> void getSubClasses(Class<T> className, boolean allowAbstract, boolean allowInterface, boolean allowEnum, Collection<Class<? extends T>> result) {
		String[] entries = System.getProperty("java.class.path").split( //$NON-NLS-1$
				Pattern.quote(System.getProperty("path.separator"))); //$NON-NLS-1$
		String lentry;			
		
		for(String path : entries) {
			lentry = path.toLowerCase();
			if(lentry.endsWith(".jar") || lentry.endsWith(".war")) { //$NON-NLS-1$//$NON-NLS-2$
				getSubClassesFromJar(result, path, className, allowAbstract, allowInterface, allowEnum);
			}else{
				getSubClassesFromFileSystem(result, path, className, allowAbstract, allowInterface, allowEnum);
			}
	 
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> void getSubClassesFromJar(Collection<Class<? extends T>> classes, String jarFilename, Class<T> className, boolean allowAbstract, boolean allowInterface, boolean allowEnum) {
		try {
			JarFile jarFile = new JarFile(jarFilename);
			try {
				String classN = className.getCanonicalName();
				if (classN!=null) {
					Enumeration<JarEntry> entries = jarFile.entries();
					JarEntry entry;
					String entryPath, entryClassname;
					
					while (entries.hasMoreElements()) {
						entry = entries.nextElement();
						entryPath = entry.getName();
			
						// In package and not inner class
						if (entryPath.endsWith(".class") //$NON-NLS-1$
							&& !entryPath.contains("$")) { //$NON-NLS-1$
							entryClassname = entryPath.substring(0, entryPath.length()-6).replaceAll(
									Pattern.quote(File.separator), "."); //$NON-NLS-1$
							try {
								Class<?> clazz = Class.forName(entryClassname);
								if ((className.isAssignableFrom(clazz))
									&&(allowAbstract || !Modifier.isAbstract(clazz.getModifiers()))
									&&(allowInterface || !clazz.isInterface())
									&&(allowEnum || !clazz.isEnum()))
									classes.add((Class<? extends T>)clazz);
							}
							catch(AssertionError e) {
								throw e;
							}
							catch(Throwable _) {
								//
							}
						}	
					}
				}
			}
			finally {
				jarFile.close();
			}
		}
		catch(IOException _) {
			//
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> void getSubClassesFromFileSystem(Collection<Class<? extends T>> classes, String directory, Class<T> className, boolean allowAbstract, boolean allowInterface, boolean allowEnum) {
		String classN = className.getCanonicalName();
		if (classN!=null) {
			List<String> directories = new ArrayList<String>();
			directories.add(""); //$NON-NLS-1$
			
			String ldir, entryClassname;
			File dir, fullFile;
			
			while (!directories.isEmpty()) {
				ldir = directories.remove(0);
				dir = new File(directory, ldir);
				if (dir.isDirectory()) {
					for(String entryPath : dir.list()) {
			
						fullFile = new File(dir,entryPath);
						
						if (fullFile.isDirectory()) {
							if (ldir==null || "".equals(ldir)) { //$NON-NLS-1$
								directories.add(entryPath);
							}
							else {
								directories.add(new File(ldir,entryPath).toString());
							}
						}
						// In package and not inner class
						else if (entryPath.endsWith(".class") //$NON-NLS-1$
							&& !entryPath.contains("$")) { //$NON-NLS-1$
							assert(ldir!=null);
							entryClassname = ldir.replaceAll(
									Pattern.quote(File.separator), ".") //$NON-NLS-1$
									+ "." + FileSystem.shortBasename(entryPath); //$NON-NLS-1$
							try {
								Class<?> clazz = Class.forName(entryClassname);
								if ((className.isAssignableFrom(clazz))
									&&(allowAbstract || !Modifier.isAbstract(clazz.getModifiers()))
									&&(allowInterface || !clazz.isInterface())
									&&(allowEnum || !clazz.isEnum()))
									classes.add((Class<? extends T>)clazz);
							}
							catch(AssertionError e) {
								throw e;
							}
							catch(Throwable _) {
								//
							}
						}
					}
				}
			}
		}
	}
	
	/**
     * Determines the interfaces implemented by the classes from the lowest type
     * to the highestType which are extended the given interfaceType.
     * <p>
     * Insteed of {@link Class#getInterfaces()}, this function is exploring
     * the super classes. This function does not explore super-interfaces
     * of implemented interfaces.
     * <p>
     * <pre><code>
     * interface IA {}
     * interface IB extends IA {}
     * interface IC {}
     * interface ID extends IB, IC {}
     * class CA implements IC {}
     * class CB extends CA {}
     * class CC extends CB implements IB {}
     * </code></pre>
     * This function replies for:
     * <ul>
     * <li><code>getAllDirectInterfaces(IA,null,null)</code>=<code>{}</code></li>
     * <li><code>getAllDirectInterfaces(IB,null,null)</code>=<code>{IA}</code></li>
     * <li><code>getAllDirectInterfaces(IC,null,null)</code>=<code>{}</code></li>
     * <li><code>getAllDirectInterfaces(ID,null,null)</code>=<code>{IB,IC}</code></li>
     * <li><code>getAllDirectInterfaces(CA,null,null)</code>=<code>{IC}</code></li>
     * <li><code>getAllDirectInterfaces(CB,null,null)</code>=<code>{IC}</code></li>
     * <li><code>getAllDirectInterfaces(CC,null,null)</code>=<code>{IB,IC}</code></li>
     * </ul>
	 * 
	 * @param <T> is the highest type to explore in type hierarchy.
	 * @param <I> indicates the type of the replied interfaces.
	 * @param lowestType is the lowest type to explore in type hierarchy.
	 * @param highestType is the highest type to explore in type hierarchy.
	 * @param interfaceType indicates the type of the replied interfaces.
	 * @return the implemented interfaces.
	 * @since 5.0
	 */
	@SuppressWarnings("unchecked")
	public static <T,I> Set<Class<? extends I>> getAllDirectInterfaces(Class<? extends T> lowestType, Class<T> highestType, Class<I> interfaceType) {
		assert(lowestType!=null);
		Set<Class<? extends I>> collection = new TreeSet<Class<? extends I>>(ClassComparator.SINGLETON);
		Class<?> type = lowestType;
		boolean cont;
		do {
			for(Class<?> directInterface : type.getInterfaces()) {
				if (interfaceType==null || interfaceType.isAssignableFrom(directInterface))
						collection.add((Class<? extends I>)directInterface);
			}
			cont = (highestType==null || !type.equals(highestType));
			type = type.getSuperclass();
		}
		while (type!=null && cont);
		return collection;
	}
	
	/**
     * Determines the interfaces implemented by the classes from the lowest type
     * to the highestType which are extended the given interfaceType.
     * <p>
     * Insteed of {@link Class#getInterfaces()}, this function is exploring
     * the super classes. This function does not explore super-interfaces
     * of implemented interfaces.
     * <p>
     * <pre><code>
     * interface IA {}
     * interface IB extends IA {}
     * interface IC {}
     * interface ID extends IB, IC {}
     * class CA implements IC {}
     * class CB extends CA {}
     * class CC extends CB implements IB {}
     * </code></pre>
     * This function replies for:
     * <ul>
     * <li><code>getAllDirectInterfaces(IA,null,null)</code>=<code>{}</code></li>
     * <li><code>getAllDirectInterfaces(IB,null,null)</code>=<code>{IA}</code></li>
     * <li><code>getAllDirectInterfaces(IC,null,null)</code>=<code>{}</code></li>
     * <li><code>getAllDirectInterfaces(ID,null,null)</code>=<code>{IB,IC}</code></li>
     * <li><code>getAllDirectInterfaces(CA,null,null)</code>=<code>{IC}</code></li>
     * <li><code>getAllDirectInterfaces(CB,null,null)</code>=<code>{IC}</code></li>
     * <li><code>getAllDirectInterfaces(CC,null,null)</code>=<code>{IB,IC}</code></li>
     * </ul>
	 * 
	 * @param <T> is the highest type to explore in type hierarchy.
	 * @param lowestType is the lowest type to explore in type hierarchy.
	 * @param highestType is the highest type to explore in type hierarchy.
	 * @return the implemented interfaces.
	 * @since 5.0
	 */
	public static <T> Set<Class<?>> getAllDirectInterfaces(Class<? extends T> lowestType, Class<T> highestType) {
		assert(lowestType!=null);
		Set<Class<?>> collection = new TreeSet<Class<?>>(ClassComparator.SINGLETON);
		Class<?> type = lowestType;
		boolean cont;
		do {
			for(Class<?> directInterface : type.getInterfaces()) {
				collection.add(directInterface);
			}
			cont = (highestType==null || !type.equals(highestType));
			type = type.getSuperclass();
		}
		while (type!=null && cont);
		return collection;
	}

	/**
	 * Replies the list of all the superclasses of the given class.
	 * <p>
	 * This function does not replies <code>Object.class</code>.
	 * 
	 * @param <T> is the type of the lowest class.
	 * @param className is the type of the lowest class.
	 * @return the list of superclasses.
	 * @since 5.0
	 */
	public static <T> Collection<Class<? super T>> getSuperClasses(Class<T> className) {
		assert(className!=null);
		Collection<Class<? super T>> list = new ArrayList<Class<? super T>>();
		Class<? super T> type = className.getSuperclass();
		while (type!=null && !Object.class.equals(type)) {
			list.add(type);
			type = type.getSuperclass();
		}
		return list;
	}
	
	/** Replies the top-most type which is common to both given types.
	 * 
	 * @param type1
	 * @param type2
	 * @return the top-most type which is common to both given types.
	 * @since 6.0
	 */
	public static Class<?> getCommonType(Class<?> type1, Class<?> type2) {
		if (type1==null) return type2;
		if (type2==null) return type1;
		Class<?> top = type1;
		while (!top.isAssignableFrom(type2)) {
			top = top.getSuperclass();
			assert(top!=null);
		}
		return top;
	}

	/** Replies the top-most type which is common to both given objects.
	 * 
	 * @param instance1
	 * @param instance2
	 * @return the top-most type which is common to both given objects.
	 * @since 6.0
	 */
	public static Class<?> getCommonType(Object instance1, Object instance2) {
		if (instance1==null ) {
			return instance2==null ? null : instance2.getClass();
		}
		if (instance2==null) return instance1.getClass();
		Class<?> top = instance1.getClass();
		while (!top.isInstance(instance2)) {
			top = top.getSuperclass();
			assert(top!=null);
		}
		return top;
	}
	
	/** Replies the outboxing type for the given type.
	 * 
	 * @param type
	 * @return the outboxing of the given type.
	 * @since 7.1
	 */
	public static Class<?> getOutboxingType(Class<?> type) {
		if (void.class.equals(type)) {
			return Void.class;
		}
		if (boolean.class.equals(type)) {
			return Boolean.class;
		}
		if (byte.class.equals(type)) {
			return Byte.class;
		}
		if (char.class.equals(type)) {
			return Character.class;
		}
		if (double.class.equals(type)) {
			return Double.class;
		}
		if (float.class.equals(type)) {
			return Float.class;
		}
		if (int.class.equals(type)) {
			return Integer.class;
		}
		if (long.class.equals(type)) {
			return Long.class;
		}
		if (short.class.equals(type)) {
			return Short.class;
		}
		return type;
	}

	/** Replies the outboxing type for the given type.
	 * 
	 * @param type
	 * @return the outboxing of the given type.
	 * @since 7.1
	 */
	public static Class<?> getInboxingType(Class<?> type) {
		if (Void.class.equals(type)) {
			return void.class;
		}
		if (Boolean.class.equals(type)) {
			return boolean.class;
		}
		if (Byte.class.equals(type)) {
			return byte.class;
		}
		if (Character.class.equals(type)) {
			return char.class;
		}
		if (Double.class.equals(type)) {
			return double.class;
		}
		if (Float.class.equals(type)) {
			return float.class;
		}
		if (Integer.class.equals(type)) {
			return int.class;
		}
		if (Long.class.equals(type)) {
			return long.class;
		}
		if (Short.class.equals(type)) {
			return short.class;
		}
		return type;
	}

	/** Replies if the formal parameters are matching the given values.
	 * 
	 * @param formalParameters
	 * @param parameterValues
	 * @return <code>true</code> if the values could be passed to the method.
	 * @since 7.1
	 */
	public static boolean matchesParameters(Class<?>[] formalParameters, Object... parameterValues) {
		if (formalParameters==null) return parameterValues==null;
		if (parameterValues!=null && formalParameters.length==parameterValues.length) {
			for(int i=0; i<formalParameters.length; ++i) {
				if (!isInstance(formalParameters[i], parameterValues[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/** Replies if the parameters of the given method are matching the given values.
	 * 
	 * @param method
	 * @param parameters
	 * @return <code>true</code> if the values could be passed to the method.
	 * @since 7.1
	 */
	public static boolean matchesParameters(Method method, Object... parameters) {
		return matchesParameters(method.getParameterTypes(), parameters);
	}

}
