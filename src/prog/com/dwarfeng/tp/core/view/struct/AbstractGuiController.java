package com.dwarfeng.tp.core.view.struct;

import java.awt.Component;
import java.util.Objects;

public abstract class AbstractGuiController<T extends Component> implements GuiController<T>{

	protected T component = null;

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#hasInstance()
	 */
	@Override
	public boolean hasInstance() {
		return Objects.nonNull(null);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#getInstance()
	 */
	@Override
	public T getInstance() {
		return component;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#getVisible()
	 */
	@Override
	public boolean isVisible() {
		if(! hasInstance()) return false;
		return component.isVisible();
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#setVisible(boolean)
	 */
	@Override
	public boolean setVisible(boolean aFlag) {
		if(! hasInstance()) return false;
		component.setVisible(aFlag);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#show()
	 */
	@Override
	public void show() {
		if(! hasInstance()){
			newInstance();
		}
		setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#newInstance()
	 */
	@Override
	public boolean newInstance() {
		if(hasInstance()) return false;
		component =  subNewInstance();
		return component != null;
	}

	/**
	 *生成新实例。
	 *<p> 只有控制器中没有实例且调用了 {@link #newInstance()} 方法时，才会调用此方法。
	 * @return 实例有没有被生成。
	 */
	protected abstract T subNewInstance();

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.view.struct.GuiController#dispose()
	 */
	@Override
	public boolean dispose() {
		if(!hasInstance()) return false;
		subDispose();
		this.component = null;
		return true;
	}

	/**
	 * 释放实例。
	 * <p> 只有控制器中有实例且调用了 {@link #dispose()} 方法时，才会调用此方法。
	 */
	protected abstract void subDispose();
	
}
