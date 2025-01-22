package com.innoveworkshop.bolota.ui.components;

import com.innoveworkshop.bolota.models.Document;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;

/**
 * Bolota document outline viewer.
 */
public class DocumentViewer extends JTree {
	public Document doc;

	/**
	 * Initializes a Bolota {@link Document} viewer {@link JTree}.
	 *
	 * @param doc Associated Bolota document object.
	 */
	public DocumentViewer(Document doc) {
		super(doc);
		this.doc = doc;

		// Set up the component itself.
		setCellRenderer(new BolotaFieldRenderer());
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(false);
		setShowsRootHandles(true);
		setEditable(true);
	}

}
