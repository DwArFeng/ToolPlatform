package com.dwarfeng.tp.core.control;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.basic.threads.NumberedThreadFactory;
import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigSaver;
import com.dwarfeng.tp.core.model.cfg.BlockKey;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.ModalConfig;
import com.dwarfeng.tp.core.model.cfg.ResourceKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.cm.BlockModel;
import com.dwarfeng.tp.core.model.cm.CoreConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultBackgroundModel;
import com.dwarfeng.tp.core.model.cm.DefaultBlockModel;
import com.dwarfeng.tp.core.model.cm.DefaultCoreConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultLibraryModel;
import com.dwarfeng.tp.core.model.cm.DefaultLoggerModel;
import com.dwarfeng.tp.core.model.cm.DefaultModalConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultMutilangModel;
import com.dwarfeng.tp.core.model.cm.DefaultResourceModel;
import com.dwarfeng.tp.core.model.cm.DefaultToolHistoryModel;
import com.dwarfeng.tp.core.model.cm.DefaultToolInfoModel;
import com.dwarfeng.tp.core.model.cm.DefaultToolRuntimeModel;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.cm.LoggerModel;
import com.dwarfeng.tp.core.model.cm.ModalConfigModel;
import com.dwarfeng.tp.core.model.cm.MutilangModel;
import com.dwarfeng.tp.core.model.cm.ResourceModel;
import com.dwarfeng.tp.core.model.cm.ToolHistoryModel;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.io.XmlBlockLoader;
import com.dwarfeng.tp.core.model.io.XmlLibraryLoader;
import com.dwarfeng.tp.core.model.io.XmlLoggerLoader;
import com.dwarfeng.tp.core.model.io.XmlMutilangLoader;
import com.dwarfeng.tp.core.model.io.XmlPathGetter;
import com.dwarfeng.tp.core.model.io.XmlResourceLoader;
import com.dwarfeng.tp.core.model.io.XmlUnsafeToolHistoryLoader;
import com.dwarfeng.tp.core.model.io.XmlUnsafeToolInfoLoader;
import com.dwarfeng.tp.core.model.obv.LoggerAdapter;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.obv.MutilangAdapter;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.AbstractFlow;
import com.dwarfeng.tp.core.model.struct.DefaultExitedRunningToolTaker;
import com.dwarfeng.tp.core.model.struct.DefaultFinishedFlowTaker;
import com.dwarfeng.tp.core.model.struct.DefaultRunningTool;
import com.dwarfeng.tp.core.model.struct.DefaultToolInfo;
import com.dwarfeng.tp.core.model.struct.ExitedRunningToolTaker;
import com.dwarfeng.tp.core.model.struct.FinishedFlowTaker;
import com.dwarfeng.tp.core.model.struct.Flow;
import com.dwarfeng.tp.core.model.struct.Library;
import com.dwarfeng.tp.core.model.struct.LibraryKeyChecker;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
import com.dwarfeng.tp.core.model.struct.UnsafeToolHistory;
import com.dwarfeng.tp.core.model.struct.UnsafeToolInfo;
import com.dwarfeng.tp.core.util.Constants;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.gui.MainFrame;
import com.dwarfeng.tp.core.view.gui.SplashScreen;
import com.dwarfeng.tp.core.view.obv.MainFrameObverser;
import com.dwarfeng.tp.core.view.struct.AbstractMainFrameController;
import com.dwarfeng.tp.core.view.struct.AbstractSplashScreenController;
import com.dwarfeng.tp.core.view.struct.MainFrameController;
import com.dwarfeng.tp.core.view.struct.SplashScreenController;

/**
 * ToolPlatform（DwArFeng 的工具平台）。
 * <p> 该工具平台是用来管理 DwArFeng 编写的众多的工具的。
 * 该工具平台利用反射读取其工具目录下的所有工具，并且拥有将这些工具进行分标签管理、搜索、分类的功能。
 * <p> TODO ToolPlatform 的Doc文档需要进行详细的描述。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ToolPlatform {
	
	/**
	 * 调试用的启动方法。
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws ProcessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new ToolPlatform().start();
	}
	
	/**程序的版本*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**程序的实例列表，用于持有引用*/
	private static final Set<ToolPlatform> INSTANCES  = Collections.synchronizedSet(new HashSet<>());
	/**工具平台的进程工厂*/
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("tool_platform");
	
	/**程序的过程提供器*/
	private final FlowProvider flowProvider = new FlowProvider();
	/**程序的退出钩子提供器*/
	private final ShutdownHookProvider shutdownHookProvider = new ShutdownHookProvider();
	/**程序被中止时的钩子*/
	private final Map<String, Thread> shutdownHooks = Collections.synchronizedMap(new HashMap<>());

	/**程序管理器*/
	private final Manager manager;
	/**程序的状态*/
	private final AtomicReference<RuntimeState> state;
	
	/**
	 * 新实例。
	 */
	public ToolPlatform() {
		this.manager = new Manager();
		this.state = new AtomicReference<RuntimeState>(RuntimeState.NOT_START);
		
		//为自己保留引用。
		INSTANCES.add(this);
	}
	
	/**
	 * 启动程序。
	 * @throws ProcessException 过程异常。
	 * @throws IllegalStateException 程序已经开始。
	 */
	public void start() throws ProcessException{
		//开启初始化过程
		final Flow initializeFlow = flowProvider.newInitializeFlow();
		manager.getBackgroundModel().submit(initializeFlow);
		while(! initializeFlow.isDone()){
			try {
				initializeFlow.waitFinished();
			} catch (InterruptedException ignore) {
				//中断也要按照基本法
			}
		}
		if(initializeFlow.getThrowable() != null){
			throw new ProcessException("初始化过程失败", initializeFlow.getThrowable());
		}
	}
	
	

	/**
	 * 返回程序的状态。
	 * @return 程序的状态。
	 */
	public RuntimeState getState() {
		return state.get();
	}
	
	private void setState(RuntimeState state){
		this.state.set(state);
	}
	
	/**
	 * 尝试关闭本程序。
	 * <p> 调用该方法后，会尝试关闭程序。如果程序满足关闭的条件，则关闭，否则，会询问用户。
	 * 就像是用户点击关闭按钮那样。
	 */
	public void tryExit(){
		manager.getBackgroundModel().submit(flowProvider.newClosingFlow());
	}
	
	private void exit(){
		THREAD_FACTORY.newThread(new Exitor()).start();
	}
	
	private boolean putShutdownHook(String key, Runnable runnable){
		if(shutdownHooks.containsKey(key)) return false;
		Thread hook = THREAD_FACTORY.newThread(runnable);
		shutdownHooks.put(key, hook);
		Runtime.getRuntime().addShutdownHook(hook);
		return true;
	}
	
	private boolean removeShutdownHook(String key){
		if(! shutdownHooks.containsKey(key)) return false;
		Thread hook = shutdownHooks.remove(key);
		return Runtime.getRuntime().removeShutdownHook(hook);
	}
	
	
	private final class Manager{
		
		//model
		private ResourceModel resourceModel = new DefaultResourceModel();
		private LoggerModel loggerModel = new DefaultLoggerModel();
		private CoreConfigModel coreConfigModel = new DefaultCoreConfigModel();
		private ModalConfigModel modalConfigModel = new DefaultModalConfigModel();
		private MutilangModel loggerMutilangModel = new DefaultMutilangModel();
		private MutilangModel labelMutilangModel = new DefaultMutilangModel();
		private ToolInfoModel toolInfoModel = new DefaultToolInfoModel();
		private BackgroundModel backgroundModel = new DefaultBackgroundModel();
		private LibraryModel libraryModel = new DefaultLibraryModel();
		private BlockModel blockModel = new DefaultBlockModel();
		private ToolRuntimeModel toolRuntimeModel = new DefaultToolRuntimeModel();
		private ToolHistoryModel toolHistoryModel = new DefaultToolHistoryModel();
		//structs
		private FinishedFlowTaker finishedFlowTaker = new DefaultFinishedFlowTaker(backgroundModel);
		private ExitedRunningToolTaker exitedRunningToolTaker = new DefaultExitedRunningToolTaker(toolRuntimeModel);
		//obvs
		private LoggerObverser loggerObverser = new LoggerAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.setLogger(loggerModel.getLogger());
				exitedRunningToolTaker.setLogger(loggerModel.getLogger());
			}
		};
		private MutilangObverser loggerMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.setMutilang(loggerMutilangModel.getMutilang());
				exitedRunningToolTaker.setMutilang(loggerMutilangModel.getMutilang());
			}
		};
		private ConfigObverser coreConfigObverser = new ConfigAdapter() {
			@Override
			public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue,String validValue) {
				if(configKey.equals(CoreConfig.MUTILANG_LOGGER.getConfigKey())){
					try {
						loggerMutilangModel.setCurrentLocale(coreConfigModel.getLoggerMutilangLocale());
						loggerMutilangModel.update();
					} catch (ProcessException e) {
						loggerModel.getLogger().warn(loggerMutilangModel.getMutilang().getString(LoggerStringKey.Update_LoggerMutilang_1.getName()), e);
					}
				}
				
				if(configKey.equals(CoreConfig.RUNNINGTOOL_AUTOTAKE.getConfigKey())){
					exitedRunningToolTaker.setPause(! coreConfigModel.isRunningToolAutoTake());
				}
				
			}
		};
		//GuiControllers
		private SplashScreenController splashScreenController = new AbstractSplashScreenController(){

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.struct.AbstractGuiController#subNewInstance()
			 */
			@Override
			protected SplashScreen subNewInstance() {
				return new SplashScreen();
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.struct.AbstractGuiController#subDispose(java.awt.Component)
			 */
			@Override
			protected void subDispose(SplashScreen component) {
				component.dispose();
			}
			
		};
		private MainFrameController mainFrameController = new AbstractMainFrameController() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.struct.AbstractGuiController#subNewInstance()
			 */
			@Override
			protected MainFrame subNewInstance() {
				MainFrame mainFrame = new MainFrame(
						labelMutilangModel.getMutilang(),
						backgroundModel,
						toolInfoModel,
						libraryModel,
						toolRuntimeModel
				);
				mainFrame.addObverser(mainFrameObverser);
				return mainFrame;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.struct.AbstractGuiController#subDispose(java.awt.Component)
			 */
			@Override
			protected void subDispose(MainFrame component) {
				component.removeObverser(mainFrameObverser);
				component.dispose();
			}
		};
		
		
		private final MainFrameObverser mainFrameObverser = new MainFrameObverser() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.obv.MainFrameObverser#fireWindowClosing()
			 */
			@Override
			public void fireWindowClosing() {
				manager.getBackgroundModel().submit(flowProvider.newClosingFlow());
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.obv.MainFrameObverser#fireFireWindowActivated()
			 */
			@Override
			public void fireFireWindowActivated() {
				manager.getBackgroundModel().submit(flowProvider.newLoadLibFlow());
				manager.getBackgroundModel().submit(flowProvider.newCheckLibFlow());
				
			}
		
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.obv.MainFrameObverser#fireRunTool(com.dwarfeng.tp.core.model.struct.ToolInfo)
			 */
			@Override
			public void fireRunTool(ToolInfo toolInfo) {
				manager.getBackgroundModel().submit(flowProvider.newRunToolFlow(toolInfo));
			}
			
		};


		/**
		 * 新的实例。
		 */
		public Manager() {
			coreConfigModel.addAll(Arrays.asList(CoreConfig.values()));
			modalConfigModel.addAll(Arrays.asList(ModalConfig.values()));
			loggerMutilangModel.setDefaultMutilangInfo(Constants.getDefaultLoggerMutilangInfo());
			loggerMutilangModel.setDefaultValue(Constants.getDefaultMissingString());
			loggerMutilangModel.setSupportedKeys(Constants.getLoggerMutilangSupportedKeys());
			labelMutilangModel.setDefaultMutilangInfo(Constants.getDefaultLabelMutilangInfo());
			labelMutilangModel.setDefaultValue(Constants.getDefaultMissingString());
			labelMutilangModel.setSupportedKeys(Constants.getLabelMutilangSupportedKeys());
			
			loggerModel.addObverser(loggerObverser);
			loggerMutilangModel.addObverser(loggerMutilangObverser);
			coreConfigModel.addObverser(coreConfigObverser);
			
			try {
				loggerMutilangModel.update();
				labelMutilangModel.update();
			} catch (ProcessException e) {
				//未初始化之前，多语言模型使用的是固化在程序中的数据，不可能出现异常。
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 释放资源。
		 */
		public void dispose(){
			loggerModel.removeObverser(loggerObverser);
			loggerMutilangModel.removeObverser(loggerMutilangObverser);
			coreConfigModel.removeObverser(coreConfigObverser);
		}
		
		/**
		 * @return the resourceModel
		 */
		public ResourceModel getResourceModel() {
			return resourceModel;
		}

		/**
		 * @return the loggerModel
		 */
		public LoggerModel getLoggerModel() {
			return loggerModel;
		}

		/**
		 * @return the coreConfigModel
		 */
		public CoreConfigModel getCoreConfigModel() {
			return coreConfigModel;
		}

		/**
		 * @return the modalConfigModel
		 */
		public ModalConfigModel getModalConfigModel() {
			return modalConfigModel;
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
		 * @return the toolInfoModel
		 */
		public ToolInfoModel getToolInfoModel() {
			return toolInfoModel;
		}

		/**
		 * @return the backgroundModel
		 */
		public BackgroundModel getBackgroundModel() {
			return backgroundModel;
		}
		
		/**
		 * @return the libraryModel
		 */
		public LibraryModel getLibraryModel() {
			return libraryModel;
		}
		
		/**
		 * @return the blockModel
		 */
		public BlockModel getBlockModel(){
			return blockModel;
		}

		/**
		 * @return the toolRuntimeModel
		 */
		public ToolRuntimeModel getToolRuntimeModel() {
			return toolRuntimeModel;
		}

		/**
		 * @return the toolHistoryModel
		 */
		public ToolHistoryModel getToolHistoryModel() {
			return toolHistoryModel;
		}


		/**
		 * @return the finishedFlowTaker
		 */
		public FinishedFlowTaker getFinishedFlowTaker() {
			return finishedFlowTaker;
		}

		/**
		 * @return the exitedRunningToolTaker
		 */
		public ExitedRunningToolTaker getExitedRunningToolTaker() {
			return exitedRunningToolTaker;
		}


		/**
		 * @return the splashScreenController
		 */
		public SplashScreenController getSplashScreenController() {
			return splashScreenController;
		}

		/**
		 * @return the mainFrameController
		 */
		public MainFrameController getMainFrameController() {
			return mainFrameController;
		}
		
	}
	
	private final class FlowProvider{

		/**
		 * 获取一个新的程序初始化时使用的过程。
		 * @return 新的程序初始化时使用的后台过程。
		 */
		public Flow newInitializeFlow() {
			return new InitializeFlow();
		}
		
		/**
		 * 获取一个新的读取库的过程。
		 * @return 新的读取库的过程。
		 */
		public Flow newLoadLibFlow() {
			return new LoadLibFlow();
		}
		
		/**
		 * 获取一个新的检查库的过程。
		 * @return 新的检查库的过程。
		 */
		public Flow newCheckLibFlow() {
			return new CheckLibFlow();
		}

		/**
		 * 获取一个新的读取工具信息的过程。
		 * @return 新的读取工具信息的过程。
		 */
		public Flow newLoadToolInfoFlow() {
			return new LoadToolInfoFlow();
		}

		/**
		 *  获取一个新的运行工具的过程。
		 * @param toolInfo 指定的工具。
		 * @return 新的运行工具的过程。
		 */
		public Flow newRunToolFlow(ToolInfo toolInfo) {
			return new RunToolFlow(toolInfo);
		}

		/**
		 * 获取一个新的关闭窗口的过程。
		 * @return 新的关闭窗口的过程。
		 */
		public Flow newClosingFlow() {
			return new WindowClosingFlow();
		}


		/**
		 * 内部抽象过程。
		 * <p> 定义常用的内部用方法。
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractInnerFlow extends AbstractFlow{
			
			private final String blockKey;
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键, 不能为 <code>null</code>。
			 * @param initMessage 初始的信息，不能为 <code>null</code>。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage) {
				this(blockKey, initMessage, 0, 0, false, false);
			}
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键，不能为 <code>null</code>。
			 * @param initMessage 初始的信息，不能为 <code>null</code>。
			 * @param progress 当前进度。
			 * @param totleProgress 总进度。
			 * @param determinateFlag 是否为进度已知的流程。
			 * @param cancelableFlag 是否能够被取消。
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
				super(progress, totleProgress, determinateFlag, cancelableFlag);
				Objects.requireNonNull(blockKey, "入口参数 blockKey 不能为 null。");
				Objects.requireNonNull(initMessage, "入口参数 initMessage 不能为 null。");
				this.blockKey = blockKey.getName();
				setMessage(initMessage);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.AbstractFlow#process()
			 */
			@Override
			protected void process() {
				manager.getBlockModel().getBlock().block(blockKey);
				try{
					subProcess();
				}finally {
					manager.getBlockModel().getBlock().unblock(blockKey);
				}
			}
			
			/**
			 * 子处理方法。
			 * <p> 该方法是主要的处理方法。
			 * <p> 该方法不允许抛出任何异常。
			 */
			protected abstract void subProcess();
			
			/**
			 * 返回指定的记录器键所对应的字符串。
			 * @param loggerStringKey 指定的记录器键。
			 * @return 记录器键对应的字符串。
			 */
			protected String getLabel(LoggerStringKey loggerStringKey){
				return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
			}

			/**
			 * 返回指定记录器键的 format 字符串。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args 指定的 format 参数。
			 * @return 指定的记录器键的 format 字符串。
			 */
			protected String formatLabel(LoggerStringKey loggerStringKey, Object... args){
				return String.format(manager.getLoggerMutilangModel().getMutilang().getString(
						loggerStringKey.getName()),args);
			}
			
			/**
			 * 向记录器中输入一条INFO类信息。
			 * @param loggerStringKey 指定的记录器键。
			 */
			protected void info(LoggerStringKey loggerStringKey){
				manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
			}

			/**
			 * 向记录器中format一条INFO类信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args format参数。
			 */
			protected void formatInfo(LoggerStringKey loggerStringKey, Object... args){
				manager.getLoggerModel().getLogger().info(formatLabel(loggerStringKey, args));	
			}

			/**
			 * 向记录器中输入一条WARN类信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param throwable 指定的可抛出对象。
			 */
			protected void warn(LoggerStringKey loggerStringKey, Throwable throwable){
				manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), throwable);
			}

			/**
			 * 获取指定键对应的资源。
			 * @param resourceKey 指定的键。
			 * @return 指定的键对应的资源。
			 */
			protected Resource getResource(ResourceKey resourceKey){
				return manager.getResourceModel().get(resourceKey.getName());
			}
			
			/**
			 * 设置信息为指定的信息。
			 * @param loggerStringKey 指定的记录器键。
			 */
			protected void message(LoggerStringKey loggerStringKey){
				setMessage(getLabel(loggerStringKey));
			}
			
			/**
			 * 格式化设置信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args format 参数。
			 */
			protected void formatMessage(LoggerStringKey loggerStringKey, Object... args){
				setMessage(formatLabel(loggerStringKey, args));	
			}
			
		}
		
		private final class InitializeFlow extends AbstractInnerFlow{
			
			public InitializeFlow() {
				super(BlockKey.INITIALIZE, manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_3.getName()));
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.InnerFlowProvider.InnerAbstractFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
				try{
					if(getState() != RuntimeState.NOT_START){
						throw new IllegalStateException("程序已经启动或已经结束");
					}
					
					//更新模型，此时的多语言模型和记录器模型被更新为默认值。
					try{
						manager.getLoggerModel().update();
						manager.getLabelMutilangModel().update();
						manager.getLoggerMutilangModel().update();
						manager.getBlockModel().update();
					}catch (ProcessException ignore) {
						//此时均为默认值，不可能抛出异常。
					}
					
					//加载程序的资源模型
					info(LoggerStringKey.ToolPlatform_FlowProvider_3);
					message(LoggerStringKey.ToolPlatform_FlowProvider_3);
					XmlResourceLoader resourceLoader = null;
					try{
						resourceLoader = new XmlResourceLoader(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/paths.xml"));
						resourceLoader.load(manager.getResourceModel());
					}finally{
						if(Objects.nonNull(resourceLoader)){
							resourceLoader.close();
						}
					}
					
					//加载程序中的记录器模型。
					info(LoggerStringKey.ToolPlatform_FlowProvider_5);
					message(LoggerStringKey.ToolPlatform_FlowProvider_5);
					if(manager.getLoggerModel().getLoggerContext() != null){
						manager.getLoggerModel().getLoggerContext().stop();
					}
					XmlLoggerLoader loggerLoader = null;
					try{
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.LOGGER_SETTING).reset();
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}finally {
						if(Objects.nonNull(loggerLoader)){
							loggerLoader.close();
						}
					}
					try{
						manager.getLoggerModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_Logger_1, e);
					}
					
					//加载记录器多语言配置。
					info(LoggerStringKey.ToolPlatform_FlowProvider_7);
					message(LoggerStringKey.ToolPlatform_FlowProvider_7);
					XmlMutilangLoader loggerMutilangLoader = null;
					try{
						loggerMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LOGGER_SETTING).openInputStream());
						loggerMutilangLoader.load(manager.getLoggerMutilangModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.MUTILANG_LOGGER_SETTING).reset();
						loggerMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LOGGER_SETTING).openInputStream());
						loggerMutilangLoader.load(manager.getLoggerMutilangModel());
					}finally {
						if(Objects.nonNull(loggerMutilangLoader)){
							loggerMutilangLoader.close();
						}
					}
					try{
						manager.getLoggerMutilangModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_LoggerMutilang_1, e);
					}
					
					//加载程序的核心配置。
					info(LoggerStringKey.ToolPlatform_FlowProvider_6);
					message(LoggerStringKey.ToolPlatform_FlowProvider_6);
					PropConfigLoader coreConfigLoader = null;
					try{
						coreConfigLoader = new PropConfigLoader(getResource(ResourceKey.CONFIGURATION_CORE).openInputStream());
						coreConfigLoader.load(manager.getCoreConfigModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.CONFIGURATION_CORE).reset();
						coreConfigLoader = new PropConfigLoader(getResource(ResourceKey.CONFIGURATION_CORE).openInputStream());
						coreConfigLoader.load(manager.getCoreConfigModel());
					}finally {
						if(Objects.nonNull(coreConfigLoader)){
							coreConfigLoader.close();
						}
					}
					
					//如果需要显示启动窗口，则显示启动窗口
					boolean splashFlag = manager.getCoreConfigModel().isShowSplashScreen();
					TimeMeasurer tm = new TimeMeasurer();
					if(splashFlag){
						info(LoggerStringKey.ToolPlatform_FlowProvider_8);
						message(LoggerStringKey.ToolPlatform_FlowProvider_8);
						splash(LoggerStringKey.ToolPlatform_FlowProvider_8);
						tm.start();
					}
					
					//加载阻挡模型字典
					info(LoggerStringKey.ToolPlatform_FlowProvider_13);
					message(LoggerStringKey.ToolPlatform_FlowProvider_13);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_13);
					}
					XmlBlockLoader blockLoader = null;
					try{
						blockLoader = new XmlBlockLoader(ToolPlatformUtil.newBlockDictionary());
						blockLoader.load(manager.getBlockModel());
					}finally {
						if(Objects.nonNull(blockLoader)){
							blockLoader.close();
						}
					}
					
					//加载标签多语言配置。
					info(LoggerStringKey.ToolPlatform_FlowProvider_9);
					message(LoggerStringKey.ToolPlatform_FlowProvider_9);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_9);
					}
					XmlMutilangLoader labelMutilangLoader = null;
					try{
						labelMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LABEL_SETTING).openInputStream());
						labelMutilangLoader.load(manager.getLabelMutilangModel());
					}catch(IOException e){
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.MUTILANG_LABEL_SETTING).reset();
						labelMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LABEL_SETTING).openInputStream());
						labelMutilangLoader.load(manager.getLabelMutilangModel());
					}finally{
						if(Objects.nonNull(labelMutilangLoader)){
							labelMutilangLoader.close();
						}
					}
					try{
						manager.getLabelMutilangModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_LabelMutilang_1, e);
					}
					
					//加载模态模型
					info(LoggerStringKey.ToolPlatform_FlowProvider_10);
					message(LoggerStringKey.ToolPlatform_FlowProvider_10);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_10);
					}
					PropConfigLoader modalConfigLoader = null;
					try{
						modalConfigLoader = new PropConfigLoader(getResource(ResourceKey.CONFIGURATION_MODAL).openInputStream());
						modalConfigLoader.load(manager.getModalConfigModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.CONFIGURATION_MODAL).reset();
						modalConfigLoader = new PropConfigLoader(getResource(ResourceKey.CONFIGURATION_MODAL).openInputStream());
						modalConfigLoader.load(manager.getModalConfigModel());
					}finally{
						if(Objects.nonNull(modalConfigLoader)){
							modalConfigLoader.close();
						}
					}
					
					//唤起主界面
					info(LoggerStringKey.ToolPlatform_FlowProvider_11);
					message(LoggerStringKey.ToolPlatform_FlowProvider_11);
					if(splashFlag){
						splash(LoggerStringKey.ToolPlatform_FlowProvider_11);
					}
					ToolPlatformUtil.invokeAndWaitInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getMainFrameController().newInstance();
						}
					});
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getMainFrameController().setLastNormalHeight(manager.getModalConfigModel().getMainFrameStartupHeight());
							manager.getMainFrameController().setLastNormalWidth(manager.getModalConfigModel().getMainFrameStartupWidth());
							manager.getMainFrameController().setExtendedState(manager.getModalConfigModel().getMainFrameStartupExtendedState());
							manager.getMainFrameController().setSouthHeight(manager.getModalConfigModel().getMainFrameStartupSouthHeight());
							manager.getMainFrameController().setLocationRelativeTo(null);
						}
					});
					
					//重新加载LoggerModel;
					info(LoggerStringKey.ToolPlatform_FlowProvider_5);
					message(LoggerStringKey.ToolPlatform_FlowProvider_5);
					if(manager.getLoggerModel().getLoggerContext() != null){
						manager.getLoggerModel().getLoggerContext().stop();
					}
					loggerLoader = null;
					try{
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.LOGGER_SETTING).reset();
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}finally {
						if(Objects.nonNull(loggerLoader)){
							loggerLoader.close();
						}
					}
					try{
						manager.getLoggerModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_Logger_1, e);
					}
					
					//加载库模型
					info(LoggerStringKey.ToolPlatform_FlowProvider_12);
					message(LoggerStringKey.ToolPlatform_FlowProvider_12);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_12);
					}
					XmlLibraryLoader libraryLoader = null;
					try{
						libraryLoader = new XmlLibraryLoader(getResource(ResourceKey.TOOL_LIB).openInputStream());
						libraryLoader.load(manager.getLibraryModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_LIB).reset();
						libraryLoader = new XmlLibraryLoader(getResource(ResourceKey.TOOL_LIB).openInputStream());
						libraryLoader.load(manager.getLibraryModel());
					}finally{
						if(Objects.nonNull(libraryLoader)){
							libraryLoader.close();
						}
					}
					
					//加载工具信息模型
					info(LoggerStringKey.ToolPlatform_FlowProvider_22);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_22);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_22);
					XmlUnsafeToolInfoLoader toolInfoLoader = null;
					Set<UnsafeToolInfo> unsafeToolInfos = new LinkedHashSet<>();
					try{
						toolInfoLoader = new XmlUnsafeToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(unsafeToolInfos);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_INFO).reset();
						toolInfoLoader = new XmlUnsafeToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(unsafeToolInfos);
					}finally{
						if(Objects.nonNull(toolInfoLoader)){
							toolInfoLoader.close();
						}
					}
					
					next:
					for(UnsafeToolInfo unsafeToolInfo : unsafeToolInfos){
						String name;
						Image image;
						Version version;
						Map<Locale, String> descriptions;
						String[] authors;
						String toolClass;
						String infoClass;
						String toolFile;
						String[] toolLibs;
						try{
							name = unsafeToolInfo.getName();
							image = unsafeToolInfo.getImage();
							version = unsafeToolInfo.getVersion();
							descriptions = unsafeToolInfo.getDescriptionMap();
							authors = unsafeToolInfo.getAuthors();
							toolClass = unsafeToolInfo.getToolClass();
							infoClass = unsafeToolInfo.getInfoClass();
							toolFile = unsafeToolInfo.getToolFile();
							toolLibs = unsafeToolInfo.getToolLibs();
						}catch (ProcessException e) {
							warn(LoggerStringKey.ToolPlatform_FlowProvider_26, e);
							continue next;
						}

						ToolInfo toolInfo = new DefaultToolInfo(name, image, version, descriptions, authors, toolClass, infoClass, toolFile, toolLibs);
						manager.getToolInfoModel().add(toolInfo);
					}
					
					//TODO 加载工具历史
					info(LoggerStringKey.ToolPlatform_FlowProvider_34);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_34);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_34);
					XmlUnsafeToolHistoryLoader toolHistoryLoader = null;
					List<UnsafeToolHistory> unsafeToolHistories = new ArrayList<>();
					try{
						toolHistoryLoader = new XmlUnsafeToolHistoryLoader(getResource(ResourceKey.TOOL_HISTORY).openInputStream());
						toolHistoryLoader.load(unsafeToolHistories);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_HISTORY).reset();
						toolHistoryLoader = new XmlUnsafeToolHistoryLoader(getResource(ResourceKey.TOOL_HISTORY).openInputStream());
						toolHistoryLoader.load(unsafeToolHistories);
					}finally{
						if(Objects.nonNull(toolHistoryLoader)){
							toolHistoryLoader.close();
						}
					}
					
					//注册退出束钩子
					info(LoggerStringKey.ToolPlatform_FlowProvider_33);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_33);
					}
					
					putShutdownHook("destroy_runningtool", shutdownHookProvider.newDestroyRunningToolRunnable());
					putShutdownHook("remove_reference", shutdownHookProvider.newRemoveReferenceRunnable());
					
					//等待启动窗口到达指定的时间后，令其消失。
					if(splashFlag){
						tm.stop();
						long time = tm.getTimeMs();
						int duration = manager.getCoreConfigModel().getStartupSplashDuration();
						if(time < duration){
							Thread.sleep(duration - time);
						}
						ToolPlatformUtil.invokeInEventQueue(new Runnable() {
							@Override
							public void run() {
								manager.getSplashScreenController().dispose();
							}
						});
					}
					
					
					
					//显示启动界面
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getMainFrameController().setVisible(true);
						}
					});
					
					//设置成功消息
					message(LoggerStringKey.ToolPlatform_FlowProvider_1);
					setState(RuntimeState.RUNNING);
					
				}catch (Exception e) {
					//将启动窗口关闭。
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getSplashScreenController().dispose();
						}
					});
					setThrowable(e);
					message(LoggerStringKey.ToolPlatform_FlowProvider_2);
				}
			}

			private void splash(LoggerStringKey loggerStringKey){
				ToolPlatformUtil.invokeInEventQueue(new Runnable() {
					@Override
					public void run() {
						manager.getSplashScreenController().show();
						manager.getSplashScreenController().setMessage(manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName()));
					}
				});
			}
			
		}

		private final class LoadLibFlow extends AbstractInnerFlow{
			
			public LoadLibFlow() {
				super(BlockKey.LOAD_LIB, manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_14.getName()));
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.InnerFlowProvider.InnerAbstractFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					//读取库文件
					info(LoggerStringKey.ToolPlatform_FlowProvider_14);
					message(LoggerStringKey.ToolPlatform_FlowProvider_14);
					XmlLibraryLoader libraryLoader = null;
					LibraryModel tempLibraryModel = new DefaultLibraryModel();
					try{
						libraryLoader = new XmlLibraryLoader(getResource(ResourceKey.TOOL_LIB).openInputStream());
						libraryLoader.load(tempLibraryModel);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_LIB).reset();
						libraryLoader = new XmlLibraryLoader(getResource(ResourceKey.TOOL_LIB).openInputStream());
						libraryLoader.load(tempLibraryModel);
					}finally{
						if(Objects.nonNull(libraryLoader)){
							libraryLoader.close();
						}
					}
					
					for(Library library : tempLibraryModel){
						manager.getLibraryModel().add(library);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_15);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_16);
				}
			}
			
		}
		
		private final class CheckLibFlow extends AbstractInnerFlow{

			public CheckLibFlow() {
				super(BlockKey.CHECK_LIB,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_17.getName()));
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.InnerFlowProvider.InnerAbstractFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					//开始构造条件
					info(LoggerStringKey.ToolPlatform_FlowProvider_17);
					message(LoggerStringKey.ToolPlatform_FlowProvider_17);
					
					Set<Library> libraries = new HashSet<>();
					ReadWriteLock modelLock = manager.getLibraryModel().getLock();
					
					modelLock.readLock().lock();
					try{
						for(Library library : manager.libraryModel){
							libraries.add(library);
						}
					}finally {
						modelLock.readLock().unlock();
					}
					
					
					InputStream in = null;
					LibraryKeyChecker libraryChecker = null;
					try{
						in = getResource(ResourceKey.TOOL_LIB).openInputStream();
						libraryChecker = new LibraryKeyChecker(in);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						in = getResource(ResourceKey.TOOL_LIB).openInputStream();
						libraryChecker = new LibraryKeyChecker(in);
					}finally{
						if(Objects.nonNull(in)){
							in.close();
						}
					}
					
					if(isCancel()){
						message(LoggerStringKey.ToolPlatform_FlowProvider_21);
						return;
					}
					
					//设置过程是可以确定进度的，并确定进度
					setTotleProgress(manager.getLibraryModel().size());
					setCancelable(true);
					setDeterminate(true);
					
					//开始循环检查
					for(Library key : libraries){
						if(isCancel()) return;
						if(libraryChecker.nonValid(key)){
							formatInfo(LoggerStringKey.ToolPlatform_FlowProvider_18, key);
							formatMessage(LoggerStringKey.ToolPlatform_FlowProvider_18, key);
							manager.getLibraryModel().remove(key);
						}
						setProgress(getProgress() + 1);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_19);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_20);
				}
			}
			
		}
		private final class LoadToolInfoFlow extends AbstractInnerFlow{

			public LoadToolInfoFlow() {
				super(BlockKey.RUN_TOOL,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_22.getName()));
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.InnerFlowProvider.InnerAbstractFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					//加载工具信息模型
					info(LoggerStringKey.ToolPlatform_FlowProvider_22);
					message(LoggerStringKey.ToolPlatform_FlowProvider_22);
					
					ToolInfoModel tempToolInfoModel = new DefaultToolInfoModel();
					XmlUnsafeToolInfoLoader toolInfoLoader = null;
					Set<UnsafeToolInfo> unsafeToolInfos = new HashSet<>();
					try{
						toolInfoLoader = new XmlUnsafeToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(unsafeToolInfos);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_INFO).reset();
						toolInfoLoader = new XmlUnsafeToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(unsafeToolInfos);
					}finally{
						if(Objects.nonNull(toolInfoLoader)){
							toolInfoLoader.close();
						}
					}
					
					//解析工具信息
					info(LoggerStringKey.ToolPlatform_FlowProvider_23);
					message(LoggerStringKey.ToolPlatform_FlowProvider_23);
					setTotleProgress(tempToolInfoModel.size());
					setDeterminate(true);
					
					manager.toolInfoModel.clear();
					next:
					for(UnsafeToolInfo unsafeToolInfo : unsafeToolInfos){
						
						String name;
						Image image;
						Version version;
						Map<Locale, String> descriptionMap;
						String[] authors;
						String toolClass;
						String infoClass;
						String toolFile;
						String[] toolLibs;
						try{
							name = unsafeToolInfo.getName();
							image = unsafeToolInfo.getImage();
							version = unsafeToolInfo.getVersion();
							descriptionMap = unsafeToolInfo.getDescriptionMap();
							authors = unsafeToolInfo.getAuthors();
							toolClass = unsafeToolInfo.getToolClass();
							infoClass = unsafeToolInfo.getInfoClass();
							toolFile = unsafeToolInfo.getToolFile();
							toolLibs = unsafeToolInfo.getToolLibs();
						}catch (ProcessException e) {
							warn(LoggerStringKey.ToolPlatform_FlowProvider_26, e);
							continue next;
						}finally {
							setProgress(getProgress() + 1);
						}

						ToolInfo toolInfo = new DefaultToolInfo(name, image, version, descriptionMap, authors, toolClass, infoClass, toolFile, toolLibs);
						manager.getToolInfoModel().add(toolInfo);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_24);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_25);
				}
			}
			
		}
		
		private final class RunToolFlow extends AbstractInnerFlow{

			private final ToolInfo toolInfo;
			
			public RunToolFlow(ToolInfo toolInfo) {
				super(BlockKey.LOAD_TOOLINFO,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_27.getName()));
				Objects.requireNonNull(toolInfo, "入口参数 toolInfo 不能为 null。");
				this.toolInfo = toolInfo;
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.InnerFlowProvider.InnerAbstractFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.ToolPlatform_FlowProvider_27);
					
					XmlPathGetter pathGetter = null;
					InputStream libCfg = null;
					InputStream dataCfg = null;

					try{
						try{
							libCfg =getResource(ResourceKey.TOOL_LIB).openInputStream();
						}catch (IOException e) {
							warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
							getResource(ResourceKey.TOOL_LIB).reset();
							libCfg =getResource(ResourceKey.TOOL_LIB).openInputStream();
						}
						
						try{
							dataCfg =getResource(ResourceKey.TOOL_DATA).openInputStream();
						}catch (IOException e) {
							warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
							getResource(ResourceKey.TOOL_DATA).reset();
							dataCfg =getResource(ResourceKey.TOOL_DATA).openInputStream();
						}
						
						pathGetter = new XmlPathGetter(libCfg, dataCfg);
						
					}finally {
						if(Objects.nonNull(libCfg)){
							libCfg.close();
						}
						if(Objects.nonNull(dataCfg)){
							dataCfg.close();
						}
					}

					String[] libNames = toolInfo.getToolLibs();
					String[] libPaths = new String[libNames.length];
					for(int i = 0 ; i < libPaths.length ; i ++){
						libPaths[i] = pathGetter.getLibraryPath(libNames[i]);
					}
					
					Image image = toolInfo.getImage();
					String jarPath = pathGetter.getToolFilePath(toolInfo.getToolFile());
					String entryClass = toolInfo.getToolClass();
					File directory = pathGetter.getToolDirectory(toolInfo);
					
					if(! directory.exists()) directory.mkdirs();
					
					RunningTool runningTool = new DefaultRunningTool(toolInfo.getName(), image, libPaths, jarPath, entryClass, directory);
					
					manager.getToolRuntimeModel().getLock().writeLock().lock();
					try{
						if(manager.getToolRuntimeModel().isAddRejected()){
							info(LoggerStringKey.ToolPlatform_FlowProvider_32);
							message(LoggerStringKey.ToolPlatform_FlowProvider_32);
							return;
						}else{
							manager.getToolRuntimeModel().add(runningTool);
						}
					}finally {
						manager.getToolRuntimeModel().getLock().writeLock().unlock();
					}
					try{
						ToolPlatformUtil.invokeAndWaitInEventQueue(new Runnable() {
							@Override
							public void run() {
								manager.getMainFrameController().assignStream(runningTool);
							}
						});
					}catch (InterruptedException ignore) {
						//中断也要按照基本法。
					}
					runningTool.lockStream();
					message(LoggerStringKey.ToolPlatform_FlowProvider_28);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_29);
				}
			}
			
		}
		
		private final class WindowClosingFlow extends AbstractInnerFlow{

			public WindowClosingFlow() {
				super(BlockKey.CLOSING,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_30.getName()));
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.control.ToolPlatform.InnerFlowProvider.InnerAbstractFlow#subProcess()
			 */
			@Override
			protected void subProcess() {
				try{
					if(getState() != RuntimeState.RUNNING){
						throw new IllegalStateException("程序还未启动或已经结束");
					}
					
					info(LoggerStringKey.ToolPlatform_FlowProvider_30);
					
					manager.getToolRuntimeModel().setAddRejected(true);
					
					if(manager.getToolRuntimeModel().hasNotExited()){
						//TODO 之后要通过对话框进行确认，而不是在控制台中输出这样一条信息。
						info(LoggerStringKey.ToolPlatform_FlowProvider_31);
						manager.getToolRuntimeModel().setAddRejected(false);
						message(LoggerStringKey.ToolPlatform_FlowProvider_31);
						return;
					}
					
					exit();
					message(LoggerStringKey.ToolPlatform_FlowProvider_28);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_29);
				}
			}
			
		}
	
	}
	







	private final class Exitor implements Runnable{
	
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			//解除注册工具钩子
			info(LoggerStringKey.ToolPlatform_Exitor_11);
			removeShutdownHook("destroy_runningtool");
			
			//停止后台模型和工具运行时模型。
			info(LoggerStringKey.ToolPlatform_Exitor_3);
			manager.getBackgroundModel().shutdown();
			manager.getToolRuntimeModel().shutdown();
			manager.getFinishedFlowTaker().shutdown();
			manager.getExitedRunningToolTaker().shutdown();
			
			//等待50毫秒，此时后台模型和工具运行时模型中的执行器应该会自然终结。
			try {
				Thread.sleep(50);
			} catch (InterruptedException ignore) {
				//中断也要按照基本法。
			}
			
			boolean waitFlag = false;
			if(! manager.getBackgroundModel().getExecutorService().isTerminated()){
				warn(LoggerStringKey.ToolPlatform_Exitor_1);
				waitFlag = true;
			}
			if(! manager.getToolRuntimeModel().getExecutorService().isTerminated()){
				warn(LoggerStringKey.ToolPlatform_Exitor_2);
				waitFlag = true;
			}
			
			if(waitFlag){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ignore) {
					//中断也要按照基本法。
				}
				
				if(! manager.getBackgroundModel().getExecutorService().isTerminated()){
					warn(LoggerStringKey.ToolPlatform_Exitor_4);
				}
				if(! manager.getToolRuntimeModel().getExecutorService().isTerminated()){
					warn(LoggerStringKey.ToolPlatform_Exitor_5);
				}
				
			}
			
			//保存模态配置
			info(LoggerStringKey.ToolPlatform_Exitor_8);
			ModalConfigModel modalConfigModel = manager.getModalConfigModel();
			MainFrameController mainFrameController = manager.getMainFrameController();
			modalConfigModel.setCurrentValue(ModalConfig.STARTUP_MAINFRAME_EXTENDEDSTATE.getConfigKey(), mainFrameController.getExtendedState() + "");
			modalConfigModel.setCurrentValue(ModalConfig.STARTUP_MAINFRAME_HEIGHT.getConfigKey(), mainFrameController.getLastNormalHeight() + "");
			modalConfigModel.setCurrentValue(ModalConfig.STARTUP_MAINFRAME_WIDTH.getConfigKey(), mainFrameController.getLastNormalWidth() + "");
			modalConfigModel.setCurrentValue(ModalConfig.STARTUP_MAINFRAME_SOUTHHEIGHT.getConfigKey(), mainFrameController.getSouthHeight() + "");
	
			PropConfigSaver modalConfigSaver = null;
			try{
				try{
					modalConfigSaver = new PropConfigSaver(getResource(ResourceKey.CONFIGURATION_MODAL).openOutputStream());
					modalConfigSaver.save(manager.getModalConfigModel());
				}catch (IOException e) {
					warn(LoggerStringKey.ToolPlatform_Exitor_9, e);
					getResource(ResourceKey.CONFIGURATION_MODAL).reset();
					modalConfigSaver = new PropConfigSaver(getResource(ResourceKey.CONFIGURATION_MODAL).openOutputStream());
					modalConfigSaver.save(manager.getModalConfigModel());
				}finally{
					if(Objects.nonNull(modalConfigSaver)){
						modalConfigSaver.close();
					}
				}
				
				//
			}catch (Exception e) {
				warn(LoggerStringKey.ToolPlatform_Exitor_10, e);
			}
			
			//TODO 保存核心配置
			
			//释放界面
			info(LoggerStringKey.ToolPlatform_Exitor_6);
			if(manager.getLoggerModel().getLoggerContext() != null){
				manager.getLoggerModel().getLoggerContext().stop();
			}
			try {
				ToolPlatformUtil.invokeAndWaitInEventQueue(new Runnable() {
					@Override
					public void run() {
						manager.getMainFrameController().dispose();
					}
				});
			} catch (InvocationTargetException ignore) {
			} catch (InterruptedException ignore) {
				//中断也要按照基本法。
			}
			
			//重新加载LoggerModel;
			XmlLoggerLoader loggerLoader = null;
			boolean logInvalid = false;
			try{
				try{
					loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
					loggerLoader.load(manager.getLoggerModel());
				}catch (IOException e) {
					getResource(ResourceKey.LOGGER_SETTING).reset();
					loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
					loggerLoader.load(manager.getLoggerModel());
				}finally {
					if(Objects.nonNull(loggerLoader)){
						loggerLoader.close();
					}
				}
				manager.getLoggerModel().update();
			}catch (LoadFailedException | IOException | ProcessException e) {
				e.printStackTrace();
				logInvalid = true;
			}
	
			//释放模型
			if(! logInvalid){
				info(LoggerStringKey.ToolPlatform_Exitor_7);
			}
			manager.dispose();
			
			//解除对象的引用并移除钩子
			INSTANCES.remove(ToolPlatform.this);
			if(! logInvalid){
				info(LoggerStringKey.ToolPlatform_Exitor_12);
			}
			removeShutdownHook("remove_reference");

		}
		
	
		private void info(LoggerStringKey loggerStringKey){
			manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
		}
	
		private void warn(LoggerStringKey loggerStringKey){
			manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey));
		}
		
		private void warn(LoggerStringKey loggerStringKey, Throwable e){
			manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), e);
		}
	
	
		private String getLabel(LoggerStringKey loggerStringKey){
			return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
		}
	
		/**
		 * 获取指定键对应的资源。
		 * @param resourceKey 指定的键。
		 * @return 指定的键对应的资源。
		 */
		private Resource getResource(ResourceKey resourceKey){
			return manager.getResourceModel().get(resourceKey.getName());
		}
		
	}







	private final class ShutdownHookProvider{
		
		/**
		 * 返回一个新的销毁运行工具可运行对象。
		 * @return 新的销毁工具可运行对象。
		 */
		public Runnable newDestroyRunningToolRunnable(){
			return new DestroyRunningToolRunnable();
		}
		
		/**
		 * 返回一个新的移除引用可运行对象。
		 * @return 新的移除引用可运行对象。
		 */
		public Runnable newRemoveReferenceRunnable(){
			return new RemoveReferenceRunnable();
		}
		
		private abstract class AbstractInnerRunnable implements Runnable{

			/**
			 * 返回指定的记录器键所对应的字符串。
			 * @param loggerStringKey 指定的记录器键。
			 * @return 记录器键对应的字符串。
			 */
			protected String getLabel(LoggerStringKey loggerStringKey){
				return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
			}

			/**
			 * 返回指定记录器键的 format 字符串。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args 指定的 format 参数。
			 * @return 指定的记录器键的 format 字符串。
			 */
			protected String formatLabel(LoggerStringKey loggerStringKey, Object... args){
				return String.format(manager.getLoggerMutilangModel().getMutilang().getString(
						loggerStringKey.getName()),args);
			}

			/**
			 * 向记录器中输入一条INFO类信息。
			 * @param loggerStringKey 指定的记录器键。
			 */
			protected void info(LoggerStringKey loggerStringKey){
				manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
			}

			/**
			 * 向记录器中format一条INFO类信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param args format参数。
			 */
			protected void formatInfo(LoggerStringKey loggerStringKey, Object... args){
				manager.getLoggerModel().getLogger().info(formatLabel(loggerStringKey, args));	
			}

			/**
			 * 向记录器中输入一条WARN类信息。
			 * @param loggerStringKey 指定的记录器键。
			 * @param throwable 指定的可抛出对象。
			 */
			protected void warn(LoggerStringKey loggerStringKey, Throwable throwable){
				manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), throwable);
			}
			
		}

		private final class DestroyRunningToolRunnable extends AbstractInnerRunnable{
		
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				CT.trace("1233");
				info(LoggerStringKey.ToolPlatform_ShutdownHookProvider_1);
				
				ReadWriteLock lock = manager.getToolRuntimeModel().getLock();
				List<RunningTool> runningTools = new ArrayList<>();
				lock.readLock().lock();
				try{
					for(RunningTool runningTool : manager.getToolRuntimeModel()){
						runningTools.add(runningTool);
					}
				}finally {
					lock.readLock().unlock();
				}
				for(RunningTool runningTool : runningTools){
					if(! runningTool.getRuntimeState().equals(RuntimeState.ENDED)){
						runningTool.destroy();
					}
				}
			}
			
		}

		private class RemoveReferenceRunnable extends AbstractInnerRunnable {
		
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				info(LoggerStringKey.ToolPlatform_ShutdownHookProvider_1);

				INSTANCES.remove(ToolPlatform.this);
			}
		
		}
		
	}
	
	
}
