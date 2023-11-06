package com.backend.dream.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdate")
    private Date createDate = new Date();

    private String note;

    @Temporal(TemporalType.TIME)
    @Column(name = "createtime")
    private Time createTime = Time.valueOf(LocalTime.now());

    @Column(name = "totalamount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "idaccount")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "idstatus")
    private OrderStatus status;

    @JsonIgnore
    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    private List<OrderDetails> detail;

}
