package se.casparsylwan.cugexam.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import se.casparsylwan.cugexam.entity.CugExamUser;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Name", "Email", "Country", "Roles", "Exams passed", "total attempts" };
    static String SHEET = "Tutorials";
    public static ByteArrayInputStream cugExamUsersToExcel(List<CugExamUser> examUsers) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);
            // Header
            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }
            int rowIdx = 1;
            for (CugExamUser examUser : examUsers) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(examUser.getName());
                row.createCell(1).setCellValue(examUser.getEmail());
                row.createCell(2).setCellValue(examUser.getCountry());
                row.createCell(3).setCellValue(examUser.getRoles());
                row.createCell(4).setCellValue(examUser
                        .getExamsTaken()
                        .stream()
                        .filter( user -> user.isPassed())
                        .collect(Collectors.toList())
                        .size());
                row.createCell(5).setCellValue(examUser.getExamsTaken().size());

            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
