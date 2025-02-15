package com.innoveworkshop.bolota.ui.components;

import com.innoveworkshop.bolota.models.Document;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

/**
 * Bolota document outline viewer.
 */
public class DocumentViewer extends JTree {
	public Document doc;

	/**
	 * Initializes a Bolota {@link Document} viewer {@link JTree}.
	 */
	public DocumentViewer() {
		super();

		// Set up the component itself.
		setCellRenderer(new BolotaFieldRenderer());
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setRootVisible(false);
		setShowsRootHandles(true);
		setEditable(true);
	}

	/**
	 * Initializes a Bolota {@link Document} viewer {@link JTree}.
	 *
	 * @param doc Associated Bolota document object.
	 */
	public DocumentViewer(Document doc) {
		this();
		openDocument(doc);
	}

	/**
	 * Opens a document in the viewer.
	 *
	 * @param doc Bolota document to be associated with the viewer.
	 */
	public void openDocument(Document doc) {
		// Load the new document into the tree.
		this.doc = doc;
		setModel(new DefaultTreeModel(doc, false));
	}
}
