package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.Process;

/**
 * 过程模型观察器适配器。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class ProcessAdapter implements ProcessObverser{

	@Override
	public void fireProgressChanged(Process process, int oldValue, int newValue) {}
	@Override
	public void fireTotleProgressChanged(Process process, int oldValue, int newValue) {}
	@Override
	public void fireDeterminateChanged(Process process, boolean oldValue, boolean newValue) {}
	@Override
	public void fireMessageChanged(Process process, String oldValue, String newValue) {}
	@Override
	public void fireThrowableChanged(Process process, Throwable oldValue, Throwable newValue) {}
	@Override
	public void fireCancelableChanged(Process process, boolean oldValue, boolean newValue) {}
	@Override
	public void fireCanceled(Process process) {}
	@Override
	public void fireDone(Process process) {}
	
}
