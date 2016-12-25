package com.dwarfeng.tp.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import org.dom4j.io.SAXReader;

import com.dwarfeng.dutil.basic.io.CT;
import com.dwarfeng.dutil.basic.io.FileUtil;
import com.dwarfeng.tp.model.init.PathResloveType;

/**
 * ”√”⁄≤‚ ‘
 * @author  DwArFeng
 * @since 1.8
 */
final class Foo {

	public static void main(String[] args) throws Exception {
		InputStream in = new FileInputStream(new File("attributes\\logger\\logger-cfg.xml"));
		SAXReader reader = new SAXReader();
		reader.read(in);
		reader.read(in);
	}

}
