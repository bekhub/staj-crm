package kg.ktmu.staj.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import kg.ktmu.staj.entity.Brand;
import kg.ktmu.staj.entity.Category;
import kg.ktmu.staj.entity.Measurement;
import kg.ktmu.staj.entity.Product;
import kg.ktmu.staj.form.ProductForm;
import kg.ktmu.staj.service.BrandService;
import kg.ktmu.staj.service.CategoryService;
import kg.ktmu.staj.service.MeasurementService;
import kg.ktmu.staj.service.ProductService;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Route(value = "", layout = MainLayout.class)
@PageTitle("Products | Staj CRM")
public class ProductView extends VerticalLayout {

    private final Grid<Product> grid = new Grid<>(Product.class);
    private final ProductService service;
    private final ProductForm form;
    private final TextField filterText = new TextField();

    public ProductView(ProductService service, CategoryService categoryService,
                       BrandService brandService, MeasurementService measurementService) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new ProductForm(categoryService.findAll(), brandService.findAll(),
                measurementService.findAll());
        form.addListener(ProductForm.SaveEvent.class, this::saveProduct);
        form.addListener(ProductForm.DeleteEvent.class, this::deleteProduct);
        form.addListener(ProductForm.CancelEvent.class, e -> closeEditor());
        closeEditor();

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
    }

    private void saveProduct(ProductForm.SaveEvent event) {
        byte[] image = new byte[0];
        try {
            image = IOUtils.toByteArray(form.getBuffer().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = event.getProduct();
        product.setPhoto(image);
        service.save(product);
        updateList();
        closeEditor();
    }

    private void deleteProduct(ProductForm.DeleteEvent event) {
        service.delete(event.getProduct());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addProductButton = new Button("Add product");
        addProductButton.addClickListener(click -> addProduct());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduct(new Product());
    }

    private void configureGrid() {
        grid.addClassName("list-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("category");
        grid.removeColumnByKey("brand");
        grid.removeColumnByKey("measurementType");
        grid.setColumns("title", "quantity", "price", "grossWeight");
        grid.addColumn(product -> {
            Category category = product.getCategory();
            return category == null ? "-" : category.getTitle();
        }).setHeader("Category");
        grid.addColumn(product -> {
            Brand brand = product.getBrand();
            return brand == null ? "-" : brand.getTitle();
        }).setHeader("Brand");
        grid.addColumn(product -> {
            Measurement measurement = product.getMeasurementType();
            return measurement == null ? "-" : measurement.getTitle();
        }).setHeader("Measurement type");
        grid.addComponentColumn(product -> {
            byte[] photo = product.getPhoto();
            StreamResource resource = new StreamResource(product.getTitle(),
                    () -> new ByteArrayInputStream(photo));
            Image image;
            if(photo.length == 0)
                image = new Image("https://vaadin.com/images/trademark/PNG/VaadinLogomark_RGB_500x500.png", "image");
            else
                image = new Image(resource, product.getTitle());
            image.setMaxHeight("30px");
            image.setMaxWidth("30px");
            return image;
        }).setHeader("Photo");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(event ->
                editProduct(event.getValue()));
    }

    public void editProduct(Product product) {
        if(product == null) {
            closeEditor();
        } else {
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
}
