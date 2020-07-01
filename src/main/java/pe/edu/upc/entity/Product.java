package pe.edu.upc.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Ingrese su nombre del producto*")
	@Column(name = "name", nullable = false, length = 90)
	private String name;

	@DecimalMin("1.00")
	@DecimalMax("5.00")
	@Column(name = "price", columnDefinition = "Decimal(8,2)", nullable = false)
	private Double price;

	@ManyToOne
	@JoinColumn(name = "idCategory", nullable = false)
	private Category category;

	@Column(name = "dateIn")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateIn;

	@NotNull(message = "La fecha es obligatoria")
	@Future(message = "La fecha debe estar en el futuro")
	@Column(name = "dateOf")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOf;

	
	
	@PrePersist
	public void prePersist() {
		dateIn = new Date();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getDateIn() {
		return dateIn;
	}
	public void setDateIn(Date dateIn) {
		this.dateIn = dateIn;	}
	public Date getDateOf() {
		return dateOf;	}
	public void setDateOf(Date dateOf) {
		this.dateOf = dateOf;	}
}
