package com.excilys.formation.cdb.core;

public class CompanyDTO extends ModelBase {

		private Long id;
		private String name;
		
		public CompanyDTO() {
			super();
		}

		public CompanyDTO(Long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public Long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return new StringBuilder()
					.append("CompanyDTO:(")
					.append("id=")
					.append(id)
					.append(", ")
					.append("name=")
					.append(name)
					.append(")")
					.toString();
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CompanyDTO other = (CompanyDTO) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 5;
			result = result << prime * result + ((id == null) ? 0 : id.hashCode());
			result = result << prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}
}
