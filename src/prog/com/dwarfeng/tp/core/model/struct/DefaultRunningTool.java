package com.dwarfeng.tp.core.model.struct;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.dwarfeng.dutil.basic.prog.RuntimeState;
import com.dwarfeng.tp.core.model.obv.RunningToolObverser;

/**
 * 默认运行中工具。
 * <p> 运行中工具的默认实现。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultRunningTool implements RunningTool{
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Condition startCondition = lock.writeLock().newCondition();

	private final String name;
	private final String[] libraries;
	private final String jarPath;
	private final String entryClass;
	
	private final Set<RunningToolObverser> obversers = Collections.newSetFromMap(new WeakHashMap<>());
	
	private RuntimeState runtimeState = RuntimeState.NOT_START;
	private InputStream in = null;
	private OutputStream out = null;
	private boolean streamLockFlag = false;
	
	private Process process = null;
	
	/**
	 * 
	 * @param name
	 * @param libraries
	 * @param jarPath
	 * @param entryClass
	 */
	public DefaultRunningTool(String name, String[] libraries, String jarPath, String entryClass) {
		Objects.requireNonNull(name, "入口参数 name 不能为 null。");
		Objects.requireNonNull(libraries, "入口参数 libraries 不能为 null。");
		Objects.requireNonNull(jarPath, "入口参数 jarPath 不能为 null。");
		Objects.requireNonNull(entryClass, "入口参数 entryClass 不能为 null。");

		this.name = name;
		this.libraries = libraries;
		this.jarPath = jarPath;
		this.entryClass = entryClass;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		lock.readLock().lock();
		try{
			return name;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#getObversers()
	 */
	@Override
	public Set<RunningToolObverser> getObversers() {
		lock.readLock().lock();
		try{
			return Collections.unmodifiableSet(obversers);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#addObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean addObverser(RunningToolObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.add(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#removeObverser(com.dwarfeng.dutil.basic.prog.Obverser)
	 */
	@Override
	public boolean removeObverser(RunningToolObverser obverser) {
		lock.writeLock().lock();
		try{
			return obversers.remove(obverser);
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.ObverserSet#clearObverser()
	 */
	@Override
	public void clearObverser() {
		lock.writeLock().lock();
		try{
			obversers.clear();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getInputStream()
	 */
	@Override
	public InputStream getInputStream() {
		lock.readLock().lock();
		try{
			return in;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#setInputStream(java.io.InputStream)
	 */
	@Override
	public boolean setInputStream(InputStream in) {
		lock.writeLock().lock();
		try{
			if(streamLockFlag) return false;
			this.in = in;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() {
		lock.readLock().lock();
		try{
			return out;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#setOutputStream(java.io.OutputStream)
	 */
	@Override
	public boolean setOutputStream(OutputStream out) {
		lock.writeLock().lock();
		try{
			if(streamLockFlag) return false;
			this.out = out;
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#lockStream()
	 */
	@Override
	public void lockStream() {
		lock.writeLock().lock();
		try{
			streamLockFlag = true;
			startCondition.signalAll();
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.RunningTool#start()
	 */
	@Override
	public void start() throws InterruptedException {
		lock.writeLock().lock();
		try{
			
		}finally {
			lock.writeLock().unlock();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RuntimeState getRuntimeState() {
		// TODO Auto-generated method stub
		return null;
	}

}
