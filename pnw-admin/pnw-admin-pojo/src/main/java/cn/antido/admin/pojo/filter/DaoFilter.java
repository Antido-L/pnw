package cn.antido.admin.pojo.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.antido.admin.pojo.Park;
import cn.antido.admin.pojo.User;

/**
 * @Description SpaceFilter<br>
 * 用于封装查询条件 将做为参数传递给Mapper<br>
 * @action sql语句
 * @value 条件的值
 * @orderBy 输入需要排序的字段
 * @author Antido
 * @date 2017年12月20日 上午11:24:49
 */
public class DaoFilter {
	
	private List<Criteria> andCondition;
	private List<Criteria> orCondition;

	private String orderBy;
	private boolean distinct; //是否去重:turn-去重,false-不去重复
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isDistinct() {
		// TODO : 按指定字段去重
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	
	public List<Criteria> getAndCondition() {
		return andCondition;
	}

	public void setAndCondition(List<Criteria> andCondition) {
		this.andCondition = andCondition;
	}

	public List<Criteria> getOrCondition() {
		return orCondition;
	}

	public void setOrCondition(List<Criteria> orCondition) {
		this.orCondition = orCondition;
	}

	/**
	 * 新增AND查询条件,该查询没有输入值,由程序员插入sql,没有防sql注入功能<br>
	 * 适用与做判断查询 例如: filed is not null
	 */
	public void addAndCriteria(String action) {
		if(this.andCondition == null) {
			this.andCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setNoValue(true);
		criteria.setAction(action);
		
		this.andCondition.add(criteria);
	}
	
	/**
	 * 新增AND查询条件,该查询只针对输入单个值<br>
	 * 适用与做比较查询 例如: filed = value 或者 filed > value
	 */
	public void addAndCriteria(String action, Object value) {
		if(this.andCondition == null) {
			this.andCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setSingleValue(true);
		criteria.setAction(action);
		criteria.setValue(value);
		
		this.andCondition.add(criteria);
	}
	
	/**
	 * 新增AND查询条件,该查询只针对两个输入值<br>
	 * 适用于做between查询 例如: filed between value and anotherValue
	 */
	public void addAndCriteria( Object value, String action, Object anotherValue) {
		if(this.andCondition == null) {
			this.andCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setBetweenValue(true);
		criteria.setAction(action);
		criteria.setValue(value);
		criteria.setAnotherValue(anotherValue);
		
		this.andCondition.add(criteria);
	}
	
	/**
	 * 新增AND查询条件,该查询只针对输入数组
	 * 适用于做判断查询 例如 :filed in values 或者 filed not in values
	 */
	public void addAndCriteria(String action, Object[] values) {
		if(this.andCondition == null) {
			this.andCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setArrValue(true);
		criteria.setAction(action);
		criteria.setValues(values);
		
		this.andCondition.add(criteria);
	}
	
	/**
	 * 新增AND查询条件,该查询只针对输入列表
	 * 适用于做判断查询 例如 :filed in list 或者 filed not in list
	 * @param <T>
	 */
	public <T> void addAndCriteria(String action, List<T> list) {
		if(this.andCondition == null) {
			this.andCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setListValue(true);
		criteria.setAction(action);
		criteria.setList(list);
		
		this.andCondition.add(criteria);
	}
	
	//****************************************************************//
	
	/**
	 * 新增OR查询条件,该查询没有输入值,由程序员插入sql,没有防sql注入功能<br>
	 * 适用与做判断查询 例如: filed is not null
	 */
	public void addOrCriteria(String action) {
		if(this.orCondition == null) {
			this.orCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setNoValue(true);
		criteria.setAction(action);
		
		this.orCondition.add(criteria);
	}
	
	/**
	 * 新增OR查询条件,该查询只针对输入单个值<br>
	 * 适用与做比较查询 例如: filed = value 或者 filed > value
	 */
	public void addOrCriteria(String action, Object value) {
		if(this.orCondition == null) {
			this.orCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setSingleValue(true);
		criteria.setAction(action);
		criteria.setValue(value);
		
		this.orCondition.add(criteria);
	}
	
	/**
	 * 新增OR查询条件,该查询只针对两个输入值<br>
	 * 适用于做between查询 例如: filed between value and anotherValue
	 */
	public void addORCriteria( Object value, String action, Object anotherValue) {
		if(this.orCondition == null) {
			this.orCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setBetweenValue(true);
		criteria.setAction(action);
		criteria.setValue(value);
		criteria.setAnotherValue(anotherValue);
		
		this.orCondition.add(criteria);
	}
	
	/**
	 * 新增OR查询条件,该查询只针对输入数组
	 * 适用于做判断查询 例如 :filed in values 或者 filed not in values
	 */
	public void addORCriteria(String action, Object[] values) {
		if(this.orCondition == null) {
			this.orCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setArrValue(true);
		criteria.setAction(action);
		criteria.setValues(values);
		
		this.orCondition.add(criteria);
	}
	
	/**
	 * 新增OR查询条件,该查询只针对输入列表
	 * 适用于做判断查询 例如 :filed in list 或者 filed not in list
	 * @param <T>
	 */
	public <T> void addORCriteria(String action, List<T> list) {
		if(this.orCondition == null) {
			this.orCondition = new ArrayList<Criteria>();
		}
		Criteria criteria = new Criteria();
		criteria.setListValue(true);
		criteria.setAction(action);
		criteria.setList(list);
		
		this.orCondition.add(criteria);
	}
	
	//*******************************************************************//
	
	/**
	 * @Description 内置查询条件,在mybatis映射文件中作为遍历项
	 * @author Antido
	 * @param <T>
	 * @date 2017年12月20日 下午3:23:46
	 */
	public class Criteria {
		
		private String action;
		private Object value;
		private Object anotherValue;
		private Object[] values;
		private List list;
		
		/**
		 * 通过boolean值控制sql语句
		 */
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean arrValue;
		private boolean listValue;
		
		
		public Criteria() {
			super();
		}
		
		public Criteria(String action, Object value) {
			super();
			this.action = action;
			this.value = value;
		}
		
		//setter and getter...
		public String getAction() {
			return action;
		}
		public void setAction(String action) {
			this.action = action;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(Object value) {
			this.value = value;
		}
		public Object getAnotherValue() {
			return anotherValue;
		}
		public void setAnotherValue(Object anotherValue) {
			this.anotherValue = anotherValue;
		}
		public Object[] getValues() {
			return values;
		}
		public void setValues(Object[] values) {
			this.values = values;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public void setNoValue(boolean noValue) {
			this.noValue = noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public void setSingleValue(boolean singleValue) {
			this.singleValue = singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public void setBetweenValue(boolean betweenValue) {
			this.betweenValue = betweenValue;
		}

		public boolean isArrValue() {
			return arrValue;
		}

		public void setArrValue(boolean arrValue) {
			this.arrValue = arrValue;
		}

		public List getList() {
			return list;
		}

		public void setList(List list) {
			this.list = list;
		}

		public boolean isListValue() {
			return listValue;
		}

		public void setListValue(boolean listValue) {
			this.listValue = listValue;
		}
		
	}
	
}
