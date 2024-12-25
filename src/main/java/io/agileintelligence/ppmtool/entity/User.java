package io.agileintelligence.ppmtool.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Schema(description = "User entity")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_user_id")
    @Schema(description = "Unique identifier for the user", example = "1")
    private Long myUserId;

    @Email(message = "Username should be an email")
    @NotBlank(message = "Username should not be blank")
    @Column(unique = true)
    @Schema(description = "Username of the user", example = "user@example.com")
    private String username;

    @NotBlank(message = "Full name should not be blank")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @NotBlank(message = "Password should not be blank")
    @Schema(description = "Password of the user", example = "password123")
    private String password;

    @Transient
    @Schema(description = "Confirmation password of the user", example = "password123")
    private String confirmPassword;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Creation date of the user", example = "2023-01-01")
    private Date create_At;

    @Temporal(TemporalType.DATE)
    @Schema(description = "Last update date of the user", example = "2023-06-01")
    private Date update_At;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    @Schema(description = "List of projects associated with the user")
    private List<Project> projects = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.update_At = new Date();
    }

    public User() {
        super();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + myUserId + ", username=" + username + ", fullName=" + fullName + ", password=" + password
                + ", confirmPassword=" + confirmPassword + ", create_At=" + create_At + ", update_At=" + update_At
                + "]";
    }
}