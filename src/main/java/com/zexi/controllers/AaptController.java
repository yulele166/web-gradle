/**
 * 
 */
package com.zexi.controllers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.zexi.bean.Package;
import com.zexi.bean.ResponseMessage;
import com.zexi.bean.message.rep.TableListRep;
import com.zexi.service.AaptService;
import com.zexi.service.MessageService;

import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

/**
 * @author yulele
 *
 * @time 2017年6月15日 上午11:07:09
 */
@Path("/pack/")
public class AaptController {
    @Autowired
    private AaptService aaptService;
    @Autowired
    private MessageService messageService;
    
    @Get("/getPacks")
    public TableListRep getHistory(
            @Param("stxt") String stxt,
            @Param("offset") int offset,
            @Param("limit") int limit) {
        
        return aaptService.getHistory(stxt,offset,limit);
    }
    
    @Get("/generateApk")
    public ResponseMessage generateApk(
            Invocation inv,//记录操作日志用
            @Param("txt_APPLICATIONID") String txt_APPLICATIONID,
            @Param("txt_THEMENAME") String txt_THEMENAME,
            @Param("txt_THEMEDESC") String txt_THEMEDESC,
            @Param("txt_THEMECHANNEL") String txt_THEMECHANNEL,
            @Param("cid") String cid,
            @Param("t") String t
            ){
        
        
        ResponseMessage message = messageService.getRepMessage(inv);
        if ("add".equals(t)) {
            Package p = new Package();
            p.setApplicationId(txt_APPLICATIONID);
            p.setThemeName(txt_THEMENAME);
            p.setThemeDesc(txt_THEMEDESC);
            p.setThemeChannel(txt_THEMECHANNEL);
            Map<String, Object> map = new ConcurrentHashMap<String, Object>();
            map =  aaptService.generateApk(p);
            
            message.setMessage(map.get("isSuccess").toString());
            message.setStatus(map.get("isSuccess").toString().equals("true")?1:0);
            message.setData(map);
        }
        return message;
        

    }
    
    @Post("/uploadZip")
    public ResponseMessage uploadZip(HttpServletRequest request, @Param("file") MultipartFile file){
        
        return aaptService.uploadZip(request, file);
        
    }
    
	@Get("/del")
	public ResponseMessage delPack(@Param("cid") String cid) {

		ResponseMessage rm = new ResponseMessage();

		if (cid != null) {
			aaptService.delPack(Integer.parseInt(cid));
		}

		return rm;

	}
}
