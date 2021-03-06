/* 
 * $Id$
 * 
 * Copyright (c) 2005-10, Multiagent Team,
 * Laboratoire Systemes et Transports,
 * Universite de Technologie de Belfort-Montbeliard.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Laboratoire Systemes et Transports
 * of the Universite de Technologie de Belfort-Montbeliard ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 *
 * http://www.multiagent.fr/
 */
package org.arakhne.afc.math.tree.iterator;

/**
 * This interface is used to select the data to reply
 * by a Data*TreeIterator.
 * 
 * @see PostfixDataDepthFirstTreeIterator
 * @see DataBroadFirstTreeIterator
 * 
 * @param <D> is the type of the data inside the tree
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface DataSelector<D> {

	/** Replies if the specified data could be replied by the iterator.
	 *
	 * @param data is the data to test.
	 * @return <code>true</code> if the data could be replied by the iterator,
	 * otherwhise <code>false</code>
	 */
	public boolean dataCouldBeRepliedByIterator(D data);

}
