package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.LibraryObverser;
import com.dwarfeng.tp.core.model.struct.Library;

/**
 * ��ģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface LibraryModel extends ExternalReadWriteThreadSafe, ObverserSet<LibraryObverser>, Iterable<Library>{
	
	/**
	 * ��ָ���Ŀ���ӵ�ģ���С�
	 * @param library ָ���Ŀ⡣
	 * @return �Ƿ���ӳɹ���
	 */
	public boolean add(Library library);
	
	/**
	 * �Ƴ�ָ���Ŀ⡣
	 * @param library ָ���Ŀ⡣
	 * @return �Ƿ�ɹ��Ƴ���
	 */
	public boolean remove(Library library);
	
	/**
	 * ���ָ���Ŀ⡣
	 */
	public void clear();
	
	/**
	 * ���ظ�ģ���е�Ԫ��������
	 * @return ��ģ���е�Ԫ��������
	 */
	public int size();
	
	/**
	 * ���ظ�ģ���Ƿ��ǿյġ�
	 * @return ��ģ���Ƿ��ǿյġ�
	 */
	public boolean isEmpty();
	
	/**
	 * ���ظ�ģ���Ƿ���ָ�������ƵĿ⡣
	 * @param name ָ�������ơ�
	 * @return �Ƿ���ָ�����ƵĿ⡣
	 */
	public boolean contains(Name name);
	
}
