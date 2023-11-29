package org.example.util.template;

public abstract class ReportProductTemplate {
    public final void generateReport() {
        initialize();
        createHeader();
        generateContent();
        createFooter();
        finalizeReport();
    }

    protected abstract void initialize();

    protected abstract void generateContent();

    protected abstract void createFooter();

    protected void finalizeReport() {}


    private void createHeader() {
        System.out.println("Creating report header...");
    }
}
