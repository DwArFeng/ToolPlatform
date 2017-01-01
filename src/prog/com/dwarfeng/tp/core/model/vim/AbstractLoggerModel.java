package com.dwarfeng.tp.core.model.vim;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import com.dwarfeng.tp.core.model.obv.LoggerObverser;

/**
 * 抽象记录器模型。
 * <p> 记录器模型的抽象实现。
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class AbstractLoggerModel implements LoggerModel {

	/**模型的侦听器集合。*/
	protected Set<LoggerObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<LoggerObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(LoggerObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(LoggerObverser obverser) {
		return obversers.remove(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		obversers.clear();
	}
	
	

}
