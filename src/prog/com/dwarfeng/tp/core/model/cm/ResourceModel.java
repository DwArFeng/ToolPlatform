package com.dwarfeng.tp.core.model.cm;

import java.util.Map;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.tp.core.model.cfg.ResourceKey;
import com.dwarfeng.tp.core.model.struct.Resource;

/**
 * 资源模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author  DwArFeng
 * @since 1.8
 */
public interface ResourceModel extends Map<ResourceKey, Resource>, ExternalReadWriteThreadSafe{

}
