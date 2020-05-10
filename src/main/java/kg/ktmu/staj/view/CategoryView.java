package kg.ktmu.staj.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Category;
import kg.ktmu.staj.service.CategoryService;

@Route("category")
public class CategoryView extends VerticalLayout {

    private final CategoryService service;
    private Grid<Category> grid;

    public CategoryView(CategoryService service) {
        this.service = service;
        grid = new Grid<>(Category.class);
        configureGrid();

        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.setColumns("id", "title");
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}
