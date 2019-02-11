package com.vaadin.starter.skeleton.grideditor;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.skeleton.grideditor.GridEditorView.Bean;

//Reproducing focus issue with the editor field when a new row is added on the grid
@Route("grid-editor")
public class CleanGridEditorView extends VerticalLayout {

	public CleanGridEditorView() {
		Grid<Bean> grid = new Grid<>();
		List<Bean> items = new ArrayList<>(); // some test data
		for (int i = 0; i < 2; i++) {
			Bean bean = new Bean(i + "");
			items.add(bean);
		}
		ListDataProvider<Bean> ldp = new ListDataProvider<>(items);
		grid.setDataProvider(ldp);

		Binder<Bean> binder = new Binder<>(Bean.class);
		Editor<Bean> editor = grid.getEditor();
		editor.setBinder(binder);
		TextField field = new TextField();
		binder.forField(field).bind(Bean::getName, Bean::setName);
		grid.addColumn(bean -> bean.getName()).setEditorComponent(field).setHeader("name");

		editor.addOpenListener(e -> {
			field.focus();
		});

		grid.addItemClickListener(e -> editor.editItem(e.getItem()));
		Button button = new Button("Click to add item", e -> {
			Bean bean = new Bean("added from button");
			items.add(bean);
			ldp.refreshAll();
			editor.editItem(bean);
		});
		Button focus = new Button("Focus Editor Textfield");
		focus.addClickListener(event -> {
			field.focus();
		});

		add(grid, button, focus);
	}
}
