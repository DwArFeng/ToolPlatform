package com.dwarfeng.tp.core.view.ctrl;

import java.awt.Component;
import java.util.Objects;

import com.dwarfeng.tp.core.model.struct.Mutilang;
import com.dwarfeng.tp.core.model.struct.MutilangSupported;

/**
 * ���������֧��ͼ�ν��������������
 * <p> ������֧��ͼ�ν�������������ĳ���ʵ�֡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractMusuedGuiController<T extends Component & MutilangSupported> 
extends AbstractGuiController<T> implements MutilangSupportedGuiController<T> {

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return null;
			return component.getMutilang();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangSupported#setMutilang(com.dwarfeng.tp.core.model.struct.Mutilang)
	 */
	@Override
	public boolean setMutilang(Mutilang mutilang) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			return component.setMutilang(mutilang);
		}finally {
			lock.writeLock().unlock();
		}
	}

}
