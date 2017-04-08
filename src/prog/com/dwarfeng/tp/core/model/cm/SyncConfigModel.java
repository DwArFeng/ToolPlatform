package com.dwarfeng.tp.core.model.cm;

import com.dwarfeng.dutil.basic.threads.ExternalReadWriteThreadSafe;
import com.dwarfeng.dutil.develop.cfg.ConfigModel;

/**
 * 同步配置模型。
 * <p> 模型中数据的读写均应该是线程安全的。
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public interface SyncConfigModel extends ConfigModel, ExternalReadWriteThreadSafe {

}
