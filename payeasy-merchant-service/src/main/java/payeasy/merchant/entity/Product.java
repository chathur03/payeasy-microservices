package payeasy.merchant.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity @Table(name="PRODUCTS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="PRODUCT_ID") private Long productId;

  @Column(name="MERCHANT_ID") private Long merchantId;
  @Column(name="NAME") private String name;
  @Column(name="DESCRIPTION") private String description;
  @Column(name="PRICE", precision=19, scale=2) private java.math.BigDecimal price;
  @Column(name="CATEGORY") private String category;
  @Column(name="STOCK") private Integer stock;
  @Column(name="CREATED_AT") private Instant createdAt;

  @PrePersist public void pre(){ createdAt = Instant.now(); }
}
