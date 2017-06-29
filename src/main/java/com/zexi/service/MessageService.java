package com.zexi.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.zexi.bean.RequestMessage;
import com.zexi.bean.ResponseMessage;

import net.paoding.rose.web.Invocation;


/**
 * @author ming.yang
 * @since 2015年11月24日 下午4:44:58
 */
@Service
public class MessageService {

	/**
	 * 获取invocation中存放的result
	 * 
	 * @param inv
	 * @return
	 */
	public ResponseMessage getRepMessage(Invocation inv) {
		ResponseMessage message = (ResponseMessage) inv.getAttribute("result");
		return message;
	}

	/**
	 * 获取invocation中存放的request message
	 * 
	 * @param inv
	 * @return
	 */
	public RequestMessage getReqMessage(Invocation inv) {
		RequestMessage requestMessage = (RequestMessage) inv.getAttribute("message");
		return requestMessage;
	}

	/**
	 * 
	 * 
	 * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request) {
		// 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip. length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		} else if (ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = (String) ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		return ip;
	}
}
