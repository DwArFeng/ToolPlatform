package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.vim.MutilangModel;

/**
 * ������ģ�Ͷ�ȡ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface MutilangLoader {
	
	/**
	 * ��ָ���Ķ�����ģ���ж�ȡ���ݡ�
	 * @param mutilangModel ָ���Ķ�����ģ�͡�
	 * @throws LoadFailedException ��ȡʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void load(MutilangModel mutilangModel) throws LoadFailedException;

}
