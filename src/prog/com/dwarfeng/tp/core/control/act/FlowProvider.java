package com.dwarfeng.tp.core.control.act;

import com.dwarfeng.tp.core.model.struct.Flow;

/**
 * �����ṩ����
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface FlowProvider {
	
	/**
	 * ��ȡһ���µĳ����ʼ��ʱʹ�õĹ��̡�
	 * @return �µĳ����ʼ��ʱʹ�õĺ�̨���̡�
	 */
	public Flow newInitializeFlow();
	
	/**
	 * ��ȡһ���µĶ�ȡ��Ĺ��̡�
	 * @return �µĶ�ȡ��Ĺ��̡�
	 */
	public Flow newLoadLibFlow();
	
	/**
	 * ��ȡһ���µļ���Ĺ��̡�
	 * @return �µļ���Ĺ��̡�
	 */
	public Flow newCheckLibFlow();

}
