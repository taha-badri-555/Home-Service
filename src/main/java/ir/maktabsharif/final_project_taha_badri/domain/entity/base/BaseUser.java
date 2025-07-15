package ir.maktabsharif.final_project_taha_badri.domain.entity.base;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = BaseUser.TABLE_NAME)
@DiscriminatorColumn(name = "user_type")

public class BaseUser extends BaseEntity<Long> {
    public static final String TABLE_NAME = "users";

    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String PASSWORD_COLUMN = "password";
    public static final String EMAIL_COLUMN = "email";


    @Column(name = FIRST_NAME_COLUMN)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN)
    private String lastName;

    @Column(name = PASSWORD_COLUMN)
    private String password;

    @Column(name = EMAIL_COLUMN)
    private String email;
}
