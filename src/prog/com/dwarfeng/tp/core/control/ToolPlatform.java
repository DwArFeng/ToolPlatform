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
 * ToolPlatform��DwArFeng �Ĺ���ƽ̨����
 * <p> �ù���ƽ̨���������� DwArFeng ��д���ڶ�Ĺ��ߵġ�
 * �ù���ƽ̨���÷����ȡ�乤��Ŀ¼�µ����й��ߣ�����ӵ�н���Щ���߽��зֱ�ǩ��������������Ĺ��ܡ�
 * <p> TODO ��Ҫ������ϸ��������
 * @author  DwArFeng
 * @since 1.8
 */
public final class ToolPlatform {
	
	/**
	 * �����õ�����������
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
//			if(! status.equals(Status.NOT_START))  throw new IllegalStateException("�����Ѿ����������Ѿ�����");
//			
//			//�������
//			Resource resource = null;
//			Mutilang usingMutilang = ToolPlatformUtil.newInitialLoggerMutilang();
//			Logger usingLogger = null;
//			SplashScreenController usingSplashScreenController = null;
//			TimeMeasurer usingTimeMeasurer = null;
//			
//			
//			try{
//				
//				//���س������Դģ��
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
//				//���س����еļ�¼��ģ�͡�
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
//					//TODO �Ժ�Ѱ�Ҹ��õĽ����ʽ��
//					loggerProvider.update2Default();
//				}
//				usingLogger = loggerProvider.getLogger();
//				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_1));
//				
//				//���س���ĺ������á�
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
//				//���ؼ�¼�����������á�
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
//				//�ж��Ƿ�Ҫ�������ִ��塣
//				if(coreConfigProvider.isShowSplashScreen()){
//					usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_9));
//					usingSplashScreenController = ViewUtil.newSplashScreenController();
//					usingTimeMeasurer = new TimeMeasurer();
//					usingTimeMeasurer.start();
//				}
//				
//				//���ر�ǩ���������á�
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
//				//���ز��ɼ�ģ��
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
//				//����ģ�͹�������
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
//				 * TODO �������������д����ơ�
//				 * 	���س���ĺ�������					��
//				 * ���ؼ�¼������������				��
//				 * Ӧ�ü�¼������������				��
//				 * �������ִ���								��
//				 * ���ر�ǩ������ģ��					��
//				 * ���ز��ɼ�����ģ��					��
//				 * 
//				 */
//				
//			}catch (IOException | LoadFailedException e) {
//				if(Objects.nonNull(usingLogger) && Objects.nonNull(usingMutilang)){
//					//TODO �޷���ɳ�ʼ����
//				}
//				throw new ProcessException("��ʼ�����ɹ�", e.getCause());
//			}finally{
//				if(Objects.nonNull(usingSplashScreenController)){
//					usingSplashScreenController.dispose();
//				}
//			}
//			
		}
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
	private final Manager manager;
	/**�����״̬*/
	private Status status;

	
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
		this.manager = new Manager();
		this.status = Status.NOT_START;
	}
	
	/**
	 * ��������
	 * @throws ProcessException �����쳣��
	 */
	public void start() throws ProcessException{
		actionProcessor.start();
	}

	/**
	 * ���س����״̬��
	 * @return �����״̬��
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * �����е����Լ��ϡ�
	 * <p> �����Լ����ṩ�����е�һЩ�������ԣ������������ơ���������ߡ�����İ汾�ȵȡ�
	 * @author DwArFeng
	 * @since 1.8
	 */
	public final static class Attributes{
		
		/**����İ汾*/
		public final static Version VERSION = new DefaultVersion.Builder()
				.type(VersionType.RELEASE)
				.firstVersion((byte) 0)
				.secondVersion((byte) 0)
				.thirdVersion((byte) 0)
				.buildDate("20161222")
				.buildVersion('A')
				.build();
		
		/**���������*/
		public final static String author = "DwArFeng";
		
		
	}

	/**
	 * �����ģ�ͽṹ��������
	 * @author  DwArFeng
	 * @since 1.8
	 */
	private final static class Manager{
		
		/**���ڳ�ʼ���Ŀ����ж���*/
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
		 * �µ�ʵ����
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
