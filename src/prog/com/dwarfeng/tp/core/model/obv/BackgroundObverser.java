package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Process;

public interface BackgroundObverser extends Obverser {
	
	/**
	 * 通知指定的过程对象的进度发生改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 进度的旧值。
	 * @param newValue 进度的新值。
	 */
	public void fireProcessProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * 通知指定的过程对象的总进度发生改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 总进度的旧值。
	 * @param newValue 总进度的新值。
	 */
	public void fireProcessTotleProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * 通知指定的过程对象的确定性改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 旧的确定性。
	 * @param newValue 新的确定性。
	 */
	public void fireProcessDeterminateChanged(Process process, boolean oldValue, boolean newValue);
	
	/**
	 * 通知指定的过程对象的消息发生了改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 旧的消息。
	 * @param newValue 新的消息。
	 */
	public void fireProcessMessageChanged(Process process, String oldValue, String newValue);
	
	/**
	 * 通知指定的过程对象的可抛出对象发生了改变。
	 * @param process 发生了改变的过程对象。
	 * @param oldValue 旧的可抛出对象。
	 * @param newValue 新的可抛出对象。
	 */
	public void fireProcessThrowableChanged(Process process, Throwable oldValue, Throwable newValue);
	
	/**
	 * 通知指定的过程对象被取消。
	 * @param process 指定的过程对象。
	 */
	public void fireProcessCanceled(Process process);

	/**
	 * 通知指定的过程对象完成。
	 * @param process 指定的过程对象。
	 */
	public void fireProcessDone(Process process);
	
	/**
	 * 通知指定的过程对象被添加。
	 * @param process 指定的过程对象。
	 */
	public void fireProcessAdded(Process process);
	
	/**
	 * 通知指定的过程对象被移除。
	 * @param process 指定的过程对象。
	 */
	public void fireProcessRemoved(Process process);
	
	
}
