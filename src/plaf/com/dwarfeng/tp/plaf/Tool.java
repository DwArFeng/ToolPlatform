package com.dwarfeng.tp.plaf;

import java.awt.Image;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.Version;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.plaf.core.ToolIconSize;
import com.dwarfeng.tp.plaf.core.ToolObverser;
import com.dwarfeng.tp.plaf.core.ToolStopMode;

/**
 * ���ߡ�
 * <p> �����ǹ��ߵĺ��Ľӿڣ�ʵ�ָýӿڵĹ����ǳ�����ᱻ����һ����Ч�Ĺ��ߡ�
 * <br> ����ʼ���е�ʱ�򣬻��ڹ���Ŀ¼�½����䷢�ֵ�ÿһ��jar����jar����������ิ������������
 * ������ӽ������б��С�
 * <p> ע�⣺��Ҫ��֤�˷�����Զ������ <code>null</code>��
 * @author DwArFeng
 * @since 1.8
 */
public interface Tool extends ObverserSet<ToolObverser>, Name{
	
	/**
	 * ��ȡ��������Ϊͼ���ͼƬ��
	 * <p> ͼƬӦ����С���С����������ͣ����ص�ͼƬ��÷���ָ�����͵Ĵ�С������Ļ���ͼ������ʾ��ʱ�򽫽������ţ�
	 * ���ʧ�档
	 * @param size ͼƬ�Ĵ�Сö�١�
	 * @return ������ָ����С��ͼƬ��
	 */
	public Image getIconImage(ToolIconSize size);
	
	/**
	 * ���ع��ߵ�ͷ��ͼ��
	 * <p> ͷ��ͼ�����ڹ��ߵ���ϸ��Ϣ���ڿ��еı��⴦���������Թ��߽��������������ġ�
	 * ��ͼ����һ�����ͼ�� TODO ����ĳߴ硣
	 * @return ���ߵ�ͷ��ͼ��
	 */
	public Image getHeadImage();
	
	/**
	 * �������ߵ��������顣
	 * <p> ��������ǰ�������ֻ���ʾ�ڹ�����ϸ��Ϣ�Լ�����
	 * @return ����������ɵ����顣
	 */
	public String[] getAuthors();
	
	/**
	 * ��ȡ����İ汾��
	 * @return ����İ汾��
	 */
	public Version getVersion();
	
	/**
	 * ��ָ����ֹͣ��ʽֹͣ����
	 * <p> ���߳����ܹ��Լ�ֹͣ�����û�����ˡ��رա���ť��֮�⣬��Ӧ���ܹ�������ƽֹ̨ͣ��
	 * ����ƽ̨��ֹͣӦ�þ���ʵʱ�ԣ������۳����ں���״̬����Ӧ�ÿ�����Ӧ��ֹͣ������
	 * <p> ����ֹͣ��ʽ�Ĳ�ͬ���÷�����������Ҫ��һЩʱ�����ǿ���ԣ��������жϹ��߲��������˳������۹����Ƿ���ɻ��������Ƿ񱣴档
	 * @param stopMode ֹͣ��ʽ��
	 */
	public void stop(ToolStopMode stopMode);
	
}
