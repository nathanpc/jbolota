package com.innoveworkshop.bolota.ui.components;

import com.innoveworkshop.bolota.models.Field;
import com.innoveworkshop.bolota.models.fields.DateField;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.text.SimpleDateFormat;

/**
 * A customized {@link javax.swing.tree.TreeCellRenderer} for Bolota {@link Field}s.
 */
public class BolotaFieldRenderer extends DefaultTreeCellRenderer {

	/**
	 * Initializes the Bolota field tree cell renderer.
	 */
	public BolotaFieldRenderer() {
		super();
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
			boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		Field field = (Field)value;

		// Sets the node's text to the field's text.
		setText(field.getText());

		// Handle the rendering of each field type.
		if (field instanceof DateField) {
			handleDateField((DateField)field);
		}

		return this;
	}

	/**
	 * Handles the rendering of a {@link DateField} node.
	 *
	 * @param field     Date field to be rendered.
	 */
	private void handleDateField(DateField field) {
		// TODO: Set icon to calendar.

		// Display the date and time alongside the field's text.
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		setText("(" + sdf.format(field.getDate()) + ") " + field.getText());
	}

}
