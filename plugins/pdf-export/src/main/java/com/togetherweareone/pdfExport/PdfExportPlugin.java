package com.togetherweareone.pdfExport;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.togetherweareone.fx.Fx;
import com.togetherweareone.fx.extensions.ProjectViewExtensionPoint;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.pf4j.Extension;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

import java.io.File;
import java.io.IOException;

public class PdfExportPlugin extends Plugin {

    public PdfExportPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        System.out.println("Pdf Export plugin found !");
    }

    @Override
    public void stop() {
        System.out.println("Pdf Export plugin shutting down");
    }

    @Extension
    public static class PdfExportProjectViewExtension implements ProjectViewExtensionPoint {

        @Override
        public void loadProjectViewModifications(Scene scene) {
            GridPane pane = (GridPane) scene.lookup("#gridPane");
            Button addColumnButton = (Button) scene.lookup("#addColumnButton");

            addColumnButton.setText("+");
            addColumnButton.setPrefSize(30, 30);
            GridPane.setHalignment(addColumnButton, HPos.RIGHT);
            addColumnButton.setTranslateX(-20);

            Button btn = new Button();

            btn.setText("Export PDF");
            btn.setPrefSize(100, 30);

            btn.setOnAction(e -> {

                /*try {
                    File pdfOutput = new File("/" + Fx.currentProject.getTitle() + ".txt");
                    if (pdfOutput.createNewFile()) {
                        System.out.println("File created: " + pdfOutput.getName());
                    } else {
                        System.out.println("File already exists.");
                    }

                    Document document = new Document(new PdfDocument(new PdfWriter(pdfOutput)));

                    document.add(new Paragraph("Hello ceci est un test"));

                    document.close();

                } catch (IOException error) {
                    System.out.println("An error occurred.");
                    error.printStackTrace();
                }*/

            });

            pane.add(btn, 2, 0);
        }
    }

}
