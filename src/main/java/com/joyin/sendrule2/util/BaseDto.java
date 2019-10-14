package com.joyin.sendrule2.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 数据传输对象(DateTransferObject)<br>
 * 建议在参数传递过程中尽量使用Dto来传递<br>
 * 
 * @see Dto
 * @see Serializable
 */
public class BaseDto extends LinkedHashMap {

	public BaseDto() {
	}

	public BaseDto(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	public BaseDto(String key, Object value) {
		put(key, value);
	}

	public BaseDto(Boolean success) {
		setSuccess(success);
	}

	public BaseDto(Boolean success, String msg) {
		setSuccess(success);
		setMsg(msg);
	}

	/**
	 * 以BigDecimal类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return BigDecimal 键值
	 */
	public BigDecimal getAsBigDecimal(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
		if (obj != null)
			return (BigDecimal) obj;
		else
			return null;
	}

	/**
	 * 以Date类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Date 键值
	 */
	public Date getAsDate(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
		if (obj != null)
			return (Date) obj;
		else
			return null;
	}

	/**
	 * 以Integer类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Integer 键值
	 */
	public Integer getAsInteger(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
		if (obj != null)
			return (Integer) obj;
		else
			return null;
	}

	/**
	 * 以Long类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Long 键值
	 */
	public Long getAsLong(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Long", null);
		if (obj != null)
			return (Long) obj;
		else
			return null;
	}

	/**
	 * 以String类型返回键值
	 *
	 * @param key 键名
	 * @return String 键值
	 */
	public String getAsString(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null) {
			if(get(key) instanceof Clob){
				remove(key);
				put(key, obj.toString());
			}
			return (String) obj;
		} else
			return "";
	}

	/**
	 * 以List类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return List 键值
	 */
	public List getAsList(String key) {
		return (List) get(key);
	}

	/**
	 * 以Timestamp类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Timestamp 键值
	 */
	public Timestamp getAsTimestamp(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Timestamp", "yyyy-MM-dd HH:mm:ss");
		if (obj != null)
			return (Timestamp) obj;
		else
			return null;
	}

	/**
	 * 以Boolean类型返回键值
	 *
	 * @param key
	 *            键名
	 * @return Timestamp 键值
	 */
	public Boolean getAsBoolean(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "Boolean", null);
		if (obj != null)
			return (Boolean) obj;
		else
			return null;
	}


	/**
	 * 设置交易状态
	 *
	 * @param pSuccess
	 */
	public void setSuccess(Boolean pSuccess) {
		put("success", pSuccess);
		if (pSuccess) {
			// put("bflag", "1");
		} else {
			// put("bflag", "0");
		}

	}

	/**
	 * 获取状态
	 *
	 * @param pSuccess
	 */
	public Boolean getSuccess() {
		return getAsBoolean("success");
	}

	/**
	 * 设置交易提示信息
	 *
	 * @param pSuccess
	 */
	public void setMsg(String pMsg) {
		put("msg", pMsg);
	}

	/**
	 * 获取交易提示信息
	 *
	 * @param pSuccess
	 */
	public String getMsg() {
		return getAsString("msg");
	}

	/**
	 * 打印DTO对象
	 *
	 */
	public void println() {
		System.out.println(this);
	}

	public Object[] keyArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] arr = new Object[size()];
		int i = 0;
		for (Iterator iter = keySet().iterator(); iter.hasNext();) {
			arr[(i++)] = iter.next();
		}
		return arr;
	}

	public Object[] valueArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] arr = new Object[size()];
		int i = 0;
		for (Iterator iter = values().iterator(); iter.hasNext();) {
			arr[(i++)] = iter.next();
		}
		return arr;
	}

	/**
	 * 全Clob字段转String方法
	 * @Description 将结果集进行遍历，对于内容是clob的字段通过getAsString方法进行转换
	 */
	public void changeClob() {
		Iterator iter = this.entrySet().iterator();
		List<String> clobKeys = new ArrayList<String>() ;
		while (iter.hasNext()) {
		    Entry entry = (Entry) iter.next();
			if (entry.getValue() instanceof Clob) {
				clobKeys.add(entry.getKey().toString());
			}
		}
	    Iterator<String> iterator = clobKeys.iterator();
	    while(iterator.hasNext()){
	    	this.getAsString(iterator.next());
	    }
	}

	/**
	 *除去<a>标签
	 * @Description 将结果集进行遍历，对于内容含有<a>的字段
	 */
	public void changeALabel() {
		Iterator iter = this.entrySet().iterator();
		List<String> aKeys = new ArrayList<String>() ;
		while (iter.hasNext()) {
		    Entry entry = (Entry) iter.next();
			if (entry.getValue()!=null&&((entry.getValue().toString().indexOf("</a>"))!=-1||(entry.getValue().toString().indexOf("</font>"))!=-1)) {
				aKeys.add(entry.getKey().toString());
			}
		} 
	    Iterator<String> iterator = aKeys.iterator();
	    while(iterator.hasNext()){
	    
	    	this.getAsnotA(iterator.next());
	    }
	}
	
	
	/**
	 * 以String类型返回键值
	 * 
	 * @param key 键名
	 * @return String 键值
	 */
	public String getAsnotA(String key) {
		Object obj = TypeCaseHelper.convert(get(key), "String", null);
		if (obj != null) {
			if(get(key).toString().toString().indexOf("</a>")!=-1||get(key).toString().toString().indexOf("</font>")!=-1){
				 remove(key);
				 String contentString=obj.toString();
				 String patternTag = "</?[a-zA-Z]+[^><]*>";
				 String patternBlank = "(^\\s*)|(\\s*$)";
				 contentString= contentString.replaceAll(patternTag, "").replaceAll(patternBlank, "");   
				 put(key, contentString);
			}
			return (String) obj;
		} else
			return "";
	}
	
	@SuppressWarnings("unchecked")
	public BaseDto add(String key, Object value) {
		this.put(key, value);
		return this;
	}
}
