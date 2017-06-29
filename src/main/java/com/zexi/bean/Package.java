/**
 * 
 */
package com.zexi.bean;

/**
 * @author yulele
 *
 * @time 2017年6月19日 上午10:38:39
 */
public class Package extends TableElement{
    private int id;
    private String applicationId;//应用包名
    private String themeName;//主题名
    private String themeDesc;//主题描述
    private String themeChannel;//渠道
    private String createTime;//打包时间
    private String url;//apk地址
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getApplicationId() {
        return applicationId;
    }
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
    public String getThemeName() {
        return themeName;
    }
    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }
 
    public String getThemeChannel() {
        return themeChannel;
    }
    public void setThemeChannel(String themeChannel) {
        this.themeChannel = themeChannel;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getThemeDesc() {
        return themeDesc;
    }
    public void setThemeDesc(String themeDesc) {
        this.themeDesc = themeDesc;
    }
    
}
