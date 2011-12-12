package org.hibernate.envers.test.entities.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
@org.hibernate.envers.Audited
public class MultipleCollectionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", length = 10)
	private Long id;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Integer version;

	@Column(name = "TEXT", length = 50, nullable = false)
	private String text;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MCE_ID", nullable = false)
	@org.hibernate.envers.AuditJoinTable(name = "MCE_RE1_AUD", inverseJoinColumns = @JoinColumn(name = "RE1_ID"))
	private List<MultipleCollectionRefEntity1> refEntities1 = new ArrayList<MultipleCollectionRefEntity1>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "MCE_ID", nullable = false)
	@org.hibernate.envers.AuditJoinTable(name = "MCE_RE2_AUD", inverseJoinColumns = @JoinColumn(name = "RE2_ID"))
	private List<MultipleCollectionRefEntity2> refEntities2 = new ArrayList<MultipleCollectionRefEntity2>();

	public Long getId() {
		return id;
	}

	public Integer getVersion() {
		return version;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<MultipleCollectionRefEntity1> getRefEntities1() {
		return Collections.unmodifiableList(refEntities1);
	}

	public void addRefEntity1(MultipleCollectionRefEntity1 refEntity1) {
		refEntities1.add(refEntity1);
	}

	public void removeRefEntity1(MultipleCollectionRefEntity1 refEntity1) {
		refEntities1.remove(refEntity1);
	}

	public List<MultipleCollectionRefEntity2> getRefEntities2() {
		return Collections.unmodifiableList(refEntities2);
	}

	public void addRefEntity2(MultipleCollectionRefEntity2 refEntity2) {
		refEntities2.add(refEntity2);
	}

	public void removeRefEntity2(MultipleCollectionRefEntity2 refEntity2) {
		refEntities2.remove(refEntity2);
	}

	@Override
	public String toString() {
		return "MultipleCollectionEntity [id=" + id + ", text=" + text
				+ ", refEntities1=" + refEntities1 + ", refEntities2="
				+ refEntities2 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result
				+ ((refEntities1 == null) ? 0 : refEntities1.hashCode());
		result = prime * result
				+ ((refEntities2 == null) ? 0 : refEntities2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MultipleCollectionEntity other = (MultipleCollectionEntity) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (refEntities1 == null) {
			if (other.refEntities1 != null)
				return false;
		} else if (!refEntities1.equals(other.refEntities1))
			return false;
		if (refEntities2 == null) {
			if (other.refEntities2 != null)
				return false;
		} else if (!refEntities2.equals(other.refEntities2))
			return false;
		return true;
	}

}
