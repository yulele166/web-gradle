/**
 * 
 */
package com.zexi.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.gradle.tooling.BuildLauncher;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zexi.bean.Package;
import com.zexi.bean.ResponseMessage;
import com.zexi.bean.ServerConfig;
import com.zexi.bean.message.rep.TableListRep;
import com.zexi.dao.AaptDAO;
import com.zexi.utils.DateUtil;
import com.zexi.utils.FileUtil;
import com.zexi.utils.OSUtil;

/**
 * @author yulele
 *
 * @time 2017年6月15日 上午11:08:29
 */
@Service
public class AaptService {
    @Autowired
    private AaptDAO aaptDAO;
    @Autowired
    private ServerConfig serverConfig;
    
    private Logger log = LoggerFactory.getLogger(AaptService.class);
    
    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
    
    /**
     * 打包历史展示
     * @param stxt  主题名
     * @param offset
     * @param limit
     * @return
     */
    public TableListRep getHistory(String stxt, int offset, int limit) {
        TableListRep tableListRep = new TableListRep();
        if (stxt==null) {
            stxt = "";
        }
        List<Package> pl = aaptDAO.getHistory(stxt.trim(),offset, limit);
        int total = aaptDAO.getDataCount(stxt.trim());
        
        tableListRep.setRows(pl);
        tableListRep.setTotal(total);
        return tableListRep;
    }
    
    /**
     * 生成apk包
     * @param pl
     * @return
     * 1.调用gradle进行打包
     * 2.打包记录入库
     */
    public synchronized Map<String,Object> generateApk(Package pl) {
        String projectPath = serverConfig.getProjectPath();
        String url = serverConfig.getApkFilePrefix()
                    +DateUtil.forDefaultDatetime(new Date())
                    +"_"+pl.getThemeName()+".apk";
        pl.setUrl(url);
        int newId = 0;
        boolean flag = buildLauncher(projectPath,pl);//判断打包成功或失败
        int returnCode = 0;
        if(flag){//打包成功
        	if("linux".equals(OSUtil.getOSName())){
        		//调用shell
                returnCode = runShell("/data0/operate/download/apks/apk_tool.sh", url.substring(0, url.indexOf(".apk")));
        	}
            if(returnCode==0){
            	if(isExists(pl)){//以前打过包，更新记录时间
                    aaptDAO.updateInfo(pl);
                }else{
                    newId = aaptDAO.generateApk(pl);
                }
            }
        }
        map.put("isSuccess", flag);
        map.put("cid", newId+"");
        return map;
    }
    
    /**
     * 判断主题包之前是否打过
     * true 打过  false 未打过
     * @return
     */
    private boolean isExists(Package pkg) {
        Integer count = aaptDAO.getHistoryByName(pkg);
        return count != 0;
    }
    
    /**
     * 上传zip数据到服务器,并解压
     * @param request
     * @param file
     * @return
     */
    public ResponseMessage uploadZip(HttpServletRequest request, MultipartFile file) {
        ResponseMessage rm = new ResponseMessage();
        if(!file.isEmpty()){
            String name = file.getOriginalFilename();
            String realPath = serverConfig.getZipPath()+name;
            String dirName = name.substring(0, name.lastIndexOf("."));
            rm.setData(dirName);
            String targetPath = serverConfig.getZipDecompressPath()+"/"+dirName;
            fileHandle(targetPath);
           
            File zipFile = new File(realPath);
            Process pro = null;
            try {
                file.transferTo(zipFile);
                //解压zip包到指定目录
                if(zipFile.exists()){
                    FileUtil.zipDecompress(realPath,targetPath);
                }
            }catch (Exception e) {
                log.info("上传或解压zip出错");
                rm.setMessage("error");
                rm.setStatus(0);
                e.printStackTrace();
            }finally {
                if(pro!=null){
                    pro.exitValue();
                    pro.destroy();
                }
            }
        }
        return rm;
    }
    
    /**
     * 调用gradle
     * @param projectPath  项目根目录
     * @param pkg          打包相关配置
     */
    public boolean  buildLauncher(String projectPath,Package pkg) {
        ProjectConnection connection = GradleConnector.newConnector().
                forProjectDirectory(new File(projectPath)).connect();
        String buildResult = "";
        try {
            BuildLauncher build = connection.newBuild();
            build.forTasks("clean","assemble"+pkg.getThemeName()+"Release");
            List<String> buildArgs = new ArrayList<String>();
            buildArgs.add("--parallel");//并行
            buildArgs.add("-P" + "APPLICATIONID="+pkg.getApplicationId());
            buildArgs.add("-P" + "THEME_NAME="+pkg.getThemeName());
            buildArgs.add("-P" + "THEME_DESC="+pkg.getThemeDesc());
//            buildArgs.add("-P" + "THEME_CHANNEL="+pkg.getThemeChannel());
            buildArgs.add("-P" + "ICON_STYLE_TYPE="+pkg.getIconId());
            build.withArguments(buildArgs.toArray(new String[] {}));
          
            
            ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
            PrintStream cacheStream = new PrintStream(baoStream);
            
            System.setOut(cacheStream);//不打印到控制台
            
            build.setStandardOutput(System.out);
            build.setStandardError(System.err);
            build.run();
            
            buildResult = baoStream.toString();
            
            log.info(buildResult);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return buildResult.contains("BUILD SUCCESSFUL");
    }
    
    /**
	 * 调用shell脚本
	 */
	private int runShell(String shell_file,String dir_name) {
		int returnCode = 0;//正常结束
		try {
//			Process process = Runtime.getRuntime().exec("chmod 755 ");
//			process.waitFor();
            // xx.sh是要执行的shell文件，param1参数值，xx.sh和param1之间要有空格
            // 多个参数可以在param1后面继续增加，但不要忘记空格！！
			Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh","-c",shell_file+" "+dir_name});
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			StringBuffer sb = new StringBuffer("");
			while((line=br.readLine()) != null){
				sb.append(line);
			}
			br.close();
//			System.out.println(sb.toString());
			returnCode = process.waitFor();
		} catch (IOException e) {
			log.info("exe shell IOException");
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.info("exe shell InterruptedException");
		}
		return returnCode;

	}
    
    public static void main(String[] args) {
        String path = "D:/DevelopTools/workspace/NewAutoTheme";
        Package pl = new Package();
        pl.setApplicationId("com.mycheering.launcher.auto.xiaomi");
        pl.setThemeChannel("100041");
        pl.setThemeName("theme_xiaomi");
        pl.setThemeDesc("小米主题");
        AaptService aapt = new AaptService();
        System.out.println(aapt.buildLauncher(path,pl));
    }
    
    
    /**
     * 文件处理
     * @param resPath 资源路径
     * @param dirPath 目标目录
     */
    private synchronized void fileHandle(String dirPath) {
        File file = new File(dirPath);
        if(file.exists()){
            if(!file.isDirectory()){
                log.info("target is not dir!");
                return;
            }
            FileUtil.deleteAll(file);
        }else{
            //创建目录
            file.mkdir();
        }
    }

    /**
     * @param id
     */
    public void delPack(int id) {
        //先删打包记录
        //再删apk包
        aaptDAO.delPack(id);
        
    }

	/**
	 * @return
	 */
	public List<Map<Integer,String>> getIconUrl() {
		
		return aaptDAO.getIconUrl();
	}
}
