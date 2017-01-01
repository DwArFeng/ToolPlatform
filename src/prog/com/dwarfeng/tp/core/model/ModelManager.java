package com.dwarfeng.tp.core.model;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.struct.LoggerProvider;
import com.dwarfeng.tp.core.model.struct.MutilangProvider;
import com.dwarfeng.tp.core.model.vim.LoggerModel;
import com.dwarfeng.tp.core.model.vim.MutilangModel;
import com.dwarfeng.tp.core.model.vim.ResourceModel;

/**
 * 程序的模型管理器。
 * @author DwArFeng
 * @since 1.8
 */
public final class ModelManager {
	
	private final ResourceModel resourceModel;
	
	private final ConfigModel coreConfigModel;
	
	private final ConfigModel invisibleConfigModel;
	
	private final MutilangModel loggerMutilangModel;
	
	private final MutilangModel labelMutilangModel;
	
	private final MutilangProvider loggerMutilangProvider;
	
	private final MutilangProvider labelMutilangProvider;
	
	private final LoggerModel loggerModel;
	
	private final LoggerProvider loggerProvider;
	
	
	
}
