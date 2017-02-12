package com.dwarfeng.tp.core.model.io;

import java.io.OutputStream;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dwarfeng.dutil.basic.io.SaveFailedException;
import com.dwarfeng.dutil.basic.io.StreamSaver;
import com.dwarfeng.tp.core.model.cm.ToolHistoryModel;
import com.dwarfeng.tp.core.model.struct.ToolHistory;

/**
 * Xml ������ʷ��������
 * ʹ�� xml ���湤����ʷ��
 * @author DwArFeng
 * @since 0.0.0-alpha
 */
public class XmlToolHistorySaver extends StreamSaver<ToolHistoryModel> {

	/**
	 * ��ʵ����
	 * @param out ָ�����������
	 * @throws NullPointerException ��ڲ���Ϊ <code>null</code>��
	 */
	public XmlToolHistorySaver(OutputStream out) {
		super(out);
	}

	/*
	 * (non-Javadoc)
	 * @see com.dwarfeng.dutil.basic.prog.Saver#save(java.lang.Object)
	 */
	@Override
	public void save(ToolHistoryModel toolHistoryModel) throws SaveFailedException {
		Objects.requireNonNull(toolHistoryModel, "��ڲ��� toolHistoryModel ����Ϊ null��");
		
		try{
			Element root = DocumentHelper.createElement("root");
			
			for(ToolHistory toolHistory : toolHistoryModel){
				Element history = DocumentHelper.createElement("history");
				history.addAttribute("name", toolHistory.getName());
				history.addAttribute("ran_date", toolHistory.getRanDate().getTime() + "");
				history.addAttribute("exited_date", toolHistory.getExitedDate().getTime() + "");
				history.addAttribute("exited_code", toolHistory.getExitedCode() + "");
				root.add(history);
			}
			
			Document document = DocumentHelper.createDocument(root);
			
			OutputFormat outputFormat = OutputFormat.createPrettyPrint();
			outputFormat.setEncoding("UTF-8");
			XMLWriter writer = new XMLWriter(out, outputFormat);
			
			try{
				writer.write(document);
			}finally {
				writer.close();
			}
			
		}catch (Exception e) {
			throw new SaveFailedException("������ʷ������-�޷���ָ�������б��湤����ʷģ�͵�����", e);
		}

	}

}
