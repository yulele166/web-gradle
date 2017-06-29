/**
 * 
 */
package com.zexi.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author yulele
 *
 * @time 2017年6月21日 上午11:07:44
 */
public class FileUtil {

    public static void zipDecompress(String filePath,String targetPath) {
        long startTime=System.currentTimeMillis();  
        File zipFile = new File(filePath);
//        String dirName = zipFile.getName().substring(0, zipFile.getName().lastIndexOf("."));
//        File outDir = new File(zipFile.getParent()+"/"+dirName);
        File outDir = new File(targetPath);
        extract(zipFile,outDir);
        long endTime=System.currentTimeMillis();  
        System.out.println("耗费时间： "+(endTime-startTime)+" ms");  
    }
    
    private static final int  BUFFER_SIZE = 4096;

    private static void extractFile(ZipInputStream in, File outdir, String name) throws IOException
    {
      byte[] buffer = new byte[BUFFER_SIZE];
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(outdir,name)));
      int count = -1;
      while ((count = in.read(buffer)) != -1)
        out.write(buffer, 0, count);
      out.close();
    }

    private static void mkdirs(File outdir,String path)
    {
      File d = new File(outdir, path);
      if( !d.exists() )
        d.mkdirs();
    }

    private static String dirpart(String name)
    {
      int s = name.lastIndexOf( File.separatorChar );
      return s == -1 ? null : name.substring( 0, s );
    }

    public static void extract(File zipfile, File outdir)
    {
      try
      {
        ZipInputStream zin = new ZipInputStream(new FileInputStream(zipfile));
        ZipEntry entry;
        String name, dir;
        while ((entry = zin.getNextEntry()) != null)
        {
          name = entry.getName();
          if( entry.isDirectory() )
          {
            mkdirs(outdir,name);
            continue;
          }
          dir = dirpart(name);
          if( dir != null )
            mkdirs(outdir,dir);

          extractFile(zin, outdir, name);
        }
        zin.close();
      } 
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    
    public static void deleteAll(File file){
        if(file.isFile()||file.list().length ==0){  
            file.delete();       
        }else{ 
            File[] files = file.listFiles();  
            for (int i = 0; i < files.length; i++) {  
               deleteAll(files[i]);  
               files[i].delete();      
            }
        }
        //if(file.exists())         //如果文件本身就是目录 ，就要删除目录  
        //file.delete();  
    } 
}
