package de.tudresden.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JButton;

import de.tudresden.Logic.Rule;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JLabel;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4955051165582595139L;
	private JPanel contentPane;
	private static GUIInteraction guii;
	private JTextField textField;
	JList<Object> list;
	JList<Object> list_1;
	JList<String> list_2;
	JFileChooser chooser;
	String choosertitle;
	String choosenFile;
	String defaultText = "./square_numbers_grammar.txt";
	private JTextField textField_1;
	private JTextPane textPane;
	private JButton btnDerivate;
	private JTextField textField_2;
	private JTextField textField_3;
	private int number_of_dervs = 16;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {

			try {
				GUI frame = new GUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1123, 584);
		setName("Grammar Evaluation");
		setTitle("Grammar Evaluation");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		list = new JList<Object>();
		list.setBounds(220, 38, 114, 468);
		contentPane.add(list);
		setMinimumSize(new Dimension(690, 562));

		list_1 = new JList<Object>();
		list_1.setBounds(344, 38, 726, 440);
		contentPane.add(list_1);

		list.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseExited(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (guii != null && !guii.nomoreRules()) {
					list_1.setListData(guii.derivate(
							new Rule((String) list.getSelectedValue()),
							number_of_dervs));
					list.setListData(guii.getAvailableRules());
					list.setSelectedIndex(0);
					list_1.setSelectedIndex(list_1.getLastVisibleIndex());
					textField_1.setText((String) list_1.getSelectedValue());
					textPane.setText(guii.getL((String) list_1
							.getSelectedValue()));

				}
			}

		});
		list_1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if (guii != null && !guii.nomoreRules()) {
					textField_1.setText((String) list_1.getSelectedValue());
					textPane.setText(guii.getL((String) list_1
							.getSelectedValue()));

				}

			}
		});
		textField = new JTextField();
		textField.setBounds(100, 170, 92, 23);

		textField.setText(defaultText);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSource = new JButton("Source...");
		btnSource.setBounds(9, 170, 86, 23);
		contentPane.add(btnSource);

		JButton btnNewButton_2 = new JButton("Load");
		btnNewButton_2.addActionListener(arg0 -> refresh());
		btnNewButton_2.setBounds(9, 136, 89, 23);
		contentPane.add(btnNewButton_2);

		list_2 = new JList<>();
		list_2.setBounds(9, 273, 177, 233);
		contentPane.add(list_2);

		JButton btnUndo = new JButton("Undo");
		btnUndo.addActionListener(arg0 -> {
			if (guii != null) {
				guii.removeDerivation();
				list.setListData(guii.getAvailableRules());
				list_1.setListData(guii.getDerivations(number_of_dervs));
				list.setSelectedIndex(0);
			}
		});
		btnUndo.setBounds(100, 99, 89, 23);
		contentPane.add(btnUndo);

		textField_1 = new JTextField();
		textField_1.setBounds(344, 11, 726, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(arg0 -> {
			if (guii != null && !textField_1.getText().trim().isEmpty()) {
				guii.addDerivations(textField_1.getText());
				list_1.setListData(guii.getDerivations(number_of_dervs));
				list.setListData(guii.getAvailableRules());
				textField_1.setText((String) list_1.getSelectedValue());
			}
		});
		btnAdd.setBounds(245, 10, 89, 23);
		contentPane.add(btnAdd);

		textPane = new JTextPane();
		textPane.setBounds(344, 486, 726, 20);
		contentPane.add(textPane);

		btnDerivate = new JButton("Derivate*");
		btnDerivate.addActionListener(arg0 -> {
			if (guii != null && !guii.nomoreRules()) {
				while (!guii.nomoreRules()) {
					list_1.setListData(guii.derivate(
							new Rule((String) list.getSelectedValue()),
							number_of_dervs));
					list.setListData(guii.getAvailableRules());
					Random r = new Random();
					if (guii.getAvailableRules().length > 1)
						list.setSelectedIndex(r.nextInt((guii
								.getAvailableRules().length)));
					else
						list.setSelectedIndex(0);
					list_1.setSelectedIndex(list_1.getLastVisibleIndex());
					textField_1.setText((String) list_1.getSelectedValue());

				}
				textPane.setText(guii.getL((String) list_1
						.getSelectedValue()));
			}
		});
		btnDerivate.setBounds(9, 99, 89, 23);
		contentPane.add(btnDerivate);

		JButton GenWords = new JButton("Generate Random Words");
		GenWords.addActionListener(arg0 -> {
			if (guii != null) {
				int count = textField_2.getText().isEmpty() ? 1 : Integer
						.parseInt(textField_2.getText());
				list_1.setListData(guii.genWords(count));

			}

		});
		GenWords.setBounds(10, 38, 182, 23);
		contentPane.add(GenWords);

		textField_2 = new JTextField();
		textField_2.setBounds(73, 72, 86, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(1);

		JLabel lblCount = new JLabel("Count:");
		lblCount.setBounds(25, 68, 37, 28);
		contentPane.add(lblCount);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(arg0 -> {
			if (guii != null)
				try {
					guii.saveDer(textField_3.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		});
		btnSave.setBounds(100, 204, 89, 23);
		contentPane.add(btnSave);

		JButton btnSaveGrammar = new JButton("Save Grammar");
		btnSaveGrammar.addActionListener(arg0 -> {
			try {
				guii.saveGra(textField_3.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		btnSaveGrammar.setBounds(9, 204, 89, 23);
		contentPane.add(btnSaveGrammar);

		JButton btnTarget = new JButton("Target...");
		btnTarget.addActionListener(arg0 -> {
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File(textField_3
					.getText().equals("") ? "." : textField_3.getText()));
			chooser.setDialogTitle(choosertitle);

			if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
				textField_3.setText(chooser.getSelectedFile()
						.getAbsolutePath());
			} else {
				System.out.println("No Selection ");
			}
		});
		btnTarget.setBounds(6, 238, 89, 23);
		contentPane.add(btnTarget);

		textField_3 = new JTextField();
		textField_3.setBounds(100, 238, 92, 21);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		textField_3.setText("./square_numbers_grammar.tex");

		btnSource.addActionListener(arg0 -> {

			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File(textField
					.getText().equals("") ? "." : textField.getText()));
			chooser.setDialogTitle(choosertitle);

			if (chooser.showOpenDialog(new JPanel()) == JFileChooser.APPROVE_OPTION) {
				textField.setText(chooser.getSelectedFile()
						.getAbsolutePath());

				refresh();

			} else {
				System.out.println("No Selection ");
			}

		});

		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub

				Rectangle bound = list.getBounds();
				list.setBounds((int) bound.getX(), (int) bound.getY(),
						(int) bound.getWidth(), getHeight() - 150);

				bound = list_1.getBounds();
				list_1.setBounds((int) bound.getX(), (int) bound.getY(),
						getWidth() - 400, getHeight() - 150);
				bound = list_1.getBounds();
				number_of_dervs = (int) bound.getHeight() / 18;
				if (guii != null)
					list_1.setListData(guii.getDerivations(number_of_dervs));
				list_1.setSelectedIndex(list_1.getLastVisibleIndex());

				textPane.setBounds((int) bound.getX(),
						(int) (bound.getY() + bound.getHeight()) + 10,
						(int) bound.getWidth(), textPane.getHeight());
				textField_1.setBounds((int) textField_1.getX(),
						(int) textField_1.getY(), (int) (bound.getWidth()),
						(int) textField_1.getHeight());

				bound = list_2.getBounds();
				list_2.setBounds((int) bound.getX(), (int) bound.getY(),
						(int) bound.getWidth(), getHeight() - 388);

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		refresh();
	}

	public void refresh() {
		try {
			guii = new GUIInteraction(
					textField.getText().equals("") ? defaultText
							: textField.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		list_1.setListData(guii.getDerivations(number_of_dervs));

		list.setListData(guii.getAvailableRules());
		list.setSelectedIndex(0);
		list_2.setListData(guii.getCFG());
		textPane.setText("");
	}
}
