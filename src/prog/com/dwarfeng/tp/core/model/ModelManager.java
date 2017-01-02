package com.dwarfeng.tp.core.model;

import java.util.Objects;

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
	
	/*
	 * TODO 生成注释
	 */
	public ModelManager(ResourceModel resourceModel, 
			ConfigModel coreConfigModel, 
			ConfigModel invisibleConfigModel,
			MutilangModel loggerMutilangModel, 
			MutilangModel labelMutilangModel,
			MutilangProvider loggerMutilangProvider,
			MutilangProvider labelMutilangProvider, 
			LoggerModel loggerModel,
			LoggerProvider loggerProvider) {
		Objects.requireNonNull(resourceModel, "入口参数 resourceModel 不能为 null。");
		Objects.requireNonNull(coreConfigModel, "入口参数 coreConfigModel 不能为 null。");
		Objects.requireNonNull(invisibleConfigModel, "入口参数 invisibleConfigModel 不能为 null。");
		Objects.requireNonNull(loggerMutilangModel, "入口参数 loggerMutilangModel 不能为 null。");
		Objects.requireNonNull(labelMutilangModel, "入口参数 labelMutilangModel 不能为 null。");
		Objects.requireNonNull(loggerMutilangProvider, "入口参数 loggerMutilangProvider 不能为 null。");
		Objects.requireNonNull(labelMutilangProvider, "入口参数 labelMutilangProvider 不能为 null。");
		Objects.requireNonNull(loggerModel, "入口参数 loggerModel 不能为 null。");
		Objects.requireNonNull(loggerProvider, "入口参数 loggerProvider 不能为 null。");
		
		this.resourceModel = resourceModel;
		this.coreConfigModel = coreConfigModel;
		this.invisibleConfigModel = invisibleConfigModel;
		this.loggerMutilangModel = loggerMutilangModel;
		this.labelMutilangModel = labelMutilangModel;
		this.loggerMutilangProvider = loggerMutilangProvider;
		this.labelMutilangProvider = labelMutilangProvider;
		this.loggerModel = loggerModel;
		this.loggerProvider = loggerProvider;
	}

	/**
	 * @return the resourceModel
	 */
	public ResourceModel getResourceModel() {
		return resourceModel;
	}

	/**
	 * @return the coreConfigModel
	 */
	public ConfigModel getCoreConfigModel() {
		return coreConfigModel;
	}
	
	/**
	 * @return the invisibleConfigModel
	 */
	public ConfigModel getInvisibleConfigModel() {
		return invisibleConfigModel;
	}

	/**
	 * @return the loggerMutilangModel
	 */
	public MutilangModel getLoggerMutilangModel() {
		return loggerMutilangModel;
	}

	/**
	 * @return the labelMutilangModel
	 */
	public MutilangModel getLabelMutilangModel() {
		return labelMutilangModel;
	}

	/**
	 * @return the loggerMutilangProvider
	 */
	public MutilangProvider getLoggerMutilangProvider() {
		return loggerMutilangProvider;
	}

	/**
	 * @return the labelMutilangProvider
	 */
	public MutilangProvider getLabelMutilangProvider() {
		return labelMutilangProvider;
	}

	/**
	 * @return the loggerModel
	 */
	public LoggerModel getLoggerModel() {
		return loggerModel;
	}

	/**
	 * @return the loggerProvider
	 */
	public LoggerProvider getLoggerProvider() {
		return loggerProvider;
	}

	
}
