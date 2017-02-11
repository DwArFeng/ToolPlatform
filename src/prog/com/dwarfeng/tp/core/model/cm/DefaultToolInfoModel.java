package com.dwarfeng.tp.core.model.cm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.dwarfeng.tp.core.model.obv.ToolInfoObverser;
import com.dwarfeng.tp.core.model.struct.ToolInfo;

/**
 * Ĭ�Ϲ�����Ϣģ�͡�
 * <p> ������Ϣģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolInfoModel extends AbstractToolInfoModel implements ToolInfoModel {
	
	private final Map<String, ToolInfo> toolInfoMap = new HashMap<>();
	
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
		for(ToolInfo toolInfo : c){
			notFireAdd(toolInfo);
		}
	}
	
	/**
	 * ���ָ���Ĺ�����Ϣ������֪ͨ��Ҳ���̰߳�ȫ��
	 * @param toolInfo ָ���Ĺ�����Ϣ��
	 */
	private void notFireAdd(ToolInfo toolInfo){
		if(Objects.isNull(toolInfo)) return;
		if(toolInfoMap.containsKey(toolInfo.getName())) return;
		
		toolInfoMap.put(toolInfo.getName(), toolInfo);
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
			if(toolInfoMap.containsKey(toolInfo.getName())) return false;
			
			toolInfoMap.put(toolInfo.getName(), toolInfo);
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
			if(Objects.isNull(toolInfo)) return false;
			if(! toolInfoMap.containsKey(toolInfo.getName())) return false;
			toolInfoMap.remove(toolInfo).getName();
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
			toolInfoMap.clear();
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
			return toolInfoMap.size();
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
			return toolInfoMap.isEmpty();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#contains(java.lang.String)
	 */
	@Override
	public boolean contains(String name) {
		lock.readLock().lock();
		try{
			if(Objects.isNull(name)) return false;
			return toolInfoMap.containsKey(name);
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolInfoModel#get(java.lang.String)
	 */
	@Override
	public ToolInfo get(String name) {
		lock.readLock().lock();
		try{
			if(Objects.isNull(name)) return null;
			return toolInfoMap.get(name);
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
			return toolInfoMap.values().iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}
