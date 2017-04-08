package com.dwarfeng.tp.core.view.ctrl;

import java.util.Objects;

import com.dwarfeng.tp.core.view.gui.SplashScreen;

/**
 * 默认启动窗口控制器。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public abstract class AbstractSplashScreenController extends AbstractGuiController<SplashScreen> implements SplashScreenController{
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.SplashScreenController#setMessage(java.lang.String)
	 */
	@Override
	public boolean setMessage(String text) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(component)) return false;
			return this.component.setMessage(text);
		}finally {
			lock.writeLock().unlock();
		}
	}

}
