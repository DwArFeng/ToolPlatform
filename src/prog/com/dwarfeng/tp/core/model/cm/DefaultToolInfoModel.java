package com.dwarfeng.tp.core.model.cm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * Ĭ�Ϲ�����Ϣģ�͡�
 * <p> ����ģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolInfoModel extends AbstractToolInfoModel implements ToolInfoModel {
	
	private final Set<ToolInfo> toolInfos = new HashSet<>();
	private final Set<String> toolInfoNames = new HashSet<>();
	
	/**
	 * ��ʵ����
	 */
	public DefaultToolInfoModel() {
		this(new HashSet<>());
	}
	
	/**
	 * ��ʵ����
	 * @param c ָ���ĳ�ʼֵ��
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public DefaultToolInfoModel(Set<ToolInfo> c){
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		toolInfos.addAll(c);
		for(ToolInfo toolInfo : toolInfos){
			toolInfoNames.add(toolInfo.getName());
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#add(com.dwarfeng.tp.core.model.struct.ToolInfo)
	 */
	@Override
	public boolean add(ToolInfo toolInfo) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(toolInfo)) return false;
			if(toolInfoNames.contains(toolInfo.getName())) return false;
			
			toolInfos.add(toolInfo);
			toolInfoNames.add(toolInfo.getName());
			fireToolInfoAdded(toolInfo);
			
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireToolInfoAdded(ToolInfo toolInfo) {
		for(ToolInfoObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireToolInfoAdded(toolInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#remove(com.dwarfeng.tp.core.model.struct.ToolInfo)
	 */
	@Override
	public boolean remove(ToolInfo toolInfo) {
		lock.writeLock().lock();
		try{
			if(! toolInfos.contains(toolInfo)) return false;
			toolInfos.remove(toolInfo);
			toolInfoNames.remove(toolInfo.getName());
			fireToolInfoRemoved(toolInfo);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireToolInfoRemoved(ToolInfo toolInfo) {
		for(ToolInfoObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireToolInfoRemoved(toolInfo);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			toolInfos.clear();
			toolInfoNames.clear();
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(ToolInfoObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return toolInfos.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		lock.readLock().lock();
		try{
			return toolInfos.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#contains(com.dwarfeng.dutil.basic.str.Name)
	 */
	@Override
	public boolean contains(Name name) {
		lock.readLock().lock();
		try{
			if(Objects.isNull(name)) return false;
			return toolInfoNames.contains(name.getName());
		}finally {
			lock.readLock().unlock();
		}
	}

	/**
	 * ���ظù�����Ϣģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return �ù�����Ϣģ�͵Ĺ��̵�������
	 */
	@Override
	public Iterator<ToolInfo> iterator() {
		lock.readLock().lock();
		try{
			return toolInfos.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}
