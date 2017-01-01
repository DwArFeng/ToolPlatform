package com.dwarfeng.tp.core.control;

import java.util.Objects;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.tp.core.control.proc.ActionProcessor;
import com.dwarfeng.tp.core.control.proc.CoreProvider;
import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.view.ViewManager;

/**
 * ToolPlatform（DwArFeng 的工具平台）。
 * <p> 该工具平台是用来管理 DwArFeng 编写的众多的工具的。
 * 该工具平台利用反射读取其工具目录下的所有工具，并且拥有将这些工具进行分标签管理、搜索、分类的功能。
 * <p> TODO 需要进行详细的描述。
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {
	
	public static void main(String[] args) {
		new ToolPlatform();
	}
	
	/**
	 * 程序中的属性集合。
	 * <p> 该属性集合提供程序中的一些开放属性，比如程序的名称、程序的作者、程序的版本等等。
	 * @author DwArFeng
	 * @since 1.8
	 */
	public final static class Attributes{
		
		/**程序的版本*/
		public final static Version VERSION = new DefaultVersion.Builder()
				.type(VersionType.RELEASE)
				.firstVersion((byte) 0)
				.secondVersion((byte) 0)
				.thirdVersion((byte) 0)
				.buildDate("20161222")
				.buildVersion('A')
				.build();
		
		/**程序的作者*/
		public final static String author = "DwArFeng";
		
		
	}
	
	
	private final ActionProcessor actionProcessor = new ActionProcessor() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.ActionProcessor#start()
		 */
		@Override
		public void start() {
			
		}
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
	/**程序的核心提供器*/
	private final CoreProvider coreProvider;
	
	/**程序中引用的模型管理器*/
	private ModelManager modelManager;
	/**程序中引用的视图管理器*/
	private ViewManager viewManager;

	
	
	/**
	 * 生成一个默认的工具平台实例。
	 * 生成一个具有指定 TODO
	 */
	public ToolPlatform() {
		this(ToolPlatformHelper.newCoreProvider());
	}
	
	/**
	 * 
	 * @param preLogger
	 * @param preMutilang
	 */
	public ToolPlatform(CoreProvider coreProvider){
		Objects.requireNonNull(coreProvider, "入口参数 coreProvider 不能为 null。");
		this.coreProvider = coreProvider;
	}
	
}
