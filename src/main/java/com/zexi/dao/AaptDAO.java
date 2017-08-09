/**
 * 
 */
package com.zexi.dao;

import java.util.List;
import java.util.Map;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;
import com.zexi.bean.Package;

/**
 * @author yulele
 *
 * @time 2017年6月15日 上午11:09:03
 */
@DAO
public interface AaptDAO {

    final String TABLE = "aapt_package_log";
    final String TABLE_FIELD = "applicationId,themeName,themeDesc,themeChannel,url,iconUrl";
    
    @ReturnGeneratedKeys
    @SQL("insert into $TABLE ($TABLE_FIELD) values (:1.applicationId,:1.themeName,:1.themeDesc,:1.themeChannel,:1.url,:1.iconUrl)")
    public int generateApk(Package pl);

    @SQL("select id,$TABLE_FIELD,createTime from $TABLE #if(:1!=null){ where themeDesc like '%##(:1)%'} order by createTime desc limit :3 offset :2")
    public List<Package> getHistory(String themeDesc, int offset, int limit);

    @SQL("select count(1) from $TABLE #if(:1!=null){ where themeName like '%##(:1)%'}")
    public int getDataCount(String themeName);

    @SQL("delete from $TABLE where id = :1")
    public void delPack(int id);

    @SQL("select count(1) from $TABLE where themeName = :1.themeName and iconUrl = :1.iconUrl")
    public Integer getHistoryByName(Package pkg);

    @SQL("update $TABLE set createTime = now(),themeDesc = :1.themeDesc,themeChannel = :1.themeChannel,applicationId = :1.applicationId,url = :1.url,iconUrl = :1.iconUrl where themeName = :1.themeName")
    public void updateInfo(Package pl);

	@SQL("select iconId,iconUrl from aapt_theme_icon_style")
	public List<Map<Integer,String>> getIconUrl();

}
