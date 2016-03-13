package com.camaleon.view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.camaleon.entities.FuncDependency;
import com.camaleon.entities.Relation;
import com.camaleon.logic.CandidateKeys;
import com.camaleon.logic.LoadFile;
import com.camaleon.logic.MinimalCover;
import javax.swing.JScrollPane;

public class LoadFileFirst extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public LoadFileFirst(String path) {
		setTitle("Carga Archivos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel lblReacinCargada = new JLabel("Relaci\u00F3n Cargada");
		JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblReacinCargada)
					.addContainerGap(340, Short.MAX_VALUE))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblReacinCargada)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE))
		);		

		Relation relation = LoadFile.loadFile(path);
		
		textPane.setText(printAlgorithm(relation));

		contentPane.setLayout(gl_contentPane);
	}
	
	public String printAlgorithm(Relation relation){
		StringBuilder sb = new StringBuilder();
		HashMap<HashSet<String>, HashSet<String>> closures = new HashMap<HashSet<String>, HashSet<String>>();
		sb.append("<html>");
		sb.append("Atributos: ");
		sb.append(relation.getAttributes());
		sb.append(printFuncDeps(relation,"Dependencias Funcionales: "));		

		relation.setDependencies(MinimalCover.rightDecomposition(relation
				.getDependencies()));

		sb.append(printFuncDeps(relation,"L0: "));

		relation.setDependencies(MinimalCover.removeStrangeElemLeft(
				relation.getDependencies(), closures));

		sb.append(printFuncDeps(relation,"L1: "));

		relation.setDependencies(MinimalCover
				.removeRedundantDependencies(relation.getDependencies()));

		sb.append(printFuncDeps(relation,"Recubrimiento Minimo: "));

		List<HashSet<String>> keys = CandidateKeys.candidateKeys(relation,
				closures);

		sb.append("<br />");
		sb.append("Llaves Candidatas: ");
		sb.append(keys);

		sb.append("</html>");

		return sb.toString(); 
	}
	
	public String printFuncDeps(Relation relation, String title){
		StringBuilder sb = new StringBuilder();
		sb.append("<br />");
		sb.append(title);
		sb.append("<br />");
		for (Iterator<FuncDependency> iterator = relation.getDependencies()
				.iterator(); iterator.hasNext();) {
			FuncDependency funcDependency = (FuncDependency) iterator.next();
			sb.append(funcDependency);
			sb.append("<br />");
		}
		sb.append("-------------------------------------------------------------</br>");
		return sb.toString(); 
		
	}
}
