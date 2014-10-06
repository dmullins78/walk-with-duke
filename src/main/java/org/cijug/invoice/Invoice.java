package org.cijug.invoice;

import javax.persistence.*;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "invoice")
@NamedQuery(name = Invoice.FIND_BY_NAME, query = "select a from Invoice a where a.name = :name")
public class Invoice implements java.io.Serializable {
    private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	public static final String FIND_BY_NAME = "Invoice.findByName";

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
    @NotBlank(message = Invoice.NOT_BLANK_MESSAGE)
    private String name;

    @Column
	private Integer amount;

    @Column
    private boolean paid;

    public Long getId() {
		return id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isOutstanding() {
        return !paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
