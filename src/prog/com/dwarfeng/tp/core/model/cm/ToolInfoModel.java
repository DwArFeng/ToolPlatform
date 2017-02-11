package com.dwarfeng.tp.core.model.cm;

import java.util.Objects;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * ������Ϣģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface ToolInfoModel extends ObverserSet<ToolInfoObverser>, ExternalReadWriteThreadSafe, Iterable<ToolInfo>{
	
	/**
	 * ��ָ���Ĺ�����Ϣ��ӵ�ģ���С�
	 * @param toolInfo ָ���Ĺ�����Ϣ��
	 * @return �Ƿ���ӳɹ���
	 */
	public boolean add(ToolInfo toolInfo);
	
	/**
	 * �Ƴ�ָ���Ĺ�����Ϣ��
	 * @param toolInfo ָ���Ĺ�����Ϣ��
	 * @return �Ƿ�ɹ��Ƴ���
	 */
	public boolean remove(ToolInfo toolInfo);
	
	/**
	 * ���ָ���Ĺ�����Ϣ��
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
	 * ���ظ�ģ���Ƿ���ָ�������ƵĹ�����Ϣ��
	 * @param name ָ�������ơ�
	 * @return �Ƿ���ָ�����ƵĹ�����Ϣ��
	 */
	public boolean contains(String name);
	
	/**
	 * ���ظ�ģ���Ƿ���ָ�������ƵĹ�����Ϣ��
	 * @param name ָ�������ơ�
	 * @return �Ƿ���ָ�����ƵĹ�����Ϣ��
	 */
	public default boolean contains(Name name){
		if(Objects.isNull(name)) return false;
		return contains(name.getName());
	}
	
	/**
	 * ��ȡģ����ָ�����ƵĹ�����Ϣ��
	 * @param name ָ�������ơ�
	 * @return ģ����ָ�����ƵĹ�����Ϣ��
	 */
	public ToolInfo get(String name);
	
	/**
	 * ��ȡģ��������Ϊָ�����ƽӿڵ����ƵĹ�����Ϣ��
	 * @param name ָ�������ƽӿڡ�
	 * @return ģ��������Ϊָ�����ƽӿڵ����ƵĹ�����Ϣ��
	 */
	public default ToolInfo get(Name name){
		if(Objects.isNull(name)) return null;
		return get(name.getName());
	}
	
}
