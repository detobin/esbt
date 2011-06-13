package org.scalastuff.esbt;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;

public class Utils {
	
	public static List<String> read(IFile file) {
		try {
			return read(file.getContents());
		} catch (CoreException e) {
		}
		return Collections.emptyList();
	}
	
	public static List<String> read(File file) {
		try {
			return read(new FileInputStream(file));
		} catch (FileNotFoundException e) {
		}
		return Collections.emptyList();
	}
	
	public static List<String> read(InputStream is) {
		List<String> lines = new ArrayList<String>();
		final BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line;
		try {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return lines;
	}
	
	public static InputStream linesInputStream(List<String> lines) {
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line).append("\n");
		}
		return new ByteArrayInputStream(builder.toString().getBytes());
	}

	public static void write(OutputStream os, List<String> lines) throws IOException {
		try {
			for (String line : lines) {
				os.write((line + "\n").getBytes());
			}
		} finally {
			os.close();
		}
	}
	
	public static void copyStream(InputStream is, OutputStream os) throws IOException {
		try {
			byte[] b = new byte[2000];
			int read;
			while ((read = is.read(b)) != -1) {
				os.write(b, 0, read);
			}
		} finally {
			is.close();
			os.close();
		}
	}
	
	public static int indexOfLineContaining(List<String> lines, String s) {
		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).contains(s)) {
				return i;
			}
		}
		return -1;
	}
}