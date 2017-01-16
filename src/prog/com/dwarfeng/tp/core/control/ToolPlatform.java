package com.dwarfeng.tp.core.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigLoader;
import com.dwarfeng.tp.core.control.proc.ProcessProvider;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.ResourceKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.cm.DefaultBackgroundModel;
import com.dwarfeng.tp.core.model.cm.DefaultLoggerModel;
import com.dwarfeng.tp.core.model.cm.DefaultMutilangModel;
import com.dwarfeng.tp.core.model.cm.DefaultResourceModel;
import com.dwarfeng.tp.core.model.cm.DefaultSyncConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultToolModel;
import com.dwarfeng.tp.core.model.cm.LoggerModel;
import com.dwarfeng.tp.core.model.cm.MutilangModel;
import com.dwarfeng.tp.core.model.cm.ResourceModel;
import com.dwarfeng.tp.core.model.cm.SyncConfigModel;
import com.dwarfeng.tp.core.model.cm.ToolModel;
import com.dwarfeng.tp.core.model.io.StreamLoggerLoader;
import com.dwarfeng.tp.core.model.io.StreamMutilangLoader;
import com.dwarfeng.tp.core.model.io.StreamResourceLoader;
import com.dwarfeng.tp.core.model.io.XmlLoggerLoader;
import com.dwarfeng.tp.core.model.io.XmlMutilangLoader;
import com.dwarfeng.tp.core.model.io.XmlResourceLoader;
import com.dwarfeng.tp.core.model.obv.BackgroundAdapter;
import com.dwarfeng.tp.core.model.obv.BackgroundObverser;
import com.dwarfeng.tp.core.model.obv.LoggerAdapter;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.obv.MutilangAdapter;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.AbstractProcess;
import com.dwarfeng.tp.core.model.struct.CoreConfigProvider;
import com.dwarfeng.tp.core.model.struct.DefaultCoreConfigProvider;
import com.dwarfeng.tp.core.model.struct.DefaultFinishedProcessTaker;
import com.dwarfeng.tp.core.model.struct.DefaultInvisibleConfigProvider;
import com.dwarfeng.tp.core.model.struct.DefaultLoggerProvider;
import com.dwarfeng.tp.core.model.struct.DefaultMutilangProvider;
import com.dwarfeng.tp.core.model.struct.FinishedProcessTaker;
import com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider;
import com.dwarfeng.tp.core.model.struct.LoggerProvider;
import com.dwarfeng.tp.core.model.struct.MutilangProvider;
import com.dwarfeng.tp.core.model.struct.Process;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.gui.SplashScreen;
import com.dwarfeng.tp.core.view.struct.AbstractSplashScreenController;
import com.dwarfeng.tp.core.view.struct.SplashScreenController;

/**
 * ToolPlatform（DwArFeng 的工具平台）。
 * <p> 该工具平台是用来管理 DwArFeng 编写的众多的工具的。
 * 该工具平台利用反射读取其工具目录下的所有工具，并且拥有将这些工具进行分标签管理、搜索、分类的功能。
 * <p> TODO 需要进行详细的描述。
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {
	
	/**
	 * 调试用的启动方法。
	 */
	public static void main(String[] args) throws ProcessException {
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
	
	private final ProcessProvider processProvider = new InnerProcessProvider();
	/**程序管理器*/
	private final Manager manager;
	/**程序的状态*/
	private Status status;

	
	
	/**
	 * 生成一个默认的工具平台实例。
	 * 生成一个具有指定 TODO
	 */
	public ToolPlatform() {
		this.manager = new Manager();
		this.status = Status.NOT_START;
	}
	
	/**
	 * 启动程序。
	 * @throws ProcessException 过程异常。
	 */
	public void start() throws ProcessException{
		final Process startProcess = processProvider.newStartProcess();
		final Lock lock = new ReentrantLock();
		final Condition condition = lock.newCondition();
		final BackgroundObverser obverser = new BackgroundAdapter() {
			@Override
			public void fireProcessDone(Process process) {
				if(Objects.equals(process, startProcess)){
					lock.lock();
					try{
						condition.signalAll();
					}finally {
						lock.unlock();
					}
				}
			}
		};
		
		manager.getBackgroundModel().addObverser(obverser);
		manager.getBackgroundModel().submit(startProcess);
		
		lock.lock();
		try{
			try{
				while(! startProcess.isDone()){
					condition.await();
				}
			} catch (InterruptedException e) {
				manager.getBackgroundModel().removeObverser(obverser);
				throw new ProcessException("初始化过程被中断", e);
			}
		}finally {
			lock.unlock();
		}

		manager.getBackgroundModel().removeObverser(obverser);
		if(startProcess.getThrowable() != null){
			throw new ProcessException("初始化过程失败", startProcess.getThrowable());
		}
		
	}

	/**
	 * 返回程序的状态。
	 * @return 程序的状态。
	 */
	public Status getStatus() {
		return status;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private final class Manager{
		
		//model
		private ResourceModel resourceModel = new DefaultResourceModel();
		private LoggerModel loggerModel = new DefaultLoggerModel();
		private SyncConfigModel coreConfigModel = new DefaultSyncConfigModel();
		private SyncConfigModel invisibleConfigModel = new DefaultSyncConfigModel();
		private MutilangModel loggerMutilangModel = new DefaultMutilangModel();
		private MutilangModel labelMutilangModel = new DefaultMutilangModel();
		private ToolModel toolModel = new DefaultToolModel();
		private BackgroundModel backgroundModel = new DefaultBackgroundModel();
		//taker & provider
		private FinishedProcessTaker finishedProcessTaker = new DefaultFinishedProcessTaker(backgroundModel);
		private MutilangProvider loggerMutilangProvider = new DefaultMutilangProvider(loggerMutilangModel);
		private MutilangProvider labelMutilangProvider = new DefaultMutilangProvider(labelMutilangModel);
		private CoreConfigProvider coreConfigProvider = new DefaultCoreConfigProvider(coreConfigModel);
		private InvisibleConfigProvider invisibleConfigProvider = new DefaultInvisibleConfigProvider(invisibleConfigModel);
		private LoggerProvider loggerProvider = new DefaultLoggerProvider(loggerModel);
		//obvs
		private LoggerObverser loggerObverser = new LoggerAdapter() {
			@Override
			public void fireUpdated() {
				finishedProcessTaker.setLogger(loggerProvider.getLogger());
			}
		};
		private MutilangObverser loggerMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				finishedProcessTaker.setMutilang(loggerMutilangProvider.getMutilang());
			}
		};
		private ConfigObverser coreConfigObverser = new ConfigAdapter() {
			@Override
			public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue,String validValue) {
				if(configKey.equals(CoreConfig.MUTILANG_LOGGER.getConfigKey())){
					loggerMutilangModel.setCurrentLocale(coreConfigProvider.getLoggerMutilangLocale());
					try {
						loggerMutilangModel.tryUpdate();
					} catch (ProcessException e) {
						loggerProvider.getLogger().warn(loggerMutilangProvider.getMutilang().getString(LoggerStringKey.ToolPlatform_Manager_1), e);
					}
				}
				
				if(configKey.equals(CoreConfig.MUTILANG_LABEL.getConfigKey())){
					labelMutilangModel.setCurrentLocale(coreConfigProvider.getLabelMutilangLocale());
					try {
						labelMutilangModel.tryUpdate();
					} catch (ProcessException e) {
						loggerProvider.getLogger().warn(loggerMutilangProvider.getMutilang().getString(LoggerStringKey.ToolPlatform_Manager_2), e);
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
		
		
		/**
		 * 新的实例。
		 */
		public Manager() {
			coreConfigModel.addAll(Arrays.asList(CoreConfig.values()));
			invisibleConfigModel.addAll(Arrays.asList(InvisibleConfig.values()));
			loggerMutilangModel.setDefaultMutilangMap(ToolPlatformUtil.getLoggerMutilangDefaultMap());
			loggerMutilangModel.setDefaultValue(ToolPlatformUtil.getMissingString());
			loggerMutilangModel.setSupportedKeys(new HashSet<>(Arrays.asList(LoggerStringKey.values())));
			labelMutilangModel.setDefaultMutilangMap(ToolPlatformUtil.getLabelMutilangDefaultMap());
			labelMutilangModel.setDefaultValue(ToolPlatformUtil.getMissingString());
			labelMutilangModel.setSupportedKeys(new HashSet<>(Arrays.asList(LabelStringKey.values())));
			
			loggerModel.addObverser(loggerObverser);
			loggerMutilangModel.addObverser(loggerMutilangObverser);
			coreConfigModel.addObverser(coreConfigObverser);
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
		public SyncConfigModel getCoreConfigModel() {
			return coreConfigModel;
		}

		/**
		 * @return the invisibleConfigModel
		 */
		public SyncConfigModel getInvisibleConfigModel() {
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
		 * @return the toolModel
		 */
		public ToolModel getToolModel() {
			return toolModel;
		}

		/**
		 * @return the backgroundModel
		 */
		public BackgroundModel getBackgroundModel() {
			return backgroundModel;
		}

		/**
		 * @return the finishedProcessTaker
		 */
		public FinishedProcessTaker getFinishedProcessTaker() {
			return finishedProcessTaker;
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
		 * @return the coreConfigProvider
		 */
		public CoreConfigProvider getCoreConfigProvider() {
			return coreConfigProvider;
		}

		/**
		 * @return the invisibleConfigProvider
		 */
		public InvisibleConfigProvider getInvisibleConfigProvider() {
			return invisibleConfigProvider;
		}

		/**
		 * @return the loggerProvider
		 */
		public LoggerProvider getLoggerProvider() {
			return loggerProvider;
		}

		/**
		 * @return the splashScreenController
		 */
		public SplashScreenController getSplashScreenController() {
			return splashScreenController;
		}
		
	}
	
	private final class InnerProcessProvider implements ProcessProvider{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.ProcessProvider#getStartProcess()
		 */
		@Override
		public Process newStartProcess() {
			return new StartProcess();
		}
		
		
		private final class StartProcess extends AbstractProcess{
			
			public StartProcess() {
				super(0, 0, false, false);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.AbstractProcess#process()
			 */
			@Override
			protected void process() {
				try{
					//更新模型，此时的多语言模型和记录器模型被更新为默认值。
					manager.getLoggerModel().update();
					manager.getLabelMutilangModel().update();
					manager.getLoggerMutilangModel().update();
					
					//加载程序的资源模型
					info(LoggerStringKey.ToolPlatform_ProcessProvider_3);
					StreamResourceLoader resourceLoader = null;
					try{
						resourceLoader = new XmlResourceLoader(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/paths.xml"));
						resourceLoader.load(manager.getResourceModel());
					}finally{
						if(Objects.nonNull(resourceLoader)){
							resourceLoader.close();
						}
					}
					
					//加载程序中的记录器模型。
					info(LoggerStringKey.ToolPlatform_ProcessProvider_5);
					StreamLoggerLoader loggerLoader = null;
					try{
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_4, e);
						getResource(ResourceKey.LOGGER_SETTING).reset();
						loggerLoader = new XmlLoggerLoader(getResource(ResourceKey.LOGGER_SETTING).openInputStream());
						loggerLoader.load(manager.getLoggerModel());
					}finally {
						if(Objects.nonNull(loggerLoader)){
							loggerLoader.close();
						}
					}
					try{
						manager.getLoggerModel().tryUpdate();
					}catch (ProcessException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_7, e);
					}
					
					//加载记录器多语言配置。
					info(LoggerStringKey.ToolPlatform_ProcessProvider_8);
					StreamMutilangLoader loggerMutilangLoader = null;
					try{
						loggerMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LOGGER_SETTING).openInputStream());
						loggerMutilangLoader.load(manager.getLoggerMutilangModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_4, e);
						getResource(ResourceKey.MUTILANG_LOGGER_SETTING).reset();
						loggerMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LOGGER_SETTING).openInputStream());
						loggerMutilangLoader.load(manager.getLoggerMutilangModel());
					}finally {
						if(Objects.nonNull(loggerMutilangLoader)){
							loggerMutilangLoader.close();
						}
					}
					try{
						manager.getLoggerMutilangModel().tryUpdate();
					}catch (ProcessException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_9, e);
					}
					
					//加载程序的核心配置。
					info(LoggerStringKey.ToolPlatform_ProcessProvider_6);
					StreamConfigLoader coreConfigLoader = null;
					try{
						coreConfigLoader = new PropertiesConfigLoader(getResource(ResourceKey.CONFIGURATION_CORE).openInputStream());
						coreConfigLoader.load(manager.getCoreConfigModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_4, e);
						getResource(ResourceKey.CONFIGURATION_CORE).reset();
						coreConfigLoader = new PropertiesConfigLoader(getResource(ResourceKey.CONFIGURATION_CORE).openInputStream());
						coreConfigLoader.load(manager.getCoreConfigModel());
					}finally {
						if(Objects.nonNull(coreConfigLoader)){
							coreConfigLoader.close();
						}
					}
					
					//如果需要显示启动窗口，则显示启动窗口
					boolean splashFlag = manager.getCoreConfigProvider().isShowSplashScreen();
					if(splashFlag){
						info(LoggerStringKey.ToolPlatform_ProcessProvider_10);
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_10);
					}
					
					//加载标签多语言配置。
					info(LoggerStringKey.ToolPlatform_ProcessProvider_11);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_11);
					}
					StreamMutilangLoader labelMutilangLoader = null;
					try{
						labelMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LABEL_SETTING).openInputStream());
						labelMutilangLoader.load(manager.getLabelMutilangModel());
					}catch(IOException e){
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_4, e);
						getResource(ResourceKey.MUTILANG_LABEL_SETTING).reset();
						labelMutilangLoader = new XmlMutilangLoader(getResource(ResourceKey.MUTILANG_LABEL_SETTING).openInputStream());
						labelMutilangLoader.load(manager.getLabelMutilangModel());
					}finally{
						if(Objects.nonNull(labelMutilangLoader)){
							labelMutilangLoader.close();
						}
					}
					
					//加载不可见模型
					info(LoggerStringKey.ToolPlatform_ProcessProvider_12);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_12);
					}
					StreamConfigLoader invisibleConfigLoader = null;
					try{
						invisibleConfigLoader = new PropertiesConfigLoader(getResource(ResourceKey.CONFIGURATION_INVISIBLE).openInputStream());
						invisibleConfigLoader.load(manager.getInvisibleConfigModel());
					}catch (IOException e) {
						getResource(ResourceKey.CONFIGURATION_INVISIBLE).reset();
						invisibleConfigLoader = new PropertiesConfigLoader(getResource(ResourceKey.CONFIGURATION_INVISIBLE).openInputStream());
						invisibleConfigLoader.load(manager.getInvisibleConfigModel());
					}finally{
						if(Objects.nonNull(invisibleConfigLoader)){
							invisibleConfigLoader.close();
						}
					}
					
					//设置成功消息
					setMessage(manager.getLoggerMutilangProvider().getMutilang().getString(LoggerStringKey.ToolPlatform_ProcessProvider_1));
				}catch (Exception e) {
					//将启动窗口关闭。
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							//manager.getSplashScreenController().dispose();
						}
					});
					setThrowable(e);
					setMessage(manager.getLoggerMutilangProvider().getMutilang().getString(LoggerStringKey.ToolPlatform_ProcessProvider_2));
				}
			}
			
		}
		
		private void info(LoggerStringKey loggerStringKey){
			manager.getLoggerProvider().getLogger().info(manager.getLoggerMutilangProvider().getMutilang().getString(loggerStringKey));
		}
		
		private void warn(LoggerStringKey loggerStringKey, Throwable throwable){
			manager.getLoggerProvider().getLogger().warn(manager.getLoggerMutilangProvider().getMutilang().getString(loggerStringKey), throwable);
		}
		
		private Resource getResource(ResourceKey resourceKey){
			return manager.getResourceModel().get(resourceKey);
		}
		
		private void splash(LoggerStringKey loggerStringKey){
			ToolPlatformUtil.invokeInEventQueue(new Runnable() {
				@Override
				public void run() {
					manager.getSplashScreenController().show();
					manager.getSplashScreenController().setMessage(manager.getLoggerMutilangProvider().getMutilang().getString(loggerStringKey));
				}
			});
		}
		
	}
	
	
	
}
