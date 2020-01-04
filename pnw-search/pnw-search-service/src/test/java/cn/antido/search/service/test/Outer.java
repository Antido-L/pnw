package cn.antido.search.service.test;

public class Outer {
	private String name;
	private Inner inner;
	
	static class Inner {
		private String name;

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Inner [name=" + name + "]";
		}
		
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the inner
	 */
	public Inner getInner() {
		return inner;
	}

	/**
	 * @param inner the inner to set
	 */
	public void setInner(Inner inner) {
		this.inner = inner;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Outer [name=" + name + ", inner=" + inner + "]";
	}
	
	
}
