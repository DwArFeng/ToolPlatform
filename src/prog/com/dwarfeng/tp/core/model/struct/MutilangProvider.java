package com.dwarfeng.tp.core.model.struct;

import java.util.Locale;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * �����Խӿ��ṩ����
 * <p>�ṩ�����Խӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangProvider {
	
	/**
	 * ���ظö����Խӿ��ṩ��ʹ�õĶ�����ģ�͡�
	 * @return ʹ�õĶ�����ģ�͡�
	 */
	public MutilangModel getMutilangModel();

	/**
	 * ��ȡ��ǰ�Ķ����Խӿڡ�
	 * <p> �÷���ֵӦ�㲻Ϊ <code>null</code>��
	 * @return ��ǰ�Ķ����Խӿڡ�
	 */
	public Mutilang getMutilang();
	
	/**
	 * ��ȡ�ö����Խӿ��ṩ������֧�ֵ����м���
	 * <p> ���صļ����ǲ��ɸı�ģ���ͼ�������еĸ��ķ������׳� {@link UnsupportedOperationException}��
	 * @return �ö����Խӿ��ṩ������֧�ֵ������ֶΡ�
	 */
	public Set<Name> getSupportedKeys();
	
	/**
	 * �ж�ָ���ļ��Ƿ��ܸ��ṩ����֧�֡�
	 * <p> ��ڲ�������Ϊ <code>null</code>����ʱ���÷����㷵�� <code>false</code>��
	 * @param key ָ���ļ���
	 * @return ���ṩ���Ƿ�֧��ָ���ļ���
	 */
	public boolean isSupport(Name key);
	
	/**
	 * �������Խӿڸ���ΪĬ��ֵ��
	 */
	public void update2Default();
	
	/**
	 * ˢ�¶����Խӿڣ�ʹ�����Խӿڷ���ָ�������ԡ�
	 * @param locale ָ�������ԡ�
	 * @throws MutilangException �����Խӿ��쳣��
	 * @throws ProcessException �����쳣��
	 */
	public void update(Locale locale) throws ProcessException;
}
