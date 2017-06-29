/**
 * 
 */
package com.zexi.dao;

import java.util.List;

import com.zexi.bean.Package;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

/**
 * @author yulele
 *
 * @time 2017年6月15日 上午11:09:03
 */
@DAO
public interface AaptDAO {

    final String TABLE = "aapt_package_log";
    final String TABLE_FIELD = "applicationId,themeName,themeDesc,themeChannel,url";
    
    @ReturnGeneratedKeys
    @SQL("insert into $TABLE ($TABLE_FIELD) values (:1.applicationId,:1.themeName,:1.themeDesc,:1.themeChannel,:1.url)")
    public int generateApk(Package pl);

    @SQL("select id,$TABLE_FIELD,createTime from $TABLE #if(:1!=null){ where themeDesc like '%##(:1)%'} order by createTime desc limit :3 offset :2")
    public List<Package> getHistory(String themeDesc, int offset, int limit);

    @SQL("select count(1) from $TABLE #if(:1!=null){ where themeName like '%##(:1)%'}")
    public int getDataCount(String themeName);

    @SQL("delete from $TABLE where id = :1")
    public void delPack(int id);

    @SQL("select count(1) from $TABLE where themeName = :1")
    public Integer getHistoryByName(String themeName);

    @SQL("update $TABLE set createTime = now() where themeName = :1")
    public void updateTime(String themeName);

}
