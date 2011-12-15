/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2011, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.envers.test.integration.onetoone.bidirectional;

import javax.persistence.EntityManager;

import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.envers.test.AbstractEntityTest;
import org.hibernate.envers.test.entities.onetoone.BidirectionalEagerAnnotationRefEdOneToOne;
import org.hibernate.envers.test.entities.onetoone.BidirectionalEagerAnnotationRefIngOneToOne;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Test HHH-3854: NullPointerException in AbstractEntityTuplizer.createProxy() when using eager loading. 
 * 
 * @author Erik-Berndt Scheper
 */
public class BidirectionalEagerAnnotationTest extends AbstractEntityTest {
	private Integer refedId1;

	public void configure(Ejb3Configuration cfg) {
		cfg.addAnnotatedClass(BidirectionalEagerAnnotationRefEdOneToOne.class);
		cfg.addAnnotatedClass(BidirectionalEagerAnnotationRefIngOneToOne.class);
	}

	@BeforeClass(dependsOnMethods = "init")
	public void initData() {
		BidirectionalEagerAnnotationRefEdOneToOne ed1 = new BidirectionalEagerAnnotationRefEdOneToOne();
		BidirectionalEagerAnnotationRefIngOneToOne ing1 = new BidirectionalEagerAnnotationRefIngOneToOne();
		ed1.setData("referredEntity1");
		ed1.setRefIng(ing1);
		ing1.setData("referringEntity");
		ing1.setRefedOne(ed1);

		// Revision 1
		EntityManager em = getEntityManager();
		em.getTransaction().begin();

		em.persist(ed1);
		em.persist(ing1);
		em.getTransaction().commit();

		// Revision 2
		em.getTransaction().begin();

		ing1 = em.find(BidirectionalEagerAnnotationRefIngOneToOne.class, ing1
				.getId());
		em.getTransaction().commit();

		refedId1 = ed1.getId();
	}

	@Test
	public void testRevisionsCounts() {
		BidirectionalEagerAnnotationRefIngOneToOne referencing = getAuditReader()
				.find(BidirectionalEagerAnnotationRefIngOneToOne.class,
						refedId1, 1);
		assert referencing.getRefedOne().getData() != null;
	}

}