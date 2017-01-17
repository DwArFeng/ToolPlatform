package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 1.8
 */
public enum LoggerStringKey implements Name{

	FinishedProcessTaker_1(new DefaultName("FinishedProcessTaker.1")),
	FinishedProcessTaker_2(new DefaultName("FinishedProcessTaker.2")),
	FinishedProcessTaker_3(new DefaultName("FinishedProcessTaker.3")),
	FinishedProcessTaker_4(new DefaultName("FinishedProcessTaker.4")),
	
	ToolPlatform_Manager_1(new DefaultName("ToolPlatform.Manager.1")),
	ToolPlatform_Manager_2(new DefaultName("ToolPlatform.Manager.2")),
	
	ToolPlatform_ProcessProvider_1(new DefaultName("ToolPlatform.ProcessProvider.1")),
	ToolPlatform_ProcessProvider_2(new DefaultName("ToolPlatform.ProcessProvider.2")),
	ToolPlatform_ProcessProvider_3(new DefaultName("ToolPlatform.ProcessProvider.3")),
	ToolPlatform_ProcessProvider_4(new DefaultName("ToolPlatform.ProcessProvider.4")),
	ToolPlatform_ProcessProvider_5(new DefaultName("ToolPlatform.ProcessProvider.5")),
	ToolPlatform_ProcessProvider_6(new DefaultName("ToolPlatform.ProcessProvider.6")),
	ToolPlatform_ProcessProvider_7(new DefaultName("ToolPlatform.ProcessProvider.7")),
	ToolPlatform_ProcessProvider_8(new DefaultName("ToolPlatform.ProcessProvider.8")),
	ToolPlatform_ProcessProvider_9(new DefaultName("ToolPlatform.ProcessProvider.9")),
	ToolPlatform_ProcessProvider_10(new DefaultName("ToolPlatform.ProcessProvider.10")),
	ToolPlatform_ProcessProvider_11(new DefaultName("ToolPlatform.ProcessProvider.11")),
	
	Update_LoggerMutilang_1(new DefaultName("Update.LoggerMutilang.1")),
	Update_LabelMutilang_1(new DefaultName("Update.LabelMutilang.1")),
	Update_Logger_1(new DefaultName("Update.Logger.1")),

	;

	private Name name;
	
	private LoggerStringKey(Name name) {
		this.name = name;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name.getName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return name.getName();
	}

}
