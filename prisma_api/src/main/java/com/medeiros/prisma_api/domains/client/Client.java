package com.medeiros.prisma_api.domains.client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;
    @Column(name = "owner", nullable = false)
    private String Owner;
    @Column(name = "cnpj", nullable = false)
    private String cnpj;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "email")
    private String email;

    public Client(ClientRequestDTO dto) {
        this.storeName = dto.storeName();
        this.Owner = dto.owner();
        this.email = dto.email();
        this.cnpj = dto.cnpj();
        this.address = dto.address();
    }
}
