package com.dwarfeng.tp.model.struct;

import java.util.Map;

import com.dwarfeng.tp.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.model.cfg.Mutilang;

/**
 * 生成器。
 * <p> 通过 {@link ProgramResource} 生成程序的实例。
 * <br> 生成器仅仅在程序初始化的时候被使用，在此时如果出现异常，结果将是致命的，应该在告知用户之后立即退出程序。
 * @author  DwArFeng
 * @since 1.8
 */
public interface Generator<T> {
	
	/**
	 * 通过该生成器生成一个指定的实例。
	 * @param resourceMap 指定的资源映射。
	 * @param logger 记录有关信息的记录器。
	 * @param mutilang 记录器使用的多语言接口。
	 * @return 新的实例。
	 * @throws EmergencyException 在生成过程中出现异常。
	 * @throws NullPointerException 入口参数为 <code>null</code>。
	 */
	public T newInstance(Map<String, ProgramResource> resourceMap, ProgramLogger logger, Mutilang<LoggerStringKey> mutilang) throws EmergencyException;

}
