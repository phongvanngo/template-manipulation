package novapo.research.templatemanipulationspring.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import novapo.research.templatemanipulationspring.service.filehandler.FileHandlerService;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class KickoffTemplateService {

    @Value("classpath:template-files/[Java] Amaris & Prudential Kick-off Project_CS Name_DD.MM.YYYY copy.pptx")
    private Resource resource;

    private final FileHandlerService fileHandlerService;

    private final String content = """
            CLIENT: Prudential Assurance Vietnam (PVA)
            DATE : Monday, 03rd April 2023
            PARTICIPANTS: 
            Prudentials:
            Mr. Nguyen Tran Huy Tam – Head of
            Mr. Nguyen Tran Huy Tam – Head of
            Mr. Nguyen Tran Huy Tam – Head of
            Mr. Nguyen Tran Huy Tam – Head of
            Amaris: 
            Mr. Ngo Van Phong – Software Engineer Consultant 
            Mr. Ngo Van Phong – Software Engineer Consultant 
            Mr. Ngo Van Phong – Software Engineer Consultant 
            Mr. Ngo Van Phong – Software Engineer Consultant 
            Mr. Huynh Tam Hao – Team Manager""";

    public void editTextbox() {
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (XMLSlideShow ppt = new XMLSlideShow(inputStream)) {
            // Get the slides from the PPT
            List<XSLFSlide> slides = ppt.getSlides();

            // Perform your modifications to the slides here
            int slideIndex = 0;
            XSLFSlide slide = ppt.getSlides().get(slideIndex);
            List<XSLFShape> shapes = slide.getShapes();
            XSLFShape shape = shapes.get(0);
            XSLFTextShape textShape = (XSLFTextShape) shapes.get(4);
            List<XSLFTextParagraph> textParagraphs = textShape.getTextParagraphs();


            textShape.setText(content);


            int clients = 4;
            int consultants = 5;
            int index = 3;

            for (int i = 0; i < 3; i++) {
                textParagraphs.get(i).getTextRuns().get(0).setFontSize((double) 24);
            }

            textParagraphs.get(index).setBulletCharacter("✓");
            textParagraphs.get(index).setIndentLevel(1);
            textParagraphs.get(index).getTextRuns().get(0).setFontSize((double) 24);


            for (int i = index + 1; i <= index + clients; i++) {
                textParagraphs.get(i).setBulletCharacter("•");
                textParagraphs.get(i).getTextRuns().get(0).setFontSize((double) 19);
                textParagraphs.get(i).setIndentLevel(1);
            }

            index = index + clients + 1;
            textParagraphs.get(index).setBulletCharacter("✓");
            textParagraphs.get(index).setIndentLevel(1);
            textParagraphs.get(index).getTextRuns().get(0).setFontSize((double) 24);


            for (int i = index + 1; i <= index + consultants; i++) {
                textParagraphs.get(i).setBulletCharacter("•");
                textParagraphs.get(i).getTextRuns().get(0).setFontSize((double) 19);
                textParagraphs.get(i).setIndentLevel(1);
            }


            // Save the modified PPT file
            ppt.write(new FileOutputStream("processing_files/output2.pptx"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final String role = "devops";
    private final String roleRequirements = """
            Apply the knowledge of OOP, Java, Spring boot, Spring JPA, Spring schedule, Microservices, Postgres, Oracle, MongoDB, etc. to develop products’ features.\s
            Be familiar with tools: Intellji, Kafka, Flowable...
            Understand the business flows/ system concepts. , Spring schedule, Microservices, Postgres, Oracle, MongoDB, etc. to de , Spring schedule, Microservices, Postgres, Oracle, MongoDB, etc. to de
            Be well-engaged with team / stakeholders.
            Attitude: honest, transparent, proactive.
            """;

    public void editTable() {
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (XMLSlideShow ppt = new XMLSlideShow(inputStream)) {
            // Get the slides from the PPT
            List<XSLFSlide> slides = ppt.getSlides();

            // Perform your modifications to the slides here
            int slideIndex = 1;
            XSLFSlide slide = ppt.getSlides().get(slideIndex);
            List<XSLFShape> shapes = slide.getShapes();
            XSLFShape shape = shapes.get(1);

            XSLFTable xslfTable = (XSLFTable) shapes.get(4);
            xslfTable.getCell(0, 0).setText("novapo");

            ((XSLFTextBox) shapes.get(7)).setText(role);
            setTextBoxFontSize(((XSLFTextBox) shapes.get(7)), 14);

            XSLFTextBox mainTask = (XSLFTextBox) shapes.get(5);

            mainTask.setText(roleRequirements);

            setTextColor(mainTask, Color.BLACK);

            setTextBoxFontSize(mainTask, 14);

            mainTask.resizeToFitText();

            setTextColor((XSLFTextBox) shapes.get(7), Color.BLACK);


            XSLFSimpleShape otherContentShape = (XSLFSimpleShape) shapes.get(6);


            setShapeCoordinates(otherContentShape, otherContentShape.getAnchor().getX(),
                    mainTask.getAnchor().getY() + mainTask.getAnchor().getHeight() - 25);


            ppt.write(new FileOutputStream("processing_files/output2.pptx"));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setShapeCoordinates(XSLFSimpleShape shape, double x, double y) {
        Rectangle2D anchor = shape.getAnchor();
        double width = anchor.getWidth();
        double height = anchor.getHeight();
        Rectangle2D newAnchor = new Rectangle2D.Double(x, y, width, height);
        shape.setAnchor(newAnchor);
    }

    public void setTextBoxFontSize(XSLFTextBox textBox, double fontSize) {
        for (XSLFTextParagraph paragraph : textBox.getTextParagraphs()) {
            for (XSLFTextRun textRun : paragraph.getTextRuns()) {
                textRun.setFontSize(fontSize);
            }
        }
    }

    public void setTextColor(XSLFTextBox textBox, java.awt.Color color) {
        for (XSLFTextParagraph paragraph : textBox.getTextParagraphs()) {
            for (XSLFTextRun textRun : paragraph.getTextRuns()) {
                textRun.setFontColor(color);
            }
        }
    }


}
