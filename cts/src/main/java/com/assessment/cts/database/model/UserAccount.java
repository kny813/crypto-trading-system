package com.assessment.cts.database.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_account")
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;

    private String surname;

    private char gender;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp updatedDateTime;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Timestamp createdDateTime;
}
