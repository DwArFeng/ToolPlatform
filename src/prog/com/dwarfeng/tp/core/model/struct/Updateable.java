package com.dwarfeng.tp.core.model.struct;

/**
 * 可更新接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface Updateable{
	
	/**
	 * 更新该接口。
	 * @throws ProcessException 更新过程异常。
	 */
	public void update() throws ProcessException;

}
