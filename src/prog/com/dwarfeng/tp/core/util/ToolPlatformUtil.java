package com.dwarfeng.tp.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import com.dwarfeng.tp.core.control.ToolPlatform;
import com.dwarfeng.tp.core.model.struct.Logger;
import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.ProcessException;

/**
 * ���ڹ���ƽ̨�Ĺ����ࡣ
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public final class ToolPlatformUtil {
	
	/**
	 * ��ȡһ���µ��赲�ֵ�ָʾ����������
	 * @return �µ��赲�ֵ�ָʾ����������
	 */
	public final static InputStream newBlockDictionary(){
		return ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/block_dictionary.xml");
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�������ġ�
	 * @return Ĭ�ϵļ�¼�������ġ�
	 */
	public final static LoggerContext newDefaultLoggerContext(){
		try {
			ConfigurationSource cs = new ConfigurationSource(ToolPlatform.class.getResourceAsStream("/com/dwarfeng/tp/resource/defaultres/logger/setting.xml"));
			return Configurator.initialize(null, cs);
		} catch (IOException e) {
			e.printStackTrace();
			return (LoggerContext) LogManager.getContext();
		}
	}
	
	/**
	 * ��ȡĬ�ϼ�¼���ӿڡ�
	 * <p> �ü�¼���������κβ�����
	 * @return �µĳ�ʼ����¼���ӿڡ�
	 */
	public final static Logger newDefaultLogger(){
		return new InitialLogger();
	}
	
	/**
	 * ��ȡĬ�ϵļ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @return �µ�Ĭ�ϼ�¼�������Խӿڡ�
	 */
	public final static Mutilang newDefaultLoggerMutilang(){
		return new DefaultLoggerMutilang();
	}
	
	/**
	 * ��ȡĬ�ϵı�ǩ�����Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @return �µ�Ĭ�ϱ�ǩ�����Խӿڡ�
	 */
	public final static Mutilang newDefaultLabelMutilang(){
		return new DefaultLabelMutilang();
	}
	
	/**
	 * ���¼�����������һ��ָ���Ŀ����ж���
	 * @param runnable ָ���Ŀ����ж���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public final static void invokeInEventQueue(Runnable runnable){
		Objects.requireNonNull(runnable, "��ڲ��� runnable ����Ϊ null��");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeLater(runnable);
		}
	}
	
	/**
	 * ��ʵ������������һ��ָ���Ŀ����ж���
	 * <p> ��ָ���Ŀ����ж������н���֮ǰ����ǰ�߳̽���������״̬��
	 * @param runnable ָ���Ŀ����ж���
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 * @throws InvocationTargetException <code>runnable</code>	����ʱ�׳��쳣��
	 * @throws InterruptedException ����ȴ��¼�ָ���߳�ִ���ִ�� <code>runnable.run()</code>ʱ���ж� 
	 */
	public final static void invokeAndWaitInEventQueue(Runnable runnable) throws InvocationTargetException, InterruptedException{
		Objects.requireNonNull(runnable, "��ڲ��� runnable ����Ϊ null��");
		
		if(SwingUtilities.isEventDispatchThread()){
			runnable.run();
		}else{
			SwingUtilities.invokeAndWait(runnable);
		}
	}
	
	
	

	/**
	 * Ĭ�ϼ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class DefaultLoggerMutilang implements Mutilang {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			try {
				if(! Constants.getDefaultLoggerMutilangInfo().getMutilangMap().containsKey(key)){
					throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
				}
				return Constants.getDefaultLoggerMutilangInfo().getMutilangMap().getOrDefault(key, Constants.getDefaultMissingString());
			} catch (ProcessException ignore) {
				//�����׳��쳣
				return Constants.getDefaultMissingString();
			}
		}
		
	}
	
	/**
	 * Ĭ�ϼ�¼�������Խӿڡ�
	 * <p> ʹ�ó��������õļ������ġ�
	 * @author DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class DefaultLabelMutilang implements Mutilang{

		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(java.lang.String)
		 */
		@Override
		public String getString(String key) {
			try {
				if(! Constants.getDefaultLabelMutilangInfo().getMutilangMap().containsKey(key)){
					throw new IllegalArgumentException("�˶����Խӿڲ�֧�ָü�");
				}
				return Constants.getDefaultLabelMutilangInfo().getMutilangMap().getOrDefault(key, Constants.getDefaultMissingString());
			} catch (ProcessException ignore) {
				//�����׳��쳣
				return Constants.getDefaultMissingString();
			}
		}
		
	}
	
	

	/**
	 * Ĭ�ϼ�¼���ӿڡ�
	 * <p> �ü�¼���������κβ�����
	 * @author  DwArFeng
	 * @since 0.0.0-alpha
	 */
	private static final class InitialLogger implements Logger{

		@Override
		public void trace(String message) {}
		@Override
		public void debug(String message) {}
		@Override
		public void info(String message) {}
		@Override
		public void warn(String message) {}
		@Override
		public void warn(String message, Throwable t) {}
		@Override
		public void error(String message, Throwable t) {}
		@Override
		public void fatal(String message, Throwable t) {}
		
	}

	//��ֹ�ⲿʵ����
	private ToolPlatformUtil(){}

}