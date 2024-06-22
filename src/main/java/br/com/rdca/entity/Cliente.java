package br.com.rdca.entity;

import java.util.Date;

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
public class Cliente extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nome;

    @Column
    private String cpf;

    @Column
    private Date dataNascimento;
    
    @Column
    private String email;

    @Column
    private String telefone;

}