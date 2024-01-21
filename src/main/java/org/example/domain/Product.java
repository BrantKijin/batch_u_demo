package org.example.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
	private Integer productId;
	private String productName;
	private String productCategory;
	private Integer productPrice;
}
