package com.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loai_dich_vu")
public class LoaiDichVu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loai_dich_vu_id")
    private Integer loaiDichVuId;

    @Column(name = "ten_loai_dich_vu")
    private String tenLoaiDichVu;

    @Column(name = "xoa")
    private Integer xoa = 0;

}
