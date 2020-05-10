package kg.ktmu.staj.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import kg.ktmu.staj.entity.Report;
import kg.ktmu.staj.service.ReportService;

@Route(value = "report", layout = MainLayout.class)
@PageTitle("Reports | Staj CRM")
public class ReportView extends VerticalLayout {

    private final ReportService service;
    private final Grid<Report> grid;

    public ReportView(ReportService service) {
        this.service = service;
        grid = new Grid<>(Report.class);
        configureGrid();

        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.setColumns("id", "text", "money", "created");
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}
