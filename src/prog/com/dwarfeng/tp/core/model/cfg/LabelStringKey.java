package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ������ַ�������
 * <p> ���ַ�����ö�ټ�¼�˳��������õ��������ַ����ļ���
 * @author  DwArFeng
 * @since 1.8
 */
public enum LabelStringKey implements Name{
	
	
	
	;

	private Name name;
	
	private LabelStringKey(Name name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name.getName();
	}

}
