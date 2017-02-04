package com.dwarfeng.tp.core.control;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigLoader;
import com.dwarfeng.tp.core.control.act.FlowProvider;
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
import com.dwarfeng.tp.core.model.cm.DefaultToolInfoModel;
import com.dwarfeng.tp.core.model.cm.DefaultToolRuntimeModel;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.cm.LoggerModel;
import com.dwarfeng.tp.core.model.cm.ModalConfigModel;
import com.dwarfeng.tp.core.model.cm.MutilangModel;
import com.dwarfeng.tp.core.model.cm.ResourceModel;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.cm.ToolRuntimeModel;
import com.dwarfeng.tp.core.model.io.XmlBlockLoader;
import com.dwarfeng.tp.core.model.io.XmlLibraryLoader;
import com.dwarfeng.tp.core.model.io.XmlLoggerLoader;
import com.dwarfeng.tp.core.model.io.XmlMutilangLoader;
import com.dwarfeng.tp.core.model.io.XmlPathGetter;
import com.dwarfeng.tp.core.model.io.XmlResourceLoader;
import com.dwarfeng.tp.core.model.io.XmlToolInfoLoader;
import com.dwarfeng.tp.core.model.obv.LoggerAdapter;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.obv.MutilangAdapter;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.AbstractFlow;
import com.dwarfeng.tp.core.model.struct.DefaultFinishedFlowTaker;
import com.dwarfeng.tp.core.model.struct.DefaultRunningTool;
import com.dwarfeng.tp.core.model.struct.FinishedFlowTaker;
import com.dwarfeng.tp.core.model.struct.Flow;
import com.dwarfeng.tp.core.model.struct.LibraryKeyChecker;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.model.struct.SafeToolInfo;
import com.dwarfeng.tp.core.model.struct.ToolInfo;
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
		CT.trace("线程 main 结束！");
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
	
	/**程序的过程提供器*/
	private final FlowProvider flowProvider = new InnerFlowProvider();
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
	 * 
	 * @return
	 */
	public boolean attemptExit(){
		//TODO 为 ToolPlaform 中的 attemptExit 方法添加具体实现。 
		return true;
	}
	
	public void forceExit(){
		//TODO 为 ToolPlaform 中的 forceExit 方法添加具体实现。 
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
		//structs
		private FinishedFlowTaker finishedFlowTaker = new DefaultFinishedFlowTaker(backgroundModel);
		//obvs
		private LoggerObverser loggerObverser = new LoggerAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.setLogger(loggerModel.getLogger());
			}
		};
		private MutilangObverser loggerMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				finishedFlowTaker.setMutilang(loggerMutilangModel.getMutilang());
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
				
				if(configKey.equals(CoreConfig.MUTILANG_LABEL.getConfigKey())){
					try {
						labelMutilangModel.setCurrentLocale(coreConfigModel.getLabelMutilangLocale());
						loggerMutilangModel.update();
					} catch (ProcessException e) {
						loggerModel.getLogger().warn(loggerMutilangModel.getMutilang().getString(LoggerStringKey.Update_LoggerMutilang_1.getName()), e);
					}
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
				CT.trace("CLICKED");
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
			 * @see com.dwarfeng.tp.core.view.obv.MainFrameObverser#fireRunTool(java.lang.String)
			 */
			@Override
			public void fireRunTool(String name) {
				manager.getBackgroundModel().submit(flowProvider.newRunToolFlow(name));
			}
			
		};


		/**
		 * 新的实例。
		 */
		public Manager() {
			coreConfigModel.addAll(Arrays.asList(CoreConfig.values()));
			modalConfigModel.addAll(Arrays.asList(ModalConfig.values()));
			loggerMutilangModel.setDefaultMutilangInfo(ToolPlatformUtil.getDefaultLoggerMutilangInfo());
			loggerMutilangModel.setDefaultValue(ToolPlatformUtil.getDefaultMissingString());
			loggerMutilangModel.setSupportedKeys(ToolPlatformUtil.getLoggerMutilangSupportedKeys());
			labelMutilangModel.setDefaultMutilangInfo(ToolPlatformUtil.getDefaultLabelMutilangInfo());
			labelMutilangModel.setDefaultValue(ToolPlatformUtil.getDefaultMissingString());
			labelMutilangModel.setSupportedKeys(ToolPlatformUtil.getLabelMutilangSupportedKeys());
			
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
		 * @return the finishedFlowTaker
		 */
		public FinishedFlowTaker getFinishedFlowTaker() {
			return finishedFlowTaker;
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
	
	private final class InnerFlowProvider implements FlowProvider{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.act.FlowProvider#newInitializeFlow()
		 */
		@Override
		public Flow newInitializeFlow() {
			return new InitializeFlow();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.act.FlowProvider#newLoadLibFlow()
		 */
		@Override
		public Flow newLoadLibFlow() {
			return new LoadLibFlow();
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.act.FlowProvider#newCheckLibFlow()
		 */
		@Override
		public Flow newCheckLibFlow() {
			return new CheckLibFlow();
		}

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.act.FlowProvider#newLoadToolInfoFlow()
		 */
		@Override
		public Flow newLoadToolInfoFlow() {
			return new LoadToolInfoFlow();
		}


		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.act.FlowProvider#newRunToolFlow(java.lang.String)
		 */
		@Override
		public Flow newRunToolFlow(String name) {
			return new RunToolFlow(name);
		}


		/**
		 * 内部抽象过程。
		 * <p> 定义常用的内部用方法。
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class InnerAbstractFlow extends AbstractFlow{
			
			private final String blockKey;
			
			/**
			 * 新实例。
			 * @param blockKey 阻挡键, 不能为 <code>null</code>。
			 * @param initMessage 初始的信息，不能为 <code>null</code>。
			 * @throws NullPointerException 入口参数为 <code>null</code>。
			 */
			public InnerAbstractFlow(BlockKey blockKey, String initMessage) {
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
			public InnerAbstractFlow(BlockKey blockKey, String initMessage, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
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
		
		private final class InitializeFlow extends InnerAbstractFlow{
			
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
					
					//加载不可见模态模型
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
					ToolInfoModel tempToolInfoModel = new DefaultToolInfoModel();
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_22);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_22);
					XmlToolInfoLoader toolInfoLoader = null;
					try{
						toolInfoLoader = new XmlToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(tempToolInfoModel);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_INFO).reset();
						toolInfoLoader = new XmlToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(tempToolInfoModel);
					}finally{
						if(Objects.nonNull(toolInfoLoader)){
							toolInfoLoader.close();
						}
					}
					
					next:
					for(String name : tempToolInfoModel.keySet()){
						ToolInfo unsafeToolInfo = tempToolInfoModel.get(name);
						
						Image image;
						Version version;
						Map<Locale, String> descriptions;
						String[] authors;
						String toolClass;
						String infoClass;
						String toolFile;
						String[] toolLibs;
						try{
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

						SafeToolInfo safeToolInfo = new SafeToolInfo(image, version, descriptions, authors, toolClass, infoClass, toolFile, toolLibs);
						manager.getToolInfoModel().put(name, safeToolInfo);
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
							manager.getMainFrameController().setHeight(manager.getModalConfigModel().getMainFrameStartupHeight());
							manager.getMainFrameController().setWidth(manager.getModalConfigModel().getMainFrameStartupWidth());
							manager.getMainFrameController().setExtendedState(manager.getModalConfigModel().getMainFrameStartupExtendedState());
							manager.getMainFrameController().setLocationRelativeTo(null);
						}
					});
					
					//重新加载LoggerModel;
					info(LoggerStringKey.ToolPlatform_FlowProvider_5);
					message(LoggerStringKey.ToolPlatform_FlowProvider_5);
					manager.getLoggerModel().getLoggerContext().close();
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

		private final class LoadLibFlow extends InnerAbstractFlow{
			
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
					manager.getLibraryModel().clear();
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
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_15);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_16);
				}
			}
			
		}
		
		private final class CheckLibFlow extends InnerAbstractFlow{

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
					
					Set<String> keys = new HashSet<>();
					ReadWriteLock modelLock = manager.getLibraryModel().getLock();
					
					modelLock.readLock().lock();
					try{
						keys.addAll(manager.getLibraryModel().keySet());
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
					for(String key : keys){
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
		private final class LoadToolInfoFlow extends InnerAbstractFlow{

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
					XmlToolInfoLoader toolInfoLoader = null;
					try{
						toolInfoLoader = new XmlToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(tempToolInfoModel);
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_FlowProvider_4, e);
						getResource(ResourceKey.TOOL_INFO).reset();
						toolInfoLoader = new XmlToolInfoLoader(getResource(ResourceKey.TOOL_INFO).openInputStream());
						toolInfoLoader.load(tempToolInfoModel);
					}finally{
						if(Objects.nonNull(toolInfoLoader)){
							toolInfoLoader.close();
						}
					}
					
					//解析工具信息
					info(LoggerStringKey.ToolPlatform_FlowProvider_23);
					message(LoggerStringKey.ToolPlatform_FlowProvider_23);
					setTotleProgress(tempToolInfoModel.keySet().size());
					setDeterminate(true);
					
					manager.toolInfoModel.clear();
					next:
					for(String name : tempToolInfoModel.keySet()){
						ToolInfo unsafeToolInfo = tempToolInfoModel.get(name);
						
						Image image;
						Version version;
						Map<Locale, String> descriptionMap;
						String[] authors;
						String toolClass;
						String infoClass;
						String toolFile;
						String[] toolLibs;
						try{
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

						SafeToolInfo safeToolInfo = new SafeToolInfo(image, version, descriptionMap, authors, toolClass, infoClass, toolFile, toolLibs);
						manager.getToolInfoModel().put(name, safeToolInfo);
					}
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_24);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_25);
				}
			}
			
		}
		
		private final class RunToolFlow extends InnerAbstractFlow{

			private final String name;
			
			public RunToolFlow(String name) {
				super(BlockKey.LOAD_TOOLINFO,manager.getLoggerMutilangModel().getMutilang().getString(LoggerStringKey.ToolPlatform_FlowProvider_27.getName()));
				Objects.requireNonNull(name, "入口参数 name 不能为 null。");
				this.name = name;
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

					ToolInfo toolInfo = manager.getToolInfoModel().get(name);
					
					String[] libNames = toolInfo.getToolLibs();
					String[] libPaths = new String[libNames.length];
					for(int i = 0 ; i < libPaths.length ; i ++){
						libPaths[i] = pathGetter.getLibraryPath(libNames[i]);
					}
					
					Image image = toolInfo.getImage();
					String jarPath = pathGetter.getToolFilePath(toolInfo.getToolFile());
					String entryClass = toolInfo.getToolClass();
					File directory = pathGetter.getToolDirectory(name);
					
					RunningTool runningTool = new DefaultRunningTool(image, name, libPaths, jarPath, entryClass, directory);
					
					manager.getToolRuntimeModel().add(runningTool);
					manager.getMainFrameController().assignStream(runningTool);
					runningTool.lockStream();
					
					message(LoggerStringKey.ToolPlatform_FlowProvider_28);
					
				}catch (Exception e) {
					message(LoggerStringKey.ToolPlatform_FlowProvider_29);
				}
			}
			
		}
	
	}
	
}
