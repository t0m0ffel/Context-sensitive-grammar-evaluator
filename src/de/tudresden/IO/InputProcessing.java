package de.tudresden.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import de.tudresden.Logic.CFG;
import de.tudresden.Logic.Rule;

public class InputProcessing {

	String path;

	public InputProcessing(String path) {
		this.path = path;
	}

	public CFG getCFG() throws IOException {
		List<Rule> rl = new LinkedList<>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(new File(path)));
		String line = "";
		while ((line = br.readLine()) != null) {
			if (line.trim().matches("\\w*->\\w*")) {
				if (!line.trim().isEmpty()
						&& (line.matches("\\w*-> ") || line.matches("\\w*->") || line
								.split("->").length == 1)) {
					rl.add(new Rule(line.replace("->", ""), "\u03B5"));
				} else
					rl.add(new Rule(line.trim()));
			}
		}
		return new CFG(rl);

	}

}
