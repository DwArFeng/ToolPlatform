package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.RunningToolObverser;

/**
 * �����й��ߡ�
 * <p> �����ʾһ���������еĹ��ߣ��ṩ�йصķ�����
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface RunningTool extends Name, ObverserSet<RunningToolObverser>{
	
	/**
	 * ���ع����е�ͼƬ��
	 * @return �����е�ͼƬ��
	 */
	public Image getImage();
	
	/**
	 * ��ȡ����ת�ӹ������������������
	 * @return ����ת�ӹ������������������
	 */
	public InputStream getInputStream();
	
	/**
	 * ��������ת�ӹ������������������
	 * <p> �����й��������е�ʱ�򣬻᲻�ϵĽ�������е�������������ߵ�������У��ù����ǲ����ġ�
	 * <p> �����������������ʽ�ģ������ϵͳ��Դ��ɾ޴�ĸ�����
	 * <p> ��ڲ�������Ϊ <code>null</code>������������й��߽��������ߵ�������е��������ݡ�
	 * @param in ָ�����������
	 * @return �Ƿ����óɹ�
	 */
	public boolean setInputStream(InputStream in);
	
	/**
	 * ��ȡ����ת�ӹ������������������
	 * @return ����ת�ӹ������������������
	 */
	public OutputStream getOutputStream();
	
	/**
	 * ��������ת�ӹ������������������
	 * <p> �����й��������е�ʱ�򣬻᲻�ϵĽ����ߵ�����������ݷ��͵���������У��ù����ǲ����ġ�
	 * <p> ��ڲ�������Ϊ <code>null</code>������������й��߽��������ߵ��������е��������ݡ�
	 * @param out ָ�����������
	 * @return �Ƿ����óɹ�
	 */
	public boolean setOutputStream(PrintStream out);
	
	/**
	 * ��������
	 * <p> �÷������������������й��ߵ�������������������ø÷���֮���ٴε���
	 * {@link RunningTool#setInputStream(InputStream)} �� {@link RunningTool#setOutputStream(OutputStream)} ������ <code>false</code>��
	 * <p> ֻ�е���������֮�󣬹��߲ŻῪʼ���С�
	 */
	public void lockStream();
	
	/**
	 * �����������й��ߡ�
	 * <p> �÷��������� {@link RunningTool#lockStream()} ֮ǰ������ɵ����̵߳�������
	 * �������Ĺ����У�����̱߳��жϣ��������������ߡ�
	 * @throws InterruptedException �����߳�������ʱ���жϡ�
	 * @throws IllegalStateException �ڳ����Ѿ�������������ٴε��ø÷�����
	 */
	public void start() throws InterruptedException;
	
	/**
	 * ǿ����ֹ�������й��ߡ�
	 * <p> ���ø÷���������ù��߻�δ�������򹤾���Զ�������������ص��˳�ֵ�� <code>-12450</code>��
	 * <br> ���ø÷���������ù������ڵȴ�������������ȴ��������׳� {@link InterruptedException}�����ҽ�����������Զ����������
	 * ���ص��˳�ֵ�� <code>-12451</code>��
	 */
	public void destroy();
	
	/**
	 * ��ȡ�ù��ߵ�����ʱ״̬��
	 * @return �ù��ߵ�����ʱ״̬��
	 */
	public RuntimeState getRuntimeState();
	
	/**
	 * ��ȡ�������й��ߵĹ���Ŀ¼��
	 * @return �������й��ߵĹ���Ŀ¼��
	 */
	public File getDirectory();
	
	/**
	 * ���ظ������й��ߵ��˳����롣
	 * <p> ����ĳЩʵ�֣���ʹ���ߴ�������״̬�£����ø÷�����Ȼ�ܹ�����ֵ���������������������塣
	 * @return �ù��ߵ��˳����롣
	 */
	public int getExitCode();
	
	/**
	 * ��ȡ���߿�ʼ���е����ڡ�
	 * @return ���߿�ʼ���е����ڡ�
	 */
	public Date getRanDate();
	
	/**
	 * ��ȡ���߽������е����ڡ�
	 * @return ���߽������е����ڡ�
	 */
	public Date getExitedDate();
	
}
