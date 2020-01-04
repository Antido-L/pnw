package cn.antido.common.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cn.antido.common.pojo.BaiduPosition.Content.Point;

/**
 * 百度地图ip定位返回结果封装对象<br>
 * @Description:
 * @author Antido
 * @date 2018年5月7日 上午11:24:03
 */

@JsonIgnoreProperties(ignoreUnknown = true) //在使用JackSon json 转 javaBean的时候忽略bean中没有的属性
public class BaiduPosition {
	
	private Integer status;
	private String message;
	
	private Content content;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Content {
		public String address;
		public AddressDetail address_detail;
		public Point point;
		
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class Point {
			public String x;
			public String y;
			/**
			 * @return the x
			 */
			public String getX() {
				return x;
			}
			/**
			 * @param x the x to set
			 */
			public void setX(String x) {
				this.x = x;
			}
			/**
			 * @return the y
			 */
			public String getY() {
				return y;
			}
			/**
			 * @param y the y to set
			 */
			public void setY(String y) {
				this.y = y;
			}
			
			/* (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			@Override
			public String toString() {
				return "Point [x=" + x + ", y=" + y + "]";
			}
			
			
		}
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class AddressDetail {
			public String city;
			public Integer city_code;
			/**
			 * @return the city
			 */
			public String getCity() {
				return city;
			}
			/**
			 * @param city the city to set
			 */
			public void setCity(String city) {
				this.city = city;
			}
			/**
			 * @return the city_code
			 */
			public Integer getCity_code() {
				return city_code;
			}
			/**
			 * @param city_code the city_code to set
			 */
			public void setCity_code(Integer city_code) {
				this.city_code = city_code;
			}
			/* (non-Javadoc)
			 * @see java.lang.Object#toString()
			 */
			
			@Override
			public String toString() {
				return "AddressDetail [city=" + city + ", city_code=" + city_code + "]";
			}
			
			
		}

		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

		/**
		 * @return the address_detail
		 */
		public AddressDetail getAddress_detail() {
			return address_detail;
		}

		/**
		 * @param address_detail the address_detail to set
		 */
		public void setAddress_detail(AddressDetail address_detail) {
			this.address_detail = address_detail;
		}

		/**
		 * @return the point
		 */
		public Point getPoint() {
			return point;
		}

		/**
		 * @param point the point to set
		 */
		public void setPoint(Point point) {
			this.point = point;
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "Content [address=" + address + ", address_detail=" + address_detail + ", point=" + point + "]";
		}
		
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaiduPosition [status=" + status + ", message=" + message + ", content=" + content + "]";
	}
	
	/**
	 * 获取百度地图的CityCode
	 * @return
	 */
	public Integer getCityCode() {
		if(this.content != null && this.content.getAddress_detail() != null)
			return this.content.getAddress_detail().getCity_code();
		else return null;
	}
	
	/**
	 * 设置CityCode
	 * @param cityCode
	 */
	public void setCityCode(Integer cityCode) {
		if(this.content != null && this.content.getAddress_detail() != null) {
			this.content.address_detail.setCity_code(cityCode);
		}
	}
	
	/**
	 * 获取百度地图中的坐标
	 * @return
	 */
	public Point getPoint() {
		if(this.getContent() != null) {
			return this.content.getPoint();
		} else return null;
	}
	
}
