package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.model.struct.Process;

/**
 * �����ṩ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface ProcessProvider {
	
	/**
	 * ��ȡһ���µĳ�������ʱʹ�õĹ��̡�
	 * @return �µĳ�������ʱʹ�õĺ�̨���̡�
	 */
	public Process newStartProcess();

}
