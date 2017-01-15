package com.dwarfeng.tp.core.model.obv;

import com.dwarfeng.tp.core.model.struct.Process;

/**
 * 后台模型观察器适配器。
 * @author DwArFeng
 * @since 1.8
 */
public abstract class BackgroundAdapter implements BackgroundObverser {

	@Override
	public void fireProcessProgressChanged(Process process, int oldValue, int newValue) {}
	@Override
	public void fireProcessTotleProgressChanged(Process process, int oldValue, int newValue) {}
	@Override
	public void fireProcessDeterminateChanged(Process process, boolean oldValue, boolean newValue) {}
	@Override
	public void fireProcessMessageChanged(Process process, String oldValue, String newValue) {
	}@Override
	public void fireProcessThrowableChanged(Process process, Throwable oldValue, Throwable newValue) {}
	@Override
	public void fireProcessCancelableChanged(Process process, boolean oldValue, boolean newValue) {}
	@Override
	public void fireProcessCanceled(Process process) {}
	@Override
	public void fireProcessDone(Process process) {}
	@Override
	public void fireProcessAdded(Process process) {}
	@Override
	public void fireProcessRemoved(Process process) {}
	
}
