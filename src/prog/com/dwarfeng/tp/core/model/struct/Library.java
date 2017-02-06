package com.dwarfeng.tp.core.model.struct;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.io.LibraryClassLoader;

/**
 * �⡣
 * <p> �ýӿڵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface Library extends ExternalReadWriteThreadSafe, Name{
	
	/**
	 * ��ȡ�ÿ��Ƿ񱻳������á�
	 * @return �ÿ��Ƿ񱻳������á�
	 */
	public boolean hasReference();
	
//	/**
//	 * ��ȡ���øÿ�����п����������
//	 * @return ���øÿ�����п����������
//	 */
//	public Set<LibraryClassLoader> getReferences();
	
	/**
	 * ����һ����������������á�
	 * @param libraryClassLoader ָ���Ŀ����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void reference(LibraryClassLoader libraryClassLoader);
	
	/**
	 * ���һ����������������á�
	 * @param libraryClassLoader ָ���Ŀ����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public void removeReference(LibraryClassLoader libraryClassLoader);
	
}
