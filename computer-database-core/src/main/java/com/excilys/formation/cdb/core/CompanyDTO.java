package com.excilys.formation.cdb.core;

public class CompanyDTO extends ModelBase {

		private int id;
		private String name;	
		
		public CompanyDTO(int id, String name) {
			this.id = id;
			this.name = name;
		}
		

		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}


		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}


		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}


		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		@Override
		public boolean equals(Object o) {
			CompanyDTO cpy = (CompanyDTO) o;
			return this.id == cpy.id && this.name.equals(cpy.name);
		}
		
		@Override
		public int hashCode() {
			int hash = this.getClass().getMethods().length;
			hash = hash * 7 + (int) id;
			hash = hash * 11 + name.hashCode();
			
			return hash;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Company:("
					+ "id=" + id + ", "
					+ "name=" + name + ")";
		}
	

}
