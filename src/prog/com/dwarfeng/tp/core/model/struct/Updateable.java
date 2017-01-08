package com.dwarfeng.tp.core.model.struct;

/**
 * 可更新接口。
 * @author  DwArFeng
 * @since 1.8
 */
public interface Updateable{
	
	/**
	 * 更新该接口。
	 * <p> 该过程与 {@link #tryUpdate()}不一样的是，如果在更新过程中发生异常，
	 * 该方法会选择在内部处理异常，并且以返回 <code>false</code>的形式告知用户更新不成功。
	 * @return 更新过程是否成功。
	 */
	public boolean update();
	
	/**
	 * 尝试更新该接口。
	 * <p> 如果在更新的过程中发生了异常，该方法会将异常封装成 {@link ProcessException} 抛出，
	 * 用户可以查看 {@link ProcessException#getCause()}来获得发生的异常。
	 * @throws ProcessException 过程异常。
	 */
	public void tryUpdate() throws ProcessException;

}
