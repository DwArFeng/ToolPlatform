package com.dwarfeng.tp.core.model.struct;

import java.util.concurrent.Callable;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ProcessObverser;

public interface Process extends Callable<Object>, ObverserSet<ProcessObverser>, ExternalReadWriteThreadSafe {

	/**
	 * ���ع��̵Ľ��ȡ�
	 * @return ���̵Ľ��ȡ�
	 */
	public int getProgress();

	/**
	 * ���ع��̵��ܽ��ȡ�
	 * @return ���̵��ܽ��ȡ�
	 */
	public int getTotleProgress();

	/**
	 * ���ظù�����ȷ�����̻��ǲ�ȷ�����̡�
	 * @return �ù����Ƿ�Ϊȷ�����̡�
	 */
	public boolean isDeterminate();

	/**
	 * ���ظù����Ƿ�ȡ����
	 * @return �ù����Ƿ�ȡ����
	 */
	public boolean isCancel();
	
	/**
	 * ָʾ�ù����Ƿ��ܱ�ȡ����
	 * @return �ù����ܷ�ȡ����
	 */
	public boolean isCancelable();

	/**
	 * ���ظù����Ƿ���ɡ�
	 * @return �ù����Ƿ���ɡ�
	 */
	public boolean isDone();

	/**
	 * ���ظù��̵���Ϣ��
	 * @return �ù��̵���Ϣ��
	 */
	public String getMessage();
	
	/**
	 * ���ع����еĿ��׳�����
	 * <p> ����ֵΪ <code>null</code>ͨ��������̱��ɹ���ִ����ϣ�
	 * ��Ϊ <code>null</code> ��ͨ����ζ�Ź�����ʲô�ط����������⣬
	 * �����صĿ��׳������������ĸ�Դ��
	 * @return ���ع����еĿ��׳�����
	 */
	public Throwable getThrowable();
	
	

}