package com.dwarfeng.tp.core.view.struct;

import java.util.Objects;

import com.dwarfeng.tp.core.view.gui.MainFrame;

/**
 * �����������������
 * <p> ������������ĳ���ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public abstract class AbstractMainFrameController extends AbstractMusuedGuiController<MainFrame> implements MainFrameController {

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#getHeight()
	 */
	@Override
	public int getHeight() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return -1;
			return component.getHeight();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#getWidth()
	 */
	@Override
	public int getWidth() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return -1;
			return component.getWidth();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setHeight(int)
	 */
	@Override
	public boolean setHeight(int height) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			component.setSize(getWidth(), height);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setWidth(int)
	 */
	@Override
	public boolean setWidth(int width) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			component.setSize(width, getHeight());
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#getExtendedState()
	 */
	@Override
	public int getExtendedState() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return -1;
			return component.getExtendedState();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setExtendedState(int)
	 */
	@Override
	public boolean setExtendedState(int state) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			component.setExtendedState(state);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}