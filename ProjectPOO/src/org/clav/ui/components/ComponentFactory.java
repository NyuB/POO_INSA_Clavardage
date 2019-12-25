package org.clav.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public interface ComponentFactory {
	JLabel createLabel(String title);

	JTextArea createTextArea();

	JTextField createTextField();

	JCheckBox createCheckBox();

	JButton createButton(String title);

	JButton createCloseButton();

	LabeledTickBox createLabeledTickBox(JLabel label);

	CloseLabel createTabCloseLabel(String title, ActionListener l);

	Color getMainColor();

	void setMainColor(Color mainColor);

	Font getMainFontItalicBold();

	void setMainFontItalicBold(Font mainFontItalicBold);

	Color getDangerColor();

	void setDangerColor(Color dangerColor);

	Color getBorderColor();

	void setBorderColor(Color borderColor);

	Color getTextColor();

	void setTextColor(Color textColor);

	Font getMainFont();

	void setMainFont(Font mainFont);

	Font getMainFontBold();

	void setMainFontBold(Font mainFontBold);

	Font getMainFontItalic();

	void setMainFontItalic(Font mainFontItalic);
}
