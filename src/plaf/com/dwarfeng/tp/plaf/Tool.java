package com.dwarfeng.tp.plaf;

import java.awt.Image;
import java.util.Locale;
import java.util.Map;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.struct.ToolImageType;
import com.dwarfeng.tp.plaf.core.FileManager;
import com.dwarfeng.tp.plaf.core.ToolObverser;
import com.dwarfeng.tp.plaf.core.ToolStopMode;

/**
 * ���ߡ�
 * <p> �����ǹ��ߵĺ��Ľӿڣ�ʵ�ָýӿڵĹ����ǳ�����ᱻ����һ����Ч�Ĺ��ߡ�
 * <br> ����ʼ���е�ʱ�򣬻��ڹ���Ŀ¼�½����䷢�ֵ�ÿһ��jar����jar����������ิ������������
 * ������ӽ������б��С�
 * <p> ע�⣺��Ҫ��֤�˷�����Զ������ <code>null</code>��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface Tool extends ObverserSet<ToolObverser>, Name{
	
	/**
	 * ���ع�����ָ�����͵�ͼƬ��
	 * @param type ͼƬ���͡�
	 * @return ������ָ�����͵�ͼƬ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public Image getImage(ToolImageType type);
	
	/**
	 * ��ȡ����İ汾��
	 * @return ����İ汾��
	 */
	public Version getVersion();
	
	/**
	 * TODO �Ƿ�Ҫ�� Doucument ����ʽ��������������
	 * ��ȡ���ߵ�������
	 * <p> ���ص�ӳ����Ӧ����һ����Ϊ <code>null</code>����ڣ������ڴ�����Ĭ�ϵ�������
	 * @param ָ�������ԣ�����Ϊ <code>null</code>������Ĭ�����ԡ�
	 * @return ָ���������¹��ߵ�������
	 */
	public Map<Locale, String> getDescriptions();
	
	/**
	 * ��ȡ���ߵ�Ĭ��������
	 * @return ���ߵ�Ĭ��������
	 */
	public String getDefaultDescription();
	
	/**
	 * ��ȡ���ߵ��������顣
	 * <p> ����Ӧ�ð��չ��׳̶ȵĴ�С������Ϊ���ص����������У�
	 * ��ǰ�����߸��п�����ʾ�ڸ�Ҫ�����ϡ�
	 * @return ���ߵ��������顣
	 */
	public String[] getAuthors();
	
	/**
	 * ��ȡ���ߵĿ��б�
	 * <p> ���ص��ַ���Ӧ��������Ҫʹ�õĿ��ļ����ļ��������ɴ�·����
	 * @return ���ߵĿ��б�
	 */
	public String[] getToolLibs();
	
	/**
	 * ��ָ����ֹͣ��ʽֹͣ����
	 * <p> ���߳����ܹ��Լ�ֹͣ�����û�����ˡ��رա���ť��֮�⣬��Ӧ���ܹ�������ƽֹ̨ͣ��
	 * ����ƽ̨��ֹͣӦ�þ���ʵʱ�ԣ������۳����ں���״̬����Ӧ�ÿ�����Ӧ��ֹͣ������
	 * <p> ����ֹͣ��ʽ�Ĳ�ͬ���÷�����������Ҫ��һЩʱ�����ǿ���ԣ��������жϹ��߲��������˳������۹����Ƿ���ɻ��������Ƿ񱣴档
	 * @param stopMode ֹͣ��ʽ��
	 */
	public void stop(ToolStopMode stopMode);
	
	/**
	 * �������ߡ�
	 * @param fileManager ָ�����ļ���������
	 * 
	 */
	public void start(FileManager fileManager);
	
}
