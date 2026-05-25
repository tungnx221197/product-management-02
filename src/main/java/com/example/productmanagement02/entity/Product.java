package com.example.productmanagement02.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PRODUCT")
@EntityListeners(AuditingEntityListener.class)
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PRODUCT_ID")
  private Long productId;

  @Column(name = "PRODUCT_NAME", nullable = false, length = 200)
  private String productName;

  @Column(precision = 12, scale = 2)
  private BigDecimal price = BigDecimal.ZERO;

  @Column(name = "STOCK_QTY")
  private Integer stockQty = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CATEGORY_ID", nullable = false)
  private Category category;

  @CreatedDate
  @Column(name = "CREATED_AT", updatable = false)
  private LocalDateTime createdAt;

  @CreatedBy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID")
  private User createdBy;

  @CreatedDate
  @Column(name = "UPDATED_AT")
  private LocalDateTime updatedAt;

  @CreatedBy
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "UPDATED_BY", referencedColumnName = "USER_ID")
  private User updatedBy;
}
