package com.dwarfeng.tp.core.model.struct;

import java.util.Objects;

import com.dwarfeng.dutil.develop.cfg.ConfigModel;

/**
 * Ĭ�ϲ��ɼ������ṩ����
 * <p> ���ɼ������ṩ����Ĭ��ʵ�֡�
 * @author  DwArFeng
 * @since 1.8
 */
public class DefaultInvisibleConfigProvider implements InvisibleConfigProvider {
	
	private final ConfigModel configModel;

	public DefaultInvisibleConfigProvider(ConfigModel configModel) {
		Objects.requireNonNull(configModel, "��ڲ��� configModel ����Ϊ null��");
		this.configModel = configModel;
	}
	
	
	

}
