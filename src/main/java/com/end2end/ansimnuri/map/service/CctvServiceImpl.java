package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.entity.Cctv;
import com.end2end.ansimnuri.map.domain.repository.CctvRepository;
import com.end2end.ansimnuri.map.dto.CctvDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Cell;

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

    @Transactional
    @Override
    public void insert() {
        cctvRepository.deleteAll();

        ClassPathResource resource = new ClassPathResource("static/excel/12_04_08_E_CCTV정보.xlsx");
        try(InputStream inputStream = resource.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
        ) {
            Sheet sheet = workbook.getSheetAt(0);

            List<Cctv> cctvList = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // 헤더 행 건너뛰기
                }
                if (row == null) {
                    continue;
                }

                String stringDate = getStringCell(row.getCell(9));
                if (stringDate == null || stringDate.isEmpty()) {
                    continue;
                }

                LocalDate installDate = LocalDate.parse(getStringCell(row.getCell(9)) + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                CctvDTO dto = CctvDTO.builder()
                        .latitude(Double.parseDouble(getStringCell(row.getCell(11))))
                        .longitude(Double.parseDouble(getStringCell(row.getCell(12))))
                        .address(getStringCell(row.getCell(3)))
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
