package kg.ktmu.staj.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import kg.ktmu.staj.entity.Brand;
import kg.ktmu.staj.entity.Category;
import kg.ktmu.staj.entity.Measurement;
import kg.ktmu.staj.entity.Product;
import lombok.Getter;

import java.util.List;

public class ProductForm extends FormLayout {

    TextField title = new TextField("Title");
    @Getter
    MemoryBuffer buffer = new MemoryBuffer();
    Upload photo = new Upload(buffer);
    IntegerField quantity = new IntegerField("Quantity");
    IntegerField quantityInStock = new IntegerField("Quantity in stock");
    NumberField addedValue = new NumberField("Added value");
    NumberField addedPrice = new NumberField("Added price");
    NumberField grossWeight = new NumberField("Gross weight");
    NumberField price = new NumberField("Price");
    TextField barcode = new TextField("Barcode");
    ComboBox<Category> category = new ComboBox<>("Category");
    ComboBox<Brand> brand = new ComboBox<>("Brand");
    ComboBox<Measurement> measurementType = new ComboBox<>("Measurement Type");

    Binder<Product> binder = new BeanValidationBinder<>(Product.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ProductForm(List<Category> categories, List<Brand> brands, List<Measurement> measurements) {
        addClassName("list-form");
        binder.bindInstanceFields(this);
        setUpload();

        category.setItems(categories);
        category.setItemLabelGenerator(Category::getTitle);
        brand.setItems(brands);
        brand.setItemLabelGenerator(Brand::getTitle);
        measurementType.setItems(measurements);
        measurementType.setItemLabelGenerator(Measurement::getTitle);

        add(title,
                category,
                brand,
                measurementType,
                quantity,
                quantityInStock,
                price,
                addedPrice,
                addedValue,
                grossWeight,
                photo,
                barcode,
                createButtonsLayout()
        );
    }

    private void setUpload() {
        photo.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        photo.setMaxFiles(1);
        photo.setMaxFileSize(20000);
        photo.setDropLabel(new Label("Upload a 20 KB image(.png, .jpeg, .gif)"));

        photo.addFileRejectedListener(event -> {
            Notification.show(event.getErrorMessage());
        });
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new CancelEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public void setProduct(Product product) {
        binder.setBean(product);
    }

    public static abstract class ProductFormEvent extends ComponentEvent<ProductForm> {

        private final Product product;

        protected ProductFormEvent(ProductForm source, Product product) {
            super(source, false);
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }

    public static class SaveEvent extends ProductFormEvent {
        SaveEvent(ProductForm source, Product product) {
            super(source, product);
        }
    }

    public static class DeleteEvent extends ProductFormEvent {
        DeleteEvent(ProductForm source, Product product) {
            super(source, product);
        }
    }

    public static class CancelEvent extends ProductFormEvent {
        CancelEvent(ProductForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType,
            ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
