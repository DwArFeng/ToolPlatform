package com.dwarfeng.tp.core.control;

import java.util.Arrays;

import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.tp.core.control.proc.ActionProcessor;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;
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
import com.dwarfeng.tp.core.model.struct.ProcessException;

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
		
	}
	
	private final ActionProcessor actionProcessor = new ActionProcessor() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.ActionProcessor#start()
		 */
		@Override
		public void start() throws ProcessException {
//			if(! status.equals(Status.NOT_START))  throw new IllegalStateException("程序已经启动或者已经结束");
//			
//			//定义变量
//			Resource resource = null;
//			Mutilang usingMutilang = ToolPlatformUtil.newInitialLoggerMutilang();
//			Logger usingLogger = null;
//			SplashScreenController usingSplashScreenController = null;
//			TimeMeasurer usingTimeMeasurer = null;
//			
//			
//			try{
//				
//				//加载程序的资源模型
//				ResourceModel resourceModel = new DefaultResourceModel();
//				StreamResourceLoader resourceLoader = null;
//				try{
//					resourceLoader =
//							new XmlResourceLoader(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/paths.xml"));
//					resourceLoader.load(resourceModel);
//				}finally{
//					if(Objects.nonNull(resourceLoader)){
//						resourceLoader.close();
//					}
//				}
//				
//				
//				//加载程序中的记录器模型。
//				LoggerModel loggerModel = new DefaultLoggerModel();
//				resource = resourceModel.get(PathKey.LOGGER_SETTING);
//				StreamLoggerLoader loggerLoader = null;
//				try{
//					loggerLoader = new XmlLoggerLoader(resource.openInputStream());
//					loggerLoader.load(loggerModel);
//				}catch (IOException e) {
//					resource.reset();
//					loggerLoader = new XmlLoggerLoader(resource.openInputStream());
//					loggerLoader.load(loggerModel);
//				}finally {
//					if(Objects.nonNull(loggerLoader)){
//						loggerLoader.close();
//					}
//				}
//				LoggerProvider loggerProvider = new DefaultLoggerProvider(loggerModel);
//				try{
//					loggerProvider.update();
//				}catch(ProcessException e){
//					//TODO 以后寻找更好的解决方式。
//					loggerProvider.update2Default();
//				}
//				usingLogger = loggerProvider.getLogger();
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_1));
//				
//				//加载程序的核心配置。
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_2));
//				ConfigModel coreConfigModel = new DefaultConfigModel(CoreConfig.values());
//				resource = resourceModel.get(PathKey.CONFIGURATION_CORE);
//				StreamConfigLoader coreConfigLoader = null;
//				try{
//					coreConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
//					coreConfigLoader.loadConfig(coreConfigModel);
//				}catch (IOException e) {
//					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
//					resource.reset();
//					coreConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
//					coreConfigLoader.loadConfig(coreConfigModel);
//				}finally {
//					if(Objects.nonNull(coreConfigLoader)){
//						coreConfigLoader.close();
//					}
//				}
//				CoreConfigProvider coreConfigProvider = new DefaultCoreConfigProvider(coreConfigModel);
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_4));
//				
//				//加载记录器多语言配置。
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_5));
//				MutilangModel loggerMutilangModel = new DefaultMutilangModel();
//				resource = resourceModel.get(PathKey.MUTILANG_LOGGER_SETTING);
//				StreamMutilangLoader loggerMutilangLoader = null;
//				try{
//					loggerMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
//					loggerMutilangLoader.load(loggerMutilangModel);
//				}catch (IOException e) {
//					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
//					resource.reset();
//					loggerMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
//					loggerMutilangLoader.load(loggerMutilangModel);
//				}finally {
//					if(Objects.nonNull(loggerMutilangLoader)){
//						loggerMutilangLoader.close();
//					}
//				}
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_6));
//				MutilangProvider loggerMutilangProvider = ToolPlatformUtil.newLoggerMutilangProvider(loggerMutilangModel);
//				try{
//					loggerMutilangProvider.update(coreConfigProvider.getLoggerMutilangLocale());
//					usingMutilang = loggerMutilangProvider.getMutilang();
//				}catch (ProcessException e) {
//					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_7), e);
//					loggerMutilangProvider.update2Default();
//					usingMutilang = loggerMutilangProvider.getMutilang();
//				}
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_8));
//				
//				//判断是否要生成闪现窗体。
//				if(coreConfigProvider.isShowSplashScreen()){
//					usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_9));
//					usingSplashScreenController = ViewUtil.newSplashScreenController();
//					usingTimeMeasurer = new TimeMeasurer();
//					usingTimeMeasurer.start();
//				}
//				
//				//加载标签多语言配置。
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_10));
//				if(Objects.nonNull(usingSplashScreenController)){
//					usingSplashScreenController.setText(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_10));
//				}
//				MutilangModel labelMutilangModel = new DefaultMutilangModel();
//				resource = resourceModel.get(PathKey.MUTILANG_LABEL_SETTING);
//				StreamMutilangLoader labelMutilangLoader = null;
//				try{
//					labelMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
//					labelMutilangLoader.load(labelMutilangModel);
//				}catch(IOException e){
//					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
//					resource.reset();
//					labelMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
//					labelMutilangLoader.load(labelMutilangModel);
//				}finally{
//					if(Objects.nonNull(labelMutilangLoader)){
//						labelMutilangLoader.close();
//					}
//				}
//				MutilangProvider labelMutilangProvider = ToolPlatformUtil.newLabelMutilangProvider(labelMutilangModel);
//				try{
//					labelMutilangProvider.update(coreConfigProvider.getLabelMutilangLocale());
//				}catch (ProcessException e) {
//					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_11), e);
//					labelMutilangProvider.update2Default();
//				}
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_12));
//				
//				//加载不可见模型
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_13));
//				if(Objects.nonNull(usingSplashScreenController)){
//					usingSplashScreenController.setText(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_13));
//				}
//				ConfigModel invisibleConfigModel = new DefaultConfigModel(InvisibleConfig.values());
//				resource = resourceModel.get(PathKey.CONFIGURATION_INVISIBLE);
//				StreamConfigLoader invisibleConfigLoader = null;
//				try{
//					invisibleConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
//					invisibleConfigLoader.loadConfig(invisibleConfigModel);
//				}catch (IOException e) {
//					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
//					resource.reset();
//					invisibleConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
//					invisibleConfigLoader.loadConfig(invisibleConfigModel);
//				}finally{
//					if(Objects.nonNull(invisibleConfigLoader)){
//						invisibleConfigLoader.close();
//					}
//				}
//				InvisibleConfigProvider invisibleConfigProvider = new DefaultInvisibleConfigProvider(invisibleConfigModel);
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_14));
//				
//				
//				
//				
//				
//				
//				
//				
//				//生成模型管理器。
//				ModelManager modelManager = new ModelManager(
//						resourceModel, 
//						coreConfigModel, 
//						invisibleConfigModel, 
//						loggerMutilangModel,
//						labelMutilangModel,
//						loggerMutilangProvider, 
//						labelMutilangProvider, 
//						loggerModel, 
//						loggerProvider);
//				ToolPlatform.this.modelManager = modelManager;
//				
//				
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//				/*
//				 * TODO 程序启动方法有待完善。
//				 * 	加载程序的核心配置					√
//				 * 加载记录器多语言配置				√
//				 * 应用记录器多语言配置				√
//				 * 加载闪现窗体								√
//				 * 加载标签多语言模型					√
//				 * 加载不可见配置模型					√
//				 * 
//				 */
//				
//			}catch (IOException | LoadFailedException e) {
//				if(Objects.nonNull(usingLogger) && Objects.nonNull(usingMutilang)){
//					//TODO 无法完成初始化。
//				}
//				throw new ProcessException("初始化不成功", e.getCause());
//			}finally{
//				if(Objects.nonNull(usingSplashScreenController)){
//					usingSplashScreenController.dispose();
//				}
//			}
//			
		}
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
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
		actionProcessor.start();
	}

	/**
	 * 返回程序的状态。
	 * @return 程序的状态。
	 */
	public Status getStatus() {
		return status;
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

	/**
	 * 程序的模型结构管理器。
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private final static class Manager{
		
		/**用于初始化的可运行对象*/
		private final Runnable initializer = new Runnable() {
			
			
			/*
			 * (non-Javadoc)
			 * @see java.lang.Runnable#run()
			 */
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
		
		private ResourceModel resourceModel;
		private LoggerModel loggerModel;
		private SyncConfigModel coreConfigModel;
		private SyncConfigModel invisibleConfigModel;
		private MutilangModel loggerMutilangModel;
		private MutilangModel labelMutilangModel;
		private ToolModel toolModel;
		private BackgroundModel backgroundModel;
		
		
		/**
		 * 新的实例。
		 */
		public Manager() {
			initializer.run();
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
		
		
	}
	
}
