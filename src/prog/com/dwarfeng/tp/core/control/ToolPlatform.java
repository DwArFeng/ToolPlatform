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
 * ToolPlatform��DwArFeng �Ĺ���ƽ̨����
 * <p> �ù���ƽ̨���������� DwArFeng ��д���ڶ�Ĺ��ߵġ�
 * �ù���ƽ̨���÷����ȡ�乤��Ŀ¼�µ����й��ߣ�����ӵ�н���Щ���߽��зֱ�ǩ��������������Ĺ��ܡ�
 * <p> TODO ToolPlatform ��Doc�ĵ���Ҫ������ϸ��������
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ToolPlatform {
	
	/**
	 * �����õ�����������
	 * @throws UnsupportedLookAndFeelException 
	 */
	public static void main(String[] args) throws ProcessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		new ToolPlatform().start();
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
	
	/**�����ʵ���б����ڳ�������*/
	private static final Set<ToolPlatform> INSTANCES  = Collections.synchronizedSet(new HashSet<>());
	/**����ƽ̨�Ľ��̹���*/
	private static final ThreadFactory THREAD_FACTORY = new NumberedThreadFactory("tool_platform");
	
	/**����Ĺ����ṩ��*/
	private final FlowProvider flowProvider = new FlowProvider();
	/**������˳������ṩ��*/
	private final ShutdownHookProvider shutdownHookProvider = new ShutdownHookProvider();
	/**������ֹʱ�Ĺ���*/
	private final Map<String, Thread> shutdownHooks = Collections.synchronizedMap(new HashMap<>());

	/**���������*/
	private final Manager manager;
	/**�����״̬*/
	private final AtomicReference<RuntimeState> state;
	
	/**
	 * ��ʵ����
	 */
	public ToolPlatform() {
		this.manager = new Manager();
		this.state = new AtomicReference<RuntimeState>(RuntimeState.NOT_START);
		
		//Ϊ�Լ��������á�
		INSTANCES.add(this);
	}
	
	/**
	 * ��������
	 * @throws ProcessException �����쳣��
	 * @throws IllegalStateException �����Ѿ���ʼ��
	 */
	public void start() throws ProcessException{
		//������ʼ������
		final Flow initializeFlow = flowProvider.newInitializeFlow();
		manager.getBackgroundModel().submit(initializeFlow);
		while(! initializeFlow.isDone()){
			try {
				initializeFlow.waitFinished();
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�����
			}
		}
		if(initializeFlow.getThrowable() != null){
			throw new ProcessException("��ʼ������ʧ��", initializeFlow.getThrowable());
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
	 * ���Թرձ�����
	 * <p> ���ø÷����󣬻᳢�Թرճ��������������رյ���������رգ����򣬻�ѯ���û���
	 * �������û�����رհ�ť������
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
		 * �µ�ʵ����
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
				//δ��ʼ��֮ǰ��������ģ��ʹ�õ��ǹ̻��ڳ����е����ݣ������ܳ����쳣��
				e.printStackTrace();
			}
		}
		
		
		/**
		 * �ͷ���Դ��
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
		 * ��ȡһ���µĳ����ʼ��ʱʹ�õĹ��̡�
		 * @return �µĳ����ʼ��ʱʹ�õĺ�̨���̡�
		 */
		public Flow newInitializeFlow() {
			return new InitializeFlow();
		}
		
		/**
		 * ��ȡһ���µĶ�ȡ��Ĺ��̡�
		 * @return �µĶ�ȡ��Ĺ��̡�
		 */
		public Flow newLoadLibFlow() {
			return new LoadLibFlow();
		}
		
		/**
		 * ��ȡһ���µļ���Ĺ��̡�
		 * @return �µļ���Ĺ��̡�
		 */
		public Flow newCheckLibFlow() {
			return new CheckLibFlow();
		}

		/**
		 * ��ȡһ���µĶ�ȡ������Ϣ�Ĺ��̡�
		 * @return �µĶ�ȡ������Ϣ�Ĺ��̡�
		 */
		public Flow newLoadToolInfoFlow() {
			return new LoadToolInfoFlow();
		}

		/**
		 *  ��ȡһ���µ����й��ߵĹ��̡�
		 * @param toolInfo ָ���Ĺ��ߡ�
		 * @return �µ����й��ߵĹ��̡�
		 */
		public Flow newRunToolFlow(ToolInfo toolInfo) {
			return new RunToolFlow(toolInfo);
		}

		/**
		 * ��ȡһ���µĹرմ��ڵĹ��̡�
		 * @return �µĹرմ��ڵĹ��̡�
		 */
		public Flow newClosingFlow() {
			return new WindowClosingFlow();
		}


		/**
		 * �ڲ�������̡�
		 * <p> ���峣�õ��ڲ��÷�����
		 * @author DwArFeng
		 * @since 0.0.0-alpha
		 */
		private abstract class AbstractInnerFlow extends AbstractFlow{
			
			private final String blockKey;
			
			/**
			 * ��ʵ����
			 * @param blockKey �赲��, ����Ϊ <code>null</code>��
			 * @param initMessage ��ʼ����Ϣ������Ϊ <code>null</code>��
			 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage) {
				this(blockKey, initMessage, 0, 0, false, false);
			}
			
			/**
			 * ��ʵ����
			 * @param blockKey �赲��������Ϊ <code>null</code>��
			 * @param initMessage ��ʼ����Ϣ������Ϊ <code>null</code>��
			 * @param progress ��ǰ���ȡ�
			 * @param totleProgress �ܽ��ȡ�
			 * @param determinateFlag �Ƿ�Ϊ������֪�����̡�
			 * @param cancelableFlag �Ƿ��ܹ���ȡ����
			 */
			public AbstractInnerFlow(BlockKey blockKey, String initMessage, int progress, int totleProgress, boolean determinateFlag, boolean cancelableFlag ){
				super(progress, totleProgress, determinateFlag, cancelableFlag);
				Objects.requireNonNull(blockKey, "��ڲ��� blockKey ����Ϊ null��");
				Objects.requireNonNull(initMessage, "��ڲ��� initMessage ����Ϊ null��");
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
			 * �Ӵ�������
			 * <p> �÷�������Ҫ�Ĵ�������
			 * <p> �÷����������׳��κ��쳣��
			 */
			protected abstract void subProcess();
			
			/**
			 * ����ָ���ļ�¼��������Ӧ���ַ�����
			 * @param loggerStringKey ָ���ļ�¼������
			 * @return ��¼������Ӧ���ַ�����
			 */
			protected String getLabel(LoggerStringKey loggerStringKey){
				return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
			}

			/**
			 * ����ָ����¼������ format �ַ�����
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args ָ���� format ������
			 * @return ָ���ļ�¼������ format �ַ�����
			 */
			protected String formatLabel(LoggerStringKey loggerStringKey, Object... args){
				return String.format(manager.getLoggerMutilangModel().getMutilang().getString(
						loggerStringKey.getName()),args);
			}
			
			/**
			 * ���¼��������һ��INFO����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 */
			protected void info(LoggerStringKey loggerStringKey){
				manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
			}

			/**
			 * ���¼����formatһ��INFO����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args format������
			 */
			protected void formatInfo(LoggerStringKey loggerStringKey, Object... args){
				manager.getLoggerModel().getLogger().info(formatLabel(loggerStringKey, args));	
			}

			/**
			 * ���¼��������һ��WARN����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param throwable ָ���Ŀ��׳�����
			 */
			protected void warn(LoggerStringKey loggerStringKey, Throwable throwable){
				manager.getLoggerModel().getLogger().warn(getLabel(loggerStringKey), throwable);
			}

			/**
			 * ��ȡָ������Ӧ����Դ��
			 * @param resourceKey ָ���ļ���
			 * @return ָ���ļ���Ӧ����Դ��
			 */
			protected Resource getResource(ResourceKey resourceKey){
				return manager.getResourceModel().get(resourceKey.getName());
			}
			
			/**
			 * ������ϢΪָ������Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 */
			protected void message(LoggerStringKey loggerStringKey){
				setMessage(getLabel(loggerStringKey));
			}
			
			/**
			 * ��ʽ��������Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args format ������
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
						throw new IllegalStateException("�����Ѿ��������Ѿ�����");
					}
					
					//����ģ�ͣ���ʱ�Ķ�����ģ�ͺͼ�¼��ģ�ͱ�����ΪĬ��ֵ��
					try{
						manager.getLoggerModel().update();
						manager.getLabelMutilangModel().update();
						manager.getLoggerMutilangModel().update();
						manager.getBlockModel().update();
					}catch (ProcessException ignore) {
						//��ʱ��ΪĬ��ֵ���������׳��쳣��
					}
					
					//���س������Դģ��
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
					
					//���س����еļ�¼��ģ�͡�
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
					
					//���ؼ�¼�����������á�
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
					
					//���س���ĺ������á�
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
					
					//�����Ҫ��ʾ�������ڣ�����ʾ��������
					boolean splashFlag = manager.getCoreConfigModel().isShowSplashScreen();
					TimeMeasurer tm = new TimeMeasurer();
					if(splashFlag){
						info(LoggerStringKey.ToolPlatform_FlowProvider_8);
						message(LoggerStringKey.ToolPlatform_FlowProvider_8);
						splash(LoggerStringKey.ToolPlatform_FlowProvider_8);
						tm.start();
					}
					
					//�����赲ģ���ֵ�
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
					
					//���ر�ǩ���������á�
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
					
					//����ģ̬ģ��
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
					
					//����������
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
					
					//���¼���LoggerModel;
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
					
					//���ؿ�ģ��
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
					
					//���ع�����Ϣģ��
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
					
					//TODO ���ع�����ʷ
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
					
					//ע���˳�������
					info(LoggerStringKey.ToolPlatform_FlowProvider_33);
					if (splashFlag) {
						splash(LoggerStringKey.ToolPlatform_FlowProvider_33);
					}
					
					putShutdownHook("destroy_runningtool", shutdownHookProvider.newDestroyRunningToolRunnable());
					putShutdownHook("remove_reference", shutdownHookProvider.newRemoveReferenceRunnable());
					
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
					message(LoggerStringKey.ToolPlatform_FlowProvider_1);
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
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					//��ȡ���ļ�
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
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					//��ʼ��������
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
					
					//���ù����ǿ���ȷ�����ȵģ���ȷ������
					setTotleProgress(manager.getLibraryModel().size());
					setCancelable(true);
					setDeterminate(true);
					
					//��ʼѭ�����
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
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					//���ع�����Ϣģ��
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
					
					//����������Ϣ
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
				Objects.requireNonNull(toolInfo, "��ڲ��� toolInfo ����Ϊ null��");
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
						throw new IllegalStateException("����δ�������Ѿ�����");
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
						//�ж�ҲҪ���ջ�������
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
						throw new IllegalStateException("����δ�������Ѿ�����");
					}
					
					info(LoggerStringKey.ToolPlatform_FlowProvider_30);
					
					manager.getToolRuntimeModel().setAddRejected(true);
					
					if(manager.getToolRuntimeModel().hasNotExited()){
						//TODO ֮��Ҫͨ���Ի������ȷ�ϣ��������ڿ���̨���������һ����Ϣ��
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
			//���ע�Ṥ�߹���
			info(LoggerStringKey.ToolPlatform_Exitor_11);
			removeShutdownHook("destroy_runningtool");
			
			//ֹͣ��̨ģ�ͺ͹�������ʱģ�͡�
			info(LoggerStringKey.ToolPlatform_Exitor_3);
			manager.getBackgroundModel().shutdown();
			manager.getToolRuntimeModel().shutdown();
			manager.getFinishedFlowTaker().shutdown();
			manager.getExitedRunningToolTaker().shutdown();
			
			//�ȴ�50���룬��ʱ��̨ģ�ͺ͹�������ʱģ���е�ִ����Ӧ�û���Ȼ�սᡣ
			try {
				Thread.sleep(50);
			} catch (InterruptedException ignore) {
				//�ж�ҲҪ���ջ�������
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
					//�ж�ҲҪ���ջ�������
				}
				
				if(! manager.getBackgroundModel().getExecutorService().isTerminated()){
					warn(LoggerStringKey.ToolPlatform_Exitor_4);
				}
				if(! manager.getToolRuntimeModel().getExecutorService().isTerminated()){
					warn(LoggerStringKey.ToolPlatform_Exitor_5);
				}
				
			}
			
			//����ģ̬����
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
			
			//TODO �����������
			
			//�ͷŽ���
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
				//�ж�ҲҪ���ջ�������
			}
			
			//���¼���LoggerModel;
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
	
			//�ͷ�ģ��
			if(! logInvalid){
				info(LoggerStringKey.ToolPlatform_Exitor_7);
			}
			manager.dispose();
			
			//�����������ò��Ƴ�����
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
		 * ��ȡָ������Ӧ����Դ��
		 * @param resourceKey ָ���ļ���
		 * @return ָ���ļ���Ӧ����Դ��
		 */
		private Resource getResource(ResourceKey resourceKey){
			return manager.getResourceModel().get(resourceKey.getName());
		}
		
	}







	private final class ShutdownHookProvider{
		
		/**
		 * ����һ���µ��������й��߿����ж���
		 * @return �µ����ٹ��߿����ж���
		 */
		public Runnable newDestroyRunningToolRunnable(){
			return new DestroyRunningToolRunnable();
		}
		
		/**
		 * ����һ���µ��Ƴ����ÿ����ж���
		 * @return �µ��Ƴ����ÿ����ж���
		 */
		public Runnable newRemoveReferenceRunnable(){
			return new RemoveReferenceRunnable();
		}
		
		private abstract class AbstractInnerRunnable implements Runnable{

			/**
			 * ����ָ���ļ�¼��������Ӧ���ַ�����
			 * @param loggerStringKey ָ���ļ�¼������
			 * @return ��¼������Ӧ���ַ�����
			 */
			protected String getLabel(LoggerStringKey loggerStringKey){
				return manager.getLoggerMutilangModel().getMutilang().getString(loggerStringKey.getName());
			}

			/**
			 * ����ָ����¼������ format �ַ�����
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args ָ���� format ������
			 * @return ָ���ļ�¼������ format �ַ�����
			 */
			protected String formatLabel(LoggerStringKey loggerStringKey, Object... args){
				return String.format(manager.getLoggerMutilangModel().getMutilang().getString(
						loggerStringKey.getName()),args);
			}

			/**
			 * ���¼��������һ��INFO����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 */
			protected void info(LoggerStringKey loggerStringKey){
				manager.getLoggerModel().getLogger().info(getLabel(loggerStringKey));
			}

			/**
			 * ���¼����formatһ��INFO����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param args format������
			 */
			protected void formatInfo(LoggerStringKey loggerStringKey, Object... args){
				manager.getLoggerModel().getLogger().info(formatLabel(loggerStringKey, args));	
			}

			/**
			 * ���¼��������һ��WARN����Ϣ��
			 * @param loggerStringKey ָ���ļ�¼������
			 * @param throwable ָ���Ŀ��׳�����
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
