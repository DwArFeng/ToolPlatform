package com.dwarfeng.tp.core.model.cm;

import java.util.Map;

import com.dwarfeng.tp.core.model.struct.ReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.struct.Resource;

/**
 * ��Դģ�͡�
 * <p> ģ�������ݵĶ�д��Ӧ�����̰߳�ȫ�ġ�
 * @author  DwArFeng
 * @since 1.8
 */
public interface ResourceModel extends Map<String, Resource>, ReadWriteThreadSafe{

}
