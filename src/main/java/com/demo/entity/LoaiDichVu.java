package com.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loai_dich_vu")
public class LoaiDichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loai_dich_vu_id")
    private Long id;

    @Column(name = "ten_loai_dich_vu", nullable = false, length = 255)
    private String tenLoaiDichVu;

    @Column(name = "xoa", columnDefinition = "INT DEFAULT 0")
    private Integer xoa;
}
