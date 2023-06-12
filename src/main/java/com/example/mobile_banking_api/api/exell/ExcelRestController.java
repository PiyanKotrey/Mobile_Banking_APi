package com.example.mobile_banking_api.api.exell;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/excel")
public class ExcelRestController {
    @Autowired
    private ResourceLoader resourceLoader;
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws IOException {
        //add layout excel
        Resource resource = resourceLoader.getResource("classpath:static/generate.xlsx");
        InputStream templateStream = resource.getInputStream();

        // Create a new workbook and sheet
        Workbook workbook = new XSSFWorkbook(templateStream);
        Sheet sheet = workbook.getSheetAt(0);
        //create list for store users
            List<Person> people = Arrays.asList(
            new Person("Piyan Kotrey",20,"kotrey@gmail.com","097776655","ADMIN",100,""),
            new Person("Vorn Saran",21,"saran@gmail.com","088775556","ADMIN",100,""),
            new Person("Chea Chento",90,"toto@gmail.com","087788666","ADMIN",100,""),
            new Person("Piyan Kotrey",20,"kotrey@gmail.com","097776655","ADMIN",100,""),
            new Person("Vorn Saran",21,"saran@gmail.com","088775556","ADMIN",100,""),
            new Person("Chea Chento",90,"toto@gmail.com","087788666","ADMIN",100,"")
    );
    // Create header row and style
    Row headerRow1 = sheet.createRow(8);
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    Font headerFont = workbook.createFont();
    headerFont.setBold(true);
    headerStyle.setFont(headerFont);

   // Set header values
    String[] headers = {"Name", "Age", "Email" ,"PhoneNumber" ,"Role" ,"Score","Image"};
    for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow1.createCell(i);
        cell.setCellValue(headers[i]);
        cell.setCellStyle(headerStyle);
    }

    int rowNum=9;
    for (Person person : people){
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(person.getName());
        row.createCell(1).setCellValue(person.getAge());
        row.createCell(2).setCellValue(person.getEmail());
        row.createCell(3).setCellValue(person.getPhoneNumber());
        row.createCell(4).setCellValue(person.getRole());
        row.createCell(5).setCellValue(person.getScore());
        row.createCell(6).setCellValue(person.getImage());


        // Add image
        if (person.getImage() != null) {
            String imagePath = "static/2404-messi.jpg";
            InputStream imageStream = new ClassPathResource(imagePath + person.getImage()).getInputStream();
            byte[] imageBytes = IOUtils.toByteArray(imageStream);
            int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_JPEG);
            imageStream.close();

            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(6); // Image column index
            anchor.setRow1(rowNum - 1); // Row index

            Picture picture = drawing.createPicture(anchor, pictureIdx);
            // Resize the image to fit in the cell
            int maxWidth = 90; // Set the maximum width of the image in pixels
            int maxHeight = 20; // Set the maximum height of the image in pixels
            int width = picture.getImageDimension().width;
            int height = picture.getImageDimension().height;
            double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);

            picture.resize(scale);
        }
    }



    // Set the response headers for Excel file download
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=layout.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();


    }


}

