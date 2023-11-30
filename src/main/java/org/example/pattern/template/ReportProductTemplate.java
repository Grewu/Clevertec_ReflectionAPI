package org.example.pattern.template;

import org.example.entity.Product;

public abstract class ReportProductTemplate {
    public final void generateReport(Product product) {
        initialize(product);
        createHeader();
        finalizeReport();
    }

    protected abstract void initialize(Product product);

    protected void finalizeReport() {
        System.out.println("Pdf has been created");
    }


    private void createHeader() {
        System.out.println("Creating report header...");
    }
}
