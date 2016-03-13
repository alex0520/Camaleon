package com.camaleon.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadFileWelcome extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public LoadFileWelcome() {
		setTitle("Cargar Archivo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 564);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(80);

		JTextPane txtpnMensaje = new JTextPane();
		txtpnMensaje.setBackground(new Color(240, 240, 240));
		txtpnMensaje.setContentType("text/html");
		txtpnMensaje
				.setText("<html>\r\nEsta aplicaci\u00F3n permite cargar un archivo JSON con la siguiente estructura\r\n<br />\r\n<pre>\r\n{\r\n  \"attributes\": [\r\n    \"A\",\r\n    \"B\",\r\n    \"C\",\r\n    \"D\"\r\n  ],\r\n  \"functionalDependencies\": [\r\n    {\r\n      \"implicant\": [\r\n        \"A\",\r\n        \"B\"\r\n      ],\r\n      \"implied\": [\r\n        \"C\",\r\n        \"D\"\r\n      ]\r\n    }\r\n  ]\r\n}\r\n</pre>\r\nPor favor seleccione el archivo y presione siguiente\r\n</html>");
		txtpnMensaje.setEditable(false);
		JButton btnSeleccionarArchivo = new JButton("Seleccionar Archivo");
		btnSeleccionarArchivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jFileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Archivos JSON", "json", "JSON");
				jFileChooser.setFileFilter(filter);
				int returnVal = jFileChooser.showOpenDialog(JOptionPane
						.getFrameForComponent((Component) e.getSource()));
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					textField.setText(jFileChooser.getSelectedFile()
							.getAbsolutePath());
				}
			}
		});

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().equals("")) {
					JOptionPane.showMessageDialog(JOptionPane
							.getFrameForComponent((Component) e.getSource()),
							"No ha seleccionado un archivo", "Error",
							JOptionPane.ERROR_MESSAGE);
				}else{
					dispose();
					String path = textField.getText();
					LoadFileFirst loadFileFirst = new LoadFileFirst(path);
					loadFileFirst.setExtendedState(JFrame.MAXIMIZED_BOTH); 
					loadFileFirst.setVisible(true);
				}
			}
		});

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(txtpnMensaje, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnSeleccionarArchivo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(587, Short.MAX_VALUE)
							.addComponent(btnSiguiente)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(txtpnMensaje, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSeleccionarArchivo)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnSiguiente)
					.addGap(27))
		);

		contentPane.setLayout(gl_contentPane);
	}

	public static int getContentHeight(String content) {
		JTextPane dummyEditorPane = new JTextPane();
		dummyEditorPane.setContentType("text/html");
		dummyEditorPane.setSize(100, Short.MAX_VALUE);
		dummyEditorPane.setText(content);

		return dummyEditorPane.getPreferredSize().height;
	}
}
