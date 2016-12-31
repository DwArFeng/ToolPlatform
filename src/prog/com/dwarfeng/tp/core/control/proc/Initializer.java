package com.dwarfeng.tp.core.control.proc;

import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.struct.InitializeFailedException;
import com.dwarfeng.tp.core.view.ViewManager;

/**
 * 初始化器。
 * <p> 负责整个初始化的过程。
 * @author DwArFeng
 * @since 1.8
 */
public interface Initializer {

	/**
	 * 进行初始化。
	 * @param preLogger 记录器还未生成之前使用的记录器。
	 * @throws 因初始化失败而抛出的紧急异常。
	 */
	public void init() throws InitializeFailedException;
	
	/**
	 * 获取初始化器中的模型管理器。
	 * @return 模型管理器。
	 */
	public ModelManager getModelManager();
	
	/**
	 * 获取初始化器中的视图管理器。
	 * @return 视图管理器。
	 */
	public ViewManager getViewManager();

}
