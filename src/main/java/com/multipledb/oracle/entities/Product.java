package com.multipledb.oracle.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "m_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String name;
    private Integer price;
}
