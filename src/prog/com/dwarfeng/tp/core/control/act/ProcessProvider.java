package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.model.struct.Process;

/**
 * �����ṩ����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface ProcessProvider {
	
	/**
	 * ��ȡһ���µĳ����ʼ��ʱʹ�õĹ��̡�
	 * @return �µĳ����ʼ��ʱʹ�õĺ�̨���̡�
	 */
	public Process newInitializeProcess();

}
