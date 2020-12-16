package de.tudresden.IO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class OutputProcessing {

	String path;

	public OutputProcessing() {
		this.path = "C:\\Users\\user\\Desktop\\CFG\\1.tex";
	}

	public OutputProcessing(String path) {
		this.path = path;
	}

	public void write(Object[] arr) throws IOException {
		writee(arr, false);
	}

	public void writeGrammar(Object[] arr) throws IOException {

		arr = addLatexConf((String[]) arr);
		PrintWriter bf = new PrintWriter(new BufferedWriter(
				new FileWriter(path)), true);
		for (Object object : arr) {
			if (object != null) {
				if (object.toString().contains("->"))
					object = "\\hspace*{1cm}" + object;
				bf.println(convertToTex((String) object));
			}
		}
		bf.close();
		toPDF();
	}

	public void writeTex(Object[] arr) throws IOException {

		writee(addLatexConf(arr), true);
		toPDF();

	}

	private void writee(Object[] arr, Boolean tex) throws IOException {
		PrintWriter bf = new PrintWriter(new BufferedWriter(
				new FileWriter(path)), true);
		for (Object object : arr) {
			if (tex)
				object = convertToTex((String) object);
			bf.println(object);
		}
		bf.close();

	}

	private String convertToTex(String input) {
		return input.replace("->", "$\\rightarrow$ ").replace("A", "N$^2$")
				.replace("\u03A3", "$\\Sigma$")
				.replace("\u03B5", "$\\epsilon$");
	}

	private <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	private Object[] addLatexConf(Object[] arr) throws IOException {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].toString().replace("{", "\\{").replace("}", "\\}")
					+ "\\\\";
		}
		Object[] begin = new String[] {
				"\\documentclass[a4paper,100pt]{minimal}",
				"\\usepackage[utf8]{inputenc}", "\\usepackage[T1]{fontenc}",
				"\\usepackage{fixltx2e}", "\\usepackage{microtype}",
				"\\usepackage{tkz-graph}",
				"\\usepackage[style=british]{csquotes}",
				"\\usepackage[ngerman,english]{babel}",
				"\\usepackage{mathabx}", "\\begin{document}", "\\noindent", };
		Object[] end = new String[] { "\\end{document}" };
		arr = (String[]) (concat(concat(begin, arr), end));
		return arr;

	}

	public void toPDF() throws IOException {
		String newpath = path.substring(0, path.lastIndexOf("\\"));
		String name = path.substring(path.lastIndexOf("\\") + 1).split("\\.")[0];
		new File(newpath + "\\" + name + ".pdf").delete();
		String command = "cmd  /c  start /min /wait pdflatex.exe " + path;
		try {
			Runtime.getRuntime().exec(command, null, new File(newpath))
					.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (new File(newpath + "\\" + name + ".pdf").exists())
			Runtime.getRuntime().exec(
					"cmd /c start " + newpath + "\\" + name + ".pdf");
	
		deleteFiles(new String[] { newpath + "\\" + name + ".aux",
				newpath + "\\" + name + ".tex", newpath + "\\" + name + ".log" });

	}

	private boolean deleteFiles(String[] paths) {
		boolean re = true;
		for (String path : paths)
			re = re & new File(path).delete();
		return re;
	}
}
