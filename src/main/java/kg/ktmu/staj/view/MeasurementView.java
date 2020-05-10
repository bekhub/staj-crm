package kg.ktmu.staj.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Measurement;
import kg.ktmu.staj.service.MeasurementService;

@Route("measurement")
public class MeasurementView extends VerticalLayout {

    private final MeasurementService service;
    private final Grid<Measurement> grid;

    public MeasurementView(MeasurementService service) {
        this.service = service;
        grid = new Grid<>(Measurement.class);
        configureGrid();

        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.setColumns("id", "title", "symbol");
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}
