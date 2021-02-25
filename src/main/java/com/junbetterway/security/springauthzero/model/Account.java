package com.junbetterway.security.springauthzero.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

	private String name;
	private String remarks;
	private BigDecimal balance;
	
}
