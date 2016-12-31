package com.dwarfeng.tp.core.model.io;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.tp.core.model.vim.MutilangModel;

public interface MutilangSaver {

	/**
	 * ��ָ���Ķ�����ģ���б������ݡ�
	 * @param mutilangModel ָ���Ķ�����ģ�͡�
	 * @throws SaveFailedException ����ʧ���쳣��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void save(MutilangModel mutilangModel) throws SaveFailedException;
	
}
