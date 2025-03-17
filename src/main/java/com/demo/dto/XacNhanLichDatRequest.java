package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class XacNhanLichDatRequest {
    private Long lichDatId;
    private Integer nhanVienId;
    private List<Long> caLamIds;
    private LocalDate ngayDat;
}
