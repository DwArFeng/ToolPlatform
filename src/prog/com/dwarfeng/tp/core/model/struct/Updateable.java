package com.dwarfeng.tp.core.model.struct;

/**
 * �ɸ��½ӿڡ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface Updateable{
	
	/**
	 * ���¸ýӿڡ�
	 * @throws ProcessException ���¹����쳣��
	 */
	public void update() throws ProcessException;

}
