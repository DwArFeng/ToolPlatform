package com.dwarfeng.tp.core.model.cfg;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.DefaultName;

/**
 * 记录器文本键。
 * <p> 该键枚举用于实现记录器的多语言。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public enum LoggerStringKey implements Name{

	FinishedFlowTaker_1(new DefaultName("FinishedFlowTaker.1")),
	FinishedFlowTaker_2(new DefaultName("FinishedFlowTaker.2")),
	FinishedFlowTaker_3(new DefaultName("FinishedFlowTaker.3")),
	FinishedFlowTaker_4(new DefaultName("FinishedFlowTaker.4")),
	
	ToolPlatform_Manager_1(new DefaultName("ToolPlatform.Manager.1")),
	ToolPlatform_Manager_2(new DefaultName("ToolPlatform.Manager.2")),
	
	ToolPlatform_FlowProvider_1(new DefaultName("ToolPlatform.FlowProvider.1")),
	ToolPlatform_FlowProvider_2(new DefaultName("ToolPlatform.FlowProvider.2")),
	ToolPlatform_FlowProvider_3(new DefaultName("ToolPlatform.FlowProvider.3")),
	ToolPlatform_FlowProvider_4(new DefaultName("ToolPlatform.FlowProvider.4")),
	ToolPlatform_FlowProvider_5(new DefaultName("ToolPlatform.FlowProvider.5")),
	ToolPlatform_FlowProvider_6(new DefaultName("ToolPlatform.FlowProvider.6")),
	ToolPlatform_FlowProvider_7(new DefaultName("ToolPlatform.FlowProvider.7")),
	ToolPlatform_FlowProvider_8(new DefaultName("ToolPlatform.FlowProvider.8")),
	ToolPlatform_FlowProvider_9(new DefaultName("ToolPlatform.FlowProvider.9")),
	ToolPlatform_FlowProvider_10(new DefaultName("ToolPlatform.FlowProvider.10")),
	ToolPlatform_FlowProvider_11(new DefaultName("ToolPlatform.FlowProvider.11")),
	ToolPlatform_FlowProvider_12(new DefaultName("ToolPlatform.FlowProvider.12")),
	ToolPlatform_FlowProvider_13(new DefaultName("ToolPlatform.FlowProvider.13")),
	ToolPlatform_FlowProvider_14(new DefaultName("ToolPlatform.FlowProvider.14")),
	ToolPlatform_FlowProvider_15(new DefaultName("ToolPlatform.FlowProvider.15")),
	ToolPlatform_FlowProvider_16(new DefaultName("ToolPlatform.FlowProvider.16")),
	ToolPlatform_FlowProvider_17(new DefaultName("ToolPlatform.FlowProvider.17")),
	ToolPlatform_FlowProvider_18(new DefaultName("ToolPlatform.FlowProvider.18")),
	ToolPlatform_FlowProvider_19(new DefaultName("ToolPlatform.FlowProvider.19")),
	ToolPlatform_FlowProvider_20(new DefaultName("ToolPlatform.FlowProvider.20")),
	ToolPlatform_FlowProvider_21(new DefaultName("ToolPlatform.FlowProvider.21")),
	ToolPlatform_FlowProvider_22(new DefaultName("ToolPlatform.FlowProvider.22")),
	ToolPlatform_FlowProvider_23(new DefaultName("ToolPlatform.FlowProvider.23")),
	ToolPlatform_FlowProvider_24(new DefaultName("ToolPlatform.FlowProvider.24")),
	ToolPlatform_FlowProvider_25(new DefaultName("ToolPlatform.FlowProvider.25")),
	ToolPlatform_FlowProvider_26(new DefaultName("ToolPlatform.FlowProvider.26")),

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
