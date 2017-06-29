package com.zexi.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class ServerConfig {
	
	@Value("#{propertyConfigurer['theme.zip.path']}")
    private String zipPath;
	@Value("#{propertyConfigurer['zip.decompress.path']}")
    private String zipDecompressPath;
	@Value("#{propertyConfigurer['project.path']}")
    private String projectPath;
	@Value("#{propertyConfigurer['apk.file.prefix']}")
    private String apkFilePrefix;

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public String getZipDecompressPath() {
        return zipDecompressPath;
    }

    public void setZipDecompressPath(String zipDecompressPath) {
        this.zipDecompressPath = zipDecompressPath;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public String getApkFilePrefix() {
        return apkFilePrefix;
    }

    public void setApkFilePrefix(String apkFilePrefix) {
        this.apkFilePrefix = apkFilePrefix;
    }
	
}
