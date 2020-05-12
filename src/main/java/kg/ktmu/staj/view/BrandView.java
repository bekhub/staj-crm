package kg.ktmu.staj.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import kg.ktmu.staj.entity.Brand;
import kg.ktmu.staj.form.BrandForm;
import kg.ktmu.staj.service.BrandService;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Route(value = "brand", layout = MainLayout.class)
@PageTitle("Brands | Staj CRM")
public class BrandView extends VerticalLayout {

    private final BrandService service;
    private final Grid<Brand> grid;
    private final BrandForm form;

    public BrandView(BrandService service) {
        this.service = service;
        grid = new Grid<>(Brand.class);
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new BrandForm();
        form.addListener(BrandForm.SaveEvent.class, this::saveBrand);
        form.addListener(BrandForm.DeleteEvent.class, this::deleteBrand);
        form.addListener(BrandForm.CancelEvent.class, e -> closeEditor());
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
        grid.removeColumnByKey("photo");
        grid.setColumns("id", "title");
        grid.addComponentColumn(brand -> {
            byte[] photo = brand.getPhoto();
            StreamResource resource = new StreamResource(brand.getTitle(),
                    () -> new ByteArrayInputStream(photo));
            Image image;
            if(photo.length == 0)
                image = new Image("https://vaadin.com/images/trademark/PNG/VaadinLogomark_RGB_500x500.png", "image");
            else
                image = new Image(resource, brand.getTitle());
            image.setMaxHeight("30px");
            image.setMaxWidth("30px");
            return image;
        }).setHeader("Photo");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editBrand(event.getValue()));
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }

    private void saveBrand(BrandForm.SaveEvent event) {
        byte[] image = new byte[0];
        try {
            image = IOUtils.toByteArray(form.getBuffer().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Brand brand = event.getBrand();
        brand.setPhoto(image);
        service.save(brand);
        updateList();
        closeEditor();
    }

    private void deleteBrand(BrandForm.DeleteEvent event) {
        service.delete(event.getBrand());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        Button addBrandButton = new Button("Add brand");
        addBrandButton.addClickListener(click -> addBrand());

        HorizontalLayout toolbar = new HorizontalLayout(addBrandButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addBrand() {
        grid.asSingleSelect().clear();
        editBrand(new Brand());
    }

    public void editBrand(Brand brand) {
        if(brand == null) {
            closeEditor();
        } else {
            form.setBrand(brand);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setBrand(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
