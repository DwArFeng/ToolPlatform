package com.dwarfeng.tp.core.model.cm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

import com.dwarfeng.tp.core.model.obv.ToolHistoryObverser;
import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * Ĭ�Ϲ�����ʷģ�͡�
 * <p> ������ʷģ�͵�Ĭ��ʵ�֡�
 * <p> ��ģ���е����ݵĶ�д�����̰߳�ȫ�ġ�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class DefaultToolHistoryModel extends AbstractToolHistoryModel{
	
	private final LinkedList<ToolHistory> toolHistories= new LinkedList<>();
	private int maxSize;
	
	/**
	 * ��ʵ����
	 */
	public DefaultToolHistoryModel() {
		this(100, new ArrayList<>());
	}
	
	/**
	 * ��ʵ����
	 * @param maxSize ���Ĵ洢��С��
	 * @param c
	 */
	public DefaultToolHistoryModel(int maxSize, Collection<ToolHistory> c) {
		Objects.requireNonNull(c, "��ڲ��� c ����Ϊ null��");
		if(maxSize < 0) throw new IllegalArgumentException("maxSize ����С�� 0��");
		this.maxSize = maxSize;
		for(ToolHistory toolHistory : c){
			notFireAdd(toolHistory);
		}
	}
	
	private void notFireAdd(ToolHistory toolHistory){
		if(Objects.isNull(toolHistory)) return;
		toolHistories.offer(toolHistory);
		if(toolHistories.size() > maxSize){
			toolHistories.poll();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#offer(com.dwarfeng.tp.core.model.struct.ToolHistory)
	 */
	@Override
	public boolean offer(ToolHistory toolHistory) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(toolHistory)) return false;
			int index = toolHistories.size();
			boolean aFlag = toolHistories.offer(toolHistory);
			if(aFlag) fireToolHistoryAdded(index, toolHistory);
			if(toolHistories.size() > maxSize){
				toolHistories.poll();
				fireToolHistoryRemoved(index, toolHistory);
			}
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireToolHistoryAdded(int index, ToolHistory toolHistory) {
		for(ToolHistoryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireToolHistoryAdded(index, toolHistory);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#add(int, com.dwarfeng.tp.core.model.struct.ToolHistory)
	 */
	@Override
	public boolean add(int index, ToolHistory toolHistory) {
		lock.writeLock().lock();
		try{
			if(Objects.isNull(toolHistory)) return false;
			toolHistories.add(index, toolHistory);
			fireToolHistoryAdded(index, toolHistory);
			if(toolHistories.size() > maxSize){
				toolHistories.poll();
				fireToolHistoryRemoved(index, toolHistory);
			}
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#poll()
	 */
	@Override
	public boolean poll() {
		lock.writeLock().lock();
		try{
			int index = toolHistories.size();
			ToolHistory toolHistory = toolHistories.poll();
			boolean aFlag = Objects.nonNull(toolHistory);
			if(aFlag) fireToolHistoryRemoved(index, toolHistory);
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireToolHistoryRemoved(int index, ToolHistory toolHistory) {
		for(ToolHistoryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireToolHistoryRemoved(index, toolHistory);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#size()
	 */
	@Override
	public int size() {
		lock.readLock().lock();
		try{
			return toolHistories.size();
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#maxSize()
	 */
	@Override
	public int maxSize() {
		lock.readLock().lock();
		try{
			return maxSize;
		}finally {
			lock.readLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#setMaxSize(int)
	 */
	@Override
	public boolean setMaxSize(int size) {
		lock.writeLock().unlock();
		try{
			if(size < 0) return false;
			int oldValue = this.maxSize;
			this.maxSize = size;
			fireMaxSizeChanged(oldValue, size);
			return true;
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireMaxSizeChanged(int oldValue, int newValue) {
		for(ToolHistoryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireMaxSizeChanged(oldValue, newValue);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#remove(int)
	 */
	@Override
	public boolean remove(int index) {
		lock.writeLock().lock();
		try{
			ToolHistory toolHistory = toolHistories.remove(index);
			boolean aFlag = Objects.nonNull(toolHistory);
			if(aFlag) fireToolHistoryRemoved(index, toolHistory);
			return aFlag;
		}finally {
			lock.writeLock().unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.cm.ToolHistoryModel#clear()
	 */
	@Override
	public void clear() {
		lock.writeLock().lock();
		try{
			toolHistories.clear();
			fireCleared();
		}finally {
			lock.writeLock().unlock();
		}
	}

	private void fireCleared() {
		for(ToolHistoryObverser obverser : obversers){
			if(Objects.nonNull(obverser)) obverser.fireCleared();
		}
	}

	/**
	 * ���ظù�����Ϣģ�͵Ĺ��̵�������
	 * <p> ע�⣬�õ����������̰߳�ȫ�ģ����Ҫʵ���̰߳�ȫ����ʹģ�����ṩ�Ķ�д��
	 * {@link #getLock()}�����ⲿͬ����
	 * @return �ù�����Ϣģ�͵Ĺ��̵�������
	 */
	@Override
	public Iterator<ToolHistory> iterator() {
		lock.readLock().lock();
		try{
			return toolHistories.iterator();
		}finally {
			lock.readLock().unlock();
		}
	}

}
