package de.tudresden.GUI;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import de.tudresden.IO.InputProcessing;
import de.tudresden.IO.OutputProcessing;
import de.tudresden.Logic.ProcessCFG;
import de.tudresden.Logic.Rule;

public class GUIInteraction {

	ProcessCFG p;
	List<String> derivations;

	public String currentDerrivation() {
		return derivations.get(derivations.size() - 1);
	}

	public GUIInteraction(String path) throws IOException {
		p = new ProcessCFG(new InputProcessing(path).getCFG());
		derivations = new LinkedList<>();
		derivations.add("S");
	}

	public Object[] getAvailableRules() {

		List<Rule> rules = p.getValidRules(currentDerrivation());
		Object[] obarr = new Object[rules.size()];
		for (Rule rule : rules) {
			obarr[rules.indexOf(rule)] = rule.getRule();
		}
		return obarr;
	}

	public Object[] getAvailableRules(String input) {

		List<Rule> rules = p.getValidRules(input);
		Object[] obarr = new Object[rules.size()];
		for (Rule rule : rules) {
			obarr[rules.indexOf(rule)] = rule.getRule();
		}
		return obarr;
	}

	public void addDerivations(String rule) {
		derivations.add(rule);
	}

	public void removeDerivation() {
		if (derivations.size() > 1)
			derivations.remove(derivations.size() - 1);
	}

	public Object[] getDerivations(int count) {
		return cut(derivations.toArray(), count);
	}

	public Object[] getAllDerivations() {
		return derivations.toArray();
	}

	public void useRule(Rule r) {
		derivations.add(p.processRule(currentDerrivation(), r));
	}

	public Object[] derivate(Rule rule,int count) {
		useRule(rule);
		return getDerivations(count);
	}

	public boolean nomoreRules() {
		return p.getValidRules(currentDerrivation()).isEmpty();
	}

	public String[] getCFG() {
		return p.getCFG();
	}

	public String getL(String input) {
		if (input == null || input.trim().isEmpty())
			return "";
		String crrnt = input.substring(0, 1), output = "";
		int c = -1;
		for (int i = 0; i < input.length(); i++) {
			if (crrnt.equals(input.substring(i, i + 1)))
				c++;

			else {
				output = output + crrnt + ":" + (c + 1) + " ";
				c = 0;
				crrnt = input.substring(i, i + 1);
			}
		}
		output = output + crrnt + ":" + (c + 1);
		return output;

	}

	private Object[] cut(Object[] array, int l) {
		if (array.length > l) {
			Object[] out = new Object[l];
			for (int i = array.length - l; i < array.length; i++) {
				out[i - (array.length - l)] = array[i];
			}
			return out;
		}
		return array;
	}

	public String genWord() {
		Object[][] der = this.genRDerivations(1);
		return (String) der[0][der[0].length - 1];
	}

	@Deprecated
	public Object[][] genDerivations(int count) {
		List<List<Object>> deri = new LinkedList<>();
		deri.add(new LinkedList<>());
		deri.get(0).add("S");

		while (count >= finishedDeri(deri)) {
			List<List<Object>> nderis = new LinkedList<>();
			List<List<Object>> newderi;
			for (int i = 0; i < deri.size(); i++) {
				String crrnt = (String) deri.get(i).get(deri.get(i).size() - 1);
				List<Object> crrntderi = deri.get(i);

				List<Rule> rules = p.getValidRules(crrnt);
				newderi = new LinkedList<>();
				// System.out.println(crrnt);
				// System.out.println(rules.size());
				for (int j = 0; j < rules.size(); j++) {
					List<Object> h = new LinkedList<>(crrntderi);
					h.add(p.processRule(crrnt, rules.get(j)));

					newderi.add(h);
				}

				nderis.addAll(newderi);
				int max = 0;
				for (List<Object> rule : nderis) {
					if (rule.size() > max) {
						max = rule.size();
					}
				}
				List<List<Object>> h1 = new LinkedList<List<Object>>();
				for (List<Object> rule : nderis) {
					if (max == rule.size()) {
						h1.add(rule);
					}
				}
				List<List<Object>> h2 = h1;
				for (int j = 0; j < h1.size(); j++) {
					Object h = h1.get(j).get(h1.get(j).size() - 1);
					for (int j2 = j + 1; j2 < h1.size(); j2++) {
						if (h.equals(h2.get(j2).get((h2.get(j2).size() - 1)))) {
							h2.remove(h2.get(j2));
						}
					}
				}
				nderis = h2;

			}
			// System.out.println("-----------------");
			deri.addAll(nderis);
			// System.out.println("fd" + finishedDeri(deri));

		}
		List<List<Object>> h = new LinkedList<>();
		for (List<Object> list : deri) {
			if (noMoreRules(list.get(list.size() - 1)))
				h.add(list);
		}
		Object[][] out = new Object[h.size()][];
		for (int i = 0; i < out.length; i++) {
			out[i] = h.get(i).toArray();
		}
		return out;
	}

	public Object[][] genRDerivations(int count) {
		List<List<Object>> der = new LinkedList<List<Object>>();

		for (int i = 0; i < count; i++) {
			Object[] crrnt = derivateend("S");
			while (check(der, crrnt[crrnt.length - 1])) {
				crrnt = derivateend("S");
			}
			der.add(Arrays.asList(crrnt));

		}
		Object[][] out = new Object[der.size()][];
		for (int i = 0; i < out.length; i++) {
			out[i] = der.get(i).toArray();
		}
		return out;
	}

	public boolean check(List<List<Object>> list, Object o) {
		boolean out = false;
		for (List<Object> list2 : list) {
			out = list2.contains(o);
		}
		return out;
	}

	public Object[] derivateend(String input) {
		List<Object> ders = new LinkedList<Object>();
		while (!noMoreRules(input)) {
			List<Rule> rules = p.getValidRules(input);
			Random r = new Random();
			int rnd = r.nextInt(rules.size());
			input = p.processRule(input, rules.get(rnd));
			ders.add(input);
		}
		ders.add(input);
		return ders.toArray();
	}

	public Object[] genWords(int count) {
		final List<Object> out = new LinkedList<>();
		System.out.println("t:" + Thread.getAllStackTraces().size());
		if (Thread.getAllStackTraces().size() < 11) {
			LinkedList<Thread> threads = new LinkedList<Thread>();
			for (int i = 0; i < count; i++) {
				System.out.println(i);
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						String word = genWord();
						while (out.contains(word)) {
							word = genWord();
						}
						System.out.println(out.size());
						out.add(word);

					}
				});
				threads.add(t);
				t.start();

			}
			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			List<Object> outdup = con(new LinkedList<Object>(), out);
			while (outdup.size() < count) {
				String word = genWord();
				while (out.contains(word)) {
					word = genWord();
				}
			}
		}
		return out.toArray();
	}

	private List<Object> con(List<Object> l1, List<Object> l2) {
		for (Object object : l2) {
			if (!l1.contains(object))
				l1.add(object);
		}
		return l1;
	}

	public int finishedDeri(List<List<Object>> input) {
		int count = 0;
		List<Object> li = new LinkedList<>();
		li.add(input.get(0).get(input.get(0).size() - 1));
		for (List<Object> list : input) {
			Object object = list.get(list.size() - 1);
			li.add(object);

			for (int i = 0; i < li.size() - 1; i++) {
				if (li.get(i).equals((String) object)) {
					li.remove(li.size() - 2);
				}
			}
		}
		for (Object object : li) {// System.out.println(object);
			if (noMoreRules(object))
				count++;
		}
		return count;

	}

	public boolean noMoreRules(Object input) {
		return p.getValidRules((String) input).size() == 0;
	}

	public void saveDer(String path) throws IOException{
		new OutputProcessing(path).writeTex(derivations.toArray());
	}

	public void saveGra(String path) throws IOException{
		new OutputProcessing(path).writeGrammar(p.getCFG());
	}

	public void toPDF(String path) throws IOException{
		new OutputProcessing(path).toPDF();
	}
}
