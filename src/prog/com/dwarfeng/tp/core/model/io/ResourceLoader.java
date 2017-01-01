package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.vim.ResourceModel;

/**
 * ��Դģ�Ͷ�ȡ����
 * @author  DwArFeng
 * @since 1.8
 */
public interface ResourceLoader {

	/**
	 * ��ָ������Դģ���ж�ȡ���ݡ�
	 * @param resourceModel ָ������Դģ�͡�
	 * @throws LoadFailedException ��ȡʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void load(ResourceModel resourceModel) throws LoadFailedException;
	
}
