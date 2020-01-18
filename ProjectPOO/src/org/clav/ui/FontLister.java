package org.clav.ui;

import org.clav.ui.components.ScrollComponent;

import javax.swing.*;
import java.awt.*;

/**
 * App to visualize different fonts, could be integrated in the settings option of the chat application
 */
public class FontLister {
	public static void main(String[] args) {
		String sample = "Un texte bateau mais néanmoins utile à la visualisation des diffférentes polices\n" +
				"lablalblblalbla\n" +
				"Ipsum dolores bidule latinos\n";
		JFrame frame = new JFrame("FONT");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		JTextField fontField = new JTextField();
		JTextArea testArea = new JTextArea(sample);
		JTextArea fontList = new JTextArea();
		StringBuilder sb = new StringBuilder();
		for(Font f: ge.getAllFonts()){
			sb.append(f.getFontName()+"\n");
			break;
		}
		fontList.setText(sb.toString());
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx=0.7;
		gbc.weighty=0.2;
		gbc.fill=GridBagConstraints.BOTH;
		fontField.addActionListener(l->{
			String fontName = fontField.getText();
			fontField.setText("");
			Font f = new Font(fontName,Font.PLAIN,15);
			testArea.setFont(f);
			testArea.repaint();
			testArea.revalidate();
		});
		panel.add(fontField,gbc);

		gbc.gridy=1;
		gbc.weighty=0.8;
		panel.add(new ScrollComponent<>(testArea),gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 0.3;
		gbc.weighty=1;
		gbc.gridheight = 2;
		panel.add(new ScrollComponent<>(fontList),gbc);

		frame.setContentPane(panel);
		frame.setSize(1400,800);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);



	}


}
