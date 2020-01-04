package cn.antido.search.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.antido.common.pojo.BaiduPosition;
import cn.antido.common.pojo.BaiduPosition.Content.Point;

/**
 * @Description 用于定位用户所在位置<br>
 * 如果是第一次访问,根据IP地址调用百度地图API获取用户城市位置,将城市代码放入cookie中<br>
 * 如果cookie中已经有了城市代码则放行<br>
 * 如果用户是已经登录的状态则直接放行<br>
 * @author Antido
 * @date 2018年4月24日 下午4:21:55
 */
public class PositionInterceptor implements HandlerInterceptor{
	
	@Value("${defalut.CITY_ID}")
	private String CITY_ID;
	
	@Value("${defalut.CITY_NAME}")
	private String CITY_NAME;
	
	//位置信息的cookie名
	@Value("${cookie.PNW_POSITION}")
	private String PNW_POSITION;
	
	//百度地图的开发者密钥
	@Value("${baidu.BAIDU_AK}")
	private String BAIDU_AK;
	
	/**
	 * 前处理  执行Handler之前执行此方法<br>
	 * 检查是否有位置信息的cookie,如果有放行,如果没有调用百度地图api获取位置信息,存放在cookie中
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		return true;
		//TODO:
		/*
		Cookie[] cookies = request.getCookies();
		String position = null;
		//获取cookie
		if(cookies == null) {
			setPositionCookie(request,response);
			return true;
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(PNW_POSITION)) {
				position = cookie.getValue();
			}
		}
		
		if(position == null) { //cookie中没有位置信息  加入cookie放行
			setPositionCookie(request,response);
		}
		
		return true;
		*/
	}
	
	/**
	 * 放入包含position信息的cookie
	 * @param request
	 */
	private void setPositionCookie(HttpServletRequest request, HttpServletResponse response) {
		BaiduPosition position = getPosition(request);

		//正常获取位置信息
		if(position != null && position.getStatus() == 0) {
			Integer cityCode = position.getCityCode();
			String cityName = position.getContent().getAddress_detail().getCity();
			//cookie中包含城市ID和坐标 (ID-EAST-NORTH)
			Cookie cookie = new Cookie(PNW_POSITION, cityCode + "-" + cityName);
			response.addCookie(cookie);
		} else { 
			//位置信息获取异常 , 设置为默认地点
			try {
				String cityName = URLEncoder.encode(CITY_NAME,"UTF-8");
				Cookie cookie = new Cookie(PNW_POSITION, CITY_ID + "-" + cityName);
				response.addCookie(cookie);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取当前用户的城市ID
	 */
	private BaiduPosition getPosition(HttpServletRequest request) {
		//获取用户IP地址
		//优先获取x-forwarded-for
		//getRemoteAddr是直接和web服务器握手的ip地址
		//当使用反向代理服务器的时候getRemoteAddr会是反向代理服务器的地址(Nginx)
		//X-Forwarded-For 是一个 HTTP扩展头部,X-Forwarded-For: client, proxy1, proxy2
		//client 表示用户的真实IP 每经过一次代理服务器 代理服务器会在这个头增加用户的IP
		//在部署nginx的时候需要配置 proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        
        try {
        	//调用百度地图API获取大致位置信息
			BaiduPosition baiduPosition = getPositionFromBaidu(ip);
			//通过百度地图中的城市编码查询数据库 , 获取PNW项目中的城市编码
			if(baiduPosition != null && baiduPosition.getStatus() == 0) {
				Integer cityCodeFromDB = getCityCodeFromDB(baiduPosition);
				baiduPosition.setCityCode(cityCodeFromDB);
				return baiduPosition;
			} else { //百度地图调用内部异常 直接返回异常的position,由前台做默认处理
				
				return baiduPosition;
			}
		} catch (IOException e) { //调用百度地图API出现异常
			 System.err.println("ip:" + ip + "位置定位失败");
			 BaiduPosition errorPosition = new BaiduPosition();
			 errorPosition.setStatus(1); //非0-异常结果
			 return errorPosition;
		}
       
	}

	/**
	 * 从数据库中获取相应的cityCode<br>
	 * @param baiduPosition
	 * @return
	 */
	private Integer getCityCodeFromDB(BaiduPosition baiduPosition) {
		//TODO:
		return 210100;
	}

	/**
	 * 向百度服务器 发送get请求 <br>
	 * 根据客户端ip地址获取获取城市位置信息状态码<br>
	 * 百度地图API返回的是JSON格式数据,解析获取所需的信息
	 * @param ip
	 */
	private BaiduPosition getPositionFromBaidu(String ip) throws ClientProtocolException, IOException {
		CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/location/ip?ip="+ ip +"&ak="+ BAIDU_AK +"&coor=bd09ll");
        
        //发送GET请求
        CloseableHttpResponse resp = client.execute(httpGet);
        
        StatusLine responseCode = resp.getStatusLine();
        String result = EntityUtils.toString(resp.getEntity());
        //关闭客户端
        //TODO: 有时间写个池子
        client.close();
        
        //当响应正常时解析返回的json字符串,封装结果集,并返回
        if(responseCode.getStatusCode() == 200) {
        	ObjectMapper mapper = new ObjectMapper();
        	BaiduPosition baiduPosition = mapper.readValue(result, BaiduPosition.class);
        	return baiduPosition;
        }
        
        return null;
        
        
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
