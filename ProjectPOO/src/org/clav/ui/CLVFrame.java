package org.clav.ui;

import javax.swing.*;
import java.awt.*;

public class CLVFrame extends JFrame {
	private CLVModel model;

	public CLVFrame() throws HeadlessException {
		super("CLV APP");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setSize(1400,800);
	}
}
