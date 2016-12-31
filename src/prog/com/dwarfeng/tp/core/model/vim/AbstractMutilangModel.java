package com.dwarfeng.tp.core.model.vim;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

import com.dwarfeng.tp.core.model.obv.MutilangObverser;

/**
 * ���������ģ�͡�
 * <p> ������ģ�͵ĳ���ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public abstract class AbstractMutilangModel implements MutilangModel {

	/**���й۲����ļ���*/
	protected final Set<MutilangObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<MutilangObverser> getObversers() {
		return Collections.unmodifiableSet(obversers);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(MutilangObverser obverser) {
		return obversers.add(obverser);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(MutilangObverser obverser) {
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
