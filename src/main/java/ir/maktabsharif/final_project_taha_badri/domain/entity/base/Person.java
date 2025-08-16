package ir.maktabsharif.final_project_taha_badri.domain.entity.base;

import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.domain.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table(name = Person.TABLE_NAME)
@DiscriminatorColumn(name = "user_type")

public class Person extends BaseEntity<Long> implements UserDetails {
    public static final String TABLE_NAME = "users";

    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String PASSWORD_COLUMN = "password";
    public static final String EMAIL_COLUMN = "email";
    public static final String ROLE_COLUMN = "role";
    public static final String STATUS_COLUMN = "expert_status";



    @Column(name = FIRST_NAME_COLUMN)
    private String firstName;

    @Column(name = LAST_NAME_COLUMN)
    private String lastName;

    @Column(name = PASSWORD_COLUMN)
    private String password;

    @Column(name = EMAIL_COLUMN,unique = true)
    private String email;

    @Column(name = ROLE_COLUMN,nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = STATUS_COLUMN)
    @Enumerated(EnumType.STRING)
    private ExpertStatus status;

    private boolean isVerified = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isVerified;
    }
}
