package com.dwarfeng.tp.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;

/**
 * ��¼���ı�����
 * <p> �ü�ö������ʵ�ּ�¼���Ķ����ԡ�
 * @author  DwArFeng
 * @since 1.8
 */
public enum LoggerStringKey implements Name{
	
	ProgramAttributes_0("ProgramAttributes.0"),
	ProgramAttributes_1("ProgramAttributes.1"),
	ProgramAttributes_2("ProgramAttributes.2"),
	ProgramAttributes_3("ProgramAttributes.3"),
	ProgramAttributes_4("ProgramAttributes.4"),
	
	;

	private String name;
	
	private LoggerStringKey(String name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
