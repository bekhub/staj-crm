package kg.ktmu.staj.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Category;
import kg.ktmu.staj.form.CategoryForm;
import kg.ktmu.staj.service.CategoryService;

@Route(value = "category", layout = MainLayout.class)
@PageTitle("Categories | Staj CRM")
public class CategoryView extends VerticalLayout {

    private final CategoryService service;
    private final Grid<Category> grid = new Grid<>(Category.class);
    private final CategoryForm form;

    public CategoryView(CategoryService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new CategoryForm();
        form.addListener(CategoryForm.SaveEvent.class, this::saveCategory);
        form.addListener(CategoryForm.DeleteEvent.class, this::deleteCategory);
        form.addListener(CategoryForm.CancelEvent.class, e -> closeEditor());
        closeEditor();

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("list-grid");
        grid.setSizeFull();
        grid.setColumns("id", "title");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editCategory(event.getValue()));
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

    private void saveCategory(CategoryForm.SaveEvent event) {
        service.save(event.getCategory());
        updateList();
        closeEditor();
    }

    private void deleteCategory(CategoryForm.DeleteEvent event) {
        service.delete(event.getCategory());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addCategoryButton = new Button("Add category");
        addCategoryButton.addClickListener(click -> addCategory());

        HorizontalLayout toolbar = new HorizontalLayout(addCategoryButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addCategory() {
        grid.asSingleSelect().clear();
        editCategory(new Category());
    }

    public void editCategory(Category category) {
        if(category == null) {
            closeEditor();
        } else {
            form.setCategory(category);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setCategory(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
