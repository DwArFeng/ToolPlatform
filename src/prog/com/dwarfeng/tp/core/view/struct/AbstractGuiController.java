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
	 *������ʵ����
	 *<p> ֻ�п�������û��ʵ���ҵ����� {@link #newInstance()} ����ʱ���Ż���ô˷�����
	 * @return ʵ����û�б����ɡ�
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
	 * �ͷ�ʵ����
	 * <p> ֻ�п���������ʵ���ҵ����� {@link #dispose()} ����ʱ���Ż���ô˷�����
	 */
	protected abstract void subDispose();
	
}
