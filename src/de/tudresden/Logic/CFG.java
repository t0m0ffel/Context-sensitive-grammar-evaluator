package de.tudresden.Logic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CFG {
	String startsysmbol;
	String name;
	List<String> nonTerminals;
	List<String> terminals;
	List<Rule> rules;

	public CFG(List<Rule> r) {
		startsysmbol = "S";
		name = "G";
		nonTerminals = new LinkedList<String>();
		terminals = new LinkedList<String>();
		rules = r;
		for (Rule rule : r) {
			for (String symbol : rule.getLHS().split("")) {
				addSysmbol(symbol);
			}
			for (String symbol : rule.getRHS().split("")) {
				addSysmbol(symbol);
			}
		}
	}

	public void addSysmbol(String input) {
		if (!input.isEmpty() && input.charAt(0) >= 65 && input.charAt(0) <= 90)
			addNonTerminal(input);
		if (!input.isEmpty() && input.charAt(0) >= 97 && input.charAt(0) <= 122)
			addTerminal(input);
	}

	public void addTerminal(String s) {
		if (!terminals.contains(s))
			terminals.add(s);
	}

	public void addNonTerminal(String s) {
		if (!nonTerminals.contains(s))
			nonTerminals.add(s);
	}

	public List<String> getNonTerminals() {
		return nonTerminals;
	}

	public List<String> getTerminals() {
		return terminals;

	}

	public List<Rule> getRules() {
		return rules;
	}

	public String[] getDef() {
		String[] def = new String[4];
		def[0] = name + "={N,\u03A3," + startsysmbol + ",R}";
		def[1] = "N={";
		for (String nT : nonTerminals) {
			def[1] = def[1] + nT + ",";
		}
		def[1] = def[1].substring(0, def[1].length() - 1) + "}";
		def[2] = "\u03A3={";
		for (String t : terminals) {
			def[2] = def[2] + t + ",";
		}
		def[2] = def[2].substring(0, def[2].length() - 1) + "}";
		def[3] = "R={";

		return concat(def, getSRules());
	}

	public String[] getSRules() {
		String[] srules = new String[rules.size() / 2 + 2];
		List<Rule> ruless = new LinkedList<Rule>();

		for (int i = 0; i < rules.size(); i++) {
			if (rules.get(i).getRHS() == null
					|| rules.get(i).getRHS().trim().isEmpty())
				ruless.add(new Rule(rules.get(i).getLHS(), "\u03B5"));

			else
				ruless.add(rules.get(i));

		}
		srules[0] = "";
		for (int i = 0; i < ruless.size(); i = i + 2) {
			srules[i / 2] = " " + (srules[i / 2] == null ? "" : srules[i / 2])
					+ ruless.get(i).getRule() + ", ";
			if (i + 1 < ruless.size())
				srules[i / 2] = srules[i / 2] + ruless.get(i + 1).getRule()
						+ ",";
		}
		if (ruless.size() % 2 != 0)
			srules[ruless.size() / 2] = srules[ruless.size() / 2].substring(0,
					srules[ruless.size() / 2].length() - 2) ;
		else
			srules[ruless.size() / 2 - 1] = srules[ruless.size() / 2 - 1]
					.substring(0, srules[ruless.size() / 2 - 1].length() - 1)
					;
		srules[rules.size() / 2 +1]="}";

		return srules;
	}

	private <T> T[] concat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}
