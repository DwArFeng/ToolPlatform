package com.dwarfeng.tp.model.cfg;

import java.util.Objects;
import java.util.Properties;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ���Զ����ԡ�
 * <p> ����ĳ������� Properties �ļ����ɵ� �����Խӿڡ�
 * @author DwArFeng
 * @since 1.8
 */
public final class PropertiesMutilang<T extends Name> implements Mutilang<T> {
	
	private final Properties properties;
	private final String defaultString;
	
	/**
	 * ����һ���µ�ʵ����
	 * @param properties ָ���� Properties ʵ����
	 * @param defaultString �����������ֶ�ʱʹ�õ�Ĭ���ı���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public PropertiesMutilang(Properties properties, String defaultString) {
		Objects.requireNonNull(properties, "��ڲ��� properties ����Ϊ null��");
		Objects.requireNonNull(defaultString, "��ڲ��� defaultString ����Ϊ null��");
		
		this.properties = properties;
		this.defaultString = defaultString;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.cfg.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public String getString(T key) {
		return properties.getProperty(key.getName(), defaultString);
	}

}
