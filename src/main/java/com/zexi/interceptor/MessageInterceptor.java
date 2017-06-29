package com.zexi.interceptor;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.zexi.bean.ResponseMessage;
import com.zexi.service.MessageService;

import net.paoding.rose.web.ControllerInterceptorAdapter;
import net.paoding.rose.web.Invocation;

/**
 * 将所有客户端请求转换成bean的拦截器
 * 
 * @author ming.yang
 * @since 2015年11月20日 下午6:42:34
 */
@Component
public class MessageInterceptor extends ControllerInterceptorAdapter {

	private final Logger LOG = LoggerFactory.getLogger(MessageInterceptor.class);

	@Autowired
	private MessageService messageService;

	@Override
	protected Object before(Invocation inv) throws Exception {
		HttpServletRequest request = inv.getRequest();
		String data = (String) request.getParameter("data");// 默认的参数名称
		
//		data = URLDecoder.decode(data, "UTF-8");
		ResponseMessage reponseMessage = new ResponseMessage();
		// 开始时间
		long beginTime = System.currentTimeMillis();
		// 访问uri
		String requestURI = request.getRequestURI();
		
		String ip = messageService.getIpAddress(request);
		LOG.info("request {},ip:{},params:{}", requestURI, ip, data);

		inv.setAttribute("begin", beginTime);
		inv.setAttribute("result", reponseMessage);
		
		//检验权限
		Cookie[] cookies  = request.getCookies();
//		if (cookies!=null) {
//			for(Cookie cookie:cookies){
//				if ("token".equals(cookie.getName())) {
//					String token = cookie.getValue();
//					if (!StringUtils.isEmpty(token)) {
//						boolean flag = userService.checkRequestUrl(token, requestURI);
//						if (!flag) {
//							return false;
//						}
//						continue;
//					}
//				}
//			}
//		}

		return super.before(inv);
	}

	@Override
	protected Object after(Invocation inv, Object instruction) throws Exception {
		ResponseMessage message = messageService.getRepMessage(inv);
//		message.setData(instruction);
		inv.getResponse().setContentType("text/html;charset=UTF-8");
		String result = "@" + JSON.toJSONString(instruction);
		
		HttpServletRequest request = inv.getRequest();
		String requestURI = request.getRequestURI();
		
		long begin = (Long) inv.getAttribute("begin");
		// 请求耗时
		long timeconsume = System.currentTimeMillis() - begin;
		LOG.info("{} result:{},cost:[{}ms]", requestURI, result, timeconsume);
		inv.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		return super.after(inv, result);
	}

}
