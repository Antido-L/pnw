package cn.antido.sso.service.impl.SMS;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @Description 短信发送工具类<br>
 * 调用阿里云短信平台用于向用户发送注册验证码和登录验证码<br>
 * @author Antido
 * @date 2018年8月10日 下午3:50:22
 */
public class SMSTools {
	
	//产品名称:云通信短信API产品,开发者无需替换
    private final static String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private final static String domain = "dysmsapi.aliyuncs.com";
    //开发者AK
    private final String accessKeyId;
    private final String accessKeySecret;
    private final String signName;
    private final String regisTemplateCode;
    private final String loginTemplateCode;
    //private final String accessKeyId = "LTAIy0NPl3xdhW1c";
    //private final String accessKeySecret = "HfgdjZsuM4XRPwaB03nrOKiyaoK2Hh";
    
    /**
     * 用于存放已经发送的短信回执单号
     * @throws UnsupportedEncodingException 
     */
    //private LinkedBlockingQueue<String> detailCheckQueue;
    
    public SMSTools(String accessKeyId, String accessKeySecret, 
    		String signName, String regisTemplateCode, String loginTemplateCode) throws UnsupportedEncodingException {
    	String encode = new String(signName.getBytes("ISO-8859-1"),"UTF-8");
    	this.accessKeyId = accessKeyId;
    	this.accessKeySecret = accessKeySecret;
    	this.signName = encode;
    	this.regisTemplateCode = regisTemplateCode;
    	this.loginTemplateCode = loginTemplateCode;
    	System.out.println("loading SMSTools...");
    }
    
    /**
     * 发送注册验证码
     * @param phone
     * @param code
     * @return 发送结果集
     * @throws ClientException
     */
    public SendSmsResponse sendRegisMsg(String phone, String code) throws ClientException{
        //超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(regisTemplateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    /**
     * 发送登录验证码
     * @param phone
     * @param code
     * @return 发送结果集
     * @throws ClientException 
     */
	public SendSmsResponse sendLoginMsg(String phone, String code) throws ClientException {
		//超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(loginTemplateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\""+code+"\"}");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
	}
    
}
