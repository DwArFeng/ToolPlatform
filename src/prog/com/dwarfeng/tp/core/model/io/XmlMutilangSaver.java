package com.dwarfeng.tp.core.model.io;

import java.io.OutputStream;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * xml������ģ�ͱ�������
 * <p> ʹ��xml���������ģ�͡�
 * @author  DwArFeng
 * @since 1.8
 */
public final class XmlMutilangSaver extends StreamMutilangSaver {

	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlMutilangSaver(OutputStream out) {
		super(out);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.MutilangSaver#save(com.dwarfeng.tp.core.model.vim.MutilangModel)
	 */
	@Override
	public void save(MutilangModel mutilangModel) throws SaveFailedException {
		// TODO Auto-generated method stub
		
	}

}
