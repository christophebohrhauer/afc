/* 
 * $Id$
 * 
 * Copyright (C) 2010 Stephane GALLAND.
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
package org.arakhne.vmutil.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.arakhne.vmutil.URLHandlerUtil;

import junit.framework.TestCase;

/**
 * @author $Author: galland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid org.arakhne.afc
 * @mavenartifactid arakhneVmutils
 */
public class URLConnectionTest extends TestCase {

	private URLConnection connection;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		URLHandlerUtil.installArakhneHandlers();
		URL resourceUrl = new URL("resource:org/arakhne/vmutil/test.txt"); //$NON-NLS-1$
		assertNotNull(resourceUrl);
		this.connection = new URLConnection(resourceUrl);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void tearDown() throws Exception {
		this.connection = null;
		URLHandlerUtil.uninstallArakhneHandlers();
		super.tearDown();
	}
	
	/**
	 * @throws IOException
	 */
	public void testGetInputStream() throws IOException {
		String line;
		InputStream is = this.connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			line = br.readLine();
		}
		finally {
			br.close();
			is.close();
		}
		assertEquals("TEST1: FOR UNIT TEST ONLY", line); //$NON-NLS-1$
    }

}
