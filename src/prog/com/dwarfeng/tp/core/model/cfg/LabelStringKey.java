package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

/**
 * ������ַ�������
 * <p> ���ַ�����ö�ټ�¼�˳��������õ��������ַ����ļ���
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum LabelStringKey implements Name{
	
	MainFrame_1(new DefaultName("MainFrame.1")),
	MainFrame_2(new DefaultName("MainFrame.2")),
	MainFrame_3(new DefaultName("MainFrame.3")),
	MainFrame_4(new DefaultName("MainFrame.4")),
	MainFrame_5(new DefaultName("MainFrame.5")),
	MainFrame_6(new DefaultName("MainFrame.6")),
	MainFrame_7(new DefaultName("MainFrame.7")),

	JTpconsole_1(new DefaultName("JTpconsole.1")),
	JTpconsole_2(new DefaultName("JTpconsole.2")),
	JTpconsole_3(new DefaultName("JTpconsole.3")),
	
	JToolRuntimePanel_1(new DefaultName("JToolRuntimePanel.1")),
	
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