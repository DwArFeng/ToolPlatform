package com.dwarfeng.tp.core.model;

import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.tp.core.model.struct.LoggerProvider;
import com.dwarfeng.tp.core.model.struct.MutilangProvider;
import com.dwarfeng.tp.core.model.vim.LoggerModel;
import com.dwarfeng.tp.core.model.vim.MutilangModel;
import com.dwarfeng.tp.core.model.vim.ResourceModel;

/**
 * �����ģ�͹�������
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
	 * TODO ����ע��
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
		Objects.requireNonNull(resourceModel, "��ڲ��� resourceModel ����Ϊ null��");
		Objects.requireNonNull(coreConfigModel, "��ڲ��� coreConfigModel ����Ϊ null��");
		Objects.requireNonNull(invisibleConfigModel, "��ڲ��� invisibleConfigModel ����Ϊ null��");
		Objects.requireNonNull(loggerMutilangModel, "��ڲ��� loggerMutilangModel ����Ϊ null��");
		Objects.requireNonNull(labelMutilangModel, "��ڲ��� labelMutilangModel ����Ϊ null��");
		Objects.requireNonNull(loggerMutilangProvider, "��ڲ��� loggerMutilangProvider ����Ϊ null��");
		Objects.requireNonNull(labelMutilangProvider, "��ڲ��� labelMutilangProvider ����Ϊ null��");
		Objects.requireNonNull(loggerModel, "��ڲ��� loggerModel ����Ϊ null��");
		Objects.requireNonNull(loggerProvider, "��ڲ��� loggerProvider ����Ϊ null��");
		
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
