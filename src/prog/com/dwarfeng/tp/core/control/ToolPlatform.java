package com.dwarfeng.tp.core.control;

import java.io.IOException;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.dutil.basic.prog.DefaultVersion;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.prog.VersionType;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;
import com.dwarfeng.dutil.develop.cfg.DefaultConfigModel;
import com.dwarfeng.dutil.develop.cfg.io.ConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.PropertiesConfigLoader;
import com.dwarfeng.dutil.develop.cfg.io.StreamConfigLoader;
import com.dwarfeng.tp.core.control.proc.ActionProcessor;
import com.dwarfeng.tp.core.model.ModelManager;
import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.model.cfg.InvisibleConfig;
import com.dwarfeng.tp.core.model.cfg.LoggerStringKey;
import com.dwarfeng.tp.core.model.cfg.PathKey;
import com.dwarfeng.tp.core.model.io.StreamLoggerLoader;
import com.dwarfeng.tp.core.model.io.StreamMutilangLoader;
import com.dwarfeng.tp.core.model.io.StreamResourceLoader;
import com.dwarfeng.tp.core.model.io.XmlLoggerLoader;
import com.dwarfeng.tp.core.model.io.XmlMutilangLoader;
import com.dwarfeng.tp.core.model.io.XmlResourceLoader;
import com.dwarfeng.tp.core.model.struct.CoreConfigProvider;
import com.dwarfeng.tp.core.model.struct.DefaultCoreConfigProvider;
import com.dwarfeng.tp.core.model.struct.DefaultInvisibleConfigProvider;
import com.dwarfeng.tp.core.model.struct.DefaultLoggerProvider;
import com.dwarfeng.tp.core.model.struct.InvisibleConfigProvider;
import com.dwarfeng.tp.core.model.struct.Logger;
import com.dwarfeng.tp.core.model.struct.LoggerProvider;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangProvider;
import com.dwarfeng.tp.core.model.struct.ProcessException;
import com.dwarfeng.tp.core.model.struct.Resource;
import com.dwarfeng.tp.core.model.vim.DefaultLoggerModel;
import com.dwarfeng.tp.core.model.vim.DefaultMutilangModel;
import com.dwarfeng.tp.core.model.vim.DefaultResourceModel;
import com.dwarfeng.tp.core.model.vim.LoggerModel;
import com.dwarfeng.tp.core.model.vim.MutilangModel;
import com.dwarfeng.tp.core.model.vim.ResourceModel;
import com.dwarfeng.tp.core.util.ToolPlatformUtil;
import com.dwarfeng.tp.core.util.ViewUtil;
import com.dwarfeng.tp.core.view.ViewManager;
import com.dwarfeng.tp.core.view.ctrl.SplashScreenController;

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
	
	
	private final ActionProcessor actionProcessor = new ActionProcessor() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.control.proc.ActionProcessor#start()
		 */
		@Override
		public void start() throws ProcessException {
			if(! status.equals(Status.NOT_START))  throw new IllegalStateException("�����Ѿ����������Ѿ�����");
			
			//�������
			Resource resource = null;
			Mutilang usingMutilang = ToolPlatformUtil.newInitialLoggerMutilang();
			Logger usingLogger = null;
			SplashScreenController usingSplashScreenController = null;
			TimeMeasurer usingTimeMeasurer = null;
			
			
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
				
				
				//���س����еļ�¼��ģ�͡�
				LoggerModel loggerModel = new DefaultLoggerModel();
				resource = resourceModel.get(PathKey.LOGGER_SETTING);
				StreamLoggerLoader loggerLoader = null;
				try{
					loggerLoader = new XmlLoggerLoader(resource.openInputStream());
					loggerLoader.load(loggerModel);
				}catch (IOException e) {
					resource.reset();
					loggerLoader = new XmlLoggerLoader(resource.openInputStream());
					loggerLoader.load(loggerModel);
				}finally {
					if(Objects.nonNull(loggerLoader)){
						loggerLoader.close();
					}
				}
				LoggerProvider loggerProvider = new DefaultLoggerProvider(loggerModel);
				try{
					loggerProvider.update();
				}catch(ProcessException e){
					//TODO �Ժ�Ѱ�Ҹ��õĽ����ʽ��
					loggerProvider.update2Default();
				}
				usingLogger = loggerProvider.getLogger();
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_1));
				
				//���س���ĺ������á�
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_2));
				ConfigModel coreConfigModel = new DefaultConfigModel(CoreConfig.values());
				resource = resourceModel.get(PathKey.CONFIGURATION_CORE);
				StreamConfigLoader coreConfigLoader = null;
				try{
					coreConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
					coreConfigLoader.loadConfig(coreConfigModel);
				}catch (IOException e) {
					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
					resource.reset();
					coreConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
					coreConfigLoader.loadConfig(coreConfigModel);
				}finally {
					if(Objects.nonNull(coreConfigLoader)){
						coreConfigLoader.close();
					}
				}
				CoreConfigProvider coreConfigProvider = new DefaultCoreConfigProvider(coreConfigModel);
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_4));
				
				//���ؼ�¼�����������á�
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_5));
				MutilangModel loggerMutilangModel = new DefaultMutilangModel();
				resource = resourceModel.get(PathKey.MUTILANG_LOGGER_SETTING);
				StreamMutilangLoader loggerMutilangLoader = null;
				try{
					loggerMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
					loggerMutilangLoader.load(loggerMutilangModel);
				}catch (IOException e) {
					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
					resource.reset();
					loggerMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
					loggerMutilangLoader.load(loggerMutilangModel);
				}finally {
					if(Objects.nonNull(loggerMutilangLoader)){
						loggerMutilangLoader.close();
					}
				}
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_6));
				MutilangProvider loggerMutilangProvider = ToolPlatformUtil.newLoggerMutilangProvider(loggerMutilangModel);
				try{
					loggerMutilangProvider.update(coreConfigProvider.getLoggerMutilangLocale());
					usingMutilang = loggerMutilangProvider.getMutilang();
				}catch (ProcessException e) {
					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_7), e);
					loggerMutilangProvider.update2Default();
					usingMutilang = loggerMutilangProvider.getMutilang();
				}
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_8));
				
				//�ж��Ƿ�Ҫ�������ִ��塣
				if(coreConfigProvider.isShowSplashScreen()){
					usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_9));
					usingSplashScreenController = ViewUtil.newSplashScreenController();
					usingTimeMeasurer = new TimeMeasurer();
					usingTimeMeasurer.start();
				}
				
				//���ر�ǩ���������á�
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_10));
				if(Objects.nonNull(usingSplashScreenController)){
					usingSplashScreenController.setText(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_10));
				}
				MutilangModel labelMutilangModel = new DefaultMutilangModel();
				resource = resourceModel.get(PathKey.MUTILANG_LABEL_SETTING);
				StreamMutilangLoader labelMutilangLoader = null;
				try{
					labelMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
					labelMutilangLoader.load(labelMutilangModel);
				}catch(IOException e){
					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
					resource.reset();
					labelMutilangLoader = new XmlMutilangLoader(resource.openInputStream());
					labelMutilangLoader.load(labelMutilangModel);
				}finally{
					if(Objects.nonNull(labelMutilangLoader)){
						labelMutilangLoader.close();
					}
				}
				MutilangProvider labelMutilangProvider = ToolPlatformUtil.newLabelMutilangProvider(labelMutilangModel);
				try{
					labelMutilangProvider.update(coreConfigProvider.getLabelMutilangLocale());
				}catch (ProcessException e) {
					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_11), e);
					labelMutilangProvider.update2Default();
				}
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_12));
				
				//���ز��ɼ�ģ��
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_13));
				if(Objects.nonNull(usingSplashScreenController)){
					usingSplashScreenController.setText(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_13));
				}
				ConfigModel invisibleConfigModel = new DefaultConfigModel(InvisibleConfig.values());
				resource = resourceModel.get(PathKey.CONFIGURATION_INVISIBLE);
				StreamConfigLoader invisibleConfigLoader = null;
				try{
					invisibleConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
					invisibleConfigLoader.loadConfig(invisibleConfigModel);
				}catch (IOException e) {
					usingLogger.warn(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_3), e);
					resource.reset();
					invisibleConfigLoader = new PropertiesConfigLoader(resource.openInputStream());
					invisibleConfigLoader.loadConfig(invisibleConfigModel);
				}finally{
					if(Objects.nonNull(invisibleConfigLoader)){
						invisibleConfigLoader.close();
					}
				}
				InvisibleConfigProvider invisibleConfigProvider = new DefaultInvisibleConfigProvider(invisibleConfigModel);
				usingLogger.info(usingMutilang.getString(LoggerStringKey.ActionProcessor_start_14));
				
				
				
				
				
				
				
				
				//����ģ�͹�������
				ModelManager modelManager = new ModelManager(
						resourceModel, 
						coreConfigModel, 
						invisibleConfigModel, 
						loggerMutilangModel,
						labelMutilangModel,
						loggerMutilangProvider, 
						labelMutilangProvider, 
						loggerModel, 
						loggerProvider);
				ToolPlatform.this.modelManager = modelManager;
				
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*
				 * TODO �������������д����ơ�
				 * 	���س���ĺ�������					��
				 * ���ؼ�¼������������				��
				 * Ӧ�ü�¼������������				��
				 * �������ִ���								��
				 * ���ر�ǩ������ģ��					��
				 * ���ز��ɼ�����ģ��					��
				 * 
				 */
				
			}catch (IOException | LoadFailedException e) {
				if(Objects.nonNull(usingLogger) && Objects.nonNull(usingMutilang)){
					//TODO �޷���ɳ�ʼ����
				}
				throw new ProcessException("��ʼ�����ɹ�", e.getCause());
			}finally{
				if(Objects.nonNull(usingSplashScreenController)){
					usingSplashScreenController.dispose();
				}
			}
			
		}
		
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**���������õ�ģ�͹�����*/
	private ModelManager modelManager;
	/**���������õ���ͼ������*/
	private ViewManager viewManager;
	/**�����״̬*/
	private Status status;

	
	
	/**
	 * ����һ��Ĭ�ϵĹ���ƽ̨ʵ����
	 * ����һ������ָ�� TODO
	 */
	public ToolPlatform() {
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
	
}
