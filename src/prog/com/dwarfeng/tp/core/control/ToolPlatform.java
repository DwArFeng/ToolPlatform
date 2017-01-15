package com.dwarfeng.tp.core.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.tp.core.control.proc.ProcessProvider;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;
import com.dwarfeng.tp.core.model.cfg.LabelStringKey;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
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
import com.dwarfeng.tp.core.model.io.StreamResourceLoader;
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
import com.dwarfeng.tp.core.util.ToolPlatformUtil;

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
		new ToolPlatform().start();
		CT.trace("�߳� main ������");
	}
	
	private final ProcessProvider processProvider = new InnerProcessProvider();
	
	
	
	
	
	
	
	
	
	
	
	
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
				throw new ProcessException("��ʼ�����̱��ж�", e);
			}
		}finally {
			lock.unlock();
		}

		manager.getBackgroundModel().removeObverser(obverser);
		if(startProcess.getThrowable() != null){
			throw new ProcessException("��ʼ������ʧ��", startProcess.getThrowable());
		}
		
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
		
		//model
		private ResourceModel resourceModel = new DefaultResourceModel();
		private LoggerModel loggerModel = new DefaultLoggerModel();
		private SyncConfigModel coreConfigModel = new DefaultSyncConfigModel();
		private SyncConfigModel invisibleConfigModel = new DefaultSyncConfigModel();
		private MutilangModel loggerMutilangModel = new DefaultMutilangModel();
		private MutilangModel labelMutilangModel = new DefaultMutilangModel();
		private ToolModel toolModel = new DefaultToolModel();
		private BackgroundModel backgroundModel = new DefaultBackgroundModel();
		
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
		
		
		/**
		 * �µ�ʵ����
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
		
	}
	
	private final class InnerProcessProvider implements ProcessProvider{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.ProcessProvider#getStartProcess()
		 */
		@Override
		public Process newStartProcess() {
			return new AbstractProcess(0, 0, false, false) {
				
				/*
				 * (non-Javadoc)
				 * @see com.dwarfeng.tp.core.model.struct.AbstractProcess#process()
				 */
				@Override
				protected void process() {
					
					//����һ��ʼʹ�õ���Ĭ�ϵ����ã������ܳ����쳣��
					manager.getLoggerModel().update();
					manager.getLabelMutilangModel().update();
					manager.getLoggerMutilangModel().update();
					
					//��ȡ����
					try{
						
						//���س������Դģ��
						ResourceModel resourceModel = new DefaultResourceModel();
						StreamResourceLoader resourceLoader = null;
						try{
							resourceLoader =
									new XmlResourceLoader(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/paths.xml"));
							resourceLoader.load(resourceModel);
						}finally{
							if(Objects.nonNull(resourceLoader)){
								resourceLoader.close();
							}
						}
						
					}catch (LoadFailedException | IOException e) {
						// TODO: handle exception
					}

					
					setMessage(manager.getLoggerMutilangProvider().getMutilang().getString(LoggerStringKey.ToolPlatform_ProcessProvider_1));
				}
			};
		}
		
//		/*
//		 * (non-Javadoc)
//		 * @see com.dwarfeng.tp.core.control.proc.ActionProcessor#start()
//		 */
//		@Override
//		public void start() throws ProcessException {
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
//		}
		
	}
	
	
	
}
