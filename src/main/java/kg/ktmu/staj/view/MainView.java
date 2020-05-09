package kg.ktmu.staj.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Brand;
import kg.ktmu.staj.entity.Category;
import kg.ktmu.staj.entity.Product;
import kg.ktmu.staj.service.ProductService;

@Route("")
public class MainView extends VerticalLayout {

    private Grid<Product> grid = new Grid<>(Product.class);
    private ProductService service;

    public MainView(ProductService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.addClassName("product-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("category");
        grid.removeColumnByKey("brand");
        grid.setColumns("title", "quantity", "price", "grossWeight");
        grid.addColumn(product -> {
            Category category = product.getCategory();
            return category == null ? "-" : category.getTitle();
        }).setHeader("Category");
        grid.addColumn(product -> {
            Brand brand = product.getBrand();
            return brand == null ? "-" : brand.getTitle();
        }).setHeader("Brand");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}
