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
 * Ĭ�ϳ�����Դ��
 * <p>������Դ��Ĭ��ʵ�֡�
 * @author DwArFeng
 * @since 1.8
 */
public final class DefaultProgramResource implements ProgramResource {
	
	private final URL def;
	private final File res;
	
	/**
	 * ����ʵ����
	 * @param def ָ����Ĭ��URL��
	 * @param res ָ������Դ�ļ���
	 * @throws NullPointerException ��ڲ���Ϊ null��
	 */
	public DefaultProgramResource(URL def, File res) {
		Objects.requireNonNull(def, "��ڲ��� def ����Ϊ null");
		Objects.requireNonNull(res, "��ڲ��� res ����Ϊ null");
		
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