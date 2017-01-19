package com.dwarfeng.tp.core.control;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigAdapter;
import com.dwarfeng.dutil.develop.cfg.ConfigKey;
import com.dwarfeng.dutil.develop.cfg.ConfigObverser;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigLoader;
import com.dwarfeng.tp.core.control.act.ProcessProvider;
import com.dwarfeng.tp.core.control.act.UiObverserProvider;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.ModalConfig;
import com.dwarfeng.tp.core.model.cfg.ResourceKey;
import com.dwarfeng.tp.core.model.cm.BackgroundModel;
import com.dwarfeng.tp.core.model.cm.CoreConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultBackgroundModel;
import com.dwarfeng.tp.core.model.cm.DefaultCoreConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultLibraryModel;
import com.dwarfeng.tp.core.model.cm.DefaultLoggerModel;
import com.dwarfeng.tp.core.model.cm.DefaultModalConfigModel;
import com.dwarfeng.tp.core.model.cm.DefaultMutilangModel;
import com.dwarfeng.tp.core.model.cm.DefaultResourceModel;
import com.dwarfeng.tp.core.model.cm.DefaultToolInfoModel;
import com.dwarfeng.tp.core.model.cm.LibraryModel;
import com.dwarfeng.tp.core.model.cm.LoggerModel;
import com.dwarfeng.tp.core.model.cm.ModalConfigModel;
import com.dwarfeng.tp.core.model.cm.MutilangModel;
import com.dwarfeng.tp.core.model.cm.ResourceModel;
import com.dwarfeng.tp.core.model.cm.ToolInfoModel;
import com.dwarfeng.tp.core.model.io.XmlLibraryLoader;
import com.dwarfeng.tp.core.model.io.XmlLoggerLoader;
import com.dwarfeng.tp.core.model.io.XmlMutilangLoader;
import com.dwarfeng.tp.core.model.io.XmlResourceLoader;
import com.dwarfeng.tp.core.model.obv.LoggerAdapter;
import com.dwarfeng.tp.core.model.obv.LoggerObverser;
import com.dwarfeng.tp.core.model.obv.MutilangAdapter;
import com.dwarfeng.tp.core.model.obv.MutilangObverser;
import com.dwarfeng.tp.core.model.struct.AbstractProcess;
import com.dwarfeng.tp.core.model.struct.DefaultFinishedProcessTaker;
import com.dwarfeng.tp.core.model.struct.FinishedProcessTaker;
import com.dwarfeng.tp.core.model.struct.Process;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.model.struct.RuntimeState;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.view.gui.MainFrame;
import com.dwarfeng.tp.core.view.gui.SplashScreen;
import com.dwarfeng.tp.core.view.obv.MainFrameObverser;
import com.dwarfeng.tp.core.view.struct.AbstractMainFrameController;
import com.dwarfeng.tp.core.view.struct.AbstractSplashScreenController;
import com.dwarfeng.tp.core.view.struct.MainFrameController;
import com.dwarfeng.tp.core.view.struct.SplashScreenController;

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
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws ProcessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new ToolPlatform().start();
		CT.trace("�߳� main ������");
	}
	
	/**����İ汾*/
	public final static Version VERSION = new DefaultVersion.Builder()
			.type(VersionType.RELEASE)
			.firstVersion((byte) 0)
			.secondVersion((byte) 0)
			.thirdVersion((byte) 0)
			.buildDate("20161222")
			.buildVersion('A')
			.build();
	
	/**����Ľ���۲����ṩ��*/
	private final UiObverserProvider uiObverserProvider = new InnerUiObverserProvider();
	/**����Ĺ����ṩ��*/
	private final ProcessProvider processProvider = new InnerProcessProvider();
	/**���������*/
	private final Manager manager;
	/**�����״̬*/
	private final AtomicReference<RuntimeState> state;

	
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
		this.manager = new Manager();
		this.state = new AtomicReference<RuntimeState>(RuntimeState.NOT_START);
	}
	
	/**
	 * ��������
	 * @throws ProcessException �����쳣��
	 * @throws IllegalStateException �����Ѿ���ʼ��
	 */
	public void start() throws ProcessException{
		//������ʼ������
		final Process initializeProcess = processProvider.newInitializeProcess();
		manager.getBackgroundModel().submit(initializeProcess);
		while(! initializeProcess.isDone()){
			try {
				initializeProcess.waitFinished();
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�����
			}
		}
		if(initializeProcess.getThrowable() != null){
			throw new ProcessException("��ʼ������ʧ��", initializeProcess.getThrowable());
		}
	}
	
	

	/**
	 * ���س����״̬��
	 * @return �����״̬��
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
		//TODO
		return true;
	}
	
	public void forceExit(){
		//TODO
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
		//structs
		private FinishedProcessTaker finishedProcessTaker = new DefaultFinishedProcessTaker(backgroundModel);
		//obvs
		private LoggerObverser loggerObverser = new LoggerAdapter() {
			@Override
			public void fireUpdated() {
				finishedProcessTaker.setLogger(loggerModel.getLogger());
			}
		};
		private MutilangObverser loggerMutilangObverser = new MutilangAdapter() {
			@Override
			public void fireUpdated() {
				finishedProcessTaker.setMutilang(loggerMutilangModel.getMutilang());
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
				MainFrame mainFrame = new MainFrame(labelMutilangModel.getMutilang());
				mainFrame.addObverser(uiObverserProvider.getMainFrameProvider());
				return mainFrame;
			}
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.struct.AbstractGuiController#subDispose(java.awt.Component)
			 */
			@Override
			protected void subDispose(MainFrame component) {
				component.clearObverser();
				component.dispose();
			}
		};
		
		
		/**
		 * �µ�ʵ����
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
		 * @return the finishedProcessTaker
		 */
		public FinishedProcessTaker getFinishedProcessTaker() {
			return finishedProcessTaker;
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
	
	private final class InnerProcessProvider implements ProcessProvider{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.ProcessProvider#getStartProcess()
		 */
		@Override
		public Process newInitializeProcess() {
			return new InitializeProcess();
		}
		
		
		
		
		
		private void info(LoggerStringKey loggerStringKey){
			manager.getLoggerModel().getLogger().info(manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName()));
		}
		
		private void warn(LoggerStringKey loggerStringKey, Throwable throwable){
			manager.getLoggerModel().getLogger().warn(manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName()), throwable);
		}
		
		private final class InitializeProcess extends AbstractProcess{
			
			public InitializeProcess() {
				super(0, 0, false, false);
			}

			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.model.struct.AbstractProcess#process()
			 */
			@Override
			protected void process() {
				try{
					if(getState() != RuntimeState.NOT_START){
						throw new IllegalStateException("�����Ѿ��������Ѿ�����");
					}
					
					//����ģ�ͣ���ʱ�Ķ�����ģ�ͺͼ�¼��ģ�ͱ�����ΪĬ��ֵ��
					try{
						manager.getLoggerModel().update();
						manager.getLabelMutilangModel().update();
						manager.getLoggerMutilangModel().update();
					}catch (ProcessException ignore) {
						//��ʱ��ΪĬ��ֵ���������׳��쳣��
					}
					
					//���س������Դģ��
					info(LoggerStringKey.ToolPlatform_ProcessProvider_3);
					manager.getResourceModel().clear();
					XmlResourceLoader resourceLoader = null;
					try{
						resourceLoader = new XmlResourceLoader(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/paths.xml"));
						resourceLoader.load(manager.getResourceModel());
					}finally{
						if(Objects.nonNull(resourceLoader)){
							resourceLoader.close();
						}
					}
					
					//���س����еļ�¼��ģ�͡�
					info(LoggerStringKey.ToolPlatform_ProcessProvider_5);
					manager.getLoggerModel().clear();
					if(manager.getLoggerModel().getLoggerContext() != null){
						manager.getLoggerModel().getLoggerContext().stop();
					}
					XmlLoggerLoader loggerLoader = null;
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
						manager.getLoggerModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_Logger_1, e);
					}
					
					//���ؼ�¼�����������á�
					info(LoggerStringKey.ToolPlatform_ProcessProvider_7);
					manager.getLoggerMutilangModel().clear();
					XmlMutilangLoader loggerMutilangLoader = null;
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
						manager.getLoggerMutilangModel().update();
					}catch (ProcessException e) {
						warn(LoggerStringKey.Update_LoggerMutilang_1, e);
					}
					
					//���س���ĺ������á�
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
					
					//�����Ҫ��ʾ�������ڣ�����ʾ��������
					boolean splashFlag = manager.getCoreConfigModel().isShowSplashScreen();
					TimeMeasurer tm = new TimeMeasurer();
					if(splashFlag){
						info(LoggerStringKey.ToolPlatform_ProcessProvider_8);
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_8);
						tm.start();
					}
					
					//���ر�ǩ���������á�
					info(LoggerStringKey.ToolPlatform_ProcessProvider_9);
					manager.getLabelMutilangModel().clear();
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_9);
					}
					XmlMutilangLoader labelMutilangLoader = null;
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
					
					//���ز��ɼ�ģ̬ģ��
					info(LoggerStringKey.ToolPlatform_ProcessProvider_10);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_10);
					}
					StreamConfigLoader modalConfigLoader = null;
					try{
						modalConfigLoader = new PropertiesConfigLoader(getResource(ResourceKey.CONFIGURATION_MODAL).openInputStream());
						modalConfigLoader.load(manager.getModalConfigModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_4, e);
						getResource(ResourceKey.CONFIGURATION_MODAL).reset();
						modalConfigLoader = new PropertiesConfigLoader(getResource(ResourceKey.CONFIGURATION_MODAL).openInputStream());
						modalConfigLoader.load(manager.getModalConfigModel());
					}finally{
						if(Objects.nonNull(modalConfigLoader)){
							modalConfigLoader.close();
						}
					}
					
					//���ؿ�ģ��
					info(LoggerStringKey.ToolPlatform_ProcessProvider_12);
					manager.getLibraryModel().clear();
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_12);
					}
					XmlLibraryLoader libraryLoader = null;
					try{
						libraryLoader = new XmlLibraryLoader(getResource(ResourceKey.TOOL_LIBS).openInputStream());
						libraryLoader.load(manager.getLibraryModel());
					}catch (IOException e) {
						warn(LoggerStringKey.ToolPlatform_ProcessProvider_4, e);
						getResource(ResourceKey.TOOL_LIBS).reset();
						libraryLoader = new XmlLibraryLoader(getResource(ResourceKey.TOOL_LIBS).openInputStream());
						libraryLoader.load(manager.getLibraryModel());
					}finally{
						if(Objects.nonNull(libraryLoader)){
							libraryLoader.close();
						}
					}
					
					//����������
					info(LoggerStringKey.ToolPlatform_ProcessProvider_11);
					if(splashFlag){
						splash(LoggerStringKey.ToolPlatform_ProcessProvider_11);
					}
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getMainFrameController().newInstance();
							manager.getMainFrameController().setHeight(manager.getModalConfigModel().getMainFrameStartupHeight());
							manager.getMainFrameController().setWidth(manager.getModalConfigModel().getMainFrameStartupWidth());
							manager.getMainFrameController().setExtendedState(manager.getModalConfigModel().getMainFrameStartupExtendedState());
							manager.getMainFrameController().setLocationRelativeTo(null);
						}
					});
					
					//�ȴ��������ڵ���ָ����ʱ���������ʧ��
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
					
					//��ʾ��������
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getMainFrameController().setVisible(true);
						}
					});
					
					//���óɹ���Ϣ
					message(LoggerStringKey.ToolPlatform_ProcessProvider_1);
					setState(RuntimeState.RUNNING);
				}catch (Exception e) {
					//���������ڹرա�
					ToolPlatformUtil.invokeInEventQueue(new Runnable() {
						@Override
						public void run() {
							manager.getSplashScreenController().dispose();
						}
					});
					setThrowable(e);
					message(LoggerStringKey.ToolPlatform_ProcessProvider_2);
				}
			}
			
			private Resource getResource(ResourceKey resourceKey){
				return manager.getResourceModel().get(resourceKey.getName());
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
			private void message(LoggerStringKey loggerStringKey){
				setMessage(manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName()));
			}
			
		}

		
	}
	
	private final class InnerUiObverserProvider implements UiObverserProvider{
		
		private final MainFrameObverser mainFrameObverser = new MainFrameObverser() {
			
			/*
			 * (non-Javadoc)
			 * @see com.dwarfeng.tp.core.view.obv.MainFrameObverser#fireProgramClosed()
			 */
			@Override
			public void fireProgramToClose() {
				CT.trace("CLICKED");
			}
		};

		
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.act.UiObverserProvider#getMainFrameProvider()
		 */
		@Override
		public MainFrameObverser getMainFrameProvider() {
			return mainFrameObverser;
		}
		
	}
	
}
