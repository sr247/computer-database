package com.excilys.formation.cdb.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.excilys.formation.cdb.core.ModelBase;

@Entity
@Table(name = "company")
public class CompanyEntity extends ModelBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "name")
	private String name;

	public CompanyEntity() {}

	public CompanyEntity(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	

	@Override 
	public String toString() {
		StringBuilder s = new StringBuilder();
		return s.append("ComputerEntity:(")
				.append("id=")
				.append(id)
				.append(", ")
				.append("name=")
				.append(name)
				.append(")")
				.toString();
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 7;
		result = result << prime * result + (int) id;
		result = result << prime * result + ((name == null) ? 0 : name.hashCode());
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
		CompanyEntity other = (CompanyEntity) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
