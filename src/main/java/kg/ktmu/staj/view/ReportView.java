package kg.ktmu.staj.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Report;
import kg.ktmu.staj.form.ReportForm;
import kg.ktmu.staj.service.ReportService;

@Route(value = "report", layout = MainLayout.class)
@PageTitle("Reports | Staj CRM")
public class ReportView extends VerticalLayout {

    private final ReportService service;
    private final Grid<Report> grid;
    private final ReportForm form;

    public ReportView(ReportService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        grid = new Grid<>(Report.class);
        configureGrid();

        form = new ReportForm();
        form.addListener(ReportForm.SaveEvent.class, this::saveReport);
        form.addListener(ReportForm.DeleteEvent.class, this::deleteReport);
        form.addListener(ReportForm.CancelEvent.class, e -> closeEditor());
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
        grid.setColumns("id", "text", "money", "created");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editReport(event.getValue()));
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

    private void saveReport(ReportForm.SaveEvent event) {
        service.save(event.getReport());
        updateList();
        closeEditor();
    }

    private void deleteReport(ReportForm.DeleteEvent event) {
        service.delete(event.getReport());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addReportButton = new Button("Add report");
        addReportButton.addClickListener(click -> addReport());

        HorizontalLayout toolbar = new HorizontalLayout(addReportButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addReport() {
        grid.asSingleSelect().clear();
        editReport(new Report());
    }

    public void editReport(Report report) {
        if(report == null) {
            closeEditor();
        } else {
            form.setReport(report);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setReport(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
