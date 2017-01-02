package com.dwarfeng.tp.core.model.io;

import java.io.OutputStream;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * xml多语言模型保存器。
 * <p> 使用xml保存多语言模型。
 * @author  DwArFeng
 * @since 1.8
 */
public final class XmlMutilangSaver extends StreamMutilangSaver {

	/**
	 * 新实例。
	 * @param out 指定的输出流。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
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
