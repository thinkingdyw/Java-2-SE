package com.think.common.util.placeholder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 占位符属性配置
 **/
public final class PropertiesPlaceholderConfigureFactoryBean {

	private String configFile;//单个配置文件，位于classpath下
	private Set<String> configFiles;//多个配置文件，如果有重复，后面覆盖前面的，位于classpath下
	private Map<String, String> config = new HashMap<String, String>();//解析后的配置信息
	private PlaceholderResolver placeholderResolver;//properties解析器
	private List<Properties> configs = new ArrayList<Properties>();
	
	public void parse() throws Exception{
		loadConfigure(configFile);
		loadConfigure(configFiles);
		this.resolve();
	}
	/**
	 * 解析配置文件
	 **/
	private void resolve() {
		if(null == placeholderResolver){
			placeholderResolver = new PropertiesPlaceholderResolver();
		}
		if(this.configs.size() > 0){
			Map<String, String> map = this.placeholderResolver.resolve(configs);
			this.config.putAll(map);
		}
	}
	/**
	 * 加载配置文件
	 **/
	private void loadConfigure(Set<String> configFiles) throws Exception {
		if(null != configFiles && configFiles.size() >0){
			for (String config : configFiles) {
				this.loadConfigure(config);
			}
		}
	}
	/**
	 * 加载配置文件
	 **/
	public void loadConfigure(String configFile) throws Exception{
		Properties properties = getProperties(configFile);
		configs.add(properties);
	}
	/**
	 * 根据配置信息获取properties文件实例
	 **/
	private Properties getProperties(String configFile) throws IOException{
		ClassLoader classLoader = getClassLoader();
		InputStream in= classLoader.getResourceAsStream(configFile);
		if(null == in){
			throw new FileNotFoundException("找不到:"+configFile+".properties!");
		}
		Properties properties = new Properties();
		properties.load(in);
		in.close();
		return properties;
	}
	/**
	 * 获得类加载器
	 **/
	private ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if(null == classLoader){
			classLoader = PropertiesPlaceholderConfigureFactoryBean.class.getClassLoader();
		}
		return classLoader;
	}
	
	
	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public Set<String> getConfigFiles() {
		return configFiles;
	}

	public void setConfigFiles(Set<String> configFiles) {
		this.configFiles = configFiles;
	}

	public Map<String, String> getConfig() {
		return config;
	}

	public void setConfig(Map<String, String> config) {
		this.config = config;
	}

	public PlaceholderResolver getPlaceholderResolver() {
		return placeholderResolver;
	}

	public void setPlaceholderResolver(PlaceholderResolver placeholderResolver) {
		this.placeholderResolver = placeholderResolver;
	}
}
