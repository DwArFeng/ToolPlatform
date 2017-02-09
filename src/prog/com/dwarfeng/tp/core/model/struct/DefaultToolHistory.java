package com.dwarfeng.tp.core.model.struct;

import java.awt.Image;
import java.util.Date;
import java.util.Objects;

/**
 * Ĭ�Ϲ�����ʷ��
 * <p> ������ʷ��Ĭ��ʵ�֡�
 * <p> �����̰߳�ȫ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public final class DefaultToolHistory implements ToolHistory{
	
	private final String name;
	private final Image image;
	private final Date runnedTime;
	private final Date exitedDate;
	
	/**
	 * ��ʵ��
	 * @param name ָ�������ơ�
	 * @param image ָ����ͼƬ��
	 * @param runnedTime ָ��������ʱ�䡣
	 * @param exitedDate ָ�����˳�ʱ�䡣
	 */
	public DefaultToolHistory(String name, Image image, Date runnedTime, Date exitedDate) {
		Objects.requireNonNull(name, "��ڲ��� name ����Ϊ null��");
		Objects.requireNonNull(image, "��ڲ��� image ����Ϊ null��");
		Objects.requireNonNull(runnedTime, "��ڲ��� runnedTime ����Ϊ null��");
		Objects.requireNonNull(exitedDate, "��ڲ��� exitedDate ����Ϊ null��");

		this.name = name;
		this.image = image;
		this.runnedTime = runnedTime;
		this.exitedDate = exitedDate;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.str.Name#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getImage()
	 */
	@Override
	public Image getImage() {
		return image;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getRunedTime()
	 */
	@Override
	public Date getRunedTime() {
		return runnedTime;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.struct.ToolHistory#getExitedTime()
	 */
	@Override
	public Date getExitedTime() {
		return exitedDate;
	}

}
