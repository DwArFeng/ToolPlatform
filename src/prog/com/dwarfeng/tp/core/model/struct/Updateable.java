package com.dwarfeng.tp.core.model.struct;

/**
 * �ɸ��½ӿڡ�
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface Updateable{
	
	/**
	 * ���¸ýӿڡ�
	 * @throws ProcessException ���¹����쳣��
	 */
	public void update() throws ProcessException;

}
