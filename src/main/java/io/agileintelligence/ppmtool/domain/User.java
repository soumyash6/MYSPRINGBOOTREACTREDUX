package io.agileintelligence.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "my_user_id")
    private Long myUserId;

    @Email(message = "Username should be an email")
    @NotBlank(message = "Username should not be blank")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "Full name should not be blank")
    private String fullName;

    @NotBlank(message = "Password should not be blank")
    private String password;

    @Transient
    private String confirmPassword;

    @Temporal(TemporalType.DATE)
    private Date create_At;

    @Temporal(TemporalType.DATE)
    private Date update_At;

    // OneToMany with Project
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
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

    // Implement getUsername method required by UserDetails
    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }


    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN")); // Example of an admin role
        return authorities;
    }

    // Implement account status methods
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true; // Update based on your business logic
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true; // Update based on your business logic
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true; // Update based on your business logic
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true; // Update based on your business logic, e.g., user activation
    }

    @Override
    public String toString() {
        return "User [id=" + myUserId + ", username=" + username + ", fullName=" + fullName + ", password=" + password
                + ", confirmPassword=" + confirmPassword + ", create_At=" + create_At + ", update_At=" + update_At
                + "]";
    }
}
