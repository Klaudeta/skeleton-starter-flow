package com.vaadin.starter.skeleton.grideditor;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

@Route("grid")
public class GridEditorView extends VerticalLayout {

	public GridEditorView() {
		Grid<Bean> grid = new Grid<>();
		List<Bean> items = new ArrayList<>(); // some test data 
		for (int i = 0; i < 1; i++) {
			Bean bean = new Bean(i + "");
			items.add(bean);
		}
		ListDataProvider<Bean> ldp = new ListDataProvider<>(items);
		grid.setDataProvider(ldp);
		Grid.Column<Bean> nameColumn = grid.addColumn(bean -> bean.getName()).setHeader("name");

		Binder<Bean> binder = new Binder<>(Bean.class);
		Editor<Bean> editor = grid.getEditor();
		editor.setBinder(binder);
		TextField field = new TextField();
		field.addAttachListener(event -> {
			System.out.println("GridEditorView.GridEditorView()");
		});
		binder.forField(field).bind(Bean::getName, Bean::setName);
		nameColumn.setEditorComponent(field);
		editor.addOpenListener(e -> {
//			grid.select(e.getItem());

			field.getElement().getNode().runWhenAttached(ui -> ui.getPage()
					.executeJavaScript(
							"setTimeout(function(){Polymer.flush ? Polymer.flush() : Polymer.dom.flush();debugger;console.log($0);$0.focus();},0)",
							field.getElement()));

		});


		grid.addFocusListener(event -> {
			System.out.println("GridEditorView.GridEditorView()");
		});
		grid.addItemClickListener(e -> editor.editItem(e.getItem()));
		Button button = new Button("Click to add item. This should also open the editor and focus on the TextField",
				e -> {
					Bean bean = new Bean("added from button");
					items.add(bean);
					ldp.refreshAll();
					editor.editItem(bean);
				});

		Button addReadonly = new Button("Add readonly row");
		addReadonly.addClickListener(event -> {
			Bean bean = new Bean("added from button");
			items.add(bean);
			ldp.refreshAll();
//			editor.editItem(bean);
		});

		Button focus = new Button("Focus Editor Textfield");
		focus.addClickListener(event -> {
			field.focus();
		});

		Button editFirst = new Button("Edit first row");
		editFirst.addClickListener(event -> {
			editor.editItem(items.get(0));
		});

		add(new Span("Click Grid item to open editor. Field should be focused immediately (this works)."), grid,
				button, addReadonly, focus, editFirst);

	}

	public static class Bean {
		private String name;

		public Bean(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
