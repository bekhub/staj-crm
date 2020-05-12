package kg.ktmu.staj.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Measurement;
import kg.ktmu.staj.form.MeasurementForm;
import kg.ktmu.staj.service.MeasurementService;

@Route(value = "measurement", layout = MainLayout.class)
@PageTitle("Measurements | Staj CRM")
public class MeasurementView extends VerticalLayout {

    private final MeasurementService service;
    private final Grid<Measurement> grid;
    private final MeasurementForm form;

    public MeasurementView(MeasurementService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        grid = new Grid<>(Measurement.class);
        configureGrid();

        form = new MeasurementForm();
        form.addListener(MeasurementForm.SaveEvent.class, this::saveMeasurement);
        form.addListener(MeasurementForm.DeleteEvent.class, this::deleteMeasurement);
        form.addListener(MeasurementForm.CancelEvent.class, e -> closeEditor());
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
        grid.setColumns("id", "title", "symbol");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editMeasurement(event.getValue()));
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

    private void saveMeasurement(MeasurementForm.SaveEvent event) {
        service.save(event.getMeasurement());
        updateList();
        closeEditor();
    }

    private void deleteMeasurement(MeasurementForm.DeleteEvent event) {
        service.delete(event.getMeasurement());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addMeasurementButton = new Button("Add measurement");
        addMeasurementButton.addClickListener(click -> addMeasurement());

        HorizontalLayout toolbar = new HorizontalLayout(addMeasurementButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addMeasurement() {
        grid.asSingleSelect().clear();
        editMeasurement(new Measurement());
    }

    public void editMeasurement(Measurement measurement) {
        if(measurement == null) {
            closeEditor();
        } else {
            form.setMeasurement(measurement);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setMeasurement(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
