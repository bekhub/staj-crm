package kg.ktmu.staj.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import kg.ktmu.staj.entity.Measurement;

public class MeasurementForm extends FormLayout {

    TextField title = new TextField("Title");
    TextField symbol = new TextField("Symbol");

    Binder<Measurement> binder = new BeanValidationBinder<>(Measurement.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public MeasurementForm() {
        addClassName("list-form");
        binder.bindInstanceFields(this);

        add(title, symbol, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new MeasurementForm.DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new MeasurementForm.CancelEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new MeasurementForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setMeasurement(Measurement measurement) {
        binder.setBean(measurement);
    }

    public static abstract class MeasurementFormEvent extends ComponentEvent<MeasurementForm> {

        private final Measurement measurement;

        protected MeasurementFormEvent(MeasurementForm source, Measurement measurement) {
            super(source, false);
            this.measurement = measurement;
        }

        public Measurement getMeasurement() {
            return measurement;
        }
    }

    public static class SaveEvent extends MeasurementForm.MeasurementFormEvent {
        SaveEvent(MeasurementForm source, Measurement measurement) {
            super(source, measurement);
        }
    }

    public static class DeleteEvent extends MeasurementForm.MeasurementFormEvent {
        DeleteEvent(MeasurementForm source, Measurement measurement) {
            super(source, measurement);
        }
    }

    public static class CancelEvent extends MeasurementForm.MeasurementFormEvent {
        CancelEvent(MeasurementForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType,
            ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
