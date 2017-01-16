package com.dwarfeng.tp.core.view.struct;

import java.util.Objects;

import com.dwarfeng.tp.core.view.gui.SplashScreen;

/**
 * 默认启动窗口控制器。
 * @author DwArFeng
 * @since 1.8
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
