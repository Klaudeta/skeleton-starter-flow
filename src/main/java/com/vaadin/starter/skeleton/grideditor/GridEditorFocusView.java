package com.vaadin.starter.skeleton.grideditor;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.starter.skeleton.grideditor.GridEditorView.Bean;

//Reproducing focus issue on the component added as a new row into a grid
@Route("focus")
public class GridEditorFocusView extends VerticalLayout {

	public GridEditorFocusView() {

		Grid<Bean> grid = new Grid<>();
		List<Bean> items = new ArrayList<>(); // some test data
		for (int i = 0; i < 2; i++) {
			Bean bean = new Bean(i + "");
			items.add(bean);
		}
		ListDataProvider<Bean> dataProvider = new ListDataProvider<>(items);
		grid.setDataProvider(dataProvider);

		grid.addComponentColumn(item -> {
			final TextField f = new TextField();
			f.setValue(item.getName());
			f.focus();
			return f;
		}).setHeader("name");

		Button button = new Button("Click to add item",
				e -> {
					Bean bean = new Bean("added from button");
					items.add(bean);
					dataProvider.refreshAll();
				});
		add(grid, button);

	}

}
