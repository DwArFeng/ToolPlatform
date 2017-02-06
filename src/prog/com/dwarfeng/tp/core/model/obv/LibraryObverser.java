package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * ��۲�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface LibraryObverser extends Obverser {

	/**
	 * ֪ͨģ���������ָ���Ŀ⡣
	 * @param library ָ���Ŀ⡣
	 */
	public void fireLibraryAdded(Library library);
	
	/**
	 * ֪ͨģ�����Ƴ���ָ���Ŀ⡣
	 * @param library ָ���Ŀ⡣
	 */
	public void fireLibraryRemoved(Library library);
	
	/**
	 * ֪ͨģ�ͱ������
	 */
	public void fireCleared();
	
}
