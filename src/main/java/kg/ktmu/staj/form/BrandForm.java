package kg.ktmu.staj.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import kg.ktmu.staj.entity.Brand;
import lombok.Getter;

public class BrandForm extends FormLayout {

    TextField title = new TextField("Title");
    @Getter
    MemoryBuffer buffer = new MemoryBuffer();
    Upload photo = new Upload(buffer);

    Binder<Brand> binder = new BeanValidationBinder<>(Brand.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public BrandForm() {
        addClassName("list-form");
        binder.bindInstanceFields(this);
        setUpload();


        add(title, photo, createButtonsLayout());
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
        delete.addClickListener(event -> fireEvent(new BrandForm.DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new BrandForm.CancelEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new BrandForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setBrand(Brand brand) {
        binder.setBean(brand);
    }

    public static abstract class BrandFormEvent extends ComponentEvent<BrandForm> {

        private final Brand brand;

        protected BrandFormEvent(BrandForm source, Brand brand) {
            super(source, false);
            this.brand = brand;
        }

        public Brand getBrand() {
            return brand;
        }
    }

    public static class SaveEvent extends BrandForm.BrandFormEvent {
        SaveEvent(BrandForm source, Brand brand) {
            super(source, brand);
        }
    }

    public static class DeleteEvent extends BrandForm.BrandFormEvent {
        DeleteEvent(BrandForm source, Brand brand) {
            super(source, brand);
        }
    }

    public static class CancelEvent extends BrandForm.BrandFormEvent {
        CancelEvent(BrandForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType,
            ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
