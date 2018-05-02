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
			return "CompanyDTO:("
					+ "id=" + id + ", "
					+ "name=" + name + ")";
		}
		
		@Override
		public boolean equals(Object o) {
			CompanyDTO company = (CompanyDTO) o;
			return this.id.equals(company.id) && this.name.equals(company.name);
		}
		
		@Override
		public int hashCode() {
			int hash = this.getClass().getMethods().length;
			hash = hash * 7 + id.intValue();
			hash = hash * 11 + name.hashCode();			
			return hash;
		}
}
