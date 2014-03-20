package com.think.common.util.placeholder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 默认properties文件解析器
 * @author diaoyouwei
 *
 */
public class PropertiesPlaceholderResolver implements PlaceholderResolver {

	private static final String DEFAULT_PLACEHOLDER_PREFIX = "${";// 默认占位符前缀
	private static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";// 默认占位符后缀

	private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;// 占位符前缀
	private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;// 占位符后缀
	private Properties config;
	private Set<String> visitedPlaceholders = new HashSet<String>();// 存放已访问的占位符，用于判断是否循环调用

	private boolean overrideSysProps = true;//是否覆盖系统属性
	
	
	public Map<String, String> resolve(List<Properties> properties) {
		Map<String, String> configure = new HashMap<String, String>();
		for (Properties propertie : properties) {
			configure.putAll(readConfigure(propertie));
		}
		this.parse(configure);
		return configure;
	}
	/**
	 * 读取配置
	 **/
	private Map<String, String> readConfigure(Properties properties) {
		if (null == properties) {
			throw new IllegalArgumentException("configure file is Null!");
		}
		Map<String, String> prop = new HashMap<String, String>();
		if (properties.isEmpty()) {
			return prop;
		}
		Set<Object> keySet = properties.keySet();
		Iterator<Object> keys = keySet.iterator();
		while (keys.hasNext()) {
			String key = String.valueOf(keys.next());
			String value = properties.getProperty(key);
			if (null != value) {
				prop.put(key, value);
			}
		}
		return prop;
	}
	/**
	 * 解析配置
	 **/
	private void parse(Map<String, String> config) {
		for (Map.Entry<String, String> entry : config.entrySet()) {
			String val = parseValue(entry.getKey(), entry.getValue(), config);
			entry.setValue(val);
		}
	}

	private String parseValue(String key, String value,
			Map<String, String> config) {
		int beginIndex = value.indexOf(placeholderPrefix);
		int endIndex = value.indexOf(placeholderSuffix);
		if (beginIndex != -1 && endIndex != -1) {
			final String placeHolder = value.substring(beginIndex, endIndex
					+ placeholderSuffix.length());
			final String placeHolderName = value.substring(beginIndex
					+ placeholderPrefix.length(), endIndex);
			if (isCircleReferece(new StringBuilder().append(key).append(
					placeHolderName))) {
				throw new RuntimeException("Circular placeholder reference '"
						+ placeHolder + "' in property definitions");
			}
			String placeHolderReplace = "";
			if(overrideSysProps()){
				placeHolderReplace = config.get(placeHolderName) == null ? ""
						: config.get(placeHolderName);
			}else{
				placeHolderReplace = getSystemProperty(placeHolderName);
			}
			value = value.replace(placeHolder, placeHolderReplace);
			value = parseValue(key, value, config);
		}
		return value;
	}
	private String getSystemProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 判断占位符是否循环引用
	 **/
	private boolean isCircleReferece(StringBuilder placeholder) {
		int count = 0;
		while (count < 2) {
			count++;
			if (!visitedPlaceholders.add(placeholder.reverse().toString())) {
				// 循环引用
				break;
			}
		}
		if (count != 2) {
			return true;
		}
		return false;
	}

	public String getPlaceholderPrefix() {
		return placeholderPrefix;
	}

	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.placeholderPrefix = placeholderPrefix;
	}

	public String getPlaceholderSuffix() {
		return placeholderSuffix;
	}

	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.placeholderSuffix = placeholderSuffix;
	}

	public Properties getConfig() {
		return config;
	}

	public void setConfig(Properties config) {
		this.config = config;
	}
	@Override
	public Map<String, String> resolve(Properties propertie) {
		Map<String, String> configure = new HashMap<String, String>();
		configure.putAll(readConfigure(propertie));
		this.parse(configure);
		return configure;
	}
	public void setOverrideSysProps(boolean overrideSysProps) {
		this.overrideSysProps = overrideSysProps;
	}
	public boolean overrideSysProps() {
		return overrideSysProps;
	}
}
