package kg.ktmu.staj.form;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import kg.ktmu.staj.entity.Report;

public class ReportForm extends FormLayout {

    NumberField money = new NumberField("Money");
    TextArea text = new TextArea("Text");

    Binder<Report> binder = new BeanValidationBinder<>(Report.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    public ReportForm() {
        addClassName("list-form");
        binder.bindInstanceFields(this);

        add(text, money, createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new ReportForm.DeleteEvent(this, binder.getBean())));
        cancel.addClickListener(event -> fireEvent(new ReportForm.CancelEvent(this)));

        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        if(binder.isValid()) {
            fireEvent(new ReportForm.SaveEvent(this, binder.getBean()));
        }
    }

    public void setReport(Report report) {
        binder.setBean(report);
    }

    public static abstract class ReportFormEvent extends ComponentEvent<ReportForm> {

        private final Report report;

        protected ReportFormEvent(ReportForm source, Report report) {
            super(source, false);
            this.report = report;
        }

        public Report getReport() {
            return report;
        }
    }

    public static class SaveEvent extends ReportForm.ReportFormEvent {
        SaveEvent(ReportForm source, Report report) {
            super(source, report);
        }
    }

    public static class DeleteEvent extends ReportForm.ReportFormEvent {
        DeleteEvent(ReportForm source, Report report) {
            super(source, report);
        }
    }

    public static class CancelEvent extends ReportForm.ReportFormEvent {
        CancelEvent(ReportForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(
            Class<T> eventType,
            ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
