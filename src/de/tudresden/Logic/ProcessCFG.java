package de.tudresden.Logic;

import java.util.LinkedList;
import java.util.List;

public class ProcessCFG {
	CFG cfg;

	public ProcessCFG(CFG cfg) {
		this.cfg = cfg;
	}

	public void setCFG(CFG cfg) {
		this.cfg = cfg;
	}

	public List<Rule> getValidRules(String input) {
		List<Rule> r = cfg.getRules();
		List<Rule> vr = new LinkedList<Rule>();
		for (Rule rule : r) {
			if (!input.equals(input.replaceFirst(rule.getLHS(), "")))
				vr.add(rule);
		}
		return vr;
	}

	public Rule getRule(String input) {
		if (getValidRules(input).isEmpty()) {
			return new Rule(input, input);
		}
		return getValidRules(input).get(0);
	}

	public String processRule(String input, Rule rule) {
		if(input.replaceFirst(rule.getLHS(), rule.getRHS()).equals("\u03B5"))
			return "\u03B5";
		return input.replaceFirst(rule.getLHS(), rule.getRHS()).replace("\u03B5", "");
	}

	public String[] getCFG() {
		return cfg.getDef();
	}
}
