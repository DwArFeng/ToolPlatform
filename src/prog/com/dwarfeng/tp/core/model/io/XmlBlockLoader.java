package com.dwarfeng.tp.core.model.io;

import java.io.InputStream;
import java.util.Objects;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.LoadFailedException;
import com.dwarfeng.tp.core.model.cm.BlockModel;

/**
 * xml�赲ģ�Ͷ�ȡ����
 * <p> ʹ��xml��ȡ�赲ģ�͡�
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlBlockLoader extends StreamLoader implements Loader<BlockModel> {

	/**
	 * ��ʵ����
	 * @param in ָ������������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlBlockLoader(InputStream in) {
		super(in);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.tp.core.model.io.Loader#load(java.lang.Object)
	 */
	@Override
	public void load(BlockModel blockModel) throws LoadFailedException {
		Objects.requireNonNull(blockModel, "��ڲ��� libraryModel ����Ϊ null��");
		
		try{
			SAXReader reader = new SAXReader();
			
			Element root = null;
			try{
				root = reader.read(in).getRootElement();
			}finally {
				in.close();
			}
			
			//TODO ʵ�ָ�Ԫ�صĽ����㷨��
			
		}catch (Exception e) {
			throw new LoadFailedException("�޷���ָ���Ŀ�ģ���ж�ȡ���е�����", e);
		}

	}

}
