package com.dwarfeng.tp.core.model.struct;

import java.util.concurrent.Callable;

import com.dwarfeng.dutil.basic.prog.ObverserSet;
import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.obv.ProcessObverser;

public interface Process extends Callable<Object>, ObverserSet<ProcessObverser>, ExternalReadWriteThreadSafe {

	/**
	 * 返回过程的进度。
	 * @return 过程的进度。
	 */
	public int getProgress();

	/**
	 * 返回过程的总进度。
	 * @return 过程的总进度。
	 */
	public int getTotleProgress();

	/**
	 * 返回该过程是确定过程还是不确定过程。
	 * @return 该过程是否为确定过程。
	 */
	public boolean isDeterminate();

	/**
	 * 返回该过程是否被取消。
	 * @return 该过程是否被取消。
	 */
	public boolean isCancel();

	/**
	 * 返回该过程是否完成。
	 * @return 该过程是否完成。
	 */
	public boolean isDone();

	/**
	 * 返回该过程的消息。
	 * @return 该过程的消息。
	 */
	public String getMessage();

}