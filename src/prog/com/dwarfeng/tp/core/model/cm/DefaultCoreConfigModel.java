package com.dwarfeng.tp.core.model.cm;

import java.util.Locale;

import com.dwarfeng.tp.core.model.cfg.CoreConfig;
import com.dwarfeng.tp.core.util.LocaleUtil;

/**
 * 默认核心配置模型。
 * <p> 核心配置模型的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultCoreConfigModel extends DefaultSyncConfigModel implements CoreConfigModel{
	
	/**
	 * 新实例。
	 */
	public DefaultCoreConfigModel() {
		super(CoreConfig.values());
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.CoreConfigModel#getLoggerMutilangLocale()
	 */
	@Override
	public Locale getLoggerMutilangLocale() {
		lock.readLock().lock();
		try{
			return LocaleUtil.parseLocale(getValidValue(CoreConfig.MUTILANG_LOGGER.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.CoreConfigModel#getLabelMutilangLocale()
	 */
	@Override
	public Locale getLabelMutilangLocale() {
		lock.readLock().lock();
		try{
			return LocaleUtil.parseLocale(getValidValue(CoreConfig.MUTILANG_LABEL.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.CoreConfigModel#isShowSplashScreen()
	 */
	@Override
	public boolean isShowSplashScreen() {
		lock.readLock().lock();
		try{
			return Boolean.parseBoolean(getValidValue(CoreConfig.STARTUP_SPLASH_ISSHOW.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.CoreConfigModel#getStartupSplashDuration()
	 */
	@Override
	public int getStartupSplashDuration() {
		lock.readLock().lock();
		try{
			return Integer.parseInt(getValidValue(CoreConfig.STARTUP_SHPLASH_DURATION.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.CoreConfigModel#isRunningToolAutoTake()
	 */
	@Override
	public boolean isRunningToolAutoTake() {
		lock.readLock().lock();
		try{
			return Boolean.parseBoolean(getValidValue(CoreConfig.RUNNINGTOOL_AUTOTAKE.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.CoreConfigModel#getToolHistoryMaxSize()
	 */
	@Override
	public int getToolHistoryMaxSize() {
		lock.readLock().lock();
		try{
			return Integer.parseInt(getValidValue(CoreConfig.TOOLHISTORY_MAXSIZE.getConfigKey()));
		}finally {
			lock.readLock().unlock();
		}
	}

}
