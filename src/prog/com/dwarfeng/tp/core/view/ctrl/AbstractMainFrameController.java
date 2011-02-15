package com.dwarfeng.tp.core.view.ctrl;

import java.awt.Component;
import java.util.Objects;

import com.dwarfeng.tp.core.model.struct.RunningTool;
import com.dwarfeng.tp.core.view.gui.MainFrame;

/**
 * 抽象主界面控制器。
 * <p> 主界面控制器的抽象实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractMainFrameController extends AbstractMusuedGuiController<MainFrame> implements MainFrameController {

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#getHeight()
	 */
	@Override
	public int getLastNormalHeight() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return -1;
			return component.getLastNormalHeight();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#getWidth()
	 */
	@Override
	public int getLastNormalWidth() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return -1;
			return component.getLastNormalWidth();
		}finally {
			lock.readLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setHeight(int)
	 */
	@Override
	public boolean setLastNormalHeight(int height) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			return component.setLastNormalHeight(height);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setWidth(int)
	 */
	@Override
	public boolean setLastNormalWidth(int width) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			return component.setLastNormalWidth(width);
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

	/* (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setLocationRelativeTo(java.awt.Component)
	 */
	@Override
	public boolean setLocationRelativeTo(Component component) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(this.component)) return false;
			this.component.setLocationRelativeTo(component);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#assignStream(com.dwarfeng.tp.core.model.struct.RunningTool)
	 */
	@Override
	public boolean assignStream(RunningTool runningTool) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(this.component)) return false;
			return this.component.assignStream(runningTool);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#getSouthHeight()
	 */
	@Override
	public int getSouthHeight() {
		lock.readLock().lock();
		try{
			if(Objects.isNull(component)) return -1;
			return component.getSouthHeight();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 *  (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.MainFrameController#setSouthHeight(int)
	 */
	@Override
	public boolean setSouthHeight(int height) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(this.component)) return false;
			return this.component.setSouthHeight(height);
		}finally {
			lock.writeLock().unlock();
		}
	}
	
}
