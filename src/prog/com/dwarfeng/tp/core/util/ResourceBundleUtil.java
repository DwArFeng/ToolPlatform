package com.dwarfeng.tp.core.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * ResourceBundle�еĳ��ù��ߡ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ResourceBundleUtil {
	
	/**
	 * ��ӳ�����ʽ����ָ����ResourceBundle��
	 * <p> ���ص�ӳ��ʱ���ɸ��ĵģ����Ե�����༭�������׳� {@link UnsupportedOperationException}��
	 * @param resourceBundle ָ����ResourceBundle��
	 * @return ResourceBundle��ӳ����ʽ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static Map<String, String> toMap(ResourceBundle resourceBundle){
		Objects.requireNonNull(resourceBundle, "��ڲ��� resourceBundle ����Ϊ null��");
		Map<String, String> map = new HashMap<>();
		for(String key : resourceBundle.keySet()){
			map.put(key, resourceBundle.getString(key));
		}
		return Collections.unmodifiableMap(map);
	}
	
	//��ֹ�ⲿʵ������
	private ResourceBundleUtil() {}

}
