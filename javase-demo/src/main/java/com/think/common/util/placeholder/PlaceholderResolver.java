package com.think.common.util.placeholder;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * properties文件占位符解析器
 **/
public interface PlaceholderResolver {
  /**
   * 解析配置信息
   * @param properties
   * @return
   */
  public Map<String, String> resolve(List<Properties> properties);
  /**
   * 解析配置信息
   * @param propertie
   * @return
   */
  public Map<String, String> resolve(Properties props);
  
} 
