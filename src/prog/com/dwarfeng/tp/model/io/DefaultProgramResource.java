package com.dwarfeng.tp.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Objects;

import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.dutil.basic.io.IoUtil;

/**
 * 默认程序资源。
 * <p>程序资源的默认实现。
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultProgramResource implements ProgramResource {
	
	private final URL def;
	private final File res;
	
	/**
	 * 生成实例。
	 * @param def 指定的默认URL。
	 * @param res 指定的资源文件。
	 * @throws NullPointerException 入口参数为 null。
	 */
	public DefaultProgramResource(URL def, File res) {
		Objects.requireNonNull(def, "入口参数 def 不能为 null");
		Objects.requireNonNull(res, "入口参数 res 不能为 null");
		
		this.def = def;
		this.res = res;
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramResource#openInputStream()
	 */
	@Override
	public InputStream openInputStream() throws IOException {
		return new FileInputStream(res);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramResource#openOutputStream()
	 */
	@Override
	public OutputStream openOutputStream() throws IOException {
		return new FileOutputStream(res);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.model.io.ProgramResource#reset()
	 */
	@Override
	public void reset() throws IOException {
		FileUtil.createFileIfNotExists(res);
		
		InputStream in = null;
		OutputStream out = null;
		
		try{
			in = def.openStream();
			out = new FileOutputStream(res);
			IoUtil.trans(in, out, 8192);
		}finally {
			if(Objects.nonNull(in)){
				in.close();
			}
			if(Objects.nonNull(out)){
				out.close();
			}
		}
	}

}
