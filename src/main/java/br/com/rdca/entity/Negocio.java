package br.com.rdca.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Negocio extends PanacheEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nome;

    @Column(nullable=true)
    private String cnpj;

    @Column(nullable=true)
    private String endereco;

    
}