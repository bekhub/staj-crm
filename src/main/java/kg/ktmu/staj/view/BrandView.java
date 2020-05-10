package kg.ktmu.staj.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Brand;
import kg.ktmu.staj.service.BrandService;

@Route(value = "brand", layout = MainLayout.class)
@PageTitle("Brands | Staj CRM")
public class BrandView extends VerticalLayout {

    private final BrandService service;
    private final Grid<Brand> grid;

    public BrandView(BrandService service) {
        this.service = service;
        grid = new Grid<>(Brand.class);
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
