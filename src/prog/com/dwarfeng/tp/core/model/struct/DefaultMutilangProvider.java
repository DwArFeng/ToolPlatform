package com.dwarfeng.tp.core.model.struct;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;

import com.dwarfeng.dutil.basic.str.Name;
import com.dwarfeng.tp.core.model.cm.MutilangModel;

/**
 * Ĭ�϶����Խӿ��ṩ����
 * <p> �������ṩ���ӿڵ�Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
@SuppressWarnings("unused")
public final class DefaultMutilangProvider implements MutilangProvider {
	
	private final MutilangModel mutilangModel;
	
	private final Mutilang mutilang = new Mutilang() {
		
		/*
		 * (non-Javadoc)
		 * @see com.dwarfeng.tp.core.model.struct.Mutilang#getString(com.dwarfeng.dutil.basic.str.Name)
		 */
		@Override
		public String getString(String key) {
			Objects.requireNonNull(key, "��ڲ��� key ����Ϊ null��");
			
			Map<String, String> mutilangMap;
			String defaultValue;
			ReadWriteLock lock = mutilangModel.getLock();
			
			lock.readLock().lock();
			try{
				if(!mutilangModel.getSupportedKeys().contains(key)){
					throw new IllegalArgumentException("�ö����Խӿڲ�֧�ִ˼�");
				}
				mutilangMap = new HashMap<>(mutilangModel.getMutilangMap());
				defaultValue = mutilangModel.getDefaultValue();
			}finally {
				lock.readLock().unlock();
			}
			
			return mutilangMap.getOrDefault(key, defaultValue);
		}
	};
	
	/**
	 * ��ʵ����
	 * @param mutilangModel ָ���Ķ�����ģ�͡�
	 * @param supportedKeys ָ������֧�ֵļ�ֵ���ϡ�
	 */
	public DefaultMutilangProvider(MutilangModel mutilangModel) {
		Objects.requireNonNull(mutilangModel, "��ڲ��� mutilangModel ����Ϊ null��");
		this.mutilangModel = mutilangModel;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.MutilangProvider#getMutilang()
	 */
	@Override
	public Mutilang getMutilang() {
		return this.mutilang;
	}
}
