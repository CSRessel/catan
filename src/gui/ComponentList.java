package gui;

import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;

public class ComponentList extends ArrayList<KComponent> {
	
	public void add(JComponent jC, Rectangle r) {
		super.add(new KComponent(jC, r));
	}
}
