package de.tudresden.Logic;

public class Rule {
	String rule;
	String lhs, rhs;

	public Rule(String r) {
		rule = r;
		lhs = rule.split("->")[0];
		if (rule.split("->").length == 1)
			rhs = "\u03B5";
		else
			rhs = rule.split("->")[1];

	}

	public Rule(String lhs, String rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
		rule = lhs + "->" + rhs;
	}

	public String getRule() {
		return rule;
	}

	public String getLHS() {
		return lhs;
	}

	public String getRHS() {
		return rhs;
	}

}
