package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.dutil.basic.prog.Obverser;
import com.dwarfeng.tp.core.model.struct.Process;

/**
 * 过程观察器。
 * @author  DwArFeng
 * @since 0.0.0-alpha
 */
public interface ProcessObverser extends Obverser{
	
	/**
	 * 通知指定的过程对象的进度发生改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 进度的旧值。
	 * @param newValue 进度的新值。
	 */
	public void fireProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * 通知指定的过程对象的总进度发生改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 总进度的旧值。
	 * @param newValue 总进度的新值。
	 */
	public void fireTotleProgressChanged(Process process, int oldValue, int newValue);
	
	/**
	 * 通知指定的过程对象的确定性改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 旧的确定性。
	 * @param newValue 新的确定性。
	 */
	public void fireDeterminateChanged(Process process, boolean oldValue, boolean newValue);
	
	/**
	 * 通知指定的过程对象的消息发生了改变。
	 * @param process 发生改变的过程对象。
	 * @param oldValue 旧的消息。
	 * @param newValue 新的消息。
	 */
	public void fireMessageChanged(Process process, String oldValue, String newValue);
	
	/**
	 * 通知指定的过程对象的可抛出对象发生了改变。
	 * @param process 发生了改变的过程对象。
	 * @param oldValue 旧的可抛出对象。
	 * @param newValue 新的可抛出对象。
	 */
	public void fireThrowableChanged(Process process, Throwable oldValue, Throwable newValue);
	
	/**
	 * 通知指定的过程对象可取消性发生了改变。
	 * @param process 发生了改变的过程对象。
	 * @param oldValue 旧的可取消性。
	 * @param newValue 新的可取消性。
	 */
	public void fireCancelableChanged(Process process, boolean oldValue, boolean newValue);

	/**
	 * 通知指定的过程对象被取消。
	 * @param process 指定的过程对象。
	 */
	public void fireCanceled(Process process);

	/**
	 * 通知指定的过程对象完成。
	 * @param process 指定的过程对象。
	 */
	public void fireDone(Process process);
	
}
