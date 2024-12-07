package com.youcode.product_managementV2.Entity;

import com.youcode.product_managementV2.Entity.Product;
import com.youcode.product_managementV2.Entity.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_table")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

    private Boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Product> products;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Map the `roleName` to GrantedAuthority
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
    }

    @Override
    public String getUsername() {
        return login;
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
        return active != null && active;
    }
}
