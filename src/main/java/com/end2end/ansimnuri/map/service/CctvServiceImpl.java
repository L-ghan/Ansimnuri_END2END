package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.entity.Cctv;
import com.end2end.ansimnuri.map.domain.repository.CctvRepository;
import com.end2end.ansimnuri.map.dto.CctvDTO;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CctvServiceImpl implements CctvService {
    private final CctvRepository cctvRepository;

    @Override
    public List<CctvDTO> selectAll() {
        return cctvRepository.findAll().stream()
                .map(CctvDTO::of)
                .toList();
    }

    @Override
    public void insert() {
        ClassPathResource resource = new ClassPathResource("12_04_08_E_CCTV정보.xlsx");
        try(InputStream inputStream = resource.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
        ) {
            Sheet sheet = workbook.getSheetAt(0);

            List<Cctv> cctvList = new ArrayList<>();
            for (int i = 1; i < sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                LocalDate installDate = LocalDate.parse(getStringCell(row.getCell(9)), formatter);

                CctvDTO dto = CctvDTO.builder()
                        .latitude(Double.parseDouble(getStringCell(row.getCell(11))))
                        .longitude(Double.parseDouble(getStringCell(row.getCell(12))))
                        .address(getStringCell(row.getCell(2)))
                        .cameraCount(getIntCell(row.getCell(5)))
                        .installDate(installDate)
                        .build();
                cctvList.add(Cctv.of(dto));
            }

            cctvRepository.saveAll(cctvList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getStringCell(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long)cell.getNumericCellValue());
        } else {
            return cell.toString();
        }
    }

    private Integer getIntCell(Cell cell) {
        if (cell == null) return null;
        if (cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }
}
